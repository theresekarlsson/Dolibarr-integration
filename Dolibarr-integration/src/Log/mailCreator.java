package Log;

import java.io.File;
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


public class mailCreator {
	
	private static final Logger LOGGER = Logger.getLogger(mailCreator.class.getName());
	
	private String fromAddress;
	private String toAddress;
	private String mailSubject;
	private String mailContent;
	private String host;
	private String mailUserNameSender;
	private String mailPasswordSender;
	private String filePath;
	
	
	public mailCreator(String fromEmail, String toEmail, String subject, String content, String filepath,String username, String password) 
	{
		fromAddress = fromEmail;
		toAddress = toEmail;
		mailSubject = subject;
		mailContent = content;
		mailUserNameSender = username;
		mailPasswordSender = password;
		host = "smtp.gmail.com";
		filePath = filepath;
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
	        
	    
	    try 
	    {
	    	// Sätter ihop mail
	      	Message message = new MimeMessage(session);
	       	message.setFrom(new InternetAddress(fromAddress));					//Sätter avsändaradress
	       	message.setRecipients(Message.RecipientType.TO, 
	       			InternetAddress.parse(toAddress));							//Sätter mottagaradress
	        message.setSubject(mailSubject);									//Sätter ämne i ämnesrad
	        
	       	BodyPart msgBodyPart = new MimeBodyPart();							//Skapar innehåll i mailet
	       	msgBodyPart.setText(mailContent);									//Textmeddelande i mailet
	       	Multipart multipart = new MimeMultipart();
	       	multipart.addBodyPart(msgBodyPart);
	        msgBodyPart = new MimeBodyPart();
	         
	        String getLogFile = getLatestLogFile(filePath).getAbsolutePath();	//Hämtar namn på den senaste loggfilen
	        System.out.println(getLogFile);
	        
	        DataSource source = new FileDataSource(getLogFile);					//Bifogar loggfil
	        msgBodyPart.setDataHandler(new DataHandler(source));
	        msgBodyPart.setFileName(getLogFile);
	        
	        multipart.addBodyPart(msgBodyPart);									//Sätter ihop hela mailet
	        message.setContent(multipart);
	        	
			Transport.send(message);											// Skickar epost
		} 
	    catch (AddressException e) 
	    {
	    	LOGGER.log(Level.WARNING, logMessageHandler.mailCreatorAddressException, e);
	    }
	    catch (MessagingException e) 
	    {
	        LOGGER.log(Level.WARNING, logMessageHandler.mailCreatorMessagingException, e);
		}
			
		LOGGER.log(Level.INFO, logMessageHandler.mailSent + toAddress);
	}
	

	/* Hämtar senast skapad fil på angedd plats. */
	private File getLatestLogFile(String filepath)
	{
		File dir = new File(filepath);							//Filväg till plats där loggfilerna ligger
		File[] allLogFiles = dir.listFiles();					//Skapar array med alla filer på platsen.
		    
		if (allLogFiles == null || allLogFiles.length == 0) 
		{
			return null;
		}
		
		File lastlogFile = allLogFiles[0];
		
		for (int i = 1; i < allLogFiles.length; i++) 
		{
			if (lastlogFile.lastModified() < allLogFiles[i].lastModified())
		    {
				lastlogFile = allLogFiles[i];					//Hämtar den senast ändrade filen
		    }
		}
		return lastlogFile;										//Returnerar filen
	}
}

