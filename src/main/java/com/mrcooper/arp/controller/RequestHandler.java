package com.mrcooper.arp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mrcooper.arp.model.RequestTypeList;
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
	public List<String> getAllRequestType(@PathVariable("appName") String appName) {
		RequestTypeList requestTypeList = new RequestTypeList();

		requestTypeList.setApplicationName(appName);
		return requestProcessor.extractAllRequestType(requestTypeList).getRequestTypeList();
	}
}
