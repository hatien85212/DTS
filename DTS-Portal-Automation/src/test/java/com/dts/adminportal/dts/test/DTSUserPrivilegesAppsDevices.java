package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.HomeController;
import com.dts.adminportal.util.CreatePage;
import com.dts.adminportal.util.DTSUtil;
import com.dts.adminportal.util.TestData;

import dts.com.adminportal.model.AddUser;
import dts.com.adminportal.model.DeviceEdit;
import dts.com.adminportal.model.DeviceInfo;
import dts.com.adminportal.model.DeviceList;
import dts.com.adminportal.model.Privileges;
import dts.com.adminportal.model.UserEdit;
import dts.com.adminportal.model.UserMgmt;
import dts.com.adminportal.model.Xpath;

public class DTSUserPrivilegesAppsDevices extends CreatePage {
	private HomeController home;
	
	@BeforeClass
	public void beforeTest() {
		home = new HomeController(driver, siteBase);
	}
	
	@BeforeMethod
	public void loginBeforeTest() {
		home.login(superUsername, superUserpassword);
	}
	
	/*
	 * Verify that the DTS user can view the app/device info even when both “Add and manage apps and devices” and “Publish and suspend apps and devices” privileges are disabled
	 */
	@Test
	public void TCPAD_01() {
		result.addLog("ID : TCPAD_01 : Verify that the DTS user can view the app/device info even when both “Add and manage apps and devices” and “Publish and suspend apps and devices” privileges are disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage apps and devices” and “Publish and suspend apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			VP: Verify that the app/device list page is displayed
			10. Select an app/device from audio routes list
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage apps and devices” and “Publish and suspend apps and devices” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * VP: Verify that the app/device list page is displayed
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		// 10. Select an app/device from audio routes list
		ArrayList<String> appDeviceInfo = home.selectAnAppDevice();
		/*
		 * Verify that the app/device info page is displayed
		 */
		ArrayList<String> check_info = home.getTextByXpath(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(DTSUtil.containsAll(appDeviceInfo, check_info));
	}
	
	/*
	 * Verify that the DTS user cannot add new app/device when the “Add and manage apps and devices” is disabled
	 */
	@Test
	public void TCPAD_02() {
		result.addLog("ID : TCPAD_02 : Verify that the DTS user cannot add new app/device when the “Add and manage apps and devices” is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the  “Add and manage apps and devices” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * Verify that the “Add App or Device” link is not displayed
		 */
		Assert.assertFalse(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
	}
	
	/*
	 * Verify that the DTS user cannot edit an app/device when the “Add and manage apps and devices” is disabled
	 */
	@Test
	public void TCPAD_03() {
		result.addLog("ID : TCPAD_03 : Verify that the DTS user cannot edit an app/device when the “Add and manage apps and devices” is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the  “Add and manage apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			10. Select an app/device from app/device list
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Add and manage apps and devices” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = home.selectAnAppDevice();
		/*
		 * Verify that the app/device info page is displayed without the “Edit Version” link
		 */
		ArrayList<String> check_info = home.getTextByXpath(DeviceInfo.getDeviceInfo());
		Assert.assertTrue(DTSUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertFalse(home.isElementPresent(DeviceInfo.EDIT));
	}
	
	/*
	 * Verify that the DTS user cannot publish or delete an app/device when the “Publish and suspend apps and devices” is disabled
	 */
	@Test
	public void TCPAD_04() {
		result.addLog("ID : TCPAD_04 : Verify that the DTS user cannot publish or delete an app/device when the “Publish and suspend apps and devices” is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the “Publish and suspend apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			10. Select an app/device from app/device list
		 */
		/*
		 * Pre-condition: Create a new app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click "Add app or device" link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ************************************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Publish and suspend apps and devices” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = home.selectADeviceByName(data.get("name"));
		/*
		 * Verify that the app/device info page is displayed without the “Publish” and “Delete” links
		 */
		ArrayList<String> check_info = home.getTextByXpath(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(DTSUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertFalse(home.isElementPresent(DeviceInfo.PUBLISH));
		Assert.assertFalse(home.isElementPresent(DeviceInfo.DELETE));
	}
	
	/*
	 * Verify that the DTS user cannot suspend for a published app/device when the “Publish and suspend apps and devices” is disabled
	 */
	@Test
	public void TCPAD_05() {
		result.addLog("ID : TCPAD_05 : Verify that the DTS user cannot suspend for a published app/device when the “Publish and suspend apps and devices” is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Disable the “Publish and suspend apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			10. Select a published app/device from app/device list
		 */
		/*
		 * Pre-condition: Create a new published app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click "Add app or device" link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		// Publish device
		home.click(DeviceInfo.PUBLISH);
		home.waitForElementClickable(Xpath.LINK_APP_DEVICES);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Disable the “Publish and suspend apps and devices” privileges
		home.enableAllPrivilege();
		home.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = home.selectADeviceByName(data.get("name"));
		/*
		 * Verify that the app/device info page is displayed without the “Suspend” link
		 */
		ArrayList<String> check_info = home.getTextByXpath(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(DTSUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertFalse(home.isElementPresent(DeviceInfo.SUSPEND));
	}
	
	/*
	 * Verify that the DTS user can add new app/device when the “Add and manage apps and devices” is Enabled
	 */
	@Test
	public void TCPAD_06() {
		result.addLog("ID : TCPAD_06 : Verify that the DTS user can add new app/device when the “Add and manage apps and devices” is Enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			VP: Verify that the “Add App or Device” link is displayed
			10. Click “Add App or Device” link
			11. Fill valid value into all required fields
			12. Click “Save” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Add and manage apps and devices” privileges
		home.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges[4]);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		/*
		 * VP: Verify that the “Add App or Device” link is displayed
		 */
		Assert.assertTrue(home.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 10. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Click “Save” link
		Hashtable<String,String> data = TestData.appDeviceDraft();
		ArrayList<String> deviceInfo = home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * Verify that the portal redirects to app/device info page with new information
		 */
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(DTSUtil.containsAll(data, deviceInfo));
	}
	
	/*
	 * Verify that the DTS user can edit an app/device when the “Add and manage apps and devices” is Enabled
	 */
	@Test
	public void TCPAD_07() {
		result.addLog("ID : TCPAD_07 : Verify that the DTS user can edit an app/device when the “Add and manage apps and devices” is Enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			10. Select an app/device from app/device list
			VP: Verify that the app/device info page is displayed with “Edit Version” link
			11. Click “Edit Version” link
			12. Change some info of app/device
			13. Click “Save” link
		 */
		/*
		 * Pre-condition: Create a new app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click "Add app or device" link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDevicePublish();
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Add and manage apps and devices” privileges
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Add_manage_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = home.selectADeviceByName(data.get("name"));
		/*
		 * VP: Verify that the app/device info page is displayed with “Edit Version” link
		 */
		ArrayList<String> check_info = home.getTextByXpath(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(DTSUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertTrue(home.isElementPresent(DeviceInfo.EDIT));
		// 11. Click “Edit Version” link
		home.click(DeviceInfo.EDIT);
		// 12. Change some info of app/device
		String newName = "Edit" + RandomStringUtils.randomNumeric(5);
		home.editData(DeviceEdit.NAME, newName);
		// 13. Click “Save” link
		home.click(DeviceEdit.SAVE);
		/*
		 * Verify that the portal redirects to app/device info page with new information
		 */
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.NAME), newName);
	}
	
	/*
	 * Verify that the DTS user can publish an app/device when the “Publish and suspend apps and devices” is enabled
	 */
	@Test
	public void TCPAD_08() {
		result.addLog("ID : TCPAD_08 : Verify that the DTS user can publish an app/device when the “Publish and suspend apps and devices” is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			10. Click “Add App or Device” link
			11. Fill valid value into all required fields
			12. Upload default and custom audio routes successfully
			13. Turn off the global promotion
			14. Click “Save” link
			VP: Verify that the portal redirects to App/Device main page and new app/device is added into app/device list successfully
			15. Select above app/device from app/device list
			16. Click “Publish” link
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Publish and suspend apps and devices” privileges
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 10. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Upload default and custom audio routes successfully
		// 13. Turn off the global promotion
		// 14. Click “Save” link
		Hashtable<String,String> data = TestData.appDevicePublish();
		ArrayList<String> deviceInfo = home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * VP: Verify that the portal redirects to App/Device main page
		 */
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(DTSUtil.containsAll(data, deviceInfo));
		/*
		 * VP: Verify that New app/device is added into app/device list successfully
		 */
		home.click(Xpath.LINK_APP_DEVICES);
		Assert.assertTrue(home.checkAnAppDeviceExistByName( data.get("name")));
		// 15. Select above app/device from app/device list
		home.selectADeviceByName(data.get("name"));
		// 16. Click “Publish” link
		home.click(DeviceInfo.PUBLISH);
		/*
		 * Verify that the “Publication Status” of app/device is changed to “PUBLISHED”, the “Publish” link is replaced by “Suspend” link
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Published");
		Assert.assertFalse(home.isElementPresent(DeviceInfo.PUBLISH));
		Assert.assertTrue(home.isElementPresent(DeviceInfo.SUSPEND));
		// Delete app/device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can suspend an app/device when the “Publish and suspend apps and devices” is enabled
	 */
	@Test
	public void TCPAD_09() {
		result.addLog("ID : TCPAD_09 : Verify that the DTS user can suspend an app/device when the “Publish and suspend apps and devices” is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Add and manage apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			10. Click “Add App or Device” link
			11. Fill valid value into all required fields
			12. Upload default and custom audio routes successfully
			13. Turn off the global promotion
			14. Click “Save” link
			VP: Verify that the portal redirects to App/Device main page and new app/device is added into app/device list successfully
			15. Select above app/device from app/device list
			16. Click “Publish” link
			VP: Verify that the app/device is published successfully
			17. Click “Suspend” link
			18. Click “Suspend” button on suspend confirmation dialog
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the “Publish and suspend apps and devices” privileges
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 10. Click “Add App or Device” link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Upload default and custom audio routes successfully
		// 13. Turn off the global promotion
		// 14. Click “Save” link
		Hashtable<String,String> data = TestData.appDevicePublish();
		ArrayList<String> deviceInfo = home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * VP: Verify that the portal redirects to App/Device main page
		 */
		Assert.assertEquals(home.existsElement(DeviceInfo.getElementInfo()).getResult(), "Pass");
		Assert.assertTrue(DTSUtil.containsAll(data, deviceInfo));
		/*
		 * VP: Verify that New app/device is added into app/device list successfully
		 */
		home.click(Xpath.LINK_APP_DEVICES);
		Assert.assertTrue(home.checkAnAppDeviceExistByName( data.get("name")));
		// 15. Select above app/device from app/device list
		home.selectADeviceByName(data.get("name"));
		// 16. Click “Publish” link
		home.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that the app/device is published successfully
		 */
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),	"Published");
		// 17. Click “Suspend” link
		home.click(DeviceInfo.SUSPEND);
		// 18. Click “Suspend” button on suspend confirmation dialog
		home.selectConfirmationDialogOption("Suspend");
		/*
		 * Verify that app/device is suspended successfully
		 */
		home.selectADeviceByName(data.get("name"));
		Assert.assertEquals(home.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Suspended");
		// Delete app/device
		home.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can delete an app/device when the “Publish and suspend apps and devices” is enabled
	 */
	@Test
	public void TCPAD_10() {
		result.addLog("ID : TCPAD_10 : Verify that the DTS user can delete an app/device when the “Publish and suspend apps and devices” is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click “Edit” link
			6. Enable the  “Publish and suspend apps and devices” privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the “Apps & Devices” page is displayed
			9. Navigate to “Apps & Devices” page
			10. Select an app/device from app/device list
			11. Click “Delete” link
			12. Click on “Delete” button on delete confirmation dialog
		 */
		/*
		 * Pre-condition: Create a new app/device
		 */
		// Navigate to "Apps & Devices" page
		home.click(Xpath.LINK_APP_DEVICES);
		// Click "Add app or device" link
		home.click(DeviceList.CREATE_NEW_DEVICE);
		// Create new device
		Hashtable<String,String> data = TestData.appDeviceDraft();
		home.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * ***********************************************************
		 */
		// 3. Navigate to "Users" page
		home.click(Xpath.LINK_USERS);
		// 4. Select a DTS user from the Users table
		home.dtsSelectUserByEmail(dtsUser);
		// 5. Click “Edit” link
		home.click(UserMgmt.EDIT);
		// 6. Enable the  “Publish and suspend apps and devices” privileges
		home.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.Publish_suspend_apps_devices);
		home.click(AddUser.SAVE);
		// 7. Log out DTS portal
		home.logout();
		// 8. Log into DTS portal as above DTS user successfully
		home.login(dtsUser, dtsPass);
		/*
		 * VP. Verify that the “Apps & Devices” page is displayed
		 */
		Assert.assertTrue(home.isElementPresent(Xpath.LINK_APP_DEVICES));
		// 9. Navigate to “Apps & Devices” page
		home.click(Xpath.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		home.selectADeviceByName(data.get("name"));
		// 11. Click “Delete” link
		// 12. Click on “Delete” button on delete confirmation dialog
		home.doDelete(DeviceInfo.DELETE);
		/*
		 * Verify that the portal redirects to Apps & Devices main page, and the deleted app/device is not displayed in app/device list
		 */
		Assert.assertEquals(home.existsElement(DeviceList.getListXpath()).getResult(), "Pass");
		Assert.assertFalse(home.checkAnAppDeviceExistByName( data.get("name")));
	}
	
}
