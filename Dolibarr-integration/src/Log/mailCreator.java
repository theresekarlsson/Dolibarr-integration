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

/* Denna klass skapar och skickar e-postmeddelande */ 
public class mailCreator {
	
	private static final Logger LOGGER = Logger.getLogger(mailCreator.class.getName());
	
	private String fromAddress;				// avsändarense-post 
	private String toAddress;				// mottagarens e-post
	private String mailSubject;				// ämnesrad i e-postmeddelande
	private String mailContent;				// Textinnehåll i e-postmeddelande
	private String mailHost;				// Mailhost
	private String mailUserNameSender;		// Avsändarens anv.namn
	private String mailPasswordSender;		// Avsändarens lösenord
	private String filePath;				// Filväg till loggfil
	
	
	public mailCreator(String fromEmail, String toEmail, String subject, String content, 
			String filepath,String username, String password, String mailhost) 
	{
		fromAddress = fromEmail;
		toAddress = toEmail;
		mailSubject = subject;
		mailContent = content;
		mailUserNameSender = username;
		mailPasswordSender = password;
		mailHost = mailhost;
		filePath = filepath;
	}
	
	public void sendMail() 
	{
		Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", mailHost);
	    props.put("mail.smtp.port", "587");        
	
	    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	                protected PasswordAuthentication getPasswordAuthentication() {
	                   return new PasswordAuthentication(mailUserNameSender, mailPasswordSender);
	                }
	           }); 
	        
	    try 
	    {
	    	LOGGER.log(Level.INFO, logMessageHandler.creatingEmail);
	    	
	    	// Sätter ihop mail
	      	Message message = new MimeMessage(session);
	       	message.setFrom(new InternetAddress(fromAddress));					//Sätter avsändaradress
	       	message.setRecipients(Message.RecipientType.TO, 
	       			InternetAddress.parse(toAddress));							//Sätter mottagaradress
	        message.setSubject(mailSubject);									//Sätter ämne i ämnesrad
	        
	       	BodyPart msgBodyPart = new MimeBodyPart();							//Skapar "kroppen" i mailet
	       	msgBodyPart.setText(mailContent);									//Textmeddelande i mailet
	       	Multipart multipart = new MimeMultipart();
	       	multipart.addBodyPart(msgBodyPart);
	        msgBodyPart = new MimeBodyPart();
	         
	        String getLogFile = getLatestLogFile(filePath).getAbsolutePath();	//Hämtar namn på den senaste loggfilen
	        
	        LOGGER.log(Level.INFO, logMessageHandler.filePathToLastLog + " " + getLogFile);
			
	        DataSource source = new FileDataSource(getLogFile);					//Bifogar loggfil
	        msgBodyPart.setDataHandler(new DataHandler(source));
	        msgBodyPart.setFileName(getLogFile);
	        
	        multipart.addBodyPart(msgBodyPart);									//Sätter ihop delarna i mailet
	        message.setContent(multipart);
	        	
			Transport.send(message);											// Skickar epost
			
			LOGGER.log(Level.INFO, logMessageHandler.mailSent + toAddress);
		} 
	    catch (AddressException e) 
	    {
	    	LOGGER.log(Level.WARNING, logMessageHandler.mailCreatorAddressException, e);
	    }
	    catch (MessagingException e) 
	    {
	        LOGGER.log(Level.WARNING, logMessageHandler.mailCreatorMessagingException, e);
		}
	}
	

	/* Hämtar senast skapad loggfil på angedd plats. */
	private File getLatestLogFile(String filepath)
	{
		LOGGER.log(Level.INFO, logMessageHandler.getLatestLog);
		
		File dir = new File(filepath);							// Filväg till plats(mappen) där loggfilerna ligger
		File[] allLogFiles = dir.listFiles();					// Skapar array med alla filer på platsen.
		    
		if (allLogFiles == null || allLogFiles.length == 0) 	// Kontrollerar om platsen är tom
		{
			return null;										// Isåfall, returnera inget
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

