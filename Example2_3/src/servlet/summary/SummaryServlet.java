//This is the content of servlet/summary/SummaryServlet.java file.
package servlet.summary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SummaryServlet extends HttpServlet {
	String fileName = "registry.txt";
	String subDirName = "info" ;
	String filePath;
	String separator;
	File filePathDir;
	FileWriter fileWriter;
	FileReader fileReader;

	SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");

	public void init() {
		// Here we get the system file separator, like / or \
		separator = System.getProperty("file.separator");
		// Here we get the absolute path to info subdirectory under
		// the servlet's main directory.
		filePath = this.getServletContext().getRealPath(separator) + subDirName
				+ separator;
		// Here we create a file object, which refers to
		// the registry file.
		filePathDir = new File(filePath);
		// Here we check whether the file exists or not.
		// if not, an empty file with teh given name will
		// be created.
		if (!filePathDir.exists()) {
			filePathDir.mkdir();
		}

		filePath += fileName;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Date today = new Date();
		out.println("<h2>Summary of user registration</h2>");
		out.println("<br>Good day there...!<br>");
		out.println("<p>Today is: " + today + "<p>");
		out.println("<p>Your first name is: "
				+ request.getParameter("first_name") + "<p>");
		out.println("<p>Your last name is: "
				+ request.getParameter("last_name") + "<p>");
		out.println("<p>Your email is: " + request.getParameter("email")
				+ "<p>");

		// Here we create an entry of user data.
		String entry = "<tr><td>" + sdf.format(new Date()) + "</td><td>"
				+ request.getParameter("first_name") + "</td><td>"
				+ request.getParameter("last_name") + "</td><td>"
				+ request.getParameter("email") + "</td></tr>";
		out.println("<hr>");
		// Here we write the entry into the file.
		fileWriter = new FileWriter(filePath, true);
		fileWriter.write(entry);
		fileWriter.close();
		// Here we read data from the file
		fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String line;
		out.println("<p style='text-align:center'>Summary of All Users</p>");
		out.println("<table border='1'>");
		out.println("<tr><th>Date</th><th>First name</th><th>Last name</th><th>Email</th><tr>");

		while ((line = bufferedReader.readLine()) != null)
			out.println(line);

		out.println("</table>");
		bufferedReader.close();
	}
}