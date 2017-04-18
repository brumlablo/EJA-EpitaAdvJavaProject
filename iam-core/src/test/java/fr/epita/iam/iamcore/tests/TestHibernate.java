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
import fr.epita.iam.services.IdentityHibernateJDBCDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})

public class TestHibernate {
	
	@Inject
	@Named("hibernateDao")
	IdentityHibernateJDBCDAO hibernateDao;
	
	private static final Logger LOGGER = LogManager.getLogger(TestHibernate.class);
	
	@Test
	public void testHibernate(){
		
		//test listing identities and identity creation 
		List<Identity> idenList = hibernateDao.readAllIdentities();
		int ilSize = idenList.size();
		LOGGER.info("before: {} ",idenList);
		
		Identity identity1 = new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null);
		Identity identity2 = new Identity(null, "karel","haha","karel@tradada.com",null);
		Identity identity3 = new Identity(null, "blabla","5678","16468464@troll.net",null);
		Identity identity4 = new Identity(null, "karel","noha","tradicnineco@parada.it",null);
		
		//creation
		hibernateDao.writeIdentity(identity1);
		hibernateDao.writeIdentity(identity2);
		hibernateDao.writeIdentity(identity3);
		hibernateDao.writeIdentity(identity4);
		
		idenList = hibernateDao.readAllIdentities();
		LOGGER.info("after creation: {} ",idenList);
		
		Assert.assertEquals(idenList.size(), ilSize +4 );
		
		//modification and search
		hibernateDao.modifyIdentity(2);
		
		Assert.assertEquals(hibernateDao.searchIdentityById(2).getEmail(),"kopecekslavy@huhu.com");
		
		/*TODO: fix advanced search*/
		//List<Identity> foundIds = new ArrayList<Identity>();
		//foundIds = hibernateDao.searchIdentities("karel");
		
		//LOGGER.info("identities found: {} ",foundIds);
		//Assert.assertEquals(foundIds.size(), 2 );
		
		idenList = hibernateDao.readAllIdentities();
		LOGGER.info("after modification: {} ",idenList);
		ilSize = idenList.size();
		
		//erasing
		hibernateDao.eraseIdentity(2);
		
		idenList = hibernateDao.readAllIdentities();
		LOGGER.info("after erasing: {} ",idenList);
		
		Assert.assertEquals(idenList.size(), ilSize - 1 );
		
		
	}

}
