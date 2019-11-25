package com.dts.adminportal.util;

import org.testng.Reporter;

public class LogReporter {

	public static void addLog(String logmsg) {
		Reporter.log(logmsg + "</br>", true);
	}
	
	public static void addErrorLog(String logmsg) {
		Reporter.log("<font color='red'> " + logmsg + " </font></br>", true);
	}
	
	public static void addSuccessLog(String logmsg) {
		Reporter.log("<font color='green'> " + logmsg + " </font></br>", true);
	}
	
}
