package Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import Properties.propertiesHandler;


public final class logMessageHandler {

	//Info meddelanden till logHandler
	public static String loggingStartSuccess;
	
	//Info meddelanden till logMessageHandler
	public static String getLogMessages;	
	public static String getLogMessagesFinished;
	
	//Info meddelanden till propertiesHandler
	public static String getPropertiesMessage;
	public static String getPropertiesMessageFinished;
	
	//Info meddelanden till mainClass
	public static String startingProgram;
	
	//Info meddelanden till mailCreator
	public static String mailSent;
	public static String creatingEmail;
	public static String getLatestLog;
	public static String filePathToLastLog;
	
	//Infomeddelanden till mailingHandler
	public static String triggingMailFunction;
	
	//Info meddelanden till getLeads
	public static String getLeadsStart;
	public static String httprequest;
	public static String tmpXMLfile;
	public static String gettingXMLFileDone;
	public static String strippXMLFile;
	public static String getLeadsFinished;
	
	//Info meddelanden till JDBCinsert
	public static String JDBCinsertStart;
	public static String loadJDBCdriver;
	public static String JDBCconnectionMade;
	public static String leadsInserted;
	public static String JDBCconnectionClosed;
	
	//Info meddelanden till removeLeads
	public static String removeLeadsLoadDriver;
	public static String removeLeadsConnectionMade;
	public static String leadsDeleted;
	public static String removeLeadsConnectionClosed;
	
	//Warning meddelanden till logMessageHandler
	public static String closeReadLogMessageIOException;

	//Warning meddelanden till propertiesHandler
	public static String closeReadPropertiesIOexception;
	
	//Warning meddelanden till mailCreator
	public static String mailCreatorAddressException;
	public static String mailCreatorMessagingException;
	
	
	//Severe meddelanden till logHandler
	public static String logFileCreationFailedException;
	
	//Severe meddelanden  till logMessageHandler
	public static String logMessageFileNotFound;
	public static String logMessageIOException;
	
	//Severe meddelanden  till propertiesHandler
	public static String propertiesFileNotFound;
	public static String propertiesIOException;
	
	//Severe meddelanden till getLeads
	public static String httpRequestFailed;
	public static String connectionFailed;
	public static String couldNotStoreXML;
	public static String somethingWentWrongXML;
	public static String couldNotUnmarchall;
	
	//Severe meddelanden till JDBCinsert
	public static String couldNotLoadDriver;
	public static String databaseConnectionFailed;
	public static String SQLExecuteFailed;
	public static String couldNotCloseConnection;
	
	//Severe meddelanden till JDBCinsert
	public static String RLcouldNotLoadDriver;
	public static String RLdatabaseConnectionFailed;
	public static String RLcouldNotRemoveLeads;
	public static String RLcouldNotRemoveChildLeads;
	public static String RLcouldNotCloseConnection;
	
	private static final Logger LOGGER = Logger.getLogger(propertiesHandler.class.getName());
	
	
	private logMessageHandler(){
		
	}
	
	/* Hämtar loggmeddelanden från configLogMessages.properties och sparar som strängar.*/ 
	public static  void getAllLogMessagesFromFile() {
		
		Properties _p = new Properties();
		InputStream input = null;
		
		try 
		{
			String propsFileName = "configLogMessages.properties";								
			input = logMessageHandler.class.getClassLoader().getResourceAsStream(propsFileName);
			
			_p.load(input);
			
			//Hämtar Info meddelanden till logHandler
			loggingStartSuccess = _p.getProperty("info.loggingStartSuccess");
			
			//Hämtar Severe meddelanden till logHandler
			logFileCreationFailedException = _p.getProperty("severe.logFileCreationFailed");
			
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
			creatingEmail = _p.getProperty("info.creatingEmail");
			getLatestLog = _p.getProperty("info.getLatestLog");
			filePathToLastLog = _p.getProperty("info.filePathToLastLog");
			
			//Hämtar Infomeddelanden till mailingHandler
			triggingMailFunction = _p.getProperty("info.triggingMailFunction");
			
			//Hämtar Warning meddelanden till mailCreator
			mailCreatorAddressException = _p.getProperty("warning.mailCreatorAddressException");
			mailCreatorMessagingException = _p.getProperty("warning.mailCreatorMessagingException");
			
			//Hämtar Info meddelanden till getLeads
			getLeadsStart = _p.getProperty("info.getLeadsStart");
			httprequest = _p.getProperty("info.httprequest");
			tmpXMLfile = _p.getProperty("info.tmpXMLfile");
			gettingXMLFileDone = _p.getProperty("info.gettingXMLFileDone");
			strippXMLFile = _p.getProperty("info.strippXMLFile");
			getLeadsFinished = _p.getProperty("info.getLeadsFinished");
			
			//Hämtar Severe meddelanden till getLeads
			httpRequestFailed = _p.getProperty("severe.httpRequestFailed");
			connectionFailed = _p.getProperty("severe.connectionFailed");
			couldNotStoreXML = _p.getProperty("severe.couldNotStoreXML");
			somethingWentWrongXML = _p.getProperty("severe.somethingWentWrongXML");
			couldNotUnmarchall = _p.getProperty("severe.JABXExceptions");
			
			//Hämtar Info meddelanden till JDBCinsert
			JDBCinsertStart = _p.getProperty("info.JDBCinsertStart");
			loadJDBCdriver = _p.getProperty("info.loadJDBCdriver");
			JDBCconnectionMade = _p.getProperty("info.JDBCconnectionMade");
			leadsInserted = _p.getProperty("info.leadsInserted");
			JDBCconnectionClosed = _p.getProperty("info.JDBCconnectionClosed");
			
			//Hämtar Severe meddelanden till JDBCinsert
			couldNotLoadDriver = _p.getProperty("severe.couldNotLoadDriver");
			databaseConnectionFailed = _p.getProperty("severe.databaseConnectionFailed");
			SQLExecuteFailed = _p.getProperty("severe.SQLExecuteFailed");
			couldNotCloseConnection = _p.getProperty("severe.couldNotCloseConnection");
			
			//Hämtar Info meddelanden till removeLeads
			removeLeadsLoadDriver = _p.getProperty("info.removeLeadsLoadDriver");
			removeLeadsConnectionMade = _p.getProperty("info.removeLeadsConnectionMade");
			leadsDeleted = _p.getProperty("info.leadsDeleted");
			removeLeadsConnectionClosed = _p.getProperty("info.removeLeadsConnectionClosed");
			
			//Hämtar Severe meddelanden till removeLeads
			RLcouldNotLoadDriver = _p.getProperty("severe.RLcouldNotLoadDriver");
			RLdatabaseConnectionFailed = _p.getProperty("severe.RLdatabaseConnectionFailed");
			RLcouldNotRemoveLeads = _p.getProperty("severe.RLcouldNotRemoveLeads");
			RLcouldNotRemoveChildLeads = _p.getProperty("severe.RLcouldNotRemoveChildLeads");
			RLcouldNotCloseConnection = _p.getProperty("severe.RLcouldNotCloseConnection");
			
			
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
