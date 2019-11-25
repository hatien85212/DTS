package com.dts.adminportal.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopMouse;
import org.testng.Reporter;

import com.dts.adminportal.model.AccessoryVariants;
import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.Constant;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.ProductDetailModel;
import com.dts.adminportal.model.ProductModel;
import com.dts.adminportal.util.FileUtil;
import com.dts.pb.coef.CoefAeq2.Aeq;
import com.dts.pb.coef.CoefAeq2.Aeq.Builder;
import com.dts.pb.dcc.Accessorypb.Accessory;
import com.dts.pb.dcc.Specpb.OutputChain;
import com.dts.pb.dcc.dtscs.Dtscs;
import com.dts.pb.eagle_v1_1.Eagle_v1_1.Postmix;
import com.dts.pb.eagle_v1_1.Eagle_v1_1.Premix;
import com.dts.pb.model.Listeningmodel.headphoneEq;
import com.dts.pb.model.Listeningmodel.listeningModel;

import net.sourceforge.htmlunit.corejs.javascript.ast.SwitchCase;

public class ProductController extends BaseController {

	public ProductController(WebDriver driver) {
		super(driver);
	}

	public boolean addProduct(Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
		boolean result = true;
		try {
			waitForAjax();
			if (isElementPresent(hashXpath.get("id"))) {
				editData(hashXpath.get("id"), data.get("id"));
			}
			if (isElementPresent(hashXpath.get("company"))) {
				selectOptionByName(hashXpath.get("company"), data.get("company"));
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
			selectInputChannel(hashXpath.get("bluetooth"), data.get("bluetooth"));
			selectInputChannel(hashXpath.get("usb"), data.get("usb"));
			editData(hashXpath.get("description"), data.get("description"));
			// upload tuning file
			uploadFile(hashXpath.get("add tunning"), data.get("add tunning"));
			// Select tuning rating
			if (isElementPresent(hashXpath.get("tuning rating")) && data.get("tuning rating") != null) {
				selectOptionByName(hashXpath.get("tuning rating"), data.get("tuning rating"));
			}
			// upload image file
			uploadFile(hashXpath.get("img1"), data.get("img1"));
			uploadFile(hashXpath.get("img2"), data.get("img2"));
			uploadFile(hashXpath.get("img3"), data.get("img3"));
			// upload marketing file
			if (data.get("add marketing") != "" && data.get("add marketing") != null) {
				uploadFile(hashXpath.get("add marketing"), data.get("add marketing"));
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

	public boolean addVariant(Hashtable<String, String> hashXpath, Hashtable<String, String> data) {
		try {
			waitForAjax();
			if (isElementPresent(hashXpath.get("id"))) {
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
					uploadFile(hashXpath.get("add tunning"), data.get("add tunning"));
				}
			}
			// Select tuning rating
			if (isElementPresent(hashXpath.get("tuning rating")) && data.get("tuning rating") != null) {
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
			if (data.get("add marketing") != "" && data.get("add marketing") != null) {
				uploadFile(hashXpath.get("add marketing"), data.get("add marketing"));
				selectFirstOption(hashXpath.get("marketing meterial type"));
			}
			// Click save link
			if (data.containsKey("save")) {
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
			Thread.sleep(5000);
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						addLog("Select accessory : " + name);
						columns.get(1).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
				// clickText("Previous");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		return false;
	}

	public Integer getProductIdByName(String name) {
		try {
			Thread.sleep(5000);
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			addLog("Product page size: " + size);
			clickText("Last");
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						addLog("Select accessory : " + name);
						String id = columns.get(1).findElement(By.tagName("a")).getAttribute("value");
						System.out.println(id);
						return Integer.valueOf(id);
					}
				}
				// clickText("Next");
				clickText("Previous");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			addErrorLog("Product: " + name + " not found");
			return 0;
		} catch (InterruptedException e) {
			return 0;
		}
		return 0;
	}

	public Integer getProductIdByCharacter(String name) {
		try {
			Thread.sleep(2000);
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			addLog("Product page size: " + size);
			// clickText("Last");
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(1).getText().contains(name)) {
						addLog("Select accessory : " + columns.get(1).getText());
						String id = columns.get(1).findElement(By.tagName("a")).getAttribute("value");
						System.out.println(id);
						return Integer.valueOf(id);
					}
				}
				clickText("Next");
				// clickText("Previous");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			addErrorLog("Product: " + name + " not found");
			return 0;
		} catch (InterruptedException e) {
			return 0;
		}
		return 0;
	}

	public Boolean selectVariantbyName(String name) {
		try {
			// Get the variant container
			WebElement model_variant = driver.findElement(By.xpath(ProductDetailModel.LIST_VARIANT))
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

	public Integer getVariantIdbyName(String name) {
		try {
			// Get the variant container
			WebElement model_variant = driver.findElement(By.xpath("//*[@id='model-variants']"))
					.findElement(By.tagName("span"));
			// Get all variant
			List<WebElement> variants = model_variant.findElements(By.tagName("a"));
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
			List<WebElement> containers = driver.findElement(By.xpath(xpath)).findElements(By.tagName("tbody"));
			for (WebElement marketingFile : containers) {
				if (marketingFile.findElement(By.className("edit-media-space")).getText().contains(filename)) {
					addLog("Marketing file: " + filename + " is uploaded successfully");
					return true;
				}
			}
			addLog("Marketing file: " + filename + " is not uploaded successfully");
			return false;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement: " + xpath);
			return null;
		}
	}

	public Boolean isTunningUploaded(String xpath, String filename) {
		try {
			WebElement tuningUploaded = driver.findElement(By.xpath(xpath));
			if (tuningUploaded.getText().contains(filename)
					&& tuningUploaded.getText().contains("File upload successful")) {
				addLog("Tuning: " + filename + " is uploaded successfully");
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
			if (image.getAttribute("src").contains(imagename.replaceAll(".jpg", ""))) {
				addLog("Image: " + imagename + " is uploaded successfully");
				return true;
			}
			addLog("Image: " + imagename + " is not uploaded successfully");
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
		}
		return false;
	}

	public void deleteAllProductWithPublishedStatus(String status) {
		selectFilterByName(PageHome.accessoryFilterSelect, status);
		// boolean item = isElementPresent(ProductModel.PRODUCT_TABLE);
		int totalItem = getTotalElement(PageHome.BRAND_ACCESSORY_TABLE_INFO);
		while (totalItem > 0) {
			selectAnAccessory();
			doDelete(ProductDetailModel.DELETE);
			selectFilterByName(PageHome.accessoryFilterSelect, status);
			// item = isElementPresent(ProductModel.PRODUCT_TABLE);
			totalItem--;
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
					List<WebElement> columns = row.findElements(By.tagName("td"));
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

	public int getTotalLanguageContainers(String xpath) {
		try {
			// Get languages
			List<WebElement> languages = driver.findElement(By.xpath(xpath)).findElements(By.tagName("div"));
			// Get language index
			int index = languages.size();
			addLog("The language index is: " + index);
			return index;
		} catch (NoSuchElementException e) {
			addLog("NoSuchElement " + xpath);
			return 0;
		}
	}

	// // TODO need to update below method
	// public boolean downloadPublishedAccessoryProfile() {
	// try {
	// // Get id of published accessory
	// String id =
	// getElementText(driver.findElement(By.xpath("//*[@id='dts-tracking-id' or
	// @id='dts-id']")));
	// String file_path = System.getProperty("user.home") + File.separator +
	// "Downloads" + File.separator+"profile_" + id + ".dtscs";
	// // Delete file if exist
	// if (FileUtil.FileExist(file_path)) {
	// FileUtil.DeleteFile(file_path);
	// }
	// // Download Published Accessory Profile
	// addLog("Start download Published Accessory Profile:" + id);
	// click(driver.findElement(By.xpath("//*[@id='publishedAccessoryProfile' or
	// @id='publishedVariantProfile']")));
	// handleFirefoxDownloadDialog("Save File");
	// for (int i = 1; i <= 10; i++) {
	// Thread.sleep(1000);
	// if (FileUtil.FileExist(file_path)) {
	// addLog("Profile: " + id + " is downloaded successfully");
	// return true;
	// }
	// }
	// addLog("Profile: " + id + " is not downloaded successfully");
	// return false;
	// } catch (NoSuchElementException e) {
	// addLog("No such element exception");
	// return false;
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// return false;
	// }
	// }

	public boolean checkVariantExistbyName(String xpath, String name) {
		try {
			// Get variant container
			WebElement model_variant = driver.findElement(By.xpath(xpath));
			// Get all variants
			List<WebElement> variants = model_variant.findElement(By.tagName("span")).findElements(By.tagName("a"));
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

	public void clickImage(String imageName, String type) {
		try {
			ScreenRegion screen = new DesktopScreenRegion();
			Mouse mouse = new DesktopMouse();
			File path = new File(System.getProperty("user.dir") + "\\asset");
			ScreenRegion image = null;
			// ScreenRegion image = s.find(new ImageTarget(new File(path
			// + "\\" + imageName)));

			Target imageTarget = new ImageTarget(new File(path + "\\" + imageName));
			imageTarget.setMinScore(0.9);
			image = screen.wait(imageTarget, 300);

			if (image == null) {
				image = scrollMouseUntilImage(imageName, type);
				System.out.println("null");
			}
			Thread.sleep(500);
			mouse.click(image.getCenter());
			waitForAjax();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void clickSaveLinkWhileUploading(String imageName, String imageSave, String type) {
		try {
			Thread.sleep(300);
			ScreenRegion screen = new DesktopScreenRegion();
			Mouse m = new DesktopMouse();
			File path = new File(System.getProperty("user.dir") + "\\asset");
			Target imageTarget = new ImageTarget(new File(path + "\\" + imageName));
			imageTarget.setMinScore(0.9);
			ScreenRegion image = screen.wait(imageTarget, 300);

			Target saveTarget = new ImageTarget(new File(path + "\\" + imageSave));
			saveTarget.setMinScore(0.9);
			ScreenRegion save = screen.wait(saveTarget, 300);
			if (image == null) {
				image = scrollMouseUntilImage(imageName, type);
				System.out.println("null");
			}
			// int i = 0;
			// while (i < 5 && image == null) {
			// i++;
			// Thread.sleep(1000);
			// }
			m.click(save.getCenter());
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
			autoTool.mouseWheel("up", 2);
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

	public ScreenRegion scrollMouseUntilImage(String imageName, String type) {
		try {
			int i = 0;
			ScreenRegion sreen = new DesktopScreenRegion();
			// Mouse mouse = new DesktopMouse();
			Robot robot = new Robot();
			robot.mouseMove(400, 400);
			ScreenRegion image = null;
			Target target = null;
			// autoTool.mouseWheel("up", 3);
			File path = new File(System.getProperty("user.dir") + "\\asset");
			target = new ImageTarget(new File(path + "\\" + imageName));
			target.setMinScore(0.9);
			image = sreen.find(target);
			while (image == null && i < 5) {
				if (type == "hpxtt") {
					autoTool.mouseWheel("up", 3);
				} else {
					autoTool.mouseWheel("down", 3);
				}
				Thread.sleep(700);
				image = sreen.find(target);
				i++;
			}
			return image;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} catch (AWTException e) {
			// TODO Auto-generated catch block
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
				ArrayList<String> arrayList = getDataByHeaderText(xpath, headerText);
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
			addLog("NoSuchElementException at verifyValueColumn : " + xpath);
			return false;
		}
		return true;
	}

	// TODO Can we replace the following function with other function? More
	// generic and reusable
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
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void interuptNetwork() {
		autoTool.releaseRenewNetwork(Constant.netWorkState.RELEASE.getName());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
			Thread.sleep(4000);
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			addLog("page size: " + size);
			clickText("Last");
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
				clickText("Previous");
				// clickText("Next");
			}
			addErrorLog("Accessory: " + name + " not found");
		} catch (NoSuchElementException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		return false;
	}

	public boolean checkValueOfProductTable(String name, String UUID, int num) {
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
						waitForAjax();
						if (columns.get(num).getText().equals(UUID)) {
							return true;
						}
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
			WebElement model_variant = driver.findElement(By.xpath(ProductDetailModel.LIST_VARIANT))
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
			addLog("NoSuchElementException: " + ProductDetailModel.LIST_VARIANT);
			return false;
		}
	}

	public boolean selectAccessoryContainsString(String name) {
		try {
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					addLog("product name: " + columns.get(1).getText());
					if (columns.get(1).getText().contains(name)) {
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

	public boolean isNeedsAttention(ArrayList<String> Array_Tuning, ArrayList<String> Array_Market,
			ArrayList<String> Array_Rating) {

		boolean flag = true;
		System.out.println(Array_Tuning);
		System.out.println(Array_Market);
		int size = Array_Tuning.size();
		for (int i = 0; i < size; i++) {
			if ((Array_Tuning.get(i).equals("Revoked")) && (Array_Market.get(i).equals("Unsubmitted"))) {
				Reporter.log("Passed Tuning Revoked - Marketing Unsubmitted");
			} else if ((Array_Tuning.get(i).equals("Approved")) && (Array_Market.get(i).equals("Revoked"))) {
				System.out.println("Passed Tuning Approved - Marketing Revoked");
			} else {
				if ((Array_Tuning.get(i).equals("Approved")) && (Array_Market.get(i).equals("Declined"))) {
					System.out.println("Passed Tuning Approved - Marketing Declined");
				}

				else {
					if ((Array_Tuning.get(i).equals("DTS Declined")) && (Array_Market.get(i).equals("Unsubmitted"))) {
						System.out.println("Passed Tuning DTS Declined - Marketing Unsubmitted");
					} else {
						if ((Array_Tuning.get(i).equals("Partner Declined"))
								&& (Array_Market.get(i).equals("Unsubmitted"))) {
							System.out.println("Passed Tuning Partner Declined - Marketing Unsubmitted");
						} else {
							if ((Array_Tuning.get(i).equals("Revoked"))
									&& (Array_Rating.get(i).equals("DTS Uploaded"))) {
								System.out.println("Passed Tuning Revoked - Marketing DTS Uploaded");
							} else {
								if ((Array_Tuning.get(i).equals("Partner Declined"))
										&& (Array_Market.get(i).equals("DTS Uploaded"))) {
									System.out.println("Passed Tuning Partner Declined - Marketing DTS Uploaded");
								} else {
									if ((Array_Tuning.get(i).equals("Partner Declined"))
											&& (Array_Market.get(i).equals("DTS Uploaded"))) {
										System.out.println("Passed Tuning Partner Declined - Marketing DTS Uploaded");
									} else
										flag = false;
								}
							}
						}
					}
				}
			}
		}
		return flag;
	}

	// public boolean isConvertedFile(String fileName){
	// boolean flag = false;
	// File dir = new File(System.getProperty("user.home") + File.separator
	// +"Downloads");
	// File[] dir_contents = dir.listFiles();
	// System.out.println(fileName);
	// for (int i = 0; i < dir_contents.length; i++) {
	// if(dir_contents[i].getName().contains(fileName+".part"))
	// {
	// System.out.println(dir_contents[i].getName()+" is existed");
	// return flag=false;
	// }
	// else flag = true;
	// }
	// return flag;
	// }

	public void convertDtscsText(String file) {
		String path = System.getProperty("user.dir") + File.separator + "tools" + File.separator;
		String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator;
		System.out.println(path);
		System.out.println(filePath);
		String fileExt = file.substring(file.lastIndexOf("."), file.length());
		String fileName = file.replaceAll(fileExt, "");
		String output = fileName + ".txt";
		File tempText = new File(path + output);
		if (tempText.exists()) {
			tempText.delete();
		}
		Runtime rt = Runtime.getRuntime();
		try {
			String command = "cmd.exe /c " + path + "dts-dtscs-dump.exe " + filePath + file + " > " + filePath + output;
			System.out.println(command);
			rt.exec(command);
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public boolean downloadProfile() {
		boolean flag = false;
		// Click on download link
		click(ProductDetailModel.PUBLISHED_ACCESSORY_PROFILE);
		// Get file name generated when download to local
		autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
		String file_name_download = autoTool.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
		// Check if file exist and delete
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
		System.out.println("File path: " + file_path);
		File file = new File(file_path);
		if (file.exists()) {
			file.delete();
		}
		// Download file

		addLog("Start download file: " + file_name_download);
		handleFirefoxDownloadDialog("Save File");
		int i = 0;
		while (i < 10) {
			try {
				Thread.sleep(2000);
				if (file.exists()) {
					addLog("File: " + file_name_download + " is downloaded successfully");
					i = 11;
					flag = true;
				} else {
					addLog("File: " + file_name_download + " is not downloaded successfully");
					i++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		return flag;
	}

	public boolean downloadVariantProfile() {
		boolean flag = false;
		// Click on download link
		click(ProductDetailModel.PUBLISHED_VARIANT_PROFILE);
		// Get file name generated when download to local
		autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
		String file_name_download = autoTool.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
		// Check if file exist and delete
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
		System.out.println("File path: " + file_path);
		File file = new File(file_path);
		if (file.exists()) {
			file.delete();
		}
		// Download file

		addLog("Start download file: " + file_name_download);
		handleFirefoxDownloadDialog("Save File");
		int i = 0;
		while (i < 10) {
			try {
				Thread.sleep(1000);
				if (file.exists()) {
					addLog("File: " + file_name_download + " is downloaded successfully");
					i = 11;
					flag = true;
				} else {
					addLog("File: " + file_name_download + " is not downloaded successfully");
					i++;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		return flag;
	}

	public String getProfileName() {
		String name = "profile_" + getTextByXpath(ProductDetailModel.DTS_TRACKING_ID) + ".dtscs";
		return name;
	}

	public StringBuilder getLineContainFromFile(String fileName, String key) {
		String fileExt = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		String name = fileName.replaceAll(fileExt, "");
		String fileText = name + ".txt";
		String filePath = System.getProperty("user.home") + "\\Downloads\\" + fileText;
		System.out.println(filePath);
		File file = new File(filePath);
		StringBuilder builder = new StringBuilder();
		System.out.println(filePath);
		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader(file));
				String line;
				int i = 1;
				while ((line = reader.readLine()) != null) {
					if (line.contains(key)) {
						builder.append("\n" + i + line);
						i++;
					}
				}
				builder.append("\nend");
				System.out.println("All lines contain " + key);
				System.out.println(builder);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println(file.getName());
			throw new RuntimeException("File not found " + file.getAbsolutePath());
		}
		return builder;
	}

	public boolean checkValueBypass(String fileName, String lineVal, String blueVal, String type) {
		StringBuilder b = getLineContainFromFile(fileName, ProductDetailModel.AEQKeys[0]);
		boolean flag = false;
		char charArray[] = new char[b.length()];
		ArrayList<String> result = new ArrayList<String>();
		if (type.equals(ProductDetailModel.TuningTypes.BlueToothOnly.getName())) {
			b.getChars(b.indexOf("1"), b.indexOf("7"), charArray, 0);
			System.out.println("Values of bypass_hpeq for " + type);
			System.out.println(charArray);
			for (int i = 1; i < 7; i++) {
				int index = b.indexOf(String.valueOf(i + 1)) - 2;
				result.add(String.valueOf(b.charAt(index)));
			}
			for (int j = 0; j < result.size(); j++) {
				System.out.println(result.get(j));
				if (result.get(j).equals(blueVal)) {
					flag = true;
				}
			}
		}

		if (type.equals(ProductDetailModel.TuningTypes.LineOutOnly.getName())) {
			b.getChars(b.indexOf("1"), b.indexOf("end"), charArray, 0);
			System.out.println("Values of  bypass_hpeq for " + type);
			System.out.println(charArray);
			for (int i = 1; i < 6; i++) {
				int index = b.indexOf(String.valueOf(i + 1)) - 2;
				result.add(String.valueOf(b.charAt(index)));
			}
			int lastIndex = b.indexOf(String.valueOf("end")) - 2;
			result.add(String.valueOf(b.charAt(lastIndex)));
			for (int j = 0; j < result.size(); j++) {
				System.out.println(result.get(j));
				if (result.get(j).equals(lineVal)) {
					flag = true;
				}
			}
		}
		if (type.equals(ProductDetailModel.TuningTypes.CombineTuning.getName())) {
			b.getChars(b.indexOf("1"), b.indexOf("14"), charArray, 0);
			System.out.println("Values of bypass_hpeq for " + type);
			System.out.println(charArray);
			for (int i = 1; i < 14; i++) {
				int index = b.indexOf(String.valueOf(i + 1)) - 2;
				result.add(String.valueOf(b.charAt(index)));
			}
			for (int j = 0; j < 6; j++) {
				System.out.println(result.get(j));
				if (result.get(j).equals(lineVal)) {
					flag = true;
				}
			}
			for (int j = 7; j < 12; j++) {
				System.out.println(result.get(j));
				if (result.get(j).equals(blueVal)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public boolean checkValueAEQ(String fileName, String lineVal, String blueVal, String type) {
		StringBuilder b = getLineContainFromFile(fileName, ProductDetailModel.AEQKeys[1]);
		boolean flag = false;
		char charArray[] = new char[b.length()];
		ArrayList<String> result = new ArrayList<String>();
		if (type.equals(ProductDetailModel.TuningTypes.BlueToothOnly.getName())) {
			b.getChars(b.indexOf("1"), b.indexOf("end"), charArray, 0);
			System.out.println("Values of aeq_enable for " + type);
			System.out.println(charArray);
			for (int i = 1; i < 6; i++) {
				int index = b.indexOf(String.valueOf(i + 1)) - 2;
				result.add(String.valueOf(b.charAt(index)));
			}
			int lastIndex = b.indexOf(String.valueOf("end")) - 2;
			result.add(String.valueOf(b.charAt(lastIndex)));
			for (int j = 0; j < result.size(); j++) {
				System.out.println(result.get(j));
				if (result.get(j).equals(blueVal)) {
					flag = true;
				}
			}
		}
		if (type.equals(ProductDetailModel.TuningTypes.LineOutOnly.getName())) {
			b.getChars(b.indexOf("1"), b.indexOf("end"), charArray, 0);
			System.out.println("Values of aeq_enable for " + type);
			System.out.println(charArray);
			for (int i = 1; i < 6; i++) {
				int index = b.indexOf(String.valueOf(i + 1)) - 2;
				result.add(String.valueOf(b.charAt(index)));
			}
			int lastIndex = b.indexOf(String.valueOf("end")) - 2;
			result.add(String.valueOf(b.charAt(lastIndex)));
			for (int j = 0; j < result.size(); j++) {
				System.out.println(result.get(j));
				if (result.get(j).equals(lineVal)) {
					flag = true;
				}
			}
		}
		if (type.equals(ProductDetailModel.TuningTypes.CombineTuning.getName())) {
			b.getChars(b.indexOf("1"), b.indexOf("end"), charArray, 0);
			System.out.println("Values of aeq_enable for " + type);
			System.out.println(charArray);
			for (int i = 1; i < 12; i++) {
				int index = b.indexOf(String.valueOf(i + 1)) - 2;
				result.add(String.valueOf(b.charAt(index)));
			}
			int lastIndex = b.indexOf(String.valueOf("end")) - 2;
			result.add(String.valueOf(b.charAt(lastIndex)));
			for (int j = 0; j < 6; j++) {
				System.out.println(result.get(j));
				if (result.get(j).equals(lineVal)) {
					flag = true;
				}
			}
			for (int j = 6; j < 12; j++) {
				System.out.println(result.get(j));
				if (result.get(j).equals(blueVal)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public void editVersion() {
		String publish_status = getTextByXpath(ProductDetailModel.PUBLICATION_STATUS);
		if (publish_status.equals("Published")) {
			click(ProductDetailModel.EDIT_MODE);
			selectConfirmationDialogOption("Ok");
		} else
			click(ProductDetailModel.EDIT_MODE);
	}

	public String getIDBTDeviceName(String btDeviceName) {
		String idBT = "";
		List<WebElement> BT_TRASH_ICON = driver
				.findElements(By.xpath(".//div[contains(@id,'index-icon-trash-')]/div/div[@class='cursor-pointer']/i"));
		List<WebElement> BT_DEVICEs_SELECTED = driver.findElements(By.xpath(AddEditProductModel.BT_DEVICES_SETECTED));
		for (int i = 0; i < BT_DEVICEs_SELECTED.size(); i++) {
			if (getTextByXpath(BT_DEVICEs_SELECTED.get(i)).contains(btDeviceName)) {
				idBT = BT_TRASH_ICON.get(i).getAttribute("id");
			}
		}
		return idBT;
	}

	public String getXpathBTDeviceName(String btDeviceName) {
		String idBT = getIDBTDeviceName(btDeviceName);
		return ".//*[@id='index-" + idBT + "']/div";

	}

	public String getXpathTrashBTDeviceName(String btDeviceName) {
		String idBT = getIDBTDeviceName(btDeviceName);
		return ".//*[@id='" + idBT + "']";

	}

	public boolean isBTDeviceNameDisplayed(String btDeviceName) {
		boolean flag = false;
		List<WebElement> BT_Device_Names = driver.findElements(By.xpath(ProductDetailModel.BT_DEVICE_NAMES_VIEW));
		for (WebElement element : BT_Device_Names) {
			if (getTextByXpath(element).contains(btDeviceName))
				flag = true;
		}
		return flag;
	}

	public boolean clickOnNumberOfVariants(String xpath, String headerText, String name) {
		try {
			Thread.sleep(4000);
			int size = getPageSize(PageHome.BRAND_ACCESSORY_TABLE_INFO);
			addLog("page size: " + size);
			int index = getIndexOfHeaderText(xpath, headerText);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						columns.get(index).findElement(By.tagName("a")).click();
						waitForAjax();
						return true;
					}
				}
				clickText("Next");
			}
			addErrorLog("Element: " + name + " not found");
		} catch (NoSuchElementException e) {
			return false;
		} catch (InterruptedException e) {
			return false;
		}
		return false;
	}

	// public boolean checkValueOfColumn(int index, String value) {
	// boolean check = true;
	// try {
	// int size = getPageSize(AccessoryVariants.VARIANT_TABLE_INFO);
	// addLog("page size: " + size);
	// for (int i = 0; i < size; i++) {
	//
	// WebElement tbody =
	// driver.findElement(By.xpath(AccessoryVariants.VARIANT_TABLE_DATA));
	// List<WebElement> rows = tbody.findElements(By.tagName("tr"));
	// for (WebElement row : rows) {
	// List<WebElement> columns = row.findElements(By.tagName("td"));
	// System.out.println(index);
	// System.out.println(columns.get(index).getText());
	// if (!(columns.get(index).getText().equals(value))) {
	// return false;
	// }
	// }
	// clickText("Next");
	//
	// }
	// } catch (NoSuchElementException e) {
	// return false;
	// }
	// return check;
	// }

	public boolean checkAllValueOfColumnVariant(String xpath, String headerText, String value) {
		boolean check = false;
		try {
			int size = getPageSize(AccessoryVariants.VARIANT_TABLE_INFO);
			addLog("page size: " + size);
			int index = getIndexOfHeaderText(xpath, headerText);
			System.out.println("index: " + index);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.xpath(AccessoryVariants.VARIANT_TABLE_DATA));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					System.out.println(index);
					System.out.println(columns.get(index).getText());
					if (columns.get(index).getText().equals(value)) {
						check = true;
					} else {
						return false;
					}
				}
				if (i < size - 1) {
					clickText("Next");
				}
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return check;
	}

	public boolean checkAllValueOfColumnProduct(String xpath, String headerText, String value) {
		boolean check = false;
		try {
			int size = getPageSize(ProductModel.PRODUCT_TABLE_INFO);
			addLog("page size: " + size);
			int index = getIndexOfHeaderText(xpath, headerText);
			System.out.println("index: " + index);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.xpath(ProductModel.PRODUCT_TABLE_DATA));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					System.out.println(index);
					System.out.println(columns.get(index).getText());
					if (columns.get(index).getText().equals(value)) {
						check = true;
					} else {
						return false;
					}
				}
				if (i < size - 1) {
					clickText("Next");
				}
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return check;
	}

	public boolean checkAllValueOfColumnProduct(String xpath, String headerText, String value1, String value2,
			String value3) {
		boolean check = false;
		try {
			int size = getPageSize(ProductModel.PRODUCT_TABLE_INFO);
			addLog("page size: " + size);
			int index = getIndexOfHeaderText(xpath, headerText);
			System.out.println("index: " + index);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.xpath(ProductModel.PRODUCT_TABLE_DATA));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					System.out.println(index);
					System.out.println(columns.get(index).getText());
					if ((columns.get(index).getText().equals(value1)) || (columns.get(index).getText().equals(value2))
							|| (columns.get(index).getText().equals(value3))) {
						check = true;
					} else {
						return false;
					}
				}
				if (i < size - 1) {
					clickText("Next");
				}
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return check;
	}

	public boolean checkReportNeedAttentions() {
		boolean check = false;
		try {
			int size = getPageSize(AccessoryVariants.VARIANT_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {

				WebElement tbody = driver.findElement(By.xpath(AccessoryVariants.VARIANT_TABLE_DATA));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if ((columns.get(4).getText().equals("Revoked"))
							|| (columns.get(4).getText().equals("Partner Declined"))
							|| (columns.get(6).getText().equals("Revoked"))
							|| (columns.get(6).getText().equals("Declined"))
							|| (columns.get(4).getText().equals("Approved")
									&& (columns.get(5).getText().equals("Undetermined")))) {
						check = true;
					} else {
						return false;
					}
				}
				size--;
				if (size == 0) {
					break;
				}
				clickText("Next");

			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return check;
	}

	public boolean checkPublishProfile(String file, String type, String route) {
		boolean check = false;
		String connection_type;
		String audioRoute;
		Dtscs dtscs;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file)));
			Accessory accessory = dtscs.getProfile(0).getAccessoryCollection(0);
			List<OutputChain> chainsList = accessory.getSpec().getClassification(0).getSupportedOutputChainsList();
			for (OutputChain chains : chainsList) {
				connection_type = chains.getName();
				audioRoute = chains.getRoute().getRoute().toString();
				Postmix postmix = chains.getRoute().getTech(0).getEagleV11().getPostmix();
				if ((connection_type.equals(type))
						&& (audioRoute.equals(route) && (postmix.getModel().getHeadphonesCount() != 0)
								&& (postmix.getModel().getAeqsCount() != 0) && (postmix.getMultichRoomCount() != 0)
								&& (postmix.getFrontRoomCount() != 0) && (postmix.getWideRoomCount() != 0))) {
					check = true;
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
		return check;
	}

	public boolean checkInforTuningTemplate(String UUID, String product_type, String brandUUID) {
		boolean check = false;
		Dtscs dtscs;
		String file = "tuning_" + UUID + ".hpxtt";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			Accessory accessory = dtscs.getProfile(0).getAccessoryCollection(0);
			// Spec a = accessory.getSpec();
			if ((UUID.equals(accessory.getSpec().getRenderEntityUuid()))
					&& (product_type.equals(accessory.getSpec().getClassification(0).getType()))
					&& (brandUUID.equals(accessory.getBrandUuid()))) {
				check = true;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
		return check;
	}

	public boolean checkRouteTuningTemplate(String UUID, String connection_type, String route) {
		boolean check = false;
		Dtscs dtscs;
		String file = "tuning_" + UUID + ".hpxtt";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			Accessory accessory = dtscs.getProfile(0).getAccessoryCollection(0);
			// Spec a = accessory.getSpec();
			List<OutputChain> chainsList = accessory.getSpec().getClassification(0).getSupportedOutputChainsList();
			for (OutputChain chains : chainsList) {
				String name = chains.getName();
				String Route = chains.getRoute().getRoute().toString();
				if ((Route.equals(route)) && (name.equals(connection_type))) {
					check = true;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}

	public boolean isIncludeTwoEagleVersion(String UUID) {
		boolean check = false;
		Dtscs dtscs;
		String file = "profile_" + UUID + ".dtscs";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			List<OutputChain> chainsList = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
					.getSupportedOutputChainsList();
			for (OutputChain chains : chainsList) {
				if ((chains.getRoute().getTech(0).getEagleV11() == null)
						|| (chains.getRoute().getTech(0).getEagleV20() == null)) {
					return false;
				}
			}
			check = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return check;
	}
	

	public String downloadProductTuning() {
		//boolean flag = false;
		// Click on download link
		click(ProductDetailModel.UPLOADED_TUNING);
		// Get file name generated when download to local
		autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
		String file_name_download = autoTool.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
		// Check if file exist and delete
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
		try {
			if (FileUtil.FileExist(file_path)) {
				FileUtil.DeleteFile(file_path);
			}
			
			handleFirefoxDownloadDialog("Save File");
			System.out.println("File path: "+file_path);
			//Thread.sleep(1000);
			waitForAjax();
			for (int i = 1; i <= 30; i++) {
				Thread.sleep(1000);
				waitForAjax();
				if (isFileDownloaded(System.getProperty("user.home") + File.separator +"Downloads",file_name_download)) {
					addLog("File: " + file_name_download + " is downloaded successfully");
					return file_name_download;
				}else{
					addLog("File: " + file_name_download + " is not  downloaded successfully");
				}
			}
		
//		
		} catch (NoSuchElementException e) {
			addLog("No such element exception");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return file_name_download;
	}

	public boolean checkProductTuningTemplate(String tuningFile) {
		boolean check = false;
		Dtscs dtscs;
		String file = tuningFile + ".hpxtt";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			Accessory accessory = dtscs.getProfile(0).getAccessoryCollection(0);
			// Spec a = accessory.getSpec();
			System.out.println(accessory.getModelName());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
		return check;
	}

	public String unzipProductTuning(String tuningFile) {
		// String file = tuningFile + ".hpxtt";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + tuningFile;
		String destDirectory = file_path.substring(0, file_path.lastIndexOf('.'));
		String destFolder = tuningFile.substring(0, tuningFile.lastIndexOf('.'));
		System.out.println("destDirectory: " + destDirectory);
		FileUtil.unzip(file_path, destDirectory);
		return destFolder;
	}

	public String getDTSCSFilePath(String hpxttJsonPath) {
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + hpxttJsonPath + "\\hpxtt.json";
		System.out.println(file_path);
		// Read json file and then return listeningModel
		JSONParser parser = new JSONParser();
		Object a;
		String listeningModel="";
		try {
			Object obj = parser.parse(new FileReader(file_path));
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject hpxtt = (JSONObject) jsonObject.get("hpxtt");

			JSONObject tuning = (JSONObject) hpxtt.get("tuning");

			listeningModel = (String) tuning.get("listeningModel");
			System.out.println("listeningModel " + listeningModel);

			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hpxttJsonPath +"\\"+ listeningModel;
	}

	public headphoneEq readHpEqProductTuning(String file, String audioType) {
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		// Example of audioType= "AR_OUT_LINEOUT";AR_OUT_BLUETOOTH, AR_OUT_USB
		// file is dtscs file;
		Dtscs dtscs;
		headphoneEq hpEq = null;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			int numberOfHpType = dtscs.getModel(0).getHeadphonesCount();
			for (int i = 0; i < numberOfHpType; i++) {
				System.out.println(dtscs.getModel(0).getHeadphones(i).getAudioRouteInfo(0).getRoute().toString());
				if (dtscs.getModel(0).getHeadphones(i).getAudioRouteInfo(0).getRoute().toString().equals(audioType)) {
					hpEq = dtscs.getModel(0).getHeadphones(i);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (hpEq == null) {
			System.out.println("Can not read hpeq, hpeq is null");
		}
		return hpEq;
	}

	public Aeq readAeqsProductTuning(String file, String audioType) {
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		// Example of audioType= "AR_OUT_LINEOUT";AR_OUT_BLUETOOTH, AR_OUT_USB
		Dtscs dtscs;
		Aeq aeq = null;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			int numberOfHpType = dtscs.getModel(0).getHeadphonesCount();
			for (int i = 0; i < numberOfHpType; i++) {
				System.out.println(dtscs.getModel(0).getHeadphones(i).getAudioRouteInfo(0).getRoute().toString());
				if (dtscs.getModel(0).getHeadphones(i).getAudioRouteInfo(0).getRoute().toString().equals(audioType)) {
					aeq = dtscs.getModel(0).getAeqs(i);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (aeq == null) {
			System.out.println("Can not read aeq, aeq is null");
		}
		return aeq;
	}

	// Read headphoneEQ from published product tuning file
	public headphoneEq readHpEqPublishedProductTuning(String UUID, String audioType, int ealgeVersion) {
		String file = "profile_" + UUID + ".dtscs";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		// Example of audioType= "AR_OUT_LINEOUT";AR_OUT_BLUETOOTH, AR_OUT_USB
		// file is dtscs file;
		// eagle version : 1(eagle version 1.4), 2(eagle_v2)
		Dtscs dtscs;
		headphoneEq hpEq = null;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			int numberOfHpType = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
					.getSupportedOutputChainsCount();
			for (int i = 0; i < numberOfHpType; i++) {
				System.out.println(dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getRoute());
				if (dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getRoute().toString().equals(audioType)) {
					switch (ealgeVersion) {
					case 1:// for eagle 1.4
						hpEq = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getTech(0).getEagleV11().getPostmix().getModel()
						.getHeadphones(0);
						break;
					case 2:// for eagle 2.0
						hpEq = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getTech(0).getEagleV20().getPipeline().getModel()
						.getHeadphones(0);
						break;	
					default: //default with eagle 2.0
						hpEq = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getTech(0).getEagleV20().getPipeline().getModel()
						.getHeadphones(0);
						break;
					}
					
					// System.out.println(dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0).getSupportedOutputChains(i).getRoute().getTech(0).getEagleV11().getPostmix().getModel().getHeadphones(0));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (hpEq == null) {
			System.out.println("Can not read hpeq, hpeq is null");
		}
		return hpEq;
	}

	public Aeq readAeqsPublishedProductTuning(String UUID, String audioType, int eagleVersion) {
		String file = "profile_" + UUID + ".dtscs";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		// Example of audioType= "AR_OUT_LINEOUT";AR_OUT_BLUETOOTH, AR_OUT_USB
		// file is dtscs file;
		Dtscs dtscs;
		Aeq aEq = null;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			int numberOfHpType = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
					.getSupportedOutputChainsCount();
			for (int i = 0; i < numberOfHpType; i++) {
				System.out.println(dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getRoute());
				if (dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getRoute().toString().equals(audioType)) {
					switch (eagleVersion) {
					case 1:// for eagle 1.4
						aEq = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getTech(0).getEagleV11().getPostmix().getModel()
						.getAeqs(0);
						break;
					case 2: // for ealge 2.0
						aEq = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getTech(0).getEagleV20().getPipeline().getModel()
						.getAeqs(0);
						break;
					default:// default with eagle 2.0
						aEq = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getTech(0).getEagleV20().getPipeline().getModel()
						.getAeqs(0);
						break;
					}
					
					// System.out.println(dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0).getSupportedOutputChains(i).getRoute().getTech(0).getEagleV11().getPostmix().getModel().getAeqs(0));
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (aEq == null) {
			System.out.println("Can not read aEq, aEq is null");
		}
		return aEq;
	}
	public Premix readPremixPublishedProductTuning(String UUID, String audioType) {
		String file = "profile_" + UUID + ".dtscs";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		// Example of audioType= "AR_OUT_LINEOUT";AR_OUT_BLUETOOTH, AR_OUT_USB
		// file is dtscs file;
		// Only for eagle version 1.0
		Dtscs dtscs;
		Premix premix = null;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			int numberOfHpType = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
					.getSupportedOutputChainsCount();
			for (int i = 0; i < numberOfHpType; i++) {
				System.out.println(dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getRoute());
				if (dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getRoute().toString().equals(audioType)) {
					premix = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getTech(0).getEagleV11().getPremix();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (premix == null) {
			System.out.println("Can not read premix, premix is null");
		}
		return premix;
	}
	public static Postmix.Builder readPostmixPublishedProductTuning(String UUID, String audioType) {
		String file = "profile_" + UUID + ".dtscs";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		// Example of audioType= "AR_OUT_LINEOUT";AR_OUT_BLUETOOTH, AR_OUT_USB
		// file is dtscs file;
		// Only for eagle version 1.0
		Dtscs dtscs;
		Postmix postmix = null;
		Postmix.Builder postmixNoModel=null;
		try {
			dtscs = Dtscs.parseFrom(new FileInputStream(new File(file_path)));
			int numberOfHpType = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
					.getSupportedOutputChainsCount();
			for (int i = 0; i < numberOfHpType; i++) {
				System.out.println(dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getRoute());
				if (dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getRoute().toString().equals(audioType)) {
					postmix = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0)
						.getSupportedOutputChains(i).getRoute().getTech(0).getEagleV11().getPostmix();
				}
			}
			postmixNoModel= postmix.newBuilder();
			postmixNoModel= postmix.toBuilder().setModel(postmix.getModel().newBuilder().clear());
			if(postmixNoModel.getMultichRoomCount()>0){
				postmixNoModel.removeMultichRoom(0);
				
			}
			if(postmixNoModel.getFrontRoomCount()>0){
				postmixNoModel.removeFrontRoom(0);
			}
			if(postmixNoModel.getWideRoomCount()>0){
				postmixNoModel.removeWideRoom(0);
			}
			if(postmixNoModel.getHpeqCoefCount()>0){
				postmixNoModel.removeHpeqCoef(0);
			}
			if(postmixNoModel.getAeqCoefCount()>0){
				postmixNoModel.removeAeqCoef(0);
			}
			
			
			File f = new File("C:\\Users\\be.tran\\Downloads\\postmixNoModel.txt");
			FileUtils.writeStringToFile(f, postmixNoModel.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (postmixNoModel == null) {
			System.out.println("Can not read postmix, postmix is null");
		}
		return postmixNoModel;
	}
	public String getPublishedProfileName(String UUID){
		return "profile_" + UUID + ".dtscs";
	}

	public static void main(String[] args) {

	}

}
