package com.dts.adminportal.util;

import java.util.ArrayList;
import java.util.Hashtable;

public class DTSUtil {
	static Result result = new Result();
	
	public static Boolean containsAll(ArrayList<String> array1,
			ArrayList<String> array2) {
		// array 1 contains array 2
		for (String item : array2) {
			if (array1.contains(item)) {
				result.addSuccessLog("Array 1 contains: " + item);
			} else {
				result.addErrorLog("Array 1 not contain: " + item);
				return false;
			}
		}
		return true;
	}
	
	public static Boolean containsAll(Hashtable<String,String> data, ArrayList<String> list){
		for (String item2 : list) {
			Boolean flag = false;
			for (String item1 : data.keySet()) {
				if(data.get(item1).contains(item2)){
					flag = true;
					result.addSuccessLog(data.get(item1) +" : "+ item2);
					break;
				}
			}
			if(!flag){
				result.addErrorLog("Array 1 not contains: " + item2);
				return flag;
			}
		}
		return true;
	}
	
	public static Boolean containsAll(ArrayList<String> list, Hashtable<String,String> data){
		for (String item2 : data.keySet()) {
			Boolean flag = false;
			for (String item1 : list) {
				if(item1.contains(data.get(item2))){
					flag = true;
					System.err.println(item1 +" : "+ data.get(item2));
					break;
				}
			}
			if(!flag){
				return flag;
			}
		}
		return true;
	}

	public static Boolean containsAll(ArrayList<String> options, String[] language) {
		ArrayList<String> laguageList = new ArrayList<String>();
		for (int i = 0; i < language.length; i++) {
			laguageList.add(language[i]);
		}
		return containsAll(options, laguageList);
	}

	public static boolean containsListText(String text, String[] list) {
		Boolean flag = true;
		for (String item : list) {
			if (text.contains(item)) {
				result.addSuccessLog("item found : " + item);
				flag = true;
			} else {
				result.addErrorLog("not found: " + item);
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	public static boolean containsListText(String text, ArrayList<String> list) {
		Boolean flag = true;
		for (String item : list) {
			if (text.contains(item)) {
				result.addSuccessLog("item found : " + item);
				flag = true;
			} else {
				result.addErrorLog("not found: " + item);
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	public static boolean checkListContain(ArrayList<String> list , String text) {
		for (String item : list) {
			if (item.equals(text)) {
				System.out.println("List contains: " + text);
				 return true;
			}
		}
		System.out.println("List not contain : " + text);
		return false;
	}

	public static Boolean containOnlyString(ArrayList<String> columns, String text) {
		System.out.println("Check All data on array list only contain : "+text);
		while(columns.contains(text)){
			columns.remove(text);
		}
		if(columns.size() == 0){
			System.out.println("All data are : "+ text);
			return true;
		}else {
			for (int i = 0; i < columns.size(); i++) {
				System.err.println("data incorrect : "+columns.get(i));
			}
			return false;
		}
	}
}
