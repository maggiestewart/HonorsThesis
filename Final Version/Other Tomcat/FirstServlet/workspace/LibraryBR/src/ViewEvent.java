

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class ViewEvent
 */
@WebServlet("/ViewEvent")
public class ViewEvent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewEvent() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		final PrintWriter out = response.getWriter();
		
		String searchOn = request.getParameter("searching");
		
		Cookie[] cookies = request.getCookies();
		String card = "", cardName = "";
		for (Cookie aCookie : cookies) {
			if (aCookie.getName().equals("cardNo")){
				cardName = aCookie.getName();
			    card = aCookie.getValue();
			    // out.println(cardName + " - " + card);
			}
		}
		// out.println(cardName + " - " + card);
		
		if (searchOn == null){
			searchOn = "A0411630";
		}
		
		
		StringBuilder s = new StringBuilder();
		//String card = "21659156097940";
		//String card = request.getParameter("cardNumber");
		
//		if (card == null){
//			card = "21659156097940";
//		}
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		s.append(docType);
		String head = "<html><head><title>LibraryBR</title></head>"
				+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">"
				+ "<script>function filterProfile(category) {const pr = document.querySelectorAll('.profile');pr.forEach(p => {const cat = p.getAttribute('writing');if (category === 'profile') {p.style.display = 'block';} else if (category === 'loans'){p.style.display='block';} else if (category === 'reservations'){p.style.display='block';} else if (category = 'readingList'){p.style.display='block';}else {p.style.display = 'none';}});}</script>"
				+ "<script>function replaceHref(linkId) {var myAnchor = document.getElementById(linkId);switch (linkId) {"
				+ "case 'logo': case 'footerLogo': myAnchor.href = 'http://18.222.29.185/LibraryBR/index.html';break;"
				+ "case 'services':myAnchor.href = 'http://18.222.29.185/LibraryBR/services.html';break;"
				+ "case 'navEvents': case 'footerEvents': myAnchor.href = 'http://3.145.193.254:8080/LibraryBR/GetEventList';break;"
				+ "case 'navCatalog': case 'footerCatalog': myAnchor.href = 'http://18.222.29.185/LibraryBR/catalog.html';break;"
				+ "case 'navAccount': case 'footerAccount': myAnchor.href = 'http://3.145.193.254:8080/LibraryBR/IsLoggedIn';break;"
				+ "case 'footerRegister':myAnchor.href = 'http://18.222.29.185/LibraryBR/userRegistration.html';break;"
				+ "case 'add': case 'footerAdd': myAnchor.href='http://18.222.29.185/LibraryBR/addToCatalog.html';default:break;}}</script>";
		s.append(head);
		String nav = "<body style=\"margin: 0;\">\n <div class=\"navbar\" style=\"background-color: #3E505B;\">"
				+ "<a href=\"18.222.29.185/LibraryBR/index.html\" id=\"logo\"onclick=\"replaceHref('logo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\"></a>"
				+ "<a href=\"https://www.ebrpl.com/digitallibrary/\" id=\"digital\">Digital Library</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/services.html\" id=\"services\" onclick=\"replaceHref('services')\">Services</a>"
				+ "<a href=\"http://3.145.193.254:8080/LibraryBR/GetEventList\" id=\"navEvents\" onclick=\"replaceHref('navEvents')\">Events</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"navCatalog\" onclick=\"replaceHref('navCatalog')\">Catalog</a>"
				+ "<a href=\"http://3.145.193.254:8080/LibraryBR/IsLoggedIn\" id=\"navAccount\" onclick=\"replaceHref('navAccount')\"><img src=\"Images/account.png\" style=\"height: 35px\"></a></div>";
			
		s.append(nav);
		
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
  			String sql = "SELECT * from eventInfo where id =\'" + searchOn + "\';";
  			System.out.println(searchOn);
  			
  			String heading = "<div class=\"e\" style=\"padding-top: 5%; padding-bottom: 5%; text-align: center; width: 80%; margin: auto;\">";  			
  			s.append(heading);
  			
  			ResultSet rs = (ResultSet)stmt.executeQuery(sql);
  			
  			 
 		 	//STEP 5: Extract data from result set
 			 while(rs.next()){
 				 System.out.println("data");
 				 
 				 String t = rs.getString("title");
 				 Date d = rs.getDate("day");
 				 String time = rs.getString("time");
 				 String a = rs.getString("audience");
 				 String n = rs.getString("contactName");
 				 String e = rs.getString("contactEmail");
 				 String descr = rs.getString("description");
 				 String icon = "";
 				if(a.equals("All")){
 					icon = "Images/all.png"; //<a href="https://www.flaticon.com/free-icons/event" title="event icons">Event icons created by iconixar - Flaticon</a>
 				} else if (a.equals("Adult")){
 					icon = "Images/adult.png"; //<a href="https://www.flaticon.com/free-icons/adults" title="adults icons">Adults icons created by Freepik - Flaticon</a>
 				}else if (a.equals("Children")){
 					icon = "Images/children.png"; //<a href="https://www.flaticon.com/free-icons/children" title="children icons">Children icons created by Freepik - Flaticon</a>
 				} else if (a.equals("Teen")){
 					icon = "Images/teen.png"; //<a href="https://www.flaticon.com/free-icons/teen" title="teen icons">Teen icons created by Freepik - Flaticon</a>
 				}
 				
 				SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
 		        String outputDateStr = outputFormat.format(d);
 				 
 				String html = "<div class=\"c\" style=\"width: 80%; margin:auto;cursor: pointer;\">"
	 				 				+ "<h2>" + t + "</h2>"
	 				 				+ "<h3>" + descr + "</h3>"
	 				 				+ "<div style=\"display: flex; align-items: center;\">"
	 				 					+ "<img src=\"Images/calendar.png\" style=\"width: 5%;\">"
	 				 					+ "<h4>Date: " + outputDateStr + "</h4>"
	 				 				+ "</div><br>"
	 				 				+ "<div style=\"display: flex; align-items: center;\">"
	 				 					+ "<img src=\"Images/clock.png\" style=\"width: 5%;\">"
	 				 					+ "<h4>Time: " + time + "</h4>"
	 				 				+ "</div><br>"
	 				 				+ "<div style=\"display: flex; align-items: center;\">"
	 				 					+ "<img src=\"" + icon + "\" style=\"width: 5%;\">"
	 				 					+ "<h4>Audience: " + a + "</h4>"
	 				 				+ "</div><br><br>"
	 				 				+ "<h4>Contact " + n + " (" + e + ") for more information</h4>";
 				 s.append(html);
 				 System.out.println(html);
 				 System.out.println();
 				 
 				 String markInterested = "<form action=\"http://3.145.193.254:8080/LibraryBR/MarkEventInterested\" method=\"post\" style=\"width: 60%; margin: auto;\">"
				 		+ "<input type=\"hidden\" name=\"searching\" value=\"" + searchOn + "\">"
				 		+ "<input type=\"hidden\" name=\"name\" value=\"" + t + "\">"
			 			+ "<input type=\"submit\" value=\"Interested\"></form>"
			 			+ "</div>";
 			 
 				 s.append(markInterested);
 				 System.out.println(markInterested);
 				 System.out.println();
 			 }
 			 
 			 rs.close();
 			 
 			 System.out.println("closing");
 			 conn.close();
 			 stmt.close();
  			
  		}catch(ClassNotFoundException | SQLException e){
  			e.printStackTrace();
  		}
  		  		
  		String end = "</div><div class=\"footer\" style=\"background-color: #3E505B;\"><a id=\"footerLogo\" href=\"18.222.29.185/LibraryBR/index.html\" onclick=\"replaceHref('footerLogo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\" alt=\"LibraryBR\"></a>"
				+ "<div class=\"intro\"><div class=\"grid-item\" style=\"color: white\"><h4>Library Resources</h4>"
				+ "<a href=\"http://3.145.193.254:8080/LibraryBR/IsLoggedIn\" id=\"footerAccount\" style=\"color: white\" onclick=\"replaceHref('footerAccount')\">My Account</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"footerCatalog\" style=\"color: white\" onclick=\"replaceHref('footerCatalog')\">Library Catalog</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/userRegistration.html\" id=\"footerRegister\" style=\"color: white\"onclick=\"replaceHref('footerRegister')\">Library Card</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/AddToCatalog.html\" id=\"footerAdd\" style=\"color: white\" onclick=\"replaceHref('footerAdd')\">Suggest a book</a></div><div class=\"grid-item\" style=\"color: white\"><h4>Contact Us</h4><div class=\"socials\" style=\"display: flex; flex-direction: row;\"><a><img src=\"Images/facebook.png\" style=\"height: 25px\" alt=\"Facebook\"></a><a><img src=\"Images/instagram.png\" style=\"height: 25px\" alt=\"Instagram\"></a><a><img src=\"Images/twitter.png\" style=\"height: 25px\" alt=\"Twitter\"></a><a><img src=\"Images/youtube.png\" style=\"height: 25px\" alt=\"Youtube\"></a>"
				+ "</div></div></div></div>" +
			"</body></html>";
	    s.append(end);
	    String htmlInfo = s.toString();
	    System.out.println(htmlInfo);
	   
	    out.println(htmlInfo);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
