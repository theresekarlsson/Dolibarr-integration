package Log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* TODO Den här klassen lyssnar på loggen och avgör när ett mail ska skickas. 
 * Inte klar!
 * 
 * Länkar jag tittat på: http://www.linuxtopia.org/online_books/programming_books/thinking_in_java/TIJ317_019.htm
 * 
 * */

public class MailingHandler extends Handler {

	public void publish(LogRecord record) 
	{
		//Konverterar level till String
		String checkLevel = record.getLevel().toString();

		// Kontrollen ska egentligen vara SEVERE, men kör på INFO nu bara för att komma in i mailfunktionen.
		if (checkLevel.equals("INFO"))
		{
			// Vet inte exakt vilken data vi ska skicka med här.
			System.out.println("mailfunktion triggad");
			new MailCreator("therese-karlsson@outlook.com", new String[] 
					{"therese-karlsson@outlook.com" }, "smtp.theunixman.com", 
					"Test Subject", "Test Content").sendMail();
		}
	}

	public void close() {}

	public void flush() {}

}
