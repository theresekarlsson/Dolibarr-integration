package Log;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailCreator {
	
	private static final Logger LOGGER = Logger.getLogger(MailCreator.class.getName());
	
	private String fromAddress;
	private String toAddress;
	private String mailSubject;
	private String mailContent;
	private String host;
	private String mailUserNameSender;
	private String mailPasswordSender;
	private String fileName;
	
	
	public MailCreator(String fromEmail, String toEmail, String subject, String content, String file, String username, String password) 
	{
		fromAddress = fromEmail;
		toAddress = toEmail;
		mailSubject = subject;
		mailContent = content;
		mailUserNameSender = username;
		mailPasswordSender = password;
		host = "smtp.gmail.com";
		fileName = file;
	}
	
	public void sendMail() 
	{
		Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", "587");        
	
	    Session session = Session.getInstance(props,
	    		new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                   return new PasswordAuthentication(mailUserNameSender, mailPasswordSender);
	                }
	           }); 
	        
	    // Sätter ihop epost-meddelande
	    try 
	    {
	      	Message message = new MimeMessage(session);
	       	message.setFrom(new InternetAddress(fromAddress));
	       	message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
	        message.setSubject(mailSubject);
	       	BodyPart msgBodyPart = new MimeBodyPart();
	       	msgBodyPart.setText(mailContent);
	       	Multipart multipart = new MimeMultipart();
	       	multipart.addBodyPart(msgBodyPart);
	        msgBodyPart = new MimeBodyPart();
	        DataSource source = new FileDataSource(fileName);	
	        msgBodyPart.setDataHandler(new DataHandler(source));
	        msgBodyPart.setFileName(fileName);
	        multipart.addBodyPart(msgBodyPart);
	        message.setContent(multipart);
	        	
	        // Skickar epost
			Transport.send(message);
		} 
	    catch (AddressException e) 
	    {
	    	LOGGER.log(Level.INFO, "Något gick fel med epost-adresserna", e);
	    }
	    catch (MessagingException e) 
	    {
	        LOGGER.log(Level.INFO, "Något gick fel med meddelandet", e);
		}
			
		LOGGER.log(Level.INFO, "Epost har sänts till: " + toAddress);
	}
}

