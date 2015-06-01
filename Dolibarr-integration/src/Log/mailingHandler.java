package Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import Properties.propertiesHandler;

/* Den h�r klassen lyssnar p� all loggning som sker under tiden programmet k�rs, 
 * och avg�r n�r ett mail ska skickas.  */

public class mailingHandler extends Handler {

	private static final Logger LOGGER = Logger.getLogger(mailingHandler.class.getName());
	private String mailTo;				// e-post till mottagare
	private String mailFrom;			// e-post till avs�ndare
	private String mailContent;			// meddelande i e-post
	private String mailSubject;			// �mnesrad i e-post
	private String logFilePath;			// filv�g till loggfilen
	private String mailFromUserName;	// anv�ndarnamn till avs�ndares e-postkonto
	private String mailFromPassWord;	// l�senord till avs�ndares e-postkonto


	/* H�mtar data fr�n config.properties som kr�vs f�r att skicka e-post. */
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
		String checkLevel = record.getLevel().toString();							// Konverterar till en str�ng

		if (checkLevel.equals("SEVERE"))											// Kontrollerar om str�ngen �r SEVERE
		{

			LOGGER.log(Level.INFO, logMessageHandler.triggingMailFunction);
			
			new mailCreator(mailFrom,  mailTo, mailSubject, mailContent, 
					logFilePath, mailFromUserName, mailFromPassWord).sendMail();	//Om s� �r fallet, skapas ett mailobjekt
		
			LOGGER.log(Level.INFO, logMessageHandler.closingProgram);		
			System.exit(0);															//Avslutar program
		}
	}

	public void close() { /* Anv�nds inte */ }

	public void flush() { /* Anv�nds inte */ }
}
