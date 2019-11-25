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
	public static DateTime fomaterDay(String date){
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		DateTime dateTime = formatter.parseDateTime(date);
		return dateTime;
	}
	public static String getCurrent_DDMMMYYYY(){
		DateFormat dateFormat = new SimpleDateFormat("(dd MMM yyyy)");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		return dateFormat.format(date).toString();
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
	
	public static void main(String[] args) {
		DateUtil date = new DateUtil();
		System.out.println(date.getCurrent_DDMMMYYYY());
	}
	
	
}
