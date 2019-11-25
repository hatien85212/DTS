package com.dts.adminportal.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {
	static String pattern = "MMM dd, yyyy";
	public static void main(String[] args) {
//		String s = "May 18, 2014";//"16-Jul-2009 05:20:18 PDT";
//		String pattern = "MMM dd, yyyy";//"dd-MMM-yyyy HH:mm:ss z";
//		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
//		DateTime start = formatter.parseDateTime(s);
//		System.out.println(start);
//		DateTime end = DateTime.now();
//		System.out.println(end);
//		int daysBetween = Days.daysBetween(start.withTimeAtStartOfDay() , end.withTimeAtStartOfDay() ).getDays();
//		System.out.println("Days between : "+ daysBetween);
		
		String date = formatDate("yyyy-MM-dd", "MMMM dd, yyyy", "2015-06-03");
		System.out.println(date);
	
	}
	public static DateTime fomaterDay(String date){
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		DateTime dateTime = formatter.parseDateTime(date);
		return dateTime;
	}
	public static Boolean isBetween(String startDate, int total){
		DateTime start = fomaterDay(startDate);
		DateTime end = DateTime.now();
		int days = Days.daysBetween(start.withTimeAtStartOfDay() , end.withTimeAtStartOfDay() ).getDays();
		System.out.println("Start  : "+start + "total days : "+days);
		if(days > total){
			return false;
		}else {
			return true;
		}
	}
	public static boolean isBeforeNow(String time) {
		DateTime start = fomaterDay(time);
		if(start.isBeforeNow()){
			return true;
		}else {
			return false;
		}
	}
	
	public static String getADateGreaterThanToday(String dateFormat, int days){
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return df.format(cal.getTime());
		
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	
	public static String formatDate(String oldFormat, String newFormat, String date) {
		try{
		Date newDate = new SimpleDateFormat(oldFormat).parse(date);
		return new SimpleDateFormat(newFormat).format(newDate);
	}catch (ParseException e){
		e.printStackTrace();
		return null;
	}
	}
	
	
	
	
}
