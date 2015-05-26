package Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import Properties.propertiesHandler;

/* Den h�r klassen lyssnar p� all loggning, och avg�r n�r ett mail ska skickas.  */

public class MailingHandler extends Handler {

	private static final Logger LOGGER = Logger.getLogger(MailingHandler.class.getName());
	private String mailTo;				// e-post till mottagare
	private String mailFrom;			// e-post till avs�ndare
	private String mailContent;			// meddelande i e-post
	private String mailSubject;			// �mnesrad i e-post
	private String logFileName;			// loggfilens namn
	private String mailFromUserName;	// anv�ndarnamn till avs�ndares e-postkonto
	private String mailFromPassWord;	// l�senord till avs�ndares e-postkonto


	/* Konstruktor, h�mtar all data som kr�vs f�r att skicka e-post. */
	public MailingHandler(propertiesHandler hp) {
		mailTo = hp.getEmail();
		mailFrom = hp.getEmailSender();
		mailContent = hp.getMailContent();
		mailSubject = hp.getMailSubject();
		mailFromUserName = hp.getEmailSenderUserName();
		mailFromPassWord = hp.getEmailSenderPassWord();
		logFileName = hp.getLogFileName();
	}

	/* Konverterar niv�n (level) p� loggmeddelandet till en str�ng, 
	 * kontrollerar den och triggar mailfunktion om den �r "SEVERE" */
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

	/* Anv�nds inte */
	public void close() {}

	/* Anv�nds inte */
	public void flush() {}

}
