package Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import Properties.propertiesHandler;

public class logMessageHandler {

	private String logTest;	
	
	private static final Logger LOGGER = Logger.getLogger(propertiesHandler.class.getName());
	
public void getAllLogMessagesFromFile() {
		
		Properties _p = new Properties();
		InputStream input = null;
		
		try 
		{
			String propsFileName = "configLogMessages.properties";
			input = getClass().getClassLoader().getResourceAsStream(propsFileName);
			
			logTest = _p.getProperty(logTest);
			LOGGER.log(Level.INFO, "Startar hämtning av logg meddelanden");
			_p.load(input);
	
			
		} 
		
		catch (FileNotFoundException e) 
		{
			LOGGER.log(Level.WARNING, "configLogMessages.properties-filen kan inte hittas",e);
		}
		
		catch (IOException e) 
		{
			LOGGER.log(Level.WARNING, "Något gick fel vid hämtning av data från configLogMessages.properties",e);
		} 
		
		finally 
		{
			if (input != null) 
			{
				
				try 
				{
					input.close();
					LOGGER.log(Level.INFO, "Hämtning av Loggmeddelanden klar.");
				} 
				
				catch (IOException e)
				{
					LOGGER.log(Level.WARNING, "Något gick fel.", e);
				}
			}
		}
	}
	
}
