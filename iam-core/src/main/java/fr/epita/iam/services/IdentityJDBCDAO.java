package fr.epita.iam.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epita.iam.datamodel.Identity;

/**
 * Class connecting to and working with DBS
 * @author bb
 *
 */
public class IdentityJDBCDAO {
	
	private Connection currCon;
	private static final Logger LOGGER = LogManager.getLogger(IdentityJDBCDAO.class);
	
	/**
	 * Constructor with connection to DBS
	 */
	public IdentityJDBCDAO() {
		
		try {
			
			getConnection();
			
		} catch(SQLException ex){
			System.out.println("SQL Exception when connecting to database.");
			ex.printStackTrace();
			System.exit(-1); // world failure
		} catch (Exception exx) {
			System.out.println("Exception when connecting to database.");
			exx.printStackTrace();
			System.exit(-1); // world failure
		}
	}
	
	/**
	 * Connecting to DBS with given access parameters
	 * @return current connection
	 * @throws Exception 
	 */
	private Connection getConnection() throws Exception {
		
		try {
			this.currCon.getSchema();
			LOGGER.info("connected to this schema:  {}", currCon.getSchema());
		}
		catch(Exception e){
			
			try {
				
				Configuration config = new Configuration();
				
				String user = config.getUser();
				String password = config.getPwd();
				String connectionString = config.getCon();
				//System.out.println(user, password, connectionString);
				LOGGER.info("connection props: {},{},{}", user, password, connectionString);
				
				this.currCon = DriverManager.getConnection(connectionString, user, password);
				LOGGER.info("connected to this schema:  {}", currCon.getSchema());
			}
			catch(Exception ex){
				throw ex;
				
			}
		}
		return currCon;

	}
	
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
			PreparedStatement pstmt_select = this.getConnection().prepareStatement("select * from IDENTITIES where IDENTITY_DISPLAYNAME = ? AND IDENTITY_PASSWORD = ?");
			pstmt_select.setString(1, login);
			pstmt_select.setString(2, password);
			
			pstmt_select.executeQuery();
			/*// was authentication successful?
			ResultSet rs = pstmt_select.executeQuery();
			if(rs.next()) {
				success = true;
			}
			return success;*/
			LOGGER.debug("=> authentication successfull");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LOGGER.debug("=> authentication EXCEPTION");
			System.exit(-1);
		}
	}
	
	/**
	 * Creation of new DBS identity
	 * @param identity identity (object)
	 * @return success of operation
	 */
	public void writeIdentity(Identity identity){
		
		LOGGER.debug("=> writeIdentity : tracing the input : {}", identity);
		Connection con;
		try {
			con = getConnection();
			//Boolean succ = false; // success of operation
			
			String insertStatement = "insert into IDENTITIES (IDENTITY_DISPLAYNAME, IDENTITY_PASSWORD, IDENTITY_EMAIL, IDENTITY_BIRTHDATE) "
									+ "VALUES(?, ?, ?, ?)";
			
			PreparedStatement pstmt_insert = con.prepareStatement(insertStatement);
			pstmt_insert.setString(1, identity.getDisplayName());
			pstmt_insert.setString(2, identity.getPassword());
			pstmt_insert.setString(3, identity.getEmail());
			
			Date now = new Date();
			pstmt_insert.setDate(4,new java.sql.Date(now.getTime())); // default = now
			
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
			LOGGER.debug("=> writeIdentity: leaving the method with no error" );
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.debug("=> writeIdentity EXCEPTION");
			System.exit(-1);
		}
	}
	
	/**
	 * Modification of identity (update)
	 * @param identity identity (object)
	 * @return success of operation
	 * @throws SQLException
	 */
	public void modifyIdentity(Identity identity) throws SQLException {
		
		LOGGER.debug("=> modifyIdentity : tracing the input : {}", identity);
		try {
			Connection con = getConnection();
			//Boolean succ = false;
			
			String updateStatement = "UPDATE IDENTITIES SET IDENTITY_DISPLAYNAME = ?, IDENTITY_PASSWORD = ?, IDENTITY_EMAIL = ?, IDENTITY_BIRTHDATE = ? WHERE IDENTITY_UID = ?";
			PreparedStatement pstmt_update = con.prepareStatement(updateStatement);
			
			pstmt_update.setString(1, identity.getDisplayName());
			pstmt_update.setString(2, identity.getPassword());
			pstmt_update.setString(3, identity.getEmail());
			pstmt_update.setDate(4, identity.getDOB());
			pstmt_update.setString(5, identity.getUid());
			
			pstmt_update.executeUpdate();
			// was modification successful?
			/*Integer rowsTouched = pstmt_update.executeUpdate();
			if(rowsTouched >0)
			    succ = true;
			return succ;*/
			LOGGER.debug("=> modifyIdentity: leaving the method with no error" );
		}
		catch(Exception e) {
			e.printStackTrace();
			LOGGER.debug("=> modifyIdentity EXCEPTION");
			System.exit(-1);
		}
	}
	
	/**
	 * Deletion of identity
	 * @param id identity (object)
	 * @return success of operation
	 * @throws SQLException
	 */
	public void eraseIdentity(String id) throws SQLException {
		
		LOGGER.debug("=> deleteIdentity with uid: tracing the input : {}", id);
		try {
			Connection con = getConnection();
			//Boolean succ = false;
	
			String eraseStatement = "delete from IDENTITIES where IDENTITY_UID = ?";
			PreparedStatement psmt_erase = con.prepareStatement(eraseStatement);
			psmt_erase.setString(1, id);
			
			psmt_erase.executeUpdate();
			// was deletion successful?
			/*Integer rowsTouched = psmt_erase.executeUpdate();
			if(rowsTouched >0)
			    succ = true;
			return succ;*/
			LOGGER.debug("=> deleteIdentity: leaving the method with no error" );
		}
		catch(Exception e) {
			e.printStackTrace();
			LOGGER.debug("=> deleteIdentity EXCEPTION");
			System.exit(-1);
		}
	}
	
	/**
	 * Listing of all DBS identities and their information
	 * @return list of identities (from DBS) and their info
	 * @throws SQLException
	 */
	public List<Identity>  readAllIdentities() throws SQLException {
		
		LOGGER.debug("=> readAll");
		List<Identity> identities = new ArrayList<Identity>();
		
		try
		{
			PreparedStatement pstmt_select = this.getConnection().prepareStatement("select * from IDENTITIES");
			ResultSet rs = pstmt_select.executeQuery();
			while (rs.next()){
				String displayName = rs.getString("IDENTITY_DISPLAYNAME");
				String uid = String.valueOf(rs.getString("IDENTITY_UID"));
				String email = rs.getString("IDENTITY_EMAIL");
				String password = rs.getString("IDENTITY_PASSWORD");
				java.sql.Date dob = rs.getDate("IDENTITY_BIRTHDATE");
				Identity identity = new Identity(uid, displayName, password, email, dob);
				identities.add(identity);
			}
			return LOGGER.traceExit("<= readAll : {}", identities);
		}
		catch(Exception e) {
			e.printStackTrace();
			LOGGER.debug("=> readAll EXCEPTION");
			System.exit(-1);
		}
		return identities;
	}
	
}
