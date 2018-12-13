package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewResourceServlet extends HttpServlet {
	String error = "";
	
	public static int countKeyword(String haystack, String regexToFind) {
		Pattern p = Pattern.compile(regexToFind);
		Matcher m = p.matcher(haystack); // Matcher object
		int count = 0;
		while(m.find()) {
			count++;
		}
		return count;
	}
	
	private void displayError(HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("text/html");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<title>View Resource Servlet Error Message</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<center>");
			out.println("<h1>Error</h1>");
			out.println("<p><b>Error:</b> " + error);
			out.println("<p><a href='index.html'>Back</a>");
			out.println("</center>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		URL url = null;
		URLConnection urlConnection = null;
		PrintWriter printWriter = null;
		BufferedReader reader = null;
		printWriter = response.getWriter();

		try {
			
			url = new URL(request.getParameter("site_name"));
			String searchPhrase = request.getParameter("search_phrase");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-16");

			urlConnection = url.openConnection();

			urlConnection.connect();
			
			reader = new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
			String line = "";
			String fileContent = "";
			while ((line = reader.readLine()) != null) {
				fileContent += line;
			}
			int count = countKeyword(fileContent, searchPhrase);
			int selectedIndex = -1;
			if (!searchPhrase.equals(""))
				selectedIndex = fileContent.indexOf(searchPhrase);
			if (selectedIndex != -1) {
				printWriter.write("<p>Search result for " + searchPhrase
						+ " from " + url.toString() + ":<br>");
				// Here we select the content starting from found search phrase
				printWriter.write(fileContent.substring(selectedIndex - 1));
			} else {
				printWriter.write("<p>The content of " + url.toString()
						+ "</p><br>:");
				printWriter.write(fileContent);
			}
			printWriter.println("<p>The string: "+searchPhrase+" appears: "+count+" time(s)</p>");
			printWriter
					.println("<hr><center><a href='index.html'>Back</a></center>");
		} catch (Exception e) {
			error = "Something wrong with: " + url.toString() + " " + e;
			displayError(request, response);
		} finally {
			// Here we close the input/output streams
			if (printWriter != null)
				printWriter.close();
			if (reader != null) {
				reader.close();
			}
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Here we redirect the request to the index page
		response.sendRedirect("index.html");

		return;
	}
}