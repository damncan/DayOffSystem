package tw.damncan.model;

public class LoginUser {
	private int id;
//	private String account;
//	private String pswd;
	private String name;
	private String email;
	private double leaveAmount;
	private int depID;
	private String depName;
	private int depNumber;
	
	public LoginUser(){}

//	public LoginUser(int id, String account, String pswd, String name, String email, int depID, String depName, int depNumber){
//	public LoginUser(int id, String name, String email, int depID, String depName, int depNumber){
//		this.id = id;
////		this.account = account;
////		this.pswd = pswd;
//		this.name = name;
//		this.email = email;
//		this.depID = depID;
//		this.depName = depName;
//		this.depNumber = depNumber;
//	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
//	public String getAccount() {
//		return account;
//	}
//	public void setAccount(String account) {
//		this.account = account;
//	}
//	public String getPswd() {
//		return pswd;
//	}
//	public void setPswd(String pswd) {
//		this.pswd = pswd;
//	}
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
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	public Integer getDepNumber() {
		return depNumber;
	}
	public void setDepNumber(Integer depNumber) {
		this.depNumber = depNumber;
	}
}
