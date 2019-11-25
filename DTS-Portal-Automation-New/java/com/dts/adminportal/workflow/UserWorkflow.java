package com.dts.adminportal.workflow;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.controller.LoginController;
import com.dts.adminportal.controller.UsersController;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.UserMgmt;

public class UserWorkflow {

	public UsersController userControl;
	public LoginController loginControl;
	
	public UserWorkflow(UsersController userControl, LoginController loginControl){
		this.userControl = userControl;
		this.loginControl = loginControl;
	}
	
	/**
	 * Do the following step:
	 * 1 - Login as super user
	 * 2 - Go to user page and enable all privileges of input user
	 * 3 - Save
	 * @param userMail
	 */
	public void loginAndEnableAllPrivilege(String userMail){
		loginControl.login(BasePage.SUPER_USER_NAME, BasePage.SUPER_USER_PASSWORD);
		enableAllPrivilegeOfUser(userMail);
		userControl.click(AddUser.SAVE);
	}
	
	/**
	 * Step:
	 * Login to dts with super user account
	 * Go to user page and Enable all privileges of input user
	 * Disable input privilege
	 * Save when finish
	 * @param userMail 
	 * @param privilege get from class: Privileges
	 * @param isDTSPrivilege true is dts privilege table, false is other wise
	 * eg: 	true -> AddUser.DTS_PRIVILEGES_TABLE
	 * 		false-> AddUser.PRIVILEGES_TABLE 
	 * 		
	 */
	public void loginAndDisablePrivilege(String userMail, String privilege, boolean isDTSPrivilege){
		loginControl.login(BasePage.SUPER_USER_NAME, BasePage.SUPER_USER_PASSWORD);
		enableAllPrivilegeOfUser(userMail);
		if(isDTSPrivilege){
			userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, privilege);
		} else {
			userControl.disablePrivilege(AddUser.PRIVILEGES_TABLE, privilege);
		}
		userControl.click(AddUser.SAVE);
	}
	
	/**
	 * Do the following step:
	 * 1 - click on User page link
	 * 2 - Select input user
	 * 3 - click on edit user
	 * 4 - Enable all privilege of user 
	 * note: Not yet click on save button
	 * @param userMail
	 */
	public void enableAllPrivilegeOfUser(String userMail){
		// Navigate to User page
		userControl.click(PageHome.LINK_USERS);
		// Select a DTS User from table
		userControl.selectUserInfoByEmail(userMail);
		// Click Edit link
		userControl.click(UserMgmt.EDIT);
		// enable all privilege
		userControl.enableAllPrivileges();
	}
	
}
