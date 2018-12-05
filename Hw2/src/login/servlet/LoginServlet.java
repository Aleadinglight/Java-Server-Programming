package login.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	
	private static Map<String, String> createMap()
	{
		Map<String, String> user = new HashMap<String, String>();
		user.put("admin", "admin");
		return user;
	}
	//Map of username and password
	public static Map<String, String> userDictionay = createMap();
	
	//User authentication
	boolean validateUser(String userName, String password)
	{
		for(Map.Entry<String, String> m:userDictionay.entrySet())
		{
			if(userName.equals(m.getKey()) && password.equals(m.getValue()))
				return true;
		}
		return false;
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("name");
		//System.out.println(userName);
		String password = request.getParameter("password");
		String reply = null;
		int forwardedResponse = 0;
		if(userName==null || password==null || userName.isEmpty() || password.isEmpty())
		{
			reply = "User name or password cannot be empty";
		}else 
		{
			if(!validateUser(userName, password))
				reply = "Wrong username or password";
			else 
			{
				forwardedResponse = 1;
				RequestDispatcher rs = request.getRequestDispatcher("forum.html");
				rs.forward(request, response);
			}
		}
		if (forwardedResponse == 0)
			response.sendRedirect("index.html?reply="+reply);
	}
}