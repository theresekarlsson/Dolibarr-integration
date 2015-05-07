package Leads;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class getLeads {

	
	public void getResponse(String URI, String oauth2Key)
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
			
			
		} 
		catch (ClientProtocolException e) 
		{
			
			e.printStackTrace();
			
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
		
	}
	
	
	
}
