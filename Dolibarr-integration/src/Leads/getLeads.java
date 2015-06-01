package Leads;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import Log.logMessageHandler;
import Properties.propertiesHandler;

public class getLeads {

	private static final Logger LOGGER = Logger.getLogger(getLeads.class.getName());
	private validateLeads vl;
	
	public String getResponse()
	{
		LOGGER.log(Level.INFO, logMessageHandler.getLeadsStart);
		@SuppressWarnings({ "deprecation", "resource" })
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(propertiesHandler.URI);
		HttpResponse response = null;
		InputStream content= null;
		String result = "";
		
		try 
		{
			
			httpGet.addHeader("Authorization", propertiesHandler.oauth2Key);
			response = httpClient.execute(httpGet);
			LOGGER.log(Level.INFO, logMessageHandler.httprequest);
			
		} 
		catch (ClientProtocolException e) 
		{
			
			LOGGER.log(Level.SEVERE, logMessageHandler.httpRequestFailed, e);
			
		}
		catch (IOException e) 
		{
			LOGGER.log(Level.SEVERE, logMessageHandler.connectionFailed, e);
		} 
		
		HttpEntity httpEntity = response.getEntity();
		
		try
		{
			content = httpEntity.getContent();
			LOGGER.log(Level.INFO, logMessageHandler.tmpXMLfile);
		} 
		catch (UnsupportedOperationException e)
		{
			LOGGER.log(Level.SEVERE, logMessageHandler.couldNotStoreXML, e);
		}
		catch (IOException e)
		{
			LOGGER.log(Level.SEVERE, logMessageHandler.somethingWentWrongXML , e);
		}
		
	
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(content,Charset.forName("UTF-8")));
			result =br.readLine().toString();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		LOGGER.log(Level.INFO, logMessageHandler.gettingXMLFileDone);
		return result;
	}
	
	public ArrayList<leads> createLeadArray(String result) {
		
		ArrayList<leads> leadsList = new ArrayList<leads>();
		
		String tmpString = "";

		String init = "<leads>";
		String removeFromTag = " xmlns=\"http://ws.wso2.org/dataservice\"";
		
		result = result.replaceAll(removeFromTag,"");
		
		LOGGER.log(Level.INFO, logMessageHandler.strippXMLFile);
		
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
				LOGGER.log(Level.SEVERE, logMessageHandler.couldNotUnmarchall, e);
			}
				tmpString = "";
			}		
			
		}
		LOGGER.log(Level.INFO, logMessageHandler.getLeadsFinished);
		vl = new validateLeads();
		
		//vl.checkList(leadsList);
		//return leadsList;	
		
		ArrayList <leads> tmpLeadsList = new ArrayList<leads>();	
		tmpLeadsList = vl.checkList(leadsList);
		return tmpLeadsList;
		}
	}