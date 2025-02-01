import java.sql.*;

import com.mysql.jdbc.Connection;

public class Validate {
    public static boolean checkUser(String cardNo, String pass) 
    {
        boolean st =false;
        try {
        	//JDBC driver name and database url
      		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      		
      		//String DB_URL = "jdbc:mysql://18.218.14.37:3306/testDB";
      		String DB_URL = "jdbc:mysql://localhost:3306/project";
      		
      		//DB Credential
      		String USER = "root";
      		String PASS = "";
      		      		
      		// loading drivers for mysql
      		Class.forName(JDBC_DRIVER);
  			
  			// open a connection with the database
  			System.out.println("connecting to database...");
  			Connection conn = (Connection)DriverManager.getConnection(DB_URL, USER, PASS);
  			
  			// prepare query
  			System.out.println("Preparing statement...");
            PreparedStatement ps = conn.prepareStatement("select * from userInfo where cardNumber=? and password=?");
            ps.setString(1, cardNo);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            st = rs.next();
            rs.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return st;                 
    }   
}
