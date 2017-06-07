package fr.epita.iam.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.epita.iam.datamodel.Identity;

/**
 * Class authenticating the user using PasswordEndecryptor
 * */
@Repository
public class AuthenticateUser {
	
	@Autowired
	SessionFactory sf;
	
	//singleton!
	private static AuthenticateUser inst = null;
	
	private static final Logger LOGGER = LogManager.getLogger(AuthenticateUser.class);
	
	protected AuthenticateUser(){
		
	}
	
	/**
	 * Singleton constructor
	 * @return inst instance of this class
	 */
	public static AuthenticateUser getInst()
	{
		if(inst == null)
			inst = new AuthenticateUser();
		
		return inst;
	}
	
	/**
	 * Authenticate the user in dbs
	 * @param login user login
	 * @param pwd user password
	 * */
	public Identity authenticate(String login, String pwd){
			
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		Session session = sf.openSession();
		List<Identity> ids = new ArrayList<Identity>();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM Identity AS identity WHERE identity.displayName = :login");
		//transaction - forces changes in cache to be updated to dbs
		query.setParameter("login", login);
		ids  = query.list();
		t.commit();
		session.close();
		
		if(ids.isEmpty())
			return null;
		
		//there should be only one identity to a single displayname!
		for(Identity id : ids)
		{
			PasswordEndecryptor.getInst();
			// hashed password decryption and authentication
			if(PasswordEndecryptor.checkPwd(pwd, id.getPassword()))
			{
				LOGGER.debug("Authentication should be succesfull!");
				return id;
			}
				
		}
		LOGGER.debug("Authentication should NOT be succesfull!");
		return null;
	}
	

}
