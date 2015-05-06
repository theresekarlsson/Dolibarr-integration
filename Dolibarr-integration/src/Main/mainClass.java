package Main;

import Properties.*;

import java.util.logging.*;
import Leads.getLeads;
public class mainClass
{
	private static final Logger log = Logger.getLogger(mainClass.class.getName()); 
	/* Används för loggning, ska ligga överst i varje klass. Observera att rätt klassnamn används. */

	private handleProperties HP;
	private getLeads GL;
	
	private String URI;
	private String oauth2Key;
	private String email;
	
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		mainClass _m = new mainClass();
	}
	
	
	public mainClass()
	{
		importProperties();
		getLeads();
		// TODO Initialize properties Class
		// TODO Initialize sendAlarm Class
		// TODO Initialize getLeads Class 
		// TODO Initialize establishConnection Class
		// TODO Initialize loggToFile Class
		// TODO Initialize removeLeads Class
	}
	
	public void importProperties()
	{
		HP = new handleProperties();
		HP.getAllPropertiesFromPropertiesFile();
		URI = HP.getURI();
		oauth2Key = HP.getOauth2Key();
		email = HP.getEmail();
		
	}
	
	public void getLeads()
	{
		GL = new getLeads();
		GL.getResponse(URI, oauth2Key);
		
		//log.log(Level.FINEST, "testlogg - funkar inte...");
		
	}
	
	public void sendAlarm()
	{
		// TODO Run senAlarm
		String email = null;
		
		System.out.println(email);
	}
	
	public void loggToFile()
	{
		// TODO Run loggToFile
	}
	
	public void removeLeads()
	{
		// TODO run revoeLeads	
	}
	
	
}
