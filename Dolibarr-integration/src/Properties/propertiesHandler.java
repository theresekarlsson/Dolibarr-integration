package Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import Log.logMessageHandler;

public final class propertiesHandler {
	public static String URI;
	public static String oauth2Key;
	public static String email;
	public static String URLDolibarrDB;
	public static String dbName;
	public static String dbPassword;
	public static String logFileName;
	public static String logFilePath;
	public static String mailSubject;
	public static String mailContent;
	public static String emailSender;
	public static String emailSenderUserName;
	public static String emailSenderPassWord;
	
	
	
	private static final Logger LOGGER = Logger.getLogger(propertiesHandler.class.getName());
	
	/* Hämtar properties från config.properties och sparar som strängar. */
	private propertiesHandler()
	{
		
	}
	
	public static void getAllPropertiesFromPropertiesFile() {
		
		Properties properties = new Properties();
		InputStream input = null;
		
		try 
		{
			String propsFileName = "config.properties";
			input = propertiesHandler.class.getClassLoader().getResourceAsStream(propsFileName);
			
			LOGGER.log(Level.INFO, logMessageHandler.getPropertiesMessage);
			properties.load(input);
			
			dbName = properties.getProperty("dbName");
			dbPassword = properties.getProperty("dbPassword");
			URI = properties.getProperty("URI");
			oauth2Key = properties.getProperty("oauth2Key");
			email = properties.getProperty("email");
			URLDolibarrDB = properties.getProperty("URLDolibarrDB");
			logFileName = properties.getProperty("logFileName");
			logFilePath = properties.getProperty("logFilePath");
			mailSubject = properties.getProperty("mailSubject");
			mailContent = properties.getProperty("mailContent");
			emailSender = properties.getProperty("emailSender");
			emailSenderUserName = properties.getProperty("emailSenderUserName");
			emailSenderPassWord = properties.getProperty("emailSenderPassWord");
			
			
		} 
		
		catch (FileNotFoundException e) 
		{
			LOGGER.log(Level.SEVERE, logMessageHandler.propertiesFileNotFound,e);
		}
		
		catch (IOException e) 
		{
			LOGGER.log(Level.SEVERE, logMessageHandler.propertiesIOException,e);
		} 
		
		finally 
		{
			if (input != null) 
			{
				
				try 
				{
					input.close();
					LOGGER.log(Level.INFO, logMessageHandler.getPropertiesMessageFinished);
				} 
				
				catch (IOException e)
				{
					LOGGER.log(Level.WARNING, logMessageHandler.closeReadPropertiesIOexception, e);
				}
			}
		}
	}
	
}
