package se.consys.Utilities;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
	private static SessionFactory sessionFactory;
	
	private HibernateUtility() {}
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration cfg = new Configuration().configure();
				sessionFactory = cfg.buildSessionFactory();
			} catch(HibernateException he) {
				System.out.println("Error creating sessionfactory: " + he.getMessage());
				throw new ExceptionInInitializerError(he);
			}
		}
		return sessionFactory;
	}
	
	
}
