package fr.epita.iam.iamcore.tests;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.HibernateDAO;
import fr.epita.iam.services.DAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})

public class TestHibernate {
	
	@Inject
	DAO<Identity> hibDao; //Dao<Identity> dao ...list identity dao, <java generic>
	
	@Inject
	SessionFactory sessionFactory;	
	
	private static final Logger LOGGER = LogManager.getLogger(TestHibernate.class);
	
	@Test
	public void testHibernate(){
		
		//test listing identities and identity creation 
		List<Identity> result = hibDao.readAll();
		int ilSize = result.size();
		LOGGER.info("before: {} ",result);
		
		Identity identity1 = new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null);
		
		Identity identity2 = new Identity(null, "karel","haha","karel@tradada.com",null);
		Identity identity3 = new Identity(null, "blabla","5678","16468464@troll.net",null);
		Identity identity4 = new Identity(null, "karel","noha","tradicnineco@parada.it",null);
		
		Identity identity5 = new Identity(null, "imNotThere","ahaha","nicmoc@kra.cz",null);
		
		//creation
		hibDao.write(identity1);
		Assert.assertTrue(identity1.getUid() != 0);
		hibDao.write(identity2);
		hibDao.write(identity3);
		hibDao.write(identity4);
		
		result = hibDao.readAll();
		LOGGER.info("after creation: {} ",result);
			
		Assert.assertEquals(result.size(), ilSize +4 );
		
		List<Identity> results = hibDao.search(identity5);
		LOGGER.info("fuck this shit: {}", results);
		Assert.assertTrue(results.isEmpty());
		
		//modification and search
		identity2.setEmail("kopecekslavy@huhu.com");
		hibDao.update(identity2);
		
		results.clear();
		results = hibDao.search(identity2);
		Assert.assertTrue(results != null && ! results.isEmpty());
		LOGGER.info("results: {}", results.get(0).getEmail());
		Assert.assertTrue(results.get(0) != null && (results.get(0).getEmail().equals("kopecekslavy@huhu.com")));
		LOGGER.info("noooooo");
		
		result = hibDao.readAll();
		LOGGER.info("after modification: {} ",result);
		ilSize = result.size();
		
		//erasing
		hibDao.erase(identity2);
		
		result = hibDao.readAll();
		LOGGER.info("after erasing: {} ",result);
		
		Assert.assertEquals(result.size(), ilSize - 1 );
			
	}
	@Test
	public void testHibernateHQL(){
		
		String hqlQuery = "FROM Identity AS identity WHERE identity.displayName = :displayName";
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		String dispName = "bbbb";
		session.save(new Identity(null, dispName,"2345","pozdravbobecek@kuk.sk",null));
		t.commit();
		
		//When
		Query query = session.createQuery(hqlQuery);
		query.setParameter("displayName", dispName);
		List<Identity> result = query.list();
		
		LOGGER.info("hql query - found identities: {} ",result);
		
		Assert.assertTrue(!result.isEmpty());
		Assert.assertTrue(result.size() >= 1);
		
		session.close();
		
		
	}

}
