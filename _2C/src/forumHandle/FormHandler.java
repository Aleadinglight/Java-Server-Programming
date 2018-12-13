package forumHandle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormHandler extends HttpServlet {
	private String subDir = "data";
	private String filePath;
	private String fileName = "data.txt";
	private File filePathDir;

	public void initFile() {
		String separator = File.separator;
		filePath = this.getServletContext().getRealPath(separator) + subDir + separator;
		filePathDir = new File(filePath);
		if (!filePathDir.exists()) {
			filePathDir.mkdirs();
		}
		filePath += fileName;
		System.out.println(filePath);
	}

	public void getDataFromFile() {
		boolean cont = true;
		FileInputStream fin;
		try {
			fin = new FileInputStream(filePath);
			ObjectInputStream input = new ObjectInputStream(fin);
			while (cont) {
				message obj = (message) input.readObject();

				if (obj != null)
					message.allMessages.add(obj);
				else
					cont = false;
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/*
	 * public void WriteToFile(message M) { String text = M.toString(); char
	 * buffer[] = new char[text.length()]; text.getChars(0, text.length(), buffer,
	 * 0); FileWriter fw; try { fw = new FileWriter(filePath, true);
	 * System.out.println("Write successful!\n"); fw.write(buffer); fw.close(); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); System.out.println("Invalid file path"); }
	 * 
	 * }
	 */
	public void WriteToFile(message M) {
		try {
			FileOutputStream f = new FileOutputStream(new File(filePath));
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(M);
			System.out.println("write successful !\n");
			o.close();
			f.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void returnHTML(PrintWriter out, List<message> messageList, String cssTag) {
		out.println("<html>");
		out.println("<head>");
		out.println(cssTag);
		out.println("</head>");
		out.println("<body>");
		out.println("<p> All message in this forum: </p>");
		out.println("<table>");
		out.println(message.messageListToString(messageList));
		out.println("</table>");
		out.println("<a href='forum.html'>Back</a>");
		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initFile();
		if(message.allMessages.isEmpty())
			getDataFromFile();
		try {
			message.initializeList();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// CSS style of this servlet
		String cssLocation = request.getContextPath() + "/css/style.css";
		String cssTag = "<link rel='stylesheet' type='text/css' href='" + cssLocation + "'/>";
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// action identify
		String action = request.getParameter("action");
		if (action.equalsIgnoreCase("submit")) {
			// Here we add current message to the list
			String inUsername = request.getParameter("userName");
			String inName = request.getParameter("name");
			System.out.println(inName);
			String inMessage = request.getParameter("message");
			String[] inSport = request.getParameterValues("Sports");
			String[] inView = request.getParameterValues("views");
			message currentMessage = new message(inUsername, inMessage, inSport, inView);
			message.allMessages.add(currentMessage);
			WriteToFile(currentMessage);
			/* return result to client */
			returnHTML(out, message.allMessages, cssTag);
		} else if (action.equalsIgnoreCase("Search user")) {
			// Here we search message according to user name
			String inUsername = request.getParameter("searchName");
			returnHTML(out, message.search_user(inUsername), cssTag);
		} else if (action.equalsIgnoreCase("Search date")) {
			try {
				String inDate = request.getParameter("searchDate");
				returnHTML(out, message.search_date(inDate), cssTag);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				out.println("ERROR");
				out.println("<a href='forum.html'>Back</a>");
				e.printStackTrace();
			}
		}
		out.close();
	}

}
