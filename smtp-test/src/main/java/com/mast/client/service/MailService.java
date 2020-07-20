package com.mast.client.service;

import java.util.Map;

import com.mast.client.model.MailProperties;

public interface MailService {

	boolean sendSimpleMail(String subject, String text, MailProperties mailProperties);

	boolean sendHtmlMail(String subject, String text, Map<String, String> attachmentMap, MailProperties mailProperties);

	boolean sendTemplateMail(String subject, Map<String, Object> params, MailProperties mailProperties);
}
