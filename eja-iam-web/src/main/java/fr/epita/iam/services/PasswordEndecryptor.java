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
	private static int workload = 16;
	
	public static PasswordEndecryptor inst = null;
			
	private static final Logger LOGGER = LogManager.getLogger(PasswordEndecryptor.class);
	
	public PasswordEndecryptor()
	{
		
	}
	
	public static PasswordEndecryptor getInst()
	{
		if(inst == null)
			inst = new PasswordEndecryptor();
		
		return inst;
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
}
