package com.activemq.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.activemq.demo.produce.ProductProducer;

@Component
public class Produce {

	@Autowired
	private ProductProducer productProducer;
	
	@Value("${activemq.test.a}")
	private int a;
	
	@Async("defaultExecutor")
	public void sent (String name) throws Exception{
		System.out.println(Thread.currentThread().getName()+":"+name);
		long date = System.currentTimeMillis();
		for(int i = 0 ; i < a ; i++) {
			this.productProducer.sendMessage(name);
		}
		System.out.println("发送耗时:"+ (System.currentTimeMillis()-date)/1000);
	}
	
}
