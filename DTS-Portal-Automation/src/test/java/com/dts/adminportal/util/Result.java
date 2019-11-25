package com.dts.adminportal.util;

import java.util.ArrayList;

import org.testng.Reporter;

public class Result {
	protected String result;
	protected String logmsg;
	protected ArrayList<String> listItem = new ArrayList<String>();
	protected String expected;
	public Result() {
		super();
		this.result = "Pass";
		this.logmsg = "";
		this.expected = "";
	}
	
	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public ArrayList<String> getListItem() {
		return listItem;
	}

	public void setListItem(ArrayList<String> listItem) {
		this.listItem = listItem;
	}
	public void addItem(String item){
		listItem.add(item);
	}
	public void addLog(String logmsg) {
		Reporter.log(logmsg + "</br>", true);
	}
	
	public void addErrorLog(String logmsg) {
		Reporter.log("<font color='red'> " + logmsg + " </font></br>", true);
	}
	
	public void addSuccessLog(String logmsg) {
		Reporter.log("<font color='green'> " + logmsg + " </font></br>", true);
	}

	public void reset() {
		this.result = "Pass";
		this.logmsg = "";
		this.expected = "";
	}

	public String getLogmsg() {
		return logmsg;
	}

	public void setLogmsg(String logmsg) {
		this.logmsg = logmsg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
