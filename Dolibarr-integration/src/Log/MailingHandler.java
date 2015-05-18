package Log;

import java.util.logging.Handler;
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

	private propertiesHandler HP = new propertiesHandler();
	private String mailTo;

	public MailingHandler(String eMail) {
		mailTo = eMail;
	}

	public void publish(LogRecord record) 
	{
		//Konverterar level till String
		String checkLevel = record.getLevel().toString();
		
		System.out.println(mailTo);

		// Kontrollen ska vara SEVERE, men k�r p� INFO om man vill komma vidare in i mailfunktionen.
		if (checkLevel.equals("INFO"))
		{
			System.out.println("mailfunktion triggad. Skickas till " + mailTo);
			
			new MailCreator("therese-karlsson@outlook.com", new String[] 
					{ mailTo }, "smtp.theunixman.com", 
					"Test Subject", "Test Content").sendMail();
		}
	}

	public void close() {}

	public void flush() {}

}
