package Leads;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validateLeads {

	private static final Logger LOGGER = Logger.getLogger(validateLeads.class.getName());
	
	private boolean value;
	
	ArrayList<String> failReport = new ArrayList<String>();
	
	public void checkList(ArrayList<leads> aLeadsList)
	{
		checkIfEmpty(aLeadsList);
		checkForDuplicates(aLeadsList);
		checkValues(aLeadsList);
		
		if(failReport.isEmpty())
		{
			
			System.out.println("Inga fel");
			//LOGGER.log(Level.INFO, "Inga fel");
			
		}
		else
		{
			for(int i = 0; i<failReport.size(); i++)
			{
				
			System.out.println(failReport.get(i));
			//LOGGER.log...
				
			}
			
		}
		
		
		
	}
	
	public void checkIfEmpty(ArrayList<leads> aLeadsList)
	{
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
		String regex = "[0-9]+";
		
		String tmpTele;
		for(int i = 0; i < aLeadsList.size(); i++)
		{
			tmpTele = aLeadsList.get(i).getTele();
			tmpTele = tmpTele.replace("-", "");
			
			
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
	
}
