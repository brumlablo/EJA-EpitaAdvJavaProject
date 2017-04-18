/**
 * 
 */
package fr.epita.iam.iamcore.tests;

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
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author bb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestJDBCDAO {
	
	@Inject
	IdentityJDBCDAO dao;
	
	@Inject
	DataSource ds;
	
	private static final Logger LOGGER = LogManager.getLogger(TestJDBCDAO.class);
	
	public static void globalSetup(DataSource source) throws SQLException{
		LOGGER.info("beginning the setup");
		Connection con = source.getConnection();
		DatabaseMetaData dbmd = con.getMetaData();
		ResultSet rs = dbmd.getTables(null, "BBBB", "IDENTITIES", null);
		PreparedStatement pstmt;
		if(rs.next())
		{
			/*TABLE EXISTS >> ERASE */
			pstmt = con.prepareStatement("DROP TABLE IDENTITIES");
			pstmt.execute();
			con.commit();
			
		}
		pstmt = con.prepareStatement("CREATE TABLE IDENTITIES " 
			    + " (IDENTITY_UID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT IDENTITY_PK PRIMARY KEY, " 
			    + " IDENTITY_DISPLAYNAME VARCHAR(255), "
			    + " IDENTITY_EMAIL VARCHAR(255), "
			    + " IDENTITY_PASSWORD VARCHAR(255), "
			    + " IDENTITY_BIRTHDATE DATE "
			    + " )");
		
		pstmt.execute();
		con.commit();
		pstmt.close();
		con.close();
		LOGGER.info("setup finished : ready to proceed with the testcase");
		
	}
	
	@Before
	public void setUp() throws SQLException{
		LOGGER.info("before test setup");
	
		globalSetup(ds);
		
	}

	@Test
	public void basicTest() throws SQLException{
		
		String displayName = "bbbb";
		dao.writeIdentity(new Identity(null, displayName,"1234","barbora.bbbb@gmail.com",null));	
		
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
