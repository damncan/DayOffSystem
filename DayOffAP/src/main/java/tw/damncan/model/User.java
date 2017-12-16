package tw.damncan.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Transient;

public class User {
	private int id;
	private String account;
	private String pswd;
	private String name;
	private String email;
	private double leaveAmount;
	private int depID;
	
	@JsonIgnore
	private Set<Event> events;
	@JsonIgnore
	public Set<Event> getEvents() {
		return events;
	}
	@JsonIgnore
	public void setEvents(Set<Event> events) {
		this.events = events;
	}
	
	public User() {}

//	public User(int id, String account, String pswd, String name, String email, int depID) {
//		super();
//		this.id = id;
//		this.account = account;
//		this.pswd = pswd;
//		this.name = name;
//		this.email = email;
//		this.depID = depID;
//	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	@Transient
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public double getLeaveAmount() {
		return leaveAmount;
	}
	public void setLeaveAmount(double leaveAmount) {
		this.leaveAmount = leaveAmount;
	}
	public int getDepID() {
		return depID;
	}
	public void setDepID(int depID) {
		this.depID = depID;
	}
}