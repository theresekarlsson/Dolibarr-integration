package Log;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class logHandler {
	
	FileHandler fileHandler = null;
	private static final Logger LOGGER = Logger.getLogger(logHandler.class.getName());
	
	/* Sätter filhanterare och formatterare på root-loggern. */
	public void startLogging() 
	{
		String logFileName = "GetLeadsLog.log";
		
		try 
		{
			fileHandler = new FileHandler(logFileName);
			
		} catch (SecurityException | IOException e) 
		{
			LOGGER.log(Level.SEVERE, "Loggfil kan inte skapas. ", e);
		}
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		/*
	    Handler conHdlr = new ConsoleHandler();
	    conHdlr.setFormatter(new Formatter() 
	    {
	    public String format(LogRecord record) {
	        return record.getLevel() + "  :  "
	          + record.getSourceClassName() + ":"
	          + record.getSourceMethodName() + ":"
	          + record.getMessage() + "\n";
	      }
	    })*/;
	    Logger.getLogger("").addHandler(new MailingHandler()); //lägger till mailhanterare
	    //Logger.getLogger("").addHandler(conHdlr);
		Logger.getLogger("").addHandler(fileHandler); //Lägger till filhanterare till "root-loggern"
		
		
	}

	// Stänger streamen till filen.
	public void closeLogFile() 
	{
		fileHandler.flush();
		fileHandler.close();
	}
}
