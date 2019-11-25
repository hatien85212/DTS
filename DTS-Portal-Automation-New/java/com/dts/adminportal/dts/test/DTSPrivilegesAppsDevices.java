package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSPrivilegesAppsDevices extends BasePage {
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the DTS user can view the app/device info even when both �Add and manage apps and devices� and �Publish and suspend apps and devices� privileges are disabled
	 */
	@Test
	public void TCPAD_01() {
		appDeviceControl.addLog("ID : TCPAD_01 : Verify that the DTS user can view the app/device info even when both �Add and manage apps and devices� and �Publish and suspend apps and devices� privileges are disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage apps and devices� and �Publish and suspend apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			VP: Verify that the app/device list page is displayed
			10. Select an app/device from audio routes list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage apps and devices� and �Publish and suspend apps and devices� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * VP: Verify that the app/device list page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceList.getListXpath()), true);
		// 10. Select an app/device from audio routes list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectAnAppDevice();
		/*
		 * Verify that the app/device info page is displayed
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
	}
	
	/*
	 * Verify that the DTS user cannot add new app/device when the �Add and manage apps and devices� is disabled
	 */
	@Test
	public void TCPAD_02() {
		appDeviceControl.addLog("ID : TCPAD_02 : Verify that the DTS user cannot add new app/device when the �Add and manage apps and devices� is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the  �Add and manage apps and devices� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * Verify that the �Add App or Device� link is not displayed
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
	}
	
	/*
	 * Verify that the DTS user cannot edit an app/device when the �Add and manage apps and devices� is disabled
	 */
	@Test
	public void TCPAD_03() {
		appDeviceControl.addLog("ID : TCPAD_03 : Verify that the DTS user cannot edit an app/device when the �Add and manage apps and devices� is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the  �Add and manage apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			10. Select an app/device from app/device list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the �Add and manage apps and devices� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectAnAppDevice();
		/*
		 * Verify that the app/device info page is displayed without the �Edit Version� link
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.EDIT));
	}
	
	/*
	 * Verify that the DTS user cannot publish or delete an app/device when the �Publish and suspend apps and devices� is disabled
	 */
	@Test
	public void TCPAD_04() {
		appDeviceControl.addLog("ID : TCPAD_04 : Verify that the DTS user cannot publish or delete an app/device when the �Publish and suspend apps and devices� is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the �Publish and suspend apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			10. Select an app/device from app/device list
		 */
		/*
		 * Pre-condition: Create a new app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ************************************************************************
		 */
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the �Publish and suspend apps and devices� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * Verify that the app/device info page is displayed without the �Publish� and �Delete� links
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.PUBLISH));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.DELETE));
	}
	
	/*
	 * Verify that the DTS user cannot suspend for a published app/device when the �Publish and suspend apps and devices� is disabled
	 */
	@Test
	public void TCPAD_05() {
		appDeviceControl.addLog("ID : TCPAD_05 : Verify that the DTS user cannot suspend for a published app/device when the �Publish and suspend apps and devices� is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Disable the �Publish and suspend apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			10. Select a published app/device from app/device list
		 */
		/*
		 * Pre-condition: Create a new published app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		// Publish device
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.waitForElementClickable(PageHome.LINK_APP_DEVICES);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the �Publish and suspend apps and devices� privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * Verify that the app/device info page is displayed without the �Suspend� link
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Published")), true);
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.SUSPEND));
	}
	
	/*
	 * Verify that the DTS user can add new app/device when the �Add and manage apps and devices� is Enabled
	 */
	@Test
	public void TCPAD_06() {
		appDeviceControl.addLog("ID : TCPAD_06 : Verify that the DTS user can add new app/device when the �Add and manage apps and devices� is Enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			VP: Verify that the �Add App or Device� link is displayed
			10. Click �Add App or Device� link
			11. Fill valid value into all required fields
			12. Click �Save� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Add and manage apps and devices� privileges
		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges[4]);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * VP: Verify that the �Add App or Device� link is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 10. Click �Add App or Device� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Click �Save� link
		Hashtable<String,String> data = TestData.appDeviceDraft();
		ArrayList<String> deviceInfo = appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * Verify that the portal redirects to app/device info page with new information
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(data, deviceInfo));
	}
	
	/*
	 * Verify that the DTS user can edit an app/device when the �Add and manage apps and devices� is Enabled
	 */
	@Test
	public void TCPAD_07() {
		appDeviceControl.addLog("ID : TCPAD_07 : Verify that the DTS user can edit an app/device when the �Add and manage apps and devices� is Enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			10. Select an app/device from app/device list
			VP: Verify that the app/device info page is displayed with �Edit Version� link
			11. Click �Edit Version� link
			12. Change some info of app/device
			13. Click �Save� link
		 */
		/*
		 * Pre-condition: Create a new app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the �Add and manage apps and devices� privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * VP: Verify that the app/device info page is displayed with �Edit Version� link
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.EDIT));
		// 11. Click �Edit Version� link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 12. Change some info of app/device
		String newName = "Edit" + RandomStringUtils.randomNumeric(5);
		appDeviceControl.editData(DeviceEdit.NAME, newName);
		// 13. Click �Save� link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * Verify that the portal redirects to app/device info page with new information
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), newName);
	}
	
	/*
	 * Verify that the DTS user can publish an app/device when the �Publish and suspend apps and devices� is enabled
	 */
	@Test
	public void TCPAD_08() {
		appDeviceControl.addLog("ID : TCPAD_08 : Verify that the DTS user can publish an app/device when the �Publish and suspend apps and devices� is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			10. Click �Add App or Device� link
			11. Fill valid value into all required fields
			12. Upload default and custom audio routes successfully
			13. Turn off the global promotion
			14. Click �Save� link
			VP: Verify that the portal redirects to App/Device main page and new app/device is added into app/device list successfully
			15. Select above app/device from app/device list
			16. Click �Publish� link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the �Publish and suspend apps and devices� privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Click �Add App or Device� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Upload default and custom audio routes successfully
		// 13. Turn off the global promotion
		// 14. Click �Save� link
		Hashtable<String,String> data = TestData.appDevicePublish();
		ArrayList<String> deviceInfo = appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * VP: Verify that the portal redirects to App/Device main page
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(data, deviceInfo));
		/*
		 * VP: Verify that New app/device is added into app/device list successfully
		 */
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		Assert.assertTrue(appDeviceControl.checkAnAppDeviceExistByName( data.get("name")));
		// 15. Select above app/device from app/device list
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 16. Click �Publish� link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * Verify that the �Publication Status� of app/device is changed to �PUBLISHED�, the �Publish� link is replaced by �Suspend� link
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Published");
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.PUBLISH));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.SUSPEND));
		// Delete app/device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can suspend an app/device when the �Publish and suspend apps and devices� is enabled
	 */
	@Test
	public void TCPAD_09() {
		appDeviceControl.addLog("ID : TCPAD_09 : Verify that the DTS user can suspend an app/device when the �Publish and suspend apps and devices� is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Add and manage apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			10. Click �Add App or Device� link
			11. Fill valid value into all required fields
			12. Upload default and custom audio routes successfully
			13. Turn off the global promotion
			14. Click �Save� link
			VP: Verify that the portal redirects to App/Device main page and new app/device is added into app/device list successfully
			15. Select above app/device from app/device list
			16. Click �Publish� link
			VP: Verify that the app/device is published successfully
			17. Click �Suspend� link
			18. Click �Suspend� button on suspend confirmation dialog
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the �Publish and suspend apps and devices� privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Click �Add App or Device� link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Upload default and custom audio routes successfully
		// 13. Turn off the global promotion
		// 14. Click �Save� link
		Hashtable<String,String> data = TestData.appDevicePublish();
		ArrayList<String> deviceInfo = appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * VP: Verify that the portal redirects to App/Device main page
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(data, deviceInfo));
		/*
		 * VP: Verify that New app/device is added into app/device list successfully
		 */
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		Assert.assertTrue(appDeviceControl.checkAnAppDeviceExistByName( data.get("name")));
		// 15. Select above app/device from app/device list
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 16. Click �Publish� link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that the app/device is published successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),	"Published");
		// 17. Click �Suspend� link
		appDeviceControl.click(DeviceInfo.SUSPEND);
		// 18. Click �Suspend� button on suspend confirmation dialog
		appDeviceControl.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that app/device is suspended successfully
		 */
		appDeviceControl.selectADeviceByName(data.get("name"));
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Suspended");
		// Delete app/device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can delete an app/device when the �Publish and suspend apps and devices� is enabled
	 */
	@Test
	public void TCPAD_10() {
		appDeviceControl.addLog("ID : TCPAD_10 : Verify that the DTS user can delete an app/device when the �Publish and suspend apps and devices� is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click �Edit� link
			6. Enable the  �Publish and suspend apps and devices� privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the �Apps & Devices� page is displayed
			9. Navigate to �Apps & Devices� page
			10. Select an app/device from app/device list
			11. Click �Delete� link
			12. Click on �Delete� button on delete confirmation dialog
		 */
		/*
		 * Pre-condition: Create a new app/device
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Navigate to "Apps & Devices" page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// Click "Add app or device" link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click �Edit� link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the  �Publish and suspend apps and devices� privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Publish_suspend_apps_devices);
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the �Apps & Devices� page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to �Apps & Devices� page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 11. Click �Delete� link
		// 12. Click on �Delete� button on delete confirmation dialog
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		/*
		 * Verify that the portal redirects to Apps & Devices main page, and the deleted app/device is not displayed in app/device list
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceList.getListXpath()), true);
		Assert.assertFalse(appDeviceControl.checkAnAppDeviceExistByName( data.get("name")));
	}
	
}
