package com.mrcooper.arp.dao;

import static com.google.common.collect.Lists.newArrayList;
import static com.mrcooper.arp.cassandra.CassandraConfig.APPLICATION_ID;
import static com.mrcooper.arp.cassandra.CassandraConfig.APPLICATION_NAME;
import static com.mrcooper.arp.cassandra.CassandraConfig.APPLICATION_TABLE;
import static com.mrcooper.arp.cassandra.CassandraConfig.INCREMENT_TABLE;
import static com.mrcooper.arp.cassandra.CassandraConfig.KEYSPACE;
import static com.mrcooper.arp.cassandra.CassandraConfig.OLD_ID;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_APP_ID;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_CODE;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_NAME;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_PARM;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_SCRIPT;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_SERVER;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_TABLE;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_URL;
import static com.mrcooper.arp.cassandra.CassandraConfig.TICKET_APP_NAME;
import static com.mrcooper.arp.cassandra.CassandraConfig.TICKET_HEADER;
import static com.mrcooper.arp.cassandra.CassandraConfig.TICKET_ID;
import static com.mrcooper.arp.cassandra.CassandraConfig.TICKET_STATUS;
import static com.mrcooper.arp.cassandra.CassandraConfig.TICKET_TABLE;
import static com.mrcooper.arp.cassandra.CassandraConfig.TICKET_TIME_STAMP;
import static com.mrcooper.arp.cassandra.CassandraConfig.TICKET_USER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.mrcooper.arp.model.ApplicationList;
import com.mrcooper.arp.model.NewRequest;
import com.mrcooper.arp.model.RequestProcessData;
import com.mrcooper.arp.model.RequestTypeList;
import com.mrcooper.arp.model.TicketList;

@Repository
public class DataHandlerImpl implements DataHandler {

	private final Session session;

	@Autowired
	public DataHandlerImpl(Session session) {
		this.session = session;
	}

	public List<String> getApplicationList() {
		Statement select = QueryBuilder
				.select().column(APPLICATION_NAME)
				.from(KEYSPACE, APPLICATION_TABLE);

		ResultSet resultSet = session.execute(select);

		List<String> appNameList = newArrayList();
		for (Row row : resultSet)
			appNameList.add(row.getString(APPLICATION_NAME));

		return appNameList;
	}

	public void insert(ApplicationList applicationList) {
		Statement insert = QueryBuilder
				.insertInto(APPLICATION_TABLE)
				.value(APPLICATION_NAME, applicationList.getAppName())
				.value(APPLICATION_ID, applicationList.getAppCode());

		session.execute(insert);

	}

	public List<HashMap<String, Object>> extractAllRequestType(RequestTypeList requestTypeList) {
		Statement select = QueryBuilder
				.select().all()
				.from(KEYSPACE, REQUEST_TABLE).allowFiltering().where(QueryBuilder.eq(REQUEST_APP_ID, requestTypeList.getApplicationCode()));

		ResultSet resultSet = session.execute(select);

		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		for (Row row : resultSet) {
			HashMap<String, Object> reqMap = new HashMap<String, Object>();
			reqMap.put(REQUEST_NAME, (row.getString(REQUEST_NAME)));
			reqMap.put(REQUEST_CODE, (row.getInt(REQUEST_CODE)));

			mapList.add(reqMap);
		}

		//		requestTypeList.setRequestType(reqList);

		return mapList;
	}

	public int getApplicationCode(String applicationName) {
		Statement select = QueryBuilder
				.select().column(APPLICATION_ID)
				.from(KEYSPACE, APPLICATION_TABLE).where(QueryBuilder.eq(APPLICATION_NAME, applicationName)).limit(1);
		ResultSet resultSet = session.execute(select);

		int cnt = 0;
		for (Row row : resultSet)
			cnt = row.getInt(APPLICATION_ID);

		return cnt;
	}

	public RequestProcessData getRequestData(RequestProcessData requestProcessData) {
		Statement select = QueryBuilder
				.select().all()
				.from(KEYSPACE, REQUEST_TABLE).where(QueryBuilder.eq(REQUEST_CODE, requestProcessData.getReqCode())).limit(1);

		ResultSet resultSet = session.execute(select);

		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		for (Row row : resultSet) {
			HashMap<String, Object> reqDataMap = new HashMap<String, Object>();

			reqDataMap.put(REQUEST_CODE, row.getInt(REQUEST_CODE));
			reqDataMap.put(REQUEST_NAME, row.getString(REQUEST_NAME));
			reqDataMap.put(REQUEST_URL, row.getString(REQUEST_URL));
			reqDataMap.put(REQUEST_SERVER, row.getString(REQUEST_SERVER));
			reqDataMap.put(REQUEST_SCRIPT, row.getString(REQUEST_SCRIPT));

			List<String> parmList = new ArrayList<String>();
			String[] parmArr =row.getString(REQUEST_PARM).split(",");

			for(String str : parmArr)
				parmList.add(str);

			reqDataMap.put(REQUEST_PARM, parmList);

			mapList.add(reqDataMap);
		}

		requestProcessData.setDataList(mapList);

		return requestProcessData;
	}

	public int createTicket(NewRequest newRequest) {
		Statement insert = QueryBuilder
				.insertInto(TICKET_TABLE)
				.value(TICKET_ID, newRequest.getTicNum())
				.value(TICKET_HEADER, newRequest.getTicHeader())
				.value(TICKET_APP_NAME, newRequest.getAppName())
				.value(TICKET_STATUS, newRequest.getTicStatus())
				.value(TICKET_USER, newRequest.getTicUser())
				.value(TICKET_TIME_STAMP, newRequest.getTicTS());

		ResultSet resultSet = session.execute(insert);

		if (resultSet.wasApplied())
			return 0;
		return 1;
	}

	public int getTicketNumber() {
		Statement select = QueryBuilder
				.select().all()
				.from(KEYSPACE, INCREMENT_TABLE).limit(1);

		ResultSet resultSet = session.execute(select);

		for (Row row : resultSet)
			return row.getInt(OLD_ID);

		return 0;
	}

	public int updateTicketNumber(int oldNum) {
		Statement update = QueryBuilder.update("INCREMENT_TABLE").with(QueryBuilder.incr(OLD_ID, 1));
		ResultSet resultSet = session.execute(update);

		if (resultSet.wasApplied())
			return 0;
		return 1;
	}

	public TicketList getAllTicket(TicketList ticketList) {
		Statement select = QueryBuilder
				.select().all()
				.from(KEYSPACE, TICKET_TABLE).allowFiltering().where(QueryBuilder.eq(TICKET_APP_NAME, ticketList.getAppName()));

		ResultSet resultSet = session.execute(select);

		List<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		for (Row row : resultSet) {
			HashMap<String, Object> reqDataMap = new HashMap<String, Object>();

			reqDataMap.put(TICKET_ID, row.getInt(TICKET_ID));
			reqDataMap.put(TICKET_HEADER, row.getString(TICKET_HEADER));
			reqDataMap.put(TICKET_APP_NAME, row.getString(TICKET_APP_NAME));
			reqDataMap.put(TICKET_STATUS, row.getString(TICKET_STATUS));
			reqDataMap.put(TICKET_USER, row.getString(TICKET_USER));
			reqDataMap.put(TICKET_TIME_STAMP, row.getString(TICKET_TIME_STAMP));

			mapList.add(reqDataMap);
		}

		ticketList.setRecordList(mapList);

		return ticketList;
	}

}
