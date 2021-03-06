package download;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EntrancePage extends HttpServlet {
	String separator = File.separator;
	String downloadDir = "";
	
	@Override
	public void init() {
		// Initiate a directory for downloading contents
		downloadDir = this.getServletContext().getRealPath(getServletContext().getInitParameter("download-dir"))
				+separator;
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File dir = null;
		String []fileList;
	      try {    
	         // create new download directory
	         dir = new File(downloadDir);                     
	         // array of files and directory
	         fileList = dir.list(); 
	          
	  		response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html><head><meta charset='ISO-8859-1'><title>Download list</title></head>");
			out.println("<body><form action = 'downloadServlet' method='POST'><table>");
			out.println("<tr><td>Download file</td></tr>");
			out.println("<tr><td><select name='fileName' style = 'width:200px'>");
			for(String currentFile:fileList){
				out.println("<option value='"+currentFile+"'> "+currentFile+"</option>");
			}
			out.println("</td></tr><tr><td><input type='submit' value='Download'></td></tr></table>");
			out.println("</form></body></html>");
	       } catch(Exception e) {
	          // if any error occurs
	          e.printStackTrace();
	       }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			doGet(request, response);
	}

}
