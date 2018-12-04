package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
	private String subDir = "data";
	private String filePath;
	private File filePathDir;

	public void initFile() {
		String separator = File.separator;
		filePath = this.getServletContext().getRealPath(separator) + subDir + separator;
		filePathDir = new File(filePath);
		if (!filePathDir.exists()) {
			filePathDir.mkdirs();
		}
		//System.out.println(filePath);
	}
	private void displayError(HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("text/html");
		try {
			// Here we initialize the PrintWriter object
			PrintWriter out = response.getWriter();
			// Here we print HTML tags
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String separator = System.getProperty("file.separator");
		// String privateFilePath =
		// "/WEB-INF/private_files/".replace(File.separatorChar, '/');
		// String absolutePath = getServletContext().getRealPath(separator) +
		// separator;
		// In the following we initialize necessary objects
		initFile();
		URL url = null;
		URLConnection urlConnection = null;
		PrintWriter printWriter = null;
		BufferedReader reader = null;
		printWriter = response.getWriter();
		String siteName = request.getParameter("site_name");
		String fileName = request.getParameter("fileName");
		try {
			 DataOutputStream out = new DataOutputStream(new FileOutputStream(filePath+fileName));
			// Here we access the web resource within the web application
			// as a URL object
			// url = getServletContext().getResource(filePath);
			url = new URL(siteName);
			//String searchPhrase = request.getParameter("search_phrase");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-16");
			
			urlConnection = url.openConnection();
			// Here we establish connection with URL representing web.xml
			urlConnection.connect();
			// The following would be useful to read data in binary format
			
			  BufferedInputStream inputStream = new BufferedInputStream(urlConnection.getInputStream()); 
			  int readByte; 
			  while((readByte=inputStream.read())!=-1)
			  {
				  //printWriter.write(readByte);
				  out.writeByte(readByte);
			  }
			  out.close();
			  printWriter.println("Your content has been saved in : " + filePath+fileName);
			  printWriter.println("<hr><center><a href='index.html'>Back</a></center>");
			  printWriter.close();
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