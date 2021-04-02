import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collection; // didnt use!
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

//#region       File I/O library imports
import java.io.File; // Import the File class
import java.io.FileWriter; // import to write students to file
//#endregion

public class Scheduler 
{
    // In main() after you create a Registry object, create a Scheduler object and pass in the students ArrayList/TreeMap
	// If you do not want to try using a Map then uncomment
	// the line below and comment out the TreeMap line
	
	//ArrayList<Student> students;
	
	private TreeMap<String,ActiveCourse> schedule;

	// new TreeMap to handle multiple blocks of time for a course during the week. 
	private TreeMap<String, TreeMap<Integer, ArrayList<String>>> lectureSchedule = new TreeMap<String, TreeMap<Integer, ArrayList<String>>>(); 

	//a list of weekdays
	private List<String> weekDays = Arrays.asList("MON","TUE","WED","THUR","FRI");
		
	public Scheduler(TreeMap<String,ActiveCourse> courses)
	{
	  schedule = courses;
	}
	
	public void setDayAndTime(String courseCode, String day, int startTime, int duration) throws Exception
	{
		if (!weekDays.contains(day)) throw new RuntimeException("Invalid Lecture Day: " + day);
		if ((startTime < 800 || startTime > 1700) || (duration + startTime > 1800)) throw new RuntimeException("Invalid Lecture Start Time: " + startTime);
		if (duration != 1 && duration != 2 && duration != 3) throw new RuntimeException("Invalid Lecture Duration: " + duration);
		ActiveCourse course = schedule.get(courseCode);
		if (course == null) throw new RuntimeException("Unknown Course: " + courseCode);
		//if (course.getScheduleDuration() + duration > 3) throw new RuntimeException("Invalid  Lecture Duration. The total duration cannot not exceed 3 hours");

		//create a temp active course with the given schedule to check with the list of active courses for time collision
		//ActiveCourse tempActiveCourse = new ActiveCourse("temp", "tmp1234", "tempCourse", "fmt", "semester", new ArrayList<Student>(Arrays.asList()));
		//tempActiveCourse.setSchedule(day, startTime, duration);

		// Set<String> courseSet = schedule.keySet();
		// for(String courseCodeIterate : courseSet)
		// 	if(schedule.get(courseCodeIterate).equals(tempActiveCourse)) throw new RuntimeException("Lecture Time Collision with " + courseCodeIterate);

		// course.setSchedule(day, startTime, duration);

		for(int i = 0; i < duration; i++)
		{
			if(lectureSchedule.get(day) == null) 
				break;
			if(lectureSchedule.get(day).get(startTime) != null)
				throw new RuntimeException("Lecture Time Collision with " + lectureSchedule.get(day).get(startTime).get(0));
		}

			Set<String> daySet = lectureSchedule.keySet();
			Set<Integer> timeSet;
			int totalDuration = 0;
			for(String weekDay : daySet)
			{
				timeSet = lectureSchedule.get(weekDay).keySet();

				for(Integer time : timeSet) if(lectureSchedule.get(weekDay).get(time).get(0).equalsIgnoreCase(courseCode))
				{
					totalDuration += 1;
				}
			}

		if (totalDuration + duration > 3) 
			throw new RuntimeException("Invalid  Lecture Duration. The total duration cannot not exceed 3 hours");	

		if(lectureSchedule.get(day) == null)lectureSchedule.put(day, new TreeMap<Integer, ArrayList<String>>());

		for(int i = 0; i < duration; i++)
		{
			lectureSchedule.get(day).put(startTime + (i * 100) ,new ArrayList<String>());
			lectureSchedule.get(day).get(startTime + (i * 100)).add(courseCode);
			lectureSchedule.get(day).get(startTime + (i * 100)).add(Integer.toString(duration));
		}
	}
	
	public void clearSchedule(String courseCode)
	{
			//schedule.get(courseCode).setSchedule("", 0, 0);

			Set<String> daySet = lectureSchedule.keySet();
			Set<Integer> timeSet;

			//this is cuz when you remove something from the lectureSchedule the size will change and the teration will throw exception.
			// it keeps the ones that should be deleted and then deletes them...
			TreeMap<String, ArrayList<Integer>> sch2Remove = new TreeMap<String,ArrayList<Integer>>();

			for(String day : daySet)
			{
				timeSet = lectureSchedule.get(day).keySet();
				for(Integer time : timeSet) if(lectureSchedule.get(day).get(time).get(0).equalsIgnoreCase(courseCode))
				{
					if(sch2Remove.get(day) == null) sch2Remove.put(day, new ArrayList<Integer>());
					sch2Remove.get(day).add(time);
				}
			}

			daySet = sch2Remove.keySet();
			for(String day : daySet)
			{
				if(sch2Remove.get(day) != null)
				{ 
					for(int i = 0; i < sch2Remove.get(day).size(); i++)
					{
						lectureSchedule.get(day).remove(sch2Remove.get(day).get(i));
					}
				}
			}
		}

	public String ShowSchedule(TreeMap<String, TreeMap<Integer, ArrayList<String>>> lectureSchedule)
	{
		//first line
		String outString = "";
		outString = "/----------------------------------------------------------------------------\\\n";
		outString += "|###########|    Mon     |     Tue    |     Wed    |     Thur   |     Fri    |\n|----------------------------------------------------------------------------|\n";
		for (int i = 8; i <= 17; i++)
		{
			outString += String.format("|%02d00 - %02d00" , i, i + 1);
			
			for(int j = 0; j < weekDays.size(); j++)
			{
				String coursCode = "      ";

				try 
				{
					coursCode = lectureSchedule.get(weekDays.get(j)).get(Integer.parseInt(String.format("%02d00" , i))).get(0);
				} 

				catch (Exception e) {}

				outString += "|   " + coursCode + "   ";
				
			}
			outString += "|\n";
			outString += i < 17 ? "|----------------------------------------------------------------------------|\n" : "\\----------------------------------------------------------------------------/\n";
		}

		return outString;
	}

	public void debug() throws Exception
	{
		AutoScheduler autoScheduler = new AutoScheduler(schedule, lectureSchedule);
		ArrayList<String> courseList = new ArrayList<String>(Arrays.asList("CPS209"));
		lectureSchedule = autoScheduler.schedule4Me(3, courseList, false);
	}
		
	public void printSchedule()
	{
		System.out.println(ShowSchedule(lectureSchedule));
	}
	
	//starts the auto scheduler (for auto scheduler)
	public void runAutoScheduler() throws Exception
	{
		AutoScheduler autoScheduler = new AutoScheduler(schedule, lectureSchedule);
		//autoScheduler.initializeMe(lectureSchedule, schedule);
		lectureSchedule = autoScheduler.run();
	}

	//passes the schedul object (for auto scheduler)
	public TreeMap<String,ActiveCourse> passMeSchedule()
	{
		return schedule;
	}

	//passes the lectureSchedule object (for auto scheduler)
	public TreeMap<String, TreeMap<Integer, ArrayList<String>>>  passMeLectureSchedule()
	{
		return lectureSchedule;
	}

	//sets the whole lectureSchedule (for auto scheduler)
	public void setFullSchedul(TreeMap<String, TreeMap<Integer, ArrayList<String>>> lectureSchedule)
	{
		this.lectureSchedule = lectureSchedule;
	}

	public void AutoScheduleSingleCourse(String courseCode, int algorithm) throws Exception
	{
		AutoScheduler autoScheduler = new AutoScheduler(schedule, lectureSchedule);
		ArrayList<String> courseList = new ArrayList<String>(Arrays.asList(courseCode));
		lectureSchedule = autoScheduler.schedule4Me(algorithm, courseList, false);
	}

	public void toFile(String FilePath) throws Exception
	{
		/**
		* This function saves the schedule in the given path
		* This function needs a file path as input
		* The default path is the application directory/Student Transcripts folder.
		* If you use "home" or "loopback" the FilePath will automaticly be the default path
		*/

		if(FilePath.equalsIgnoreCase("HOME") || FilePath.equalsIgnoreCase("LOOPBACK")) 
		{
			FilePath = System.getProperty("user.dir"); //if you want default folder then default forlder! this is the safest way to find the path for home and it should work across all platforms...
			File tempFile = new File(FilePath); //adding the Student Transcripts path
			if(!tempFile.exists()) tempFile.mkdir(); // if Student Transcripts folder does not exist, make one!
			FilePath = tempFile.getAbsolutePath();// get the path again with the database patth added to it
		}

		try 
		{

			File shcedulFile = new File(FilePath, "_Schedule.txt"); // make a file with the name of the student and id
			FileWriter shcedulFileOut = new FileWriter(shcedulFile);

			shcedulFileOut.write(ShowSchedule(lectureSchedule));

			shcedulFileOut.close();// save the file

			System.out.println("\n\n\n    ********* Schedule exported successfully!!!! *********\n\n");
		} 

		catch (Exception e) // handle the problems
		{
			throw new Exception(e.getMessage());
		}
	}
	
}

