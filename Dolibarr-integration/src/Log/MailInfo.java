package Log;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailInfo {
	
	private static final Logger LOGGER = Logger.getLogger(MailInfo.class.getName());
	private String fromAddress;
	private String[] toAddress;
	private String serverAddress;
	private String subject;
	private String message;
	
	public MailInfo(String from, String[] to, String server, 
			String subject, String message) 
	{
		fromAddress = from;
		toAddress = to;
		serverAddress = server;
		this.subject = subject;
		this.message = message;
	}
	
	public void sendMail() 
	{
		try
		{
			Properties props = new Properties();
			props.put("mail.smtp.host", serverAddress);
			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);
			
			Message mimeMsg = new MimeMessage(session);
			
			Address addressFrom = new InternetAddress(fromAddress);
			mimeMsg.setFrom(addressFrom);
			Address[] to = new InternetAddress[toAddress.length];
			
			for (int i = 0; i < toAddress.length; i++)
			{
				mimeMsg.setRecipients(Message.RecipientType.TO, to);
				mimeMsg.setSubject(subject);
				mimeMsg.setText(message);
				Transport.send(mimeMsg);
			}
		}
		catch (Exception e)
		{
			LOGGER.log(Level.SEVERE, "Det gick inte att skicka epost.");
		}
	}
}
