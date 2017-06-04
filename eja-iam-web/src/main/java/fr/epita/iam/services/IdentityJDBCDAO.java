package fr.epita.iam.services;

import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.epita.iam.datamodel.Identity;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;

/**
 * Class connecting to and working with DBS
 * @author bb
 *
 */
@ContextConfiguration(locations={"/applicationContext.xml"})
public class IdentityJDBCDAO implements DAO<Identity> {
	
	//private Connection currCon;
	private static final Logger LOGGER = LogManager.getLogger(IdentityJDBCDAO.class);
	
	@Inject
	@Named("dataSourceBean")
	private DataSource ds;
	
	/**
	 * Authentication of user
	 * @param login user login
	 * @param password user password
	 * @return success of authentication
	 */
	public void authenticate(String login, String password) {
		
		LOGGER.debug("=> authentication");
		//Boolean success = false;
		
		try
		{
			PreparedStatement pstmt_select = ds.getConnection().prepareStatement("select * from IDENTITIES where IDENTITY_DISPLAYNAME = ? AND IDENTITY_PASSWORD = ?");
			pstmt_select.setString(1, login);
			pstmt_select.setString(2, password);
			
			pstmt_select.executeQuery();
			/*// was authentication successful?
			ResultSet rs = pstmt_select.executeQuery();
			if(rs.next()) {
				success = true;
			}
			return success;*/
		}
		catch(SQLException e1){
			e1.printStackTrace();
			LOGGER.debug("=> authentication SQL EXCEPTION");
			System.exit(-1);
		}
		catch(Exception e2) {
			e2.printStackTrace();
			LOGGER.debug("=> authentication EXCEPTION");
			System.exit(-1);
		}
		LOGGER.debug("=> authentication successfull");
	}
	
	/**
	 * Creation of new DBS identity
	 * @param identity identity (object)
	 * @return success of operation
	 */
	public void write(Identity identity){
		
		LOGGER.debug("=> write : tracing the input : {}", identity);
		Connection con;
		try {
			
			con = ds.getConnection();
			//Boolean succ = false; // success of operation
			
			String insertStatement = "insert into IDENTITIES (IDENTITY_DISPLAYNAME, IDENTITY_PASSWORD, IDENTITY_EMAIL, IDENTITY_BIRTHDATE, IDENTITY_ROLE) "
									+ "VALUES(?, ?, ?, ?)";
			
			PreparedStatement pstmt_insert = con.prepareStatement(insertStatement);
			pstmt_insert.setString(1, identity.getDisplayName());
			pstmt_insert.setString(2, identity.getPassword());
			pstmt_insert.setString(3, identity.getEmail());
			Date now = new Date();
			pstmt_insert.setDate(4,new java.sql.Date(now.getTime())); // default = now
			pstmt_insert.setString(5, identity.getRole());
			
			
			pstmt_insert.executeUpdate();
			// was creation successful?
			/*Integer rowsTouched = pstmt_insert.executeUpdate();
			if(rowsTouched >0) {
			    succ = true;
				LOGGER.debug("=> writeIdentity: leaving the method with no error" );
			}
			else
				LOGGER.debug("=> writeIdentity: leaving the method with ERROR" );
			
			return succ;*/
		}
		catch(SQLException e1){
			e1.printStackTrace();
			LOGGER.debug("=> write SQL EXCEPTION");
			System.exit(-1);
		}
		catch(Exception e2) {
			e2.printStackTrace();
			LOGGER.debug("=> write EXCEPTION");
			System.exit(-1);
		}
		LOGGER.debug("=> write: leaving the method with no error" );
	}
	
	/**
	 * Modification of identity (update)
	 * @param identity identity (object)
	 * @return success of operation
	 * @throws SQLException
	 */
	public void update(Identity identity) {
		
		LOGGER.debug("=> modify : tracing the input : {}", identity);
		try {
			Connection con = ds.getConnection();
			//Boolean succ = false;
			
			String updateStatement = "UPDATE IDENTITIES SET IDENTITY_DISPLAYNAME = ?, IDENTITY_PASSWORD = ?, IDENTITY_EMAIL = ?, IDENTITY_BIRTHDATE = ? WHERE IDENTITY_UID = ?";
			PreparedStatement pstmt_update = con.prepareStatement(updateStatement);
			
			pstmt_update.setString(1, identity.getDisplayName());
			pstmt_update.setString(2, identity.getPassword());
			pstmt_update.setString(3, identity.getEmail());
			pstmt_update.setDate(4, identity.getDOB());
			pstmt_update.setLong(5, identity.getUid());
			
			//SIDE NOTE: it should not be possible to change the role by normal user, therefore ommited
			
			pstmt_update.executeUpdate();
			// was modification successful?
			/*Integer rowsTouched = pstmt_update.executeUpdate();
			if(rowsTouched >0)
			    succ = true;
			return succ;*/
			LOGGER.debug("=> modify: leaving the method with no error" );
		}
		catch(SQLException e1){
			e1.printStackTrace();
			LOGGER.debug("=> modify SQL EXCEPTION");
			System.exit(-1);
		}
		catch(Exception e2) {
			e2.printStackTrace();
			LOGGER.debug("=> modify EXCEPTION");
			System.exit(-1);
		}
	}
	
	/**
	 * Deletion of identity
	 * @param id identity (object)
	 * @return success of operation
	 * @throws SQLException
	 */
	public void erase(Identity identity) {
		
		LOGGER.debug("=> delete with uid: tracing the input : {}", identity.getUid());
		Connection con;
		try {
			con = ds.getConnection();
			//Boolean succ = false;
	
			String eraseStatement = "delete from IDENTITIES where IDENTITY_UID = ?";
			PreparedStatement psmt_erase = con.prepareStatement(eraseStatement);
			psmt_erase.setString(1, identity.getUid().toString());
			
			psmt_erase.executeUpdate();
			// was deletion successful?
			/*Integer rowsTouched = psmt_erase.executeUpdate();
			if(rowsTouched >0)
			    succ = true;
			return succ;*/
		}
		catch(SQLException e1){
			e1.printStackTrace();
			LOGGER.debug("=> delete SQL EXCEPTION");
			System.exit(-1);
		}
		catch(Exception e2) {
			e2.printStackTrace();
			LOGGER.debug("=> delete EXCEPTION");
			System.exit(-1);
		}
		LOGGER.debug("=> delete: leaving the method with no error" );
	}
	
	/**
	 * Listing of all DBS identities and their information
	 * @return list of identities (from DBS) and their info
	 * @throws SQLException
	 */
	public List<Identity> readAll() {
		
		LOGGER.debug("=> readAll");
		List<Identity> identities = new ArrayList<Identity>();
		
		try
		{
			PreparedStatement pstmt_select = ds.getConnection().prepareStatement("select * from IDENTITIES");
			ResultSet rs = pstmt_select.executeQuery();
			while (rs.next()){
				String displayName = rs.getString("IDENTITY_DISPLAYNAME");
				Long uid = rs.getLong("IDENTITY_UID");
				String email = rs.getString("IDENTITY_EMAIL");
				String password = rs.getString("IDENTITY_PASSWORD");
				java.sql.Date dob = rs.getDate("IDENTITY_BIRTHDATE");
				String role = rs.getString("IDENTITY_ROLE");
				Identity identity = new Identity(uid, displayName, password, email, dob, role);
				identities.add(identity);
			}
		}
		catch(SQLException e1) {
			e1.printStackTrace();
			LOGGER.debug("=> readAll SQL EXCEPTION");
			System.exit(-1);
		}
		catch(Exception e2) {
			e2.printStackTrace();
			LOGGER.debug("=> readAll EXCEPTION");
			System.exit(-1);
		}
		LOGGER.traceExit("<= readAll : {}", identities);
		return identities;
	}



	
	public List<Identity> search(Identity identity) {

		LOGGER.debug("=> search");
		List<Identity> identities = new ArrayList<Identity>();
		/*todo finish JDBC search*/
		try
		{
			PreparedStatement pstmt_select = ds.getConnection().prepareStatement("select * from IDENTITIES where IDENTITY_DISPLAYNAME = ?");
			
			pstmt_select.setString(1, identity.getDisplayName());
			ResultSet rs = pstmt_select.executeQuery();
			while (rs.next()){
				String displayName = rs.getString("IDENTITY_DISPLAYNAME");
				Long uid = rs.getLong("IDENTITY_UID");
				String email = rs.getString("IDENTITY_EMAIL");
				String password = rs.getString("IDENTITY_PASSWORD");
				java.sql.Date dob = rs.getDate("IDENTITY_BIRTHDATE");
				String role = rs.getString("IDENTITY_ROLE");
				Identity idFound = new Identity(uid, displayName, password, email, dob,role);
				identities.add(idFound);
			}
		}
		catch(SQLException e1){
			e1.printStackTrace();
			LOGGER.debug("=> search SQL EXCEPTION");
			System.exit(-1);
		}
		catch(Exception e2) {
			e2.printStackTrace();
			LOGGER.debug("=> search EXCEPTION");
			System.exit(-1);
		}
		LOGGER.info("Found identites: {} ",identities);
		return identities;
	}
	
	
	
}
