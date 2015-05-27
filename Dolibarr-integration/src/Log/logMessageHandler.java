package Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import Properties.propertiesHandler;

public class logMessageHandler {

	private String getLogMessages;	
	private String getLogMessagesFinished;
	
	private String logPropertiesFileNotFound;
	private String logPropertiesIOException;
	
	private static final Logger LOGGER = Logger.getLogger(propertiesHandler.class.getName());
	
	/* Hämtar logg meddelanden från configLogMessages.properties och sparar som strängar. */
	public void getAllLogMessagesFromFile() {
		
		Properties _p = new Properties();
		InputStream input = null;
		
		try 
		{
			String propsFileName = "configLogMessages.properties";
			input = getClass().getClassLoader().getResourceAsStream(propsFileName);
			
			
			
			_p.load(input);
			
			getLogMessages = _p.getProperty("info.getLogMessages");
			getLogMessagesFinished = _p.getProperty("info.getLogMessagesFinished");
			
			logPropertiesFileNotFound = _p.getProperty("warning.logPropertiesFileNotFound");
			logPropertiesIOException = _p.getProperty("warning.logPropertiesIOException");
			LOGGER.log(Level.INFO, getLogMessages);
			
		} 
		
		catch (FileNotFoundException e) 
		{
			LOGGER.log(Level.WARNING, logPropertiesFileNotFound,e);
		}
		
		catch (IOException e) 
		{
			LOGGER.log(Level.WARNING, logPropertiesIOException,e);
		} 
		
		finally 
		{
			if (input != null) 
			{
				
				try 
				{
					input.close();
					LOGGER.log(Level.INFO, getLogMessagesFinished);
				} 
				
				catch (IOException e)
				{
					LOGGER.log(Level.WARNING, "Något gick fel.", e);
				}
			}
		}
	}
	
}
