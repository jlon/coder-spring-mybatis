package com.ailink.framework.utils.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 
 * @author jlon
 *
 */
public class MailSenderService {
	Logger log = Logger.getLogger(MailSenderService.class);

	// --JavaMailSender 扩充父接口 MailSender 支持发送mime电子邮件（html，附件，等）
	private JavaMailSender javaMailSender;
	
	private SimpleMailMessage simpleMailMessage;
	
	
	public  void  sendMail(String subject,String text,String to){
		// 主题,内容
		try {
			simpleMailMessage.setTo(to);
			simpleMailMessage.setSubject(subject);
			simpleMailMessage.setText(text);
			javaMailSender.send(simpleMailMessage);
		} catch (MailException e) {
			e.printStackTrace();
		}
	}
	
	 /** 
     * 发送普通Html邮件 
     * 
     */  
    public void sendHtml(String subject,String content,String to){  
    	javaMailSender = this.getJavaMailSender();  
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);  
        try {  
            messageHelper.setTo(to);  
            messageHelper.setFrom(simpleMailMessage.getFrom());  
            messageHelper.setSubject(subject);  
            messageHelper.setText(content,true);        
        } catch (MessagingException e) {  
            e.printStackTrace();  
        }  
        javaMailSender.send(mimeMessage);  
    }  
    
    /** 
     * 发送普通带一张图片的Html邮件 
     * 
     */  
    public void sendHtmlWithImage(String subject,String content,String to,String imagePath){  
    	javaMailSender = this.getJavaMailSender();  
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();  
        try {  
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);  
            messageHelper.setTo(to);  
            messageHelper.setFrom(simpleMailMessage.getFrom());  
            messageHelper.setSubject(subject);  
            messageHelper.setText(content,true);  
//          Content="<html><head></head><body><img src=\"cid:image\"/></body></html>";  
            //图片必须这样子：<img src='cid:image'/>  
            FileSystemResource img = new FileSystemResource(new File(imagePath));  
            messageHelper.addInline("image",img);  
        } catch (MessagingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        javaMailSender.send(mimeMessage);  
    }  
    /** 
     * 发送普通带附件的Html邮件 
     * 
     */  
    public void sendHtmlWithAttachment(String subject,String content,String to,String filePath){  
    	javaMailSender = this.getJavaMailSender();  
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();  
        try {  
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);  
            messageHelper.setTo(to);  
            messageHelper.setFrom(simpleMailMessage.getFrom());  
            messageHelper.setSubject(subject);  
            messageHelper.setText(content);  
            FileSystemResource file = new FileSystemResource(new File(filePath));  
//          System.out.println("file.getFilename==="+file.getFilename());  
            messageHelper.addAttachment(file.getFilename(),file);  
        } catch (MessagingException e) {  
            e.printStackTrace();  
        }  
        javaMailSender.send(mimeMessage);  
    }  
	/* ===================== setter & getter ======================= */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public SimpleMailMessage getSimpleMailMessage() {
		return simpleMailMessage;
	}

	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}
}
