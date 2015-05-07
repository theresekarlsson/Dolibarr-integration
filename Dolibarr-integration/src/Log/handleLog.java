package Log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class handleLog {
	
	public Logger createLogFile(Logger LOGGER) {
		
		FileHandler fileHandler = null;
		String logFileName = "Logg.log";
		try 
		{
			fileHandler = new FileHandler(logFileName);
		} catch (SecurityException | IOException e) {
			LOGGER.log(Level.SEVERE, "Loggfil kan inte skapas. ", e);
			e.printStackTrace();
		}
		LOGGER.addHandler(fileHandler);
		SimpleFormatter formatter = new SimpleFormatter();
		fileHandler.setFormatter(formatter);
		LOGGER.info("inne i handleLog");
		return LOGGER;
	}
}
