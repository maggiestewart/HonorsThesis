

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.util.Random;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * Servlet implementation class RegisterCardServlet
 */
public class RegisterCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterCardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String first = request.getParameter("firstName");
		String last = request.getParameter("lastName");
		String pronouns = request.getParameter("pronouns");
		String birthday = request.getParameter("dob");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String message = "";
        String name = "";
        String expirationDate = "";
        String cardNo = "";
        
        //JDBC driver name and database url
      		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      		
      		//String DB_URL = "jdbc:mysql://18.218.14.37:3306/testDB";
      		String DB_URL = "jdbc:mysql://localhost:3306/project";
      		
      		//DB Credential
      		String USER = "root";
      		String PASS = "";
      		
      		Connection conn = null;
      		Statement stmt = null;
      		
        
        if(first == null || last == null || pronouns == null || birthday == null || email == null || password == null){
        	message = "<p>Registration not complete. Please try again. </p>";
        } else {
        	name = first + " " + last;
        	
        	long min = 59000000000L;
            long max = 60000000000L;
        	Random random = new Random();
            long randomValue = min + Math.abs(random.nextLong()) % (max - min);
            cardNo = "216" + randomValue;
            
            Calendar currentDate = Calendar.getInstance();
            currentDate.add(Calendar.YEAR, 4);
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            expirationDate = formatter.format(currentDate.getTime());
        	
        	//STEP 2: Register JDBC driver
      		try{
      			Class.forName(JDBC_DRIVER);
      			
      			//step 3: open a connection
      			System.out.println("connecting to database...");
      			conn = (Connection)DriverManager.getConnection(DB_URL, USER, PASS);
      			
      			//Step 4: execute query
      			System.out.println("Creating statement...");
      			stmt = (Statement)conn.createStatement();
      			String sql = "INSERT INTO userInfo(name, email, pronouns, birthday, password, cardNumber, expirationDate) " +
      					     "VALUES (\'" + name + "\', \'" + email + "\', \'" + pronouns + "\', \'" + birthday + "\', \'" + 
      					     password + "\', \'" + cardNo + "\', \'" + expirationDate + "\');";
      		
      			System.out.println(sql);
      			stmt.executeUpdate(sql);
      			
      			conn.close();
      			stmt.close();
      			
      		}catch(ClassNotFoundException | SQLException e){
      			e.printStackTrace();
      		}
        	message = "<p>Registration complete! Login <a href=\"" + "userLogin.html\">here</a></p>";
        }
        
        response.setContentType("text/html; charset=UTF-8");
		final PrintWriter out = response.getWriter();
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		String htmlInfo = docType + "<html>"+
    			"<head><title>LibraryBR</title></head>"+
    			"<body>\n" + 
    				"<h2><center>Welcome " + name + "!</h2>" +
    				"<center>" + message + 
    			"</body></html>"	
    			;
    
   
    out.println(htmlInfo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
