package com.dts.adminportal.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenLocation;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;

import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.util.FileUtil;


public class ProductController extends BaseController {
	
	public ProductController(WebDriver driver) {
		super(driver);
	}
	
	public boolean addProduct(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		boolean result = true;
		try {
			waitForAjax();
			if (isElementPresent(hashXpath.get("id"))) {
				editData(hashXpath.get("id"), data.get("id"));
			}
			if (isElementPresent(hashXpath.get("company"))) {
				selectOptionByName(hashXpath.get("company"),
						data.get("company"));
			}
			if (isElementPresent(hashXpath.get("brand"))) {
				selectOptionByName(hashXpath.get("brand"), data.get("brand"));
			}
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("number"), data.get("number"));
			editData(hashXpath.get("ean"), data.get("ean"));
			editData(hashXpath.get("upc"), data.get("upc"));
			selectOptionByName(hashXpath.get("type"), data.get("type"));
			selectInputChannel(hashXpath.get("wired"), data.get("wired"));
			selectInputChannel(hashXpath.get("bluetooth"),data.get("bluetooth"));
			editData(hashXpath.get("description"), data.get("description"));
			// upload tuning file
			uploadFile(hashXpath.get("add tunning"), data.get("add tunning"));
			// Select tuning rating
			if (isElementPresent(hashXpath.get("tuning rating"))
					&& data.get("tuning rating") != null) {
				selectOptionByName(hashXpath.get("tuning rating"),
						data.get("tuning rating"));
			}
			// upload image file
			uploadFile(hashXpath.get("img1"), data.get("img1"));
			uploadFile(hashXpath.get("img2"), data.get("img2"));
			uploadFile(hashXpath.get("img3"), data.get("img3"));
			// upload marketing file
			if (data.get("add marketing") != ""
					&& data.get("add marketing") != null) {
				uploadFile(hashXpath.get("add marketing"),
						data.get("add marketing"));
				selectFirstOption(hashXpath.get("marketing meterial type"));

			}
			// save
			if (data.containsKey("save")) {
				click(hashXpath.get("save"));
			}
		} catch (NoSuchElementException e) {
			addLog(e.toString());
			result = false;
		}
		return result;
	}
	
	public boolean addVariant(Hashtable<String, String> hashXpath,
			Hashtable<String, String> data) {
		try {
			waitForAjax();
			if(isElementPresent(hashXpath.get("id"))){
				editData(hashXpath.get("id"), data.get("id"));
			}
			editData(hashXpath.get("name"), data.get("name"));
			editData(hashXpath.get("ean"), data.get("ean"));
			editData(hashXpath.get("upc"), data.get("upc"));
			editData(hashXpath.get("descriptor"), data.get("descriptor"));
			// Upload tuning
			if (data.get("add tunning") != null) {
				if (data.get("add tunning") == "use parent") {
					click(AddEditProductModel.PARENT_TUNNING_LINK);
				} else {
					click(AddEditProductModel.UPLOAD_CUSTOM_TUNNING);
					uploadFile(hashXpath.get("add tunning"),
							data.get("add tunning"));
				}
			}
			// Select tuning rating
			if(isElementPresent(hashXpath.get("tuning rating")) && data.get("tuning rating") != null){
				selectOptionByName(hashXpath.get("tuning rating"), data.get("tuning rating"));
			}
			// Upload image
			if (data.get("img1") != null) {
				if (data.get("img1") == "use parent") {
					click(AddEditProductModel.PARENT_IMAGE_LINK);
				} else {
					click(AddEditProductModel.UPLOAD_CUSTOM_IMAGE);
					uploadFile(hashXpath.get("img1"), data.get("img1"));
					uploadFile(hashXpath.get("img2"), data.get("img2"));
					uploadFile(hashXpath.get("img3"), data.get("img3"));
				}
			}
			// Upload marketing
			if (data.get("add marketing") != ""
					&& data.get("add marketing") != null) {
				uploadFile(hashXpath.get("add marketing"),
						data.get("add marketing"));
				selectFirstOption(hashXpath.get("marketing meterial type"));
			}
			// Click save link
			if(data.containsKey("save")){
				click(hashXpath.get("save"));
			}
			return true;
		} catch (NoSuchElementException e) {
			addLog(e.toString());
			return false;
		}
	}
	
	public boolean selectProductByName(String name) {
		try {
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						addLog("Select accessory : " + name);
						columns.get(1).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}
	
	public Integer getProductIdByName(String name){
		try {
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			addLog("Product page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						addLog("Select accessory : " + name);
						String id = columns.get(1).findElement(By.tagName("a")).getAttribute("value");
						System.out.println(id);
						return Integer.valueOf(id);
					}
				}
				clickText("Next");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			addErrorLog("Product: " + name + " not found");
			return 0;
		}
		return 0;
	}
	
	public Boolean selectVariantbyName(String name) {
		try {
			// Get the variant container
			WebElement model_variant = driver.findElement(By.xpath(ProductDetailModel.MODEL_VARIANTS)).findElement(By.tagName("span"));
			// Get all variant
			List<WebElement> variants = model_variant.findElements(By.tagName("a"));
			for (WebElement variant : variants) {
				// Get the text from link
				if (variant.getText().equals(name)) {
					addLog("Select variant: " + name);
					variant.click();
					waitForAjax();
					return true;
				}
			}
			addLog("Variant: " + name + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException: " + ProductDetailModel.MODEL_VARIANTS);
			return false;
		}
	}
	
	public Integer getVariantIdbyName(String name) {
		try {
			// Get the variant container
			WebElement model_variant = driver.findElement(By.xpath(ProductDetailModel.MODEL_VARIANTS)).findElement(By.tagName("span"));
			// Get all variant
			List<WebElement> variants = model_variant.findElements(By
					.tagName("a"));
			for (WebElement variant : variants) {
				// Get the text from link
				System.out.println(variant.getText());
				if (variant.getText().contains(name)) {
					addLog("Select variant: " + name);
					String variantId = variant.getAttribute("id");
					System.out.println(variantId);
					Integer result = Integer.valueOf(variantId.split(",")[0]); 
					System.out.println(result);
					return result;
				}
			}
			addLog("Variant: " + name + " not found");
			return 0;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException: " + ProductDetailModel.MODEL_VARIANTS);
			return 0;
		}
	}

	// TODO  
	public String selectAnAccessory() {
		String accessoryName = "";
		try {
			// get table
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			// get a row
			WebElement row = tbody.findElements(By.tagName("tr")).get(0);
			// Select an accessory
			WebElement link = row.findElement(By.tagName("a"));
			addLog("Select accessory: " + link.getText());
			accessoryName = link.getText();
			link.click();
			waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("No element exception");
			return null;
		}
		return accessoryName;
	}
	
	public Boolean isMarketingUploaded(String xpath, String filename) {
		try {
			List<WebElement> containers = driver.findElement(By.xpath(xpath))
					.findElements(By.tagName("tbody"));
			for (WebElement marketingFile : containers) {
				if (marketingFile.findElement(By.className("edit-media-space"))
						.getText().contains(filename)) {
					addLog("Marketing file: " + filename
							+ " is uploaded successfully");
					return true;
				}
			}
			addLog("Marketing file: " + filename
					+ " is not uploaded successfully");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
			return null;
		}
	}
	
	public Boolean isTunningUploaded(String xpath, String filename) {
		try {
			WebElement tuningUploaded = driver.findElement(By.xpath(xpath));
			if (tuningUploaded.getText().contains(filename) && tuningUploaded.getText().contains("File upload successful")){
				addLog("Tuning: " + filename
						+ " is uploaded successfully");
				return true;
			}
			addLog("Tuning: " + filename + " is not uploaded successfully");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElemet: " + xpath);
			return null;
		}
	}

	public Boolean isImageUploaded(String xpath, String imagename) {
		try {
			WebElement image = driver.findElement(By.xpath(xpath)).findElement(By.tagName("img"));
			if(image.getAttribute("src").contains(imagename.replaceAll(".jpg", ""))){
				addLog("Image: " + imagename + " is uploaded successfully");
				return true;
			}
			addLog("Image: " + imagename + " is not uploaded successfully");
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
		}
		return false;
	}
	
	public void deleteAllProductWithPublishedStatus(String status){
		selectFilterByName(PageHome.accessoryFilterSelect, status);
		boolean item = isElementPresent(ProductModel.PRODUCT_TABLE);
		while(item){
			selectAnAccessory();
			doDelete(ProductDetailModel.DELETE);
			selectFilterByName(PageHome.accessoryFilterSelect, status);
			item = isElementPresent(ProductModel.PRODUCT_TABLE);
		}
		addLog("All product with status: " + status + " are deleted");
	}

	public boolean checkMaketingActionsDisplayed() {
		boolean chechMaketing = false;
		WebElement e = driver.findElement(By.id("marketing-action-id"));
		List<WebElement> e1 = e.findElements(By.tagName("a"));
		for (int i = 0; i < e1.size(); i++) {
			String tmp = e1.get(i).getText();
			if (tmp.equalsIgnoreCase("")) {
				chechMaketing = true;
			} else {
				return false;
			}
		}
		return chechMaketing;
	}
	
	// TODO
	public boolean checkAnAccessoryExistByName(String name) {
		try {
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						addLog("Product: " + name + " exists");
						return true;
					}
				}
				clickText("Next");
			}
			addLog("Product: " + name + " not exist");
		} catch (NoSuchElementException e) {
			addLog("No Element is present");
		}
		return false;
	}
	
	public int getTotalLanguageContainers(String xpath){
		try{
			// Get languages
			List<WebElement> languages = driver.findElement(By.xpath(xpath)).findElements(By.tagName("div"));
			// Get language index
			int index = languages.size();
			addLog("The language index is: " + index);
			return index;
		}catch(NoSuchElementException e){
			addLog("NoSuchElement " + xpath);
			return 0;
		}
	}
	
	// TODO need to update below method
	public boolean downloadPublishedAccessoryProfile() {
		try {
			// Get id of published accessory
			String id = getElementText(driver.findElement(By.xpath("//*[@id='dts-tracking-id' or @id='dts-id']")));
			String file_path = System.getProperty("user.home") + File.separator + "Downloads" + File.separator+"profile_" + id + ".dtscs";
			// Delete file if exist
			if (FileUtil.FileExist(file_path)) {
				FileUtil.DeleteFile(file_path);
			}
			// Download Published Accessory Profile
			addLog("Start download Published Accessory Profile:" + id);
			click(driver.findElement(By.xpath("//*[@id='publishedAccessoryProfile' or @id='publishedVariantProfile']")));
			handleFirefoxDownloadDialog("Save File");
			for (int i = 1; i <= 10; i++) {
				Thread.sleep(1000);
				if (FileUtil.FileExist(file_path)) {
					addLog("Profile: " + id + " is downloaded successfully");
					return true;
				}
			}
			addLog("Profile: " + id + " is not downloaded successfully");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No such element exception");
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unused")
	
		
	public boolean checkVariantExistbyName(String xpath, String name) {
		try {
			// Get variant container
			WebElement model_variant = driver.findElement(By.xpath(xpath));
			// Get all variants
			List<WebElement> variants = model_variant.findElement(By
					.tagName("span")).findElements(By.tagName("a"));
			for (WebElement variant : variants) {
				// Get the text from link
				if (variant.getText().equals(name)) {
					addLog("Variant: " + name + " exists");
					return true;
				}
			}
			addLog("Variant: " + name + " not exist");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException: " + xpath);
			return false;
		}
	}
	
	public void clickImage(String imageName){
		try {
			Thread.sleep(2000);
			ScreenRegion s = new DesktopScreenRegion();
			Mouse m = new DesktopMouse();
			File path = new File(System.getProperty("user.dir") + "\\asset");
			ScreenRegion image = s.find(new ImageTarget(new File(path
					+ "\\" +  imageName)));
			if(image == null){
				image = scrollMouseUntilImageVisible(imageName);
			}
			m.click(image.getCenter());
			waitForAjax();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public ScreenRegion scrollMouseUntilImageVisible(String imageName) {
		try {
			int i = 0;
			ScreenRegion sreen = new DesktopScreenRegion();
			Mouse mouse = new DesktopMouse();
			ScreenRegion image = null;
			Target target = null;
			File path = new File(System.getProperty("user.dir") + "\\asset");
			target = new ImageTarget(new File(path + "\\" + imageName));
			target.setMinScore(0.9);
			image = sreen.find(target);
			while (image == null && i < 10) {
				mouse.wheel(1, 3);
				Thread.sleep(300);
				image = sreen.find(target);
				i++;
			}
			return image;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// TODO rename, modify and move to baseController
	public boolean verifyValueColumn(String xpath, String headerText, ArrayList<String> listPattern) {
		try {
			driver.findElement(By.linkText("First")).click();
			waitForAjax();
			// get size page
			int size = getPageSize();
			for (int i = 0; i < size; i++) {
				waitForAjax();
				ArrayList<String> arrayList = getDataByHeaderText(xpath,
						headerText);
				if (arrayList.size() > 0) {
					for (String item : arrayList) {
						if (!listPattern.contains(item)) {
							addLog("Status is not correct : " + item);
							return false;
						}
					}
				} else {
					addLog("No Data!!!");
				}
				driver.findElement(By.linkText("Next")).click();
			}
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException at verifyValueColumn : "  + xpath);
			return false;
		}
		return true;
	}
	
	// TODO Can we replace the following function with other function? More generic and reusable
	public Boolean canEditHeadphoneRating(String xpath) {
		try {
			List<WebElement> inputs = driver.findElements(By.xpath(xpath));
			if (inputs.size() > 1) {
				return false;
			} else {
				return canEdit(xpath);
			}
		} catch (NoSuchElementException e) {
			System.err.println("No element exception");
		}
		return false;
	}
	

	public static boolean testInet() {
		Socket sock = new Socket();
		InetSocketAddress addr = new InetSocketAddress("devportal.dts.com", 80);
		try {
			sock.connect(addr, 3000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
			}
		}
	}
	
	public void connectNetwork() {
		autoTool.releaseRenewNetwork(Constant.netWorkState.RENEW.getName());
	}
	
	public void interuptNetwork() {
		autoTool.releaseRenewNetwork(Constant.netWorkState.RELEASE.getName());
	}
	
	// TODO Can we make it generic and reusable with other table
	public int getNumElementDisplayedByText(String text) {
		int i = 0;
		try {
			List<WebElement> elements = driver.findElements(By.tagName("input"));
			for (WebElement webElement : elements) {
				if (webElement.getAttribute("placeholder").contains(text)) {
					i++;
				}
			}
		} catch (NoSuchElementException e) {
		}
		return i;
	}
	
	// TODO move to wrapper class or make generic and move to Base Controller
	public boolean selectAccessorybyName(String name) {
		try {
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					addLog("product name: " + columns.get(1).getText());
					if (columns.get(1).getText().equals(name)) {
						addLog("Select accessory : " + name);
						columns.get(1).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			return false;
		}
		return false;
	}
	
	public boolean deleteAccessorybyName(String name) {
		click(PageHome.linkAccessories);
		selectAccessorybyName(name);
		doDelete(ProductDetailModel.DELETE);
		try {
			if (driver.findElement(By.xpath(ProductModel.PRODUCT_TABLE)).isDisplayed()) {
				addSuccessLog("Product: " + name + " is deleted successfully");
				return true;
			}
			addErrorLog("Product: " + name + " is not deleted successfully");
			return false;
		} catch (NoSuchElementException e) {
			addErrorLog("Product: " + name + " is not deleted successfully");
			return false;
		}
	}
	
	// TODO 
	public boolean isItemInorderList(String xpath, String[] sizes) {
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			element = element.findElement(By.tagName("tbody"));
			// get all rows
			List<WebElement> rows = element.findElements(By.tagName("tr"));
			// get list colums of first rows
			List<WebElement> colums = rows.get(0).findElements(By.tagName("td"));
			if (colums.size() == 3) {
				// verify size in order 160->290->566
				if (colums.get(0).getText().equals(sizes[0]) && colums.get(1).getText().equals(sizes[1])
						&& colums.get(2).getText().equals(sizes[2])) {
					return true;
				}
			}
		} catch (NoSuchElementException e) {
		}
		return false;
	}
	
	public boolean selectAVariantbyName(String name) {
		try {
			// Get the variant container
			WebElement model_variant = driver.findElement(By.xpath(ProductDetailModel.MODEL_VARIANTS))
					.findElement(By.tagName("span"));
			// Get all variant
			List<WebElement> variants = model_variant.findElements(By.tagName("a"));
			for (WebElement variant : variants) {
				// Get the text from link
				if (variant.getText().equals(name)) {
					addLog("Select variant: " + name);
					variant.click();
					waitForAjax();
					return true;
				}
			}
			addLog("Variant: " + name + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElementException: " + ProductDetailModel.MODEL_VARIANTS);
			return false;
		}
	}
	
	
	
	
	
	
}
