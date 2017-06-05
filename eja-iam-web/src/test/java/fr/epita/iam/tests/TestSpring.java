/**
 * 
 */
package fr.epita.iam.tests;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
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

public class TestSpring {
	
	@Inject
	DAO<Identity> dao;	
	
	private static final Logger LOGGER = LogManager.getLogger(TestSpring.class);
	
	/**
	 * @return
	 * @throws SQLException
	 */
	private static Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/IAM_DBS;create=true", "bbbb", "1234");
		return connection;
	}
	
	
	
	@Test
	public void testSpringContext() throws SQLException{
		dao.write(new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null,"admin"));
	}

}