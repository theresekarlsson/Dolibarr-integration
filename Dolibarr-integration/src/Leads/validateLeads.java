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
	private int countInvalidLeads = 0;	// Antal felaktiga leads
	private int countCorrectLeads;		// Antal validerade leads, dvs. efter att ev. felaktiga leads tagits bort.
	
	ArrayList<String> failReport = new ArrayList<String>();			// Array f�r felrapporter som triggar mailfunktion
	ArrayList<String> invalidLeads = new ArrayList<String>();		// Array f�r felaktiga leads som �tg�rdas
	ArrayList<leads> aLeadsList = new ArrayList<leads>();			// Array med leads
	
	/* Tar emot lista med leads och k�r den i diverse valideringsfunktioner. 
	 * Returnerar d�refter listan. */
	public ArrayList<leads> checkList(ArrayList<leads> aLeadsList)
	{
		this.aLeadsList = aLeadsList;
		checkIfEmpty();
		checkForDuplicates();
		checkValues();
		compareToLastWeek();
		countLeads();
		
		// Om invalidLeads inte �r tom, r�knas antalet element i arrayen, samt h�mtar felmeddelande f�r loggen
		if (!invalidLeads.isEmpty()) 
		{
			for (int i = 0; i<invalidLeads.size(); i++) 
			{
				countInvalidLeads++;
				LOGGER.log(Level.WARNING, logMessageHandler.validateLeadsCorruptedLeads + invalidLeads.get(i));
			}
			
			LOGGER.log(Level.WARNING,logMessageHandler.validateLeadsNumberOfCorruptedLeads + countInvalidLeads);
		}
		
		// Om failReport �r tom, spara listan till fil.
		if(failReport.isEmpty())
		{
			saveListToFile(aLeadsList);
		}
		
		// Om failReport inte �r tom loggas ett meddelande av allvarlig grad (SEVERE).
		else
		{
			for(int i = 0; i<failReport.size(); i++)
			{
				LOGGER.log(Level.SEVERE, logMessageHandler.validateLeadsTriggedMail + failReport.get(i));
			}	
		}
		
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsLeadsValidated);
		
		// Returnerar listan med leads.
		return aLeadsList;
	}
	
	/* Kontrollerar om listan, eller n�got av attributen f�r varje lead, �r tom. */
	public void checkIfEmpty()
	{
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCheckIfEmpty);
		
		// Om listan har f�rre element �n 1, l�gg till felrapport i failReport.
		if(aLeadsList.size() < 1)	
		{
			failReport.add("Whole list is empty");
		}
		
		// G�r igenom alla element i listan.
		for(int i=0; i<aLeadsList.size(); i++)
		{

			// Om den specifika leaden saknar namn, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getName() == "" || aLeadsList.get(i).getName() == null) {
				invalidLeads.add("Item number " + i + " has no name");
				aLeadsList.remove(i);
			}
			
			// Om den specifika leaden saknar adress, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getAddress() == "" || aLeadsList.get(i).getAddress() == null) {
				invalidLeads.add("Item number " + i + " has no address");
				aLeadsList.remove(i);
			}
			
			// Om den specifika leaden saknar stad, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getCity() == "" || aLeadsList.get(i).getCity() == null) {
				invalidLeads.add("Item number " + i + " has no City");
				aLeadsList.remove(i);
			}
			
			// Om den specifika leaden saknar kontaktperson, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getContact() == "" || aLeadsList.get(i).getContact() == null) {
				invalidLeads.add("Item number " + i + " has no contact");
				aLeadsList.remove(i);
			}
			
			// Om den specifika leaden saknar telefonnummer, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getTele() == "" || aLeadsList.get(i).getTele() == null) {
				invalidLeads.add("Item number " + i + " has no tele");
				aLeadsList.remove(i);
			}
			
			// Om den specifika leaden saknar postkod, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getZip() == "" || aLeadsList.get(i).getZip() == null) {
				invalidLeads.add("Item number " + i + " has no zip");
				aLeadsList.remove(i);
			}
			
			// Om den specifika leaden saknar e-post, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getEmail() == "" || aLeadsList.get(i).getEmail() == null) {
				invalidLeads.add("Item number " + i + " has no Email");
				aLeadsList.remove(i);
			}

			// Om den specifika leaden saknar current_provider, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getCurrent_provider() == "" || aLeadsList.get(i).getCurrent_provider() == null) {
				invalidLeads.add("Item number " + i + " has no Current provider");	
				aLeadsList.remove(i);
			}
			
			// Om den specifika leaden saknar storlek, l�gg till felrapport i invalidLeads. Ta bort leaden fr�n aLeadsList.
			if(aLeadsList.get(i).getSize() == "" || aLeadsList.get(i).getSize() == null) {
				invalidLeads.add("Item number " + i + " has no Size");
				aLeadsList.remove(i);
			}
		}
	}
	
	/* Kontrollerar om listan inneh�ller dubletter.*/
	public void checkForDuplicates()
	{
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCheckForDuplicates);
		leads tmpLead = new leads();
		boolean duplicates = false;
		
		// G�r igenom hela listan
		for(int i = 0; i < aLeadsList.size(); i++)
		{
			tmpLead = aLeadsList.get(i);
			
			for(int y = 0; y < aLeadsList.size(); y++)
			{
				if(tmpLead == aLeadsList.get(y))
				{
					// Om dublett uppt�cks, ta bort leaden fr�n aLeadsList
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
	
	/* Kontrollerar v�rden i leadsen */
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

	
	/* J�mf�r gamla listan med den nya */
	public void compareToLastWeek()
	{
		// Namn och plats till filen med den gamla listan.
		File tmpFileWithLeads = new File(propertiesHandler.tmpLeadsListFilePath + propertiesHandler.tmpLeadsListFile);
		
		try
		{
			if (tmpFileWithLeads.exists() && !tmpFileWithLeads.isDirectory())
			{
				LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCompareToLastWeek);
				
				// H�mtar fil, l�ser in och l�gger objekten (leadsen) i en array
				FileInputStream fileInputStream = new FileInputStream(tmpFileWithLeads);			
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
		        ArrayList<leads> oldLeads = (ArrayList<leads>) objectInputStream.readObject();
				objectInputStream.close();
				
				int match = 0;
				
				// G�r igenom nya listan
				for (int i = 0; i < aLeadsList.size(); i++) 
				{
					// G�r igenom nya listan
					for (int j = 0; j < oldLeads.size(); j++)
					{
						// Om likheter hittas, plussa p� match
						if (oldLeads.get(j).getName().equals(aLeadsList.get(i).getName()))
						{
							match++;
						}
					}
				}
			
				// Om match �verensst�mmer med storleken p� nya listan l�ggs en felrapport till i failReport
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
	
	/* R�knar antalet leads i nya listan. Vid f�rre �n min.v�rde eller fler �n maxv�rde g�rs en felrapport. */
	public void countLeads()
	{
		countCorrectLeads = 0;
		
		// G�r igenom listan
		for (int i = 0; i < aLeadsList.size(); i++) 
		{
			// R�knar antal element
			countCorrectLeads++;
		}
		
		LOGGER.log(Level.INFO, logMessageHandler.validateLeadsCorrectLeads + countCorrectLeads);

		int minValue = Integer.parseInt(propertiesHandler.minValueLeads);	// H�mtar min.v�rde
		int maxValue = Integer.parseInt(propertiesHandler.maxValueLeads);	// H�mtar max.v�rde
		
		// Om antalet element �r l�gre �n min.v�rde, l�ggs felrapport till i failReport 
		if (countCorrectLeads < minValue)
		{
			failReport.add(countCorrectLeads + " is not an approved number of leads. The value should be above " + minValue);
		}
		
		// Om antalet element �r h�gre �n max.v�rde, l�ggs felrapport till i failReport
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
		
		//Skapar fil med filv�g och filnamn
		File tmpFileWithLeads = new File(propertiesHandler.tmpLeadsListFilePath + propertiesHandler.tmpLeadsListFile);
		
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
		
		try 
		{
			// Skriver listan med leads till fil
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
