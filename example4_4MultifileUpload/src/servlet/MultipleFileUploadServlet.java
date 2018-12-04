package servlet;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

public class MultipleFileUploadServlet extends HttpServlet {
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
		message.newMessageList();
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		try {
			// Here we set the file limit size to 2 MB
			MultipartParser parser = new MultipartParser(request, 2 * 1024 * 1024);
			Part part = null;
			message currentM = new message();
			while ((part = parser.readNextPart()) != null) {
				if (part.isFile()) {
					// Here we get some info about the file
					FilePart filePart = (FilePart) part;
					String fname = filePart.getFileName();
					if (fname != null) {
						long fileSize = filePart.writeTo(new File(fileRepositoryPath));
						currentM.addNewImageLink(subDir + separator + filePart.getFileName());
					}
				} else if (part.isParam()) {
					ParamPart parameter = (ParamPart) part;
					String paramName = parameter.getName();
					String paramValue = parameter.getStringValue();
					if (paramName.equals("name")) {
						currentM.setName(paramValue);
					}
					if (paramName.equals("comment")) {
						currentM.setComment(paramValue);
					}
				}
			} // end of while
			//message.addNewMessageToList(currentM);
			currentM.WriteToFile(fileRepositoryPath+"data.txt");
			response.sendRedirect("index.html");
		} catch (java.io.IOException ioe) {
			// We should have mapped an error-page to the java.io.IOException
			// in the deployment descriptor
			/*
			 * throw new java.io.IOException("IOException occurred in: " +
			 * getClass().getName());
			 */
			System.out.println("<p>IOException occurred in: " + getClass().getName() + "</p>");
		}
	} // end of doPost()

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		doPost(request, response);

	}

}