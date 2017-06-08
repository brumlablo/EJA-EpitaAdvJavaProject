package fr.epita.iam.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;
import fr.epita.iam.services.IdentityDAO;

/**
 * Servlet implementation class ModifyServlet - modify the user
 */
@WebServlet(name="ModifyServlet", urlPatterns = {"/modify"})
public class ModifyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(ModifyServlet.class);
	
	@Inject
	DAO<Identity> dao;
       
	/**
	 * Takes form inputs from Identity creation (create.jsp)
	 * @param HttpServletRequest req http request
	 * @param HttpServletResponse resp http response
     */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		long uid = Long.parseLong(request.getParameter("uid"));
		String username = request.getParameter("userName");
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");
		String role = request.getParameter("role");
		String password = request.getParameter("password");
		
		Identity modifiedId;
		//leave the same password if empty
		if(password.equals("") || password == null)
		{
			modifiedId = dao.search(uid);
			modifiedId.setDisplayName(username);
			modifiedId.setEmail(email);
			modifiedId.setDOB(dob);
			modifiedId.setRole(role);
		}
		else
		{
			modifiedId = new Identity(uid, username, password, email, dob, role);
		}
		
		
		dao.update(modifiedId);
		request.setAttribute("statusMsg", "User successfully updated.");
		request.setAttribute("statusColor", "green");
		request.getRequestDispatcher("welcome.jsp").forward(request, response);
		

	}

}
