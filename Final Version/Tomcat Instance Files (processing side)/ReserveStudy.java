

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class ReserveStudy
 */
public class ReserveStudy extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReserveStudy() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String roomID = request.getParameter("roomID");
		
		if (roomID == null){
			roomID = "MainA";
		}
		
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = sdf.format(d);
		
		String photoFile = "Images/study-rooms.jpeg";
		String[] times = new String[6];
			/*
			 * 9:00 AM  --> 0
			 * 11:00 AM --> 1
			 * 1:00 PM  --> 2
			 * 3:00 PM  --> 3
			 * 5:00 PM  --> 4
			 * 7:00 PM  --> 5
			 */
		
		final PrintWriter out = response.getWriter();
		
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
  		
  		String heading = "<div style=\"text-align: center; padding-top: 2%;\"><img style=\"width: 30%; border-radius: 2%;\" src =\"Images/study-rooms.jpeg\">"
					+ "<h2 style=\"text-align: center; padding-top:5%;\">" + roomID + " Availability on " + currentDate + "</h2>";  			
	  	s.append(heading);
  		
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
  			String sql = "SELECT * from studyRoomReservations where roomID = \'" + roomID + "\' "
  					+ "and reserveDate = \'" + currentDate + "\';";
  			System.out.println(sql);
  			
  			ResultSet rs = (ResultSet)stmt.executeQuery(sql);
  			
 		 	//STEP 5: Extract data from result set
 			 while(rs.next()){
 				 System.out.println("data");
 				 
 				 String time = rs.getString("reserveTime");
 				 if (time.contains("9") || time.equals("09:00:00")){
 					 times[0] = "09:00:00";
 				 } else if (time.contains("11") || time.equals("11:00:00")){
 					 times[1] = "11:00:00";
 				 } else if (time.contains("1") || time.equals("1:00:00")){
 					times[2] = "1:00:00";
 				 } else if (time.contains("3") || time.equals("3:00:00")){
 					times[3] = "3:00:00";
 				 } else if (time.contains("5") || time.equals("5:00:00")){
 					times[4] = "5:00:00";
 				 } else if (time.contains("7") || time.equals("7:00:00")){
 					times[5] = "7:00:00";
 				 }			
 			 }
 			 
 			 rs.close();
 			 
 			 System.out.println("closing");
 			 conn.close();
 			 stmt.close();
 			 
 			 for (int i = 0; i < times.length; i++){
 				 if (times[i] == null || times[i].equals("")){
 					 times[i] = "<form action=\"http://3.145.193.254:8080/LibraryBR/Reserve\" method=\"post\" style=\"width: 50%; margin: auto;\">"
 					 			+ "<input type=\"hidden\" name=\"searchVal\" value=\"study\">"
 					 			+ "<input type=\"hidden\" name=\"roomID\" value=\"" + roomID + "\">"
 					 			+ "<input type=\"hidden\" name=\"currentDate\" value=\"" + currentDate + "\">"
 					 			+ "<input type=\"hidden\" name=\"timePos\" value=\"" + i + "\">"
 					 			+ "<button input=\"submit\">Reserve now!</button></form>";
 				 } else {
 					 times[i] = "Unavailable";
 				 }
 			 }
 			 
 			String table = "<div style=\"margin: auto; padding-bottom: 2%; width: 50%; display: flex;justify-content: center;align-items: center;\"><table><thead><tr><th>Time</th><th>Status</th></tr></thead>"
						+ "<tbody>"
							+ "<tr><td>9:00 AM</td><td>" + times[0] + "</td></tr>" 
							+ "<tr><td>11:00 AM</td><td>" + times[1] + "</td></tr>"
					    + "<tr><td>1:00 PM</td><td>" + times[2] + "</td></tr>"
					    + "<tr><td>3:00 PM</td><td>" + times[3] + "</td></tr>"
					    + "<tr><td>5:00 PM</td><td>" + times[4] + "</td></tr>"
					    + "<tr><td>7:00 PM</td><td>" + times[5] + "</td></tr>"
					+ "</tbody></table></div>";
 			
 			s.append(table);
  			
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
