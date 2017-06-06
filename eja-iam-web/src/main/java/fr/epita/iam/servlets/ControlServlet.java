package fr.epita.iam.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import fr.epita.iam.services.HibernateDAO;
import fr.epita.iam.services.PasswordEndecryptor;

/**
 * Servlet implementation class ControlServlet - go to modifyIdentity.jsp or delete identity
 */
@WebServlet(name="ControlServlet", urlPatterns = {"/control"})
public class ControlServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(ControlServlet.class);
	
	@Autowired
	DAO<Identity> dao;
       

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
			//LOGGER.info("Deleting identity {}", selectedId.getDisplayName());
			dao.erase(selectedId);
			req.setAttribute("statusMsg", "Identity successfully deleted.");
			req.setAttribute("statusColor", "green");
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
