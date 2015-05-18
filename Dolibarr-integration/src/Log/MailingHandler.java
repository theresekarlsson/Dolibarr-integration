package Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import Properties.propertiesHandler;

/* Den här klassen lyssnar på loggen och avgör när ett mail ska skickas.  */

public class MailingHandler extends Handler {

	private static final Logger LOGGER = Logger.getLogger(MailingHandler.class.getName());
	private String mailTo;
	private String mailFrom;
	private String content;
	private String subject;
	private String fileName;
	private String userName;
	private String passWord;


	public MailingHandler(propertiesHandler hp) {
		mailTo = hp.getEmail();
		mailFrom = hp.getEmailSender();
		content = hp.getMailContent();
		subject = hp.getMailSubject();
		fileName = hp.getLogFileName();
		userName = hp.getEmailSenderUserName();
		passWord = hp.getEmailSenderPassWord();
	}

	public void publish(LogRecord record) 
	{
		//Konverterar level till String
		String checkLevel = record.getLevel().toString();

		// Kontrollen ska vara SEVERE, men kör på INFO om man vill komma vidare in i mailfunktionen.
		if (checkLevel.equals("INFO"))
		{

			System.out.println("Mailfunktion triggad.");
			new MailCreator(mailFrom,  mailTo, subject, content, fileName, userName, passWord).sendMail();
		}
	}

	public void close() {}

	public void flush() {}

}
