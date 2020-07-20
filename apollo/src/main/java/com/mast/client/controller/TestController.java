package com.mast.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mast.client.conf.MqttReceiveConfig;
import com.mast.client.server.MqttGateway;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private MqttGateway mqttGateway;
	
	@Autowired
	private MqttReceiveConfig mqttReceiveConfig;

	@RequestMapping("/sendMqtt.do")
	public String sendMqtt(String topic,String sendData) {
		mqttGateway.sendToMqtt(topic, sendData);
		return "OK";
	}

	@RequestMapping("/add.do")
	public String add(String topic) {
		MqttPahoMessageDrivenChannelAdapter adapter = mqttReceiveConfig.getAdapter();
		adapter.addTopic(topic);
		return "OK";
	}
}
