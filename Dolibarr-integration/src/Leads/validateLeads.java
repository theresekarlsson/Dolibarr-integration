package Leads;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

public class validateLeads {

	private static final Logger LOGGER = Logger.getLogger(validateLeads.class.getName());
	
	private boolean value;
	
	ArrayList<String> failReport = new ArrayList<String>();
	
	public void checkList(ArrayList<leads> aLeadsList)
	{
		checkIfEmpty(aLeadsList);
		checkForDuplicates(aLeadsList);
		checkValues(aLeadsList);
		
		// TODO j�mf�r f�rra veckans lista
		compareToLastWeek(aLeadsList);
		
		
		if(failReport.isEmpty())
		{
			
			System.out.println("Inga fel");
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
		LOGGER.log(Level.INFO, "Kollar om listan �r tom");
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
		LOGGER.log(Level.INFO, "Kollar om lista inneh�llet dubbletter");
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
		LOGGER.log(Level.INFO, "Kollar om listan inneh�ller korrupta v�rden");
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
	
	/* TODO Sparar fil men nya leadslistan, j�mf�r med fil med f�rra listan. 
	 * Om de �r lika loggas SEVERE */
	public void compareToLastWeek(ArrayList<leads> aLeadsList)
	{
		LOGGER.log(Level.INFO, "J�mf�r med f�rra veckans lista");
		try
		{
			File newLeadsFile = new File("fileWithNewLeads.txt");
			FileWriter fileWriter = new FileWriter(newLeadsFile);
	    
			for(int i = 0; i < aLeadsList.size(); i++)
			{
				fileWriter.append(aLeadsList.get(i).getName());
				fileWriter.append(", ");
				fileWriter.append(aLeadsList.get(i).getAddress());
				fileWriter.append(", ");
			}
			
			fileWriter.close();
		
			File oldLeadsFile = new File("src/tmpLeads.txt");
			boolean compareFiles = FileUtils.contentEquals(newLeadsFile, oldLeadsFile);
			
			if (compareFiles)
			{
				LOGGER.log(Level.SEVERE, "Leadslistan �r likadan som f�rra veckans lista.");
			}
			
			else
			{
				// TODO G�r s� nya filen blir aktuell lista (fileWithNewLeads.txt = tmpLeads.txt)
			}
		} 
		catch(FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Sparar lista med namn och adress till leads till fil. */
	public void saveListToFile(ArrayList<leads> aLeadsList) 
	{
		LOGGER.log(Level.INFO, "Sparar lista till fil.");
	
		try 
		{	
			File tmpFile = new File("tmpLeads.txt");
		    FileWriter fileWriter = new FileWriter(tmpFile);
		    
		    for(int i = 0; i < aLeadsList.size(); i++)
		    {
		    	fileWriter.append(aLeadsList.get(i).getName());
		    	fileWriter.append(", ");
		    	fileWriter.append(aLeadsList.get(i).getAddress());
		    	fileWriter.append(", ");
		    }
		    fileWriter.close();
		} 
		catch(FileNotFoundException e) {
		    e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
