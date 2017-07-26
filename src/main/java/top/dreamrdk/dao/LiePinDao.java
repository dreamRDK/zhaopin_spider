package top.dreamrdk.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import top.dreamrdk.domain.LiePinInfo;
import top.dreamrdk.utils.HibernateUtils;

public class LiePinDao {
	
	public void save(LiePinInfo liePinInfo) {
		Session session = HibernateUtils.openSession();
		Transaction tx = session.beginTransaction();
		session.save(liePinInfo);
		tx.commit();
		session.close();
	}
}
