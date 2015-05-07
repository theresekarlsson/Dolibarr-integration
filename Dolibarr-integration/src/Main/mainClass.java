package Main;

import Properties.*;

import java.util.logging.*;

import Leads.getLeads;
import Log.handleLog;

public class mainClass
{
	private static final Logger LOGGER = Logger.getLogger(mainClass.class.getName()); 
	
	/* LOGGNING EJ KLAR */

	private handleProperties HP;
	private handleLog HL;
	private getLeads GL;
	private String URI;
	private String oauth2Key;
	private String email;
	
	
	public static void main(String[] args)
	{
		mainClass _m = new mainClass();
	}
	
	
	public mainClass()
	{
		logToFile();
		LOGGER.info("K�r program.");
		
		importProperties();
		getLeads();
		
		// TODO Initialize sendAlarm Class
		// TODO Initialize getLeads Class 
		// TODO Initialize removeLeads Class
	}
	
	public void importProperties()
	{
		LOGGER.info("Startar import fr�n config.properties.");
		HP = new handleProperties();
		HP.getAllPropertiesFromPropertiesFile();
		URI = HP.getURI();
		oauth2Key = HP.getOauth2Key();
		email = HP.getEmail();
		LOGGER.info("H�mtning fr�n config.properties klar.");
	}
	
	public void getLeads()
	{
		LOGGER.info("Startar h�mtning av leads.");
		GL = new getLeads();
		GL.getResponse(URI, oauth2Key);
		LOGGER.info("H�mtning av leads klar.");
	}
	
	public void sendAlarm()
	{
		// TODO Run senAlarm
		String email = null;
		System.out.println(email);
	}
	
	public void logToFile()
	{
		HL = new handleLog();
		HL.createLogFile(LOGGER);
	}
	
	public void removeLeads()
	{
		// TODO run revoeLeads	
	}	
}
