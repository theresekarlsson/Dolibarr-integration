package Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class handleProperties {

	/* H�mtar url (d�r leads h�mtas) fr�n config.properties och returnerar som en str�ng. */
	public String getURLFromPropertiesFile() {
		
		Properties properties = new Properties();
		InputStream input = null;
		String url = null;
		
		try {
			String fileName = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(fileName);
			properties.load(input);
			url = properties.getProperty("url");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return url;
	}
	
	/* H�mtar e-post (till tekniker) fr�n config.properties och returnerar som en str�ng. */
	public String getEmailFromPropertiesFile() {
		
		Properties properties = new Properties();
		InputStream input = null;
		String email = null;
		
		try {
			String fileName = "config.properties";
			input = getClass().getClassLoader().getResourceAsStream(fileName);
			properties.load(input);
			email = properties.getProperty("email");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return email;
	}
}
