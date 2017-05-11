/**
 * 
 */
package fr.epita.iam.iamcore.tests;

import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;
import fr.epita.iam.services.IdentityJDBCDAO;


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
	SessionFactory sf;
	
	@Test
	public void testEndToEndCrud(){
		
		Identity identity = new Identity(null, "abcd","1234","abcd.efghb@gmail.com",null);
		dao.write(identity);
		
		Assert.assertTrue(identity.getUid()!= 0);
		identity.setDisplayName("Perlimpinpin");
		dao.update(identity);
		
		Identity criteria = new Identity(null, "Perlimpinpin", null, null, null);
		
		
		List<Identity> results = dao.search(criteria);
		Assert.assertTrue(results != null && !results.isEmpty());
		
		dao.erase(identity);
		
		results = dao.search(criteria);
		Assert.assertTrue(results.isEmpty());
			
	}
}