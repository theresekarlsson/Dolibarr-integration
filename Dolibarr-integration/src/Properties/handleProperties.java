package Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import Main.mainClass;


public class handleProperties {
	private String URI;
	private String oauth2Key;
	private String email;
	private static final Logger LOGGER = Logger.getLogger(handleProperties.class.getName()); 
	
	/* Hämtar url (där leads hämtas) från config.properties och returnerar som en sträng. */
	public void getAllPropertiesFromPropertiesFile() {
		
		LOGGER.info("inne i handleProperties");
		Properties properties = new Properties();
		InputStream input = null;
		
		try 
		{
			
			String fileName = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(fileName);
			properties.load(input);
			
			URI = properties.getProperty("URI");
			oauth2Key = properties.getProperty("oauth2Key");
			email = properties.getProperty("email");
			
		} 
		
		catch (FileNotFoundException e) 
		{
			LOGGER.log(Level.WARNING, "config.properties-filen kan inte hittas",e);
			e.printStackTrace();
		}
		
		catch (IOException e) 
		{
			LOGGER.log(Level.WARNING, "Något gick fel vid hämtning av data från config.properties",e);
			e.printStackTrace();
		} 
		
		finally 
		{
			if (input != null) 
			{
				
				try 
				{
					input.close();
				} 
				
				catch (IOException e)
				{
					LOGGER.log(Level.WARNING, "Något gick fel.",e);
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public String getURI()
	{
		return URI;
	}
	
	public String getOauth2Key()
	{
		return oauth2Key;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	

}
