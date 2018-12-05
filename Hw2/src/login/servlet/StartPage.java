package login.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class startpage
 */
public class StartPage extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String reply = request.getParameter("reply");
		String userName = request.getParameter("name");
		//System.out.print(reply);
		response.setContentType("text/html");
		// Here we use a PrintWriter to send text data
		// to the client who has requested the servlet
		PrintWriter out = response.getWriter();
		out.println("<html><head>");
		out.println("<title>Login page</title></head><body>");
		out.println("<form method='POST' + action='login'>");
		out.println("<table>");
		out.println("<tr><td>User Name</td><td><input type=\"text\" size=\"40\" name=\"name\" ></td></tr>");
		out.println("<tr><td>Password</td><td><input type=\"password\" size=\"40\" name=\"password\" ></td></tr>");
		out.println("<tr><td></td><td><input type=\"submit\" VALUE=\"Login\"> </td></tr>");
		out.println("</table>");
		if(reply != null && !reply.isEmpty())
			out.println("<div>" + reply+ "</div>");
		if(userName  != null && !userName.isEmpty())
		{
			out.println("<center>");
			out.println("<div> Welcome user: "+userName+"</div>");
			out.println("</center>");
		}
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
