package fr.epita.iam.tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import fr.epita.iam.services.PasswordEndecryptor;

/**
 * @author bb
 *
 */
public class TestJBcrypt {
	
	private static final Logger LOGGER = LogManager.getLogger(TestJBcrypt.class);

	@Test
	public void test() {
		
		String test_passwd = "abcdefghijklmnopqrstuvwxyz";
		String test_hash = "$2a$06$.rCVZVOThsIa97pEDOxvGuRRgzG64bvtJ0938xuqzv18d3ZpQhstC";

		LOGGER.info("Testing JBCrypt Password hashing and verification");
		LOGGER.info("Test password: {}", test_passwd);
		LOGGER.info("Test stored hash: {}", test_hash);
		LOGGER.info("Hashing test password...");

		PasswordEndecryptor.getInst();
		String computed_hash = PasswordEndecryptor.hashPwd(test_passwd);
		LOGGER.info("Test computed hash: {}", computed_hash);
		LOGGER.info("Verifying that hash and stored hash both match for the test password...");

		PasswordEndecryptor.getInst();
		//is real pattern matching?
		Assert.assertTrue(PasswordEndecryptor.checkPwd(test_passwd, test_hash));
		
		PasswordEndecryptor.getInst();
		//is computed hash matching?
		Assert.assertTrue(PasswordEndecryptor.checkPwd(test_passwd, computed_hash));
	}

}
