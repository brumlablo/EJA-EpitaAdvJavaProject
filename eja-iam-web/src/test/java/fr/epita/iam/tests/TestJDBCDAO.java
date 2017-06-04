/**
 * 
 */
package fr.epita.iam.tests;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;

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
public class TestJDBCDAO {
	
	@Inject
	DAO<Identity> dao;	
	
	@Inject
	DataSource ds;
	
	private static final Logger LOGGER = LogManager.getLogger(TestJDBCDAO.class);
	

	@Inject
	SessionFactory sessionFactory;	
	
	@Before
	public void setUp() throws SQLException{
		
		LOGGER.info("before test...");
	
	}
	
	@After
	public void cleanDBS(){
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.createQuery("delete from Identity").executeUpdate();
		t.commit();
		session.close();
	}

	@Test
	public void basicTest() throws SQLException{
		
		String displayName = "whee";
		dao.write(new Identity(null, displayName,"1234","wheee.whee@gmail.com",null));	
		
		String validationSql = "select * from IDENTITIES where IDENTITY_DISPLAYNAME=?";
		Connection con = ds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(validationSql);
		pstmt.setString(1, displayName);
		
		ResultSet rs = pstmt.executeQuery();
		List<String> displayNames = new ArrayList<String>();
		while(rs.next()){
			String foundDisplayName= rs.getString("IDENTITY_DISPLAYNAME");
			displayNames.add(foundDisplayName);
		}
		
		Assert.assertEquals(1, displayNames.size());
		Assert.assertEquals(displayName, displayNames.get(0));
		
		pstmt.close();
		rs.close();
		con.close();
		
	}
	
	
	@After
	public void tearDown(){
		LOGGER.info("after test cleanup");
	}
	
	
	@AfterClass()
	public static void globalTearDown(){
		LOGGER.info("global cleanup");
	}

}
