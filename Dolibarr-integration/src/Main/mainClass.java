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
	private String URI;
	private String oauth2Key;
	private String email;
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
		JDBCinsert JI = new JDBCinsert();
		
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
		oauth2Key = HP.getOauth2Key();
		email = HP.getEmail();
	}
	
	public ArrayList<leads> getLeads()
	{
		GL = new getLeads();
		return GL.createLeadArray(GL.getResponse(URI, oauth2Key));
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
