package com.mast.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mast.client.model.MailProperties;
import com.mast.client.service.MailService;

import freemarker.template.TemplateException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleMailTest {

	@Autowired
	private MailService mailService;

	@Test
	public void sendMail() {
		MailProperties mailPropertie = MailProperties.builder().from("748079585@qq.com").to("748079585@qq.com").build();
		mailService.sendSimpleMail("测试Springboot发送邮件", "发送邮件...", mailPropertie);
	}

	@Test
	public void testMail() throws MessagingException {

		MailProperties mailPropertie = MailProperties.builder().from("748079585@qq.com").to("748079585@qq.com").build();

		Map<String, String> attachmentMap = new HashMap<>();
		attachmentMap.put("附件", "file.txt的绝对路径");

		mailService.sendHtmlMail("测试Springboot发送带附件的邮件", "欢迎进入<a href=\"http://www.baidu.com\">百度首页</a>", attachmentMap,
				mailPropertie);
	}
	
	@Test
	public void testFreemarkerMail() throws MessagingException, IOException, TemplateException {
		MailProperties mailPropertie = MailProperties.builder().from("748079585@qq.com").to("748079585@qq.com").build();

		Map<String, Object> params = new HashMap<>();
		params.put("username", "Cay");
		params.put("aaa", "aaa111");
		params.put("bbb", "bbb111");
		
 
		mailService.sendTemplateMail("测试Springboot发送模版邮件", params,mailPropertie);
 
	}
}
