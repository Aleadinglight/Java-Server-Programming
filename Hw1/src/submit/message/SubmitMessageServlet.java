package submit.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitMessageServlet extends HttpServlet {
 
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // These come from the html form
		 String userName=request.getParameter("userName");
		 String message=request.getParameter("message");
	 
		 
		 GregorianCalendar calendar=new GregorianCalendar();
		 /*
		 if(calendar.get(Calendar.AM_PM)==Calendar.AM) {
			 message="Good Morning";
	   
		 }
		 else {
			 message="Good Afternoon";
		 } */
	  
		 // Set the response content
		 response.setContentType("text/html");
	  
		 PrintWriter out=response.getWriter();
		 //git commit --allow-empty --date="Mon Oct 8 10:00 2018 +0100" -m "added submitmessage method"
	  
		 out.println("<html>");
		 out.println("<body>");
		  
		 
		 out.println("<fieldset><legend><b> " + userName + "</b><i> says at" + calendar + " </i> </legend>" + message + "<fieldset>");
		 
		 out.println("<a href='index.html'>Back</a>");
		 out.println("</body>");
		 out.println("</html>");
		 out.close();
	}
}