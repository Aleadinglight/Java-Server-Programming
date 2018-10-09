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
		// Create time stamp
		String timeStamp = ""+calendar.get(Calendar.DATE)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.YEAR)
		 			+" at "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
		Post newMessage = new Post(userName, message, timeStamp);
		ForumPosts.add(newMessage);
		
		// Set the response content
		response.setContentType("text/html");
	  
		PrintWriter out=response.getWriter();
	  
		out.println("<html>");
		out.println("<body>");
		for (int i = 0; i < ForumPosts.size(); i++) {
			out.println("<fieldset><legend><b> " + ForumPosts.get(i).getName() + "</b><i> says </i> </legend>" + ForumPosts.get(i).getMessage() 
					+ "<br><small><i>" +ForumPosts.get(i).getTimeStamp()+ "</i></small></fieldset><br>");
		}
		out.println("<a href='index.html'>Back</a>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}