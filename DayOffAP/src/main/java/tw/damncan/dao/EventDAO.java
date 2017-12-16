package tw.damncan.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.core.JsonProcessingException;

public class EventDAO extends BasicOperationDAO {
	public EventDAO(){
		super();
	} 
	
	public EventDAO(SessionFactory sessionFactory){
		super();
	}
	
	public Object getAllEvents(int depID, Class<?> objclass) throws JsonProcessingException{
		Session session = sessionFactory.openSession();

		Criteria criteria = session.createCriteria(objclass);
		if(depID > 0){
			criteria.createAlias("user", "u").add(Restrictions.eq("u.depID", depID));
		}
		List<?> results = criteria.list();
		
		session.close();
		
		return results;
	}
	
	public Object getEventsBetween(Class<?> objclass, Date sTime, Date eTime){
		return getEventsBetween(objclass, sTime, eTime, null);
	}
	public Object getEventsBetween(Class<?> objclass, Date sTime, Date eTime, Integer userID){
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(objclass);
		criteria.add(Restrictions.ge("startTime", sTime));
		if(sTime != eTime){
			criteria.add(Restrictions.lt("endTime", eTime));
		}
		if(userID != null){
			criteria.add(Restrictions.eq("user.id", userID));
		}
		List<?> results = criteria.list();
		
		session.close();
		
		return results;
	}
}