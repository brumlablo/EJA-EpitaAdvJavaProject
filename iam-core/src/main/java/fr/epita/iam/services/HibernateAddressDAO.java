package fr.epita.iam.services;

import java.sql.SQLException;
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
import fr.epita.iam.datamodel.Identity;

public class HibernateAddressDAO implements DAO<Address> {
	
	
	//Session session;
	@Inject
	SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LogManager.getLogger(HibernateDAO.class);
	
	public HibernateAddressDAO() {
		
	}
	
	public void write(Address addr) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.saveOrUpdate(addr);
		t.commit();
		session.close();
		LOGGER.info("created addr:  {}", addr);
	}
	
	public void update(Address addr) {
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.update(addr);
		t.commit();
		session.close();
		LOGGER.info("updated address : {} ", addr);
	}
	
	public void erase(Address addr) {
		
		
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.delete(addr);
		t.commit();
		session.close();
		LOGGER.info("deleted address : {} ", addr);
	}
	
	public List<Address> search(Address addr) {
		
		Session session = sessionFactory.openSession();	
		Query query = session.createQuery("FROM Address AS address WHERE address.addr like :addr");
		//transaction - forces changes in cache to be updated to dbs
		query.setParameter("addr", "%" + addr.getAddr());
		List<Address> addressList = query.list();
		session.close();
		LOGGER.info("searched address : {} ", addr);
		return addressList;
		
	}

	@Override
	public List<Address> readAll() {
		// TODO Auto-generated method stub
		return null;
	}
}
