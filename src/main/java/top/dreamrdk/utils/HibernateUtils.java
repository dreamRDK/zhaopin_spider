package top.dreamrdk.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private static SessionFactory sessionFactory;
	static {
		Configuration config = new Configuration().configure();
		sessionFactory = config.buildSessionFactory();
	}
	
	public static Session openSession() {
		return sessionFactory.openSession();
	}
	
	public static Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
}
