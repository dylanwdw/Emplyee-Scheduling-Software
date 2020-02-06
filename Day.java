import java.util.Scanner;
public class Day 
{
	Scanner sc = new Scanner(System.in);
	
	private final String name;
	private Shift[] shifts;
	
	private int totalTime = 0;
	private int totalAvailability = 0;
	
	public Day(String name, int numberOfShifts)
	{
		this.name = name;
		shifts = new Shift[numberOfShifts];
		generateShifts();
		calculateFields();
	}
	
	private void generateShifts()
	{
		String[][] managerInfo = EmployerInfo.getManagerInfo();
		for(int r = 1; r<shifts.length+1; r++)
		{
			int c = 1;
			if(EmployerInfo.getDays().length > 0)
			{
				c = (EmployerInfo.getDays().length*2)+1;
			}
			shifts[r-1] = new Shift(this, managerInfo[r][c], Integer.parseInt(managerInfo[r][c+1])); 
		}
	}
	
	public void calculateFields() //not called until schedule is built
	{
		for(Shift s : shifts)
		{
			this.totalTime += s.getLengthOfShift();
		}
	}
	
	public void addAvailability(int time)
	{
		this.totalAvailability += time;
	}
	
	public int getTotalAvailability()
	{
		return this.totalAvailability;
	}
	
	public int getTotalTime()
	{
		return this.totalTime;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public Shift[] getShifts()
	{
		return this.shifts.clone();
	}
	
	
}