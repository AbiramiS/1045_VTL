package com.communication;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.smtp.SMTPSSLTransport;

public class SendMail {



public SendMail(String from, String to, String subject, String text,String mailusername,String mailpassword){
	
	String host = "smtp.gmail.com";

try {
	Properties props = System.getProperties();
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", host);
	props.setProperty("mail.transport.protocol", "smtps");
	props.put("mail.smtp.user", mailusername);
	props.put("mail.smtp.password", mailpassword);
	props.put("mail.smtp.port", "465");
	props.put("mail.smtps.auth", "true");
	Session session = Session.getDefaultInstance(props, null);
	MimeMessage message = new MimeMessage(session);
	InternetAddress fromAddress = null;
	InternetAddress toAddress = null;

		try {
		fromAddress = new InternetAddress(from);
		toAddress = new InternetAddress(to);
		} catch (AddressException e) {
		
		e.printStackTrace();
		}
		
		message.setFrom(fromAddress);
		message.setRecipient(RecipientType.TO, toAddress);
		message.setSubject(subject);
		message.setText(text);
		
		SMTPSSLTransport transport =(SMTPSSLTransport)session.getTransport("smtps");
		
		//Transport transport = session.getTransport("smtps");
		transport.connect(host, mailusername, mailpassword);
		transport.sendMessage(message, message.getAllRecipients());
		System.out.println("----------");
		transport.close();
	}   catch (MessagingException e) {
			e.printStackTrace();
		}
}
}