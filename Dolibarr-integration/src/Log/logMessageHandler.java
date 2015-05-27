package Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import Properties.propertiesHandler;

public final class logMessageHandler {

	//Info meddelanden till logMessageHandler
	public static String getLogMessages;	
	public static String getLogMessagesFinished;
	
	//Info meddelanden till propertiesHandler
	public static String getPropertiesMessage;
	public static String getPropertiesMessageFinished;
	
	//Info meddelanden till mainClass
	public static String startingProgram;
	
	//Info meddelanden till mailCeator
	public static String mailSent;
	
	//Info meddelanden till getLeads
	public static String getLeadsStart;
	public static String httprequest;
	public static String tmpXMLfile;
	public static String gettingXMLFileDone;
	public static String strippXMLFile;
	public static String puttingLeadsInLeadsList;
	public static String getLeadsFinished;
	
	//Warning meddelanden till logMessageHandler
	public static String logMessageFileNotFound;
	public static String logMessageIOException;
	public static String closeReadLogMessageIOException;

	//Warning meddelanden till propertiesHandler
	public static String propertiesFileNotFound;
	public static String propertiesIOException;
	public static String closeReadPropertiesIOexception;
	
	//Warning meddelanden till mailCreator
	public static String mailCreatorAddressException;
	public static String mailCreatorMessagingException;
	
	private static final Logger LOGGER = Logger.getLogger(propertiesHandler.class.getName());
	
	private logMessageHandler(){
		
	}
	
	//Hämtar logg meddelanden från configLogMessages.properties och sparar som strängar. 
	public static  void getAllLogMessagesFromFile() {
		
		Properties _p = new Properties();
		InputStream input = null;
		
		try 
		{
			String propsFileName = "configLogMessages.properties";
			input = logMessageHandler.class.getClassLoader().getResourceAsStream(propsFileName);
			
			
			_p.load(input);
			
			//Hämtar Info meddelanden till logMessageHandler
			getLogMessages = _p.getProperty("info.getLogMessages");
			getLogMessagesFinished = _p.getProperty("info.getLogMessagesFinished");
			
			//Hämtar Warning meddelanden till logMessageHandler
			closeReadLogMessageIOException = _p.getProperty("warning.closeReadLogMessageIOException");
			
			//Hämtar Severe meddelanden till logMessageHandler
			logMessageFileNotFound = _p.getProperty("severe.logMessageFileNotFound");
			logMessageIOException = _p.getProperty("severe.logMessageIOException");
			
			//Hämtar Info meddelanden till propertiesHandler
			getPropertiesMessage = _p.getProperty("info.getPropertiesMessage");
			getPropertiesMessageFinished = _p.getProperty("info.getPropertiesMessageFinished");
			
			//Hämtar Warning meddelanden till propertiesHandler
			closeReadPropertiesIOexception = _p.getProperty("warning.closeReadPropertiesIOexception");
			
			//Hämtar Severe meddelanden till propertiesHandler
			propertiesFileNotFound=_p.getProperty("warning.propertiesFileNotFound");
			propertiesIOException = _p.getProperty("warning.propertiesIOException");
			
			//Hämtar Info meddelanden till mainClass
			startingProgram = _p.getProperty("info.startingProgram");
			
			//Hämtar Info meddelanden till mailCreator
			mailSent = _p.getProperty("info.mailSent");
			
			//Hämtar Warning meddelanden till mailCreator
			mailCreatorAddressException = _p.getProperty("warning.mailCreatorAddressException");
			mailCreatorMessagingException = _p.getProperty("warning.mailCreatorMessagingException");
			
			//Hämtar Info meddelanden till getLeads
			
			getLeadsStart = _p.getProperty("info.getLeadsStart");
			httprequest = _p.getProperty("info.httprequest");
			tmpXMLfile;
			gettingXMLFileDone;
			strippXMLFile;
			puttingLeadsInLeadsList;
			getLeadsFinished;
			
			LOGGER.log(Level.INFO, getLogMessages);
			
		} 
		
		catch (FileNotFoundException e) 
		{
			LOGGER.log(Level.SEVERE, logMessageFileNotFound,e);
		}
		
		catch (IOException e) 
		{
			LOGGER.log(Level.SEVERE, logMessageIOException,e);
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
					LOGGER.log(Level.WARNING, closeReadLogMessageIOException, e);
				}
			}
		}
	}	
	
}
