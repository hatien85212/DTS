package dts.com.adminportal.model;

public class PartnerUserInfo {
	protected String firstName;
	protected String lastName;
	protected String title;
	protected String phone;
	protected String email;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public static final String USER_STATUS = "//*[@id='user-phone']";
	public static final String EDIT = "//*[@id='edit-account']";
	public static final String SUSPEND = "//*[@id='suspend-account']";
	public static final String TITLE = "//*[@id='user-title']";
	
}
