package fr.epita.iam.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;


/**
 * Class connecting to and working with DBS using Hibernate FMW
 * @author bb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class IdentityHibernateJDBCDAO {
	
	@Inject
	SessionFactory sessionFactory;
	
	//Session session;
	
	private static final Logger LOGGER = LogManager.getLogger(IdentityHibernateJDBCDAO.class);
	
	public IdentityHibernateJDBCDAO()
	{
	}
	
	
	public void writeIdentity(Identity identity){
		
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(identity);
		LOGGER.info("created identity:  {}", identity);
	}
	
	
	public void modifyIdentity(long id) {
		
		Session session = sessionFactory.openSession();
		Identity identity;

	    identity = (Identity)session.load(Identity.class,id);
		identity.setEmail("kopecekslavy@huhu.com");
		session.update(identity); // hibernate finds and update identity on its own
		session.flush();
		LOGGER.info("modified identity:  {}", identity);
	}
	
	public void eraseIdentity(long id) {
		
		Session session = sessionFactory.openSession();
		Identity identity;

	    identity = (Identity)session.load(Identity.class,id);
	    session.delete(identity);

	    session.flush();
	    LOGGER.info("deleted identity:  {}", identity);
	}
	
	public List<Identity> searchIdentities(String displayName) {
		
		Session session = sessionFactory.openSession();
		List<Identity> ids = new ArrayList<Identity>();

		Criteria criteria = session.createCriteria(Identity.class);
		ids = (List<Identity>) criteria.add(Restrictions.ilike("IDENTITY_DISPLAYNAME", displayName, MatchMode.ANYWHERE)).uniqueResult();
		return ids;
		
	}
	
	public Identity searchIdentityById(long id) {
		
		Session session = sessionFactory.openSession();
		return (Identity)session.load(Identity.class,id);
	
	}
	
	public List<Identity>readAllIdentities() {
		
		Session session = sessionFactory.openSession();
		List<Identity> ids = new ArrayList<Identity>();
		ids = session.createCriteria(Identity.class).list();
		/* Iterator it = session.createCriteria(Identity.class).list().iterator();
		while (it.hasNext()){
			Identity id = (Identity) it.next();
			if(id instanceof Identity){
				ids.add(id);
			}
			else{
				System.err.println("FAIL");
			}
		
		}*/
		
		
		return ids;
		
	}
	

}
