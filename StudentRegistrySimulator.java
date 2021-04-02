/*

Mohammadreza Anvari 
ID: 500983431

*/

//import java.util.ArrayList; // no use since this class only uses the regisstry and all the other classes it does not make or use any arraylists.
import java.lang.instrument.IllegalClassFormatException;
import java.util.Scanner;

// this is to make beep when there is a problem!!!
//import java.awt.*;


public class StudentRegistrySimulator 
{
  public static void main(String[] args) throws Exception
  {
	  Registry registry;

	  java.io.BufferedReader temp;
	  String input;

	  try 
	  {
		//if you wanna use the file since the aasignmet 1 was designed to be optional, this one also gives the option to choose from file or local registry.
		System.out.println("\nYou have the option to import your student and course lists from a .txt database stored in the Database folder! (check README.txt for more info)\nWanna do it?! Y or N");
		temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scannet for user input no need for scanner.close! YAY!
		input = temp.readLine();// read user input to confirm
		if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) registry = new Registry("home");
		else registry = new Registry();

		//create the schdule object
		
	  } 

	  catch (Exception e) 
	  {
		  System.out.println(e.getMessage());
		  return;
	  }

	  Scheduler scheduler = new Scheduler(registry.passCourses());

	  Scanner scanner = new Scanner(System.in);
	  System.out.print("> ");
	  
	  while (scanner.hasNextLine())
	  {
		// this prevents a typo to throw an exception also, handlws any other input problem(just for the ease of use no effect on the process or algorithms)
		try {

		  String inputLine = scanner.nextLine();
		  if (inputLine == null || inputLine.equals("")) continue;
		  
		  Scanner commandLine = new Scanner(inputLine); 
		  String command = commandLine.next();

		  if (command == null || command.equals("")) continue;

		  else if (command.equalsIgnoreCase("L") || command.equalsIgnoreCase("LIST"))
		  {
			  System.out.println(); // just too look better!
			  registry.printAllStudents();
		  }
		  else if (command.equalsIgnoreCase("Q") || command.equalsIgnoreCase("QUIT"))
		  {
			  System.out.println();// just too look better!
			  commandLine.close(); // to fix the warrning 'Resource leak'
			  return;
		  }
		  
		  else if (command.equalsIgnoreCase("REG"))
		  {
			  // register a new student in registry
			  // get name and student id string 
			  // e.g. reg JohnBoy 74345
			  // Checks:
			  //  ensure name is all alphabetic characters
			  //  ensure id string is all numeric characters
				String nextInput = commandLine.next();
				String name = nextInput;
				nextInput = commandLine.next();
				String id = nextInput.toUpperCase();
				if(!isNumeric(id)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in ID " + id); } //exeption cuz id is not numeric
				if(!isStringOnlyAlphabet(name)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in Name " + name); } //exeption cuz id is not numeric
				System.out.println(registry.addNewStudent(name, id) ? "\nStudent added successfully!" : "\nStudent " + name + " already registered");
		  }
		  else if (command.equalsIgnoreCase("DEL"))
		  {
			  // delete a student from registry
			  // get student id
			  // ensure numeric
			  // remove student from registry
			  String nextInput = commandLine.next();
			  String id = nextInput;
			  if(!isNumeric(id)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in ID " + id); } //exeption cuz id is not numeric
			  System.out.println(registry.removeStudent(id) ? "\nStudent removed successfully!" :  "\nUNKNOWN ERROR!!!");
		  }
		  
		  else if (command.equalsIgnoreCase("ADDC"))
		  {
			 // add a student to an active course
			 // get student id and course code strings
			 // add student to course (see class Registry)
				String nextInput = commandLine.next();
				String id = nextInput;
				nextInput = commandLine.next();
				String course = nextInput.toUpperCase();
				if(!isNumeric(id)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in ID " + id); } //exeption cuz id is not numeric
				registry.addCourse(id, course);
				System.out.println("\nCourse added successfully!");
				
		  }
		  else if (command.equalsIgnoreCase("DROPC"))
		  {
			  // get student id and course code strings
			  // drop student from course (see class Registry)
				String nextInput = commandLine.next();
				String id = nextInput;
				nextInput = commandLine.next();
				String course = nextInput.toUpperCase();
				if(!isNumeric(id)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in ID " + id); } //exeption cuz id is not numeric
				registry.dropCourse(id, course);
				System.out.println("\nCourse dropped successfully!");
		  }
		  else if (command.equalsIgnoreCase("PAC"))
		  {
			  // print all active courses
			  System.out.println();
			  registry.printActiveCourses();
		  }		  
		  else if (command.equalsIgnoreCase("PCL"))
		  {
			  // get course code string
			  // print class list (i.e. students) for this course
			  System.out.println();
			  registry.printClassList(commandLine.next().toUpperCase());
		  }
		  else if (command.equalsIgnoreCase("PGR"))
		  {
			  // get course code string
			  // print name, id and grade of all students in active course
			  System.out.println();
			  registry.printGrades(commandLine.next().toUpperCase());
		  }
		  else if (command.equalsIgnoreCase("PSC"))
		  {
			  // get student id string
			  // print all credit courses of student
			  System.out.println();
			  registry.printStudentCourses(commandLine.next().toUpperCase());
			  
		  }
		  else if (command.equalsIgnoreCase("PST"))
		  {
			  // get student id string
			  // print student transcript
			  System.out.println();
			  registry.printStudentTranscript(commandLine.next());
		  }
		  else if (command.equalsIgnoreCase("SFG"))
		  {
			  // set final grade of student
			  // get course code, student id, numeric grade
			  // use registry to set final grade of this student (see class Registry)
			  String nextInput = commandLine.next();
			  String courseCode = nextInput.toUpperCase();
			  nextInput = commandLine.next();
			  String id = nextInput;
			  nextInput = commandLine.next();
			  String grade = nextInput;
			  if(!isNumeric(id)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in ID " + id); } //exeption cuz str is not numeric
			  if(!isNumeric(grade)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in grade " + grade); } //exeption cuz grade is not numeric
			  registry.setFinalGrade(courseCode, id, Double.parseDouble(grade));
			  System.out.println("\nDone!");
		  }
		  else if (command.equalsIgnoreCase("SCN"))
		  {
			  // get course code
			  // sort list of students in course by name (i.e. alphabetically)
			  // see class Registry
			  registry.sortCourseByName(commandLine.next().toUpperCase());
			  System.out.println("\nDone!");
		  }
		  else if (command.equalsIgnoreCase("SCI"))
		  {
			// get course code
			// sort list of students in course by student id
			// see class Registry
			registry.sortCourseById(commandLine.next().toUpperCase());
			System.out.println("\nDone!");
		  }

		  else if (command.equalsIgnoreCase("DEBUG"))// just for debug... no effect on algorithms or structure
		  {
			//   System.out.println("\nThis will erase the registry! Are you sure? Y or N");
			//   temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scannet for user input no need for scanner.close! YAY!
			//   input = temp.readLine();
			//   if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) registry.debug();
			//   else 
			//   {
			// 	  commandLine.close();
			// 	  throw new Exception("You chose NO!");
			//   }	  
			  //registry.debug(commandLine.next());
			  scheduler.debug();
		  }

		  else if (command.equalsIgnoreCase("HELP") || command.equalsIgnoreCase("H"))// help based on the A1.doc file, has all of the valid commands and their inputs
		  {
			System.out.println("\na. \"L\" : list all the students in the registry. This one has been done for you.");
			System.out.println("b. \"Q\" : quit out of the program. Also done for you.");
			System.out.println("c. \"REG\" :register a student. Reads a student name and student id from the commandLine scanner (see code). Uses Registry method to register the new student.Just make up a 5 digit id.");
			System.out.println("d. \"DEL\": deletes a student from the registry.");
			System.out.println("e. \"ADDC\": adds a student to an active course ");
			System.out.println("f. \"DROPC\": drops a student from an active course");
			System.out.println("g. \"PAC\" : prints all active course ");
			System.out.println("h. \"PCL\" : prints class list for an active course ");
			System.out.println("i. \"PGR\" : prints student id and grade for all students in an active course ");
			System.out.println("j. \"PSC\" : prints all credit courses for a student ");
			System.out.println("k. \"SFG\" : Set final grade of a student in a course ");
			System.out.println("l. \"SCN\" : sort list of students in a course by student name");
			System.out.println("m. \"SCI\" : sort list of students in a course by student id  ");
			System.out.println("n. \"PST\" : prints student transcript for a student ");
			System.out.println("\n$. \"importFromFile DatabasePath\" : clears the existing registry and created a fresh one using the student.txt and courses.txt located at DatabasePath");
			System.out.println("$$. \"ExportToFile DatabasePath\" : Exports the current registry for students and courses to students.txt and courses.txt with the correct format for import");
			System.out.println("$$$. \"estToFile DatabasePath StudentID\" : Exports the student transcript to a file with the name and the student id\n");

			//New functions for assignment 2:
			System.out.println("\nNew commands:");
			System.out.println("a. \"SCH\": ourseCode day start duration. Schedules a course for a certain day, start time and duration.");
			System.out.println("b. \"CSCH courseCode\": Clears the schedule of the given course");
			System.out.println("c. \"PSCH\": Prints the entire schedule.");
			System.out.println("$. \"CLS\" or \"CLEAR\": Clears the whole registry including students and courses.");

			System.out.println("\n\nAuto scheduler:");
			System.out.println("$. \"SCH AUTO\" : Enters the full automatic scheduler interface.");
			System.out.println("$$. \"SCH courseCode AUTO intAlgorithm\" : Auto schedules a given course with the selected algorithm.");
			System.out.println("$$$. \"SCH courseCode AUTO\" : Auto schedules a given course with the default algorithm.");

			System.out.println("\n$$$$. \"SCH tofile FilePath\" : Exports the schedule to any given path as a .txt file.");
		  }

		  else if (command.equalsIgnoreCase("importFromFile"))
		  {

			String OS = System.getProperty("os.name").toLowerCase();// windows cmd does not support ANSI codes....
			if(!(OS.indexOf("win") >= 0)) System.out.println("\nThis will " + "\033[0;1mERASE\033[0;0m" + " the registry! Are you sure? Y or N");
			else System.out.println("\nThis will ERASE the registry! Are you sure? Y or N");
			temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scannet for user input no need for scanner.close! YAY!
			input = temp.readLine();// read user input to confirm
			if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) registry.clear();
			else 
			{
				System.out.println("\nLike You Wanna append the stuff?! Y or N");
				temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
				input = temp.readLine();
				if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO"))
				{
					commandLine.close();
					throw new Exception("You chose NO!");
				}
			}	  
			registry = new Registry(commandLine.next());
		  }

		  else if (command.equalsIgnoreCase("exportToFile"))
		  {
			String OS = System.getProperty("os.name").toLowerCase();// windows cmd does not support ANSI codes....
			if(!(OS.indexOf("win") >= 0)) System.out.println("\nThis will "+ "\033[0;1mREPLACE\033[0;0m" + " the Database! Are you sure? Y or N");
			else System.out.println("\nThis will REPLACE the Database! Are you sure? Y or N");
			temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scannet for user input no need for scanner.close! YAY!
			input = temp.readLine();// read user input to confirm
			if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO"))
			{
				commandLine.close();
				throw new Exception("You chose NO!");
			}	  
			registry.ExportDatabase(commandLine.next());
		  }

		  else if (command.equalsIgnoreCase("estToFile"))
		  {
			String OS = System.getProperty("os.name").toLowerCase();// windows cmd does not support ANSI codes....
			if(!(OS.indexOf("win") >= 0)) System.out.println("\nThis will "+ "\033[0;1mREPLACE\033[0;0m" + " the student transcript! Are you sure? Y or N");
			else System.out.println("\nThis will REPLACE the student transcript! Are you sure? Y or N");
			temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scanner for user input no need for scanner.close! YAY!
			input = temp.readLine();// read user input to confirm
			if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO"))
			{
				commandLine.close();
				throw new Exception("You chose NO!");
			}
			String path = commandLine.next();
			String id = commandLine.next();
			if(!isNumeric(id)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in ID " + id); } //exeption cuz id is not numeric	  
			registry.ExportStudentTranscript(path, id);
		  }

		  else if (command.equalsIgnoreCase("SCH"))
		  {
			// schedule a course
			String nextInput = commandLine.next();
			// run auto scheduler
			if(nextInput.equalsIgnoreCase("TOFILE"))
			{
				scheduler.toFile(commandLine.next());
			}
			else if(nextInput.equalsIgnoreCase("AUTO")) scheduler.runAutoScheduler();

			else
			{
				String courseCode = nextInput.toUpperCase();
				nextInput = commandLine.next();
				if(nextInput.equalsIgnoreCase("AUTO")) // single course auto schedule
				{
					if(commandLine.hasNext()) nextInput = commandLine.next();
					else nextInput	= "3"; //default algorithm is 3 (straightForward) 
					String algorithm = nextInput;
					if(!isNumeric(algorithm)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in mode " + algorithm); } //exeption cuz str is not numeric
					if(Integer.parseInt(algorithm) > 4 || Integer.parseInt(algorithm) < 0)
						{ commandLine.close(); throw new IllegalClassFormatException("There are only 4 algorithms starting from 1 to 4..."); } //exeption cuz str is not numeric
					scheduler.AutoScheduleSingleCourse(courseCode, Integer.parseInt(algorithm));
				}

				else
				{
					String day = nextInput;
					nextInput = commandLine.next();
					String startTime = nextInput;
					nextInput = commandLine.next();
					String duration = nextInput;
					if(!isNumeric(startTime)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) in start time " + startTime); } //exeption cuz str is not numeric
					if(!isNumeric(duration)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) duration " + duration); } //exeption cuz grade is not numeric
					if(!isStringOnlyAlphabet(day)) { commandLine.close(); throw new IllegalClassFormatException("Invalid Character(s) day " + day); } //exeption cuz grade is not numeric
					scheduler.setDayAndTime(courseCode, day.toUpperCase(), Integer.parseInt(startTime), Integer.parseInt(duration));
				}

				System.out.println("\n\nDone!");
			}
		  }

		  else if (command.equalsIgnoreCase("CSCH"))
		  {
			//clear the schdule for a course
			scheduler.clearSchedule(commandLine.next().toUpperCase());
			System.out.println("\nDone!");
		  }
		  
		  else if (command.equalsIgnoreCase("PSCH"))
		  {
			//print the schedule
			System.out.println();
			System.out.println();
			scheduler.printSchedule();
		  }

		  else if (command.equalsIgnoreCase("CLS") || command.equalsIgnoreCase("CLEAR"))
		  {
			  String OS = System.getProperty("os.name").toLowerCase();// windows cmd does not support ANSI codes....
			  if(!(OS.indexOf("win") >= 0)) System.out.println("\nThis will " + "\033[0;1mERASE\033[0;0m" + " the registry! Are you sure? Y or N");
			  else System.out.println("\nThis will ERASE the registry! Are you sure? Y or N");
			  temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scannet for user input no need for scanner.close! YAY!
			  input = temp.readLine();
			  if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) registry.clear();
			  else 
			  {
				  commandLine.close();
				  throw new Exception("You chose NO!");
			  }	 

			  //cleans the console!
			  System.out.print("\033[H\033[2J");
			  System.out.flush();
			  System.out.println("\nDone!");
		  }

		  else if (command.equalsIgnoreCase("SCHAUTO"))
		  {
			  scheduler.runAutoScheduler();
		  }

		  else System.out.println("\nCommand not found, use \"help\" or \"h\" "); // for the commads that are not recognized.
		  commandLine.close(); // to fix the warrning 'Resource leak'
		}

		catch (Exception ex)
		{
			//Toolkit.getDefaultToolkit().beep();
			System.out.println();
			System.out.println(ex.getMessage() == null ? "Error! Please Check the Input... \n\n      use \"help\"" : ex.getMessage().equals("404") ? "Student not Found" : ex.getMessage().equals("405") ? "Course not Found" : ex.getMessage());
		}
		  System.out.print("\n> ");
	  }
	  scanner.close(); // to fix the warrning 'Resource leak'
  }
  
  public static boolean isStringOnlyAlphabet(String str) 
  { 
      // write method to check if string str contains only alphabetic characters 
	  return ((str != null) && (!str.equals("")) && (str.matches("^[a-zA-Z]*$"))); //returns true only if it is not null "" or alphabetic **** based on code from geeksforgeeks: https://www.geeksforgeeks.org/check-if-a-string-contains-only-alphabets-in-java-using-regex/
  } 
  
  public static boolean isNumeric(String str)
  {
	  // write method to check if string str contains only numeric characters
	  // Check if a string is numeric or not using regular expressions (regex): this works better than the traditional try catch becuase if the last character of a number is f try catch will convert it to double!!!
	  if (str == null) return false;
	  return str.matches("-?\\d+(\\.\\d+)?");
  }
}