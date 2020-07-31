package project;

public class VideoRecording extends Recording implements Playable {
	private final double FRAMERATE;
	
	public VideoRecording()
	{
		super();
		FRAMERATE=0;
	}
	public VideoRecording(String a, String n, int seconds, double frameRate)
	{
		super(a,n,seconds);
		if(frameRate<0)
		{
			FRAMERATE=0;
		}
		else
			FRAMERATE=frameRate;
	}
	public double getFrameRate()
	{
		return FRAMERATE;
	}
	@Override
	public void play()
	{
		try
		{
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
		return super.getArtist()+" - "+super.getName()+ " ["+MINUTES+"m"+SECONDS+"s]"+" [VIDEO | framerate: "+FRAMERATE+" fps]";
	}

}
