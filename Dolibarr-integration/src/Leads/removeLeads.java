package Leads;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Log.logMessageHandler;
import Properties.propertiesHandler;

public class removeLeads {
	
	private static final Logger LOGGER = Logger.getLogger(removeLeads.class.getName());
	private Connection conn = null;
	private String URLDolibarrDB;
	private String dbName;
	private String dbPassword;
	
	public removeLeads()
	{
		URLDolibarrDB = propertiesHandler.URLDolibarrDB;
		dbName = propertiesHandler.dbName;
		dbPassword = propertiesHandler.dbPassword;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			LOGGER.log(Level.SEVERE, logMessageHandler.RLcouldNotLoadDriver, e);
	
		}
		
		LOGGER.log(Level.INFO, logMessageHandler.removeLeadsLoadDriver);
		establishConnection();
		
		
	}
	
	public void establishConnection()
	{
		 
		try 
		{
			conn = DriverManager.getConnection(URLDolibarrDB, dbName, dbPassword);
			LOGGER.log(Level.INFO, "Upprättar databasanslutning."); //TODO Lägg in i logpropfil
		}
		
		catch (SQLException e) 
		{
			LOGGER.log(Level.SEVERE, logMessageHandler.RLdatabaseConnectionFailed, e);
		} 
		
		LOGGER.log(Level.INFO, logMessageHandler.removeLeadsConnectionMade);
	}
	
	public void deleteProspectLeads(){
		
		try 
		{
			ResultSet rs;
			String rowId = null;
			Statement st = conn.createStatement();
			//TODO NullPointerException här då det inte finns några leads att ta bort. Fixa.
			
			rs = st.executeQuery("SELECT rowid FROM llx_societe WHERE client='2'");
			
			while(rs.next())
			{
				rowId = rs.getString(1);
				deleteRowsInContacts(rowId);
				
			}
			
			st.executeUpdate("DELETE FROM llx_societe WHERE client='2'");
			
		} 
		catch (SQLException e) 
		{
			
			LOGGER.log(Level.SEVERE, logMessageHandler.RLcouldNotRemoveLeads, e);

		}
		
		LOGGER.log(Level.INFO, logMessageHandler.leadsDeleted);

		
	}
	
	public void deleteRowsInContacts(String rowId)
	{
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate("DELETE FROM llx_socpeople WHERE fk_soc='"+rowId+"'");
		} catch (SQLException e) {
			
			LOGGER.log(Level.SEVERE, logMessageHandler.RLcouldNotRemoveChildLeads, e);

		}
		

	}
	
	public void closeConnection()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			
			LOGGER.log(Level.SEVERE, logMessageHandler.RLcouldNotCloseConnection, e);

		}
		
		LOGGER.log(Level.INFO, logMessageHandler.removeLeadsConnectionClosed);

	}
}
