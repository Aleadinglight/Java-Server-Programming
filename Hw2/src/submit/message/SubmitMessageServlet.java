package submit.message;

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

import java.util.ArrayList;
import submit.message.Post;

public class SubmitMessageServlet extends HttpServlet {
	private String subDir = "data";
	private static String filePath;
	private String fileName = "data.txt";
	private File filePathDir;
	
	// Initialize the file
	public void initFile() {
		String separator = File.separator;
		filePath = this.getServletContext().getRealPath(separator) + subDir + separator;
		filePathDir = new File(filePath);
		if (!filePathDir.exists()) {
			filePathDir.mkdirs();
		}
		filePath += fileName;
	}
	
	// SavePostsToFile
	public void SavePostsToFile(ArrayList<Post> ForumPosts) {
		try {
			FileOutputStream f = new FileOutputStream(new File(filePath));
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject(ForumPosts);
			System.out.println("Save successfully !\n");
			o.close();
			f.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void DeleteAllPosts() {
		try {
			FileOutputStream f = new FileOutputStream(new File(filePath));
			ObjectOutputStream o = new ObjectOutputStream(f);

			o.writeObject("");
			System.out.println("Save successfully !\n");
			o.close();
			f.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Get forum posts 
	static public ArrayList<Post> getForumPosts() {
		boolean cont = true;
		FileInputStream fin;
		try {
			fin = new FileInputStream(filePath);
			ObjectInputStream input = new ObjectInputStream(fin);
			while (cont) {
				ArrayList<Post> obj = (ArrayList<Post>) input.readObject();

				if (obj != null){
					return obj;
				}
				else
					cont = false;
			}
			input.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}
	
	protected void returnHTML(PrintWriter out) {
		ArrayList<Post> ForumPosts = getForumPosts();
		out.println("<html>");
		out.println("<body>");
		for (int i = 0; i < ForumPosts.size(); i++) {
			out.println("<fieldset><legend><b> " + ForumPosts.get(i).getName() + "</b><i> says </i> </legend>" + ForumPosts.get(i).getMessage() 
					+"<br>Favorite Sports: "+ ForumPosts.get(i).getFavoriteSports()+"<br>Favorite views: "+  ForumPosts.get(i).getFavoriteView() +"<br><small><i>" +ForumPosts.get(i).getTimeStamp()+ "</i></small></fieldset><br>");
		}
		out.println("<a href='forum.html'>Back</a>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Init file
		initFile();
		//DeleteAllPosts();
		// Retrieve past data
		ArrayList<Post> ForumPosts = getForumPosts();
		if (ForumPosts == null)
			ForumPosts = new ArrayList<Post>();
		
		// These come from the html form
		String userName=request.getParameter("userName");
		String message=request.getParameter("message");
		if (userName == null || message == null){
			
		}
		String[] favoriteSport = request.getParameterValues("sports");
		String[] favoriteView = request.getParameterValues("views");
		GregorianCalendar calendar=new GregorianCalendar();
		// Create time stamp
		String timeStamp = ""+calendar.get(Calendar.DATE)+"/"+(Integer.valueOf(calendar.get(Calendar.MONTH))+1)+"/"+calendar.get(Calendar.YEAR)
		 			+" at "+calendar.get(Calendar.HOUR)+":"+calendar.get(Calendar.MINUTE)+":"+calendar.get(Calendar.SECOND);
		Post newPost = new Post(userName, message, timeStamp, favoriteSport, favoriteView);
		ForumPosts.add(newPost);
		
		// Save Forum posts to file
		SavePostsToFile(ForumPosts);
		
		// Set the response content
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		returnHTML(out);
	}
}