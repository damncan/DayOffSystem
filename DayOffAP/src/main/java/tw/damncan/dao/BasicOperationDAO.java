package tw.damncan.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class BasicOperationDAO {
	// before using DAO, you show new DAO by using context.getBean() to avoid writing "static" statement here in sessionFactory and every function
	public SessionFactory sessionFactory;
	
	public BasicOperationDAO(){}
	
	public BasicOperationDAO(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	public <T> Object insert(T object){
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		session.save(object);
		tx.commit();
		
		session.close();
		
		return object;
	}
	
	public <T> Object delete(T object){
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		session.delete(object);
		tx.commit();
		
		session.close();
		
		return object;
	}
	
	public <T> Object update(T object){
		Session session = sessionFactory.openSession();
		
		Transaction tx = session.beginTransaction();
		session.update(object);
		tx.commit();
		
		session.close();
		
		return object;
	}
	
	public Object findById(Class<?> objclass, Integer id){
		Session session = sessionFactory.openSession();
		
		Object object = (Object) session.get(objclass, id);
		
		session.close();
		
		return object;
	}
	
	public <T> Object findByColumn(Class<?> objclass, String column, T value){
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(objclass);
		Object object = (Object) criteria.add(Restrictions.eq(column, value)).uniqueResult();
		
		session.close();
		
		return object;
	}
}