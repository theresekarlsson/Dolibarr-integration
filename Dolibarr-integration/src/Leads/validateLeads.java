package Leads;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Log.logMessageHandler;
import Properties.propertiesHandler;

public class validateLeads {

	private static final Logger LOGGER = Logger.getLogger(validateLeads.class.getName());
	//private boolean value;
	private int countInvalidLeads = 0;	// Antal felaktiga leads
	private int countCorrectLeads;		// Antal validerade leads, dvs. efter att ev. felaktiga leads tagits bort.
	
	ArrayList<String> failReport = new ArrayList<String>();
	ArrayList<String> invalidLeads = new ArrayList<String>();
	ArrayList<leads> aLeadsList = new ArrayList<leads>();
	
	public void checkList(ArrayList<leads> aLeadsList)
	{
		this.aLeadsList = aLeadsList;
		checkIfEmpty();
		checkForDuplicates();
		checkValues();
		//compareToLastWeek();
		countLeads();
		
		if (!invalidLeads.isEmpty()) 
		{
			for (int i = 0; i<invalidLeads.size(); i++) 
			{
				countInvalidLeads++;
				LOGGER.log(Level.WARNING, logMessageHandler.validateLeadsCorruptedLeads + invalidLeads.get(i));
			}
			
			LOGGER.log(Level.WARNING,logMessageHandler.validateLeadsNumberOfCorruptedLeads + countInvalidLeads);
		}
		
		if(failReport.isEmpty())
		{
			saveListToFile(aLeadsList);
		}
		
		else
		{
			for(int i = 0; i<failReport.size(); i++)
			{
				LOGGER.log(Level.SEVERE, logMessageHandler.validateLeadsTriggedMail + failReport.get(i));			
			}	
		}
		
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsLeadsValidated);
	}
	
	public void checkIfEmpty()
	{
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCheckIfEmpty);
		if(aLeadsList.size() < 1)	
		{
			failReport.add("Whole list is empty");
		}
		
		for(int i=0; i<aLeadsList.size(); i++)
		{

			if(aLeadsList.get(i).getName() == "" || aLeadsList.get(i).getName() == null) {
				invalidLeads.add("Item number " + i + " has no name");
				aLeadsList.remove(i);
			}
			
			if(aLeadsList.get(i).getAddress() == "" || aLeadsList.get(i).getAddress() == null) {
				invalidLeads.add("Item number " + i + " has no address");
				aLeadsList.remove(i);
			}
			
			if(aLeadsList.get(i).getCity() == "" || aLeadsList.get(i).getCity() == null) {
				invalidLeads.add("Item number " + i + " has no City");
				aLeadsList.remove(i);
			}
		
			if(aLeadsList.get(i).getContact() == "" || aLeadsList.get(i).getContact() == null) {
				invalidLeads.add("Item number " + i + " has no contact");
				aLeadsList.remove(i);
			}
			
			if(aLeadsList.get(i).getTele() == "" || aLeadsList.get(i).getTele() == null) {
				invalidLeads.add("Item number " + i + " has no tele");
				aLeadsList.remove(i);
			}
			
			if(aLeadsList.get(i).getZip() == "" || aLeadsList.get(i).getZip() == null) {
				invalidLeads.add("Item number " + i + " has no zip");
				aLeadsList.remove(i);
			}
			
			if(aLeadsList.get(i).getEmail() == "" || aLeadsList.get(i).getEmail() == null) {
				invalidLeads.add("Item number " + i + " has no Email");
				aLeadsList.remove(i);
			}

			if(aLeadsList.get(i).getCurrent_provider() == "" || aLeadsList.get(i).getCurrent_provider() == null) {
				invalidLeads.add("Item number " + i + " has no Current provider");	
				aLeadsList.remove(i);
			}
			
			if(aLeadsList.get(i).getSize() == "" || aLeadsList.get(i).getSize() == null) {
				invalidLeads.add("Item number " + i + " has no Size");
				aLeadsList.remove(i);
			}
		}
	}
	
	public void checkForDuplicates()
	{
		// Här ska den felaktiga leaden tas bort, (räknas som felaktig för logg), men inget mail ska skickas.
		
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCheckForDuplicates);
		leads tmpLead = new leads();
		boolean duplicates = false;
		
		for(int i = 0; i < aLeadsList.size(); i++)
		{
			tmpLead = aLeadsList.get(i);
			
			for(int y = 0; y < aLeadsList.size(); y++)
			{
				if(tmpLead == aLeadsList.get(y))
				{
					
					if(duplicates)
					{
						invalidLeads.add("Item number: " + y + " has a duplicate." );	
						aLeadsList.remove(i);
					} 
					duplicates = true;
				}
			}
			duplicates = false;
		}
	}
	
	public void checkValues()
	{
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCheckValues);
		
		String regex = "[0-9]+";
		String tmpTele;
		for(int i = 0; i < aLeadsList.size(); i++)
		{
			tmpTele = aLeadsList.get(i).getTele();
			tmpTele = tmpTele.replace("-", "");
			tmpTele = tmpTele.replace("(", "");
			tmpTele = tmpTele.replace(")", "");
			tmpTele = tmpTele.replace(" ", "");
			
			
			if(!aLeadsList.get(i).getZip().matches(regex))
			{
				invalidLeads.add("Item number: " + i + " has unvalid zipcode(can only contain numbers)");
				aLeadsList.remove(i);
			}
			
			if(aLeadsList.get(i).getCity().matches(regex))
			{
				invalidLeads.add("Item number: " + i + " City cannot contain numbers");
				aLeadsList.remove(i);
			}
			
			if(aLeadsList.get(i).getContact().matches(regex))
			{
				invalidLeads.add("Item number: " + i + " contact name cannot contain numbers");
				aLeadsList.remove(i);
			}
			
			if(!tmpTele.matches(regex))
			{
				invalidLeads.add("Item number: " + i + " has unvalid phonenumber (can only contain numbers)");
				aLeadsList.remove(i);
				
				
			}
			
			String email = aLeadsList.get(i).getEmail();
	        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	        Matcher mat = pattern.matcher(email);

	        if(!mat.matches()){
	        	invalidLeads.add("Item number: " + i + " Has invalid email");
	        	aLeadsList.remove(i);
	        }	
		}
	}

	
	/* Jämför gamla listan med den nya */
	public void compareToLastWeek()
	{
		File tmpFileWithLeads = new File(propertiesHandler.tmpLeadsListFilePath + propertiesHandler.tmpLeadsListFile);
		
		try
		{
			if (tmpFileWithLeads.exists() && !tmpFileWithLeads.isDirectory())
			{
				LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCompareToLastWeek);
				
				FileInputStream fileInputStream = new FileInputStream(tmpFileWithLeads);			
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		        ArrayList<leads> oldLeads = (ArrayList<leads>) objectInputStream.readObject();
				objectInputStream.close();
				
				int match = 0;
				
				for (int i = 0; i < aLeadsList.size(); i++) 
				{
					for (int j = 0; j < oldLeads.size(); j++)
					{
						if (oldLeads.get(j).getName().equals(aLeadsList.get(i).getName()))
						{
							match++;
						}
					}
				}
			
				if (match == aLeadsList.size())
				{
					LOGGER.log(Level.WARNING, logMessageHandler.validateLeadsNumberOfDuplicatedLeads + match);
					failReport.add("The leads in the new list already exists in the old list");	
				}
				else
				{
					LOGGER.log(Level.INFO, logMessageHandler.validateLeadsNewListOK);
				}
			}
		} 
		catch(FileNotFoundException e) 
		{
			LOGGER.log(Level.WARNING, logMessageHandler.validateLeadsOldListNotFound, e);
		} 
		catch (IOException e) 
		{
			LOGGER.log(Level.WARNING, logMessageHandler.validateLeadsCouldNotReadOldList, e);
		} 
		catch (ClassNotFoundException e) 
		{
			LOGGER.log(Level.WARNING, logMessageHandler.validateLeadsCouldNotReadOldList, e);
		}
	}
	
	/* Räknar antalet leads i nya listan. Vid färre än min.värde eller fler än maxvärde görs en felrapport. */
	public void countLeads()
	{
		countCorrectLeads = 0;
		
		for (int i = 0; i < aLeadsList.size(); i++) 
		{
			countCorrectLeads++;
		}
		
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCorrectLeads + countCorrectLeads);

		int minValue = Integer.parseInt(propertiesHandler.minValueLeads);
		int maxValue = Integer.parseInt(propertiesHandler.maxValueLeads);
		
		if (countCorrectLeads < minValue)
		{
			failReport.add(countCorrectLeads + " is not an approved number of leads. The value should be above " + minValue);
		}
		else if( countCorrectLeads > maxValue)
		{
			failReport.add(countCorrectLeads + " is not an approved number of leads. The value should be below " + maxValue);
		}
		else 
		{
			LOGGER.log(Level.INFO, logMessageHandler.validateLeadsNumberOfLeads);
		}
	}
	
	/* Sparar nya listan med leads till fil. */
	public void saveListToFile(ArrayList<leads> aLeadsList) 
	{
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsSaveListToFile);
		
		File tmpFileWithLeads = new File(propertiesHandler.tmpLeadsListFilePath + propertiesHandler.tmpLeadsListFile);
		
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
		
		try 
		{
			fileOutputStream = new FileOutputStream(tmpFileWithLeads);
			objectOutputStream = new ObjectOutputStream(fileOutputStream);   
			objectOutputStream.writeObject(aLeadsList);
			objectOutputStream.close();
		
		} 
		catch(FileNotFoundException e) 
		{
			LOGGER.log(Level.WARNING, logMessageHandler.validateLeadsCouldNotSaveNewList, e);
		} 
		catch (IOException e) 
		{
			LOGGER.log(Level.WARNING, logMessageHandler.validateLeadsCouldNotSaveNewList, e);
		}
		finally
		{
			LOGGER.log(Level.INFO, logMessageHandler.validateLeadsSaveListToFileOK);
		}
	
	}
}
