package fr.epita.iam.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;

/**
 * Servlet implementation class LogoutServlet - logging out
 */
@WebServlet(name="LogoutServlet", urlPatterns = {"/logout"}) //my selected mapped method
public class LogoutServlet extends HttpServlet {
	
	private static final Logger LOGGER = LogManager.getLogger(LogoutServlet.class);
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
	
	/**
	 * Runs whilst clicking on "disconnect" link on welcome page (welcome.jsp), by default doGet method
	 * @param HttpServletRequest req http request
	 * @param HttpServletResponse resp http response
     */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		req.getSession().invalidate();
		LOGGER.info("User LOGGED OUT.");
		req.setAttribute("loginMsgColor", "green");
		req.setAttribute("loginMsg", "Successfully logged out.");
		req.getRequestDispatcher("index.jsp").forward(req, resp);
		
	}
	
}