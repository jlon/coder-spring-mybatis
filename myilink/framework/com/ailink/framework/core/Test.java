package com.ailink.framework.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class Test {

	public static void main(String[] args) {
		ApplicationContext actx = new ClassPathXmlApplicationContext(
				"spring/MailMessage.xml");
		
		MailSender ms = (MailSender) actx.getBean("mailSender");
		
		SimpleMailMessage smm = (SimpleMailMessage) actx.getBean("mailMessage");
		// 主题,内容
		smm.setSubject("你好");
		smm.setText("这个是一个通过Spring框架来发送邮件的小程序");
		ms.send(smm);

	}

}
