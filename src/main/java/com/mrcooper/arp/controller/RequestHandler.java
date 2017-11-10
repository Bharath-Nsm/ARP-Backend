package com.mrcooper.arp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrcooper.arp.model.NewRequest;
import com.mrcooper.arp.model.RequestProcessData;
import com.mrcooper.arp.model.RequestTypeList;
import com.mrcooper.arp.model.ResponseMessage;
import com.mrcooper.arp.service.RequestProcessor;

@Controller
@RequestMapping("/arp")
public class RequestHandler {
	@Autowired
	private RequestProcessor requestProcessor;

	//	@Autowired
	//	public RequestHandler(RequestProcessor requestProcessor) {
	//		this.requestProcessor = requestProcessor;
	//	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public List<String> indexTest() {
		return requestProcessor.index().getAppList();
	}

	@RequestMapping(value = "/extract/requestType/{appName}", method = RequestMethod.GET)
	@ResponseBody
	public List<HashMap<String, Object>> getAllRequestType(@PathVariable("appName") String appName) {
		RequestTypeList requestTypeList = new RequestTypeList();

		requestTypeList.setApplicationName(appName);
		return requestProcessor.extractAllRequestType(requestTypeList).getReqList();
	}

	@RequestMapping(value = "/extract/requestData/{appName}/{reqCode}", method = RequestMethod.GET)
	@ResponseBody
	public List<HashMap<String, Object>> getAllRequestType(@PathVariable("appName") String appName, @PathVariable("reqCode") int reqCode) {
		RequestProcessData reqData = new RequestProcessData();

		reqData.setAppName(appName);
		reqData.setReqCode(reqCode);

		return requestProcessor.extractDataList(reqData).getDataList();
	}

	@RequestMapping(value = "/newRequest", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage createTicket(@RequestBody NewRequest newRequest) {

		return requestProcessor.createNewTicket(newRequest);
	}

	@RequestMapping(value = "/getAppTickets/{appName}", method = RequestMethod.GET)
	@ResponseBody
	public List<HashMap<String, Object>> getAppTicket(@PathVariable("appName") String appName) {

		return requestProcessor.extractAppTicket(appName).getRecordList();
	}
}
