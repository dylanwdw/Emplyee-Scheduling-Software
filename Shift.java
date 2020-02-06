public class Shift 
{
	String startTimeString;
	String endTimeString;
	private int start;
	private int end;
	private int employeesNeeded;
	private int numEmployeesSlotted = 0;
	private Day day;
	
	private Employee[] employeesSlotted;
	
	public Shift(Day day, String startToEnd, int employeesNeeded)
	{
		startTimeString = startToEnd.substring(0, startToEnd.indexOf('-'));
		endTimeString = startToEnd.substring(startToEnd.indexOf('-')+2);
		start = Helper.getTimeAsMinutes(startToEnd.substring(0, startToEnd.indexOf('-')));
		end = Helper.getTimeAsMinutes(startToEnd.substring(startToEnd.indexOf('-')+2));
		this.employeesNeeded = employeesNeeded;
		this.day = day;
		
		employeesSlotted = new Employee[employeesNeeded];
	}
	
	public boolean addEmployee(Employee e)
	{
		for(int i=0; i<employeesSlotted.length; i++)
		{
			if(employeesSlotted[i] == null)
			{
				employeesSlotted[i] = e;
				e.schedule(this);
				numEmployeesSlotted++;
				return true;
			}
		}
		return false;
	}
	
	public int getLengthOfShift()
	{
		return end-start;
	}
	
	public int getStartTime()
	{
		return this.start;
	}
	
	public int getEndTime()
	{
		return this.end;
	}
	
	public int getEmployeesNeeded()
	{
		return this.employeesNeeded;
	}
	
	public Employee[] getEmployeesSlotted()
	{
		return this.employeesSlotted;
	}
	
	public int getNumEmployeesSlotted()
	{
		return numEmployeesSlotted;
	}
	
	public Day getDay()
	{
		return this.day;
	}
	
	public String getStartTimeString()
	{
		return startTimeString;
	}
	
	public String getEndTimeString()
	{
		return endTimeString;
	}
}
