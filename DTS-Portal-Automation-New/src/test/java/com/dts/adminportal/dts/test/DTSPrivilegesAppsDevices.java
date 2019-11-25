package com.dts.adminportal.dts.test;

import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.dts.adminportal.controller.BasePage;
import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.DeviceEdit;
import com.dts.adminportal.model.DeviceInfo;
import com.dts.adminportal.model.DeviceList;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.Privileges;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.TestData;

public class DTSPrivilegesAppsDevices extends BasePage {
	
	@Override
	protected void initData() {
	}	
	
	/*
	 * Verify that the DTS user can view the app/device info even when both ï¿½Add and manage apps and devicesï¿½ and ï¿½Publish and suspend apps and devicesï¿½ privileges are disabled
	 */
	@Test
	public void TCPAD_01() {
		appDeviceControl.addLog("ID : TCPAD_01 : Verify that the DTS user can view the app/device info even when both ï¿½Add and manage apps and devicesï¿½ and ï¿½Publish and suspend apps and devicesï¿½ privileges are disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Disable the  ï¿½Add and manage apps and devicesï¿½ and ï¿½Publish and suspend apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
			VP: Verify that the app/device list page is displayed
			10. Select an app/device from audio routes list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Create new device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String, String> deviceData = TestData.appDeviceDraft();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(),deviceData);
		ArrayList<String> appDeviceInfo = DeviceInfo.getDeviceInfo();
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the  ï¿½Add and manage apps and devicesï¿½ and ï¿½Publish and suspend apps and devicesï¿½ privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_apps_and_devices.getName());
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * VP: Verify that the app/device list page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceList.getListXpath()), true);
		// 10. Select an app/device from audio routes list
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		/*
		 * Verify that the app/device info page is displayed
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
//		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertTrue(appDeviceControl.getTextListByXpaths(appDeviceInfo).containsAll(check_info));
		// Tear down
		loginControl.logout();
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		appDeviceControl.click(PageHome.LINK_USERS);
		userControl.dtsSelectUserByEmail(DTS_USER);
		appDeviceControl.click(UserMgmt.EDIT);
		userControl.enableAllPrivileges();
		appDeviceControl.click(AddUser.SAVE);
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.selectADeviceByName(deviceData.get("name"));
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		
		
	}
	
	/*
	 * Verify that the DTS user cannot add new app/device when the ï¿½Add and manage apps and devicesï¿½ is disabled
	 */
	@Test
	public void TCPAD_02() {
		appDeviceControl.addLog("ID : TCPAD_02 : Verify that the DTS user cannot add new app/device when the ï¿½Add and manage apps and devicesï¿½ is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Disable the  ï¿½Add and manage apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the  ï¿½Add and manage apps and devicesï¿½ privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * Verify that the ï¿½Add App or Deviceï¿½ link is not displayed
		 */
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
	}
	
	/*
	 * Verify that the DTS user cannot edit an app/device when the ï¿½Add and manage apps and devicesï¿½ is disabled
	 */
	@Test
	public void TCPAD_03() {
		appDeviceControl.addLog("ID : TCPAD_03 : Verify that the DTS user cannot edit an app/device when the ï¿½Add and manage apps and devicesï¿½ is disabled");
		/*
		 	1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Disable the  ï¿½Add and manage apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
			10. Select an app/device from app/device list
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the ï¿½Add and manage apps and devicesï¿½ privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectAnAppDevice();
		System.out.println("i: " + appDeviceInfo);
		/*
		 * Verify that the app/device info page is displayed without the ï¿½Edit Versionï¿½ link
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		System.out.println("j: " + check_info);
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
//		Assert.assertTrue(appDeviceControl.getTextListByXpaths(appDeviceInfo).containsAll(check_info));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.EDIT));
	}
	
	/*
	 * Verify that the DTS user cannot publish or delete an app/device when the ï¿½Publish and suspend apps and devicesï¿½ is disabled
	 */
	@Test
	public void TCPAD_04() {
		appDeviceControl.addLog("ID : TCPAD_04 : Verify that the DTS user cannot publish or delete an app/device when the ï¿½Publish and suspend apps and devicesï¿½ is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Disable the ï¿½Publish and suspend apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
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
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the ï¿½Publish and suspend apps and devicesï¿½ privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * Verify that the app/device info page is displayed without the ï¿½Publishï¿½ and ï¿½Deleteï¿½ links
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.PUBLISH));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.DELETE));
	}
	
	/*
	 * Verify that the DTS user cannot suspend for a published app/device when the ï¿½Publish and suspend apps and devicesï¿½ is disabled
	 */
	@Test
	public void TCPAD_05() {
		appDeviceControl.addLog("ID : TCPAD_05 : Verify that the DTS user cannot suspend for a published app/device when the ï¿½Publish and suspend apps and devicesï¿½ is disabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Disable the ï¿½Publish and suspend apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
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
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Disable the ï¿½Publish and suspend apps and devicesï¿½ privileges
		userControl.enableAllPrivileges();
		userControl.disablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * Verify that the app/device info page is displayed without the ï¿½Suspendï¿½ link
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Published")), true);
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.SUSPEND));
	}
	
	/*
	 * Verify that the DTS user can add new app/device when the ï¿½Add and manage apps and devicesï¿½ is Enabled
	 */
	@Test
	public void TCPAD_06() {
		appDeviceControl.addLog("ID : TCPAD_06 : Verify that the DTS user can add new app/device when the ï¿½Add and manage apps and devicesï¿½ is Enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Enable the  ï¿½Add and manage apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
			VP: Verify that the ï¿½Add App or Deviceï¿½ link is displayed
			10. Click ï¿½Add App or Deviceï¿½ link
			11. Fill valid value into all required fields
			12. Click ï¿½Saveï¿½ link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the  ï¿½Add and manage apps and devicesï¿½ privileges
//		userControl.enablePrivilege(UserEdit.DTS_SITE_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_apps_and_devices.getName());
		userControl.enableAllPrivileges();
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		/*
		 * VP: Verify that the ï¿½Add App or Deviceï¿½ link is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceList.CREATE_NEW_DEVICE));
		// 10. Click ï¿½Add App or Deviceï¿½ link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Click ï¿½Saveï¿½ link
		Hashtable<String,String> data = TestData.appDeviceDraft();
		ArrayList<String> deviceInfo = appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		/*
		 * Verify that the portal redirects to app/device info page with new information
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(data, deviceInfo));
		
		// delete device 
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can edit an app/device when the ï¿½Add and manage apps and devicesï¿½ is Enabled
	 */
	@Test
	public void TCPAD_07() {
		appDeviceControl.addLog("ID : TCPAD_07 : Verify that the DTS user can edit an app/device when the ï¿½Add and manage apps and devicesï¿½ is Enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Enable the  ï¿½Add and manage apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
			10. Select an app/device from app/device list
			VP: Verify that the app/device info page is displayed with ï¿½Edit Versionï¿½ link
			11. Click ï¿½Edit Versionï¿½ link
			12. Change some info of app/device
			13. Click ï¿½Saveï¿½ link
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
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the ï¿½Add and manage apps and devicesï¿½ privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Add_and_manage_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		ArrayList<String> appDeviceInfo = appDeviceControl.selectADeviceByName(data.get("name"));
		/*
		 * VP: Verify that the app/device info page is displayed with ï¿½Edit Versionï¿½ link
		 */
		ArrayList<String> check_info = appDeviceControl.getTextListByXpaths(DeviceInfo.getDeviceInfo());
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertTrue(ListUtil.containsAll(appDeviceInfo, check_info));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.EDIT));
		// 11. Click ï¿½Edit Versionï¿½ link
		appDeviceControl.click(DeviceInfo.EDIT);
		// 12. Change some info of app/device
		String newName = "Edit" + RandomStringUtils.randomNumeric(5);
		appDeviceControl.editData(DeviceEdit.NAME, newName);
		// 13. Click ï¿½Saveï¿½ link
		appDeviceControl.click(DeviceEdit.SAVE);
		/*
		 * Verify that the portal redirects to app/device info page with new information
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceInfo.getElementInfo("Draft")), true);
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.NAME), newName);
	}
	
	/*
	 * Verify that the DTS user can publish an app/device when the ï¿½Publish and suspend apps and devicesï¿½ is enabled
	 */
	@Test
	public void TCPAD_08() {
		appDeviceControl.addLog("ID : TCPAD_08 : Verify that the DTS user can publish an app/device when the ï¿½Publish and suspend apps and devicesï¿½ is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Enable the  ï¿½Add and manage apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
			10. Click ï¿½Add App or Deviceï¿½ link
			11. Fill valid value into all required fields
			12. Upload default and custom audio routes successfully
			13. Turn off the global promotion
			14. Click ï¿½Saveï¿½ link
			VP: Verify that the portal redirects to App/Device main page and new app/device is added into app/device list successfully
			15. Select above app/device from app/device list
			16. Click ï¿½Publishï¿½ link
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the ï¿½Publish and suspend apps and devicesï¿½ privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Click ï¿½Add App or Deviceï¿½ link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Upload default and custom audio routes successfully
		// 13. Turn off the global promotion
		// 14. Click ï¿½Saveï¿½ link
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
		// 16. Click ï¿½Publishï¿½ link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * Verify that the ï¿½Publication Statusï¿½ of app/device is changed to ï¿½PUBLISHEDï¿½, the ï¿½Publishï¿½ link is replaced by ï¿½Suspendï¿½ link
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS), "Published");
		Assert.assertFalse(appDeviceControl.isElementPresent(DeviceInfo.PUBLISH));
		Assert.assertTrue(appDeviceControl.isElementPresent(DeviceInfo.SUSPEND));
		// Delete app/device
		appDeviceControl.doDelete(DeviceInfo.DELETE);
	}
	
	/*
	 * Verify that the DTS user can suspend an app/device when the ï¿½Publish and suspend apps and devicesï¿½ is enabled
	 */
	@Test
	public void TCPAD_09() {
		appDeviceControl.addLog("ID : TCPAD_09 : Verify that the DTS user can suspend an app/device when the ï¿½Publish and suspend apps and devicesï¿½ is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Enable the  ï¿½Add and manage apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
			10. Click ï¿½Add App or Deviceï¿½ link
			11. Fill valid value into all required fields
			12. Upload default and custom audio routes successfully
			13. Turn off the global promotion
			14. Click ï¿½Saveï¿½ link
			VP: Verify that the portal redirects to App/Device main page and new app/device is added into app/device list successfully
			15. Select above app/device from app/device list
			16. Click ï¿½Publishï¿½ link
			VP: Verify that the app/device is published successfully
			17. Click ï¿½Suspendï¿½ link
			18. Click ï¿½Suspendï¿½ button on suspend confirmation dialog
		 */
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// 3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		// 4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the ï¿½Publish and suspend apps and devicesï¿½ privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE, Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Click ï¿½Add App or Deviceï¿½ link
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		// 11. Fill valid value into all required fields
		// 12. Upload default and custom audio routes successfully
		// 13. Turn off the global promotion
		// 14. Click ï¿½Saveï¿½ link
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
		// 16. Click ï¿½Publishï¿½ link
		appDeviceControl.click(DeviceInfo.PUBLISH);
		/*
		 * VP: Verify that the app/device is published successfully
		 */
		Assert.assertEquals(appDeviceControl.getTextByXpath(DeviceInfo.PUBLICATION_STATUS),	"Published");
		// 17. Click ï¿½Suspendï¿½ link
		appDeviceControl.click(DeviceInfo.SUSPEND);
		// 18. Click ï¿½Suspendï¿½ button on suspend confirmation dialog
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
	 * Verify that the DTS user can delete an app/device when the ï¿½Publish and suspend apps and devicesï¿½ is enabled
	 */
	@Test
	public void TCPAD_10() {
		appDeviceControl.addLog("ID : TCPAD_10 : Verify that the DTS user can delete an app/device when the ï¿½Publish and suspend apps and devicesï¿½ is enabled");
		/*
			1. Navigate to DTS portal
			2. Log into DTS portal as a super admin successfully
			3. Navigate to "Users" page
			4. Select a DTS user from the Users table
			5. Click ï¿½Editï¿½ link
			6. Enable the  ï¿½Publish and suspend apps and devicesï¿½ privileges
			7. Log out DTS portal
			8. Log into DTS portal as above DTS user successfully
			VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
			9. Navigate to ï¿½Apps & Devicesï¿½ page
			10. Select an app/device from app/device list
			11. Click ï¿½Deleteï¿½ link
			12. Click on ï¿½Deleteï¿½ button on delete confirmation dialog
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
		// 5. Click ï¿½Editï¿½ link
		appDeviceControl.click(UserMgmt.EDIT);
		// 6. Enable the  ï¿½Publish and suspend apps and devicesï¿½ privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.dtsPrivileges.Publish_and_suspend_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		/*
		 * VP. Verify that the ï¿½Apps & Devicesï¿½ page is displayed
		 */
		Assert.assertTrue(appDeviceControl.isElementPresent(PageHome.LINK_APP_DEVICES));
		// 9. Navigate to ï¿½Apps & Devicesï¿½ page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		// 10. Select an app/device from app/device list
		appDeviceControl.selectADeviceByName(data.get("name"));
		// 11. Click ï¿½Deleteï¿½ link
		// 12. Click on ï¿½Deleteï¿½ button on delete confirmation dialog
		appDeviceControl.doDelete(DeviceInfo.DELETE);
		/*
		 * Verify that the portal redirects to Apps & Devices main page, and the deleted app/device is not displayed in app/device list
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceList.getListXpath()), true);
		Assert.assertFalse(appDeviceControl.checkAnAppDeviceExistByName( data.get("name")));
	}
	
	/*
	 * Verify that the DTS user can update new version a suspended app/device when the "Add and manage apps and devices” is enabled although the device is suspended
	 */
	@Test
	public void TCPAD_11() {
		appDeviceControl.addLog("Verify that the DTS user can update new version a suspended app/device when the 'Add and manage apps and devices' is enabled although the device is suspended");
		
		/*Pre-Condition : There is at least one suspended app/device in app/device page
	 	1. Navigate to DTS portal
		2. Log into DTS portal as a super admin successfully
		3. Navigate to "Users" page
		4. Select a DTS user from the Users table
		5. Click “Edit” link
		6. Enable the  “Add and manage apps and devices” privileges
		7. Log out DTS portal
		8. Log into DTS portal as above DTS user successfully
		9. Navigate to “Apps & Devices” page
		10. Select a suspened app/device from app/device list
		11. Click  "Update Product Info" link
		VP: The app/device edit page is displayed
		*/
		/*
		 * Pre-Condition : There is at least one suspended app/device in app/device page
		 */
		//1. Navigate to DTS portal
		//2. Log into DTS portal as a super admin successfully
		loginControl.login(SUPER_USER_NAME, SUPER_USER_PASSWORD);
		// Suspend app/device
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		appDeviceControl.click(DeviceList.CREATE_NEW_DEVICE);
		Hashtable<String,String> data = TestData.appDevicePublish();
		appDeviceControl.createNewDevice(DeviceEdit.getHash(), data);
		appDeviceControl.click(DeviceInfo.PUBLISH);
		appDeviceControl.click(DeviceInfo.SUSPEND);
		appDeviceControl.selectConfirmationDialogOption("Suspend");
		//3. Navigate to "Users" page
		appDeviceControl.click(PageHome.LINK_USERS);
		//4. Select a DTS user from the Users table
		userControl.dtsSelectUserByEmail(DTS_USER);
		//5. Click “Edit” link
		appDeviceControl.click(UserMgmt.EDIT);
		//6. Enable the  “Add and manage apps and devices” privileges
		userControl.enablePrivilege(AddUser.DTS_PRIVILEGES_TABLE,Privileges.dtsPrivileges.Add_and_manage_apps_and_devices.getName());
		appDeviceControl.click(AddUser.SAVE);
		// 7. Log out DTS portal
		appDeviceControl.logout();
		// 8. Log into DTS portal as above DTS user successfully
		loginControl.login(DTS_USER, DTS_PASSWORD);
		//9. Navigate to “Apps & Devices” page
		appDeviceControl.click(PageHome.LINK_APP_DEVICES);
		//10. Select a suspened app/device from app/device list
		appDeviceControl.selectADeviceByName(data.get("name"));
		//11. Click  "Update Product Info" link
		appDeviceControl.click(DeviceInfo.CREATE_NEW_VERSION);
		appDeviceControl.selectConfirmationDialogOption("OK");
		/*
		 * VP: The app/device edit page is displayed
		 */
		Assert.assertEquals(appDeviceControl.existsElement(DeviceEdit.getHash()),true);

	
	}
}
