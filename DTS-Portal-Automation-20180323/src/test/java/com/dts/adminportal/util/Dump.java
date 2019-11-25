package com.dts.adminportal.util;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import com.dts.adminportal.autotools.GuiAutoToolWin;
import com.dts.adminportal.autotools.IGuiAutoTool;

public class Dump {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
//			File path = new File(System.getProperty("user.dir") + File.separator + "tools");
			String path = System.getProperty("user.dir") + File.separator + "tools\\";
			System.out.println(System.getProperty("user.dir"));
//			Desktop.getDesktop().open(path);
//			IGuiAutoTool autoTool = GuiAutoToolWin.getGuiAutoToolInstance();;
//			autoTool.winActivate("tools");
//			autoTool.controlClick("tools","","[CLASS:ToolbarWindow32; INSTANCE:2]");
//			autoTool.send("{BACKSPACE}",false);
//			autoTool.ControlSend("tools","","[CLASS:Edit; INSTANCE:1]","cmd");
//			autoTool.controlClick("tools","","[CLASS:ToolbarWindow32; INSTANCE:3]");
			Runtime rt = Runtime.getRuntime();
			rt.exec("cmd.exe /c cd "+path+" & /c start dts-dtscs-dump.exe cc74ff82-d710-46be-b6cc-5c27163949ab.dtscs > cc74ff82-d710-46be-b6cc-5c27163949ab.dtscs.txt");
//			String cmd = "C:"+File.separator+"WINDOWS"+File.separator+"system32"+File.separator+"cmd.exe";
//			autoTool.ControlSend(cmd,"","","dts-dtscs-dump.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	}

}
