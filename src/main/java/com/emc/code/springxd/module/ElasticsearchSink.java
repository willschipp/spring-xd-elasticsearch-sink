package com.emc.code.springxd.module;


import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MessageConversionException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * simple elasticsearch spring-xd sink
 * 
 * index and type can be passed as message headers ("index","type")
 * or set as parameters (--index='', --type='') or a combination
 * 
 * payload to the message is expected to be eithe
 * 
 * @author willschipp
 *
 */
@MessageEndpoint
public class ElasticsearchSink {
	
	private static final Log logger = LogFactory.getLog(ElasticsearchSink.class);

	public static final String INDEX = "index";
	public static final String TYPE = "type";
	
	@Autowired
	private MessageChannel input;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value("${index}")
	private String index;
	
	@Value("${type}")
	private String type;
	
	@Autowired
	private Client client;
	
	@SuppressWarnings("unchecked")
	@ServiceActivator(inputChannel="input")
	public void sink(Message<?> message) throws Exception {
		//init
		Map<String,Object> map = null;
		//check the headers for index/type values
		if (message.getHeaders().containsKey(INDEX)) {
			index = message.getHeaders().get(INDEX).toString();
		}//end if
		if (message.getHeaders().containsKey(TYPE)) {
			type = message.getHeaders().get(TYPE).toString();
		}//end if		
		//extract the payload from the message --> should be a map or a JSON string
		if (message.getPayload() instanceof String && message.getPayload().toString().contains("{")) {
			//convert
			map = mapper.readValue(message.getPayload().toString(), new TypeReference<Map<String,Object>>() { });
		} else if (Map.class.isAssignableFrom(message.getPayload().getClass())) {
			map = (Map<String,Object>) message.getPayload();
		} else {
			throw new MessageConversionException("incorrect format of message");
		}//end if
		//now persist in the client
		IndexResponse response = client.prepareIndex(index, type).setSource(map).execute().actionGet();
		logger.info("message persisted: id=" + response.getId());
	}

	
	
}
