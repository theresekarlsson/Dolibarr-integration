package Leads;


public class leads {

	private int lead;
	private String zip;
	private String tele;
	private String name;
	private String address;
	private String city;
	private String contact;
	
	public String getContact() 
	{
		return contact;
	}
	
	
	public void setContact(String contact) 
	{
		this.contact = contact;
	}

	public String getCity() 
	{
		return city;
	}

	
	public void setCity(String city)
	{
		this.city = city;
	}

	public String getAddress()
	{
		return address;
	}

	
	public void setAddress(String adress)
	{
		this.address = adress;
	}

	public String getName() {
		
		return name;
	}


	public void setName(String name) 
	{
		this.name = name;
	}

	public String getTele()
	{
		return tele;
	}

	
	public void setTele(String tele) 
	{
		this.tele = tele;
	}

	public String getZip()
	{
		return zip;
	}

	
	public void setZip(String zip)
	{
		this.zip = zip;
	}

	public int getLead()
	{
		return lead;
	}

	
	public void setLead(int leadID)
	{
		this.lead = leadID;
	}
	
}
