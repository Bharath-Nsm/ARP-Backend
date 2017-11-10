package com.mrcooper.arp.cassandra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.Session;

@Component
public class CassandraSchemaInit {

	@Autowired
	private Session session;

	public void init() {
		String createKS = String.format("CREATE KEYSPACE IF NOT EXISTS %s " +
				"WITH REPLICATION = { 'class': 'SimpleStrategy', 'replication_factor': '2' }",
				CassandraConfig.KEYSPACE);
		session.execute(createKS);

		String createApplicationTbl = String.format("CREATE TABLE IF NOT EXISTS %s.%s(%s text, %s int, PRIMARY KEY(%s));",
				CassandraConfig.KEYSPACE,
				CassandraConfig.APPLICATION_TABLE,
				CassandraConfig.APPLICATION_NAME,
				CassandraConfig.APPLICATION_ID,
				CassandraConfig.APPLICATION_NAME);
		session.execute(createApplicationTbl);

		String createRequestTbl = String.format("CREATE TABLE IF NOT EXISTS %s.%s(%s int, %s text, %s int, %s text, %s text, %s text, %s text, PRIMARY KEY(%s));",
				CassandraConfig.KEYSPACE,
				CassandraConfig.REQUEST_TABLE,
				CassandraConfig.REQUEST_CODE,
				CassandraConfig.REQUEST_NAME,
				CassandraConfig.REQUEST_APP_ID,
				CassandraConfig.REQUEST_SCRIPT,
				CassandraConfig.REQUEST_PARM,
				CassandraConfig.REQUEST_URL,
				CassandraConfig.REQUEST_SERVER,
				CassandraConfig.REQUEST_CODE);
		session.execute(createRequestTbl);

		String createTicketTbl = String.format("CREATE TABLE IF NOT EXISTS %s.%s(%s int, %s text, %s text, %s text, %s text, %s text, PRIMARY KEY(%s));",
				CassandraConfig.KEYSPACE,
				CassandraConfig.TICKET_TABLE,
				CassandraConfig.TICKET_ID,
				CassandraConfig.TICKET_HEADER,
				CassandraConfig.TICKET_APP_NAME,
				CassandraConfig.TICKET_STATUS,
				CassandraConfig.TICKET_USER,
				CassandraConfig.TICKET_TIME_STAMP,
				CassandraConfig.TICKET_ID);
		session.execute(createTicketTbl);

		String createIncrementTbl = String.format("CREATE TABLE IF NOT EXISTS %s.%s(%s int);",
				CassandraConfig.KEYSPACE,
				CassandraConfig.INCREMENT_TABLE,
				CassandraConfig.OLD_ID);
		session.execute(createIncrementTbl);

		//        session.execute(String.format("INSERT INTO %s.%s (%s, %s) VALUES (\"1\", \"Gowthaman\")",
		//        		CassandraConfig.KEYSPACE,
		//        		CassandraConfig.DATA_TABLE,
		//        		CassandraConfig.DATA_TABLE_ID,
		//        		CassandraConfig.DATA_TABLE_DATA));
	}
}
