package Main;

import Properties.*;

import java.util.ArrayList;
import java.util.logging.*;

import Leads.JDBCinsert;
import Leads.getLeads;
import Leads.leads;
import Leads.removeLeads;
import Log.logHandler;
import Log.logMessageHandler;

public class mainClass
{
	private static final Logger LOGGER = Logger.getLogger(mainClass.class.getName()); 
	
	private logHandler HL;
	private getLeads GL;
	private JDBCinsert JI;
	private removeLeads RL;
	private logMessageHandler MH;
	
	ArrayList<leads> leadsList = new ArrayList<leads>();
	
	public static void main(String[] args)
	{
		mainClass _m = new mainClass();
	}
	
	
	public mainClass()
	{
		logMessageHandler.getAllLogMessagesFromFile();
		propertiesHandler.getAllPropertiesFromPropertiesFile();
		LOGGER.log(Level.INFO, logMessageHandler.startingProgram);
		
		startLogToFile();
	
		
		RL = new removeLeads();
		RL.deleteProspectLeads();
		RL.colseConnection();
		
		leadsList = getLeads();
		
		JI = new JDBCinsert();
		
		for (int i = 0; i < leadsList.size(); i++)
		{
			JI.insertLead(leadsList.get(i));
		}
		JI.closeConnection();
		
		stopLogToFile();
	}
	
	public ArrayList<leads> getLeads()
	{
		GL = new getLeads();
		return GL.createLeadArray(GL.getResponse());
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
