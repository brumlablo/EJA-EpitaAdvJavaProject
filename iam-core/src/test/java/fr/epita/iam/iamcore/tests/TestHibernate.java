package fr.epita.iam.iamcore.tests;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.HibernateDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})

public class TestHibernate {
	
	@Inject
	@Named("hibDao")
	HibernateDAO hibDao;
	
	private static final Logger LOGGER = LogManager.getLogger(TestHibernate.class);
	
	@Test
	public void testHibernate(){
		
		//test listing identities and identity creation 
		List<Identity> idenList = hibDao.readAllIdentities();
		int ilSize = idenList.size();
		LOGGER.info("before: {} ",idenList);
		
		Identity identity1 = new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null);
		
		Identity identity2 = new Identity(null, "karel","haha","karel@tradada.com",null);
		Identity identity3 = new Identity(null, "blabla","5678","16468464@troll.net",null);
		Identity identity4 = new Identity(null, "karel","noha","tradicnineco@parada.it",null);
		
		Identity identity5 = new Identity(null, "imNotThere","ahaha","nicmoc@kra.cz",null);
		
		//creation
		hibDao.writeIdentity(identity1);
		Assert.assertTrue(identity1.getUid() != 0);
		hibDao.writeIdentity(identity2);
		hibDao.writeIdentity(identity3);
		hibDao.writeIdentity(identity4);
		
		idenList = hibDao.readAllIdentities();
		LOGGER.info("after creation: {} ",idenList);
			
		Assert.assertEquals(idenList.size(), ilSize +4 );
		
		List<Identity> results = hibDao.searchIdentity(identity5);
		LOGGER.info("fuck this shit: {}", results);
		Assert.assertTrue(results.isEmpty());
		
		//modification and search
		identity2.setEmail("kopecekslavy@huhu.com");
		hibDao.updateIdentity(identity2);
		
		results.clear();
		results = hibDao.searchIdentity(identity2);
		Assert.assertTrue(results != null && ! results.isEmpty());
		LOGGER.info("results: {}", results.get(0).getEmail());
		Assert.assertTrue(results.get(0) != null && (results.get(0).getEmail().equals("kopecekslavy@huhu.com")));
		LOGGER.info("noooooo");
		
		idenList = hibDao.readAllIdentities();
		LOGGER.info("after modification: {} ",idenList);
		ilSize = idenList.size();
		
		//erasing
		hibDao.eraseIdentity(identity2);
		
		idenList = hibDao.readAllIdentities();
		LOGGER.info("after erasing: {} ",idenList);
		
		Assert.assertEquals(idenList.size(), ilSize - 1 );
		
		
	}

}
