package Leads;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Properties.propertiesHandler;

public class validateLeads {

	private static final Logger LOGGER = Logger.getLogger(validateLeads.class.getName());
	
	private boolean value;
	private int countDeletedLeads = 0;	// variabel som räknar antalet felaktiga leads
	
	ArrayList<String> failReport = new ArrayList<String>();
	ArrayList<String> invalidLeads = new ArrayList<String>();
	
	public void checkList(ArrayList<leads> aLeadsList)
	{
		checkIfEmpty(aLeadsList);
		checkForDuplicates(aLeadsList);
		checkValues(aLeadsList);
		compareToLastWeek(aLeadsList);
		countLeads(aLeadsList);
		
		/*TODO Kanske klart... Testas. 
		 * I varje funktion: Skapa ännu en array (deletedLeads kanske?) där de felaktiga leadsens felmeddelanden läggs. 
		Ta bort de felaktiga leadsen ur aLeadsList, som går vidare till nästa valideringsfunktion. 
		Räkna sedan antalet i deletedLeads, logga dem och medföljande felmeddelanden. 
		Vissa funktioner ska dock fortfarande trigga mailfunktion. */
		
		
		if (!invalidLeads.isEmpty()) 
		{
			for (int i = 0; i<invalidLeads.size(); i++) 
			{
				countDeletedLeads++;
				LOGGER.log(Level.WARNING, "Följande fel hittades i listan: " + invalidLeads.get(i));
			}
			LOGGER.log(Level.WARNING, countDeletedLeads + " antal felaktiga leads hittades.");
			
		}
		
		if(failReport.isEmpty())
		{
			saveListToFile(aLeadsList);
		}
		
		else
		{
			for(int i = 0; i<failReport.size(); i++)
			{
			LOGGER.log(Level.SEVERE, "Följande fel triggade mailfunktion: " + failReport.get(i));			
			}	
		}
	}
	
	public void checkIfEmpty(ArrayList<leads> aLeadsList)
	{
		LOGGER.log(Level.INFO, "Kollar om listan är tom");
		if(aLeadsList.size() < 1)	
		{
			failReport.add("Whole list is empty");
		}
		for(int i=0; i<aLeadsList.size(); i++)
		{
			// Här ska den felaktiga leaden tas bort, (räknas som felaktig för logg), men inget mail ska skickas.
		
			if(aLeadsList.get(i).getName() == "" || aLeadsList.get(i).getName() == null) {
				invalidLeads.add("Item number " + i + " has no name");
				invalidLeads.remove(i);
			}
			
			if(aLeadsList.get(i).getAddress() == "" || aLeadsList.get(i).getAddress() == null) {
				invalidLeads.add("Item number " + i + " has no address");
				invalidLeads.remove(i);
			}
			
			if(aLeadsList.get(i).getCity() == "" || aLeadsList.get(i).getCity() == null) {
				invalidLeads.add("Item number " + i + " has no City");
				invalidLeads.remove(i);
			}
		
			if(aLeadsList.get(i).getContact() == "" || aLeadsList.get(i).getContact() == null) {
				invalidLeads.add("Item number " + i + " has no contact");
				invalidLeads.remove(i);
			}
			
			if(aLeadsList.get(i).getTele() == "" || aLeadsList.get(i).getTele() == null) {
				invalidLeads.add("Item number " + i + " has no tele");
				invalidLeads.remove(i);
			}
			
			if(aLeadsList.get(i).getZip() == "" || aLeadsList.get(i).getZip() == null) {
				invalidLeads.add("Item number " + i + " has no zip");
				invalidLeads.remove(i);
			}
			
			if(aLeadsList.get(i).getEmail() == "" || aLeadsList.get(i).getEmail() == null) {
				invalidLeads.add("Item number " + i + " has no Email");
				invalidLeads.remove(i);
			}

			if(aLeadsList.get(i).getCurrent_provider() == "" || aLeadsList.get(i).getCurrent_provider() == null) {
				invalidLeads.add("Item number " + i + " has no Current provider");	
				invalidLeads.remove(i);
			}
			
			if(aLeadsList.get(i).getSize() == "" || aLeadsList.get(i).getSize() == null) {
				invalidLeads.add("Item number " + i + " has no Size");
				invalidLeads.remove(i);
			}
		}
	}
	
	public void checkForDuplicates(ArrayList<leads> aLeadsList)
	{
		// Här ska den felaktiga leaden tas bort, (räknas som felaktig för logg), men inget mail ska skickas.
		
		LOGGER.log(Level.INFO, "Kollar om lista innehållet dubletter");
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
						invalidLeads.remove(i);
					} 
					duplicates = true;
				}
			}
			duplicates = false;
		}

	}
	
	public void checkValues(ArrayList<leads> aLeadsList)
	{
		
		// Här ska den felaktikta leaden tas bort, (räknas som felaktig för logg), men inget mail ska skickas.
		
		LOGGER.log(Level.INFO, "Kollar om listan innehåller korrupta värden");
		String regex = "[0-9]+";
		
		String tmpTele;
		for(int i = 0; i < aLeadsList.size(); i++)
		{
			tmpTele = aLeadsList.get(i).getTele();
			tmpTele = tmpTele.replace("-", "");
			//ta bort paranteser
			
			
			if(!aLeadsList.get(i).getZip().matches(regex))
			{
				invalidLeads.add("Item number: " + i + " has unvalid zipcode(can only contain numbers)");
				invalidLeads.remove(i);
			}
			
			if(aLeadsList.get(i).getCity().matches(regex))
			{
				invalidLeads.add("Item number: " + i + " City cannot contain numbers");
				invalidLeads.remove(i);
			}
			
			if(aLeadsList.get(i).getContact().matches(regex))
			{
				invalidLeads.add("Item number: " + i + " contact name cannot contain numbers");
				invalidLeads.remove(i);
			}
			
			if(!tmpTele.matches(regex))
			{
				invalidLeads.add("Item number: " + i + " has unvalid phonenumber (can only contain numbers)");
				invalidLeads.remove(i);
			}
			
			String email = aLeadsList.get(i).getEmail();
	        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	        Matcher mat = pattern.matcher(email);

	        if(!mat.matches()){
	        	invalidLeads.add("Item number: " + i + " Has invalid email");
	        	invalidLeads.remove(i);
	        }	
		}
	}

	
	/* Jämför gamla listan med den nya */
	public void compareToLastWeek(ArrayList<leads> aLeadsList)
	{
		
		File tmpFileWithLeads = new File(propertiesHandler.tmpLeadsListFilePath + propertiesHandler.tmpLeadsListFile);
		
		try
		{
			if (tmpFileWithLeads.exists() && !tmpFileWithLeads.isDirectory())
			{
				LOGGER.log(Level.INFO, "Påbörjar jämförelse med förra veckans lista");
				
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
			
				LOGGER.log(Level.INFO, "Antal poster i den nya listan som finns i den gamla listan: " + match);
				
				if (match == aLeadsList.size())
				{
					LOGGER.log(Level.INFO, "Det finns " + match + " leads i den nya listan. Alla fanns i den gamla listan.");
					failReport.add("The leads in the new list already exists in the old list");	
				}
				else
				{
					LOGGER.log(Level.INFO, "Gammal och ny lista jämförda. Den nya är ok.");
				}
			}
		} 
		catch(FileNotFoundException e) 
		{
			LOGGER.log(Level.INFO, "Filen med den gamla listan kunde inte hittas.", e);
		} 
		catch (IOException e) 
		{
			LOGGER.log(Level.INFO, "Fel uppstod vid inläsning av fil med den gamla listan.", e);
		} 
		catch (ClassNotFoundException e) 
		{
			LOGGER.log(Level.INFO, "Fel uppstod vid inläsning av fil med den gamla listan.", e);
		}
	}
	
	/* Räknar antalet leads i nya listan. Vid färre än min.värde eller fler än maxvärde görs en felrapport. */
	public void countLeads(ArrayList<leads> aLeadsList)
	{
		int count = 0;
		for (int i = 0; i < aLeadsList.size(); i++) 
		{
			count++;
		}
		
		LOGGER.log(Level.INFO, "Antal validerade leads i listan: " + count);

		int minValue = Integer.parseInt(propertiesHandler.minValueLeads);
		int maxValue = Integer.parseInt(propertiesHandler.maxValueLeads);
		
		if (count < minValue || count > maxValue)
		{
			failReport.add(count + " is not an approved number of leads. The value should be above " + minValue + " and below " + maxValue);
		}
		else 
		{
			LOGGER.log(Level.INFO, "Antal leads är ok.");
		}
	}
	
	/* Sparar nya listan med leads till fil. */
	public void saveListToFile(ArrayList<leads> aLeadsList) 
	{
		LOGGER.log(Level.INFO, "Påbörjar sparning av ny lista till fil.");
		
		
		File tmpFileWithLeads = new File(propertiesHandler.tmpLeadsListFilePath + propertiesHandler.tmpLeadsListFile);
		// kanske behövs getAbsolutePath()
		
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
			LOGGER.log(Level.INFO, "Ny lista kunde inte sparas.", e);
		} 
		catch (IOException e) 
		{
			LOGGER.log(Level.INFO, "Ny lista kunde inte sparas.", e);
		}
		finally
		{
			LOGGER.log(Level.INFO, "Ny lista sparad till fil");
		}
	}
}
