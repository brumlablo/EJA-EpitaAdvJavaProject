/**
 * 
 */
package fr.epita.iam.iamcore.tests;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.IdentityJDBCDAO;

/**
 * @author bb
 *
 */
public class TestJDBCDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(TestJDBCDAO.class);
	
	@BeforeClass
	public static void globalSetup() throws SQLException{
		LOGGER.info("beginning the setup");
		Connection connection = DriverManager.getConnection("jdbc:derby:memory:IAM;create=true","bbbb","1234");
		PreparedStatement pstmt = connection.prepareStatement("CREATE TABLE IDENTITIES " 
	    + " (IDENTITY_UID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT IDENTITY_PK PRIMARY KEY, " 
	    + " IDENTITY_DISPLAYNAME VARCHAR(255), "
	    + " IDENTITY_EMAIL VARCHAR(255), "
	    + " IDENTITY_PASSWORD VARCHAR(255), "
	    + " IDENTITY_BIRTHDATE DATE "
	    + " )");
		
		pstmt.execute();
		connection.commit();
		pstmt.close();
		connection.close();
		LOGGER.info("setup finished : ready to proceed with the testcase");
		
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:derby:memory:IAM;create=true", "bbbb", "1234");
		return connection;
	}
	
	@Before
	public void setUp(){
		LOGGER.info("before test setup");
	}
	
	
	@Test
	public void basicTest() throws SQLException{
		
		
		IdentityJDBCDAO dao = new IdentityJDBCDAO();
		String displayName = "Barbora Bbbb";
		dao.writeIdentity(new Identity(null, displayName, "barbora.bbbb@gmail.com"));	
		
		String validationSql = "select * from IDENTITIES where IDENTITY_DISPLAYNAME=?";
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(validationSql);
		pstmt.setString(1, displayName);
		
		ResultSet rs = pstmt.executeQuery();
		List<String> displayNames = new ArrayList<String>();
		while(rs.next()){
			String foundDisplayName= rs.getString("IDENTITY_DISPLAYNAME");
			displayNames.add(foundDisplayName);
		}
		
		Assert.assertEquals(1, displayNames.size());
		Assert.assertEquals(displayName, displayNames.get(0));
		
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
