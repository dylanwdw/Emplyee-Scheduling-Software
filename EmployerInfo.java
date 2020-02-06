import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class EmployerInfo 
{
	static File employerInfoFile = new File("EmployerSetup.csv");
	private static Scanner info;
	
	private static ArrayList<Day> days;
	private static ArrayList<Employee> employees;
	private static Schedule schedule;
	
	private static String[][] managerInfo;
	private static String[][] employeeInfo;
	
	private static int numberOfShifts = 0;
	
	public static void enterInfo() 
	{	
		managerInfo = new String[11][15];
		employeeInfo = new String[100][15];
		days = new ArrayList<Day>();
		employees = new ArrayList<Employee>();
		
		readFile();
		generateDays();
		generateEmployees();
		
		Schedule schedule = new Schedule();
	}
	
	private static void readFile()
	{
		try {
			info = new Scanner(employerInfoFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
		info.useDelimiter(",|\\n");
		
		boolean done = false;
		while(!done)
		{
			for(int row = 0; row < managerInfo.length; row++) 
			{
				for(int col = 0; col < managerInfo[0].length; col++)
				{
					managerInfo[row][col] = info.next();
				}
			}
			done = true;
		}
		
		info.nextLine();
		info.nextLine();
		done = false;
		
		while(!done)
		{
			for(int row = 0; row < employeeInfo.length; row++) 
			{
				for(int col = 0; col < employeeInfo[0].length; col++)
				{
					if(info.hasNext())
					{
						employeeInfo[row][col] = info.next();
					}
				}
			}
			done = true;
		}
	}
	
	// creates Day objects and adds them to the "days" array
	public static void generateDays() 
	{
		boolean done = false;
		while(!done)
		{
			for(int c = 1; c < managerInfo[0].length; c = c+2)
			{
				if(!(managerInfo[0][c].equals("Day")))
				{
					int numShifts = 0;
					String dayName = managerInfo[0][c];
					
					for(int r = 1; !(managerInfo[r][c].equals("")); r++)
					{
						numShifts++;
					}
					days.add(new Day(dayName, numShifts));
				}
			}
			done = true;
		}
	}
	
	// creates Employee objects and adds them to the "employees" array
	private static void generateEmployees()
	{
		for(int r = 1; r < employeeInfo.length; r++)
		{
			if(employeeInfo[r][8] != null)
			{
				employees.add(new Employee(employeeInfo[r][0], Integer.parseInt(employeeInfo[r][8])));
			}
		}
	}
	
	public static Day[] getDays()
	{
		Day[] newDays = new Day[days.size()];
		newDays = days.toArray(newDays);
		return newDays;
	}
	
	public static Employee[] getEmployeeList()
	{
		Employee[] newEmployees = new Employee[employees.size()];
		newEmployees = employees.toArray(newEmployees);
		return newEmployees;
	}
	
	public static Schedule getSchedule()
	{
		return schedule;
	}
	
	public static int getNumberOfShits()
	{
		return numberOfShifts;
	}
	
	public static String[][] getManagerInfo()
	{
		return managerInfo.clone();
	}
	
	public static String[][] getEmployeeInfo()
	{
		return employeeInfo.clone();
	}

}
