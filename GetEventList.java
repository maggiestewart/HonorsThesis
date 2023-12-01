

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class GetEventList
 */
public class GetEventList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEventList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuilder s = new StringBuilder();
		
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
				+ "case 'navAccount': case 'footerAccount': myAnchor.href = 'http://18.222.29.185/LibraryBR/userLogin.html';break;"
				+ "case 'footerRegister':myAnchor.href = 'http://18.222.29.185/LibraryBR/userRegistration.html';break;"
				+ "case 'add': case 'footerAdd': myAnchor.href='http://18.222.29.185/LibraryBR/addToCatalog.html';default:break;}}</script>";
		s.append(head);
		String nav = "<body>\n <div class=\"navbar\">"
				+ "<a href=\"18.222.29.185/LibraryBR/index.html\" id=\"logo\"onclick=\"replaceHref('logo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\"></a>"
				+ "<a href=\"https://www.ebrpl.com/digitallibrary/\" id=\"digital\">Digital Library</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/services.html\" id=\"services\" onclick=\"replaceHref('services')\">Services</a>"
				+ "<a href=\"http://3.145.193.254:8080/LibraryBR/GetEventList\" id=\"navEvents\" onclick=\"replaceHref('navEvents')\">Events</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"navCatalog\" onclick=\"replaceHref('navCatalog')\">Catalog</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/userLogin.html\" id=\"navAccount\" onclick=\"replaceHref('navAccount')\"><img src=\"Images/account.png\" style=\"height: 35px\"></a></div>";
	
		s.append(nav);
		
		String filterBar = "<div style=\"padding-top: 5%; padding-bottom: 5%; display: flex; justify-content: space-between; text-align: center; width: 80%; margin: auto; \">"
				+ "<form action=\"http://3.145.193.254:8080/LibraryBR/FilterEvents\" method=\"post\" style=\"width: 60%; margin: auto;\">"
					+ "<div class=\"the\" style=\"display: flex; justify-content: center;\">"
						+ "<img src=\"Images/all.png\" style=\"width: 7%; margin: auto; margin-right: 2%; place-items: center\"><input type=\"submit\" name=\"searching\" value=\"All\" style=\"margin:auto; background-color: #404260; color: white; margin-right: 2%; font-weight: bold; padding-top: 2%; padding-bottom: 2%; padding-left: 2.5%; padding-right: 2.5%\">"
						+ "<img src=\"Images/adult.png\" style=\"width: 8%; margin: auto; margin-right: 2%; place-items: center\"><input type=\"submit\" name=\"searching\" value=\"Adult\" style=\"margin: auto; background-color: #404260; color: white; margin-right: 2%; font-weight: bold; padding-top: 2%; padding-bottom: 2%; padding-left: 2.5%; padding-right: 2.5%\">"
						+ "<img src=\"Images/children.png\" style=\"width: 8%; margin: auto; margin-right: 2%; place-items: center\"><input type=\"submit\" name=\"searching\" value=\"Children\" style=\"margin: auto; background-color: #404260; color: white; margin-right: 2%; font-weight: bold; padding-top: 2%; padding-bottom: 2%; padding-left: 2.5%; padding-right: 2.5%\">"
						+ "<img src=\"Images/teen.png\" style=\"width: 7%; margin: auto; margin-right: 2%; place-items: center\"><input type=\"submit\" name=\"searching\" value=\"Teen\" style=\"margin: auto; background-color: #404260; color: white; margin-right: 2%; font-weight: bold; padding-top: 2%; padding-bottom: 2%; padding-left: 2.5%; padding-right: 2.5%\">"
						+ "</div>"
					+ "</form></div>";
		s.append(filterBar);
		System.out.println(filterBar);
		String icon="";
		
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
  			
  			String sql = "SELECT title, time, day, id, audience from eventInfo order by day, time asc;";
  			
  			ResultSet rs = (ResultSet)stmt.executeQuery(sql);
  			
  			String heading = "<h2 style=\"text-align: center;\">Upcoming Events</h2><div class=\"intro\" style=\"padding-bottom: 5%; text-align: center; width: 80%; margin: auto; grid-template-columns: repeat(3, 1fr); grid-template-rows: repeat(3, 1fr);\">";  			
  			s.append(heading);
  			
			 
 		 	//STEP 5: Extract data from result set
 			 while(rs.next()){
 				 System.out.println("Entered while");
 				 //Retrieve by column name
 				 String t = rs.getString("title");
 				 String time  = rs.getString("time");
 				 Date d	  = rs.getDate("day");
 				 String au = rs.getString("audience");
 				 String id = rs.getString("id");
 				 
 				if(au.equals("All")){
 					icon = "Images/all.png"; //<a href="https://www.flaticon.com/free-icons/event" title="event icons">Event icons created by iconixar - Flaticon</a>
 				} else if (au.equals("Adult")){
 					icon = "Images/adult.png"; //<a href="https://www.flaticon.com/free-icons/adults" title="adults icons">Adults icons created by Freepik - Flaticon</a>
 				}else if (au.equals("Children")){
 					icon = "Images/children.png"; //<a href="https://www.flaticon.com/free-icons/children" title="children icons">Children icons created by Freepik - Flaticon</a>
 				} else if (au.equals("Teen")){
 					icon = "Images/teen.png"; //<a href="https://www.flaticon.com/free-icons/teen" title="teen icons">Teen icons created by Freepik - Flaticon</a>
 				}
 				
 				SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
 		        String outputDateStr = outputFormat.format(d);
 				
 				String html = "<div class=\"grid-item\" style=\"width: 80%; margin:auto; padding-bottom: 5%;\">"
 				 		+ "<img src=\"" + icon + "\" style=\"width: 15%\"><h3>" + t + "</h3> <h5>" + outputDateStr + " | " + time + "</h5>"
 				 				+ "<form action=\"http://3.145.193.254:8080/LibraryBR/ViewEvent\" method=\"post\" style=\"width: 60%; margin: auto;\"><input type=\"hidden\" name=\"searching\" value=\"" + id + "\"><input type=\"submit\" value=\"More Info\"></form></div>";
 				 s.append(html);
 				 System.out.println(html);
 			 }
 			 conn.close();
 			 stmt.close();
  			
  		}catch(ClassNotFoundException | SQLException e){
  			e.printStackTrace();
  		}
		

  		response.setContentType("text/html; charset=UTF-8");
		final PrintWriter out = response.getWriter();
			
		String end = "</div><div class=\"footer\"><a id=\"footerLogo\" href=\"18.222.29.185/LibraryBR/index.html\" onclick=\"replaceHref('footerLogo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\" alt=\"LibraryBR\"></a>"
				+ "<div class=\"intro\"><div class=\"grid-item\" style=\"color: white\"><h4>Library Resources</h4>"
				+ "<a href=\"18.222.29.185/LibraryBR/userLogin.html\" id=\"footerAccount\" style=\"color: white\" onclick=\"replaceHref('footerAccount')\">My Account</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"footerCatalog\" style=\"color: white\" onclick=\"replaceHref('footerCatalog')\">Library Catalog</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/userRegistration.html\" id=\"footerRegister\" style=\"color: white\"onclick=\"replaceHref('footerRegister')\">Library Card</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/AddToCatalog.html\" id=\"footerAdd\" style=\"color: white\" onclick=\"replaceHref('footerAdd')\">Suggest a book</a></div><div class=\"grid-item\" style=\"color: white\"><h4>Contact Us</h4><div class=\"socials\" style=\"display: flex; flex-direction: row;\"><a><img src=\"Images/facebook.png\" style=\"height: 25px\" alt=\"Facebook\"></a><a><img src=\"Images/instagram.png\" style=\"height: 25px\" alt=\"Instagram\"></a><a><img src=\"Images/twitter.png\" style=\"height: 25px\" alt=\"Twitter\"></a><a><img src=\"Images/youtube.png\" style=\"height: 25px\" alt=\"Youtube\"></a></div></div></div></div>" +
			"</body></html>";
	    s.append(end);
	    String htmlInfo = s.toString();
	   
	    out.println(htmlInfo);
	   
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
