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

public class logHandler {
	
	private FileHandler fileHandler = null;

	/* Skapar filhanterare, och sätter mailhanterare och formatterare på root-loggern. Returnerar sedan loggern. */
	public Logger startLogging(Logger logger) 
	{
		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd HH.mm");	//Bestämmer format på tidsstämpeln i filnamnet.
		
		try 
		{
			// Skapar filhanterare med filväg, tidsstämpel och filnamn till loggfilen.
			File logFile = new File(propertiesHandler.logFilePath + timeStamp.format(Calendar.getInstance().getTime()) + " " + propertiesHandler.logFileName);
			fileHandler = new FileHandler(logFile.getAbsolutePath());
			
		} 
		catch (SecurityException | IOException e) 
		{
			logger.log(Level.SEVERE, "Loggfil kan inte skapas. ", e);
		}
		
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);						//lägger till formatterare så filen blir i txt-format
	    Logger.getLogger("").addHandler(new mailingHandler()); 		//lägger till mailhanterare
		Logger.getLogger("").addHandler(fileHandler); 				//Lägger till filhanterare
		
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
