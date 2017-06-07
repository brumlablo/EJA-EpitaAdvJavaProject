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
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.AuthenticateUser;
import fr.epita.iam.services.DAO;

/**
 * Servlet implementation class AuthenticationServlet - authenticates the user
 */

@WebServlet(name="AuthenticationServlet", urlPatterns = {"/authenticate"}) //my selected mapped method
public class AuthenticationServlet extends HttpServlet {
	
	private static final Logger LOGGER = LogManager.getLogger(AuthenticationServlet.class);
	private static final long serialVersionUID = 1L;
    
	@Inject
	DAO<Identity> dao;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		LOGGER.info("dao instance loaded: {}",dao);
		
		String login = req.getParameter("login"); //user input
		String pwd = req.getParameter("pwd"); //user input
		LOGGER.info("trying to authenticate with this login {}", login);
				
		/*authentication*/
		Identity user = AuthenticateUser.getInst().authenticate(login, pwd);
		if(user == null)
		{
			LOGGER.info("Authentication FAIL: The user and password combination does not exists.");
			req.setAttribute("loginMsgColor", "red");
			req.setAttribute("loginMsg", "Authentication not successfull.");
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			
		}
		else
		{
			LOGGER.info("Authentication SUCCESS: Logged in!");
			req.getSession().setAttribute("user", user.getDisplayName());
			req.getSession().setAttribute("uid", user.getUid());
			req.getSession().setAttribute("role", user.getRole());
			//user is not supposed to be able to create new identity
			/*if(user.getRole().equals("user")) {
				req.setAttribute("disabled", "disabled=\"disabled\"");
				req.setAttribute("createRestrictionMsg", "<h5>Sorry, you don't have rights to create new identity.</h5>");
			}
			else
			{
				req.setAttribute("disabled", "");
				req.setAttribute("createRestrictionMsg", "<h5></h5>");
			}*/
			resp.sendRedirect("welcome.jsp");
			//req.getRequestDispatcher("welcome.jsp").forward(req, resp);
		}
		
	}

}
