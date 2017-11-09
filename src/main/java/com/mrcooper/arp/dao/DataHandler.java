package com.mrcooper.arp.dao;

import java.util.List;

import com.mrcooper.arp.model.ApplicationList;
import com.mrcooper.arp.model.RequestTypeList;

public interface DataHandler {

	List<String> getApplicationList();
	void insert(ApplicationList applicationList);

	List<String> extractAllRequestType(RequestTypeList requestTypeList);
	int getApplicationCode(String applicationName);
}
