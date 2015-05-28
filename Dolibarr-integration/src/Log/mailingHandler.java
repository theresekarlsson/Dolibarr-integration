package Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import Properties.propertiesHandler;

/* Den h�r klassen lyssnar p� all loggning, och avg�r n�r ett mail ska skickas.  */

public class mailingHandler extends Handler {

	private static final Logger LOGGER = Logger.getLogger(mailingHandler.class.getName());
	private String mailTo;				// e-post till mottagare
	private String mailFrom;			// e-post till avs�ndare
	private String mailContent;			// meddelande i e-post
	private String mailSubject;			// �mnesrad i e-post
	private String logFilePath;			// filv�g till loggfilen
	private String mailFromUserName;	// anv�ndarnamn till avs�ndares e-postkonto
	private String mailFromPassWord;	// l�senord till avs�ndares e-postkonto


	/* Konstruktor, h�mtar all data som kr�vs f�r att skicka e-post. */
	public mailingHandler() {
		mailTo = propertiesHandler.email;
		mailFrom = propertiesHandler.emailSender;
		mailContent = propertiesHandler.mailContent;
		mailSubject = propertiesHandler.mailSubject;
		mailFromUserName = propertiesHandler.emailSenderUserName;
		mailFromPassWord = propertiesHandler.emailSenderPassWord;
		logFilePath = propertiesHandler.logFilePath;
	}

	
	/* Konverterar allvarlighetsgraden (level) p� loggmeddelandet till en str�ng, 
	 * kontrollerar den och triggar mailfunktion om den �r "SEVERE" */
	public void publish(LogRecord record) 
	{
		  
		String checkLevel = record.getLevel().toString();

		if (checkLevel.equals("SEVERE"))
		{

			LOGGER.log(Level.INFO, "Mailfunktion triggad.");
			
			new mailCreator(mailFrom,  mailTo, mailSubject, mailContent, 
					logFilePath, mailFromUserName, mailFromPassWord).sendMail();
		}
	}

	/* Anv�nds inte */
	public void close() {}

	/* Anv�nds inte */
	public void flush() {}

}
