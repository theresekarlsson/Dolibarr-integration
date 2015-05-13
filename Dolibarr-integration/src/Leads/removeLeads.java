package Leads;
import java.sql.*;
import java.util.logging.Logger;

public class removeLeads {
	
	private static final Logger LOGGER = Logger.getLogger(removeLeads.class.getName());
	private Connection conn = null;
	private String URL = "jdbc:mysql://localhost/dolibarr";
	private String DBNAME = "dolibarrmysql";
	private String DBPASSWORD = "admin";
	
	public removeLeads()
	{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		establishConnection();
				
	}
	
	public void establishConnection()
	{
		 
		try {
					
			conn = DriverManager.getConnection(URL, DBNAME, DBPASSWORD);
			System.out.println("conn made");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 

		
	}
	
	public void deleteProspectLeads(){
		
		try {
			ResultSet rs;
			String rowId = null;
			Statement st = conn.createStatement();
			
			rs = st.executeQuery("SELECT rowid FROM llx_societe WHERE client='2'");
			while(rs.next())
			{
				rowId = rs.getString(1);
				System.out.println(rowId);
				
				
			}
			st.executeUpdate("DELETE FROM llx_socpeople WHERE "+rowId+"=fk_soc");
			st.executeUpdate("DELETE FROM llx_societe WHERE client='2'");
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
}
