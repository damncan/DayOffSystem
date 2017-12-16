package tw.damncan.model;

import java.util.Date;

public class Event {
	private int id;
	private String title;
	private Date startTime;
	private Date endTime;
	private LoginUser user;

	public Event(){}
	public Event(int id, Date startTime, Date endTime, String title, LoginUser user){
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.title = title;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public LoginUser getUser() {
		return user;
	}
	public void setUser(LoginUser user) {
		this.user = user;
	}
}