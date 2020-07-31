package project;

public abstract class Recording implements Playable {
	protected String ARTIST;
	protected String NAME;
	protected int DURATION_IN_SECONDS;
	
	public Recording()
	{
		ARTIST="unknown";
		NAME="unknown";
		DURATION_IN_SECONDS=0;
	}
	public Recording(String a, String n, int seconds)
	{
		if(a==null|| a.equals(""))
		{
			ARTIST="unknown";
		}
		else
			ARTIST=a;
		if(n==null || n.equals(""))
		{
			NAME="unknown";
		}
		else
			NAME=n;
		
		if (seconds<0)
		{
			DURATION_IN_SECONDS=0;
		}
		else
			DURATION_IN_SECONDS=seconds;
	}
	public String getName() {return NAME;}
	public String getArtist() {return ARTIST;}
	public double getBitRate() {return 0;}
	public double getFrameRate() {return 0;}
	public int getDuration() {return DURATION_IN_SECONDS;}
	
	public void play()
	{
		try
		{
		if(DURATION_IN_SECONDS==0)
		{
			throw new UnplayableException();
		}
		else
		{
			System.out.println("Now playing: "+toString());
		}
		}
		catch(UnplayableException ue)
		{
			System.out.println( "ERROR: cannot play this recording");
		}
	}
	public String toString()
	{
		final int MINUTES=DURATION_IN_SECONDS/60;
		final int SECONDS=DURATION_IN_SECONDS-MINUTES*60;
		return ARTIST+" - "+NAME+ " ["+MINUTES+"m"+SECONDS+"s]";
	}
	}

