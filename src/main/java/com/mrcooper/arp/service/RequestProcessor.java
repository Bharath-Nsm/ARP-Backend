package com.mrcooper.arp.service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrcooper.arp.dao.DataHandler;
import com.mrcooper.arp.model.ApplicationList;
import com.mrcooper.arp.model.NewRequest;
import com.mrcooper.arp.model.RequestProcessData;
import com.mrcooper.arp.model.RequestTypeList;
import com.mrcooper.arp.model.ResponseMessage;
import com.mrcooper.arp.model.TicketList;

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
		requestTypeList.setReqList(dataHandler.extractAllRequestType(requestTypeList));
		return requestTypeList;
	}

	public RequestProcessData extractDataList(RequestProcessData requestProcessData) {
		return dataHandler.getRequestData(requestProcessData);
	}

	public ResponseMessage createNewTicket(NewRequest newRequest) {
		String timeStamp = new SimpleDateFormat("dd/MM/yyyy.HH.mm.ss").format(new java.util.Date());
		newRequest.setTicNum(dataHandler.getTicketNumber());
		newRequest.setTicStatus("Open");
		newRequest.setTicTS(timeStamp);

		ResponseMessage responseMessage;

		if(dataHandler.updateTicketNumber(newRequest.getTicNum()) == 0 ) {


			if(dataHandler.createTicket(newRequest) == 0)
				responseMessage = new ResponseMessage(0, "Insert Successful");
			else
				responseMessage = new ResponseMessage(1, "Insert Failed");
		} else
			responseMessage = new ResponseMessage(1, "Update Failed, Insert not done");

		return responseMessage;
	}

	public TicketList extractAppTicket(String appName) {
		TicketList ticketList = new TicketList();
		ticketList.setAppName(appName);
		return dataHandler.getAllTicket(ticketList);
	}
}
