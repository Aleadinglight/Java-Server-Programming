package submit.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import submit.message.Post;

public class SubmitMessageServlet extends HttpServlet {
	static ArrayList<Post> ForumPosts = new ArrayList<Post>();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // These come from the html form
		String userName=request.getParameter("userName");
		String message=request.getParameter("message");
		GregorianCalendar calendar=new GregorianCalendar();
		// Create timestamp
		String timestamp = ""+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR)
		 			+" at "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
		
		forumMessage += "<fieldset><legend><b> " + userName + "</b><i> says </i> </legend>" + message + "<br><small><i>" +timestamp+ "</i></small></fieldset><br>";
		// Set the response content
		response.setContentType("text/html");
	  
		PrintWriter out=response.getWriter();
	  
		out.println("<html>");
		out.println("<body>");
		out.println(forumMessage);
		out.println("<a href='index.html'>Back</a>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}