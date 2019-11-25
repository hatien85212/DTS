package com.dts.adminportal.model;

public class UserInfo {
	protected String firstName;
	protected String lastName;
	protected String company;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	
	public static final String TITLE ="//*[@id='user-panel']/div/div[1]";
	public static final String USER_STATUS = "//*[@id='user-status']";
	public static final String EDIT = "//*[@id='edit-account']";
	public static final String SUSPEND = "//*[@id='suspend-account']";
	public static final String SUSPEND_CONFIRM= "//*[@class='btn btn-danger']";
}
