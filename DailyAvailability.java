import java.util.Scanner;
public class DailyAvailability 
{
	Scanner sc = new Scanner(System.in);

	private Day day;
	private Employee emp;
	private Shift shift = null;
	
	private boolean canWorkToday;
	private boolean scheduledToday = false;
	
	int start;
	int end;
	
	// if the employee can't work on this day, this constructor is called instead
	public DailyAvailability(Day day)
	{
		this.day = day;
		canWorkToday = false;
	}
	
	// if the employee can work on this day, then this constructor is called
	public DailyAvailability(Employee e, Day day, String startToEnd)
	{	
		this.day = day;
		emp = e;
		canWorkToday = true;
		start = Helper.getTimeAsMinutes(startToEnd.substring(0, startToEnd.indexOf('-')));
		end = Helper.getTimeAsMinutes(startToEnd.substring(startToEnd.indexOf('-')+2));
		emp = e;
		day.addAvailability(end-start);
	}
	
	public void scheduleThisDay(Shift s)
	{
		scheduledToday = true;
		this.shift = s;
	}
	
	public boolean isAvailableDuring(int startTime, int endTime)
	{
		
		if(canWorkToday == true && scheduledToday == false && startTime >= start && endTime <= end)
		{
			return true;
		}
		return false;
	}
	
	public Day getDay()
	{
		return day;
	}
	
	public String getDayName()
	{
		return day.getName();
	}
	
	public boolean getScheduledToday()
	{
		return scheduledToday;
	}
	
	public Shift getTodaysShift()
	{
		return shift;
	}
}