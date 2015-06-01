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
	
	ArrayList<leads> leadsList = new ArrayList<leads>();				// Array f�r lista med leads.
	
	public static void main(String[] args)
	{
		mainClass _m = new mainClass();
	}
	
	
	public mainClass()
	{
		logMessageHandler.getAllLogMessagesFromFile();					// H�mtar alla meddelanden f�r loggning
		propertiesHandler.getAllPropertiesFromPropertiesFile();			// H�mtar data n�dv�ndig f�r k�rning
		LOGGER.log(Level.INFO, logMessageHandler.startingProgram);
		
		startLogToFile();												// P�b�rjar loggning
		removeLeads();													// Tar bort gamla leads
		leadsList = getLeads();											// H�mtyar leads, uppdaterar lista
		JI = new JDBCinsert();				
		
		for (int i = 0; i < leadsList.size(); i++)
		{
			JI.insertLead(leadsList.get(i));							// K�r in leads i Dolibarrs databas
		}
		
		JI.closeConnection();											// St�nger uppkoppling mot databas
		stopLogToFile();												// Avslutar loggning
	}
	
	/* Skapar referens till getLeads-klassen, 
	 * skapar uppkoppling, h�mtar leads */
	public ArrayList<leads> getLeads()
	{
		GL = new getLeads();											
		return GL.createLeadArray(GL.getResponse());
	}
	
	/* Skapar referens till logHandler, p�b�rjar loggning. */
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
