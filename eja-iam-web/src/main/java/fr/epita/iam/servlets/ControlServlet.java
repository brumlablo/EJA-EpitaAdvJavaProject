package fr.epita.iam.servlets;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;

/**
 * Servlet implementation class ControlServlet - go to modifyIdentity.jsp or delete identity
 */
@WebServlet(name="ControlServlet", urlPatterns = {"/control"})
public class ControlServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(ControlServlet.class);
	
	@Inject
	DAO<Identity> dao;
       
	/**
	 * Takes form inputs from identity selected in the form list  in Searching page (search.jsp) 
	 * and decides if modify (>> Modification page) or delete identity based on selected action by clicking on corresponding button
	 * @param HttpServletRequest req http request
	 * @param HttpServletResponse resp http response
     */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		//nothing was selected >> go back
		String id = req.getParameter("selection");
		if(id == null)
		{
			resp.sendRedirect("searchIdentity.jsp");
			return;
		}
		
		String selectedAction = req.getParameter("action");
		Identity selectedId = null;
		
		selectedId = dao.search(Long.parseLong(id));
		
		if(selectedAction.equals("update")) 
		{
			//LOGGER.info("Modifying identity {}", selectedId.getDisplayName());		
			req.setAttribute("identity", selectedId);
			req.getRequestDispatcher("modifyIdentity.jsp").forward(req, resp);
		} 
		else if(selectedAction.equals("delete")) 
		{
			Long loggedUserUid = (Long) req.getSession().getAttribute("uid");
			if(selectedId.getUid().equals(loggedUserUid))
			{
				LOGGER.info("You cannot delete yourself.");
				req.setAttribute("statusMsg", "You cannot delete yourself.");
				req.setAttribute("statusColor", "red");
			}
			else
			{
				//LOGGER.info("Deleting identity {}", selectedId.getDisplayName());
				dao.erase(selectedId);
				req.setAttribute("statusMsg", "User successfully deleted.");
				req.setAttribute("statusColor", "green");
			}
			req.getRequestDispatcher("welcome.jsp").forward(req, resp);
		}
		else
		{
			LOGGER.info("ControlServlet - ACTION SELECTION ERROR");
			resp.sendRedirect("searchIdentity.jsp");
			return;
		}
	}

}
