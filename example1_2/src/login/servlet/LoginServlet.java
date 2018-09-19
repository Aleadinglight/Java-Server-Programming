package login.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
 
 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // These come from the html form
		 String userName=request.getParameter("userName");
		 String password=request.getParameter("password");
	 
		 String message=null;
		 GregorianCalendar calendar=new GregorianCalendar();
		 
		 if(calendar.get(Calendar.AM_PM)==Calendar.AM) {
			 message="Good Morning";
	   
		 }
		 else {
			 message="Good Afternoon";
		 }
	  
		 // Set the response content
		 response.setContentType("text/html");
	  
		 PrintWriter out=response.getWriter();
	  
	  
		 out.println("<html>");
		 out.println("<body>");
		  
		 if(userName.equals("root") && password.equals("admin")) {
			 out.println("<p>" + message + ", " + userName + "</p>");
		 } 
		 else {
			 out.println("<p>" + message+ "! "+ userName + " not known! </p>"); 
		 } 
		 out.println("<a href='index.html'>Back</a>");
		 out.println("</body>");
		 out.println("</html>");
		 out.close();
	}
}