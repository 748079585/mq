package com.mast.client.service;

import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mast.client.model.MailProperties;

import freemarker.template.Configuration;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public boolean sendSimpleMail(String subject, String text, MailProperties mailProperties) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(mailProperties.getFrom());
			mailMessage.setTo(mailProperties.getTo());

			mailMessage.setSubject(subject);
			mailMessage.setText(text);

			javaMailSender.send(mailMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendHtmlMail(String subject, String text, Map<String, String> attachmentMap,
			MailProperties mailProperties) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			// 是否发送的邮件是富文本（附件，图片，html等）
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

			messageHelper.setFrom(mailProperties.getFrom());
			messageHelper.setTo(mailProperties.getTo());

			messageHelper.setSubject(subject);
			messageHelper.setText(text, true);// 重点，默认为false，显示原始html代码，无效果

			if (attachmentMap != null) {
				attachmentMap.entrySet().stream().forEach(entrySet -> {
					try {
						File file = new File(entrySet.getValue());
						if (file.exists()) {
							messageHelper.addAttachment(entrySet.getKey(), new FileSystemResource(file));
						}
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				});
			}

			javaMailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean sendTemplateMail(String subject, Map<String, Object> params, MailProperties mailProperties) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setFrom(mailProperties.getFrom());
			helper.setTo(mailProperties.getTo());

			Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
			configuration.setClassForTemplateLoading(this.getClass(), "/templates");

			String html = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"),
					params);

			helper.setSubject(subject);
			helper.setText(html, true);

			javaMailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
