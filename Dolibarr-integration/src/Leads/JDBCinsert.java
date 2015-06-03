package Leads;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Log.logMessageHandler;
import Properties.propertiesHandler;
public class JDBCinsert
{
	private static final Logger LOGGER = Logger.getLogger(JDBCinsert.class.getName());
	private Connection conn = null;
	
	public JDBCinsert()
	{
		LOGGER.log(Level.INFO, logMessageHandler.JDBCinsertStart);
	
		try 
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			
		} 
		catch (ClassNotFoundException e) 
		{
			LOGGER.log(Level.SEVERE, logMessageHandler.couldNotLoadDriver, e);
		}
		LOGGER.log(Level.INFO, logMessageHandler.loadJDBCdriver);
		establishConnection();
		
		
	}
	
	public void establishConnection()
	{
		
		 
		try {
			
			conn = DriverManager.getConnection(propertiesHandler.URLDolibarrDB, propertiesHandler.dbName, propertiesHandler.dbPassword);
			
		} catch (SQLException e) {
			
			LOGGER.log(Level.SEVERE, logMessageHandler.databaseConnectionFailed, e);
		} 
		
		LOGGER.log(Level.INFO, logMessageHandler.JDBCconnectionMade);
		
	}
	
	public void insertLead(leads aLead)
	{
		
		try {
			ResultSet rs;
			String rowId = null;
			Statement st = conn.createStatement();
			st.executeUpdate("INSERT INTO llx_societe (nom, zip, address, town, client) "
					+ "VALUES('"+ aLead.getName() + "', "
					+ "'"+ aLead.getZip() +"',"
					+ "'"+ aLead.getAddress() +"',"
					+ "'"+ aLead.getCity() +"',"
					+ "'2')" );
			rs = st.executeQuery("SELECT rowid FROM llx_societe WHERE nom='"+ aLead.getName() +"'");
			
			while(rs.next())
			{
				rowId = rs.getString(1);
			}
			
			st.executeUpdate("INSERT INTO llx_socpeople (fk_soc, fk_user_creat, firstname, lastname, phone, email) "
					+ "VALUES('" + rowId +"',"
							+ "'1',"
							+ "'"+ aLead.getFirstName()+"',"
							+ "'"+ aLead.getLastName() +"',"
							+ "'"+ aLead.getTele() +"',"
							+ "'"+ aLead.getEmail() +"')");
			
			
		} catch (SQLException e) 
		{
			LOGGER.log(Level.SEVERE, logMessageHandler.SQLExecuteFailed, e);
		}
		
		
	}
	
	
	public void closeConnection()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, logMessageHandler.couldNotCloseConnection,e);
		}
		LOGGER.log(Level.INFO, logMessageHandler.leadsInserted);
		LOGGER.log(Level.INFO, logMessageHandler.JDBCconnectionClosed);

	}
	
	
}
