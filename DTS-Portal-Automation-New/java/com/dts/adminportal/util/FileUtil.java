package com.dts.adminportal.util;

import java.io.File;

public class FileUtil {
	public static Boolean FileExist(String path) {

		File f = new File(path);

		if (f.exists()) {
			System.out.println("File existed");
			return true;
		} else {
			System.out.println("File not found!");
			return false;
		}
	}

	public static Boolean DeleteFile(String path) {
		try {

			File file = new File(path);
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
				return true;
			} else {
				System.out.println("Delete operation is failed.");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getPath(String fileName) {
	    String username = System.getProperty("user.name"); 
		return "C:\\Users\\" + username + "\\AppData\\Local\\Temp\\" + fileName;
	}
}
