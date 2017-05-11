package fr.epita.iam.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

public class HibernateDAO implements DAO<Identity> {
	
	
	//Session session;
	@Inject
	SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LogManager.getLogger(HibernateDAO.class);
	
	public HibernateDAO() {
		
	}
	
	
	public void write(Identity identity) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(identity);
		t.commit();
		session.close();
		LOGGER.info("created identity:  {}", identity);
	}
	
	
	public void update(Identity identity) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.update(identity); // hibernate finds and update identity on its own
		t.commit();
		session.close();
		LOGGER.info("modified identity:  {}", identity);
	}
	
	public void erase(Identity identity) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.delete(identity);
		t.commit();
		session.close();
	    LOGGER.info("deleted identity:  {}", identity);
	}
	
	public List<Identity> search(Identity identity) {
		
		Session session = sessionFactory.openSession();
		List<Identity> ids = new ArrayList<Identity>();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM Identity AS identity WHERE identity.displayName like :displayName");
		//transaction - forces changes in cache to be updated to dbs
		query.setParameter("displayName", "%" + identity.getDisplayName());
		ids  = query.list();
		t.commit();
		session.close();
		return ids;
		
	}
	
	
	/*public Identity searchIdentityById(long id) {
		
		Session session = sessionFactory.openSession();
		return (Identity)session.load(Identity.class,id);
	
	}*/
	
	public List<Identity>readAll() {
		
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
	
	/*todo: fix search by address*/
	public List<Identity> searchbyAddr(String addr) {
		
		Session session = sessionFactory.openSession();
		List<Identity> ids = new ArrayList<Identity>();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM Address AS address WHERE address.addr like :addr");
		//transaction - forces changes in cache to be updated to dbs
		query.setParameter("addr", "%" + addr);
		ids  = query.list();
		t.commit();
		session.close();
		return ids;
		
	}
}
