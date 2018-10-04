package servlet.feedback;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FeedbackServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Here we set the MIME type of the response, "text/html"
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String returnParameter = "?first_name=" + firstName + "&last_name="
				+ lastName + "&email=" + email + "&fbReturn=true";
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		// Here we start assembling the HTML content
		out.println("<html><head>");
		out.println("<title>User Registeration Feedback</title></head><body>");
		out.println("<div align=center>");
		out.println("<h2 style='text-align:center'>Feedback</h2>");
		// Here we set the value for met
		Date today = new Date();
		out.println("<hr>Some error happened!<br>");
		out.println("<p>Today is: " + today);
		out.println("<p>Your first name is: "
				+ (checkParameter(firstName) ? firstName : "<b>Not known</b>"));
		out.println("<p>Your last name is: "
				+ (checkParameter(lastName) ? lastName : "<b>Not known</b>"));
		out.println("<p>Your email is: "
				+ (checkParameter(email) ? email : "<b>Not known</b>"));
		out.println("<br>");
		out.println("<a href='index.html" + returnParameter + "'>Back</a>");
		// out.println("<a href='index.html'>Back</a>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

	private boolean checkParameter(String parameter) {
		if (parameter != null && !parameter.equals(""))
			return true;
		return false;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}