package project;

public class AudioRecording extends Recording implements Playable {
	private final double BITRATE;
	
	public AudioRecording()
	{
		super();
		BITRATE=0;
	}
	public AudioRecording(String a, String n, int seconds,double bitRate)
	{
		super(a, n, seconds);
		if(bitRate<0)
		{
			BITRATE=0;
		}
		else
			BITRATE=bitRate;
	}
	public double getBitRate()
	{
		return BITRATE;
	}
	@Override
	public void play()
	{
		try {
		if(getDuration()==0)
		{
			throw new UnplayableException();
		}
		else
		{
			System.out.println("Now playing: "+this.toString());
		}
		}
		catch(UnplayableException ue)
		{
			System.out.println( "ERROR: cannot play this recording");
		}
	}
	@Override
	public String toString()
	{
		final int MINUTES=super.getDuration()/60;
		final int SECONDS=super.getDuration()-MINUTES*60;
		return super.getArtist()+" - "+super.getName()+ " ["+MINUTES+"m"+SECONDS+"s]"+" [AUDIO | bitrate: "+BITRATE+" kbps]";
	}
}
