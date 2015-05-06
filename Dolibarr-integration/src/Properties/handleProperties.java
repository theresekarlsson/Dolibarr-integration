package Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class handleProperties {
	String URI;
	String oauth2Key;
	String email;
	
	/* Hämtar url (där leads hämtas) från config.properties och returnerar som en sträng. */
	public void getAllPropertiesFromPropertiesFile() {
		
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
			e.printStackTrace();
		}
		
		catch (IOException e) 
		{
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
