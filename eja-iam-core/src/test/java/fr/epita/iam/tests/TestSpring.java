/**
 * 
 */
package fr.epita.iam.tests;

import java.sql.SQLException;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.services.DAO;

/**
 * @author bb
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})

public class TestSpring {
	
	@Inject
	DAO<Identity> dao;
	
	@Inject
	SessionFactory sessionFactory;
		
	@After
	public void cleanDBS(){
		Session session = sessionFactory.openSession();
		Transaction t = session.beginTransaction();
		session.createQuery("delete from Identity").executeUpdate();
		t.commit();
		session.close();
	}
	
	@Test
	public void testSpringContext() throws SQLException{
		dao.write(new Identity(null, "bbbb","1234","barbora.bbbb@gmail.com",null,"admin"));
	}

}