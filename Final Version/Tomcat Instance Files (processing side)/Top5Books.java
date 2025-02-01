

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class Top5Books
 */
@WebServlet("/Top5Books")
public class Top5Books extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Top5Books() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		String temp = request.getParameter("secret");
		
		StringBuilder sb = new StringBuilder();
		
		/*
		List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<String> availables = new ArrayList<>();
        List<String> iNums = new ArrayList<>();
        List<String> genres = new ArrayList<>();
        List<String> shelfmarks = new ArrayList<>();
        List<String> locations = new ArrayList<>();
        */
	
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
  			System.out.println("Created");
  			
  			String sql = "SELECT * from books where description like '%Top 5 Books%' or description like '%Book of the Month%' order by title asc;";
  			System.out.println(sql);
  			ResultSet rs = (ResultSet)stmt.executeQuery(sql);
			 
 		 	//STEP 5: Extract data from result set
 			 while(rs.next()){
 				 
 				String l=rs.getString("fileLocation");
 				String t = rs.getString("title");
 				String au=rs.getString("author");
 				// String av=rs.getString("available");
 				// String num=rs.getString("ISBN");
 				// String gen=rs.getString("genre");
 				String s=rs.getString("shelfmark");
 				// String d=rs.getString("description");
 				
 				 /*
 				locations.add(l);
 				titles.add(t);
 				authors.add(au);
 				availables.add(av);
 				iNums.add(num);
 				genres.add(gen);
 				shelfmarks.add(s);
 				descriptions.add(d);
 				*/
 				
 				String item = "<div class=\"grid-item\""
 						+ "style=\"width: 80%; margin:auto;cursor: pointer; padding-bottom: 5%;\">" 			
 				+ "<form action=\"http://3.145.193.254:8080/LibraryBR/ViewBook\" method=\"post\" style=\"width: 60%; margin: auto;\">"
 				+ "<input type=\"hidden\" name=\"searching\" value=\"" + s + "\">"
	  			+ "<button type=\"submit\"><img src=\"" + l + "\" style=\"width: 60%\"></button></form>"
	  			+ "<h3>" + t + "</h3> <h5>" + au + "</h5>"
	  			// + "<input type=\"submit\" value=\"More Info\"></form>" 
	  			+ "</div>";
 				sb.append(item);
 			 }
 			 rs.close();
 			 System.out.println("closing");
 			 conn.close();
 			 stmt.close();
  			
  		}catch(ClassNotFoundException | SQLException e){
  			e.printStackTrace();
  		}
  		/*
  		for (int i = 0; i <= 4; i++){
  			String x = availables.get(i);
	  		if (x.equals("1")){
	  			availables.set(i, "Available");
	  			System.out.println("Changed" + availables.get(i));
	  		} else if (!x.equals("1")){
	  			availables.set(i, "Not Available");
	  			System.out.println("Changed" + availables.get(i));
	  		}
  		}
  		*/
  		 		
  		
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
					+ "case 'navAccount': case 'footerAccount': myAnchor.href = 'http://3.145.193.254:8080/LibraryBR/IsLoggedIn';break;"
					+ "case 'footerRegister':myAnchor.href = 'http://18.222.29.185/LibraryBR/userRegistration.html';break;"
					+ "case 'add': case 'footerAdd': myAnchor.href='http://18.222.29.185/LibraryBR/addToCatalog.html';default:break;}}</script>" +
					
	    			"<body style=\"margin: 0;\">\n" + 
	    			"<div class=\"navbar\" style=\"background-color: #3E505B;\">"
						+ "<a href=\"18.222.29.185/LibraryBR/index.html\" id=\"logo\"onclick=\"replaceHref('logo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\"></a>"
						+ "<a href=\"https://www.ebrpl.com/digitallibrary/\" id=\"digital\">Digital Library</a>"
						+ "<a href=\"18.222.29.185/LibraryBR/services.html\" id=\"services\" onclick=\"replaceHref('services')\">Services</a>"
						+ "<a href=\"http://3.145.193.254:8080/LibraryBR/GetEventList\" id=\"navEvents\" onclick=\"replaceHref('navEvents')\">Events</a>"
						+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"navCatalog\" onclick=\"replaceHref('navCatalog')\">Catalog</a>"
						+ "<a href=\"http://3.145.193.254:8080/LibraryBR/IsLoggedIn\" id=\"navAccount\" onclick=\"replaceHref('navAccount')\"><img src=\"Images/account.png\" style=\"height: 35px\"></a></div>" + 
						
							"<h2 style=\"text-align: center\">Popular Books of the Month</h2>"
							+ "<h3 style=\"text-align: center\">Suggest books to add to our catalog <a href=\"18.222.29.185/LibraryBR/addToCatalog.html\" id=\"add\" onclick=\"replaceHref('add')\">here</a>!</h3>" +
						"<div class=\"intro\" style=\"padding-top: 5%; padding-bottom: 5%; grid-template-columns: repeat(3, 1fr); grid-template-rows: repeat(2, 1fr); width:80%; text-align: center;\">"
					  		/*
					  		+ "<div class=\"grid-item\">"
					  			+ "<img src=\"" + locations.get(0) + "\" style=\"width: 60%\">"
					  			+ "<h3>" + titles.get(0) + "</h3> <h5>" + authors.get(0) + "</h5><form action=\"http://3.145.193.254:8080/LibraryBR/ViewBook\" method=\"post\" style=\"width: 60%; margin: auto;\"><input type=\"hidden\" name=\"searching\" value=\"" + iNums.get(0) + "\"><input type=\"submit\" value=\"More Info\"></form>" 
					  		+ "</div>"
					  		
							+ "<div class=\"grid-item\">"
								+ "<img src=\"" + locations.get(1) + "\" style=\"width: 40%\">"
								+ "<h3>" + titles.get(1) + "</h3> <h5>" + authors.get(1) + "</h5><form action=\"http://3.145.193.254:8080/LibraryBR/ViewBook\" method=\"post\" style=\"width: 60%; margin: auto;\"><input type=\"hidden\" name=\"searching\" value=\"" + iNums.get(1) + "\"><input type=\"submit\" value=\"More Info\"></form>"
							+ "</div>"
							
							
							+ "<div class=\"grid-item\">"		
								+ "<img src=\"" + locations.get(2) + "\" style=\"width: 40%\">"
								+ "<h3>" + titles.get(2) + "</h3> <h5>" + authors.get(2) + "</h5><form action=\"http://3.145.193.254:8080/LibraryBR/ViewBook\" method=\"post\" style=\"width: 60%; margin: auto;\"><input type=\"hidden\" name=\"searching\" value=\"" + iNums.get(2) + "\"><input type=\"submit\" value=\"More Info\"></form>"
							+ "</div>"	
							
							+ "<div class=\"grid-item\">"
								+ "<img src=\"" + locations.get(3) + "\" style=\"width: 40%\">"
								+ "<h3>" + titles.get(3) + "</h3> <h5>" + authors.get(3) + "</h5><form action=\"http://3.145.193.254:8080/LibraryBR/ViewBook\" method=\"post\" style=\"width: 60%; margin: auto;\"><input type=\"hidden\" name=\"searching\" value=\"" + iNums.get(3) + "\"><input type=\"submit\" value=\"More Info\"></form>"
							+ "</div>"
							
							+ "<div class=\"grid-item\">"
								+ "<img src=\"" + locations.get(4) + "\" style=\"width: 40%\">"
								+ "<h3>" + titles.get(4) + "</h3> <h5>" + authors.get(4) + "</h5><form action=\"http://3.145.193.254:8080/LibraryBR/ViewBook\" method=\"post\" style=\"width: 60%; margin: auto;\"><input type=\"hidden\" name=\"searching\" value=\"" + iNums.get(4) + "\"><input type=\"submit\" value=\"More Info\"></form>"
							+ "</div>"	
								
							+ "<div class=\"grid-item\">"
								+ "<img src=\"" + locations.get(5) + "\" style=\"width: 40%\">"
								+ "<h3>" + titles.get(5) + "</h3> <h5>" + authors.get(5) + "</h5><form action=\"http://3.145.193.254:8080/LibraryBR/ViewBook\" method=\"post\" style=\"width: 60%; margin: auto;\"><input type=\"hidden\" name=\"searching\" value=\"" + iNums.get(5) + "\"><input type=\"submit\" value=\"More Info\"></form>"
							+ "</div>"	
							*/
							+ sb + "</div>" + 	
					  		
					  		"</div><div class=\"footer\" style=\"background-color: #3E505B;\"><a id=\"footerLogo\" href=\"18.222.29.185/LibraryBR/index.html\" onclick=\"replaceHref('footerLogo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\" alt=\"LibraryBR\"></a>"
								+ "<div class=\"intro\"><div class=\"grid-item\" style=\"color: white\"><h4>Library Resources</h4>"
								+ "<a href=\"http://3.145.193.254:8080/LibraryBR/IsLoggedIn\" id=\"footerAccount\" style=\"color: white\" onclick=\"replaceHref('footerAccount')\">My Account</a>"
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
