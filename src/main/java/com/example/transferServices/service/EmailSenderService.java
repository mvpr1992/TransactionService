package com.example.transferServices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String toemail, String body, String Subject) {
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("mvpr1992@gmail.com");
		message.setTo(toemail);
		message.setText(body);
		message.setSubject(Subject);
		
		mailSender.send(message);
		System.out.print("Mail Sent Successfully!");
	}

}
