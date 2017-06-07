package fr.epita.iam.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class that load configuration from file
 */
public class Configuration {
	
	//singleton
	private static Configuration inst;
	private Properties props = new Properties();
	private static final Logger LOGGER = LogManager.getLogger(Configuration.class);
	
	
	/**
	 * Default constructor
	 * @param addr search address criteria
	 */
	public Configuration() throws FileNotFoundException, IOException {
		
		
		String filePath = System.getProperty("fr.epita.iam.confFilePath");
		
		props.load(new FileInputStream(new File(filePath))); // file not found? >> exception
		LOGGER.debug("config file props: {}",props);
	}
	
	/**
	 * Singleton constructor
	 * @return inst instance of this class
	 */
	public static Configuration getInstance() throws FileNotFoundException, IOException{
		
		if (inst == null){
			inst = new Configuration();
		}
		return inst;
	}
	
	/**
	 * Get user from file
	 * @return user string
	 */
	public String getUser() {
		return props.getProperty("jdbc.connection.user");
	}
	
	/**
	 * Get password from file
	 * @return password string
	 */
	public String getPwd() {
		return props.getProperty("jdbc.connection.pwd");
	}
	
	/**
	 * Get connection settings from file
	 * @return connection info string
	 */
	public String getCon() {
		return props.getProperty("jdbc.connection.string");
	}

}
