package project;
import java.lang.NumberFormatException;
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Playlist implements Playable {
	private String name;
	private int numberOfRecordings=0;
	private int durationInSeconds=0;
	private final int MAX_PLAYLIST_SIZE;
	private Recording[] recordingList;
	private int numPlay;
	
	public Playlist()
	{
		name="unknown";
		MAX_PLAYLIST_SIZE=5;
		recordingList=new Recording[MAX_PLAYLIST_SIZE];	
		numPlay=0;
	}
	public Playlist(String n,int size)
	{
		setName(n);
		if(size<=0)
		{
			MAX_PLAYLIST_SIZE=5;
		}
		else
		MAX_PLAYLIST_SIZE=size;
		recordingList=new Recording[MAX_PLAYLIST_SIZE];	
		numPlay=0;
	}
	public void setName(String n)
	{
		while(n==null || n.equals(""))
		{
			System.out.println("invalid name");
			Scanner scan=new Scanner(System.in);
			System.out.print("enter name: ");
			n=scan.nextLine();
		}
		name=n;
	}
	public String getName()
	{
		return name;
	}
	public boolean addRecording(Recording r)
	{
		if(r==null)
		{
			return false;
		}
		else
		{
			Recording[] newList=new Recording[numberOfRecordings+1];
			for(int i=0;i<numberOfRecordings;i++)
			{
				newList[i]=recordingList[i];
			}
			newList[numberOfRecordings]=r;
			numberOfRecordings++;
			recordingList=newList;
			return true;
		}
	}
	public Recording[] getList()
	{
		return recordingList;
	}
	public boolean remove(int index)
	{
		if(numberOfRecordings<=0)
		{
			return false;
		}
		else
		{
		remove(getList()[index]);
		return true;
		}
	}
	public boolean remove(String name)
	{
		if(numberOfRecordings<=0)
		{
			return false;
		}
		else
		{
			for(int i=0;i<recordingList.length;i++)
			{
				if(recordingList[i].getName().equals(name))
				{
					remove(i);
					return true;
				}
			}
		}
		return false;
	}
	public boolean remove(Recording r)
	{
		if(r==null || numberOfRecordings<=0)
		{
			return false;
		}
		else
		{
			int k=0;
			Recording[] newRecording=new Recording[numberOfRecordings-1];
			for(int i=0;i<numberOfRecordings;i++)
			{
				if(recordingList[i].equals(r))
				{
					continue;
				}
				else
				newRecording[k++]=recordingList[i];
			}
			recordingList=newRecording;
			numberOfRecordings--;
			durationInSeconds-=r.getDuration();
			return true;
		}
		
	}
	
	public void play()
	{
		if(numberOfRecordings==0)
		{
			System.out.println("ERROR: empty playlist");
		}
		for(int i=0;i<numberOfRecordings;i++)
		{
			recordingList[i].play();
		}
		numPlay++;
	}
	public String toString()
	{
		String summary="";
		for(int i=0;i<numberOfRecordings;i++)
		{
			summary+=recordingList[i].toString()+"\n";
		}
		return "Playlist: "+name+" ["+((int)durationInSeconds/60)+"m"+(durationInSeconds-((int)durationInSeconds/60)*60)+"s] \n Number of times played: "+numPlay+"\nNumber of recordings: "+numberOfRecordings+"\n"+summary;
	}
	public void shuffle(int numberOfRecordingsToPlay)
	{	
		for(int i=0;i<numberOfRecordingsToPlay;i++)
		{
		Random rand=new Random();
		int random=rand.nextInt(numberOfRecordings);
		System.out.println("Now Playing "+recordingList[random].toString());
		}
		numPlay++;
	}
	public boolean add(Playlist p)
	{
		Recording[] newList=new Recording[p.getList().length];
		numberOfRecordings=0;
		durationInSeconds=0;
		for(int i=0;i<p.getList().length;i++)
		{
			newList[numberOfRecordings++]=p.getList()[i];
			durationInSeconds+=p.getList()[i].getDuration();
		}
		recordingList=newList;
		return true;
	}
	public void add(String fileName)
	{
		ArrayList<Recording> newList=new ArrayList<Recording>();
		System.out.println("Processing playlist file "+fileName+":");
		String[] line=new String[5];
		try {
		File file=new File(fileName);
		Scanner scan=new Scanner(file);
		while (scan.hasNextLine())
		{
			try
				{
				int count=0;
				line=scan.nextLine().split(",");
				if(line[0].equals("V"))
				{
					newList.add(new VideoRecording(line[1],line[2],Integer.parseInt(line[3]),Double.parseDouble(line[4])));
					//numberOfRecordings++;
					durationInSeconds+=Integer.parseInt(line[3]);
				}
				else
				{
					newList.add(new AudioRecording(line[1],line[2],Integer.parseInt(line[3]),Double.parseDouble(line[4])));
					//numberOfRecordings++;
					durationInSeconds+=Integer.parseInt(line[3]);
				}
				}	
			catch(NumberFormatException nfe)
			{
			System.out.println("ERROR: Number format exception. Recording rejected "+"("+line[1]+", "+line[2]+", "+line[3]+", "+line[4]+")");
			//nfe.printStackTrace();
			}
		}
		Recording[] newListArray=new Recording[newList.size()+numberOfRecordings];
		for(int k=0;k<numberOfRecordings;k++)
		{
			newListArray[k]=recordingList[k];
		}
		for(int k=numberOfRecordings,i=0;k<newListArray.length;k++)
		{
			newListArray[k]=newList.get(i);
			i++;
			numberOfRecordings++;
		}
		recordingList=newListArray;
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("ERROR: File "+fileName+" not found!");
			//fnfe.printStackTrace();
		}
	}
	}
	
