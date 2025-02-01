

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
 * Servlet implementation class LoginCardServlet
 */
public class LoginCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cardNo = request.getParameter("cardNum");
		String name = "", email = "", expirationDate = "";
		
		//JDBC driver name and database url
  		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
  		
  		//String DB_URL = "jdbc:mysql://18.218.14.37:3306/testDB";
  		String DB_URL = "jdbc:mysql://localhost:3306/project";
  		
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
  			String sql = "SELECT name, email, expirationDate from userInfo where cardNumber=\'" + cardNo + "\';";
  			
  			ResultSet rs = (ResultSet)stmt.executeQuery(sql);
			 
 		 	//STEP 5: Extract data from result set
 			 while(rs.next()){
 				 //Retrieve by column name
 				 name = rs.getString("name");
 				 email = rs.getString("email");
 				 expirationDate = rs.getString("expirationDate");
 			 }
 			 conn.close();
 			 stmt.close();
  			
  		}catch(ClassNotFoundException | SQLException e){
  			e.printStackTrace();
  		}
  		
  		response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
			String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
			String htmlInfo = docType + "<html>"+
	    			"<head><title>LibraryBR</title></head>"+
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" + 
	    			"<script>function filterProfile(category) {const pr = document.querySelectorAll('.profile');pr.forEach(p => {const cat = p.getAttribute('writing');if (category === 'profile') {p.style.display = 'block';} else if (category === 'loans'){p.style.display='block';} else if (category === 'reservations'){p.style.display='block';} else if (category = 'readingList'){p.style.display='block';}else {p.style.display = 'none';}});}</script>" + 
	    			"<body>\n" + 
	    				"<div class=\"navbar\"><a href=\"index.html\" id=\"logo\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\"></a><a href=\"\" id=\"digital\">Digital Library</a><a href=\"services.html\" id=\"services\">Services</a><a href=\"events.html\" id=\"navEvents\">Programs</a><a href=\"catalog.html\" id=\"navCatalog\">Catalog</a><a href=\"userLogin.html\" id=\"navAccount\"><img src=\"Images/account.png\" style=\"height: 35px\"></a></div>" + 
	    				"<h2><center>Welcome " + name + "!</h2>" +
	    				
	    				"<div id=\"writing\" style=\"text-align:center; width: 60%; margin: auto;\"><h4 style=\"text-align:left\">" + email + "</h4><br><h4 style=\"text-align:left\">" + cardNo + "</h4><br>"+ 
	    				"<p style=\"text-align:left\">This account will expire on " + expirationDate + ". To renew your card, please visit your nearest library branch.</p></div>" + 
	    				
	    				"<div class=\"footer\"> <a href=\"index.html\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\" alt=\"LibraryBR\"></a><div class=\"intro\"><div class=\"grid-item\" style=\"color: white\"><h4>Library Resources</h4><a href=\"userLogin.html\" id=\"account\" style=\"color: white\">My Account</a><a href=\"catalog.html\" id=\"catalog\" style=\"color: white\">Library Catalog</a><a href=\"services.html\" id=\"card\" style=\"color: white\">Library Card</a><a href=\"\"  style=\"color: white\">More</a> <!-- Add more books to the collection --></div><div class=\"grid-item\" style=\"color: white\"><h4>Contact Us</h4><div class=\"socials\" style=\"display: flex; flex-direction: row;\"><a><img src=\"Images/facebook.png\" style=\"height: 25px\" alt=\"Facebook\"></a><a><img src=\"Images/instagram.png\" style=\"height: 25px\" alt=\"Instagram\"></a><a><img src=\"Images/twitter.png\" style=\"height: 25px\" alt=\"Twitter\"></a><a><img src=\"Images/youtube.png\" style=\"height: 25px\" alt=\"Youtube\"></a></div></div></div></div>" +
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
