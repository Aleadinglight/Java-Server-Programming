//This is the content of servlet/form/UserForm.java file.
package servlet.form;

import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserFormServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		// This is to check whether the request comes from feedback.html page.
		String feedbackReturn = request.getParameter("fbReturn");
		// Here we set the MIME type of the response, "text/html"
		response.setContentType("text/html");
		// Here we use a PrintWriter to send text data
		// to the client who has requested the servlet
		PrintWriter out = response.getWriter();
		// Here we start assembling the HTML content
		out.println("<html><head>");
		out.println("<title>User Registeration Page</title></head><body>");
		out.println("<center>");
		out.println("<h2>Please submit your information</h2>");
		// Here we set the value for method to post, so that
		// the servlet service method calls doPost in the
		// response to this form submit
		out.println("<form method='GET' + action='index.html'>");
		out.println("<table border='0'><tr><td valign='top'>");
		out.println("Your first name: </td>");
		out.println("<td valign='top'><input type='text' name='first_name' value='"
				+ (checkParameter(firstName) ? firstName : "") + "' size='20'>");
		out.println("</td></tr><tr><td valign='top'>");
		out.println("Your last name: </td>");
		out.println("<td valign='top'><input type='text' name='last_name' value='"
				+ (checkParameter(lastName) ? lastName : "") + "' size='20'>");
		out.println("</td></tr><tr><td valign='top'>");
		out.println("Your email: </td>");
		out.println("<td valign='top'><input type='text' name='email' value='"
				+ (checkParameter(email) ? email : "") + "' size='20'>");
		out.println("</td></tr><tr><td valign='top'>");
		out.println("<input type='submit' value='Submit Info'></td></tr>");
		out.println("</table></form>");
		// Here we initialize the RequestDispatcher object.
		RequestDispatcher dispatcher = request
				.getRequestDispatcher("summary.html");
		// Here we include summary.html page to the current page.
		if (checkParameter(firstName) && checkParameter(lastName)
				&& checkParameter(email)) {
			out.println("<hr>");
			dispatcher.include(request, response);
		}
		// Here we make sure that not all fields are empty at the same time
		boolean A = !(checkParameter(firstName) && checkParameter(lastName) && checkParameter(email));
		// Here we make sure that at least one field is no empty
		boolean B = (checkParameter(firstName) || checkParameter(lastName) || checkParameter(email));
		// Here we forward the request to feedback.html page.
		if (A && B && (feedbackReturn == null || feedbackReturn.equals(""))) {
			dispatcher = request.getRequestDispatcher("feedback.html");
			dispatcher.forward(request, response);
			return;
		}
		out.println("</center>");
		out.println("</body></html>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		doGet(request, response);
	}

	private boolean checkParameter(String parameter) {
		if (parameter != null && !parameter.equals(""))
			return true;
		return false;
	}
}