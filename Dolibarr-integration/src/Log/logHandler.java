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

import Properties.propertiesHandler;

public class logHandler {
	
	FileHandler fileHandler = null;

	/* Sätter filhanterare, mailhanterare och formatterare på root-loggern. Returnerar sedan loggern. */
	public Logger startLogging(Logger logger, String logFileName, propertiesHandler hp) 
	{
		
		try 
		{
			fileHandler = new FileHandler(logFileName);
			
		} catch (SecurityException | IOException e) 
		{
			logger.log(Level.SEVERE, "Loggfil kan inte skapas. ", e);
		}
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
	    Logger.getLogger("").addHandler(new MailingHandler(hp)); //lägger till mailhanterare
		Logger.getLogger("").addHandler(fileHandler); //Lägger till filhanterare till "root-loggern"
		logger.log(Level.INFO, "Filhanterare och loggfil skapad. Loggning till fil påbörjad.");
		
		return logger;
		
	}

	// Stänger streamen till filen.
	public void closeLogFile() 
	{
		fileHandler.flush();
		fileHandler.close();
	}
}
