

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

/**
 * Servlet implementation class HelloWorldEnhanced2
 */
public class HelloWorldEnhanced2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldEnhanced2() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String location = request.getParameter("location");
		String gender = request.getParameter("gender");
		String experience = request.getParameter("experience");
		
		//JDBC driver name and database url
		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		
		//String DB_URL = "jdbc:mysql://18.218.14.37:3306/testDB";
		String DB_URL = "jdbc:mysql://localhost:3306/testDB";
		
		//DB Credential
		String USER = "root";
		String PASS = "";
		
		Connection conn = null;
		Statement stmt = null;
		
		//STEP 2: Register JDBC driver
		try{
			Class.forName(JDBC_DRIVER);
			
			//step 3: open a connection
			System.out.println("connecting to database...");
			conn = (Connection)DriverManager.getConnection(DB_URL, USER, PASS);
			
			//Step 4: execute query
			System.out.println("Creating statement...");
			stmt = (Statement)conn.createStatement();
			String sql = "INSERT INTO student(name, email, location, gender, experience) " +
					     "VALUES (\'" + name + "\', \'" + email + "\', \'" + location + "\', \'" + gender + "\', \'" + experience + "\');";
		
			//sql = "SELECT * from student";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			
		}catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
		}
		
		final PrintWriter out = response.getWriter();
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		out.println(docType + 
				"<html>\n" 
				+ "<head><title>User registration</title></head>\n" + 
				"<body>\n" + 
				"<h1 align=\"center\"><img src='http://3.15.41.82/pic/cat.jpg'> <br>Welcome " + name + "</h1>" +
				"<ul>\n" + 
				"<li><b>Your name</b>: " + name + "\n" + 
				"<li><b>Your email</b>: " + email + "\n" + 
				"<li><b>Your location</b>: " + location + "\n" + 
				"<li><b>Your gender</b>: " + gender + "\n" + 
				"<li><b>Your experience</b>: " + experience + "\n" + 
				"</ul>\n" + "</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
