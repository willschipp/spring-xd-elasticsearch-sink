package com.emc.code.springxd.module;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
public class ElasticsearchMockConfiguration {

	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() throws Exception {
	    final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
	    Properties properties = new Properties();
	
	    properties.setProperty("hosts", "localhost,127.0.0.1");
	    properties.setProperty("index", "temp");
	    properties.setProperty("type", "other");
	
	    pspc.setProperties(properties);
	    return pspc;
	}	
	
}
