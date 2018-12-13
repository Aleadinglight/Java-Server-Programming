package download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {
	String separtor = File.separator;
	String downloadDir;
	@Override
	public void init() {
		//We prepare the download directory
		downloadDir = this.getServletContext().getRealPath(getServletContext().getInitParameter("download-dir"))+separtor;
	}
	private void errorHandling(HttpServletRequest request, HttpServletResponse response, String error) {
		response.setContentType("text/html");
		try {
			// Here we initialize the PrintWriter object
			PrintWriter out = response.getWriter();
			// Here we print HTML tags
			out.println("<html>");
			out.println("<head>");
			out.println("<title>File Download Error Message</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Error</h1>");
			out.println("<p><b>Error:</b> " + error);
			out.println("<p><a href='index.html'>Back</a>");
			out.println("</body>");
			out.println("</html>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Get file extension
	private String getFileType(String fileName) {
		String fileExt = ""; 
		int i;
		// Get the substring after "."
		if ((i = fileName.indexOf(".")) != -1) {
			fileExt = fileName.substring(i);
		}
		
		if(fileExt.equals("doc") || fileExt.equals("docx"))
			return "application/msword";
		else if(fileExt.equals("xml"))
			return "text/xml";
		else if(fileExt.equals("txt"))
			return "text/plain";
		return "application/unknown";
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String error = "";
		File downloadFile = new File(downloadDir + fileName);
		if (!downloadFile.exists()) {
			// throw new ServletException("Invalid or non-existent 'pdf-dir context-param!");
			error = fileName + " does not exist!";
			errorHandling(request, response, error);
			return;
		}
		
		ServletOutputStream outStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			outStream = response.getOutputStream();
			response.setContentType(getFileType(fileName));
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setContentLength((int) downloadFile.length());
			
			bufferedInputStream = new BufferedInputStream(new FileInputStream(downloadFile));
			int readBytes = 0;
			// Read from file and write to the ServletOutputStream
			while ((readBytes = bufferedInputStream.read()) != -1)
				outStream.write(readBytes);
		} 
		catch (IOException ioex) {
			error = ioex.getMessage();
			errorHandling(request, response, error);
		}
		finally {
			if(outStream != null)
				outStream.close();
			if (bufferedInputStream != null)
				bufferedInputStream.close();
		}
	}

}
