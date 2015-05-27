package Leads;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Properties.propertiesHandler;
public class JDBCinsert
{
	private static final Logger LOGGER = Logger.getLogger(JDBCinsert.class.getName());
	private Connection conn = null;
	
	public JDBCinsert()
	{
		LOGGER.log(Level.INFO, "JDBCinsert körs");
	
		try 
		{
			
			Class.forName("com.mysql.jdbc.Driver");
			LOGGER.log(Level.INFO, "Laddar JDBC driver");
		} 
		catch (ClassNotFoundException e) 
		{
			LOGGER.log(Level.SEVERE, "JDBC driver kunde inte laddas", e);
		}
		establishConnection();
		
		
	}
	
	public void establishConnection()
	{
		LOGGER.log(Level.INFO, "JDBCinsert.establichConnection() körs");
		 
		try {
			
			conn = DriverManager.getConnection(propertiesHandler.URLDolibarrDB, propertiesHandler.dbName, propertiesHandler.dbPassword);
			LOGGER.log(Level.INFO, "");
			System.out.println("Skapar en anslutning till dolibarrs databas");
			
		} catch (SQLException e) {
			
			LOGGER.log(Level.SEVERE, "Gick inte att skapa en anslutning till dolibarrs databas", e);
		} 

		
	}
	
	public void insertLead(leads aLead)
	{
		LOGGER.log(Level.INFO, "JDBC.instertLead() körs");
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
				System.out.println("ID =" + rowId);
			}
			
			st.executeUpdate("INSERT INTO llx_socpeople (fk_soc, fk_user_creat, firstname, lastname, phone, email) "
					+ "VALUES('" + rowId +"',"
							+ "'1',"
							+ "'"+ aLead.getFirstName()+"',"
							+ "'"+ aLead.getLastName() +"',"
							+ "'"+ aLead.getTele() +"',"
							+ "'"+ aLead.getEmail() +"')");
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void closeConnection()
	{
		LOGGER.log(Level.INFO, "Stänger connection till dolibarrs databs");
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Något gick fel när anslutningen till dolibarrs databas skulle stängas",e);
		}
	}
	
	
}
