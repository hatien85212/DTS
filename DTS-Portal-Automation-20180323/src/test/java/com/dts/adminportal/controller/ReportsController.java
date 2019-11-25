package com.dts.adminportal.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.dts.adminportal.model.ActiveBrand;
import com.dts.adminportal.model.PageHome;
import com.dts.adminportal.model.PublishedProduct;
import com.dts.adminportal.util.DbUtil;
import com.dts.adminportal.util.FileUtil;


public class ReportsController extends BaseController {
	BaseController reportsControl;
	public ReportsController(WebDriver driver) {
		super(driver);
		reportsControl = new BaseController(driver);
	}
	
	
	@Override
	public void click(String xpath) {
		waitForAjax();
		try {
			addLog("Click : " + xpath);
			driver.findElement(By.xpath(xpath)).click();
			waitForAjax();
		} catch (NoSuchElementException e) {
			addLog("No element exception : " + xpath);
			Assert.assertTrue(false);
		}
	}
	
	public boolean selectOptionInDropdown(String xpath, String name) {
		try {
			if (name == null | name == "") {
				return false;
			}
			// Get dropdown box
			WebElement groupBox = driver.findElement(By.xpath(xpath));
			// Click on dropdown
			groupBox.findElement(By.tagName("button")).click();
			waitForAjax();
			// Select checkbox
			List<WebElement> options = groupBox.findElement(By.tagName("ul")).findElements(By.tagName("li"));
			for (WebElement option : options) {
				if (option.getText().contains(name)) {
					addLog("Select option: " + name);
					option.findElement(By.tagName("a")).click();
					waitForAjax();
					return true;
				}
			}
			addLog("Option: " + name + " not found");
			return false;
		} catch (NoSuchElementException e) {
			addLog("No element: " + xpath);
			return false;
		}
	}
	
	public String selectTheFirstElemenTActiveBrand(String element) {
		String text = "";
		int index = 0;
		if (element.equals("Company")) {
			index = 0;
		} else if (element.equals("Brand")) {
			index = 1;
		}
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			if (rows.size() > 0) {
				WebElement row = rows.get(0);
				List<WebElement> columns = row.findElements(By
						.tagName("td"));
				text = columns.get(index).getText();
				columns.get(index).findElement(By.tagName("a")).click();
				waitForAjax();
				return text;
			}
		} catch (NoSuchElementException e) {
			return text;
		}
		return text;
	}
	
	public String getNumberProductsOfTheFirstBrand() {
		String total_Products = null;
//		if (element.equals("Company")) {
//			index = 0;
//		} else if (element.equals("Brand")) {
//			index = 1;
//		}
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			if (rows.size() > 0) {
				WebElement row = rows.get(0);
				List<WebElement> columns = row.findElements(By
						.tagName("td"));
				total_Products = columns.get(2).getText();
				return total_Products;
			}
		} catch (NoSuchElementException e) {
			return total_Products;
		}
		return total_Products;
	}
	
	public String selectTheFirstElementPublishedProduct(String element) {
		String text = "";
		int index = 0;
		if (element.equals("Product")) {
			index = 1;
		} else if (element.equals("Brand")) {
			index = 2;
		}
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			if (rows.size() > 0) {
				WebElement row = rows.get(0);
				List<WebElement> columns = row.findElements(By
						.tagName("td"));
				text = columns.get(index).getText();
				columns.get(index).findElement(By.tagName("a")).click();
				waitForAjax();
				return text;
			}
		} catch (NoSuchElementException e) {
			return text;
		}
		return text;
	}
	
	public boolean selectElementByNameActiveBrand (String element, String name) {
		try {
			int index = 0;
			if (element.equals("Company")) {
				index = 0;
			} else if (element.equals("Brand")) {
				index = 1;
			}
			int size = getPageSize(PageHome.ACTIVE_BRAND_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(index).getText().equals(name)) {
						addLog("Select accessory : " + name);
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
		}
		return false;
	}
	
	public int getTotalOfProductByFiltering () {
		int total = 0;
		try {
			int size = getPageSize(PublishedProduct.PUBLISHED_PRODUCT_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement page = driver.findElement(By.xpath("//*[@id='published-product-part']"));
				WebElement tbody = page.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (@SuppressWarnings("unused") WebElement row : rows) {
						total++;
				}
				clickText("Next");
			}
		} catch (NoSuchElementException e) {
			return total;
		}
		return total;
	}
	
	public boolean selectElementByNamePublishedProduct (String element, String name) {
		try {
			int index = 0;
			if (element.equals("Product")){
				index = 1;
			} else if (element.equals("Brand")) {
				index = 2;
			}
			int size = getPageSize(PageHome.PUBLISHED_PRODUCT_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(index).getText().equals(name)) {
						addLog("Select accessory : " + name);
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
		}
		return false;
	}
	
	public boolean checkAnElementExistByNameActiveBrand(String element, String name) {
		int index = 0;
		if (element.equals("Company")) {
			index = 0;
		} else if (element.equals("Brand")) {
			index = 1;
		}
		try {
			int size = getPageSize(ActiveBrand.ACTIVE_BRAND_TABLE_INFO);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(index).getText().equals(name)) {
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
	
	public boolean checkAnElementExistByNamePublishedProduct(String element, String name) {
		int index = 0;
		if (element.equals("Product")) {
			index = 1;
		} else if (element.equals("Brand")) {
			index = 2;
		}
		try {
			int size = getPageSize(ActiveBrand.ACTIVE_BRAND_TABLE_INFO);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By
							.tagName("td"));
					if (columns.get(index).getText().equals(name)) {
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
	
	public Boolean checkSortByAlphabet(String Xpath){
		String check_item = reportsControl.getTextByXpath(Xpath);
		ArrayList<String> check_arr = new ArrayList<String>(Arrays.asList(check_item.split("</br>")));
		ArrayList<String> sorted_arr = new ArrayList<String>(Arrays.asList(check_item.split("</br>")));
		Collections.sort(sorted_arr, String.CASE_INSENSITIVE_ORDER);
		if(check_arr.equals(sorted_arr))
			return true;
		return false;
	}
	
	
	public ArrayList<String> getListElementActiveBrand(String element) {
		ArrayList<String> list = new ArrayList<String>();
		try {	
			int index = 0;
			if (element.equals("Company")){
				index = 0;
			} else if (element.equals("Brand")) {
				index = 1;
			}
			int size = getPageSize(ActiveBrand.ACTIVE_BRAND_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(index).getText() != null) {
						list.add(columns.get(index).getText());
					}
				}
				clickText("Next");
			}
		} catch (Exception e) {
			
		}
		return list;
	}
	
	public ArrayList<String> getListElementPublishedProduct(String element) {
		ArrayList<String> list = new ArrayList<String>();
		try {	
			int index = 0;
			if (element.equals("Product")){
				index = 1;
			} else if (element.equals("Brand")) {
				index = 2;
			}
			int size = getPageSize(PublishedProduct.PUBLISHED_PRODUCT_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(index).getText() != null) {
						list.add(columns.get(index).getText());
					}
				}
				clickText("Next");
			}
		} catch (Exception e) {
			
		}
		return list;
	}
	
	public String getFilePathDownload(String downloadLinkXpath) {
		String file_path = null;
		try {
			// Click on download link
			click(downloadLinkXpath);
			// Get file name generated when download to local
			autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
			Thread.sleep(3000);
			String file_name_download = autoTool.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
			// Check if file exist and delete
			file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
			System.out.println("File path: "+file_path);
			File file = new File(file_path);
			if(file.exists()){
				file.delete();
			}
			// Download file
			addLog("Start download file: " + file_name_download);
			handleFirefoxDownloadDialog("Save File");
			for (int i = 1; i <= 10; i++) {
				Thread.sleep(1000);
				if (FileUtil.FileExist(file_path)) {
					addLog("File: " + file_name_download + " is downloaded successfully");
					return file_path;
				}
			}
			addLog("File: " + file_name_download + " is not downloaded successfully");
			return null;
		} catch (NoSuchElementException e) {
			addLog("No such element exception");
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getPath(String downloadLinkXpath) {
		try {
			if (downloadLinkXpath.contains(".dtscs")){
				String filename = downloadLinkXpath; 
				System.out.println("File name (First): "+ filename);
				String fileExt = filename.substring(filename.lastIndexOf("."), filename.length());
				// Cut off file extension
				filename = filename.replace(fileExt, "");
				// Find the file link
				// should change the input filename to input xpath
				WebElement file_link = driver.findElement(By
						.xpath("//a[contains(@href,'" + filename + "')]"));
				System.out.println(filename);
				
				// Click on download link
				click(file_link);
				// Get file name generated when download to local
				autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
		
				String file_name_download = autoTool.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
				// Check if file exist and delete
				String file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
				System.out.println("File path: "+file_path);
				File file = new File(file_path);
				if(file.exists()){
					file.delete();
				}
				// Download file
				addLog("Start download file: " + file_name_download);
				handleFirefoxDownloadDialog("Save File");
				for (int i = 1; i <= 15; i++) {
					Thread.sleep(1000);
					if (FileUtil.FileExist(file_path)) {
						addLog("File: " + file_name_download + " is downloaded successfully");
						return file_path;
					}
				}
				addLog("File: " + file_name_download + " is not downloaded successfully");
				return "Not download";
			}
			
			else{
			String link = getAtributeValue(downloadLinkXpath, "href");		

			//Get download file name
			int SlashId= link.lastIndexOf("/");		   
		    String filename = link.substring(SlashId+1);	
		    System.out.println(filename);
			System.out.println("Download File: "+filename);
			String file_path = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + filename;
			System.out.println("File path: "+file_path);
			// Delete file if exist
			if (FileUtil.FileExist(file_path)) {
				FileUtil.DeleteFile(file_path);
			}
			// Download file
			addLog("Start download file:" + filename);
			click(driver.findElement(By.xpath(downloadLinkXpath)));
			handleFirefoxDownloadDialog("Save File");
			System.out.println(file_path);
			Thread.sleep(4000);
			waitForAjax();
			for (int i = 1; i <= 30; i++) {
				Thread.sleep(6000);
				waitForAjax();
				if (isFileDownloaded(System.getProperty("user.home") + File.separator +"Downloads",filename)) {
					addLog("File: " + filename + " is downloaded successfully");
					return file_path;
				}
			}
			}	
		} catch (NoSuchElementException e) {
			addLog("No such element exception");
			return "Not download";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "Not download";
		}
		return "Not download";
	}
	
	public String[][] getDataCSVActiveBrand(String csvFile) {
        BufferedReader br = null;
        String line = "";
        String[][] dataArray = new String[1000][4];
        int x = -1;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	String[] dataLine = new String[4];
            	String[] data1;
            	if (line.charAt(0) == '"') {
            		line = line.substring(1);
            		String specialElement = line.split('"' + ",")[0];
            		String line1 = line.split('"' + ",")[1];
            		data1 = line1.split(",");
            		
            		for (int j = 0; j < 3; j++) {
            			dataLine[j + 1] = data1[j];
            		}
            		dataLine[0] = specialElement;
            	} else {
            		System.out.println(line);
                	data1 = line.split(",");
                	if (data1.length == 2) {
                		continue;
                	}
                	System.out.println("Length: " + dataLine.length);
                	if (data1.length < 4) {
                		for (int j = 0; j < data1.length; j++) {
                			dataLine[j] = data1[j];
                		}
                		dataLine[3] = "";
                	} else {
                		dataLine = data1;
                	}
            	}
//            	if (line.charAt(0) == '"') {
//            		System.out.println(line);
//            		line = line.substring(1);
//            		dataLine = line.split('"' + ",");
//            	} else {
//            		System.out.println(line);
//                    // use comma as separator
//            		dataLine = line.split(",");
//            	} 
            	System.out.println("Country [code= " + dataLine[0] + "]" + " Country [code= " + dataLine[3] + "]");
            	if (x > -1) {
            		for(int j = 0; j < 4; j++) {
            			dataArray[x][j] = dataLine[j];
            		}
            	}
            	x++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataArray;
	}
	
	public String[][] getDataActiveBrand() {
		String[][] data = new String[1000][4];
		int x = 0;
		int size = getPageSize(PageHome.ACTIVE_BRAND_TABLE_INFO);
		addLog("page size: " + size);
		for (int i = 0; i < size; i++) {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));	
				for (int j = 0; j < 4; j++) {
					System.out.println("data" + j + ": " + columns.get(j).getText());
					data[x][j] = columns.get(j).getText();
				}
				x++;
//				if (columns.get(index).getText().equals(name)) {
				
//				}
			}
			clickText("Next");
		}
		return data;
	}
	
	public String[][] getDataCSV(String csvFile) {
        BufferedReader br = null;
        String line = "";
        int x = 0;
        String[][] dataArray = new String[1000][2];
       
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	String[] dataLine = new String[2];
            	String[] data1;
            	if (line.charAt(0) == '"') {
            		String line1 = line.split('"' + ",")[1];
            		data1 = line1.split(",");
            		dataLine[0] = data1[0];
            		dataLine[1] = data1[1];
            	} else {
            		System.out.println(line);
                	data1 = line.split(",");
                	if (data1.length == 2) {
                		continue;
                	}
                	System.out.println("Length: " + dataLine.length);
            		dataLine[0] = data1[1];
            		dataLine[1] = data1[2];
            	}
        		for(int j = 0; j < 2; j++) {
        			dataArray[x][j] = dataLine[j];
        		}
            	x++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataArray;
	}
	
	public String[][] getElementActiveBrand() {
		String[][] data = new String[1000][2];
		int x = 1;
		int size = getPageSize(PageHome.ACTIVE_BRAND_TABLE_INFO);
		addLog("page size: " + size);
		for (int i = 0; i < size; i++) {
			WebElement thead = driver.findElement(By.tagName("thead"));
			List<WebElement> rowHead = thead.findElements(By.tagName("tr"));
			for (WebElement row: rowHead) {
				List<WebElement> columns = row.findElements(By.tagName("th"));
				data[0][0] = columns.get(1).getText();
				data[0][1] = columns.get(2).getText();
			}
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			for (WebElement row : rows) {
				List<WebElement> columns = row.findElements(By.tagName("td"));	
				data[x][0] = columns.get(1).getText();
				data[x][1] = columns.get(2).getText();
				x++;
			}
			clickText("Next");
		}
		return data;
	}
	
	public Boolean checkDataReportActiveBrand(String[][] dataCSV, String[][] data) {
		Boolean check = true;
		for (int i = 0; i < dataCSV.length; i++) {
			for (int j = 0; j < 2; j++) {
				if (dataCSV[i][j] != null) {
					if (!dataCSV[i][j].equals(data[i][j])) {
						return false;
					}
				} else {
					return true;
				}
				
			}
		}
		return check;
	}
	
	public ArrayList<String> getDataCSVPublishedProduct(String csvFile) {
        BufferedReader br = null;
        String line = "";
        int x = -1;
        String[] temp;
        ArrayList<String> arrayBrand = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            	temp = line.split(",");
            	if ((x > -1) && (temp.length > 2)) {
            		arrayBrand.add(temp[2]);
            	}
            	x++;	
            }
//            System.out.println("X: " + x);
//            System.out.println("arrayBrand: " + arrayBrand);
//            System.out.println("variant: " +arrayBrand.get(592));
//            System.out.println("length: " + arrayBrand.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayBrand;
	}
	
	public ArrayList<String> getAllHeaderPublishedProduc(String csvFile) {
		ArrayList<String> allHeader = new ArrayList<String>();
		String lineHeader = "";
		BufferedReader br = null;
		String[] temp;
		try {
            br = new BufferedReader(new FileReader(csvFile));
            lineHeader = br.readLine();
            temp = lineHeader.split(",");
            for (int i = 0; i < temp.length; i++) {
            	if (temp[i] != null) {
            		allHeader.add(temp[i]);
            	}
            }
            System.out.println("allHeader: " + allHeader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		
		return allHeader;
	}
	
	public Boolean checkListContainAnother(ArrayList<String> list1, ArrayList<String> list2) {
		for (int i = 0; i < list2.size(); i ++) {
		    if (!(list1.contains(list2.get(i)))) {
		        return false;
		    }
		}    
		return true;
	}
	
	
	public String getDateOfPublishedProductByName(String name) {
		String date = null;
		try {
			int size = getPageSize(PageHome.PUBLISHED_PRODUCT_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						addLog("Select accessory : " + name);
//						columns.get(index).findElement(By.tagName("a")).click();
						date = columns.get(5).getText();
						waitForAjax();
						return date;
					}
				}
				clickText("Next");
			}
			addErrorLog("Element: " + name + " not found");
		} catch (NoSuchElementException e) {
			return date;
		}
		return date;
	}
	
	public String getDateOfActiveBrandByName(String name) {
		String date = null;
		try {
			int size = getPageSize(PageHome.ACTIVE_BRAND_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				for (WebElement row : rows) {
					List<WebElement> columns = row.findElements(By.tagName("td"));
					if (columns.get(1).getText().equals(name)) {
						addLog("Select accessory : " + name);
//						columns.get(index).findElement(By.tagName("a")).click();
						date = columns.get(3).getText();
						waitForAjax();
						return date;
					}
				}
				clickText("Next");
			}
			addErrorLog("Element: " + name + " not found");
		} catch (NoSuchElementException e) {
			return date;
		}
		return date;
	}
	
	public String getNameElementOfTheFirstActiveBrand(String element) {
		int index = 0;
		if (element == "Company") {
			index = 0;
		} else if (element == "Brand") {
			index = 1;
		}
		String nameBrand = null;
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			WebElement row = rows.get(0);
			List<WebElement> columns = row.findElements(By.tagName("td"));
			nameBrand = columns.get(index).getText();
			waitForAjax();
			return nameBrand;
		} catch (NoSuchElementException e) {
			return nameBrand;
		}
	}
	
	public Boolean checkAlphabetProduct() {
		try {
			int size = getPageSize(PageHome.PUBLISHED_PRODUCT_TABLE_INFO);
			addLog("page size: " + size);
			for (int i = 0; i < size; i++) {
				WebElement tbody = driver.findElement(By.tagName("tbody"));
				List<WebElement> rows = tbody.findElements(By.tagName("tr"));
				String product1 = null;
				String product2 = null;
				for (int j = 0; j < rows.size() - 1; j++) {
					String brand1 = rows.get(j).findElements(By.tagName("td")).get(2).getText();
					String brand2 = rows.get(j + 1).findElements(By.tagName("td")).get(2).getText();
					if (brand1.equals(brand2)) {
						product1 = rows.get(j).findElements(By.tagName("td")).get(1).getText();
						product2 = rows.get(j + 1).findElements(By.tagName("td")).get(1).getText();
					}
					// Check alphabet of product
					ArrayList<String> listBeforeSorting = new ArrayList<String>(
						    Arrays.asList(product1, product2));
					ArrayList<String> listAfterSorting = new ArrayList<String>(
						    Arrays.asList(product1, product2));
					Collections.sort(listAfterSorting, Collator.getInstance(new Locale("en","US")));
					if (!(listBeforeSorting.equals(listAfterSorting))) {
						return false;
					}
				}
				clickText("Next");
			}
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	public String getPublisedDateProduct(String dts_id) {
		String fmDateTimeUS = null;
		String sql = "SELECT UPDATED_ON FROM ASSET WHERE DTS_ID = '" + dts_id + "'";
//		String sql1 = "SELECT UPDATED_ON FROM ASSET a WHERE a.DTS_ID = '49b7f621-00f6-41cc-b755-5596e31b0669' and"
//+ "a.revision = (select max(a1.revision) from Asset a1" + 
//  "where a1.status='Published' and a1.id = a.id) and not exists (select a2.id from Asset a2" + 
//  "where a2.id = a.id and a2.status in ('Inactive' , 'Deleted'))";
		String date_time = DbUtil.selectStatment(sql);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			Date date = formatter.parse(date_time);
			SimpleDateFormat sdfAmerica = new SimpleDateFormat("dd MMM yyyy");
	        TimeZone tzUS = TimeZone.getTimeZone("US/Pacific");
	        sdfAmerica.setTimeZone(tzUS);
	        fmDateTimeUS = sdfAmerica.format(date);
//	        Date dateInAmerica = formatter.parse(sDateInAmerica); 
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fmDateTimeUS;
	}
	
	public String getPublisedDateTimeProduct(String dts_id) {
		String fmDateTimeUS = null;
		String sql = "SELECT UPDATED_ON FROM ASSET WHERE DTS_ID = '" + dts_id + "'";
//		String sql1 = "SELECT UPDATED_ON FROM ASSET a WHERE a.DTS_ID = '49b7f621-00f6-41cc-b755-5596e31b0669' and"
//+ "a.revision = (select max(a1.revision) from Asset a1" + 
//  "where a1.status='Published' and a1.id = a.id) and not exists (select a2.id from Asset a2" + 
//  "where a2.id = a.id and a2.status in ('Inactive' , 'Deleted'))";
		String date_time = DbUtil.selectStatment(sql);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			Date date = formatter.parse(date_time);
			SimpleDateFormat sdfAmerica = new SimpleDateFormat("dd MMM yyyy HH:mm");
	        TimeZone tzUS = TimeZone.getTimeZone("US/Pacific");
	        sdfAmerica.setTimeZone(tzUS);
	        fmDateTimeUS = sdfAmerica.format(date);
//	        Date dateInAmerica = formatter.parse(sDateInAmerica); 
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fmDateTimeUS;
	}
	
	public String getPublisedDateTimeVariant(String dts_id) {
		String fmDateTimeUS = null;
		String sql = "SELECT UPDATED_ON FROM VARIANT WHERE DTS_ID = '" + dts_id + "'";
//		String sql1 = "SELECT UPDATED_ON FROM ASSET a WHERE a.DTS_ID = '49b7f621-00f6-41cc-b755-5596e31b0669' and"
//+ "a.revision = (select max(a1.revision) from Asset a1" + 
//  "where a1.status='Published' and a1.id = a.id) and not exists (select a2.id from Asset a2" + 
//  "where a2.id = a.id and a2.status in ('Inactive' , 'Deleted'))";
		String date_time = DbUtil.selectStatment(sql);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			Date date = formatter.parse(date_time);
			SimpleDateFormat sdfAmerica = new SimpleDateFormat("dd MMM yyyy HH:mm");
	        TimeZone tzUS = TimeZone.getTimeZone("US/Pacific");
	        sdfAmerica.setTimeZone(tzUS);
	        fmDateTimeUS = sdfAmerica.format(date);
//	        Date dateInAmerica = formatter.parse(sDateInAmerica); 
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fmDateTimeUS;
	}
	
	public String getPublisedDateTimeBrand(String dts_id) {
		String fmDateTimeUS = null;
		String sql = "SELECT UPDATED_ON FROM BRAND WHERE DTS_ID = '" + dts_id + "'";
		String date_time = DbUtil.selectStatment(sql);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			Date date = formatter.parse(date_time);
			SimpleDateFormat sdfAmerica = new SimpleDateFormat("dd MMM yyyy");
	        TimeZone tzUS = TimeZone.getTimeZone("US/Pacific");
	        sdfAmerica.setTimeZone(tzUS);
	        fmDateTimeUS = sdfAmerica.format(date);
//	        Date dateInAmerica = formatter.parse(sDateInAmerica); 
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fmDateTimeUS;
	}
	
	
	public String getPublisedDateTime() throws InterruptedException {
		String fmDateTimeUS = null;
		String sql = "select MAX(last_updated) from  database_last_updated_log";
		String date_time = DbUtil.selectStatment(sql);
		// Object date_time1 = DbUtil.getDB(connection, sql);
//		Timestamp date = (Timestamp) date_time1;
		Thread.sleep(3000);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date date = null;
					try {
						date = formatter.parse(date_time);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SimpleDateFormat sdfAmerica = new SimpleDateFormat("dd MMM yyyy HH:mm");
			        TimeZone tzUS = TimeZone.getTimeZone("US/Pacific");
			        sdfAmerica.setTimeZone(tzUS);
			        Thread.sleep(3000);
			        fmDateTimeUS = sdfAmerica.format(date);
		//	        Date dateInAmerica = formatter.parse(sDateInAmerica); 
		return fmDateTimeUS;
	}
	public String selectElementPublishedProduct(String element) {
		String text = "";
		int index = 0;
		if (element.equals("Product")) {
			index = 1;
		} else if (element.equals("Brand")) {
			index = 2;
		}
		try {
			WebElement tbody = driver.findElement(By.tagName("tbody"));
			List<WebElement> rows = tbody.findElements(By.tagName("tr"));
			if (rows.size() > 0) {
				WebElement row = rows.get(31);
				List<WebElement> columns = row.findElements(By
						.tagName("td"));
				text = columns.get(index).getText();
				columns.get(index).findElement(By.tagName("a")).click();
				waitForAjax();
				return text;
			}
		} catch (NoSuchElementException e) {
			return text;
		}
		return text;
	}
	
}
 