package Log;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MailingHandler extends Handler {

	private static final Logger LOGGER = Logger.getLogger(MailingHandler.class.getName());

	public void publish(LogRecord record) {
		Object[] params = record.getParameters();
		if (params == null)
		{
			return;
		}
		if (params[0].equals(Level.INFO))
		{
			//kommer aldrig in här
			LOGGER.log(Level.INFO, "mailfunktion triggad");
			new MailInfo("therese-karlsson@outlook.com", new String[] 
					{"therese-karlsson@outlook.com" }, "smtp.theunixman.com", 
					"Test Subject", "Test Content").sendMail();
		}
	}

	public void close() {}

	public void flush() {}

}
