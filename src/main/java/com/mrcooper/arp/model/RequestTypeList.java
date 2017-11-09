package com.mrcooper.arp.model;

import java.util.ArrayList;
import java.util.List;

public class RequestTypeList {
	private String applicationName;
	private int requestCode;
	private int applicationCode;
	private String requestName;

	private List<String> requestTypeList = new ArrayList<String>();

	public List<String> getRequestTypeList() {
		return requestTypeList;
	}

	public void setRequestTypeList(List<String> requestType) {
		this.requestTypeList = requestType;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public int getRequestCode() {
		return requestCode;
	}

	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public int getApplicationCode() {
		return applicationCode;
	}

	public void setApplicationCode(int applicationCode) {
		this.applicationCode = applicationCode;
	}


}
