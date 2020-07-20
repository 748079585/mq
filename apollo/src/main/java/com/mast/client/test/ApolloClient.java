package com.mast.client.test;

import java.util.Date;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApolloClient {
	
	@Value("${spring.mqtt.username}")
	private String userName;

	@Value("${spring.mqtt.password}")
	private String passWord;

	@Value("${spring.mqtt.url}")
	private String host;

	@Value("${spring.mqtt.client.id}")
	private String clientId;

	@Value("${spring.mqtt.default.topic}")
	private String defaultTopic;
	
	//本次测试订阅的主题：test
	private String[] topicStr = {"test"};

	private MqttClient client;
    
    public MqttClient getClient() {
    	return client;
    }

    public void init() throws MqttException {
        //host为主机名，test为clientid即连接MQTT的客户端ID，一般以客户端唯一标识符表示，
        //MemoryPersistence设置clientid的保存形式，默认为以内存保存
        if(client==null)
        client = new MqttClient(host, "CallbackClient",new MqttDefaultFilePersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        //设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，
        //这里设置为true表示每次连接到服务器都以新的身份连接
        options.setCleanSession(false);
        //设置连接的用户名
        options.setUserName(userName);
        //设置连接的密码
        options.setPassword(passWord.toCharArray());
        // 设置超时时间 单位为秒
        options.setConnectionTimeout(10);
        // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
        options.setKeepAliveInterval(20);
        //回调
        client.setCallback(new MqttCallback() {
            public void messageArrived(String topicName, MqttMessage message) throws Exception {
                //subscribe后得到的消息会执行到这里面
                System.out.println("messageArrived----------");
                System.out.println(topicName+"---"+message.toString());
            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                //publish后会执行到这里
                System.out.println("deliveryComplete---------"
                        + token.isComplete());
            }
            public void test(){

            }
            public void connectionLost(Throwable cause) {
                // //连接丢失后，一般在这里面进行重连
                System.out.println("connectionLost----------");
                try {
                    init();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        //链接
        client.connect(options);
        //订阅
        client.subscribe(topicStr);
        //取消订阅
        //client.unsubscribe(topicStr);
    }
    
    public void addTopic(String topic) throws MqttException  {
    	client.subscribe(topic);
    }
    
    //自己写发布消息的方法，然后循环调用。
    public void PushMsg(String msg){
        MqttMessage m=new MqttMessage();
        m.setRetained(true);
        m.setPayload(msg.getBytes());
        try {
            client.publish("test", m);
        }catch(Exception e){
            System.out.println("发布消息失败-->"+msg);
            e.printStackTrace();
        }

    }
    
    @Test
    public void test() throws MqttException{
        init();
        while (true){
            try {
                Thread.sleep(5000);
                PushMsg("客户端发送："+new Date().getTime());
            }catch (Exception e){

            }
        }
    }
}