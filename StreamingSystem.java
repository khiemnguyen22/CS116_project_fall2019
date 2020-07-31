package project;
import java.io.*;
import java.util.*;

public class StreamingSystem {
	protected static ArrayList<User> users=new ArrayList<User>();
	
	public static void add(User u)
	{
		users.add(u);
	}
	public static void remove(int id)
	{
		for(int i=0;i<users.size();i++)
		{
			if(id==users.get(i).getID())
			{
				users.remove(i);
			}
		}
	}
	public static void showUsers()
	{
		for(int i=0;i<users.size();i++)
		{
			System.out.println(users.get(i).toString());
		}
	}
	public static void setUser(String fileName)
	{
		String[] line=new String[2];
		try {
		File file=new File("C:\\Users\\khiem\\eclipse-workspace\\CS116_FinalExam\\src\\project\\"+fileName);
		Scanner scan=new Scanner(file);
		while (scan.hasNextLine())
			{
			line=scan.nextLine().split(",");
			users.add(new User(line[0],Integer.parseInt(line[1])));
			}
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("ERROR: File "+fileName+" not found!");
			//fnfe.printStackTrace();
		}
	}
	public static void saveUSer()
	{
		try {
			FileOutputStream fos = new FileOutputStream("C:\\Users\\khiem\\eclipse-workspace\\CS116_FinalExam\\src\\project\\UsersFile.csv");
			PrintWriter pw = new PrintWriter(fos);
			for(int i=0;i<users.size();i++)
			{
				pw.print(users.get(i).getName()+",");
				pw.print(users.get(i).getID());
				pw.println();
			}
			pw.close();
			System.out.println("Sucessful!");
		}	
		catch (FileNotFoundException e){
			System.out.println("No file!");
		}
	}
					
}
				


