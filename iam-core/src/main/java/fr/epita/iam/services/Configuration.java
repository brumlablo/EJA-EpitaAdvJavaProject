package fr.epita.iam.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configuration {
	
	/*@Inject
	@Named("dataSourceBean")
	private DataSource ds;*/
	
	//singleton
	private static Configuration inst;
	private Properties props = new Properties();
	private static final Logger LOGGER = LogManager.getLogger(Configuration.class);
	
	public Configuration() throws FileNotFoundException, IOException {
		
		
		String filePath = System.getProperty("fr.epita.iam.confFilePath");
		
		props.load(new FileInputStream(new File(filePath))); // file not found? >> exception
		//LOGGER.info("config file props: {}",props);
	}
	
	public static Configuration getInstance() throws FileNotFoundException, IOException{
		
		if (inst == null){
			inst = new Configuration();
		}
		return inst;
	}
	
	public String getUser() {
		return props.getProperty("jdbc.connection.user");
	}
	
	public String getPwd() {
		return props.getProperty("jdbc.connection.pwd");
	}
	
	public String getCon() {
		return props.getProperty("jdbc.connection.string");
	}

}
