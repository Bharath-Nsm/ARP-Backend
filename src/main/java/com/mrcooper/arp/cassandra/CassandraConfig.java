package com.mrcooper.arp.cassandra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.google.common.annotations.VisibleForTesting;
import com.mrcooper.arp.controller.RequestHandler;

@Configuration
@PropertySource("application.properties")
@ComponentScan("com.mrcooper.arp")
public class CassandraConfig {
	private String cassandraHost = "0.0.0.0";
	public static final String KEYSPACE = "arpDB";

	public static final String APPLICATION_TABLE = "application";
	public static final String APPLICATION_ID = "app_id";
	public static final String APPLICATION_NAME = "app_name";

	public static final String REQUEST_TABLE = "request";
	public static final String REQUEST_CODE = "req_id";
	public static final String REQUEST_NAME = "req_name";
	public static final String REQUEST_APP_ID = "app_id";
	public static final String REQUEST_SCRIPT = "req_script";
	public static final String REQUEST_PARM = "parm_list";
	public static final String REQUEST_URL = "app_url";
	public static final String REQUEST_SERVER = "app_server_name";

	public static final String TICKET_TABLE = "ticket";
	public static final String TICKET_ID = "tic_num";
	public static final String TICKET_HEADER = "tic_header";
	public static final String TICKET_APP_NAME = "app_name";
	public static final String TICKET_STATUS = "tic_status";
	public static final String TICKET_USER = "tic_user";
	public static final String TICKET_TIME_STAMP = "tic_last_updated";

	public static final String INCREMENT_TABLE = "increment";
	public static final String OLD_ID = "old_id";

	@VisibleForTesting
	Cluster.Builder clusterBuilder() {
		return Cluster.builder();
	}

	@Bean
	public Cluster cluster() {
		return clusterBuilder()
				.addContactPoint(cassandraHost)
				.withReconnectionPolicy(new ConstantReconnectionPolicy(100L))
				.build();
	}

	@Bean
	public Session session() throws Exception {
		return cluster().newSession();
	}

	@Bean
	public RequestHandler requestHandler() {
		return new RequestHandler();
	}

	//	@Bean
	//	public DataHandler dataHandler() {
	//		return new DataHandler();
	//	}

	@Value("${cassandra.host}")
	public void setCassandraHost(String cassandraHost) {
		this.cassandraHost = cassandraHost;
	}
}
