package Leads;

import java.util.logging.Logger;


public class leads {
	
	private static final Logger LOGGER = Logger.getLogger(leads.class.getName());

	private int lead;
	private String zip;
	private String tele;
	private String name;
	private String address;
	private String city;
	private String contact;
	private String email;
	private String current_provider;
	private String size;
	
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getCurrent_provider() {
		return current_provider;
	}


	public void setCurrent_provider(String current_provider) {
		this.current_provider = current_provider;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}
	
	public String getFirstName()
	{
		String[] names = contact.split(" ");
		return names[0];
	}
	
	public String getLastName()
	{
		String[] names = contact.split(" ");
		return names[1];
	}
	
}
