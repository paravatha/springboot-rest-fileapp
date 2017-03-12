package com.springboot.file.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.springboot.file.service.EmailService;
import com.springboot.file.util.FileConstants;

/**
 * Email service implementation class to send email
 * @author Prasad Paravatha
 */
@Service
public class EmailServiceImpl implements EmailService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender javaMailService;
    
	@Value("${email.username}")
    private String userName;
	
	@Value("${email.to}")
    private String toEmail;
    
	@Override
	public void sendEmail(String message) throws Exception {
		LOGGER.debug("Sending email to " + toEmail);
		SimpleMailMessage mailMessage=new SimpleMailMessage();
		mailMessage.setFrom(userName);
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(FileConstants.EMAIL_SUBJECT);
		mailMessage.setText(message);
		javaMailService.send(mailMessage);
	}
}
