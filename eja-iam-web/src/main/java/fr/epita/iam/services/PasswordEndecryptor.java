package fr.epita.iam.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epita.iam.servlets.AuthenticationServlet;
import org.mindrot.jbcrypt.*;

/*
 * 
 * Encrypts and decrypts user password thanks to bcrypt.
 * 
 * */
public class PasswordEndecryptor {
	
	//value <10,31>
	private static int workload = 10;
			
	private static final Logger LOGGER = LogManager.getLogger(AuthenticationServlet.class);
	
	public PasswordEndecryptor()
	{
		
	}
	
	public static String hashPwd(String pwdPlain)
	{
		String pwdHashed = BCrypt.hashpw(pwdPlain, BCrypt.gensalt(workload));
		LOGGER.info("password hashed to: {}", pwdHashed);
		return pwdHashed;
	}
	
	public static boolean checkPwd(String loginInput, String dbsHash)
	{
		
		//is successfull?
		boolean isVerified = false;

		if(null == dbsHash || !dbsHash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		isVerified = BCrypt.checkpw(loginInput, dbsHash);

		return(isVerified);
	}
	
	public static void main(String[] args)
	{
		String test_passwd = "abcdefghijklmnopqrstuvwxyz";
		String test_hash = "$2a$06$.rCVZVOThsIa97pEDOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC";

		LOGGER.info("Testing BCrypt Password hashing and verification");
		LOGGER.info("Test password: {}", test_passwd);
		LOGGER.info("Test stored hash: {}", test_hash);
		LOGGER.info("Hashing test password...");

		String computed_hash = hashPwd(test_passwd);
		LOGGER.info("Test computed hash: {}", computed_hash);
		LOGGER.info("Verifying that hash and stored hash both match for the test password...");

		String compare_test = checkPwd(test_passwd, test_hash)
			? "Passwords Match" : "Passwords do not match";
		String compare_computed = checkPwd(test_passwd, computed_hash)
			? "Passwords Match" : "Passwords do not match";

		LOGGER.info("Verify against stored hash:   {}", compare_test);
		LOGGER.info("Verify against computed hash: {}", compare_computed);

	}
}
