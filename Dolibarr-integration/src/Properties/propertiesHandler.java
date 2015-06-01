package Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import Log.logMessageHandler;

/* Denna klass hanterar alla propertiesvariabler, d.v.s. värden som används i körning av programmet. */
public final class propertiesHandler {
	public static String URI;						// webadress för hämtning av leads
	public static String oauth2Key;					// nyckel
	public static String email;						// e-post till mottagare av larm
	public static String URLDolibarrDB;				// adress till databas
	public static String dbName;					// Anv.namn för inloggning i Dolibarrs databas
	public static String dbPassword;				// Lösenord för inloggnign i Dolibarrs databas
	public static String logFileName;				// Loggfilensnamn
	public static String logFilePath;				// Filväg till loggfilen
	public static String tmpLeadsListFile;			// Namn på fil med lista med leads
	public static String tmpLeadsListFilePath;		// Filväg till fil med lista med leads
	public static String mailSubject;				// Ämsesrad i e-postmeddelande för larm
	public static String mailContent;				// Textinnehåll för e-postmeddelande för larm
	public static String emailSender;				// Avsändarens e-postadress
	public static String emailSenderUserName;		// Avsändarens anv.namn
	public static String emailSenderPassWord;		// Avsändaren lösenord 
	public static String emailHost;					// Mailhost
	public static String minValueLeads;				// Minsta antal tillåtna leads
	public static String maxValueLeads;				// Maximalt antal tillåtna leads
	
	private static final Logger LOGGER = Logger.getLogger(propertiesHandler.class.getName());
	
	
	private propertiesHandler() { }
	
	/* Hämtar properties från config.properties och sparar som strängar. */
	public static void getAllPropertiesFromPropertiesFile() {
		
		Properties properties = new Properties();				// Skapar properties-objekt
		InputStream input = null;
		
		try 
		{
			String propsFileName = "config.properties"; 		// Namn på fil där variablerna hämtas
			input = propertiesHandler.class.getClassLoader()	// Laddar upp innehållet från filen till en stream
					.getResourceAsStream(propsFileName);
			
			LOGGER.log(Level.INFO, logMessageHandler.getPropertiesMessage);
			properties.load(input);								//	Laddar upp streamen i properties-objektet
				
			dbName = properties.getProperty("dbName");
			dbPassword = properties.getProperty("dbPassword");
			URI = properties.getProperty("URI");
			oauth2Key = properties.getProperty("oauth2Key");
			email = properties.getProperty("email");
			URLDolibarrDB = properties.getProperty("URLDolibarrDB");
			logFileName = properties.getProperty("logFileName");
			logFilePath = properties.getProperty("logFilePath");
			tmpLeadsListFile = properties.getProperty("tmpLeadsListFile");
			tmpLeadsListFilePath = properties.getProperty("tmpLeadsListFilePath");
			mailSubject = properties.getProperty("mailSubject");
			mailContent = properties.getProperty("mailContent");
			emailSender = properties.getProperty("emailSender");
			emailSenderUserName = properties.getProperty("emailSenderUserName");
			emailSenderPassWord = properties.getProperty("emailSenderPassWord");
			emailHost = properties.getProperty("emailHost");
			minValueLeads = properties.getProperty("minValueLeads");
			maxValueLeads = properties.getProperty("maxValueLeads");
	
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
