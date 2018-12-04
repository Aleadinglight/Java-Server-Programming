package forumHandle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class message implements Serializable{
	//Message attribute
	private String userName;
	private String message;
	private String datePosted;
	private String[] favoriteSport;
	private String[]favoriteViews;
	
	transient SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	/*static variables and method of this class*/
	public static transient List<message> allMessages = new ArrayList<message>();
	public String get_name()
	{
		return this.userName;
	}
	public String get_message()
	{
		return this.message;
	}
	public String get_date()
	{
		return this.datePosted;
	}
	public String get_favorite_sports()
	{
		return Arrays.toString(this.favoriteSport);
	}
	public String get_view()
	{
		return Arrays.toString(this.favoriteViews);
	}
	

	public static void initializeList() throws ParseException 
	{
		if(allMessages.isEmpty())
		{
			//allMessages.add(new message("Huy", "I feel good", "10/08/2016 12:00"));
			//allMessages.add(new message("Han", "Today is a nice day", "21/06/2018 13:00"));
			//allMessages.add(new message("Bim", "This is not good", "10/08/2016 12:00"));
		}
	}
	public message(String inUserName, String inMessage)
	{
		this.userName = inUserName;
		this.message = inMessage;
		datePosted = dateFormat.format(new Date());
	}
	public message(String inUserName, String inMessage, String inDate) throws ParseException
	{
		this.userName = inUserName;
		this.message = inMessage;
		this.datePosted = inDate;
	}
	public message(String inUserName, String inMessage, String[] inSports, String[] inView)
	{
		this.userName = inUserName;
		this.message =  inMessage;
		this.favoriteSport = inSports;
		this.favoriteViews = inView;
		this.datePosted = dateFormat.format(new Date());
	}
	public boolean compareDate(String date)
	{
        if(date.contains(this.datePosted.substring(0, 9)))
        {
        	return true;
        }
        else
        {
        	return false;
        }
	} 
	public static List<message> search_user(String inUserName)
	{
		List<message> messageMatched = new ArrayList<message>();
		for(int i=0;i<allMessages.size();i++)
		{
			message M = allMessages.get(i);
			if(inUserName.equals(M.userName))
			{
				messageMatched.add(M);
			}
		}
		return messageMatched;
	}
	public static List<message> search_date(String inDate) throws ParseException
	{
		List<message> dateMatched = new ArrayList<message>();
		for(int i=0;i<allMessages.size();i++)
		{
			message M = allMessages.get(i);
			if(M.compareDate(inDate))
			{
				dateMatched.add(M);
			}
		}
		return dateMatched;
	}
	
	public String toString()
	{
		String res = new String();
		res+="<tr>";
		res+="<td>"+this.get_name()+"</td><td>"+this.get_message()+"</td><td>"+this.get_date()+"</td>";
		res+="<td>"+ this.get_favorite_sports() +"</td>";
		res+="<td>"+this.get_view()+"</td>";
		res+="</tr>";
		return res;
	}
	public static String messageListToString(List<message> messageList)
	{
		String res = "<tr><th>User name</th><th>message</th><th>Date</th><th>Favorite Sport</th><th>Favorite view</th></tr>";
		for(int i=0;i<messageList.size();i++)
		{
			message M = messageList.get(i);
			res+=M.toString();
		}
		return res;
	}	
}
