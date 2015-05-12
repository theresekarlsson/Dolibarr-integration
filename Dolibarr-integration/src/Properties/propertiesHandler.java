package Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class propertiesHandler {
	private String URI;
	private String oauth2Key;
	private String email;
	private static final Logger aLogger = Logger.getLogger(propertiesHandler.class.getName());
	
	/* H�mtar properties fr�n config.properties och sparar som str�ngar. */
	public void getAllPropertiesFromPropertiesFile() {
		
		Properties properties = new Properties();
		InputStream input = null;
		
		try 
		{
			String fileName = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(fileName);
			
			aLogger.log(Level.INFO, "Startar h�mtning av properties.");
			properties.load(input);
			
			URI = properties.getProperty("URI");
			oauth2Key = properties.getProperty("oauth2Key");
			email = properties.getProperty("email");
		} 
		
		catch (FileNotFoundException e) 
		{
			aLogger.log(Level.WARNING, "config.properties-filen kan inte hittas",e);
			e.printStackTrace();
		}
		
		catch (IOException e) 
		{
			aLogger.log(Level.WARNING, "N�got gick fel vid h�mtning av data fr�n config.properties",e);
			e.printStackTrace();
		} 
		
		finally 
		{
			if (input != null) 
			{
				
				try 
				{
					input.close();
					aLogger.log(Level.INFO, "H�mtning av properties klar.");
				} 
				
				catch (IOException e)
				{
					aLogger.log(Level.WARNING, "N�got gick fel.", e);
				}
			}
		}
		
	}
	
	/* Returnerar URI */
	public String getURI()
	{
		return URI;
	}
	
	/* Returnerar nyckel */
	public String getOauth2Key()
	{
		return oauth2Key;
	}
	
	/* Returnerar e-post */
	public String getEmail()
	{
		return email;
	}
}
