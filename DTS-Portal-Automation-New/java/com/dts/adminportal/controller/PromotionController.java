package com.dts.adminportal.controller;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.dts.adminportal.model.PromotionList;
import com.dts.adminportal.model.PromotionPackage;

public class PromotionController extends BaseController {
	
	public PromotionController(WebDriver driver) {
		super(driver);
	}
	
	public int getTotalPromotionContainers(String xpath){
		try{
			// Get languages
			List<WebElement> promotions = driver.findElement(By.xpath(xpath)).findElements(By.className("control-group"));
			// Get language index
			int index = promotions.size();
			addLog("The promotion index is: " + index);
			return index;
		}catch(NoSuchElementException e){
			addLog("NoSuchElement " + xpath);
			return 0;
		}
	}
	
	public Boolean addPromotion(Hashtable<String,String> hashXpath, Hashtable<String,String> data){
		try{
		// Enter saleforceID
		editData(hashXpath.get("salesforceid"), data.get("salesforceid"));
		// Select promotion type
		selectOptionByName(hashXpath.get("promotion type"), data.get("promotion type"));
		// Select brand and device
		if(!data.get("promotion type").equals("Global")){
			selectOptionByName(hashXpath.get("device brand"), data.get("device brand"));
			selectOptionByName(hashXpath.get("device name"), data.get("device name"));
		}
		// Enter product 
		editData(hashXpath.get("name"), data.get("name"));
		Thread.sleep(2000);
		autoTool.send("{ENTER}", false);
		waitForAjax();
		// Click save
		if(data.containsKey("save")){
			click(hashXpath.get("save"));
			Thread.sleep(2000);
			waitForAjax();
		}
		return true;
		}catch(NoSuchElementException e){
			addLog("No element is present");
			return false;
		}catch(InterruptedException e){
			e.printStackTrace();
			return false;
		}
	}
	
	// TODO replace below function by generic function in basePage
	public boolean selectAPromotionByName(String xpath, String promotionName) {
		try {
			int size = getPageSize(PromotionList.PROMOTION_TABLE_INFO);
			while (size > 0) {
				// Get table
				WebElement table = driver.findElement(By.xpath(xpath))
						.findElement(By.tagName("tbody"));
				// Get all rows
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				// Find promotion Name
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if ((columns.get(1).getText() + " " + columns.get(2).getText()).equals(promotionName)) {
						// Promotion Name found
						// Select promotion
						addLog("Select Promotion Name: " + promotionName);
						columns.get(0).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				size--;
				if (size == 0) {
					break;
				}
				clickText("Next");
			}
			addLog("Promotion Name: " + promotionName + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return false;
		}
	}
	
	public boolean checkPromotionExistByName(String xpath, String promotionName) {
		try {
			int size = getPageSize(PromotionList.PROMOTION_TABLE_INFO);
			while (size > 0) {
				// Get table
				WebElement table = driver.findElement(By.xpath(xpath))
						.findElement(By.tagName("tbody"));
				// Get all rows
				List<WebElement> rows = table.findElements(By.tagName("tr"));
				// Find promotion Name
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if ((columns.get(1).getText() + " " + columns.get(2).getText()).equals(promotionName)) {
						// Promotion Name found
						addLog("Promotion Name: " + promotionName + " exists");
						return true;
					}
				}
				size--;
				if (size == 0) {
					break;
				}
				clickText("Next");
			}
			addLog("Promotion Name: " + promotionName + " not exist");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return false;
		}
	}
	
	public void deleteADevicePromotionByName(String promotionContainerXpath, String name){
		// Get promotion packages
		ArrayList<PromotionPackage> list = getPromotionPackage(promotionContainerXpath);
		for(PromotionPackage eachPackage : list){
			if(eachPackage.promotionName.getText().contains(name.replace(" ", " - "))){
				addLog("Delete promotion: " + name);
				eachPackage.deleteLink.click();
				selectConfirmationDialogOption("Delete");
				return;
			}
		}
		addErrorLog("Promotion: " + name + " not found");
	}
	
	public ArrayList<PromotionPackage> getPromotionPackage(
			String promotionContainerXpath) {
		ArrayList<PromotionPackage> list = new ArrayList<PromotionPackage>();
			// Get all promotion package
			List<WebElement> promotionPackages = driver.findElement(
					By.xpath(promotionContainerXpath)).findElements(
					By.name("promotion_div"));
			for (WebElement promotionPackage : promotionPackages) {
				PromotionPackage p = new PromotionPackage();
				p.Txt_PromotionID = promotionPackage.findElement(By
						.className("promtiondtsId-box"));
				p.promotionName = promotionPackage.findElement(By
						.name("promotion_product_name"));
				p.warningMessage = promotionPackage.findElement(By
						.name("promotion-error-message"));
				p.deleteLink = promotionPackage.findElement(By
						.linkText("Delete"));
				list.add(p);
			}
			addLog("Number of promotion package is: " + list.size());
		return list;
	}
	
	public void selectProductInPromotionList(String name) {
		try {
			int size = getPageSize(PromotionList.PROMOTION_TABLE_INFO);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(2).getText().equals(name)) {
						addLog("Select product: " + name);
						columns.get(2).findElement(By.tagName("a")).click();
						waitForAjax();
						return;
					}
				}
				clickText("Next");
			}
			addLog("Product: " + name + " not found");
		} catch (NoSuchElementException e) {
			addLog("No element exception");
		}
	}
	
	// TODO remove below method and use other existed method
	public String selectAPromotion() {
		String promotionName = null;
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));
				// Select promotion
				promotionName = columns.get(1).getText() + " " + columns.get(2).getText();
				addLog("Select promotion: " + promotionName);
				columns.get(0).findElement(By.tagName("a")).click();
				waitForAjax();
				break;
			}
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return null;
		}
		return promotionName;
	}
	
	// TODO make generic and move to BaseController or move to wrapper class - TableElement
	public Boolean selectAnItemOnTable() {
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				WebElement link = row.findElement(By.tagName("a"));
				link.click();
				waitForAjax();
				break;
			}
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return false;
		}
		return true;
	}

	public Boolean selectBrandFilterByClickOnLink(String xpath, String brandOption) {
		try {
			// Get table
			WebElement table = driver.findElement(By.xpath(xpath)).findElement(By.tagName("tbody"));
			// Get all rows
			List<WebElement> rows = table.findElements(By.tagName("tr"));
			// Find brand name link
			for (WebElement row : rows) {
				WebElement brand_link = row.findElements(By.tagName("td")).get(1);
				if (brand_link.getText().equals(brandOption)) {
					// Brand filter option found
					// Select brand filter
					addLog("Click on brand name link: " + brandOption);
					brand_link.findElement(By.tagName("a")).click();
					waitForAjax();
					return true;
				}
			}
			addLog("Brand name link: " + brandOption + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return false;
		}
	}
	
	
}
