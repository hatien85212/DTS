package com.dts.adminportal.util;

import java.awt.AWTException;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.validator.routines.EmailValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Test {

//	private static String promos[];

	public static void main(String[] args) throws InterruptedException {
//		promos = new String[8];
//		for (int i = 0; i < 7; i++) {
//			promos[i] = String.format("Promotion %d:", i + 1);
//			System.out.println(promos[i]);
//		}
		
		
//		String path = "C:\\Users\\TEMP.PSDVN.007\\Downloads\\613a98c9-29c8-49a0-933f-4a50efb9d47e.dtscs";
//		Boolean fileExist = FileUtil.FileExist(path);
//		System.out.println("File exist : "+ fileExist);
//		if(fileExist){
//			Boolean isDelete = FileUtil.DeleteFile(path);
//			System.out.println("File delete : "+ isDelete);
//		}
//		String oldString = "      test a      ";
//		String newString = oldString.trim();
//		System.out.println(oldString);
//		System.out.println(newString);
		
		EmailValidator emailValidator = EmailValidator.getInstance();
		System.out.println(emailValidator.isValid("123@3451111.com"));
	}

	public static void testUploadFileFirefox() throws AWTException,
			InterruptedException {
	}

	public static void abc() throws InterruptedException {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.rediff.com/");
		WebElement sign = driver.findElement(By
				.xpath("//*[@id='homewrapper']/div[5]/a[1]/div/img"));
		sign.click();

		Set<String> windowId = driver.getWindowHandles(); // get window id of
															// current window
		Iterator<String> itererator = windowId.iterator();

		String mainWinID = itererator.next();
		String newAdwinID = itererator.next();

		driver.switchTo().window(newAdwinID);
		System.out.println(driver.getTitle());
		Thread.sleep(3000);
		driver.close();

		driver.switchTo().window(mainWinID);
		System.out.println(driver.getTitle());
		Thread.sleep(2000);

		WebElement email_id = driver
				.findElement(By.xpath("//*[@id='c_uname']"));
		email_id.sendKeys("hi");
		Thread.sleep(5000);

		driver.close();
		driver.quit();
	}
}
