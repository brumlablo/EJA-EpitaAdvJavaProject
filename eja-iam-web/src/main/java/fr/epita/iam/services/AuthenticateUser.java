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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.servlets.AuthenticationServlet;

public class AuthenticateUser {
	
	@Autowired
	SessionFactory sf;
	
	//singleton!
	private static AuthenticateUser inst = null;
	
	protected AuthenticateUser(){
		
	}
	
	public static AuthenticateUser getInst()
	{
		if(inst == null)
			inst = new AuthenticateUser();
		
		return inst;
	}
	
	public Identity authenticate(String login, String pwd){
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);	
		Session session = sf.openSession();
		List<Identity> ids = new ArrayList<Identity>();
		Transaction t = session.beginTransaction();
		Query query = session.createQuery("FROM Identity AS identity WHERE identity.displayName = :login"
				+ " and identity.password = :pwd");
		//transaction - forces changes in cache to be updated to dbs
		query.setParameter("login", login);
		query.setParameter("pwd", pwd);
		ids  = query.list();
		t.commit();
		session.close();
		
		if(!ids.isEmpty())
			return ids.get(0);
		
		return null;
	}
	

}
