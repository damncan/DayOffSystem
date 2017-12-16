package tw.damncan.model;

import java.util.Date;

public class Event {
	private int id;
	private Date startTime;
	private Date endTime;
	private String title;
	private User user;
	
	public Event() {}
	public Event(int id, Date startTime, Date endTime, String title, User user) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.title = title;
		this.user = user;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}