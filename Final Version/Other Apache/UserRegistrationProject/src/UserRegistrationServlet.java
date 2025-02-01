import java.io.*;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class UserRegistrationServlet
 */
@WebServlet("/UserRegistrationServlet")
@MultipartConfig
public class UserRegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegistrationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	        String name = request.getParameter("name");
	        String email = request.getParameter("email");
	        String location = request.getParameter("location");
	        String gender = request.getParameter("gender");
	        String experience = request.getParameter("experience");
	        String fileName = request.getParameter("fileName");
	        
	        response.setContentType("text/html; charset=UTF-8");
			final PrintWriter out = response.getWriter();
	        
			/*	
	         * request.getPart is to get the uploaded file handler. 
	         * You can use filePart.getInputStream() to read the streaming data from client, for example:
	         * InputStream filecontent = filePart.getInputStream();
	        */
		    Part filePart = request.getPart("file");
		    InputStream filecontent = filePart.getInputStream();
		    
		    /*
		     * fileout is for you to save the uploaded picture in your local disk. 
		     * */
  		    //OutputStream fileout;
  		    
		    	    
		    /*
		     * Write your code here
		     * Step 1: check whether the client's inputs are complete or not; if anything is missing, return a web page that contains a link to go back to the registration page (e.g., UserRegistration.html)
		     * Step 2: save the uploaded picture under your project WebContent directory, for example, mine is "F:\workspace\UserRegistrationProject\WebContent". 
		     * Step 3: send back the client's registration information to the client, remember, the client should be able to see all the information, including the profile picture. 
		     * */
		    String docType = "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		    if (name.isEmpty() || email.isEmpty() || location.isEmpty() || gender.isEmpty() || experience.isEmpty() || fileName.isEmpty() || filePart == null){
		    
		    	
				out.println(docType + 
						"<html>\n" 
						+ "<head><title>User registration</title></head>\n" + 
						"<body>\n" + 
						"<p>Your input information is not complete, try again </p>" + "<a href=\"http://13.58.154.60:8080/UserRegistrationProject/UserRegistration.html\">go back</a>"
						+ "</body></html>");
			} else {
				// save uploaded photo to project directory (pics folder)
				// send registration info back to client
				String fileLocation = "C:\\Users\\Administrator\\workspace\\UserRegistrationProject\\WebContent\\pic\\" + fileName;
				File f = new File(fileLocation);
				
				OutputStream fileout = new FileOutputStream(f);
				byte[] buffer = new byte[2500000];
				filecontent.read(buffer);
				fileout.write(buffer);
				fileout.flush();
							
				try { 
					/*
					 * I've tried to fix this so that the photo loads when the page loads but it still 
					 * doesn't load the photo on the webpage after it is rendered even when I changed
					 * the time delay. Please reload the page (a few times if necessary) if the image doesn't 
					 * show up the first time!!
					 */
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				response.setContentType("text/html; charset=UTF-8");
			    String htmlInfo = docType + "<html>"+
			    			"<head><title>User Registration</title></head>"+
			    			"<body>\n" + 
			    				"<h2><center>Welcome " + name + "</h2>" +
			    				"<ul><li><strong>Your name: </strong>" + name +"\n" + 
				    			"<li><strong>Your email: </strong>" + email +"\n" + 
				    			"<li><strong>Your location: </strong>" + location +"\n" + 
				    			"<li><strong>Your gender: </strong>" + gender + "\n" + 
				    			"<li><strong>Your experience: </strong>" + experience +"\n" +
				    			"<li><strong>Your profile picture " + fileName + " has been uploaded successful: " + "\n" + 
				    			"<p><img src='pic/" + fileName + "'></p>" + "\n" +
				    			"</ul></body></html>"	
			    			;
			    
			   
			    out.println(htmlInfo);
			    fileout.flush();
			    out.flush();
			    fileout.close();
			    out.close();
			    filecontent.close();
			}
		      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
