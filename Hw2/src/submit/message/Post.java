package submit.message;

import java.util.Arrays;
import java.io.Serializable;

public class Post implements Serializable {
	private String name, message, timeStamp;
	private String[] favoriteSport;
	private String[]favoriteViews;
	
	public Post(String name, String message, String timeStamp){
		this.name = name;
		this.message = message;
		this.timeStamp = timeStamp;
	}
	public Post(String name, String message, String timeStamp, String[] favoriteSports, String[] favoriteViews){
		this.name = name;
		this.message = message;
		this.timeStamp = timeStamp;
		this.favoriteSport = favoriteSports;
		this.favoriteViews = favoriteViews;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getFavoriteSports()
	{
		return Arrays.toString(this.favoriteSport);
	}
	public String getFavoriteView()
	{
		return Arrays.toString(this.favoriteViews);
	}
}
