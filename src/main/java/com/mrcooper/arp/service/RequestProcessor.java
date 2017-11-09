package com.mrcooper.arp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrcooper.arp.dao.DataHandler;
import com.mrcooper.arp.model.ApplicationList;
import com.mrcooper.arp.model.RequestTypeList;

@Service
public class RequestProcessor {
	@Autowired
	private DataHandler dataHandler;

	//	@Autowired
	//	public RequestProcessor(DataHandler dataHandler) {
	//		this.dataHandler = dataHandler;
	//	}

	public ApplicationList index() {
		ApplicationList applicationList = new ApplicationList();

		applicationList.setAppList(dataHandler.getApplicationList());
		return applicationList;
	}

	public RequestTypeList extractAllRequestType(RequestTypeList requestTypeList) {
		requestTypeList.setApplicationCode(dataHandler.getApplicationCode(requestTypeList.getApplicationName())) ;
		requestTypeList.setRequestTypeList(dataHandler.extractAllRequestType(requestTypeList));
		return requestTypeList;
	}
}
