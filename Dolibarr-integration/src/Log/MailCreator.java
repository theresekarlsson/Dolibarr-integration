package Log;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/* INTE KLAR. Behöver ha tillgång till SMTP server (och förmodligen lösenord och användarnamn) för att kunna skicka mail
 * 
 * Sida jag tittat på: http://www.tutorialspoint.com/java/java_sending_email.htm
 * 
 * */

public class MailCreator {
	
	private static final Logger LOGGER = Logger.getLogger(MailCreator.class.getName());
	
	private String fromAddress;
	private String[] toAddress;
	private String serverAddress;
	private String subject;
	private String message;
	private String host;
	private Properties properties;
	
	public MailCreator(String from, String[] to, String server, 
			String subject, String message) 
	{
		fromAddress = from;
		toAddress = to;
		serverAddress = server;
		this.subject = subject;
		this.message = message;
		host = "localhost";
		properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
	}
	
	public void sendMail() 
	{
		try
		{
			//Properties props = new Properties();
			//props.put("mail.smtp.host", serverAddress);
			//Session session = Session.getDefaultInstance(props, null);
			Session session = Session.getDefaultInstance(properties);
			//session.setDebug(true);
			
			Message mimeMsg = new MimeMessage(session);
			
			Address addressFrom = new InternetAddress(fromAddress);
			mimeMsg.setFrom(addressFrom);
			Address[] to = new InternetAddress[toAddress.length];
			
			for (int i = 0; i < toAddress.length; i++)
			{
				to[i] = new InternetAddress(toAddress[i]);
			}
			mimeMsg.setRecipients(Message.RecipientType.TO, to);
			mimeMsg.setSubject(subject);
			
			BodyPart msgBodyPart = new MimeBodyPart();
			//mimeMsg.setText(message);
			msgBodyPart.setText(message);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(msgBodyPart);
			
			msgBodyPart = new MimeBodyPart();
			String filename = "GetLeadsLog.log";
			DataSource source = new FileDataSource(filename);
			msgBodyPart.setDataHandler(new DataHandler(source));
			msgBodyPart.setFileName(filename);
			multipart.addBodyPart(msgBodyPart);
			
			mimeMsg.setContent(multipart);
			
			Transport.send(mimeMsg);
			
			LOGGER.log(Level.INFO, "Epost har sänts.");
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Det gick inte att skicka epost.");
		}
	}
}
