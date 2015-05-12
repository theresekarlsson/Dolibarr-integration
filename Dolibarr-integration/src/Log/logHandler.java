package Log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class logHandler {
	
	FileHandler fileHandler = null;
	private static final Logger LOGGER = Logger.getLogger(logHandler.class.getName());
	
	/* Tar emot ett Logger-objekt, sätter filhanterare och formatterare på den.
	 * Returnerar den sedan. */
	public void startLogging() 
	{
		String logFileName = "GetLeadsLog.log";
		LOGGER.setUseParentHandlers(false);
		
		try 
		{
			fileHandler = new FileHandler(logFileName);
			
		} catch (SecurityException | IOException e) 
		{
			LOGGER.log(Level.SEVERE, "Loggfil kan inte skapas. ", e);
		}
		SimpleFormatter formatter = new SimpleFormatter(); 
		fileHandler.setFormatter(formatter);
		Logger.getLogger("").addHandler(fileHandler); //Lägger till filhanterare till "root-loggern"
		Logger.getLogger("").addHandler(new MailingHandler()); //lägger till mailhanterare
		LOGGER.log(Level.INFO, "Loggfil skapad");
	}

	// Stänger streamen till filen.
	public void closeLogFile() 
	{
		fileHandler.flush();
		fileHandler.close();
	}
}
