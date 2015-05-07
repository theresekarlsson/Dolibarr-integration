package Leads;

import java.util.ArrayList;

public class validateLeads {

	private boolean duplicates;
	private boolean value;
	
	ArrayList<String> failReport = new ArrayList<String>();
	
	public void checkList(ArrayList<leads> aLeadsList)
	{
		checkIfEmpty(aLeadsList);
		//checkForDuplicates(aLeadsList);
		//checkValues(aLeadsList);
		
		if(failReport.isEmpty())
		{
			
			System.out.println("Inga fel");
			
		}
		else
		{
			for(int i = 0; i<failReport.size(); i++)
			{
				
			System.out.println(failReport.get(i));
				
			}
			
		}
		
		
		
	}
	
	public void checkIfEmpty(ArrayList<leads> aLeadsList)
	{
		
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
				
			
		}
		
		
		
		
		
	}
	
	public void checkForDuplicates(ArrayList<leads> aLeadsList)
	{
		
		
	}
	
	public void checkValues(ArrayList<leads> aLeadsList)
	{
		
		
		
	}
	
	//tomt
	//dubbletter
	//felaktig attribut
	
	
	
	
	
}
