package mastermind;
import java.util.concurrent.TimeUnit;

public class Timer extends Thread {
	private int seconds=0;
	private int minutes=0;
	private int hours=0;
	
	public void run()
	{
		try
		{
			while(true)
			{
				seconds++;
				if(seconds>=60)
				{
					seconds=0;
					minutes++;
					if(minutes>=60)
					{
						minutes=0;
						hours++;
					}
				}
				TimeUnit.SECONDS.sleep(1);
			}
		}
		catch (Exception e)
		{
			System.out.println("Timer error");
		}
	}
	
	public void Display_time()	// timer is displayed in format: HH:MM:SS, if hours or minutes are missing (player did it in less than 1 hour/minute) they are not displayed at all
	{
		String sec=Integer.toString(seconds);
		if(seconds<10 && minutes>0) sec="0" + Integer.toString(seconds);
		String min=Integer.toString(minutes);
		if(minutes<10 && hours>0) min="0" + Integer.toString(minutes);
		String h=Integer.toString(hours);
		String display = sec;
		if(minutes>0)
		{
			display = min + ":" + sec;
		}
		if(hours>0)
		{
			display = h + ":" + min + ":" + sec;
		}
		System.out.println("Timer: " + display);
	}
}
