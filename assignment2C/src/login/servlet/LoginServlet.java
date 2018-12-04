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
	
	//Check an array of strings to see if there are any empty strings
	boolean isOneEmpty(String...strings)
	{
		for(String s : strings)
		{
			if(s==null||s.isEmpty())
				return true;
		}
		return false;
	}
	private static Map<String, String> createMap()
	{
		Map<String, String> ret = new HashMap<String, String>();
		ret.put("root", "admin");
		ret.put("huy", "123");
		return ret;
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
		System.out.println(userName);
		String password = request.getParameter("password");
		String reply = null;
		if(isOneEmpty(userName, password))
		{
			reply = "User+name+or+password+is+empty";
		}else 
		{
			if(!validateUser(userName, password))
				reply = "Wrong+username+or+password";
			else 
			{
				RequestDispatcher rs = request.getRequestDispatcher("forum.html");
				rs.forward(request, response);
			}
		}		
		response.sendRedirect("index.html?reply="+reply);
	}
}