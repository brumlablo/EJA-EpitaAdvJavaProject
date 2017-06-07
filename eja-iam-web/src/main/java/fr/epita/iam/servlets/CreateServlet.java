package fr.epita.iam.servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

/**
 * Servlet implementation class CreateServlet - identity creation
 */
@WebServlet(name="CreateServlet", urlPatterns = {"/create"})
public class CreateServlet extends HttpServlet {

	private static final Logger LOGGER = LogManager.getLogger(CreateServlet.class);
	private static final long serialVersionUID = 1L;
    
	@Inject
	DAO<Identity> dao;
	
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

		//LOGGER.info("Creating user: {} {} {} {} {}", username, password, email, date, role);
		dao.write(new Identity(null,username,password,email,date,role));
		req.setAttribute("statusMsg", "Identity successfully created.");
		req.setAttribute("statusColor", "green");
		req.getRequestDispatcher("welcome.jsp").forward(req, resp);
	}
}
