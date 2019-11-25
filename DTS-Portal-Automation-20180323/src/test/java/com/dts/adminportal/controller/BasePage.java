package com.dts.adminportal.controller;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.io.File;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.dts.adminportal.workflow.ProductWorkflow;
import com.dts.adminportal.workflow.UserWorkflow;

public abstract class BasePage {
	
	public static String securityCompanyId2="702"; 	
	public static String securityBrandId1 = "1424";			// Remember to change the ID when re-create the database
	public static String securityBrandId2 = "1340";			// Remember to change the ID when re-create the database
	public static String securityInvalidBrand="-------------------------------------";	// use for function selectBrandPrivilege in userController
	
	//Company & Brands information
	public static String DTS_COMPANY_NAME="DTS Inc.";
	public static String DTS_BRAND_NAME = "DTS Inc.";
	public static String ALL_BRANDS="All brands";
	public static String PARTNER_COMPANY_NAME="TestCorp-Auto14";
	public static String SECURITY_COMPANY_1="TestCorp-Security14";
	public static String SECURITY_COMPANY_2="TestCorp-Security1";
	public static String SECURITY_BRAND_2="Brand-Security11";
	public static String SECURITY_BRAND_1="Brand-Security14";
	public static String COMPANY_NAME_TEST="TestCorp-Auto1";
	public static String COMPANY_NAME_LICENSEE = "TestCorp-Licensee18";
	public static String BRAND_NAME_LICENSEE = "TestCorp-Licensee18 brand";
	
	public static String BRAND_NAME_TEST="TestCorp-Brand3";
	public static String brandTestId ="1648";
	public static String PARTNER_BRAND_NAME_1="TestCorp-Brand14";
	public static String PARTNER_BRAND_NAME_2="TestCorp-Brand15";
	public static String PARTNER_SECURITY_BRAND_NAME = "Brand-Security8";
	public static String YAHOO_IMAP_SERVER = "imap.mail.yahoo.com";
	public static String DTS_EMAIL = "dtstesting123@yahoo.com";
	public static String PARTNER_DTS_EMAIL = "pdtstesting123@yahoo.com";
	public static String EMAIL_PASSWORD = "dtspassword";
	public static String NEW_ACTIVE_USER_PASSWORD = "dts999";

	// partner user admin : This partner user has full user privileges and
	// should not be edited
	public static String SUPER_PARTNER_USER = "pdtsautosuper14@mailinator.com";
	public static String SUPER_PARTNER_PASSWORD = "dts999";
	
	// partner user : Use this account when user need to create some
	// pre-conditions for test case such as: edit user info, enable privileges,
	// disabled privileges,...
	public static String PARTNER_USER = "pdtsauto14@mailinator.com";
	public static String PARTNER_PASSWORD = "dts999";
	
	protected String partneruserId = "33";
	protected String partnerAutoId = "49";

	
	// supper admin
	public static String SUPER_USER_NAME = "dtsautosuper@mailinator.com";
	public static String SUPER_USER_PASSWORD = "dts999";
	
	// dts user
	public static String DTS_USER =  "dtsauto14@mailinator.com";
	public static String DTS_PASSWORD = "dts999";
	
	//dts User Privileges 
	 public static String DTS_PRIVILEGES_USER =  "dtsprivilegesuser@mailinator.com";
	 public static String DTS_PRIVILEGES_PASSWORD = "dts999";
	
	// dts user security
	public static String DTS_SECURITY_USER =  "dtssecurity14@mailinator.com";
	public static String DTS_SECURITY_PASSWORD = "dts999";
	// Remember to change the ID when re-create the database
	public static String DTS_USER_SER_ID = "1498";
	public static String DTS_USER_SER_COMP_ID = "2";

	// pdts user security
	public static String PARTNER_DTS_SECURITY_USER =  "pdtssecurity14@mailinator.com";
	public static String PARTNER_DTS_SECURITY_PASSWORD = "dts999";
	// Remember to change the ID when re-create the database
	public static String PARTNER_DTS_USER_SER_ID = "1851"; 		
	public static String PARTNER_DTS_USER_SER_COMP_ID = "558";
	public static String PARTNER_DTS_USER_SER_COM_ID_OTHER = "23";
	
	// pdts licensee user
	public static String PARTNER_LICENSSEE_USER =  "pdtlicensee18@mailinator.com";
	public static String PARTNER_LICENSSEE_PASSWORD = "dts999";

	//Model types
	public static String TYPE_BUDS_EAR = "Ear-buds";
	public static String TYPE_IN_EAR = "In-Ear";
	public static String TYPE_OVER_EAR = "Over-Ear";
	public static String TYPE_ON_EAR = "On-Ear";
	
	// Audio Type
	public static String AR_OUT_LINEOUT = "AR_OUT_LINEOUT";
	public static String AR_OUT_BLUETOOTH= "AR_OUT_BLUETOOTH";
	public static String AR_OUT_USB= "AR_OUT_USB";
	
	// button 
	public static String OK = "OK";
	public static String Refresh = "Refresh";
	public static String Delete ="Delete";
		
	protected String url;
	protected WebDriver driver;	
	protected LoginController loginControl;
	protected UsersController userControl;
	protected ProductController productControl;
	protected AppDeviceController appDeviceControl;
	protected CompanyController companyControl;
	protected PromotionController promotionControl;
	protected AudioRouteController audioControl;
	protected TrainingController trainingControl;
	protected ReportsController reportsControl;
	
	protected ProductWorkflow productWf;
	protected UserWorkflow userWf;
	
	private boolean isInit = false;
	private static boolean isCloseBrowser = true;
	
	protected void initControllers(WebDriver driver) {
		loginControl = new LoginController(driver);
		userControl = new UsersController(driver);
		productControl = new ProductController(driver);
		appDeviceControl = new AppDeviceController(driver, url);
		companyControl = new CompanyController(driver);
		promotionControl = new PromotionController(driver);
		audioControl = new AudioRouteController(driver);
		trainingControl = new TrainingController(driver);
		reportsControl = new ReportsController(driver);
	};
	
	protected void initWorkflows(){
		productWf = new ProductWorkflow(loginControl, productControl, userControl);
		userWf = new UserWorkflow(userControl, loginControl);
	};
	
	protected abstract void initData();
	
	@Parameters({"browser", "url"})
	@BeforeMethod(alwaysRun = true)
	public void initializeDriver(@Optional("firefox") String browser, @Optional("https://devportal.dts.com/saap") String url) {
		if (!isInit) {
			try {
				this.url = url;
				if (browser.equalsIgnoreCase("firefox")) {
					System.out.println(" Executing on FireFox");
					System.setProperty("webdriver.gecko.driver", "webdrivers//geckodriver.exe");
					driver = new FirefoxDriver();
				} else if (browser.equalsIgnoreCase("chrome")) {
					System.out.println(" Executing on CHROME");
					System.setProperty("webdriver.chrome.driver", "webdrivers//chromedriver.exe");
					driver = new ChromeDriver();
				} else if (browser.equalsIgnoreCase("ie")) {
					System.out.println("Executing on IE");
					System.setProperty("webdriver.ie.driver", "webdrivers/IEDriverServer64.exe");
					driver = new InternetExplorerDriver();
				} else {
					throw new IllegalArgumentException("The Browser Type is Undefined");
				}
	
				driver.get(url);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);			
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);			
				driver.manage().window().maximize();			
				
				initControllers(driver);
				initWorkflows();
				initData();
				isInit = true;
			} catch (TimeoutException e) {
				System.out.println("Page load time out exception");
				driver.navigate().refresh();
			} catch (UnreachableBrowserException e) {
				System.out.println("Unreacheable browser exception");
				driver.navigate().refresh();
			}
		}
	}
	
	@BeforeMethod
	public static String getTCnameBefore(Method method)
	{
		System.out.println("Test name: " + method.getName());
		return method.getName();          
	}
	
//	@AfterClass
//	public void closeBrowser(){
//		if(driver == null){
//			return;
//		}
//		System.out.println("Close browser");
//		driver.close();
//	}		
	
	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult iTestResult) {
		if(driver == null)
			return;
		Reporter.setCurrentTestResult(iTestResult);
		MouseMover();
		if(!iTestResult.isSuccess()){
			System.setProperty("org.uncommons.reportng.escape-output", "false");
			try {
				File scrnsht = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				String destDir = "test-output/screenshots";
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
		if (isCloseBrowser) {
			// close browser after finish test case
			if(driver == null){
				return;
			}
			System.out.println("Close browser");
			driver.close();
		}
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
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuiteTest() {
		isCloseBrowser = false;
	}

	@AfterSuite(alwaysRun = true)
	public void afterSuiteTest() {
		if(driver == null){
			return;
		}
		System.out.println("Close browser");
		driver.close();
	}
}
