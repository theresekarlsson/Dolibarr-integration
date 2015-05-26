package Main;

import Properties.*;

import java.util.ArrayList;
import java.util.logging.*;

import Leads.JDBCinsert;
import Leads.getLeads;
import Leads.leads;
import Log.logHandler;

public class mainClass
{
	private static final Logger LOGGER = Logger.getLogger(mainClass.class.getName()); 

	private propertiesHandler HP;
	private logHandler HL;
	private getLeads GL;
	private JDBCinsert JI;
	private Leads.removeLeads RL;
	
	private String URI;
	private String OAUTH2KEY;
	private String URLDOLIBARRDB;
	private String DBNAME;
	private String DBPASSWORD;
	private String LOGFILENAME;
	private String LOGFILEPATH;
	
	ArrayList<leads> leadsList = new ArrayList<leads>();
	
	public static void main(String[] args)
	{
		mainClass _m = new mainClass();
	}
	
	
	public mainClass()
	{
		importProperties();
		startLogToFile();
		
		LOGGER.log(Level.INFO, "Program körs.");
		
		leadsList = getLeads();
		RL = new Leads.removeLeads();
		
		RL.deleteProspectLeads();
		RL.colseConnection();
		JI = new JDBCinsert(URLDOLIBARRDB, DBNAME, DBPASSWORD);
		
		for (int i = 0; i < leadsList.size(); i++)
		{
			JI.insertLead(leadsList.get(i));
		}
		JI.closeConnection();
		
		stopLogToFile();
	}
	
	public void importProperties()
	{
		HP = new propertiesHandler();
		HP.getAllPropertiesFromPropertiesFile();
		URI = HP.getURI();
		OAUTH2KEY = HP.getOauth2Key();
		URLDOLIBARRDB = HP.getURLDolibarrDB();
		DBNAME = HP.getDbName();
		DBPASSWORD = HP.getDbPassword();
		LOGFILENAME = HP.getLogFileName();
		LOGFILEPATH = HP.getLogFilePath();
	}
	
	public ArrayList<leads> getLeads()
	{
		GL = new getLeads();
		return GL.createLeadArray(GL.getResponse(URI, OAUTH2KEY));
	}
	
	public void startLogToFile()
	{
		HL = new logHandler();
		HL.startLogging(LOGGER, LOGFILENAME, LOGFILEPATH, HP);
	}
	
	private void stopLogToFile() {
		HL.closeLogFile();
	}
	
	public void removeLeads()
	{
		// TODO run revoeLeads	
	}	
}
