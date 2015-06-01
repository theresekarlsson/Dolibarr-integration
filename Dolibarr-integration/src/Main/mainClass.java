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
	
	ArrayList<leads> leadsList = new ArrayList<leads>();				// Array för lista med leads.
	
	public static void main(String[] args)
	{
		mainClass _m = new mainClass();
	}
	
	
	public mainClass()
	{
		logMessageHandler.getAllLogMessagesFromFile();					// Hämtar alla meddelanden för loggning
		propertiesHandler.getAllPropertiesFromPropertiesFile();			// Hämtar data nödvändig för körning
		LOGGER.log(Level.INFO, logMessageHandler.startingProgram);
		
		startLogToFile();												// Påbörjar loggning
		removeLeads();													// Tar bort gamla leads
		leadsList = getLeads();											// Hämtyar leads, uppdaterar lista
		JI = new JDBCinsert();				
		
		for (int i = 0; i < leadsList.size(); i++)
		{
			JI.insertLead(leadsList.get(i));							// Kör in leads i Dolibarrs databas
		}
		
		JI.closeConnection();											// Stänger uppkoppling mot databas
		stopLogToFile();												// Avslutar loggning
	}
	
	/* Skapar referens till getLeads-klassen, 
	 * skapar uppkoppling, hämtar leads */
	public ArrayList<leads> getLeads()
	{
		GL = new getLeads();											
		return GL.createLeadArray(GL.getResponse());
	}
	
	/* Skapar referens till logHandler, påbörjar loggning. */
	public void startLogToFile()
	{
		HL = new logHandler();
		HL.startLogging(LOGGER);
	}
	
	/* Avslutar loggning */
	private void stopLogToFile() {
		HL.closeLogFile();
	}
	
	public void removeLeads()
	{
		RL = new removeLeads();
		RL.deleteProspectLeads();
		RL.closeConnection();
	}	
}
