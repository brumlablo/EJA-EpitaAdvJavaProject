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
import fr.epita.iam.services.DAO;

/**
 * Servlet implementation class AuthenticationServlet
 */

@WebServlet(name="AuthenticationServlet", urlPatterns = {"/authenticate"}) //my selected mapped method
public class AuthenticationServlet extends HttpServlet {
	
	private static final Logger LOGGER = LogManager.getLogger(AuthenticationServlet.class);
	private static final long serialVersionUID = 1L;
    
	@Autowired
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
		String password = req.getParameter("pwd"); //user input
		LOGGER.info("tried to authenticate with this login {}", login);
		
		Identity admin = new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null);
		dao.write(admin);
		
		/*authentication*/
		/*select for corresponding login*/
		List<Identity> foundIds = new ArrayList<Identity>();
		foundIds = dao.search(new Identity(null,login,null,null,null));
		if(foundIds.isEmpty())
		{
			LOGGER.info("Impossible to LOG IN");
		}
		else
		{
			Boolean success = false;
			for(Identity id : foundIds)
			{
				LOGGER.info("{} vs {}, {} vs {}",id.getDisplayName(),login,id.getPassword(),password);
				if((id.getDisplayName().equals(login)) && (id.getPassword().equals(password)))
				{
					success = true;
					break;
				}
					
			}
			if (success)
			{
				LOGGER.info("Successfully LOGGED IN");
				/*TODO: create response, redirect to the welcone page*/
			
			}
			else
			{
				LOGGER.info("FAIL to LOG IN");
			}
		}
		
	}

}
