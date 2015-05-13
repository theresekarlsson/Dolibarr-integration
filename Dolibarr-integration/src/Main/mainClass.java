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
	
	private String URI;
	private String OAUTH2KEY;
	private String EMAIL;
	private String URLDOLIBARRDB;
	private String DBNAME;
	private String DBPASSWORD;
	
	ArrayList<leads> leadsList = new ArrayList<leads>();
	
	public static void main(String[] args)
	{
		mainClass _m = new mainClass();
	}
	
	
	public mainClass()
	{
		startLogToFile();
		
		LOGGER.log(Level.INFO, "Program körs.");
		
		importProperties();
		leadsList = getLeads();
		JI = new JDBCinsert(URLDOLIBARRDB, DBNAME, DBPASSWORD);
		
		for(int i = 0; i < leadsList.size(); i++)
		{
			JI.insertLead(leadsList.get(i));
		}
		JI.closeConnection();
		
		// TODO Initialize sendAlarm Class
		// TODO Initialize getLeads Class 
		// TODO Initialize removeLeads Class
		
		stopLogToFile();
	}
	
	public void importProperties()
	{
		HP = new propertiesHandler();
		HP.getAllPropertiesFromPropertiesFile();
		URI = HP.getURI();
		OAUTH2KEY = HP.getOauth2Key();
		EMAIL = HP.getEmail();
		URLDOLIBARRDB = HP.getURLDolibarrDB();
		DBNAME = HP.getDbName();
		DBPASSWORD = HP.getDbPassword();
	}
	
	public ArrayList<leads> getLeads()
	{
		GL = new getLeads();
		return GL.createLeadArray(GL.getResponse(URI, OAUTH2KEY));
	}
	
	public void sendAlarm()
	{
		// TODO Run senAlarm
		String email = null;
		System.out.println(email);
	}
	
	public void startLogToFile()
	{
		HL = new logHandler();
		HL.startLogging(LOGGER);
	}
	
	private void stopLogToFile() {
		HL.closeLogFile();
	}
	
	public void removeLeads()
	{
		// TODO run revoeLeads	
	}	
}
