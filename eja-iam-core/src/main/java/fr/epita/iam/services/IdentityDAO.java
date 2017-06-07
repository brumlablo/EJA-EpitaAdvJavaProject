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
import org.springframework.stereotype.Repository;
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
public class IdentityDAO implements DAO<Identity> {
	
	
	//Session session;
	@Inject
	SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LogManager.getLogger(IdentityDAO.class);
	
	/**
	 * Default constructor
	 * 
	 */
	public IdentityDAO() {
		
	}
	

	/**
	 * Create new identity
	 * @param identity new identity
	 */
	public void write(Identity identity) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(identity);
		t.commit();
		session.close();
		LOGGER.info("created identity:  {}", identity);
	}
	
	/**
	 * Update existing identity
	 * @param identity updated identity
	 */
	public void update(Identity identity) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.update(identity); // hibernate finds and update identity on its own
		t.commit();
		session.close();
		LOGGER.info("modified identity:  {}", identity);
	}
	
	/**
	 * Erase/Delete identity
	 * @param identity identity to be deleted
	 */
	public void erase(Identity identity) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.delete(identity);
		t.commit();
		session.close();
	    LOGGER.info("deleted identity:  {}", identity);
	}
	
	/**
	 * Search identity based on string pattern
	 * @param criteria searching criteria
	 * @return ids list of found identities
	 */
	public List<Identity> search(String criteria) {
		
		Session session = sessionFactory.openSession();
		List<Identity> ids = new ArrayList<Identity>();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM Identity AS identity WHERE identity.displayName like :criteria"
				+ " or identity.email like :criteria"
				+ " or identity.dob like :criteria");
		//transaction - forces changes in cache to be updated to dbs
		query.setParameter("criteria", "%" + criteria + "%");
		ids  = query.list();
		t.commit();
		session.close();
		return ids;
		
	}
	
	/**
	 * 
	 * List all identities
	 * @return ids list of identities
	 */
	public List<Identity>listAll() {
		
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
	
	/**
	 * Search identity by address
	 * @param addr search address criteria
	 */
	public List<Identity> searchbyAddr(String addr) {
		
		//TODO fix search by address
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

	/**
	 * Search identity by its uid
	 * @param id identity uid
	 * @return found identity
	 */
	public Identity search(Long id) {
		
		//LOGGER.info("retrieving identity with id : {} ", id);
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		String queryString = "from Identity as identity where identity.id = :id";
		Query query = session.createQuery(queryString);
		query.setParameter("id", id);
		List<Identity> ids = new ArrayList<Identity>();
		ids = query.list();
		t.commit();
		session.close();
		return ids.get(0);
	}
	
	/*public Identity searchIdentityById(long id) {
		
		Session session = sessionFactory.openSession();
		return (Identity)session.load(Identity.class,id);
	
	}*/
}
