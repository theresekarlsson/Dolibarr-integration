package Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Properties.propertiesHandler;

/* Denna klass hanterar loggning i programmet. Skapar logg och loggfil. */
public class logHandler {
	
	private FileHandler fileHandler = null;

	/* Skapar filhanterare, och sätter mailhanterare och formatterare på root-loggern. 
	 * Returnerar sedan loggern. */
	public Logger startLogging(Logger logger) 
	{
		//Bestämmer format på tidsstämpeln i filnamnet.
		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH.mm");	
		
		try 
		{
			// Skapar filhanterare med filväg, tidsstämpel och filnamn till loggfilen.
			File logFile = new File(propertiesHandler.logFilePath + 
					timeStamp.format(Calendar.getInstance().getTime()) + 
					" " + propertiesHandler.logFileName);
			fileHandler = new FileHandler(logFile.getAbsolutePath());
		} 
		
		catch (SecurityException | IOException e) 
		{
			logger.log(Level.SEVERE, logMessageHandler.logFileCreationFailedException, e);
		}
		
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);						//lägger till formatterare så filen blir i txt-format
	    Logger.getLogger("").addHandler(new mailingHandler()); 		//lägger till mailhanterare
		Logger.getLogger("").addHandler(fileHandler); 				//Lägger till filhanterare
		fileHandler.setLevel(Level.ALL);
		
		logger.log(Level.INFO, logMessageHandler.loggingStartSuccess);
		return logger;
	}

	// Stänger streamen till filen.
	public void closeLogFile() 
	{
		fileHandler.flush();
		fileHandler.close();
	}
}
