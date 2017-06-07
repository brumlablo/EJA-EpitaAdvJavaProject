package fr.epita.iam.tests;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;

/**
 * @author bb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestIdentityDAO {
	
	@Inject
	DAO<Identity> dao; //Dao<Identity> dao ...list identity dao, <java generic>
	
	@Inject
	SessionFactory sessionFactory;	
	
	private static final Logger LOGGER = LogManager.getLogger(TestIdentityDAO.class);
	
	@After
	public void cleanDBS(){
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.createQuery("delete from Identity").executeUpdate();
		t.commit();
		session.close();
	}
	
	@Test
	public void testHibernate(){
		
		
		//test listing identities and identity creation 
		List<Identity> result = dao.listAll();
		int ilSize = result.size();
		LOGGER.info("before: {} ",result);
		
		Identity identity1 = new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null,"admin");
		
		Identity identity2 = new Identity(null, "karel","haha","karel@tradada.com",null);
		Identity identity3 = new Identity(null, "blabla","5678","16468464@troll.net",null);
		Identity identity4 = new Identity(null, "karel","noha","tradicnineco@parada.it",null);
		
		Identity identity5 = new Identity(null, "imNotThere","ahaha","nicmoc@kra.cz",null);
		
		//creation
		dao.write(identity1);
		Assert.assertTrue(identity1.getUid() != 0);
		dao.write(identity2);
		dao.write(identity3);
		dao.write(identity4);
		
		result = dao.listAll();
		LOGGER.info("after creation: {} ",result);
			
		Assert.assertEquals(result.size(), ilSize +4 );
		
		List<Identity> results = dao.search(identity5.getEmail());
		LOGGER.info("after search: {}", results);
		Assert.assertTrue(results.isEmpty());
		
		//modification and search
		identity2.setEmail("kopecekslavy@huhu.com");
		dao.update(identity2);
		
		results.clear();
		results = dao.search(identity2.getDisplayName());
		Assert.assertTrue(results != null && ! results.isEmpty());
		LOGGER.info("results: {}", results.get(0).getEmail());
		Assert.assertTrue(results.get(0) != null && (results.get(0).getEmail().equals("kopecekslavy@huhu.com")));
		
		result = dao.listAll();
		LOGGER.info("after modification: {} ",result);
		ilSize = result.size();
		
		//erasing
		dao.erase(identity2);
		
		result = dao.listAll();
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
		List<Identity> result = new ArrayList<Identity>();
		result = query.list();
		
		LOGGER.info("hql query - found identities: {} ",result);
		
		Assert.assertTrue(!result.isEmpty());
		Assert.assertTrue(result.size() >= 1);
		
		session.close();
		
		
	}

}
