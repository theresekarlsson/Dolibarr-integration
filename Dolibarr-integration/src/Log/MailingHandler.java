package Log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* TODO Den h�r klassen lyssnar p� loggen och avg�r n�r ett mail ska skickas. 
 * Inte klar!
 * 
 * L�nkar jag tittat p�: http://www.linuxtopia.org/online_books/programming_books/thinking_in_java/TIJ317_019.htm
 * 
 * */

public class MailingHandler extends Handler {

	public void publish(LogRecord record) 
	{
		//Konverterar level till String
		String checkLevel = record.getLevel().toString();

		// Kontrollen ska egentligen vara SEVERE, men k�r p� INFO nu bara f�r att komma in i mailfunktionen.
		if (checkLevel.equals("INFO"))
		{
			// Vet inte exakt vilken data vi ska skicka med h�r.
			System.out.println("mailfunktion triggad");
			new MailCreator("therese-karlsson@outlook.com", new String[] 
					{"therese-karlsson@outlook.com" }, "smtp.theunixman.com", 
					"Test Subject", "Test Content").sendMail();
		}
	}

	public void close() {}

	public void flush() {}

}
