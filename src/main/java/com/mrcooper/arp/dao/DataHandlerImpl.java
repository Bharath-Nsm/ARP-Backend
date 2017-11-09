package com.mrcooper.arp.dao;

import static com.google.common.collect.Lists.newArrayList;
import static com.mrcooper.arp.cassandra.CassandraConfig.APPLICATION_ID;
import static com.mrcooper.arp.cassandra.CassandraConfig.APPLICATION_NAME;
import static com.mrcooper.arp.cassandra.CassandraConfig.APPLICATION_TABLE;
import static com.mrcooper.arp.cassandra.CassandraConfig.KEYSPACE;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_APP_ID;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_NAME;
import static com.mrcooper.arp.cassandra.CassandraConfig.REQUEST_TABLE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.mrcooper.arp.model.ApplicationList;
import com.mrcooper.arp.model.RequestTypeList;

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

	public List<String> extractAllRequestType(RequestTypeList requestTypeList) {
		Statement select = QueryBuilder
				.select().column(REQUEST_NAME)
				.from(KEYSPACE, REQUEST_TABLE).allowFiltering().where(QueryBuilder.eq(REQUEST_APP_ID, requestTypeList.getApplicationCode()));

		ResultSet resultSet = session.execute(select);

		List<String> reqList = newArrayList();

		for (Row row : resultSet)
			reqList.add(row.getString(REQUEST_NAME));

		//		requestTypeList.setRequestType(reqList);

		return reqList;
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

}
