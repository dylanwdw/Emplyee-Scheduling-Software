import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Schedule 
{
	private Day[] days;
	private Employee[] employees;
	
	private FileOutputStream schedule;
	private PrintWriter writer;
	
	public Schedule()
	{
		this.days = EmployerInfo.getDays();
		this.employees = EmployerInfo.getEmployeeList();
		
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String fileName = dateFormat.format(date) + ".csv";
		
		try {
			schedule = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		writer = new PrintWriter(schedule);
		
		assignSchedule();
		buildSchedule();
		
	}
	
	public void assignSchedule()
	{
		//outermost loop - keeps looping until all days have been scheduled
		int daysDone = 0;
		while(daysDone < days.length)
		{
			//this loop finds the remaining day with the biggest need for employees.	
			Day currentDay = null;
			
			for(int d = 0; d < days.length; d++)
			{
				if(currentDay == null)
				{
					currentDay = days[d];
				}
				else if(days[d] != null && (days[d].getTotalTime()-days[d].getTotalAvailability()) > (currentDay.getTotalTime()-currentDay.getTotalAvailability()))
				{
					currentDay = days[d];
				}
			}

			//fills in each shift of the current day
			for(Shift s: currentDay.getShifts())
			{
				//loops until the shift is completely filled
				while(s.getNumEmployeesSlotted() < s.getEmployeesNeeded())
				{
					//checks to see if at least one person is available for the shift
					boolean someoneIsAvailable = false;
					for(int e = 0; e<employees.length; e++)
					{
						if(employees[e].isAvailableDuring(currentDay, s.getStartTime(), s.getEndTime()))
						{
							someoneIsAvailable = true;
						}
					}
					//if multiple people were available, the employee who is missing the most hours (or over-scheduled the least) is picked
					if(someoneIsAvailable == true)
					{
						Employee emp = null;
						for(int e = 0; e < employees.length; e++) 
						{
							if(emp == null)
							{
								emp = employees[e];
							}
							else if(emp.getTimeRequested()-emp.getTimeScheduled() < employees[e].getTimeRequested()-employees[e].getTimeScheduled()) 
							{
								emp = employees[e];
							}
						}
						s.addEmployee(emp);
					}
					//if no one is available, then the employee with the closest to perfect schedule is chosen
					else
					{
						Employee emp = null;
						for(int e = 0; e < employees.length; e++)
						{
							if(emp == null)
							{
								emp = employees[e];
							}
							else if(emp.getTimeRequested() - emp.getTimeScheduled() < employees[e].getTimeRequested() - employees[e].getTimeScheduled())
							{
								emp = employees[e];
							}
						}
						s.addEmployee(emp);
					}
				}
			}
			
			if(currentDay != null)	//removes the day we just scheduled from the list
			{
				for(int d = 0; d < days.length; d++)
				{
					if(days[d] != null && days[d].equals(currentDay))
					{
						days[d] = null;
					}
				}
			}
			
			daysDone++;
		}
		
	}
	
	private void buildSchedule() // writes the schedule on the csv file.
	{
	
		writer.print(", "); //empty box in top left corner
		
		for(Day d: EmployerInfo.getDays()) // prints all the days. Using the list from the main class because the assignSchedule algorithm changes this class's list.
		{
			writer.print(d.getName() + ", ");
		}
		
		writer.println();
		
		for(Employee e: EmployerInfo.getEmployeeList()) 
		{
			writer.print(e.getName() + ", ");
			
			for(DailyAvailability da : e.getAvailability())
			{
				if(da.getTodaysShift() != null)
				{
					writer.print(da.getTodaysShift().getStartTimeString() + " - " + da.getTodaysShift().getEndTimeString() + ", ");
				}
				else
				{
					writer.print(", ");
				}	
			}
			writer.println();
		}

		writer.close();
	}
}
