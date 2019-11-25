package com.dts.adminportal.util;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class CreatePage extends AbstractTestNGSpringContextTests {
	
	//Company & Brands information
	protected static String dts_company_name="DTS Inc.";
	protected static String all_brand="All brands";
	protected static String partner_company_name="TestCorp-Auto";
	protected static String securityCompany1="TestCorp-Security";
	protected static String securityCompany2="TestCorp-Security1";
	protected static String securityCompanyId2="702";
	protected static String securityBrand1="Brand-Security";
	protected static String securityBrandId1 = "447";			// Remember to change the ID when re-create the database
	protected static String securityBrand2="Brand-Security1";
	protected static String securityBrandId2 = "546";			// Remember to change the ID when re-create the database
	protected static String securityInvalidBrand="-------------------------------------";	// use for function selectBrandPrivilege in userController
	protected static String partner_brand_name_1="TestCorp-Brand1";
	protected static String partner_brand_name_2="TestCorp-Brand2";
	protected static String partner_security_brand_name = "Brand-Security";
	protected static String yahoo_imap_server = "imap.mail.yahoo.com";
	protected static String dts_email = "dtstesting123@yahoo.com";
	protected static String pdts_email = "pdtstesting123@yahoo.com";
	protected static String email_password = "dtspassword";
	protected static String new_active_user_password = "dts999";
	
//	protected static String dts_company_name="DTS Inc.";
//	protected static String all_brand="All brands";
//	protected static String partner_company_name="TestCorp-Auto-1";
//	protected static String partner_brand_name_1="TestCorp-Brand-1";
//	protected static String partner_brand_name_2="TestCorp-Brand-2";
//	protected static String yahoo_imap_server = "imap.mail.yahoo.com";
//	protected static String dts_email = "dtstesting1234@yahoo.com";
//	protected static String pdts_email = "pdtstesting1234@yahoo.com.vn";
//	protected static String email_password = "dtspassword";
//	protected static String new_active_user_password = "dts999";
	
	// partner user admin : This partner user has full user privileges and
	// should not be edited
	protected static String superpartneruser = "pdtsautosuper@mailinator.com";
	protected static String superpartnerpassword = "dts999";
//	protected static String superpartneruser = "pdtsautosuper1@mailinator.com";
//	protected static String superpartnerpassword = "dts999";
	
	// partner user : Use this account when user need to create some
	// pre-conditions for test case such as: edit user info, enable privileges,
	// disabled privileges,...
	protected String partneruser = "pdtsauto@mailinator.com";
	protected String password = "dts999";
	protected String partneruserId = "33";
	protected String partnerAutoId = "49";
//	protected static String partneruser = "pdtsauto99@mailinator.com";
//	protected static String password = "dts999";
	
	// supper admin
	protected static String superUsername = "dtsautosuper@mailinator.com";
	protected static String superUserpassword = "dts999";
	
	// dts user
	protected static String dtsUser =  "dtsauto@mailinator.com";
	protected static String dtsPass = "dts999";
//	protected static String dtsUser =  "dtsauto99@mailinator.com";
//	protected static String dtsPass = "dts999";
	
	
	// dts user security
	protected static String dtsUserSecurity =  "dtssecurity@mailinator.com";
	protected static String dtsUserSecurityPassword = "dts999";
	// Remember to change the ID when re-create the database
	protected static String dtsUserSerId = "1498"; 		
	protected static String dtsUserSerCompId = "2";

	// pdts user security
	protected static String pdtsUserSecurity =  "pdtssecurity@mailinator.com";
	protected static String pdtsUserSecurityPassword = "dts999";
	// Remember to change the ID when re-create the database
	protected static String pdtsUserSerId = "1851"; 		
	protected static String pdtsUserSerCompId = "558";
	
	
	//Model types
	protected static String typeEarBuds = "Ear-buds";
	protected static String typeInEar = "In-Ear";
	protected static String typeOverEar = "Over-Ear";
	protected static String typeOnEar = "On-Ear";
	
	protected Result result;
	@Autowired
	protected URI siteBase;

	@Autowired
	protected WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		try {
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
			driver.get(siteBase.toString());
			driver.manage().window().maximize();
			result = new Result();
		} catch (TimeoutException e) {
			result.addErrorLog("Page load time out exception");
			driver.navigate().refresh();
		} catch (UnreachableBrowserException e) {
			result.addErrorLog("Unreacheable browser exception");
			driver.navigate().refresh();
		}
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult iTestResult) {
		Reporter.setCurrentTestResult(iTestResult);
		MouseMover();
		if(!iTestResult.isSuccess()){
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			try {
				File scrnsht = ((TakesScreenshot) driver)
						.getScreenshotAs(OutputType.FILE);
				String destDir = "target/surefire-reports/screenshots";
				DateFormat dateFormat = new SimpleDateFormat(
						"dd_MMM_yyyy__hh_mm_ssaa");
				String destFile = dateFormat.format(new Date()) + ".png";
				org.apache.commons.io.FileUtils.copyFile(scrnsht, new File(destDir
						+ "/" + destFile));
				Reporter.log("View error : <a href=../screenshots/" + destFile + ">Screenshot</a>");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Reporter.log("FAILED", true);
		}else {
			Reporter.log("PASSED", true);
		}
		result.reset();
		Reporter.log("===========================================================================", true);
	}
	
	public void MouseMover() {
    	try {
    		Robot robot = new Robot();
			Point point = MouseInfo.getPointerInfo().getLocation();
			robot.mouseMove(point.x+120, point.y +120);
			robot.mouseMove(point.x, point.y);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
