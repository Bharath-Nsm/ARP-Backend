package com.mrcooper.arp.model;

import java.util.ArrayList;
import java.util.List;

public class ApplicationList {

	private String appName;
	private int appCode;

	private List<String> appList = new ArrayList<String>();

	//	public ApplicationList (List<String> appList) {
	//		this.appList = appList;
	//	}
	//
	//	public ApplicationList (String appName, int appCode) {
	//		this.appName = appName;
	//		this.appCode = appCode;
	//	}

	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getAppCode() {
		return appCode;
	}
	public void setAppCode(int appCode) {
		this.appCode = appCode;
	}

	public List<String> getAppList() {
		return appList;
	}

	public void setAppList(List<String> appList) {
		this.appList = appList;
	}
}
