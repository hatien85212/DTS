package com.dts.adminportal.autotools;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import autoitx4java.AutoItX;

import com.jacob.com.LibraryLoader;

public class GuiAutoToolWin implements IGuiAutoTool {
	
	private static GuiAutoToolWin guiAutoToolWin;
	private AutoItX autoIT;	
	private static Robot robot;
	
	static{
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	private GuiAutoToolWin(){
		String jacobDllVersionToUse = System.getProperty("sun.arch.data.model");
		System.out.println("sun.arch.data.model: " + jacobDllVersionToUse);
		if (jacobDllVersionToUse.contains("32")) {
			jacobDllVersionToUse = "jacob-1.18-M2-x86.dll";
		} else {
			jacobDllVersionToUse = "jacob-1.18-M2-x64.dll";
		}
		System.out.println("Jacob version: " + jacobDllVersionToUse);
		File file = new File("lib/jacob/jacobm2/1.0", jacobDllVersionToUse);
		System.setProperty(LibraryLoader.JACOB_DLL_PATH, file.getAbsolutePath());
		autoIT = new AutoItX();
	}
	
	public static GuiAutoToolWin getGuiAutoToolInstance(){
		if(guiAutoToolWin == null){
			guiAutoToolWin = new GuiAutoToolWin();
		}
		return guiAutoToolWin;
	}

	public void winWait(String title, String text, int timeout) {
		autoIT.winWait(title, text, timeout);
		
	}

	public void winActivate(String title) {
		autoIT.winActive(title);
		
	}

	public void ControlSetText(String title, String text, String control, String filePath) {
		autoIT.ControlSetText(title, text, control, filePath);
	}

	public void controlClick(String title, String text, String controlID) {
		autoIT.controlClick(title, text, controlID);
		
	}

	public boolean winExists(String title) {
		return autoIT.winExists(title);
	}

	public String winGetHandle(String title) {
		return autoIT.winGetHandle(title);		
	}

	public void send(String keys, boolean isRaw) {
		autoIT.send(keys, isRaw);
	}


	public void mouseWheel(String direction, int clicks) {
		autoIT.mouseWheel(direction, clicks);
//		robot.mouseWheel(clicks);
//		//autoIT.mouseWheel();
	}

	public void winWait(String title) {
		autoIT.winWait(title);		
	}

	public void winMove(String title, String text, int x, int y, int width, int height) {
		autoIT.winMove(title, text, x, y, width, height);
		
	}

	public void winClose(String title) {
		autoIT.winClose(title);		
	}

	public String winGetTitle(String title) {
		return autoIT.winGetTitle(title);		
	}
	
	// Release and Renew Network
	public void releaseRenewNetwork(String status){
		if (status=="release"){
			try {
				Runtime.getRuntime().exec("ipconfig /release");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		if (status=="renew"){
			try {
				Runtime.getRuntime().exec("ipconfig /renew");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}

}

