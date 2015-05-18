package Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import Properties.propertiesHandler;

/* TODO Den h�r klassen lyssnar p� loggen och avg�r n�r ett mail ska skickas. 
 * Inte klar!
 * 
 * L�nkar jag tittat p�: http://www.linuxtopia.org/online_books/programming_books/thinking_in_java/TIJ317_019.htm
 * 
 * */

public class MailingHandler extends Handler {

	private static final Logger LOGGER = Logger.getLogger(MailingHandler.class.getName());
	private String mailTo;
	private String mailFrom;
	private String content;
	private String subject;
	private String fileName;


	public MailingHandler(propertiesHandler hp) {
		mailTo = hp.getEmail();
		mailFrom = hp.getEmailSender();
		content = hp.getMailContent();
		subject = hp.getMailSubject();
		fileName = hp.getLogFileName();
	}

	public void publish(LogRecord record) 
	{
		//Konverterar level till String
		String checkLevel = record.getLevel().toString();

		// Kontrollen ska vara SEVERE, men k�r p� INFO om man vill komma vidare in i mailfunktionen.
		if (checkLevel.equals("INFO"))
		{

			System.out.println("Mailfunktion triggad.");
			new MailCreator(mailFrom,  mailTo, subject, content, fileName).sendMail();
		}
	}

	public void close() {}

	public void flush() {}

}
