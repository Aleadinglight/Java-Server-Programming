package servlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class message implements Serializable{
	private static final long serialVersionUID = 1L;
	//private attributes of this class
	private String _name;
	private String _comment;
	private List<String> _imageLink;
	
	//A static attribute contain all message of this class
	private static List<message> AllMessage = new ArrayList<message>();
	//Constructors
	public message(String name, String comment) {
		this._name = name;
		this._comment = comment;
	} 
	public message() {
		this._name="";
		this._comment = "";
		this._imageLink = new ArrayList<String>();
	}
	
	/***** Methods *****/
	//getter and setter of _name attribute
	public String getName(){
		return this._name;
	}
	public void setName(String name) {
		this._name = name;
	}
	//getter and setter of _comment attribute
	public String getComment(){
		return this._comment;
	}
	public void setComment(String comment) {
		this._comment = comment;
	}
	//Methods to manipulate _imageLink list
	public void addNewImageLink(String link) {
		this._imageLink.add(link);
	}
	public String getAllLink() {
		String res="";
		for (String string : _imageLink) {
			res+=string+"<br>";
		}
		return res;
	}
	public String getAllLinkAsImgTag() {
		String res="";
		for(String s:_imageLink) {
			res += "<img src="+s+" alt='userUploadImg' height='60\' width='60'><br>";
		}
		return res;
	}
	public void WriteToFile(String filePath) {
		try {
			File inFile = new File(filePath);
			FileOutputStream f = new FileOutputStream(inFile, true);
			if(inFile.length()==0 || !inFile.exists()){
				ObjectOutputStream o = new ObjectOutputStream(f);
				o.writeObject(this);
				o.close();
			}
			else{
				AppendableStream o = new AppendableStream(f);
				o.writeObject(this);
				o.close();
			}
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Methods to manipulate message list
	public static void addNewMessageToList(message M) {
		AllMessage.add(M);
	}
	public static String getMessageListAsHTMLTag() {
		String res="<table>";
		res+="<tr><th>User name</th><th>Comment</th><th>Image(s)</th></tr>";
		for(message M:AllMessage) {
			res+="<tr><td>"+M._name+"</td><td>"+M._comment+"</td>";
			res+="<td>"+M.getAllLinkAsImgTag()+"</td>";
			res+="</tr>";
		}
		res+="</table>";
		return res;
	}
	public static String searchName(String name) {
		String res="<table>";
		res+="<tr><th>User name</th><th>Comment</th><th>Image(s)</th></tr>";
		for(message M:AllMessage) {
			if(M._name.contains(name)) {
				res+="<tr><td>"+M._name+"</td><td>"+M._comment+"</td>";
				res+="<td>"+M.getAllLinkAsImgTag()+"</td>";
				res+="</tr>";
			}
		}
		res+="</table>";
		return res;
	}
	public static int NumberOfMessage() {
		return AllMessage.size();
	}
	public static boolean messageListIsEmpty() {
		return AllMessage.isEmpty();
	}
	public static void newMessageList(){
		AllMessage.clear();
	}
}
