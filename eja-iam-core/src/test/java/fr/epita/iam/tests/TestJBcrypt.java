package fr.epita.iam.tests;

import static org.junit.Assert.*;

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

		LOGGER.info("Testing BCrypt Password hashing and verification");
		LOGGER.info("Test password: {}", test_passwd);
		LOGGER.info("Test stored hash: {}", test_hash);
		LOGGER.info("Hashing test password...");

		String computed_hash = PasswordEndecryptor.getInst().hashPwd(test_passwd);
		LOGGER.info("Test computed hash: {}", computed_hash);
		LOGGER.info("Verifying that hash and stored hash both match for the test password...");

		//is real pattern matching?
		Assert.assertTrue(PasswordEndecryptor.getInst().checkPwd(test_passwd, test_hash));
		
		//is computed hash matching?
		Assert.assertTrue(PasswordEndecryptor.getInst().checkPwd(test_passwd, computed_hash));
	}

}
