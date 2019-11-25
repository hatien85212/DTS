package com.dts.adminportal.util;

import java.util.ArrayList;
import java.util.Hashtable;


public class ListUtil extends LogReporter{
	
	public static Boolean containsAll(ArrayList<String> array1,	ArrayList<String> array2) {
		// array 1 contains array 2
		for (String item : array2) {
			if (array1.contains(item)) {
				addSuccessLog("Array 1 contains: " + item);
			} else {
				addErrorLog("Array 1 not contain: " + item);
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
					addSuccessLog(data.get(item1) +" : "+ item2);
					break;
				}
			}
			if(!flag){
				addErrorLog("Array 1 not contains: " + item2);
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
				addSuccessLog("item found : " + item);
				flag = true;
			} else {
				addErrorLog("not found: " + item);
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
				addSuccessLog("item found : " + item);
				flag = true;
			} else {
				addErrorLog("not found: " + item);
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	public static boolean checkListContainSubString(ArrayList<String> list , String text) {
		for (String item : list) {
			if (item.contains(text)) {
				System.out.println("List contains: " + text);
				 return true;
			}
		}
		System.out.println("List not contain : " + text);
		return false;
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
	public static boolean checkArrayContain(String[] list , String text) {
		for (String item : list) {
			if (item.equals(text)) {
				System.out.println("Array contains: " + text);
				 return true;
			}
		}
		System.out.println("Array not contain : " + text);
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
	
	public static Boolean containOnlyStringAppDevice(ArrayList<String> columns, String text) {
		System.out.println("Check All data on array list only contain : "+text);
		int i = 0;
		while (i < columns.size()) {
			if (!columns.get(i).equals(text)) {
				return false;
			} else {
				i++;
			}
		}
		return true;
	}
	public static Boolean isEquals(ArrayList<String> list ,String[]arr){
		Boolean flag=true;
		if(list.size()!=arr.length){
			return false;
		}else{
			for (int i = 0; i < arr.length; i++) {
			flag = checkListContain(list, arr[i]);
			if (flag==false){
				return false;
			}
			}
		}		
	return flag;	
	}
public static Boolean isEquals(String[] list ,String[]arr){
	Boolean flag=true;
	if(list.length!=arr.length){
		flag=false;
	}else{
		for (int i = 0; i < arr.length; i++) {
		flag = checkArrayContain(list, arr[i]);
		if (flag==false){
			return false;
		}
		}
	}		
return flag;	
}
public static Boolean isEquals(ArrayList<String> list1 ,ArrayList<String> list2){
	Boolean flag=true;
	if(list1.size()!=list2.size()){
		return false;
	}else{
		for (int i = 0; i < list2.size(); i++) {
		flag = checkListContain(list1, list2.get(i));
		if (flag==false){
			return false;
		}
		}
	}		
return flag;	
}
}
