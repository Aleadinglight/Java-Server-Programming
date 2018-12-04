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
		downloadDir = this.getServletContext().getRealPath(getServletContext().getInitParameter("download-dir"))
				+separtor;
	}
	private void sendHTMLErrorMessage(HttpServletRequest request, HttpServletResponse response, String error) {
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
	//This function will get contain type from a file name
	private String getContentType(String fileName) {
		String fileExt = ""; //file extension
		String contentType = "";
		int i;
		if ((i = fileName.indexOf(".")) != -1) {
			/*
			 * Here we read s substring of filename starting from ".", which will be the
			 * file extension
			 */
			fileExt = fileName.substring(i);
		}
		if(fileExt.equals("doc") || fileExt.equals("docx"))
			contentType = "application/msword";
		else if(fileExt.equals("xml"))
			contentType = "text/xml";
		else if(fileExt.equals("txt"))
			contentType = "text/plain";
		else
			contentType = "application/unknown";
		return contentType;
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String error = "";
		File downloadFile = new File(downloadDir + fileName);
		if (!downloadFile.exists()) {
			// throw new
			// ServletException("Invalid or non-existent 'pdf-dir context-param!");
			error = fileName + " does not exist!";
			sendHTMLErrorMessage(request, response, error);
			return;
		}
		ServletOutputStream outStream = null;
		BufferedInputStream bufferedInputStream = null;
		try {
			outStream = response.getOutputStream();
			response.setContentType(getContentType(fileName));
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setContentLength((int) downloadFile.length());
			
			bufferedInputStream = new BufferedInputStream(new FileInputStream(downloadFile));
			int readBytes = 0;
			// Here we read from the file and write to the ServletOutputStream
			while ((readBytes = bufferedInputStream.read()) != -1)
				outStream.write(readBytes);
		}catch (IOException ioex) {
			error = ioex.getMessage();
			sendHTMLErrorMessage(request, response, error);
		}finally {
			if(outStream != null)
				outStream.close();
			if (bufferedInputStream != null)
				bufferedInputStream.close();
		}
	}

}
