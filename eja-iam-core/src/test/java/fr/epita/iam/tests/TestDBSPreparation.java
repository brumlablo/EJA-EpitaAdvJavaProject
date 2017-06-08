/**
 * 
 */
package fr.epita.iam.tests;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
public class TestDBSPreparation {

	@Inject
	DAO<Identity> dao;
	
	@Inject
	SessionFactory sf;	
	
	private static final Logger LOGGER = LogManager.getLogger(TestDBSPreparation.class);
	
	@Test
	public void test() {
		
		//DBS cleaning
		Session session = sf.openSession();
		Transaction t = session.beginTransaction();
		session.createQuery("delete from Identity").executeUpdate();
		t.commit();
		session.close();
		
		//DBS preparation
		Identity identity1 = new Identity(null, "admin","1234","barbora.bbbb@gmail.com","1955-10-05", "admin");
		
		Identity identity2 = new Identity(null, "admin2","1234","adminkarel@tradada.com","1993-04-16", "admin");
		Identity identity3 = new Identity(null, "blabla","5678","16468464@troll.net","1955-10-16");	
		Identity identity4 = new Identity(null, "eva","eva123","eva@kra.cz","1968-04-11");
		Identity identity5 = new Identity(null, "thomas","password","whee@whee.com","1968-04-11");
		
		//creation of identities
		dao.write(identity1);
		Assert.assertTrue(identity1.getUid() != 0);
		dao.write(identity2);
		dao.write(identity3);
		dao.write(identity4);
		dao.write(identity5);
		
		List<Identity> result = new ArrayList<Identity>();
		result = dao.listAll();
		Assert.assertTrue(! result.isEmpty());
		Assert.assertEquals(result.size(), 5 );
		LOGGER.info("DBS successfully prepared,  I wish you happy UI clickity clicking :)...");
	}

}
