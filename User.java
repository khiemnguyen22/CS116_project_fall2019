package project;
import java.util.*;
public class User {
	private int id;
	private String name;
	private int numberOfAttempts=1;
	private static int counter=0;
	private static ArrayList<User> users=new ArrayList<User>();
	private Playlist userPlay;

	public User (String n)
	{
		name=n;
		counter++;
		id=counter;
		userPlay=new Playlist();
		numberOfAttempts=1;
	}
	public User(String n, int id)
	{
		name=n;
		this.id=id;
		userPlay=new Playlist();
		numberOfAttempts=1;
	}
	public String getName()
	{
		return name;
	}
	public int getID()
	{
		return id;
	}
	public boolean login(int id, String username)
	{
			if (this.id==id && name.equals(username))
			{
				return true;
			}
			else 
			{
				return false;
			}
	}
	public Playlist getPlaylist()
	{
		return userPlay;
	}
	public void add(User u)
	{
		userPlay=u.getPlaylist();
	}
	public String toString()
	{
		return "user "+name+", id: "+id+" playlist: "+userPlay.getName();
	}
	
	
}
