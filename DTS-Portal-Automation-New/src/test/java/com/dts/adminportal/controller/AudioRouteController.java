package com.dts.adminportal.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.dts.adminportal.model.AddEditProductModel;
import com.dts.adminportal.model.AudioRoutes;
import com.dts.adminportal.model.AudioRoutesEdit;
import com.dts.adminportal.model.AudioRoutesInfo;
import com.dts.adminportal.model.LanguagePackage;
import com.dts.adminportal.model.RoomModelInfo;
import com.dts.adminportal.util.FileUtil;
import com.dts.pb.dcc.Accessorypb.Accessory;
import com.dts.pb.dcc.Specpb.OutputChain;
import com.dts.pb.dcc.dtscs.Dtscs;

public class AudioRouteController extends BaseController {
	BaseController audioControl;
	BaseController appDeviceControl;
	public AudioRouteController(WebDriver driver) {
		super(driver);
		audioControl = new BaseController(driver);
		appDeviceControl = new BaseController(driver);
	}
	
	public ArrayList<String> getAudioRouteName(String Xpath) {
		waitForAjax();
		ArrayList<String> arrayList = new ArrayList<String>();
		try {
			// Find the AudioRoute name container
			WebElement element = driver.findElement(By.xpath(Xpath));
			// Get all spans
			List<WebElement> each_span = element.findElements(By
					.tagName("span"));
			// Get text from each span
			for (WebElement item : each_span) {
				String name = item.getText();
				arrayList.add(name);
			}
		} catch (NoSuchElementException e) {
		}
		return arrayList;
	}
	
	// TODO Replace below function with generic function in basePage
	public void selectAnAudioRouteByName(String name){
		// Get audio route container
		List<WebElement> audioRoutes = driver.findElement(By.id("router-audio-brand-info")).findElements(By.tagName("a"));
		for(WebElement audioRoute : audioRoutes){
			if(audioRoute.getText().contains(name)){
				addLog("Select audio route: " + name);
				audioRoute.click();
				waitForAjax();
				return;
			}
		}
		addErrorLog("Audio route: " + name + " not found");
	}
	
	public ArrayList<String> addAllLanguage(String Xpath) {
		try {
			waitForAjax();
			ArrayList<String> list = new ArrayList<String>();
			WebElement anotherpackage = driver.findElement(By.xpath(Xpath));

			for (int i = 0; i < 9; i++) {
				List<WebElement> all_package = anotherpackage.findElements(By.tagName("div"));
				addLog("Add language: " + AudioRoutes.LANGUAGE_LIST[i + 1] + " : " + AudioRoutes.NAME_LIST[i + 1]);
				selectOptionByName(all_package.get(i).findElement(By.tagName("select")),
						AudioRoutes.LANGUAGE_LIST[i + 1]);
				editData(all_package.get(i).findElement(By.tagName("input")), AudioRoutes.NAME_LIST[i + 1]);
				list.add(AudioRoutes.LANGUAGE_LIST[i + 1] + " : " + AudioRoutes.NAME_LIST[i + 1]);
			}
			return list;

		} catch (NoSuchElementException e) {
			addErrorLog("NoSuchElementException" + Xpath);
			return null;
		}
	}
	
	public void editVersion() {
		String publish_status = audioControl .getTextByXpath(RoomModelInfo.PUBLISH_STATUS);
		if(publish_status.equals("Published"))
		{
			audioControl.click(RoomModelInfo.EDIT);
			audioControl.selectConfirmationDialogOption("OK");
		} else audioControl.click(RoomModelInfo.EDIT);
	}
	
	public void uploadFileTuning(String xpath,String filename){
		
		if(audioControl.isElementPresent(xpath)){
			audioControl.uploadTuningAudioRoute(xpath,filename);
		}else {
			WebElement delete_icon = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[1]/td[2]/img"));
			audioControl.click(delete_icon);
			audioControl.selectConfirmationDialogOption("Delete");
			audioControl.uploadTuningAudioRoute(xpath,filename);
		}
	}
	
	public void uploadInvalidFileTuning(String xpath){
		WebElement delete_icon = driver.findElement(By.xpath("//*[contains(@id,'tuning-fileupload-')]/table/tbody/tr[1]/td[2]/img"));
		if(audioControl.isElementPresent(xpath)){
			audioControl.uploadFile(xpath,AddEditProductModel.FileUpload.Tuning_Invalid.getName());
		}else {
			audioControl.click(delete_icon);
			audioControl.selectConfirmationDialogOption("Delete");
			audioControl.uploadFile(xpath,AddEditProductModel.FileUpload.Tuning_Invalid.getName());
		}
	}

	
	public void publishAudioRoute(String filename){
		String publish_status = audioControl.getTextByXpath(AudioRoutesInfo.PUBLISH_STATUS);
		if(publish_status.equals("Draft"))
		{
			appDeviceControl.click(AudioRoutesInfo.EDIT);
			if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_TUNING))
			{
					appDeviceControl.uploadTuningAudioRoute(AudioRoutesEdit.ADD_TUNING,filename);
			};
			if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE250)){
				appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE250,AddEditProductModel.FileUpload.IMG_250_JPG.getName());
			};
			if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE500)){
				appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE500,AddEditProductModel.FileUpload.IMG_500_JPG.getName());
			};
			if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE1000)){
				appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE1000,AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
			};
			appDeviceControl.click(AudioRoutesEdit.SAVE);
			appDeviceControl.click(AudioRoutesInfo.PUBLISH);
		}
		
	}
	
	public void uploadImages(){
		if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE250)){
			appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE250,AddEditProductModel.FileUpload.IMG_250_JPG.getName());
		};
		if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE500)){
			appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE500,AddEditProductModel.FileUpload.IMG_500_JPG.getName());
		};
		if(appDeviceControl.isElementPresent(AudioRoutesEdit.ADD_IMAGE1000)){
			appDeviceControl.uploadFile(AudioRoutesEdit.ADD_IMAGE1000,AddEditProductModel.FileUpload.IMG_1000_JPG.getName());
		}
		
	}
	
	public void donwloadFilesuccess() {
//	String className = driver.findElement(By.className("uploaded-tunings")).getText();
//	WebElement file_link = driver.findElement(By.linkText(className));
//	// Click on download link
//	audioControl.click(file_link);
	driver.findElement(By.partialLinkText(".dtscs")).click();;
	// Get file name generated when download to local
	audioControl.autoTool.winWait("[Class:MozillaDialogClass]", "", 10);
	String file_name_download = audioControl.autoTool.winGetTitle("[Class:MozillaDialogClass]").replace("Opening ", "");
	// Check if file exist and delete
	String file_path = System.getProperty("user.home") + "\\Downloads\\" + file_name_download;
	File file = new File(file_path);
	if(file.exists()){
		file.delete();
	}
	// Download file
	audioControl.addLog("Start download file: " + file_name_download);
	audioControl.handleFirefoxDownloadDialog("Save File");
	for (int i = 1; i <= 10; i++) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (FileUtil.FileExist(file_path)) {
			audioControl.addLog("File: " + file_name_download + " is downloaded successfully");
			Assert.assertTrue(true);
		}
		else
		{
		audioControl.addLog("File: " + file_name_download + "is not downloaded successfully");
		Assert.assertTrue(false);
		}
	}
	}
	
	public void deleteAllLang(List<LanguagePackage> languages){
		for(int i=0;i<languages.size();i++){
			audioControl.click(AudioRoutesEdit.getDeleteLangIcon(i+1));
		}
	}
	
	// Check route file
	public boolean checkRouteFile(String dtsId, String audioRoute) {
		boolean check = false;
		String file = System.getProperty("user.home")+"/Downloads/profile_"+dtsId+".dtscs";
		String route;
		Dtscs dtscs;
		try {
			dtscs = Dtscs.parseFrom(
					new FileInputStream(new File(file)));
			List<Accessory> listAccessory = dtscs.getProfile(0).getAccessoryCollectionList();
			for (Accessory accessory : listAccessory) {
				route = accessory.getSpec().getClassification(0).getSupportedOutputChains(0).getRoute().getRoute().toString();
				if (route.equals(audioRoute)) {
					check = true;
					System.out.println(audioRoute+" is contained in file");
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(audioRoute+" is not contained in file");
        return check;
	}
	
	public String checkEalgeVersion(String UUID) {
		String eagle_Version = "";
		Dtscs dtscs;
		String file = "profile_" + UUID + ".dtscs";
		String file_path = System.getProperty("user.home") + "\\Downloads\\" + file;
		try {
			dtscs = Dtscs.parseFrom(
					new FileInputStream(new File(file_path)));
			List<OutputChain> chainsList = dtscs.getProfile(0).getAccessoryCollection(0).getSpec().getClassification(0).getSupportedOutputChainsList();
			for (OutputChain chains : chainsList) {
				if (chains.getRoute().getTech(0).getEagleV11() != null) {
					eagle_Version = "Eagle 1.4";
				} else if (chains.getRoute().getTech(0).getEagleV20() != null){
					eagle_Version = "Eagle 2.0";
				} else {
					System.out.println("The audio route profile failed");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return eagle_Version;
	}
}
