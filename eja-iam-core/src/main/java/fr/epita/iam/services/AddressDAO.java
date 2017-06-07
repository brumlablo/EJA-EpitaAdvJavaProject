package fr.epita.iam.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import fr.epita.iam.datamodel.Address;

/**
 * Class for storing address to identity
 * */
public class AddressDAO implements DAO<Address> {
	
	@Inject
	SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LogManager.getLogger(AddressDAO.class);

	/**
	 * Default constructor
	 */
	public AddressDAO() {
		
	}
	
	/**
	 * Create new address
	 * @param address new address
	 */
	public void write(Address addr) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(addr);
		t.commit();
		session.close();
		LOGGER.info("created addr:  {}", addr);
	}
	
	/**
	 * Update existing address
	 * @param address updated address
	 */
	public void update(Address addr) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.update(addr);
		t.commit();
		session.close();
		LOGGER.info("updated address : {} ", addr);
	}
	
	/**
	 * Delete/Erase address
	 * @param address address to be deleted
	 */
	public void erase(Address addr) {
		
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.delete(addr);
		t.commit();
		session.close();
		LOGGER.info("deleted address : {} ", addr);
	}
	
	/**
	 * Search address
	 * @param address address pattern criteria
	 */
	public List<Address> search(String addr) {
		
		Session session = sessionFactory.openSession();	
		Query query = session.createQuery("FROM Address AS address WHERE address.addr like :addr");
		//transaction - forces changes in cache to be updated to dbs
		query.setParameter("addr", "%" + addr);
		List<Address> addressList = new ArrayList<Address>();
		addressList = query.list();
		session.close();
		LOGGER.info("searched address : {} ", addr);
		return addressList;
		
	}

	/**
	 * 
	 * List all addresses to a identity (FUTURE)
	 */
	public List<Address> listAll() {
		// TODO return multiple adresses to a person
		return null;
	}
	
	/**
	 * Search address of identity based on identity uid  (FUTURE)
	 * 
	 */
	public Address search(Long uid) {
		// TODO return the address of the person with this uid
		return null;
	}
}
