

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class HelloWorldRetrieve
 */
public class HelloWorldRetrieve extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldRetrieve() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name, email="", location="", gender="", experience="";
		name = request.getParameter("nameRetrieve");
		
		///////////////////////////////
		// JDBC driver name and database URL
		 String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		 
		 // tomcat 18.218.14.37 & apache 3.15.41.82
		 //String DB_URL = "jdbc:mysql://18.218.14.37:3306/student";
		 String DB_URL = "jdbc:mysql://localhost:3306/testDB";

		 // Database credentials
		 String USER = "root";
		 String PASS = "";

		 Connection conn = null;
		 Statement stmt = null;
		 
		 //STEP 2: Register JDBC driver
		 try {
			 Class.forName(JDBC_DRIVER);
			 
			 //STEP 3: Open a connection
			 System.out.println("Connecting to database...");
			 conn = (Connection)DriverManager.getConnection(DB_URL, USER, PASS);
			 
			 //STEP 4: Execute a query
			 System.out.println("Creating statement...");
			 stmt = (Statement)conn.createStatement();
			 String sql = "SELECT * FROM student WHERE name=\'"+ name + "\';";
			 System.out.println(sql);
			 
			 ResultSet rs = (ResultSet)stmt.executeQuery(sql);
			 
		 	//STEP 5: Extract data from result set
			 while(rs.next()){
				 //Retrieve by column name
				 email = rs.getString("email");
				 location = rs.getString("location");
				 gender = rs.getString("gender");
				 experience = rs.getString("experience");
				 System.out.println(email);
			 }
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		////////////////////////////////////
		response.setContentType("text/html;charset=UTF-8");
		final PrintWriter out = response.getWriter();
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		out.println(docType +
				"<html>\n" +
				"	<head><title>User Information Retrieval</title></head>\n" +
				"	<body>\n" +
				"		<h1 align=\"center\"><img src='http://3.15.41.82/pic/cat.jpg'>\n\n Hello " + name + ", the following is your profile info:</h1>\n" +
				"		<ul>\n" +
				"			<li><b>Your name</b>: " + name + "</li>\n" +
				"			<li><b>Your email</b>: " + email + "</li>\n" +
				"			<li><b>Your location</b>: " + location + "</li>\n" +
				"			<li><b>Your gender</b>: " + gender + "</li>\n" +
				"			<li><b>Your experience</b>: " + experience + "</li>\n" +
				"		</ul>\n" +
				"	</body>\n" +
				"</html>"
		);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
