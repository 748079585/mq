package com.activemq.demo.test;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.activemq.demo.value.Search;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Component
public class ProduceTest {

	@Autowired
	private Produce produce;
	
	@Value("${activemq.test.b}")
	private int b;

//	@PostConstruct
	public void sendToQueen() throws Exception {
		for(int i = 0 ; i<b;i++) {
			ObjectMapper mapper = new ObjectMapper();
			Search search = new Search();
			search.setAge(33);
			search.setFileName("HW-CM-00001-A_34.jpg");
			search.setPid(22l);
			String s = mapper.writeValueAsString(search);
			produce.sent(s);
		}
	}
}
