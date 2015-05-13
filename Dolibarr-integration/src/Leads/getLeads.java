package Leads;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit.Parser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;

import Main.mainClass;




public class getLeads {

	private static final Logger LOGGER = Logger.getLogger(getLeads.class.getName());
	
	public String getResponse(String URI, String oauth2Key)
	{
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URI);
		HttpResponse response = null;
		InputStream content= null;
		String result = "";
		
		try 
		{
			
			httpGet.addHeader("Authorization", oauth2Key);
			response = httpClient.execute(httpGet);
			LOGGER.log(Level.INFO, "Http-request genomförd.");
			
		} 
		catch (ClientProtocolException e) 
		{
			
			LOGGER.log(Level.SEVERE, "Http-request misslyckad.", e);
			
		}
		catch (IOException e) 
		{
			
			e.printStackTrace();
		} 
		HttpEntity httpEntity = response.getEntity();
		
		try
		{
			content = httpEntity.getContent();
		} 
		catch (UnsupportedOperationException e)
		{
			
			e.printStackTrace();
		}
		catch (IOException e)
		{
			
			e.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(content));
		
		try 
		{
			
			result =br.readLine().toString();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result);
		
		LOGGER.log(Level.INFO, "Hämtning av leads klar.");
		return result;
	}
	
	public ArrayList<leads> createLeadArray(String result){
	
		ArrayList<leads> leadsList = new ArrayList<leads>();
		
		String tmpString = "";

		
		String init = "<leads>";
		String removeFromTag = " xmlns=\"http://ws.wso2.org/dataservice\"";
		
		result = result.replaceAll(removeFromTag,"");
		
		System.out.println(result);
		
		for(char c: result.toCharArray())
		{
			tmpString = tmpString + Character.toString(c);
		
			if(tmpString.toString().equals(init.toString()))
			{
				tmpString = "";
			}
			
			if(tmpString.toString().contains("</lead>"))
			{
			
				
			try {
				   
				   JAXBContext jc = JAXBContext.newInstance(leads.class);
				   Unmarshaller unmarshaller = jc.createUnmarshaller();
				   StreamSource streamSource = new StreamSource(new StringReader(tmpString.toString()));
				   JAXBElement<leads> je = unmarshaller.unmarshal(streamSource,
				     leads.class);
				   
				   leads aLead = (leads)je.getValue();
				   
				   leadsList.add(aLead);
				   
				   
				  } 
					catch (JAXBException e) 
					{
						   e.printStackTrace();
					}
						tmpString = "";
				 }
				
			
		}
		for (int i = 0; i < leadsList.size(); i++)
		{
			System.out.println(leadsList.get(i).getName());
			
			
				
		}
		
		validateLeads vl = new validateLeads();
		vl.checkList(leadsList);
		return leadsList;
			
		}
		
	}
	
	
	

