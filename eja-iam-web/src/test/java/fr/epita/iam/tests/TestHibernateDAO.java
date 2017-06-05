/**
 * 
 */
package fr.epita.iam.tests;

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

import fr.epita.iam.datamodel.Address;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;


/**
 * @author bb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestHibernateDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(TestHibernateDAO.class);

	
	@Inject
	DAO<Identity> dao;
	
	@Inject
	DAO<Address> addrDao;
	
	@Inject
	SessionFactory sf;
	
	@After
	public void cleanDBS(){
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		session.createQuery("delete from Identity").executeUpdate();
		t.commit();
		session.close();
	}
	
	@Test
	public void testEndToEndCrud(){
		
		Identity identity = new Identity(null, "abcd","1234","abcd.efghb@gmail.com",null,"user");
		dao.write(identity);
		
		Assert.assertTrue(identity.getUid()!= 0);
		identity.setDisplayName("Perlimpinpin");
		dao.update(identity);
		
		List<Identity> results = dao.search("Perlimpinpin");
		Assert.assertTrue(results != null && !results.isEmpty());
		
		dao.erase(identity);
		
		results = dao.search("Perlimpinpin");
		Assert.assertTrue(results.isEmpty());
			
	}
	
	/*@Test
	public void testSearchByAddr(){
		
		
		Identity identity1 = new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null,true);
		Identity identity2 = new Identity(null, "karel","haha","karel@tradada.com",null);
		Identity identity3 = new Identity(null, "blabla","5678","16468464@troll.net",null);
		
		String[] addrs = new String[3];
		addrs[0] = "Bobakova 1, Kozoprdy 665 00";
		addrs[1] = "Nuf 1234, Brno 12345";
		addrs[2] = "Micova 117, Pragl 104 00";
		Address addr1 = new Address(null,addrs[0],identity1);
		Address addr2 = new Address(null,addrs[0],identity1);
		Address addr3 = new Address(null,addrs[1],identity2);
		
		addrDao.write(addr1);
		addrDao.write(addr2);
		addrDao.write(addr3);
		
		List<Address> results = addrDao.search(addrs[0]);
		LOGGER.info("before search: {} ",results);
		Assert.assertTrue(results.isEmpty());
		
		results = addrDao.search(addrs[0]);
		
		Assert.assertTrue(!results.isEmpty() && results.size() == 1);
		
		LOGGER.info("after search: {} ",results);
		
	}*/
}