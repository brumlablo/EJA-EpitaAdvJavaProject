package fr.epita.iam.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
	
	private Properties props = new Properties();
	
	public Configuration() throws FileNotFoundException, IOException {
		
		
		String filePath = System.getProperty("fr.epita.iam.confFilePath");
		props.load(new FileInputStream(new File(filePath)));
		System.out.print(props);
		
		}
	}

	public String getUser() {
		return props.getProperty("jdbc.user.string");
	}
	
	public String getPwd() {
		return props.getProperty("jdbc.pwd.string");
	}
	
	public String getCon() {
		return props.getProperty("jdbc.connection.string");
	}

}
