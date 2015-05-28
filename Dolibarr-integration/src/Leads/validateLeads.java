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

import Properties.propertiesHandler;

public class validateLeads {

	private static final Logger LOGGER = Logger.getLogger(validateLeads.class.getName());
	
	private boolean value;
	
	ArrayList<String> failReport = new ArrayList<String>();
	
	public void checkList(ArrayList<leads> aLeadsList)
	{
		System.out.println("Inne i checkList.");
		checkIfEmpty(aLeadsList);
		checkForDuplicates(aLeadsList);
		checkValues(aLeadsList);
		compareToLastWeek(aLeadsList);
		countLeads(aLeadsList);
		
		if(failReport.isEmpty())
		{
			
			LOGGER.log(Level.INFO, "Inga fel i listan hittades.");
			saveListToFile(aLeadsList);
			

		}
		else
		{
			for(int i = 0; i<failReport.size(); i++)
			{
			LOGGER.log(Level.SEVERE, "Fel i listan: " + failReport.get(i));				
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
		
			if(aLeadsList.get(i).getName() == "" || aLeadsList.get(i).getName() == null)
				failReport.add("Item number " + i + " has no name");
				
			if(aLeadsList.get(i).getAddress() == "" || aLeadsList.get(i).getAddress() == null)
				failReport.add("Item number " + i + " has no address");
			
			if(aLeadsList.get(i).getCity() == "" || aLeadsList.get(i).getCity() == null)
				failReport.add("Item number " + i + " has no City");
		
			if(aLeadsList.get(i).getContact() == "" || aLeadsList.get(i).getContact() == null)
				failReport.add("Item number " + i + " has no contact");
			
			if(aLeadsList.get(i).getTele() == "" || aLeadsList.get(i).getTele() == null)
				failReport.add("Item number " + i + " has no tele");
			
			if(aLeadsList.get(i).getZip() == "" || aLeadsList.get(i).getZip() == null)
				failReport.add("Item number " + i + " has no zip");
			
			if(aLeadsList.get(i).getEmail() == "" || aLeadsList.get(i).getEmail() == null)
				failReport.add("Item number " + i + " has no Email");

			if(aLeadsList.get(i).getCurrent_provider() == "" || aLeadsList.get(i).getCurrent_provider() == null)
				failReport.add("Item number " + i + " has no Current provider");	
			
			if(aLeadsList.get(i).getSize() == "" || aLeadsList.get(i).getSize() == null)
				failReport.add("Item number " + i + " has no Size");	
		}
	}
	
	public void checkForDuplicates(ArrayList<leads> aLeadsList)
	{
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
						failReport.add("Item number: " + y + " has a duplicate" );	
					} 
					duplicates = true;
				}
			}
			
			duplicates = false;
		}

	}
	
	public void checkValues(ArrayList<leads> aLeadsList)
	{
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
				failReport.add("Item number: " + i + " has unvalid zipcode(can only contain numbers)");
			}
			
			if(aLeadsList.get(i).getCity().matches(regex))
			{
				failReport.add("Item number: " + i + " City cannot contain numbers");
			}
			
			if(aLeadsList.get(i).getContact().matches(regex))
			{
				failReport.add("Item number: " + i + " contact name cannot contain numbers");
			}
			
			if(!tmpTele.matches(regex))
			{
				failReport.add("Item number: " + i + " has unvalid phonenumber (can only contain numbers)");
			}
			
			String email = aLeadsList.get(i).getEmail();
	        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
	        Matcher mat = pattern.matcher(email);

	        if(!mat.matches()){
	            failReport.add("Item number: " + i + " Has invalid email");
	        }	
		}
	}

	
	/* Jämför gamla listan med den nya TODO Hur hitta gamla listan? */
	public void compareToLastWeek(ArrayList<leads> aLeadsList)
	{
		LOGGER.log(Level.INFO, "Påbörjar jämförelse med förra veckans lista");
		
		File tmpFileWithLeads = new File("tmpFileWithLeads.txt");
		
		try
		{
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
				LOGGER.log(Level.INFO, "Leadsen i den nya listan finns i den gamla listan.");
				failReport.add("The leads in the new list already exists in the old list");	
			}
			else
			{
				LOGGER.log(Level.INFO, "Gammal och ny lista jämförda. Den nya är ok.");
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
	
	/* Sparar nya listan med leads till fil. TODO: Hur ska den sparas? Med datum? */
	public void saveListToFile(ArrayList<leads> aLeadsList) 
	{
		LOGGER.log(Level.INFO, "Påbörjar spar av ny lista till fil.");
		
		File tmpFileWithLeads = new File("tmpFileWithLeads.txt");
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
	
	
	/* Räknar antalet leads i nya listan. Vid färre än 50 eller fler än 5000 görs en felrapport. */
	public void countLeads(ArrayList<leads> aLeadsList)
	{
		int count = 0;
		for (int i = 0; i < aLeadsList.size(); i++) 
		{
			count++;
		}
		
		LOGGER.log(Level.INFO, "Antal leads i listan: " + count);

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
}
