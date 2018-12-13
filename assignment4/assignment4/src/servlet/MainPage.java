package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainPage
 */
@WebServlet("/MainPage")
public class MainPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String fileRepositoryPath;
	String subDir = "";
	String separator = System.getProperty("file.separator");
	@Override
	public void init() {

		/*
		 * Below we set the path for the directory where uploaded files are saved
		 * 
		 * getServletContext().getRealPath(separator) returns the path to the root
		 * directory of
		 * 
		 * the servlet. Variable separator indicates the directory separator on the
		 * system.
		 */
		subDir = getServletContext().getInitParameter("upload_dir");
		fileRepositoryPath = getServletContext().getRealPath(separator) + separator + subDir + separator;
		// Here we make sure to create the file repository directory.
		File repoDirFile = new File(fileRepositoryPath);
		if (!repoDirFile.exists())
			repoDirFile.mkdir();
	}
    public void readMessage(String filePath){
    	boolean cont = true;
		FileInputStream fin;
		message.newMessageList();
		try {
			fin = new FileInputStream(filePath);
			ObjectInputStream input = new ObjectInputStream(fin);
			while (cont) {
				message obj = (message) input.readObject();
				if (obj != null)
					message.addNewMessageToList(obj);
				else
					cont = false;
			}
			input.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		readMessage(fileRepositoryPath+"data.txt");
		String cssLocation = request.getContextPath() + "/css/style.css";
		String cssTag = "<link rel='stylesheet' type='text/css' href='" + cssLocation + "'/>";
		response.setContentType("text/html");
		// Here we use a PrintWriter to send text data
		// to the client who has requested the servlet
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html><head>");
		out.println(cssTag);
		out.println("<title>Main Page</title></head><body>");
		if(!message.messageListIsEmpty()) {
			out.println(message.getMessageListAsHTMLTag());
		}
		out.println("<a href='fileUpload.html'>Upload Your own comment now!</a>");
		out.println("<h1>Search for user</h1>");
		out.println("<form action=index.html method='POST'>");
		out.println("<table>"+
		"<tr><td>User Name: </td> "+
		"<td><input type='text' size='40' name='searchName'></td></tr>");
		out.println("<tr><td></td><td>"+
		"<input type='submit' name='action' VALUE='Search user'> </td></tr></table>");
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String cssLocation = request.getContextPath() + "/css/style.css";
		String cssTag = "<link rel='stylesheet' type='text/css' href='" + cssLocation + "'/>";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html><head>");
		out.println(cssTag);
		out.println("<title>Search result</title></head><body>");
		String searchName=request.getParameter("searchName");
		if(searchName.isEmpty()) {
			out.println("The search entry is empty<br>");
		}
		else {
			out.println("Search entry: "+searchName+"<br>");
			out.println(message.searchName(searchName));
		}
		out.println("<a href = 'index.html'>Back</a>");
		out.println("</body></html>");
	}

}
