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
import org.hibernate.SessionFactory;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;

/**
 * Servlet implementation class AuthenticationServlet
 */
@WebServlet(name="AuthenticationServlet", urlPatterns = {"/authenticate"}) //my selected mapped method
public class AuthenticationServlet extends HttpServlet {
	
	private static final Logger LOGGER = LogManager.getLogger(AuthenticationServlet.class);
	private static final long serialVersionUID = 1L;
    
	@Inject
	DAO<Identity> dao;
	/**
     * Default constructor. 
     */
    public AuthenticationServlet() {
        // TODO Auto-generated constructor stub
    }

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
		
		LOGGER.info("dao instance loaded: {}",dao);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		LOGGER.info("tried to authenticate with this login {}", login);
		
		Identity me = new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null);
		/*todo - authentication*/
	}

}
