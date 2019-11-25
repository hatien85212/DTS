package com.dts.adminportal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import com.dts.adminportal.model.AddUser;
import com.dts.adminportal.model.DTSSitePrivilege;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PageLogin;
import com.dts.adminportal.model.PartnerUserInfo;
import com.dts.adminportal.model.SitePrivilege;
import com.dts.adminportal.model.UserEdit;
import com.dts.adminportal.model.UserInfo;
import com.dts.adminportal.model.UserMgmt;
import com.dts.adminportal.model.UsersList;
import com.dts.adminportal.util.DbUtil;
import com.dts.adminportal.util.ListUtil;
import com.dts.adminportal.util.MailUtil;

import autoitx4java.AutoItX;

public class UsersController extends BaseController {
	public AutoItX AutoItX;

	public UsersController(WebDriver driver) {
		super(driver);
	}

	/**
	 * Select or deselect brand privilege in table 
	 * if select is true: uncheck all brand privilege except the given privilege 
	 * if select is false: check all brand privilege except the given privilege
	 * if brandPrivilege is null and select is false: check all brand privileges
	 * @param privilege get privilege in class: Privileges
	 * @param brandName name of brand 
	 * @param select boolean
	 * @return boolean
	 */
	public boolean selectBrandPrivilegeInTable(String privilege, String brandName, boolean select) {
		try {
			waitForAjax();
			// Get table
			WebElement table = driver.findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(privilege)) {
					// Privilege found
					WebElement brand_groupbox = columns.get(1).findElement(By.tagName("button"));
					// Click on brand_groupbox
					brand_groupbox.click();
					waitForAjax();
					List<WebElement> brand_privileges = columns.get(1).findElement(By.tagName("ul"))
							.findElements(By.tagName("li"));
					// Uncheck all brand privileges
					WebElement AllBrandPrivilege = brand_privileges.get(0).findElement(By.tagName("input"));
					boolean isAllBrandChecked = AllBrandPrivilege.isSelected();
					if (isAllBrandChecked) {
						AllBrandPrivilege.click();
						waitForAjax();
					} else {
						AllBrandPrivilege.click();
						waitForAjax();
						AllBrandPrivilege.click();
						waitForAjax();
					}
					if (!select) {
						AllBrandPrivilege.click();
						waitForAjax();
					}
					// Select brand privilege
					for (WebElement brand_privilege : brand_privileges) {
						if (brandName != null && brand_privilege.getText().contains(brandName)) {
							addLog("Select brand privilege: " + brandName);
							brand_privilege.findElement(By.tagName("input")).click();
							waitForAjax();
							brand_groupbox.click();
							waitForAjax();
							return true;
						}
					}
					addLog("Brand privilege not found");
					return false;
				}
			}
			addLog("Site privilege not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement is present");
			return false;
		}
	}

	/**
	 * Select or deselect brand privilege 
	 * if select is true: uncheck all brand privileges except the given privilege 
	 * if select is false: check all brand privileges except the given privilege
	 * if brandPrivilege is null and select is false: check all brand privileges
	 * @param xpath xpath to combobox: AddUser.BRAND_PRIVILEGES
	 * @param brandName name of brand 
	 * @param select boolean
	 * @return boolean
	 */
	public boolean selectBrandPrivilege(String xpath, String brandName, boolean select) {
		try {
			waitForAjax();
//			// Get brand privileges groupbox
			WebElement brand_groupbox = driver.findElement(By.xpath(xpath)).findElement(By.tagName("button"));
//			// Click on brand_groupbox
			brand_groupbox.click();
//			WebElement brand_groupbox = driver.findElement(By.xpath(xpath));
//			brand_groupbox.click();
			waitForAjax();
			List<WebElement> brand_privileges = driver.findElement(By.xpath(xpath)).findElement(By.tagName("ul"))
					.findElements(By.tagName("li"));
			// Uncheck all privileges
			WebElement AllBrandPrivilege = brand_privileges.get(0).findElement(By.tagName("input"));
			boolean isAllBrandChecked = AllBrandPrivilege.isSelected();
			if (isAllBrandChecked) {
				AllBrandPrivilege.click();
				waitForAjax();
			} else {
				AllBrandPrivilege.click();
				waitForAjax();
				AllBrandPrivilege.click();
				waitForAjax();
			}
			if (!select) {
				AllBrandPrivilege.click();
				waitForAjax();
			}
			// Select brand privilege
			for (WebElement brand_privilege : brand_privileges) {
				if (brandName != null && brand_privilege.getText().contains(brandName)) {
					addLog("Select brand privilege: " + brandName);
					brand_privilege.findElement(By.tagName("input")).click();
					waitForAjax();
					brand_groupbox.click();
					waitForAjax();
					return true;
				}
			}
			addLog("Brand privilege not found");
			brand_groupbox.click();
			waitForAjax();
			return false;

		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
			return false;
		}
	}

	@Override
	public void editData(String editXpath, String data) {
		if (data != null) {
			try {
				waitForAjax();
				driver.findElement(By.xpath(editXpath)).clear();
				addLog("change data : " + data);
				driver.findElement(By.xpath(editXpath)).sendKeys(data);
				waitForAjax();
				// Work around due to issue PDPP-836
				try {
					if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES)).isDisplayed()) {
						if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
								.findElement(By.tagName("button")).getText()
								.equals("None selected")) {
							selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, null, false);
						}
					}
				} catch (NoSuchElementException e) {
				}
				// End work around
			} catch (NoSuchElementException e) {
				addLog("NoSuchElementException at editData :  "
						+ editXpath);
			}
		}
	}
	
	public boolean enablePrivilege(String xpath, String privilege) {
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(privilege)) {
					// check privilege
					WebElement checkbox = columns.get(0).findElement(By.tagName("input"));
					if (!checkbox.isSelected()) {
						addLog("Enable: " + privilege);
						checkbox.click();
						waitForAjax();
						if(columns.size() == 3 && columns.get(1).getText().equals("None selected")){
							selectBrandPrivilegeInTable(privilege, null, false);
						}
					}else{
						addLog("Privilege enabled, break");
					}
					return true;
				}
			}
			addLog("Privilege: " + privilege + " not found");
			return false;
			
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return false;
		}
	}
	
	public boolean enablePrivileges(String xpath, ArrayList<String> privileges) {
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (String privilege : privileges) {
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(0).getText().contains(privilege)) {
						// check privilege
						WebElement privilegeCheckbox = columns.get(0)
								.findElement(By.tagName("input"));
						if (!privilegeCheckbox.isSelected()) {
							addLog("Enable: " + privilege);
							privilegeCheckbox.click();
							waitForAjax();
							if (columns.size() == 3	&& columns.get(1).getText().equals("None selected")){
								selectBrandPrivilegeInTable(privilege, null, false);
							}
							break;
						} else {
							addLog("Privilege enabled, break");
							break;
						}
					}
				}
			}
			// Work around due to issue PDPP-836
			if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
					.findElement(By.tagName("button")).getText()
					.equals("None selected")) {
				selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, null, false);
			}
			// End work around
			clickText("Save");
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return false;
		}
		return true;
	}
	
	public boolean enableAllPrivileges() {
		try {
			// get table
			List<WebElement> tbodys = driver.findElements(By.tagName("tbody"));
			// get all rows
			for (WebElement tbody : tbodys) {
				if(!tbody.isDisplayed()){
					break;
				}
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					// check privilege
					WebElement privilegeCheckbox = columns.get(0).findElement(
							By.tagName("input"));
					if (!privilegeCheckbox.isSelected()) {
						addLog("Enable privilege : "
								+ columns.get(0).getText());
						privilegeCheckbox.click();
						waitForAjax();
					}
				}
			}
			// Select brand privileges
			selectBrandPrivilege(AddUser.BRAND_PRIVILEGES, null, false);
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return false;
		}
		return true;
	}
	
	public boolean disablePrivilege(String xpath, String privilege) {
		try {
			// get table
			waitElement(xpath);
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(privilege)) {
					// check privilege
					WebElement checkbox = columns.get(0).findElement(By.tagName("input"));
					if (checkbox.isSelected()) {
						addLog("Disable : " + privilege);
						checkbox.click();
						waitForAjax();
						return true;
					}
					addLog("Privilege disabled, break");
					return true;
				}
			}
			addLog("Privilege: " + privilege + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return false;
		}
	}
	
	public boolean disablePrivileges(String xpath, ArrayList<String> list) {
		waitForAjax();
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (String privilege : list) {
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(0).getText().contains(privilege)) {
						// check privilege
						String attr;
						if(columns.size() == 3){
							attr = columns.get(2).getAttribute("innerHTML");
						}else{
							attr = columns.get(1).getAttribute("innerHTML");
						}
						if (!attr.contains("disabled")) {
							addLog("Disable : " + privilege);
							columns.get(0).findElement(By.tagName("label")).click();
							waitForAjax();
							break;
						} else {
							addLog("Privilege " + privilege + " disabled, break");
							break;
						}
					}
				}
			}
			clickText("Save");
			for (String privilege : list) {
				if (driver.getPageSource().contains(privilege)) {
					System.err.println("Can't disable " + privilege);
					Assert.assertFalse(true);
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			return false;
		}
	}

	public boolean disableAllPrivileges(String xpath) {
		try {
			// get table
			WebElement tbody = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// check privilege
				WebElement privilege = columns.get(0).findElement(By.tagName("input"));
				if (privilege.isSelected()) {
					addLog("Disable : " + columns.get(0).getText());
					privilege.click();
					waitForAjax();
				}
			}
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return false;
		}
		return true;
	}
	
	public boolean dtsSelectUserByEmail(String email) {
		try {
			clickText("First");
			Thread.sleep(4000);
			int limit = getPageSize(PageHome.ADMIN_USER_LIST_TABLE_INFO);
			while (limit > 0) {
				// Get table
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				if (tbody.getText().contains(email)) {
					// Get all rows
					List<WebElement> rows = tbody.findElements(By.tagName("tr"));
					for (WebElement row : rows) {
						// Get all columns
						List<WebElement> colums = row.findElements(By.tagName("td"));
						if (colums.get(colums.size() - 2).getText().equals(email)) {
							// Found email
							// Select user by email
							addLog("Select user has email: " + email);
							colums.get(0).findElement(By.tagName("a")).click();
							waitForAjax();
							return true;
						}
					}
				}
				limit--;
				if (limit == 0) {
					break;
				}
				clickText("Next");
			}
			addLog("User has email: " + email + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return false;
		} catch (InterruptedException e) {
			return false;
		}
	}
	
	public ArrayList<String> selectUserInfoByEmail(String email) {
//		System.out.println(Calendar.getInstance().getTime());
		try {
			Thread.sleep(3000);
			clickText("First");
			int limit = getPageSize(PageHome.ADMIN_USER_LIST_TABLE_INFO);
			while (limit > 0) {
				ArrayList<String> list = new ArrayList<String>();
				// Get table
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				if (tbody.getText().contains(email)) {
					// Get all rows
					List<WebElement> rows = tbody.findElements(By.tagName("tr"));
					for (WebElement row : rows) {
						// Get all columns
						List<WebElement> colums = row.findElements(By.tagName("td"));
						if (colums.get(colums.size() - 2).getText().equals(email)) {
							// Get user data
							for(WebElement column : colums){
								list.add(column.getText());
							}
							// Select user by email
							colums.get(0).findElement(By.tagName("a")).click();
							waitForAjax();
							addLog("Select user has email: " + email);
							return list;
						}
					}
				}
				limit--;
				if (limit == 0) {
					break;
				}
				clickText("Next");
			}
			addErrorLog("User has email: " + email + " not found");
			Assert.assertTrue(false);
			return null;
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			Assert.assertTrue(false);
			return null;
		} catch (InterruptedException e) {
			return null;
		}
	}

	public boolean deleteUserByEmail(String email) {
		if(!dtsSelectUserByEmail(email)){
			return true;
		}
		doDelete(UserMgmt.DELETE_ACCOUNT);
		if (isElementPresent(UsersList.USER_TABLE)) {
			addLog("User has email: " + email + " is deleted successfully");
			return true;
		}
		addErrorLog("User has email: " + email + " is not deleted successfully");
		return false;
	}
	
	public boolean changeEmailOfUser(String email) {
		if(!dtsSelectUserByEmail(email)){
			return true;
		}
//		dtsSelectUserByEmail(email);
		
		click(UserInfo.EDIT);
		editData(UserEdit.EMAIL, RandomStringUtils.randomAlphanumeric(20) + "@mailinator.com");
		click(UserEdit.SAVE);
		click(PageHome.LINK_USERS);
		if (isElementPresent(UsersList.USER_TABLE)) {
			addLog("User has change " + email + " successfully");
			return true;
		}
		addErrorLog("User has not change " + email + " successfully");
		return false;
	}
	
	public String getCompanyOfUser(String name){
		String result = null;
		try {
			Thread.sleep(2000);
			int size = getPageSize(PageHome.ADMIN_USER_LIST_TABLE_INFO);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(4).getText().equals(name)) {
						result = columns.get(2).getText();
					}
				}
				size--;
				if (size > 0) {
					clickText("Next");
				}
//				clickText("Previous");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			addErrorLog("Product: " + name + " not found");
			return null;
		} catch (InterruptedException e) {
			return null;
		}
		return result;
	}
	
	public boolean checkUserExistByEmail(String email) {
		boolean flag = true;
		int limit = getPageSize(PageHome.ADMIN_USER_LIST_TABLE_INFO);
		try {
			while (flag) {
				// step 1 : find element tbody
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				if (tbody.getText().contains(email)) {
					flag = false;
					// find and click user by email
					// step 2 : find all row
					List<WebElement> rows = tbody.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> columns = row.findElements(By.tagName("td"));
						if (columns.get(columns.size() - 2).getText().equals(email)) {
							// found element
							addLog("User has email: " + email + " exists");
							return true;
						}
					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
			addLog("User has email: " + email + " not exist");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return false;
		}
	}		
	
	public boolean isAllPrivilegeSelected(String table) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(table)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				// Get columns
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// Get privilege
				WebElement privilege = columns.get(0);
				boolean isSelected = privilege.findElement(By.tagName("input"))
						.isSelected();
				if (isSelected) {
					addLog("Privilege: " + privilege.getText()
							+ " is selected");
				} else {
					addLog("Privilege: " + privilege.getText()
							+ " is not selected");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement is present");
			return false;
		}
	}
	
	public boolean checkUserAddedValue(String xpath, List<String> data) {
		waitForAjax();
		boolean check = false;
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(xpath));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column

				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				WebElement afName = all_column.get(0).findElement(
						By.tagName("a"));
				WebElement alName = all_column.get(1).findElement(
						By.tagName("a"));
				WebElement sTitle = all_column.get(2).findElement(
						By.tagName("span"));
				WebElement sPhone = all_column.get(3).findElement(
						By.tagName("span"));
				WebElement smail = all_column.get(4).findElement(
						By.tagName("span"));
				String fName = afName.getAttribute("name");
				String lName = alName.getAttribute("name");
				String title = sTitle.getText();
				String phone = sPhone.getText();
				String mail = smail.getText();
				if (fName.equals(data.get(0)) && lName.equals(data.get(1))
						&& title.equals(data.get(2))
						&& phone.equals(data.get(3))
						&& mail.equals(data.get(4))) {
					return true;
				} else {
					check = false;
				}

			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + xpath);
		}
		return check;

	}

	public boolean isUserAdded(String xpath, List<String> data, String index) {
		boolean result = true;
		try {
			waitForAjax();
			int sizePage = getPageSize(index);
			// check variant page
			for (int i = 0; i < sizePage; i++) {
				if (checkUserAddedValue(xpath, data)) {
					addLog("On page exist Variant");
					return result;
				} else {
					addLog("Next Page");
					driver.findElement(By.linkText("Next")).click();
					// wait for load
					waitForAjax();
				}
			}
		} catch (NoSuchElementException e) {
			result = false;
			addLog("NoSuchElementException");
		}
		return result;
	}
	
	public ArrayList<PartnerUserInfo> getPartnerUsers(String tbody) {
		ArrayList<PartnerUserInfo> users = new ArrayList<PartnerUserInfo>();
		try {
			waitForAjax();
			waitForLoadElement(tbody);
			WebElement body = driver.findElement(By.xpath(tbody));
			// get all rows
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			// get data
			for (WebElement row : rows) {
				// get all colums
				List<WebElement> colums = row.findElements(By.tagName("td"));
				PartnerUserInfo info = new PartnerUserInfo();
				if (colums.size() == 5) {
					info.setFirstName(colums.get(0).getText());
					info.setLastName(colums.get(1).getText());
					info.setTitle(colums.get(2).getText());
					info.setEmail(colums.get(3).getText());
					info.setPhone(colums.get(4).getText());
					// add to array list
					users.add(info);
				}

			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException on getPartnerUsers");
		}
		return users;
	}
	
	public boolean checkPartnerUserInfo(PartnerUserInfo userInfo,
			Hashtable<String, String> elementsInfo) {
		boolean result = true;
		try {
			waitForAjax();
			String fullname = driver.findElement(By.xpath(elementsInfo.get("fullname"))).getText();
			String title = driver.findElement(By.xpath(elementsInfo.get("title"))).getText();
			String phone = driver.findElement(By.xpath(elementsInfo.get("phone"))).getText();
			String email = driver.findElement(By.xpath(elementsInfo.get("email"))).getText();
			// check name
			if (fullname.equals(userInfo.getFirstName() + " " + userInfo.getLastName())) {
				addLog("name correct : " + fullname);
			} else {
				addLog("name inccorect : "  + fullname + " : "
						+ userInfo.getFirstName() + " "
						+ userInfo.getLastName());
				result = false;
			}
			// check title
			if (title.equals(userInfo.getTitle())) {
				addLog("title correct : " + title);
			} else {
				addLog("title inccorect : " + title + " : " + userInfo.getTitle());
				result = false;
			}
			// check phone
			if (userInfo.getPhone().contains(phone)) {
				addLog("phone correct : " + phone);
			} else {
				addLog("phone inccorect : " + phone + " : " + userInfo.getPhone());
				result = false;
			}
			// check email
			if (email.equals(userInfo.getEmail())) {
				addLog("email correct : " + email);
			} else {
				addLog("email inccorect : " + email + " : " + userInfo.getEmail());
				result = false;
			}
		} catch (NoSuchElementException e) {
			result = false;
			addLog("NoSuchElementException");
		}
		return result;
	}
	
	public boolean addUser(Hashtable<String,String> hashXpath, Hashtable<String,String> data) {
		
		// Add user
		boolean result = true;
		try {
				waitForAjax();
				editData(hashXpath.get("firstName"), data.get("firstName"));
				editData(hashXpath.get("lastName"), data.get("lastName"));
				if (data.containsKey("company")) {
					selectOptionByName(hashXpath.get("company"),data.get("company"));
				}
				editData(hashXpath.get("title"), data.get("title"));
				editData(hashXpath.get("email"), data.get("email"));
				editData(hashXpath.get("code"), data.get("code"));
				editData(hashXpath.get("phone"), data.get("phone"));
				if (data.containsKey("brand"))
				{
				selectBrandPrivilege(hashXpath.get("brand"), data.get("brand"));
				}
				// save
				if (data.containsKey("save")) {
					click(hashXpath.get("save"));
				    //If user is not created successfully
					if(!checkMessageDisplay(AddUser.messages.Success.getName())){
						result = false;
					}
				}
				waitForAjax();	
			
			return true;
		} catch (NoSuchElementException e) {
			addLog(e.toString());
			result = false;
		}
		return result;

	}
	
	public boolean selectBrandPrivilege(String xpath, String brandPrivilege) {
		try {
			// Get brand privileges groupbox
			WebElement brand_groupbox = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("button"));
			// Click on brand_groupbox
			brand_groupbox.click();
			waitForAjax();
			List<WebElement> brand_privileges = driver.findElement(By.xpath(xpath)).findElement(By.tagName("ul"))
					.findElements(By.tagName("li"));
			// Uncheck all privileges
			WebElement AllBrandPrivilege = brand_privileges.get(0).findElement(By.tagName("input"));
			boolean isAllBrandChecked = AllBrandPrivilege.isSelected();
			if(isAllBrandChecked){
				AllBrandPrivilege.click();
				waitForAjax();
			}else{
				AllBrandPrivilege.click();
				waitForAjax();
				AllBrandPrivilege.click();
				waitForAjax();
			}
			// Select brand privilege
			for (WebElement brand_privilege : brand_privileges) {
				if (brand_privilege.getText().contains(brandPrivilege)) {
					addLog("Select brand privilege: " + brandPrivilege);
					brand_privilege.findElement(By.tagName("input")).click();
					waitForAjax();
					brand_groupbox.click();
					waitForAjax();
					return true;
				}
			}
			addLog("Brand privilege not found");
			return false;
			
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
			return false;
		}
	}
	
	public boolean fillDataAddUser(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			editData(hashXpath.get("firstName"), data.get("firstName"));
			editData(hashXpath.get("lastName"), data.get("lastName"));
			if (data.containsKey("company")) {
				selectOptionByName(hashXpath.get("company"),
						data.get("company"));
			}
			editData(hashXpath.get("title"), data.get("title"));
			editData(hashXpath.get("email"), data.get("email"));
			editData(hashXpath.get("code"), data.get("code"));
			editData(hashXpath.get("phone"), data.get("phone"));
			waitForAjax();
			return true;
		} catch (NoSuchElementException e) {
			addLog(e.toString());
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean selectPartnerUserByEmail(String email) {
		boolean flag = true;
		int limit = getPageSize(PageHome.ADMIN_USER_LIST_TABLE_INFO);
		try {
			while (flag) {
				// step 1 : find element tbody
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				if (tbody.getText().contains(email)) {
					flag = false;
					// step 2 : find all row
					List<WebElement> rows = tbody
							.findElements(By.tagName("tr"));
					// step 3 : for all row get element contain email and click
					for (WebElement row : rows) {
						// get all colums
						List<WebElement> colums = row.findElements(By
								.tagName("td"));
						if (colums.size() == 5) {
							if (colums.get(4).getText().equals(email)) {
								addLog("found email");
								// found element
								// click element
								WebElement link = colums.get(0).findElement(
										By.tagName("a"));
								addLog("Select user has email: " + email);
								link.click();
								waitForAjax();
								return true;
							}
						}

					}
				}
				limit--;
				if (limit < 0) {
					flag = false;
				}
				driver.findElement(By.linkText("Next")).click();
				waitForAjax();
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception : " + email);
		}
		return false;
	}
	
	public ArrayList<String> getPrivileges(String xpath) {
		ArrayList<String> privileges = new ArrayList<String>();
		try {
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				addLog("Privilege: " + columns.get(0).getText());
				privileges.add(columns.get(0).getText());
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException : getPrivileges");
			return null;
		}
		return privileges;
	}
	
	public ArrayList<DTSSitePrivilege> getDTSSitePrivileges(String xpath) {
		ArrayList<DTSSitePrivilege> privileges = new ArrayList<DTSSitePrivilege>();
		try {
			WebElement table = driver.findElement(By.xpath(xpath));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.size() == 2) {
					DTSSitePrivilege userPrivilege = new DTSSitePrivilege();
					userPrivilege.value = columns.get(0).getText();
					userPrivilege.privileges = columns.get(0);
					userPrivilege.notifications = columns.get(1);
					privileges.add(userPrivilege);
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : getAddUserPrivileges");
		}
		return privileges;
	}
	
	public boolean enableDTSSitePrivilege(DTSSitePrivilege userPrivilege) {
		try {
			String attr = userPrivilege.notifications.getAttribute("innerHTML");
			if (attr.contains("disabled")) {
				click(userPrivilege.privileges.findElement(By.tagName("input")));
				return true;
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : enableUserPrivilege");
		}
		return false;
	}

	public boolean enableSitePrivilege(SitePrivilege userPrivilege) {
		try {
			String attr = userPrivilege.notifications.getAttribute("innerHTML");
			if (attr.contains("disabled")) {
				click(userPrivilege.privileges);
				return true;
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : enableUserPrivilege");
		}
		return false;
	}
	
	public boolean isCheckBoxEnable(WebElement checkBox) {
		try {
			return !checkBox.getAttribute("innerHTML").contains("disabled");
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : checkBoxEnable");
		}
		return false;
	}

	public boolean disableDTSSitePrivilege(DTSSitePrivilege userPrivilege) {
		try {
			String attr = userPrivilege.notifications.getAttribute("innerHTML");
			if (!attr.contains("disabled")) {
				click(userPrivilege.privileges.findElement(By.tagName("input")));
				return true;
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : disableUserPrivilege");
		}
		return false;

	}

	public boolean isSelectedAllPrivileges(ArrayList<DTSSitePrivilege> list) {
		try {
			for (DTSSitePrivilege userPrivilege : list) {
				String attr = userPrivilege.notifications
						.getAttribute("innerHTML");
				if (attr.contains("disabled")) {
					return false;
				} else {
					System.out.println(userPrivilege.value + " is selected");
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : enableUserPrivilege");
		}
		return true;
	}

	public ArrayList<SitePrivilege> getSitePrivileges(String xpath) {
		ArrayList<SitePrivilege> privileges = new ArrayList<SitePrivilege>();
		SitePrivilege userPrivilege = new SitePrivilege();
		try {
			WebElement table = driver.findElement(By.xpath(xpath));
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.size() == 3) {
					userPrivilege.value = columns.get(0).getText();
					userPrivilege.privileges = columns.get(0);
					userPrivilege.brand = columns.get(1);
					userPrivilege.notifications = columns.get(2);
					privileges.add(userPrivilege);
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : getAddUserPrivileges");
		}
		return privileges;
	}

	public boolean disableSitePrivilege(ArrayList<SitePrivilege> list) {
		try {
			for (SitePrivilege userPrivilege : list) {
				if (!disableSitePrivilege(userPrivilege)) {
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException : disableDTSSitePrivilege");
		}
		return false;

	}

	public boolean uncheckNotification(ArrayList<SitePrivilege> list) {
		try {
			for (SitePrivilege userPrivilege : list) {
				String attr = userPrivilege.notifications
						.getAttribute("innerHTML");
				if (!attr.contains("disabled")) {
					click(userPrivilege.notifications.findElement(By
							.tagName("input")));
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			System.err.println("NoSuchElementException : uncheckNotification");
		}
		return false;
	}

	public boolean disableSitePrivilege(SitePrivilege sitePrivilege) {
		try {
			String attr = sitePrivilege.notifications.getAttribute("innerHTML");
			if (!attr.contains("disabled")) {
				click(sitePrivilege.privileges.findElement(By.tagName("input")));
			}
			return true;
		} catch (NoSuchElementException e) {
			System.err
					.println("NoSuchElementException : disableDTSSitePrivilege");
		}
		return false;
	}
	
	public boolean isAllCheckboxDisabled(String Xpath) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath));
			WebElement body = element.findElement(By.tagName("tbody"));
			// Get all row
			List<WebElement> all_row = body.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				// Get column size
				int columnNum = all_column.size();
				// Get checkbox status
				String checkboxStatus = getAtribute(
						all_column.get(columnNum - 1).findElement(
								By.tagName("input")), "disabled");
				if (!checkboxStatus.contains("true")) {
					return false;
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
			return false;
		}
		return true;
	}
	
	public void uncheckAllNotification(String Xpath) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath)).findElement(By.tagName("tbody"));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				// Get column size
				int columnNum = all_column.size();
				// Get checkbox status
				WebElement checkbox = all_column.get(columnNum - 1)
						.findElement(By.tagName("input"));
				boolean isSelected = checkbox.isSelected();
				if (isSelected) {
					addLog("Uncheck notification: " + checkbox.getAttribute("id"));
					checkbox.click();
					waitForAjax();
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
		}
	}
	
	public void checkSitePrivilegesByText(String Xpath, String SiteName) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				// Get checkbox status
				WebElement checkbox = all_column.get(0).findElement(
						By.tagName("input"));
				boolean isChecked = checkbox.isSelected();
				String label = all_column.get(0).getText();// getAttribute("value");
				if (label.equals(SiteName) && !isChecked) {
					checkbox.click();
					break;
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
		}
	}

	public void uncheckSitePrivilegesByText(String Xpath, String SiteName) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				String label = row.findElement(By.tagName("label")).getText();
				// Get checkbox status
				WebElement checkbox = all_column.get(0).findElement(
						By.tagName("input"));
				boolean isChecked = checkbox.isSelected();
				if (label.equals(SiteName) && isChecked) {
					checkbox.click();
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
		}
	}

	public boolean isNotificationCheckboxDisable(String Xpath, String SiteName) {
		waitForAjax();
		try {
			// Get the contact row
			WebElement element = driver.findElement(By.xpath(Xpath)).findElement(By.tagName("tbody"));
			// Get all row
			List<WebElement> all_row = element.findElements(By.tagName("tr"));
			for (WebElement row : all_row) {
				// Get all column
				List<WebElement> all_column = row
						.findElements(By.tagName("td"));
				String label = row.findElement(By.tagName("label")).getText();
				int columnNum = all_column.size();
				// Get checkbox status

				WebElement checkbox = all_column.get(columnNum - 1)
						.findElement(By.tagName("input"));
				String disabled = checkbox.getAttribute("disabled");
				if (label.equals(SiteName)) {
					if (!disabled.isEmpty() && disabled.contains("true")) {
						return true;
					} else {
						return false;
					}
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("NoSuchElementException :" + Xpath);
		}
		return false;
	}
	
	public boolean isNotificationCheckboxEnable(String xpath, String Privilege) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Get columns
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(Privilege)) {
					WebElement checkbox = columns.get(columns.size() - 1).findElement(
							By.tagName("input"));
					if (checkbox.getAttribute("disabled") == null) {
						addLog("Notification checkbox of privilege: "
								+ Privilege + " is enabled");
						return true;
					} else {
						addLog("Notification checkbox of privilege: "
								+ Privilege + " is not enabled");
						return false;
					}
				}
			}
			addLog("Privilege not found");
			return false;

		} catch (NoSuchElementException e) {
			addLog("NoSuchElement " + xpath);
			return false;
		}
	}

	public boolean enableAllnotification() {
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// check privilege
				String attr = columns.get(2).getAttribute("innerHTML");
				System.out.println(attr);
				if (attr.contains("disabled")) {
					System.out.println("Enable : " + columns.get(2).getText());
					columns.get(2).click();
					waitForAjax();
				}
			}
			clickText("Save");
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
			return false;
		}
		return true;
	}
	
	public boolean isNotificationCheckboxSelected(String xpath, String Privilege) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Get columns
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				if (columns.get(0).getText().contains(Privilege)) {
					WebElement checkbox = columns.get(2).findElement(
							By.tagName("input"));
					if (checkbox.isSelected()) {
						addLog("Notification checkbox of privilege: "
								+ Privilege + " is selected");
						return true;
					} else {
						addLog("Notification checkbox of privilege: "
								+ Privilege + " is not selected");
						return false;
					}
				}
			}
			addLog("Privilege not found");
			return false;

		} catch (NoSuchElementException e) {
			addLog("NoSuchElement " + xpath);
			return false;
		}
	}
	
	public boolean isAllNotificationCheckboxSelected(String xpath){
		ArrayList<String> privileges = getPrivileges(xpath);
		for(String privilege : privileges){
			if(!isNotificationCheckboxSelected(xpath, privilege)){
				return false;
			}
		}
		return true;
	}
	
	public String getAtribute(WebElement element, String atr) {
		try {
			waitForAjax();
			String text = element.getAttribute(atr);
			addLog("Text : " + text);
			return text;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at getTextByXpath : " + element);
			return "";
		}
	}
	
	public boolean isAllNotificationCheckboxDisable(String xpath) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Get columns
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				WebElement checkbox = columns.get(columns.size() - 1).findElement(
						By.tagName("input"));
				if (checkbox.getAttribute("disabled") != null) {
					addLog("Notification checkbox: " + columns.get(0).getText() + " is disabled");
				} else {
					addLog("Notification checkbox: " + columns.get(0).getText() + " is enabled");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement " + xpath);
			return false;
		}
	}
	
	public boolean isAllPrivilegeEnable(String table) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(table)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				// Get columns
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// Get privilege
				WebElement privilege = columns.get(0);
				String disable = privilege.findElement(By.tagName("input"))
						.getAttribute("disabled");
				if (disable == null) {
					addLog("Privilege: " + privilege.getText()
							+ " is enable and editable");
				} else {
					addLog("Privilege: " + privilege.getText()
							+ " is disable and uneditable");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement is present");
			return false;
		}
	}
	
	public boolean checkAllUserStatus(String status) {
		String sql;
		try {
			List<WebElement> rows = driver.findElement(By.tagName("tbody"))
					.findElements(By.tagName("tr"));
			if (rows.get(0).getText().equals("No data available in table")) {
				addLog("No data for verifying");
				return false;
			}
			ArrayList<String> emailList = getDataByHeaderText(
					UsersList.USER_TABLE, "Email");
			for (String item : emailList) {
				System.out.println(item);
			}
			if (emailList.contains("fae@dts.com")) {
				emailList.remove("fae@dts.com");
			}
			if (emailList.contains("sys@dts.com")) {
				emailList.remove("sys@dts.com");
			}
			if (status.equals("All Accounts")) {
				sql = "SELECT EMAIL FROM ACCOUNT";
			} else {
				sql = "SELECT EMAIL FROM ACCOUNT WHERE STATUS = '"
						+ status.toUpperCase() + "'";
			}
			String emailReturned = DbUtil.selectStatment(sql);
			System.out.println(emailReturned);

			if (ListUtil.containsListText(emailReturned, emailList)) {

				addLog("All user status is correct");
				return true;
			}
			addLog("All user status is not correct");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No such element present");
			return false;
		}
	}
	
	public boolean isAllNotificationCheckboxEnable(String xpath) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Get columns
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				WebElement checkbox = columns.get(columns.size() - 1).findElement(
						By.tagName("input"));
				if (!checkbox.isDisplayed()){
					continue;
				}
				if (checkbox.getAttribute("disabled") == null) {
					addLog("Notification checkbox: " + columns.get(0).getText() + " is enabled");
				} else {
					addLog("Notification checkbox: " + columns.get(0).getText() + " is disabled");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement " + xpath);
			return false;
		}
	}
	
	public boolean isAllPrivilegeDisable(String table) {
		try {
			// Get table
			WebElement tbody = driver.findElement(By.xpath(table)).findElement(
					By.tagName("tbody"));
			// Get rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				// Get columns
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// Get privilege
				WebElement privilege = columns.get(0);
				if (!privilege.isDisplayed()){
					continue;
				}
				String disable = privilege.findElement(By.tagName("input"))
						.getAttribute("disabled");
				if (disable != null) {
					addLog("Privilege: " + privilege.getText()
							+ " is disabled and uneditable");
				} else {
					addLog("Privilege: " + privilege.getText()
							+ " is enabled and editable");
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement is present");
			return false;
		}
	}
	
	// TODO: need to replace with other same existed function
	public boolean isBrandPrivilegeSelected(String xpath, String privilege) {
		try {
			Thread.sleep(3000);
			// Get brand privileges groupbox
			WebElement brand_groupbox = driver.findElement(By.xpath(xpath))
					.findElement(By.tagName("button"));
			// Click on brand_groupbox
			brand_groupbox.click();
			waitForAjax();
			// Get all brand privileges
			List<WebElement> brand_privileges = driver.findElement(
					By.xpath(xpath)).findElements(By.tagName("li"));
			// Check brand privilege selected
			for (WebElement brand_privilege : brand_privileges) {
				if (brand_privilege.getText().equals(privilege)) {
					// Found privilege
					if (brand_privilege.findElement(By.tagName("input"))
							.isSelected()) {
						addLog("Privilege: " + privilege
								+ " is selected");
						return true;
					}
					addLog("Privilege: " + privilege
							+ " is not selected");
					return false;
				}
			}
			addLog("Privilege: " + privilege + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No such element: " + xpath);
			return false;
		} catch(InterruptedException e){
			e.printStackTrace();
			return false;
		}
	}
	
	// TODO: move following method to Wrapper class - ???
	public boolean selectRowAt(String tbody, int index, String waitElement) {
		boolean result = true;
		try {
			waitForAjax();
			WebElement body = driver.findElement(By.xpath(tbody));
			// get all rows
			List<WebElement> rows = body.findElements(By.tagName("tr"));
			// get data
			if (rows.size() >= index) {
				// get row at index
				WebElement rowAt = rows.get(index).findElement(By.tagName("td"));
				WebElement link = rowAt.findElement(By.tagName("a"));
				link.click();
				waitForAjax();
			} else {
				addLog("Out of range or no data");
			}

		} catch (NoSuchElementException e) {
			result = false;
			addLog("NoSuchElementException");
		}
		return result;
	}
	
	// TODO: not yet checked
		public HashMap<String, String> getItemUserMenu(String xpath) {
			HashMap<String, String> data = new HashMap<String, String>();
			try {
				WebElement footer = driver.findElement(By.xpath(xpath));
				List<WebElement> columns = footer.findElements(By.tagName("a"));
				for (int i = 1; i <= columns.size(); i++) {
					data.put("option " + i, columns.get(i - 1).getAttribute("text"));
				}
			} catch (NoSuchElementException e) {
				addLog("NoSuchElementException on getItemUserMenu " + xpath);
			}
			return data;
		}
	
	// TODO 
	public ArrayList<String> getAllLinkA(String xpath) {
		ArrayList<String> links = new ArrayList<String>();
		try {
			WebElement action = driver.findElement(By.xpath(xpath));
			List<WebElement> actions = action.findElements(By.tagName("a"));
			for (WebElement webElement : actions) {
				String act = webElement.getText();
				links.add(act);
			}
		} catch (NoSuchElementException e) {
			System.err.println("No such element exception : getAllLinkA");
		}
		return links;
	}

	// TODO
	public String[] getConfirm() {
		String confirm[] = new String[2];
		int i = 0;
		try {
			List<WebElement> links = driver.findElements(By.tagName("a"));
			for (WebElement webElement : links) {
				String href = webElement.getAttribute("href");
				if (href != null && href.contains("javascript:;")) {
					confirm[i] = webElement.getText();
					System.out.println("Confirm : " + confirm[i]);
					i++;
				}
			}
		} catch (NoSuchElementException e) {
			System.err.println("No Such element exception : getConfirm");
		}
		return confirm;
	}
	
	public boolean isElementEditable(ArrayList<String> listXpath) {
		try {
			for (String element : listXpath) {
				if (!isElementEditable(element)) {
					return false;
				}
			}
			return true;
		} catch (NoSuchElementException e) {
			addLog("No such element exception");
			return false;
		}
	}
	
	public boolean activeNewUserViaEmail(String host, String email, String emailPassword, String userPassword) {
		addLog("Start active user via email");
		try {
			// Get link active user via email
			String link = MailUtil.getLinkActive(host, email, emailPassword);
			// Open new firefox browser for active user
			WebDriver driver = new FirefoxDriver();
			driver.switchTo().defaultContent();
			driver.manage().window().maximize();
			UsersController userControl = new UsersController(driver);
			driver.get(link);
			// Active user
			userControl.activeUser(email, userPassword);
			if (driver.findElement(By.id("signinId")).getText().equals(email)) {
				addLog("User with email: " + email + " is actived successfully");
				// Close Firefox browser
				driver.quit();
				return true;
			} else {
				addLog("User with email: " + email + " is not actived successfully");
				driver.quit();
				Assert.assertTrue(false);
			}
		} catch (NoSuchElementException e) {
			addLog("No such element");
		}
		return false;
	}
	
	public void activeUser(String email, String password) {
		try {
			// Enter email
			addLog("Enter email: " + email);
			driver.findElement(By.xpath(PageLogin.USERNAME)).clear();
			driver.findElement(By.xpath(PageLogin.USERNAME)).sendKeys(email);
			// Enter password
			addLog("Enter password: " + password);
			driver.findElement(By.xpath(PageLogin.PASSWORD)).clear();
			driver.findElement(By.xpath(PageLogin.PASSWORD)).sendKeys(password);
			// Confirm password
			addLog("Enter confirm password: " + password);
			driver.findElement(By.xpath(PageLogin.CONFIRM_PASSWORD)).clear();
			driver.findElement(By.xpath(PageLogin.CONFIRM_PASSWORD)).sendKeys(password);
			// Click Sign in link
			addLog("Click sign in link");
			driver.findElement(By.xpath(PageLogin.SIGN_IN)).click();
			waitForAjax();
			Thread.sleep(2000);
			waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("No element exception");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean verifyColumnNotContainValue(String xpath, String headerText, String pattern) {
		try {
			clickText("First");
			ArrayList<String> arrayList = getDataByHeaderText(xpath, headerText);
			if (arrayList.size() > 0) {
				for (String item : arrayList) {
					if (item.equals(pattern)) {
						addLog("Column value contains : " + pattern);
						return false;
					}
				}
				addLog("Column value not contain: " + pattern);
				return true;
			} else {
				addLog("No Data for verify");
				return false;
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at verifyValueColumn : " + xpath);
		}
		return false;
	}
	
	@Override
	public void click(String xpath) {
		waitForAjax();
		try {
			addLog("Click : " + xpath);
			driver.findElement(By.xpath(xpath)).click();
			waitForAjax();
			try {
				if (driver.findElement(By.xpath(AddUser.BRAND_PRIVILEGES))
						.isDisplayed()) {
					Thread.sleep(3000);
					waitForAjax();
				}
			} catch (NoSuchElementException e) {
			} catch (InterruptedException e){				
			}
		} catch (NoSuchElementException e) {
			addLog("No element exception : " + xpath);
			Assert.assertTrue(false);
		}
	}
	
}