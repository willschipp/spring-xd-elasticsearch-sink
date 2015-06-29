package com.emc.code.springxd.module;

import java.nio.channels.UnresolvedAddressException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableIntegration
public class ElasticsearchConfiguration {

	private static final int DEFAULT_PORT = 9300;
	
	@Value("${hosts}")
	private String hosts[];
	
	@Bean
	MessageChannel input() {
		return new DirectChannel();
	}
	
	@Bean
	ElasticsearchSink sink() {
		return new ElasticsearchSink();
	}
	
	@Bean
	Client client() {
		TransportClient client = null;
		TransportAddress[] addresses = new TransportAddress[hosts.length];
		for (int i=0;i<hosts.length;i++) {
			TransportAddress address = new InetSocketTransportAddress(hosts[i], DEFAULT_PORT);
			addresses[i] = address;
		}//end for
		//build the client
		try {
			client = new TransportClient().addTransportAddresses(addresses);
		}
		catch (UnresolvedAddressException e) {
			throw new RuntimeException(e);
		}
		//return
		return client;
	}
	
	@Bean
	ObjectMapper mapper() {
		return new ObjectMapper();
	}
}
