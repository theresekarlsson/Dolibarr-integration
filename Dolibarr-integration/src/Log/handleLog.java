package Log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class handleLog {
	
	private FileHandler fileHandler = null;
	
	// Tar emot Logger, s�tter filhanterare och formatterare p� den och returnerar den sedan.
	public Logger createLogFile(Logger logger) 
	{

		String logFileName = "GetLeadsLog.log";
		try 
		{
			fileHandler = new FileHandler(logFileName);
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Loggfil kan inte skapas. ", e);
			e.printStackTrace();
		}
		Logger.getLogger("").addHandler(fileHandler); //G�r s� alla loggar i systemet skriver till "root-loggen"
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		logger.log(Level.INFO, "Loggfil skapad");
		return logger;
	}

	// St�nger streamen till filen.
	public void closeLogFile() 
	{
		fileHandler.flush();
		fileHandler.close();
	}
}
