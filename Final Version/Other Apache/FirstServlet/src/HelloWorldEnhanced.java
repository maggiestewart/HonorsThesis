

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloWorldEnhanced
 */
public class HelloWorldEnhanced extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloWorldEnhanced() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String location = request.getParameter("location");
		String gender = request.getParameter("gender");
		String experience = request.getParameter("experience");
		
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		out.println(docType + 
				"<html>\n" 
				+ "<head><title>User registration</title></head>\n" + 
				"<body>\n" + 
				"<h1 align=\"center\">Welcome " + name + "</h1>" +
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
