

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterCardServlet
 */
public class RegisterCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterCardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String first = request.getParameter("firstName");
		String last = request.getParameter("lastName");
		String pronouns = request.getParameter("pronouns");
		String birthday = request.getParameter("dob");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String message = "";
        
        if(first.isEmpty() || last.isEmpty() || birthday.isEmpty() || pronouns.isEmpty() || email.isEmpty() || password.isEmpty()){
        	message = "<p>Registration not complete. Please try again. </p>";
        } else {
        	// insert into sql table
        	message = "<p>Registration complete! Login <a href=\"" + "userLogin.html\">here</a></p>";
        }
        
        response.setContentType("text/html; charset=UTF-8");
		final PrintWriter out = response.getWriter();
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		String htmlInfo = docType + "<html>"+
    			"<head><title>LibraryBR</title></head>"+
    			"<body>\n" + 
    				"<h2><center>Welcome " + first + " " + last + "</h2>" +
    				message + 
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
