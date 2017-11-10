package com.mrcooper.arp.dao;

import java.util.HashMap;
import java.util.List;

import com.mrcooper.arp.model.ApplicationList;
import com.mrcooper.arp.model.NewRequest;
import com.mrcooper.arp.model.RequestProcessData;
import com.mrcooper.arp.model.RequestTypeList;
import com.mrcooper.arp.model.TicketList;

public interface DataHandler {

	List<String> getApplicationList();
	void insert(ApplicationList applicationList);

	List<HashMap<String, Object>> extractAllRequestType(RequestTypeList requestTypeList);
	int getApplicationCode(String applicationName);

	RequestProcessData getRequestData(RequestProcessData requestProcessData);

	int getTicketNumber();

	int updateTicketNumber(int oldNum);

	int createTicket(NewRequest newRequest);

	TicketList getAllTicket(TicketList ticketList);
}
