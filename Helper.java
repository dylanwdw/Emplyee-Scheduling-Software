public class Helper 
{
	
	// returns the time as minutes past midnight. 
	public static int getTimeAsMinutes(String time)
	{
		int minutes = 0;
		
		if(time.contains("AM") && Integer.parseInt(time.substring(0, time.indexOf(":"))) == 12)
		{
			minutes += 0; 
		}
		else if(time.contains("PM") && Integer.parseInt(time.substring(0, time.indexOf(":"))) != 12)
		{
			minutes = (Integer.parseInt(time.substring(0, time.indexOf(":"))) + 12) * 60;
		}
		else
		{
			minutes = Integer.parseInt(time.substring(0, time.indexOf(":"))) * 60;
		}
		minutes += Integer.parseInt(time.substring(time.indexOf(":")+1, time.indexOf(":") + 3));
		
		return minutes;
	}
}
