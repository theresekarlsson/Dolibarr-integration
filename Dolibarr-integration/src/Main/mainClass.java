package Main;

import Properties.*;

import java.util.logging.*;

public class mainClass
{
	private static final Logger log = Logger.getLogger(mainClass.class.getName()); 
	/* Anv�nds f�r loggning, ska ligga �verst i varje klass. Observera att r�tt klassnamn anv�nds. */

	//private getLeads GL = new getLeads();
	private handleProperties HP = new handleProperties();
	
	public mainClass()
	{

		getLeads();
		// TODO Initialize properties Class
		// TODO Initialize sendAlarm Class
		// TODO Initialize getLeads Class 
		// TODO Initialize establishConnection Class
		// TODO Initialize loggToFile Class
		// TODO Initialize removeLeads Class
	}
	
	public void establishConnection()
	{
		// TODO Run establishConnection, save the result in a boolean
		
	}
	
	public void getLeads()
	{
		// TODO Run getLeads
		String url = null;
		url = HP.getURLFromPropertiesFile();	//h�mtar url
		System.out.println(url);
		//log.log(Level.FINEST, "testlogg - funkar inte...");
		
	}
	
	public void sendAlarm()
	{
		// TODO Run senAlarm
		String email = null;
		email = HP.getEmailFromPropertiesFile();	//h�mtar e-post
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
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		mainClass _m = new mainClass();
	}
}
