package fr.epita.iam.servlets;

import java.io.IOException;
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

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet(name="SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(SearchServlet.class);
	
	@Autowired
	DAO<Identity> dao;
       

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		String searchCrit = request.getParameter("searchCriteria");
		LOGGER.info("search criteria: {}",searchCrit);
		
		List<Identity> ids = new ArrayList<Identity>();
		ids = dao.search(searchCrit);
		if(!ids.isEmpty())
			LOGGER.info("Found identity: {}",ids.get(0).getDisplayName());

		request.setAttribute("identities", ids);
	    request.getRequestDispatcher("searchIdentity.jsp").forward(request, response);
	}

}
