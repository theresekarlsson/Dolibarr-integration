package Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import Properties.propertiesHandler;

/* Den här klassen lyssnar på all loggning, och avgör när ett mail ska skickas.  */

public class MailingHandler extends Handler {

	private static final Logger LOGGER = Logger.getLogger(MailingHandler.class.getName());
	private String mailTo;				// e-post till mottagare
	private String mailFrom;			// e-post till avsändare
	private String mailContent;			// meddelande i e-post
	private String mailSubject;			// ämnesrad i e-post
	private String logFileName;			// loggfilens namn
	private String mailFromUserName;	// användarnamn till avsändares e-postkonto
	private String mailFromPassWord;	// lösenord till avsändares e-postkonto


	/* Konstruktor, hämtar all data som krävs för att skicka e-post. */
	public MailingHandler(propertiesHandler hp) {
		mailTo = hp.getEmail();
		mailFrom = hp.getEmailSender();
		mailContent = hp.getMailContent();
		mailSubject = hp.getMailSubject();
		mailFromUserName = hp.getEmailSenderUserName();
		mailFromPassWord = hp.getEmailSenderPassWord();
		logFileName = hp.getLogFileName();
	}

	/* Konverterar nivån (level) på loggmeddelandet till en sträng, 
	 * kontrollerar den och triggar mailfunktion om den är "SEVERE" */
	public void publish(LogRecord record) 
	{
		  
		String checkLevel = record.getLevel().toString();

		if (checkLevel.equals("SEVERE"))
		{

			LOGGER.log(Level.INFO, "Mailfunktion triggad.");
			new MailCreator(mailFrom,  mailTo, mailSubject, mailContent, 
					logFileName, mailFromUserName, mailFromPassWord).sendMail();
		}
	}

	/* Används inte */
	public void close() {}

	/* Används inte */
	public void flush() {}

}
