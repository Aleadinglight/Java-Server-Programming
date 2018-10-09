package submit.message;

public class Post {
	private String name, message, timeStamp;
	public Post(String name, String message, String timeStamp){
		this.name = name;
		this.message = message;
		this.timeStamp = timeStamp;
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
}
