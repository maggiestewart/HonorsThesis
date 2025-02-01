

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class IsLoggedIn
 */
public class IsLoggedIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IsLoggedIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		String cardNo = "", cardName = "";
		for (Cookie aCookie : cookies) {
			if (aCookie.getName().equals("cardNo")){
				cardName = aCookie.getName();
			    cardNo = aCookie.getValue();
			    // out.println(cardName + " - " + card);
			}
		}
		// out.println(cardName + " - " + card);
		System.out.println("COOKIE: " + cardName + " - " + cardNo);
		
		PrintWriter out = response.getWriter();
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
				+ "case 'footerRegister': case 'noReg': myAnchor.href = 'http://18.222.29.185/LibraryBR/userRegistration.html';break;"
				+ "case 'add': case 'footerAdd': myAnchor.href='http://18.222.29.185/LibraryBR/addToCatalog.html';default:break;}}</script>" +
    			"<body style=\"margin: 0;\">\n" + 
    			"<div class=\"navbar\" style=\"background-color: #3E505B;\">"
					+ "<a href=\"18.222.29.185/LibraryBR/index.html\" id=\"logo\"onclick=\"replaceHref('logo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\"></a>"
					+ "<a href=\"https://www.ebrpl.com/digitallibrary/\" id=\"digital\">Digital Library</a>"
					+ "<a href=\"18.222.29.185/LibraryBR/services.html\" id=\"services\" onclick=\"replaceHref('services')\">Services</a>"
					+ "<a href=\"http://3.145.193.254:8080/LibraryBR/GetEventList\" id=\"navEvents\" onclick=\"replaceHref('navEvents')\">Events</a>"
					+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"navCatalog\" onclick=\"replaceHref('navCatalog')\">Catalog</a>"
					+ "<a href=\"http://3.145.193.254:8080/LibraryBR/IsLoggedIn\" id=\"navAccount\" onclick=\"replaceHref('navAccount')\"><img src=\"Images/account.png\" style=\"height: 35px\"></a>"
				+ "</div>";
		String footer = "<div class=\"footer\" style=\"background-color: #3E505B;\"><a id=\"footerLogo\" href=\"18.222.29.185/LibraryBR/index.html\" onclick=\"replaceHref('footerLogo')\"><img src=\"Images/libraryLogo.svg\" style=\"height: 80px\" alt=\"LibraryBR\"></a>"
				+ "<div class=\"intro\"><div class=\"grid-item\" style=\"color: white\"><h4>Library Resources</h4>"
				+ "<a href=\"http://3.145.193.254:8080/LibraryBR/IsLoggedIn\" id=\"footerAccount\" style=\"color: white\" onclick=\"replaceHref('footerAccount')\">My Account</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/catalog.html\" id=\"footerCatalog\" style=\"color: white\" onclick=\"replaceHref('footerCatalog')\">Library Catalog</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/userRegistration.html\" id=\"footerRegister\" style=\"color: white\"onclick=\"replaceHref('footerRegister')\">Library Card</a>"
				+ "<a href=\"18.222.29.185/LibraryBR/AddToCatalog.html\" id=\"footerAdd\" style=\"color: white\" onclick=\"replaceHref('footerAdd')\">Suggest a book</a></div>"
					+ "<div class=\"grid-item\" style=\"color: white\"><h4>Contact Us</h4>"
						+ "<div class=\"socials\" style=\"display: flex; flex-direction: row;\">"
							+ "<a><img src=\"Images/facebook.png\" style=\"height: 25px\" alt=\"Facebook\"></a>"
							+ "<a><img src=\"Images/instagram.png\" style=\"height: 25px\" alt=\"Instagram\"></a>"
							+ "<a><img src=\"Images/twitter.png\" style=\"height: 25px\" alt=\"Twitter\"></a>"
							+ "<a><img src=\"Images/youtube.png\" style=\"height: 25px\" alt=\"Youtube\"></a>"
						+ "</div>"
					+ "</div>"
			+ "</div>"
			+ "</div>" +
			"</body></html>";
		
		if (!cardNo.equals("")){
			response.setContentType("text/html;charset=UTF-8");
			
			StringBuilder s = new StringBuilder();
			
			String name = "", email = "";
			Date expirationDate = null;
			
			String titleLoans = "", authorLoans = "",  numRenewals = "", fileLoans = ""; 
			Date dueDateLoans = null;
//			String titleSuggests = "", authorSuggests = "";
//			Date dateSuggests = null;
			String shelfmarkReservations = "", shelfmarkReadingLists = "", fileReservations = "";
			Date dateReserved = null, dateHeld = null;
			String roomMeeting = "", availableMeeting = "", reserveTimeMeeting = "", eventTitle = "", eventTime = "", eventAudience = "";
			Date reserveDateMeeting = null, reserveDateStudy = null, eventDate = null;
			String roomStudy = "", availableStudy = "", reserveTimeStudy = ""; 
			
			//JDBC driver name and database url
	  		String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	  		
	  		//String DB_URL = "jdbc:mysql://18.218.14.37:3306/testDB";
	  		String DB_URL = "jdbc:mysql://localhost:3306/project";
	  		
	  		//DB Credential
	  		String USER = "root";
	  		String PASS = "";
	  		
	  		Connection conn = null;
	  		Statement stmt = null;
	  		
	  		SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
	  		String exp = "";
	  		
	  		String loansTableHeader = "<table border=\"1\"><tr><th>Title</th><th>Due Date</th><th>Renewals Left</th></tr>";
			String suggestsTableHeader = "<table border=\"1\"><tr><th style=\"width: 40%;\">Title</th><th style=\"width: 40%;\">Author</th><th style=\"width: 40%;\">Request Date</th></tr>";
			String meetingsTableHeader = "<table border=\"1\"><tr><th style=\"width: 40%;\">Room</th><th style=\"width: 40%;\">Date</th><th style=\"width: 40%;\">Time</th></tr>";
			String studyTableHeader = "<table border=\"1\"><tr><th style=\"width: 40%;\">Room</th><th style=\"width: 40%;\">Date</th><th style=\"width: 40%;\">Time</th></tr>";
			String reservationsTableHeader = "<table border=\"1\"><tr><th>Title</th><th>Reservation Date</th><th>Held Until</th></tr>";
			String readingListsTableHeader = "<table border=\"1\"><tr><th>Title</th></tr>";
			String eventsTableHeader = "<table border=\"1\"><tr><th style=\"width: 40%;\">Name</th><th style=\"width: 40%;\">Day</th><th style=\"width: 40%;\">Time</th><th style=\"width: 40%;\">Audience</th></tr>";
			
			StringBuilder loans = new StringBuilder();
			StringBuilder suggests = new StringBuilder(suggestsTableHeader);
			StringBuilder meetings = new StringBuilder(meetingsTableHeader);
			StringBuilder study = new StringBuilder(studyTableHeader);
			StringBuilder reservations = new StringBuilder();
			StringBuilder readingLists = new StringBuilder();
			StringBuilder events = new StringBuilder(eventsTableHeader);
			
			String tableFooter = "</table>";
			
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
	  			//String sql = "SELECT name, email, expirationDate from userInfo where cardNumber = \"" + cardNo + "\";";
	  			
	  			String sql = "SELECT name, email, expirationDate from userInfo where cardNumber = \'" + cardNo + "\';";
	  			System.out.println(sql);
	  			ResultSet rs = (ResultSet)stmt.executeQuery(sql);
				 
	 		 	//STEP 5: Extract data from result set
	 			 while(rs.next()){
	 				 System.out.println("Entered while");
	 				 //Retrieve by column name
	 				 name = rs.getString("name");
	 				 email = rs.getString("email");
	 				 expirationDate = rs.getDate("expirationDate");
	 			 
	 				 exp = outputFormat.format(expirationDate);
	 			 }
	 			 rs.close();
	 			 
	 			 // String sqlLoans = "SELECT shelfmark, returnDate, renewalsAvailable from loanedBooks where cardNumber = \'" + cardNo + "\' and completedTransaction = \'0\';";
	 			 String sqlLoans = "SELECT b.fileLocation, b.title, l.shelfmark, l.returnDate, l.renewalsAvailable "
	 			 		+ "FROM loanedBooks l join Books b on l.shelfmark = b.shelfmark "
	 			 		+ "WHERE cardNumber = \'" + cardNo + "\' and completedTransaction = \'0\';";
	 			 System.out.println(sqlLoans);
	 			 ResultSet rsLoans = (ResultSet)stmt.executeQuery(sqlLoans);
	 			 
	 			 while (rsLoans.next()){
	 				 System.out.println("Entered while...");
	 				 fileLoans = rsLoans.getString("fileLocation");
	 				 titleLoans = rsLoans.getString("title");
	 				 // authorLoans = rsLoans.getString("author");
	 				 dueDateLoans = rsLoans.getDate("returnDate");
	 				 numRenewals = rsLoans.getString("renewalsAvailable");
	 				 String d = outputFormat.format(dueDateLoans);
	 				 
	 				 loans.append(
	 						"<div class=\"grid-item\" style=\"width: 90%;cursor: pointer; padding-bottom: 5%;\"><img src=\""+ fileLoans + "\" style=\"width: 80%; padding: 10px;\">"
	 								+ "<h3>" + titleLoans + "</h3>"
	 								+ "<h5>Due on: <br>" + d + "<br><br>"
	 								+ "Renewals remaining: <br>" + numRenewals + "/3</h5></div>");
	 				 // loans.append("<tr><td>" + titleLoans + "</td><td>" + d +  "</td><td>" + numRenewals + "</td></tr>");
	 			 }
	 			 //loans.append(tableFooter);
	 			 rsLoans.close();
	 			 
	 			 
	 			 //String sqlReservations = "SELECT shelfmark, dateReserved, dateHeld from reservedBooks where cardNumber = \'" + cardNo + "\' and pickupStatus = \'0\';";
	 			 String sqlReservations = "SELECT b.fileLocation, b.title, r.shelfmark, r.dateReserved, r.dateHeld "
	 			 		+ "FROM reservedBooks r join Books b on r.shelfmark = b.shelfmark "
	 			 		+ "WHERE cardNumber = \'" + cardNo + "\' and pickupStatus = \'0\';";
	 			 System.out.println(sqlReservations);
	 			 ResultSet rsReservations = (ResultSet)stmt.executeQuery(sqlReservations);
	 			 
	 			 while (rsReservations.next()){
	 				 System.out.println("Entered while...");
	 				 String title = rsReservations.getString("title");
	 				 fileReservations = rsReservations.getString("fileLocation");
	 			 	 shelfmarkReservations = rsReservations.getString("shelfmark");
	 				 dateReserved = rsReservations.getDate("dateReserved");
	 				 dateHeld = rsReservations.getDate("dateHeld");
	 				 String dr = outputFormat.format(dateReserved);
	 				 String dh = outputFormat.format(dateHeld);
	 				  				 
	 				 // reservations.append("<tr><td>" + shelfmarkReservations + "</td><td>" + dr +  "</td><td>" + dh + "</td></tr>");
	 				 reservations.append(
	 						"<div class=\"grid-item\" style=\"width: 90%; cursor: pointer; padding-bottom: 5%;\"><img src=\""+ fileReservations + "\" style=\"width: 80%; padding: 10px;\">"
	 								+ "<h3>" + title + "</h3>"
	 								+ "<h5>Reserved on: <br>" + dr + "<br><br>"
	 								+ "Held until: <br>" + dh + "</h5></div>");
	 				 
	 			 }
	 			 //reservations.append(tableFooter);
	 			 rsReservations.close();
	 			 
	 			 String sqlReadingList = "SELECT rl.shelfmark, b.title, b.fileLocation from readingList rl "
	 			 		+ "join Books b on rl.shelfmark = b.shelfmark where rl.cardNumber = " + cardNo + ";"; //cardNo
	 			 // "SELECT shelfmark from readingList where cardNumber = \'" + cardNo + "\';";
	 			 System.out.println(sqlReadingList);
	 			 ResultSet rsReadingList = (ResultSet)stmt.executeQuery(sqlReadingList);
	 			 
	 			 while (rsReadingList.next()){
	 				 System.out.println("Entered while...");
	 			 	 shelfmarkReadingLists = rsReadingList.getString("shelfmark");
	 			 	 String titles = rsReadingList.getString("title");
	 			 	 String files = rsReadingList.getString("fileLocation");
	 			 	 readingLists.append(
	 			 			 "<div class=\"grid-item\" style=\"cursor: pointer; padding-bottom: 5%;\">"
	 			 			 + "<form action=\"http://3.145.193.254:8080/LibraryBR/RemoveBookReadingList\" method=\"post\" style=\"width: 60%; margin: auto;\">"
	 			 	 			+ "<input type=\"hidden\" name=\"cardNo\" value=\"" + cardNo + "\">"
	 			 	 			+ "<input type=\"hidden\" name=\"title\" value=\"" + titles + "\">"
	 			 	 			+ "<input type=\"hidden\" name=\"rlshelfmark\" value=\"" + shelfmarkReadingLists + "\">" + "<button type=\"submit\">"
	 			 	 		 + "<img src=\""+ files + "\" style=\"width: 90%; padding: 10px;\"></button></form></div>");
	 				 //readingLists.append("<tr><td>" + shelfmarkReadingLists + "</td></tr>");
	 			 }
	 			 //readingLists.append(tableFooter);
	 			 rsReadingList.close();
	 			 
	 			 
	 			 String sqlSuggests = "SELECT title, author, dateRequested from requestedBooks where cardNumber = \'" 
	 		 	 + cardNo + "\' order by dateRequested asc;"; //cardNo
				 System.out.println(sqlSuggests);
				 ResultSet rsSuggests = (ResultSet)stmt.executeQuery(sqlSuggests);
				 
				 //suggests.append(suggestsTableHeader);
				 while (rsSuggests.next()){
					 System.out.println("SUGGESTS Entered while...");
					 String titleSuggests = rsSuggests.getString("title");
					 String authorSuggests = rsSuggests.getString("author");
					 Date dateSuggests = rsSuggests.getDate("dateRequested");
					 String d = outputFormat.format(dateSuggests);
					 
					 suggests.append("<tr><td style=\"width: 40%;\">" + titleSuggests + "</td><td style=\"width: 40%;\">" 
					 + authorSuggests + "</td><td style=\"width: 40%;\">" + d + "</td></tr>");
					
				 }
				 suggests.append(tableFooter);
				 rsSuggests.close();
	 			 
	 			 
	 			 String sqlMeetings = "SELECT roomID, reserveDate, reserveTime from meetingroomreservations "
	 			 		+ "where cardNumber = \'" + cardNo + "\' order by reserveDate asc;"; //cardNo
	 			 System.out.println(sqlMeetings);
				 ResultSet rsMeetings = (ResultSet)stmt.executeQuery(sqlMeetings);
				 
				 while (rsMeetings.next()){
					 System.out.println("Entered while...");
					 roomMeeting = rsMeetings.getString("roomID");
					 reserveDateMeeting = rsMeetings.getDate("reserveDate");
					 reserveTimeMeeting = rsMeetings.getString("reserveTime");
					 String d = outputFormat.format(reserveDateMeeting);
					 
					 meetings.append("<tr><td style=\"width: 40%;\">" + roomMeeting + "</td>"
					 		+ "<td style=\"width: 40%;\">" + d + "</td><td style=\"width: 40%;\">" + reserveTimeMeeting + "</td></tr>");
				 }
				 meetings.append(tableFooter);
				 rsMeetings.close();
	 			 
				 String sqlStudy = "SELECT roomID, reserveDate, reserveTime from studyroomreservations "
				 		+ "where cardNumber = \'" + cardNo + "\' order by reserveDate asc;"; //cardNo
	 			 System.out.println(sqlStudy);
				 ResultSet rsStudy = (ResultSet)stmt.executeQuery(sqlStudy);
				 
				 while (rsStudy.next()){
					 System.out.print("Entered while...");
					 roomStudy = rsStudy.getString("roomID");
					 reserveDateStudy = rsStudy.getDate("reserveDate");
					 reserveTimeStudy = rsStudy.getString("reserveTime");
					 String d = outputFormat.format(reserveDateStudy);
					 
					 study.append("<tr><td style=\"width: 40%;\">" + roomStudy + "</td>"
					 		+ "<td style=\"width: 40%;\">"+ d + "</td><td style=\"width: 40%;\">"+ reserveTimeStudy + "</td></tr>");
				 }
				 study.append(tableFooter);
				 rsStudy.close();
				 
	 			 String sqlEvents = "SELECT i.title, i.audience, i.day, i.time from eventRegistration r join eventInfo i on i.id = r.eventID "
	 			 		+ "WHERE i.day >= \'2024-04-1\' and cardNumber = \'" + cardNo + "\';";
	 			 System.out.println(sqlEvents);
				 ResultSet rsEvents = (ResultSet)stmt.executeQuery(sqlEvents);
				 
				 while (rsEvents.next()){
					 System.out.print("Entered while...");
					 eventTitle = rsEvents.getString("title");
					 eventDate = rsEvents.getDate("day");
					 eventTime = rsEvents.getString("time");
					 eventAudience = rsEvents.getString("audience");
					 String d = outputFormat.format(eventDate);
					 
					 events.append("<tr><td style=\"width: 38%;\">" + eventTitle + "</td>"
					 		+ "<td style=\"width: 20%;\">"+ d + "</td>"
					 		+ "<td style=\"width: 20%;\">"+ eventTime + "</td><td style=\"width: 40%;\">" + eventAudience + "</td></tr>");
					 
				 }
				 System.out.print("ROW : " + events);
				 events.append(tableFooter);
				 rsEvents.close();
				 
	 			 System.out.println("closing");
	 			 conn.close();
	 			 stmt.close();
	  			
	  		}catch(ClassNotFoundException | SQLException e){
	  			e.printStackTrace();
	  		}
	  			s.append(htmlInfo);
				String ht =
						"<h2><center>Welcome " + name + "!</h2>" +
		    				
		    				"<div id=\"writing\" style=\"text-align:center; width: 80%; margin: auto;\">"
			    				+ "<h4 style=\"text-align: center\">Email: " + email + 
			    				"</h4><h4 style=\"text-align: center\">Card Number: " + cardNo + "</h4>"+ 
			    				"<p style=\"text-align: center\">Your account will expire on " + exp + ". To renew your card, please visit your nearest library branch.</p>"
			    				+ "<form action=\"http://3.145.193.254:8080/LibraryBR/CardLogout\" method=\"post\" style=\"width: 60%; margin: auto;\"><button type=\"submit\">Logout</button></form>"
			    			+ "</div><br><br>" + 
		    				"<div style=\"display:flex;width: 80%;margin: auto;\">" +
		    					"<div style=\"width: 50%; padding: 10px; text-align:center; margin: auto;\">"
		    						+ "<h3 style=\"text-align: left;\">Loans</h3>"
		    						+ "<div style=\"width: 85%; grid-template-columns: repeat(3, 1fr); grid-template-rows: repeat(1, 1fr); display: grid;\">" 
		    							+ loans + "</div>"
		    					+ "</div><br><br>" + 
		    					"<div style=\"width: 50%; padding: 10px; text-align:center; margin: auto;\">"
		    						+ "<h3 style=\"text-align: left;\">Reservations</h3>"
		    						+ "<div style=\"width: 85%; grid-template-columns: repeat(3, 1fr); grid-template-rows: repeat(1, 1fr); display: grid;\">" 
		    							+ reservations
		    						+ "</div>"
		    					+ "</div>" + 
		    				"</div><br><br>" + 
		    				"<div style=\"display:flex;width: 80%;margin: auto;\">" +
	    						"<div style=\"width: 50%; padding: 10px; text-align:center; margin: auto;\">"
	    							+ "<h3 style=\"text-align: left;\">Reading List</h3>"
	    							+ "<div style=\"grid-template-columns: repeat(3, 1fr); grid-template-rows: repeat(1, 1fr); display: grid;\">" 
	    								+ readingLists 
	    							+ "</div>"
	    						+ "</div><br><br>" + 
	    						"<div style=\"width: 50%; padding: 10px; margin: auto;\">"
	    							// + "<h3 style=\"text-align: left;\">Events</h3>" + events + 
	    						+ "<h3 style=\"text-align: left;\">Suggested Books</h3>" + suggests + 
	    						"</div>" + 
	    					"</div><br><br>" + 
		    				"<div style=\"display:flex; width: 80%; margin: auto;\">" +
		    					"<div style=\"width: 50%; padding: 10px; margin: auto;\">"
		    						+ "<h3 style=\"text-align: left;\">Meeting Room Reservations</h3>" + meetings 
		    					+ "</div>" + //<br><br>" + 
		    					"<div style=\"width: 50%; padding: 10px; margin: auto;\">"
		    						+ "<h3 style=\"text-align: left;\">Study Room Reservations</h3>" + study 
		    					+ "</div>" + 
		    				"</div><br><br>" + 
		    					
		    				"<div style=\"display:flex; width: 80%; margin: auto; padding-bottom: 2%;\">" +
		    					"<div style=\"width: 50%; padding: 10px; margin: auto;\">"
		    						+ "<h3>Events</h3>" + events 
		    					+"</div>" + 
		    					"<div style=\"width: 50%; padding: 10px; margin: auto;\"></div>" + 
		    				"</div><br><br><br><br>"
							    				
		    			;
		    s.append(ht);
		    s.append(footer);
		   
		    out.println(s);
		} else {
			
			String loginInfo = "<div style=\"padding-bottom: 5%; padding-top: 5%\">"
				+ "<h2 style=\"text-align: center\">Welcome back!</h2>"
				+ "<fieldset style=\"width: 30%; margin: auto\">"
				+ "<form action=\"http://3.145.193.254:8080/LibraryBR/CardLogin\" method=\"post\" style=\"width: 60%; margin: auto;\">"
				+ "<br><label for=\"cardNum\" style=\"padding-right: 10px;\">Card Number:</label><input name=\"cardNum\" type=\"text\">"
				+ "<br><label for=\"accountPW\" style=\"padding-right: 35px\">Password:</label><input name=\"accountPW\" type=\"password\">"
				+ "<br><br><br>"
				+ "<label style=\"padding-left: 35%;\"><input type=\"submit\" value=\"Login\" name=\"login\" style=\"width: 30%;\"/>"
				+ "</form></fieldset>"
				+ "<p style=\"text-align: center\">No card? <a href=\"18.222.29.185/LibraryBR/userRegistration.html\" id=\"noReg\" onclick=\"replaceHref('noReg')\">Sign up</a></p>"
			+ "</div>";
			
			
			out.println(htmlInfo + loginInfo + footer);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
