package fr.epita.iam.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.mindrot.jbcrypt.*;

/**
 * 
 * Encrypts(hashes) and decrypts user password using JBCrypt
 * 
 * */
public class PasswordEndecryptor {
	
	//value <10,31>
	private static int workload = 16;
	
	public static PasswordEndecryptor inst = null;
			
	private static final Logger LOGGER = LogManager.getLogger(PasswordEndecryptor.class);
	
	/**
	 * Default constructor
	 * */
	public PasswordEndecryptor()
	{
		
	}
	
	/**
	 * Singleton, return static single instance of PasswordDecryptor
	 * @return inst instance of this class
	 */
	public static PasswordEndecryptor getInst()
	{
		if(inst == null)
			inst = new PasswordEndecryptor();
		
		return inst;
	}
	
	/**
	 * Hash password
	 * @param pwdPlain password in plain text
	 * @return pwdHashed hashed password
	 */
	public static String hashPwd(String pwdPlain)
	{
		String pwdHashed = BCrypt.hashpw(pwdPlain, BCrypt.gensalt(workload));
		LOGGER.info("password hashed to: {}", pwdHashed);
		return pwdHashed;
	}
	
	/**
	 * Compare the hashed password
	 * @param loginInput password in plain text
	 * @param dbsHash hashed password stored in dbs
	 * @return isVerified true or false based on success of authentication
	 */
	public static boolean checkPwd(String loginInput, String dbsHash)
	{
		
		//is successfull?
		boolean isVerified = false;

		if(null == dbsHash || !dbsHash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

		isVerified = BCrypt.checkpw(loginInput, dbsHash);

		return(isVerified);
	}
}
