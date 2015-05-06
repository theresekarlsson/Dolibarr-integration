package Properties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class handleProperties {

	/* Hämtar url (där leads hämtas) från config.properties och returnerar som en sträng. */
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
	
	/* Hämtar e-post (till tekniker) från config.properties och returnerar som en sträng. */
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
