package tw.damncan.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import tw.damncan.dao.EventDAO;
import tw.damncan.dao.UserDAO;
import tw.damncan.model.Event;
import tw.damncan.model.User;

import org.springframework.context.ApplicationContext;

//@Service
//@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
public class EventService {
	ApplicationContext context;
	
	public EventService(ApplicationContext context){
		this.context = context;
	}
	
    public Event addEvent(Date startTime, Date endTime, String title, int user_ID) {
    	try{
    		User user = new User();
    		user.setId(user_ID);
    		Event event = new Event(0, startTime, endTime, title, user);
    		EventDAO eventDAO = (EventDAO) context.getBean("eventDAO");
    		Event resultEvent = (Event) eventDAO.insert(event);
    		
    		long diff = endTime.getTime() - startTime.getTime();
    		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
    		double hours = (double)minutes/60;
    		
    		// update leaveAmount
    		UserDAO userDAO = (UserDAO) context.getBean("userDAO");
    		User updatedUser = (User) userDAO.findByColumn(User.class, "id", user_ID);
    		updatedUser.setLeaveAmount(updatedUser.getLeaveAmount() - hours);
    		updatedUser = (User) userDAO.update(updatedUser);
    		
    		resultEvent.getUser().setLeaveAmount(updatedUser.getLeaveAmount());
		
    		return resultEvent;
    	}catch(Exception e){
    		return null;
    	}
    }
	
    public Event deleteEvent(int id, int user_ID) {
    	try{
    		Event event = getEvent(id);
    		EventDAO eventDAO = (EventDAO) context.getBean("eventDAO");
			Event resultEvent = (Event) eventDAO.delete(event);
			
			long diff = resultEvent.getEndTime().getTime() - resultEvent.getStartTime().getTime();
    		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
    		double hours = (double)minutes/60;
			
			// update leaveAmount
    		UserDAO userDAO = (UserDAO) context.getBean("userDAO");
    		User updatedUser = (User) userDAO.findByColumn(User.class, "id", user_ID);
    		updatedUser.setLeaveAmount(updatedUser.getLeaveAmount() + hours);
    		updatedUser = (User) userDAO.update(updatedUser);
    		
    		resultEvent.getUser().setLeaveAmount(updatedUser.getLeaveAmount());
			
			return resultEvent;
    	}catch(Exception e){
    		return null;
    	}
    }
	
    public Event updateEvent(int id, Date startTime, Date endTime, String title, int user_ID) {
    	try{
    		// get original event time diff.
    		EventDAO eventDAO = (EventDAO) context.getBean("eventDAO");
    		Event originalEvent = (Event) eventDAO.findByColumn(Event.class, "id", id);
    		long oDiff = originalEvent.getEndTime().getTime() - originalEvent.getStartTime().getTime();
    		long oMinutes = TimeUnit.MILLISECONDS.toMinutes(oDiff);
    		double oHours = (double)oMinutes/60;

    		// update event
    		User user = new User();
    		user.setId(user_ID);
			Event event = new Event(id, startTime, endTime, title, user);
			Event resultEvent = (Event) eventDAO.update(event);
			
			// get time diff
			long diff = endTime.getTime() - startTime.getTime();
    		long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
    		double hours = (double)minutes/60;
    		
    		// update leaveAmount
    		UserDAO userDAO = (UserDAO) context.getBean("userDAO");
    		User updatedUser = (User) userDAO.findByColumn(User.class, "id", user_ID);
    		updatedUser.setLeaveAmount(updatedUser.getLeaveAmount() + oHours - hours);
    		updatedUser = (User) userDAO.update(updatedUser);
    		
    		resultEvent.getUser().setLeaveAmount(updatedUser.getLeaveAmount());
			
			return resultEvent;
    	}catch(Exception e){
    		return null;
    	}
    }
	
    public List<?> getAllEvents(int depID){
    	try{
			EventDAO eventDAO = (EventDAO) context.getBean("eventDAO");
			List<?> eventList = (List<?>) eventDAO.getAllEvents(depID, Event.class);
			
			return eventList;
    	}catch(Exception e){
    		return null;
    	}
    }
	
    public List<?> getEventsBetween(Date startTime, Date endTime, Integer userID) {
    	try{
    		EventDAO eventDAO = (EventDAO) context.getBean("eventDAO");
    		List<?> eventList = (List<?>) eventDAO.getEventsBetween(Event.class, startTime, endTime, userID);
    		
    		return eventList;
    	}catch(Exception e){
    		return null;
    	}
    }
	
	public Event getEvent(int id) {
		try{
			EventDAO eventDAO = (EventDAO) context.getBean("eventDAO");
			Event event = (Event) eventDAO.findByColumn(Event.class, "id", id);
			
			return event;
		}catch(Exception e){
    		return null;
    	}
	}
}