package search.forum;

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
import submit.message.SubmitMessageServlet;

public class SearchDate extends HttpServlet{
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Post> ForumPosts = SubmitMessageServlet.getForumPosts();
		ArrayList<Post> SearchedPosts = new ArrayList<Post>();
		String date=request.getParameter("date");
		for (int i=0; i<ForumPosts.size(); i++){
			if (ForumPosts.get(i).getTimeStamp().equals(date)){
				SearchedPosts.add(ForumPosts.get(i));
			}
		}
		
		response.setContentType("text/html");
		  
		PrintWriter out=response.getWriter();
	  
		out.println("<html>");
		out.println("<body>");
		if (SearchedPosts.size()==0){
			out.println("No information found!<br>");
		}
		else{
			for (int i = 0; i < SearchedPosts.size(); i++) {
				out.println("<fieldset><legend><b> " + SearchedPosts.get(i).getName() + "</b><i> says </i> </legend>" + SearchedPosts.get(i).getMessage() 
						+ "<br><small><i>" +SearchedPosts.get(i).getTimeStamp()+ "</i></small></fieldset><br>");
			}
		}
		out.println("<a href='index.html'>Back</a>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
