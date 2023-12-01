

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class AddBooks
 */
@WebServlet("/AddBooks")
public class AddBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBooks() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String num = request.getParameter("iNum");
		String genre = request.getParameter("genre");
		Date today = new Date();
		String message;
        
        //JDBC driver name and database url
      		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      		
      		//String DB_URL = "jdbc:mysql://18.218.14.37:3306/testDB";
      		String DB_URL = "jdbc:mysql://localhost:3306/project";
      		
      		//DB Credential
      		String USER = "root";
      		String PASS = "";
      		
      		Connection conn = null;
      		Statement stmt = null;
      		
        
        if(title == null || author == null || num == null || genre == null){
        	message = "<p>Please fill in all information and try again. </p>";
        } else {
        	        	
        	//STEP 2: Register JDBC driver
      		try{
      			Class.forName(JDBC_DRIVER);
      			
      			//step 3: open a connection
      			System.out.println("connecting to database...");
      			conn = (Connection)DriverManager.getConnection(DB_URL, USER, PASS);
      			
      			//Step 4: execute query
      			System.out.println("Creating statement...");
      			stmt = (Statement)conn.createStatement();
      			String sql = "INSERT INTO requestedBooks(title, author, ISBN, genre, dateRequested) " +
      					     "VALUES (\'" + title + "\', \'" + author + "\', \'" + num + "\', \'" + genre + "\', \'" + today + "\');";
      		
      			System.out.println(sql);
      			stmt.executeUpdate(sql);
      			
      			conn.close();
      			stmt.close();
      			
      		}catch(ClassNotFoundException | SQLException e){
      			e.printStackTrace();
      		}
      		message = "You requested the " + genre + " novel<br>" + title + " by " + author + " (" + num + ")";
        }
        
        response.setContentType("text/html; charset=UTF-8");
		final PrintWriter out = response.getWriter();
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		String htmlInfo = docType + "<html>"+
    			"<head><title>LibraryBR</title></head>"+
    			"<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">" + 
    			"<script>function filterProfile(category) {const pr = document.querySelectorAll('.profile');pr.forEach(p => {const cat = p.getAttribute('writing');if (category === 'profile') {p.style.display = 'block';} else if (category === 'loans'){p.style.display='block';} else if (category === 'reservations'){p.style.display='block';} else if (category = 'readingList'){p.style.display='block';}else {p.style.display = 'none';}});}</script>" + 
    			"<script>function replaceHref(linkId) {var myAnchor = document.getElementById(linkId);switch (linkId) {"
				+ "case 'logo': case 'footerLogo': myAnchor.href = 'http://18.222.29.185/LibraryBR/index.html';break;"
				+ "case 'services':myAnchor.href = 'http://18.222.29.185/LibraryBR/services.html';break;"
				+ "case 'navEvents': case 'footerEvents': myAnchor.href = 'http://3.145.193.254:8080/LibraryBR/GetEventList';break;"
				+ "case 'navCatalog': case 'footerCatalog': myAnchor.href = 'http://18.222.29.185/LibraryBR/catalog.html';break;"
				+ "case 'navAccount': case 'footerAccount': myAnchor.href = 'http://18.222.29.185/LibraryBR/userLogin.html';break;"
				+ "case 'footerRegister':myAnchor.href = 'http://18.222.29.185/LibraryBR/userRegistration.html';break;"
				+ "case 'add': case 'footerAdd': myAnchor.href='http://18.222.29.185/LibraryBR/addToCatalog.html';default:break;}}</script>" +
				
    			"<body>\n" + 
    			"<div class=\"navbar\">"
				+ "<a href=\"18.222.29.185/LibraryBR/index.html\" id=\"logo\"onclick=\"replaceHref('logo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\"></a>"
				+ "<a href=\"https://www.ebrpl.com/digitallibrary/\" id=\"digital\">Digital Library</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/services.html\" id=\"services\" onclick=\"replaceHref('services')\">Services</a>"
				+ "<a href=\"http://3.145.193.254:8080/LibraryBR/GetEventList\" id=\"navEvents\" onclick=\"replaceHref('navEvents')\">Events</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"navCatalog\" onclick=\"replaceHref('navCatalog')\">Catalog</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/userLogin.html\" id=\"navAccount\" onclick=\"replaceHref('navAccount')\"><img src=\"Images/account.png\" style=\"height: 35px\"></a></div>" + 
				
    				"<div style=\"padding-top: 5%; padding-bottom: 15%;\"><h2><center>Thank you for your suggestion!</h2><br><br>" +
    				"<h4><center>" + message + "</h4></div>" + 
    				
				"</div><div class=\"footer\"><a id=\"footerLogo\" href=\"18.222.29.185/LibraryBR/index.html\" onclick=\"replaceHref('footerLogo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\" alt=\"LibraryBR\"></a>"
					+ "<div class=\"intro\"><div class=\"grid-item\" style=\"color: white\"><h4>Library Resources</h4>"
					+ "<a href=\"18.222.29.185/LibraryBR/userLogin.html\" id=\"footerAccount\" style=\"color: white\" onclick=\"replaceHref('footerAccount')\">My Account</a>"
					+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"footerCatalog\" style=\"color: white\" onclick=\"replaceHref('footerCatalog')\">Library Catalog</a>"
					+ "<a href=\"18.222.29.185/LibraryBR/userRegistration.html\" id=\"footerRegister\" style=\"color: white\"onclick=\"replaceHref('footerRegister')\">Library Card</a>"
					+ "<a href=\"18.222.29.185/LibraryBR/AddToCatalog.html\" id=\"footerAdd\" style=\"color: white\" onclick=\"replaceHref('footerAdd')\">Suggest a book</a></div><div class=\"grid-item\" style=\"color: white\"><h4>Contact Us</h4><div class=\"socials\" style=\"display: flex; flex-direction: row;\"><a><img src=\"Images/facebook.png\" style=\"height: 25px\" alt=\"Facebook\"></a><a><img src=\"Images/instagram.png\" style=\"height: 25px\" alt=\"Instagram\"></a><a><img src=\"Images/twitter.png\" style=\"height: 25px\" alt=\"Twitter\"></a><a><img src=\"Images/youtube.png\" style=\"height: 25px\" alt=\"Youtube\"></a></div></div></div></div>" +
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
