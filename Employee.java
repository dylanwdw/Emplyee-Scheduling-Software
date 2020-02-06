public class Employee 
{
	private DailyAvailability[] availability = new DailyAvailability[EmployerInfo.getDays().length];
	
	private final String name;
	
	private int timeRequested;
	private int timeScheduled = 0;
	
	public Employee(String name, double hoursRequested)
	{
		this.name = name;
		this.timeRequested = (int)hoursRequested*60;
		
		generateAvailability();
	}
	
	private void generateAvailability()
	{
		String[][] employeeInfo = EmployerInfo.getEmployeeInfo();
		Day[] days = EmployerInfo.getDays();
		
		for(int r = 0; r<employeeInfo.length; r++)
		{
			if(employeeInfo[r][0] != null && employeeInfo[r][0].equals(this.name))
			{
				for(int c = 1; c <= days.length; c++)
				{
					if(employeeInfo[r][c].equals(""))
					{
						availability[c-1] = new DailyAvailability(days[c-1]);
					}
					else
					{
						availability[c-1] = new DailyAvailability(this, days[c-1], employeeInfo[r][c]);
					}
				}
			}
		}
	}
	
	public void schedule(Shift s)
	{
		for(int i=0; i<availability.length; i++)
		{
			if(availability[i].getDay().equals(s.getDay()))
			{
				availability[i].scheduleThisDay(s);
				break;
			}
		}
		timeScheduled += s.getLengthOfShift();
	}
	
	public boolean isAvailableDuring(Day day, int startTime, int endTime)
	{
		for(DailyAvailability a : availability)
		{
			if(a.getDay() == day && a.isAvailableDuring(startTime, endTime) == true)
			{
				return true;
			}
		}
		return false;
	}
	
	public void addTime(int time)
	{
		timeScheduled += time;
	}
	
	public DailyAvailability getAvailability(int day)
	{
		return availability.clone()[day];
	}
	
	public DailyAvailability[] getAvailability()
	{
		return this.availability.clone();
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getTimeRequested()
	{
		return this.timeRequested;
	}

	public int getTimeScheduled()
	{
		return this.timeScheduled;
	}
}
