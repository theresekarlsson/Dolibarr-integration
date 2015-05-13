package Leads;
import java.sql.*;
public class JDBCinsert
{

	private final String URL;
	private final String DBNAME;
	private final String DBPASSWORD;
	private Connection conn = null;
	
	public JDBCinsert(String aURL, String adbName, String adbPassword)
	{
		URL = aURL;
		DBNAME = adbName;
		DBPASSWORD = adbPassword;
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
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
