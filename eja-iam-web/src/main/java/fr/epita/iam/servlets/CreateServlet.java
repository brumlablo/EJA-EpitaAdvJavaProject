package fr.epita.iam.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;

/**
 * Servlet implementation class CreateServlet - identity creation
 */
@WebServlet(name="CreateServlet", urlPatterns = {"/create"})
public class CreateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	@Inject
	DAO<Identity> dao;
	
	/**
	 * Takes form inputs from create.jsp
	 * @param HttpServletRequest req http request
	 * @param HttpServletResponse resp http response
     */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String date = req.getParameter("date");
		String role = req.getParameter("role");
		
		//Unchecked radiobox >> user
		if(role == null)
			role = "user";
		
	
		//if date not set, set today as default
		if(date.equals("")){
			java.util.Calendar cal = java.util.Calendar.getInstance();
			date = new java.sql.Date(cal.getTime().getTime()).toString();
		}
		
		/*ban creating user with existing username on UI level 
		 * (left possible in dbs for testing purposes)*/
		Boolean isBanned = false;
		List<Identity> ids = new ArrayList<Identity>();
		ids = dao.listAll();
		for(Identity id: ids)
		{
			if(username.equals(id.getDisplayName()))
			{
				isBanned = true;
				break;
			}
				
		}
		
		if(isBanned)
		{
			req.setAttribute("statusMsg", "You cannot create user with existing username. Try empty search to see all users.");
			req.setAttribute("statusColor", "red");
			
		}
		else
		{
			//LOGGER.info("Creating user: {} {} {} {} {}", username, password, email, date, role);
			dao.write(new Identity(null,username,password,email,date,role));
			req.setAttribute("statusMsg", "User successfully created.");
			req.setAttribute("statusColor", "green");
		}
		req.getRequestDispatcher("welcome.jsp").forward(req, resp);

	}
}
