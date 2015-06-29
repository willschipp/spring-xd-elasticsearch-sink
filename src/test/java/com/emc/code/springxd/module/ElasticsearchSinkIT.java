package com.emc.code.springxd.module;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={ElasticsearchConfiguration.class,ElasticsearchMockConfiguration.class})
public class ElasticsearchSinkIT {

	@Autowired
	MessageChannel input;
	
	@Test(expected=MessageHandlingException.class)
	public void testWrongFormat() throws Exception {
		//send a message to the channel and see it 'fail'
		Message<?> message = MessageBuilder.withPayload("hello world").build();
		input.send(message);
		//wait
		Thread.sleep(5 * 1000);
	}
	
	@Test
	public void testMap() throws Exception {
		Map<String,String> testMap = new HashMap<String,String>();
		testMap.put("hello","world");
		
		//send a message to the channel and see it 'fail'
		Message<?> message = MessageBuilder.withPayload(testMap).build();
		input.send(message);
		//wait
		Thread.sleep(5 * 1000);
	}	

	@Test
	public void testJSON() throws Exception {
		Map<String,String> testMap = new HashMap<String,String>();
		testMap.put("hello","world");
		
		String json = new ObjectMapper().writeValueAsString(testMap);
		
		//send a message to the channel and see it 'fail'
		Message<?> message = MessageBuilder.withPayload(json).build();
		input.send(message);
		//wait
		Thread.sleep(5 * 1000);
	}	
	
	
}
