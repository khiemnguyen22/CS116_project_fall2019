package project;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
public class Application {

	public static void main(String[] args) {
		try
		{
		//Load the existing user file/database to the Streaming System database
		Scanner scan=new Scanner(System.in);
		StreamingSystem.setUser("UsersFile.csv");
		ArrayList<User> users=StreamingSystem.users;
		
		//Introduction
		System.out.println("Welcome to the Streaming System! \nChoose the following option to get started");
		System.out.println("1. Create new account \n"
				          +"2. Existing user");
		int choice=scan.nextInt();
		
	  int currentUser=0;
		//creating a user
		if(choice==1)
		{
		System.out.println("Create an account");
		System.out.print("Input user name: ");
		String uName=scan.next();
		User u1=new User(uName);
		System.out.println("Your ID: "+ u1.getID() +"\n");
		StreamingSystem.add(u1);
		try
		{
			//Once a user is created save his/her information to the existing user file
			FileOutputStream fos = new FileOutputStream("C:\\Users\\khiem\\eclipse-workspace\\CS116_FinalExam\\src\\project\\UsersFile.csv",true);
			PrintWriter pw = new PrintWriter(fos);
			pw.print(uName+",");
			pw.print(u1.getID());
			pw.close();
		}
		catch(FileNotFoundException e){
			System.out.println("No file!");
		}
		}
		
		//Login
		String username;
		boolean flag=true;
		int counter=1;
		int inputID;
		do
		{
			// Everyone has 3 attempts to log in. If anyone fails to log in 3 times, the program will show a message and terminate
			if(counter==4)
			{
				System.out.println("Please try again later");
				break;
			}
			Scanner sc=new Scanner(System.in);
			System.out.println("number of login attempts: "+counter++);
			System.out.print("input ID: ");
			inputID=sc.nextInt();
			System.out.print("input username: ");
			username=sc.next();
			for(int i=0;i<users.size();i++)
			{
				if(users.get(i).login(inputID, username))
				{
					flag=false;
					currentUser=i;
					break;
				}
			}
			if(flag==true)
			{
				System.out.println("No matching users");
			}
		} while(flag);
		
		//Once logged in, the program starts displaying the functions
		if(flag==false)
		{
			boolean cont=true;
			while (cont)
			{
			Scanner scan1= new Scanner(System.in);
			System.out.println("What do you want to do? Choose the following options: \n"
					         + "(Type 1) Show all users \n"
					         + "(Type 2) Add new users \n"
					         + "(Type 3) Remove users \n"
					         + "(Type 4) Add recording: \n"
					         + "(Type 5) Add playlist\n"
					         + "(Type 6) Remove recording\n"
					         + "(Type 7) Play individual recording\n"
					         + "(Type 8) Play playlist\n"
					         + "(Type 9) Shuffle playlist\n"
					         + "(Type 10)Save playlist to file \n"
					         + "(Type 11) Display playlist stats");
			int action=scan1.nextInt();
			switch(action)
			{
			case 1:
			{
				System.out.println("********Show all users********");
				StreamingSystem.showUsers();
				break;
			}
			case 2:
			{
				//Scanner s=new Scanner("System.in");
				System.out.println("********Add user********");
				System.out.println("Provide username: ");
				String newName=scan1.next();
				User newUse=new User(newName);
				StreamingSystem.add(newUse);
				StreamingSystem.showUsers();
				try
				{
					//Once a user is created save his/her information to the existing user file
					FileOutputStream fos = new FileOutputStream("C:\\Users\\khiem\\eclipse-workspace\\CS116_FinalExam\\src\\project\\UsersFile.csv",true);
					PrintWriter pw = new PrintWriter(fos);
					pw.print(newName+",");
					pw.print(newUse.getID());
					pw.close();
				}
				catch(FileNotFoundException e){
					System.out.println("No file!");
				}
				break;
			}
			case 3:
			{
				System.out.println("********Remove user********");
				StreamingSystem.showUsers();
				System.out.println("Provide that user's id: ");
				int id=scan1.nextInt();
				StreamingSystem.remove(id);
				StreamingSystem.showUsers();
				StreamingSystem.saveUSer();
				break;
			}
			case 4:
			{
				System.out.println("********Add recording********");
				System.out.println("Audio (click 1) or video (click 2)?");
				int option=scan1.nextInt();
				if(option==1)
				{
					System.out.println("Provide name: ");
					String name=scan1.next();
					System.out.println("Provide artist: ");
					String artist= scan1.next();
					System.out.println("Provide duration in seconds: ");
					int duration=scan1.nextInt();
					System.out.println("Provide bit rate: ");
					double bitRate=scan1.nextDouble();
					users.get(currentUser).getPlaylist().addRecording(new AudioRecording(artist,name,duration,bitRate));
				}
				else if(option==2)
				{
					System.out.println("Provide name: ");
					String name=scan1.next();
					System.out.println("Provide artist: ");
					String artist= scan1.next();
					System.out.println("Provide duration in seconds: ");
					int duration=scan1.nextInt();
					System.out.println("Provide frame rate: ");
					double frameRate=scan1.nextDouble();
					users.get(currentUser).getPlaylist().addRecording(new VideoRecording(artist,name,duration,frameRate));	
				}
				else
				{
					System.out.println("invalid input");
				}
				break;
			}
			case 5:
			{
				System.out.println("********Add playlist********");
				System.out.println("Provide file name (type 1) or another user's playlist (type 2): ");
				int choice5=scan1.nextInt();
				if(choice5==1)
				{
					String file="C:\\Users\\khiem\\eclipse-workspace\\CS116_FinalExam\\src\\project\\";
					System.out.print("Enter file name: ");
					String name=scan1.next();
					String fileName=file+name;
					users.get(currentUser).getPlaylist().add(fileName);
				}
				else if(choice5==2)
				{
					StreamingSystem.showUsers();
					int flag5=1;
					System.out.print("Enter another user ID: ");
					int otherID=scan1.nextInt();
					for(int i=0;i<users.size();i++)
					{
						if(users.get(i).getID()==otherID)
						{
							users.get(currentUser).getPlaylist().add(users.get(i).getPlaylist());
							flag5=0;
						}
					}
					if(flag5==1)
					{
						System.out.println("Cannot find user");
					}
				}
				else
					System.out.println("Invalid input");
				break;
				
			}
			case 6:
			{
				System.out.println("********Remove recording********");
				System.out.println("***         Avalable recordings       ***");
				for(int i=0;i<users.get(currentUser).getPlaylist().getList().length;i++)
				{
					System.out.println("recording: "+users.get(currentUser).getPlaylist().getList()[i]+", index: "+i);
				}
				System.out.println("provide name (type 1) or index (type 2): ");
				int choice6=scan1.nextInt();
				if(choice6==1)
				{
					System.out.println("Provide name: ");
					String name=scan1.next();
					System.out.println("remove successful? "+users.get(currentUser).getPlaylist().remove(name));
				}
				else
				{
					System.out.println("Provide index: ");
					int index=scan1.nextInt();
					users.get(currentUser).getPlaylist().remove(index);
					System.out.println("remove successful? "+users.get(currentUser).getPlaylist().remove(index));
				}
				break;
			}
			case 7:
			{
				System.out.println("********Play individual recording********");
				System.out.println("***         Avalable recordings       ***");
				for(int i=0;i<users.get(currentUser).getPlaylist().getList().length;i++)
				{
					System.out.println("recording: "+users.get(currentUser).getPlaylist().getList()[i]+", index: "+i);
				}
				System.out.println("Provide recording index: ");
				int index=scan1.nextInt();
				users.get(currentUser).getPlaylist().getList()[index].play();
				break;
				
			}case 8:
			{
				System.out.println("********Play playlist********");
				users.get(currentUser).getPlaylist().play();
				break;
				
			}
			case 9:
			{
				System.out.println("********Shuffle playlist********");
				System.out.println("Enter the number of recording to play: ");
				int num=scan1.nextInt();
				users.get(currentUser).getPlaylist().shuffle(num);
				break;
			}
			case 10:
			{
				System.out.println("********Save playlist to file********");
				try {
					FileOutputStream fos = new FileOutputStream("C:\\Users\\khiem\\eclipse-workspace\\CS116_FinalExam\\src\\project\\playlist"+users.get(currentUser).getID()+".csv");
					PrintWriter pw = new PrintWriter(fos);
					for(int i=0;i<users.get(currentUser).getPlaylist().getList().length;i++)
					{
						if(users.get(currentUser).getPlaylist().getList()[i] instanceof AudioRecording)
						{
							pw.print("A,");
							pw.print(users.get(currentUser).getPlaylist().getList()[i].getName()+",");
							pw.print(users.get(currentUser).getPlaylist().getList()[i].getArtist()+",");
							pw.print(users.get(currentUser).getPlaylist().getList()[i].getDuration()+",");
							pw.print(users.get(currentUser).getPlaylist().getList()[i].getBitRate()+",");
							pw.println();
						}
						else if(users.get(currentUser).getPlaylist().getList()[i] instanceof VideoRecording)
						{
							pw.print("V,");
							pw.print(users.get(currentUser).getPlaylist().getList()[i].getName()+",");
							pw.print(users.get(currentUser).getPlaylist().getList()[i].getArtist()+",");
							pw.print(users.get(currentUser).getPlaylist().getList()[i].getDuration()+",");
							pw.print(users.get(currentUser).getPlaylist().getList()[i].getFrameRate()+",");
							pw.println();
						}
					}
					pw.close();
					System.out.println("Sucessful!");
				}	
				catch (FileNotFoundException e){
					System.out.println("No file!");
				}
				break;
				
			}
			case 11:
			{
				System.out.println("********Display playlist stats********");
				System.out.println(users.get(currentUser).getPlaylist().toString());
				break;
			}
			default:
			{
				cont=false;
				break;
			}
			}
			
			//Check if the user wants to continue with the program or else terminate
			int checkContinue;
			System.out.print("continue?(1:yes,0:No): ");
			checkContinue=scan1.nextInt();
			if(checkContinue!=1)
			{
				cont=false;
			}
			else
				cont=true;
			}
		}
		}
		catch (InputMismatchException ime)
		{
			System.out.println("Invalid input");
		}
}

}
