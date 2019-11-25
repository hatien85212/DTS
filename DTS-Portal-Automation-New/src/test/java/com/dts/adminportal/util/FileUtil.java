package com.dts.adminportal.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
	public static boolean extractFile(String fileName){
		
		return true;
	}
	public static void unzip(String zipFilePath, String destDirectory){
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn;
		try {
			zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry;
			entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[4096];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }
    public static void main(String[] args) {
		//FileUtil.unzip("C:\\Users\\be.tran\\Downloads\\usb_bluetooth_wired_1506399229306_auto.hpxtt", "C:\\Users\\be.tran\\Downloads\\usb_bluetooth_wired_1506399229306_auto");
    	String foo = "title part1.txt";
    	foo = foo.substring(0, foo.lastIndexOf('.'));
    	System.out.println(foo);
	}
}
