/*

Mohammadreza Anvari 
ID: 500983431

*/

//import java.sql.Array; //never used! 
import java.util.ArrayList;
//import java.util.Arrays; //never used! we are using arraylists instead
//import java.util.Collections; //never used! collection used for comparison wich has been done in active course.
import java.util.ListIterator;

//#region       File I/O library imports

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter; // import to write students to file

//#endregion

//for assignment 2
import java.util.TreeMap;
import java.util.Set;

public class Registry 
{
   private TreeMap<String, Student> students = new TreeMap<String, Student>();
   private TreeMap<String, ActiveCourse> courses  = new TreeMap<String, ActiveCourse>();

   private boolean isLocal = false; //this means that the functions are beeing used localy and inside the class itself (more info in readme.txt file)
   
   public Registry()
   {
	// Add some students
	   // in A2 we will read from a file
	   Student s1 = new Student("JohnOliver", "34562");
	   Student s2 = new Student("HarryWindsor", "38467");
	   Student s3 = new Student("SophieBrown", "98345");
	   Student s4 = new Student("FaisalQuereshi", "57643");
	   Student s5 = new Student("GenghisKhan", "25347");
	   Student s6 = new Student("SherryTu", "46532");
	   students.put(s1.getId(), s1);
	   students.put(s2.getId(), s2);
	   students.put(s3.getId(), s3);
	   students.put(s4.getId(), s4);
	   students.put(s5.getId(), s5);
	   students.put(s6.getId(), s6);
	   // sort the students alphabetically - see class Student
	   
	   ArrayList<Student> list = new ArrayList<Student>();
	   
	   // Add some active courses with students
	   String courseName = "Computer Science II";
	   String courseCode = "CPS209";
	   String descr = "Learn how to write complex programs!";
	   String format = "3Lec 2Lab";
	   list.add(s2); list.add(s3); list.add(s4);
	   courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020",list));
	   // Add course to student list of courses
	   s2.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
	   s3.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
	   s4.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
	  
	   // CPS511
	   list.clear();
	   courseName = "Computer Graphics";
	   courseCode = "CPS511";
	   descr = "Learn how to write cool graphics programs";
	   format = "3Lec";
	   list.add(s1); list.add(s5); list.add(s6);
	   courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"F2020",list));
	   s1.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
	   s5.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
	   s6.addCourse(courseName,courseCode,descr,format,"W2020", 0);
	   
	   // CPS643
	   list.clear();
	   courseName = "Virtual Reality";
	   courseCode = "CPS643";
	   descr = "Learn how to write extremely cool virtual reality programs";
	   format = "3Lec 2Lab";
	   list.add(s1); list.add(s2); list.add(s4); list.add(s6);
	   courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,"W2020",list));
	   s1.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
	   s2.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
	   s4.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
	   s6.addCourse(courseName,courseCode,descr,format,"W2020", 0); 
   }
   
   public Registry(String DatabasePath) throws Exception
   {
	   /** This constructor is for using a file as student and class lists. 
		* The two files of students.txt and courses.txt must be in the same place.
		* This constructor needs a file path as input
		* The default path is the application directory/Database folder.
		* If you use "home" or "loopback" the DatabasePath will automaticly be the default path
		*/

		try 
		{
			isLocal = true;

			if(DatabasePath.equalsIgnoreCase("HOME") || DatabasePath.equalsIgnoreCase("LOOPBACK")) 
			{
				DatabasePath = System.getProperty("user.dir"); //if you want default folder then default forlder! this is the safest way to find the path for home and it should work across all platforms...
				File tempFile = new File(DatabasePath, "Database"); //adding the Database path
				DatabasePath = tempFile.getAbsolutePath();// get the path again with the database patth added to it
			}
 
			File studentsDatabase = new File(DatabasePath, "students.txt"); // look for the file
			File coursesDatabase = new File(DatabasePath, "courses.txt");// look for the file

			Scanner scannerCourses = new Scanner(coursesDatabase);// read the lines of the file

			while (scannerCourses.hasNextLine()) //for courses
			{
				Scanner dataLine  = new Scanner(scannerCourses.nextLine());
				dataLine.useDelimiter(";");// read the string upto ;
				
				String courseName = dataLine.next();
				String courseCode = dataLine.next();
				String descr = dataLine.next();
				String format = dataLine.next();
				String semester = dataLine.next();

				//create the class with no students
				courses.put(courseCode, new ActiveCourse(courseName,courseCode,descr,format,semester , new ArrayList<Student>()));
			}

			Scanner scannerStudents = new Scanner(studentsDatabase);

			while (scannerStudents.hasNextLine()) //for students
			{
				Scanner dataLine  = new Scanner(scannerStudents.nextLine());
				dataLine.useDelimiter(";");// read the string upto ;

				while (dataLine.hasNext())
				{
					//add student
					String name = dataLine.next();
					if(!name.matches("^[a-zA-Z]*$")) { dataLine.close(); throw new IllegalArgumentException("Bad File Students.txt"); } 
					String id = dataLine.next();
					if(!id.matches("-?\\d+(\\.\\d+)?") || id.equals(null)) { dataLine.close(); throw new IllegalArgumentException("Bad File Students.txt"); }
					System.out.println(addNewStudent(name, id) ? "\nStudent " + name + " added successfully!" : "\nStudent " + name + " already registered");

					// add the student in their courses
					while (dataLine.hasNext()) addCourse(id, dataLine.next());// as long as there is a course too add, add it!
					//dataLine.next();
				}
			}

			scannerStudents.close(); // close the scanner
			scannerCourses.close();// close the scanner
			isLocal = false;
			System.out.println("\n\n\n    ********* Database imported successfully!!!! *********\n\n");
		} 
		
		catch(FileNotFoundException ex)// handle FileNotFoundException
		{
			isLocal = false;
			throw new FileNotFoundException("\n\nstudents.txt "+ "\033[0;1mOR\033[0;0m" + " courses.txt File Not Found\n\n");
			//Runtime.getRuntime().exit(0);
		}

		catch (Exception ex)// handle other problems
		{
			isLocal = false;
			throw new Exception("\n\n" + ex.getMessage() + "\n\n");
			//Runtime.getRuntime().exit(0);
		}
   }

   public void ExportDatabase(String DatabasePath) throws Exception
   {
	   /**
		* This function saves the database of the students arraylist in the given path
		* The output of this function is a file with a format based on the input format of students.txt
		* This function needs a file path as input
		* The default path is the application directory/Database folder.
		* If you use "home" or "loopback" the DatabasePath will automaticly be the default path
		*/

		if(DatabasePath.equalsIgnoreCase("HOME") || DatabasePath.equalsIgnoreCase("LOOPBACK")) 
		{
			DatabasePath = System.getProperty("user.dir"); //if you want default folder then default forlder! this is the safest way to find the path for home and it should work across all platforms...
			File tempFile = new File(DatabasePath, "Database"); //adding the Database path
			if(!tempFile.exists()) tempFile.mkdir(); // if Database folder does not exist, make one!
			DatabasePath = tempFile.getAbsolutePath();// get the path again with the database patth added to it
		}

		try 
		{
			File studentsDatabase = new File(DatabasePath, "students.txt");
			File coursesDatabase = new File(DatabasePath, "courses.txt");

			
			FileWriter studentsDatabaseOut = new FileWriter(studentsDatabase);
			FileWriter coursesDatabaseOut = new FileWriter(coursesDatabase);

			Set<String> studentsId = students.keySet();

			for(String studentId : studentsId)
			{
				studentsDatabaseOut.write(students.get(studentId).getName() + ";" + students.get(studentId).getId());

				for (Course course : students.get(studentId).courses)
				{
					studentsDatabaseOut.write(";" + course.getCode());
				}
				studentsDatabaseOut.write(System.lineSeparator());
			}

			studentsDatabaseOut.close();// save the file

			Set<String> coursesCode = courses.keySet();

			for (String courseCode : coursesCode)
				{
					coursesDatabaseOut.write(courses.get(courseCode).getName() + ";" + courses.get(courseCode).getCode() + ";" + courses.get(courseCode).getInfo() + ";" + courses.get(courseCode).getFormat() + ";" + courses.get(courseCode).getSemester());
					coursesDatabaseOut.write(System.lineSeparator());
				}
				
				coursesDatabaseOut.close();// save the file

			System.out.println("\n\n\n    ********* Database exported successfully!!!! *********\n\n");
		} 

		catch (Exception e) 
		{
			throw new Exception(e.getMessage());
		}
   }

   public void ExportStudentTranscript(String FilePath, String studentId) throws Exception
   {
	   /**
		* This function saves the transcrip of a student in the given path
		* The output of this function is a file with the same foemat as pst command with the name of the student and their id
		* This function needs a file path and student id as input
		* The default path is the application directory/Student Transcripts folder.
		* If you use "home" or "loopback" the FilePath will automaticly be the default path
		*/

		if(FilePath.equalsIgnoreCase("HOME") || FilePath.equalsIgnoreCase("LOOPBACK")) 
		{
			FilePath = System.getProperty("user.dir"); //if you want default folder then default forlder! this is the safest way to find the path for home and it should work across all platforms...
			File tempFile = new File(FilePath, "Student Transcripts"); //adding the Student Transcripts path
			if(!tempFile.exists()) tempFile.mkdir(); // if Student Transcripts folder does not exist, make one!
			FilePath = tempFile.getAbsolutePath();// get the path again with the database patth added to it
		}

		try 
		{

			File studentTranscript = new File(FilePath, students.get(studentId).getName() + " - " + students.get(studentId).getId()+ ".txt"); // make a file with the name of the student and id
			FileWriter studentTranscriptOut = new FileWriter(studentTranscript);

			for(CreditCourse creditCourse : students.get(studentId).courses) if(!creditCourse.getActive()) //write each inactive course with its grade in the txt file
				studentTranscriptOut.write(creditCourse.getCode() + " " + creditCourse.getName() + " " + creditCourse.getSemester() + " Grade "  + creditCourse.displayGrade() + System.lineSeparator());

			studentTranscriptOut.close();// save the file

			System.out.println("\n\n\n    ********* Student Transcript exported successfully!!!! *********\n\n");
		} 

		catch (Exception e) // handle the problems
		{
			throw new Exception(e.getMessage());
		}
   }

   // Add new student to the registry (students arraylist above) 
   public boolean addNewStudent(String name, String id) 
   {
	   // Create a new student object
	   Student newStudent = new Student(name, id);
	   // check to ensure student is not already in registry
	   // if not, add them and return true, otherwise return false
	   // make use of equals method in class Student
	   if(newStudent.equals(students.get(id))) return false;
	   students.put(id, newStudent);
	   return true;
   }

   // Remove student from registry 
   public boolean removeStudent(String studentId) throws Exception
   {
	   // Find student in students arraylist
	   // If found, remove this student and return true
	   // when we remove a student from the university they must be removed from the active courses too

		Student student = students.get(studentId);
		if(student == null) throw new Exception("404");
		ArrayList<CreditCourse> coursesList = new ArrayList<CreditCourse>(student.courses); 

		for(CreditCourse creditCourse : coursesList)
			dropCourse(studentId, creditCourse.getCode());// drops all of the courses for the removed student, uses the dropc function for all of the courses active and inactive!
		
		student.courses.clear(); //clears the student's courses
		students.remove(student.getId()); //removes the student from registry
		return true; // the student is removed from everything!
   }
   
   // Print all registered students
   public void printAllStudents()
   {
		Set<String> studentsId = students.keySet();

	   for (String studentId : studentsId)
	   {
		   System.out.println("ID: " + students.get(studentId).getId() + " Name: " + students.get(studentId).getName() );   
	   }
	   
   }
   
   // Given a studentId and a course code, add student to the active course
   public void addCourse(String studentId, String courseCode) throws Exception
   {
	   // Find student object in registry (i.e. students arraylist)
	   // Check if student has already taken this course in the past Hint: look at their credit course list
	   // If not, then find the active course in courses array list using course code
	   // If active course found then check to see if student already enrolled in this course
	   // If not already enrolled
	   //   add student to the active course
	   //   add course to student list of credit courses with initial grade of 0
	   String ErrorCode = "-1";
	   Student student = students.get(studentId); if(student == null) throw new Exception("404");
	   ActiveCourse activeCourse = courses.get(courseCode); if(activeCourse == null) throw new Exception("405");

		//if student has that course throw...
		for(CreditCourse creditCourse : student.courses)
			if (creditCourse.getCode().equalsIgnoreCase(courseCode)) 
				if(!isLocal) throw new Exception("Error - Student has already taken this course"); //exeption cuz student has this course in their course list, terminate immediately!
				else if (isLocal) { ErrorCode = "Error - Student has already taken this course" + studentId + courseCode; return;} // if local (File IO) just print the error.

			activeCourse.addStudent(student);
			student.addCourse(activeCourse.getName(), activeCourse.getCode(), activeCourse.getInfo(), activeCourse.getFormat(), activeCourse.getSemester(), 0);
			ErrorCode = "Done";

		if (ErrorCode != "-1" && ErrorCode != "Done" && !isLocal) throw new Exception(ErrorCode);
		else if (ErrorCode != "-1" && ErrorCode != "Done" && isLocal) System.out.print(ErrorCode);
   }
   
   // Given a studentId and a course code, drop student from the active course
   public void dropCourse(String studentId, String courseCode) throws Exception
   {
	   // Find the active course
	   // Find the student in the list of students for this course
	   // If student found:
	   //   remove the student from the active course
	   //   remove the credit course from the student's list of credit courses
	   ActiveCourse activeCourse = courses.get(courseCode);
	   if(activeCourse == null) throw new Exception("405");
	   Student student = students.get(studentId);
	   if(student == null) throw new Exception("404");
	   activeCourse.removeStudent(student);
	   student.removeActiveCourse(courseCode);
   }
   
   // Print all active courses
   public void printActiveCourses() 
   {
	   Set<String> activeCourse = courses.keySet(); 
	   for (String courseCode : activeCourse)
	   {
		   ActiveCourse ac = courses.get(courseCode);
		   System.out.println(ac.getDescription());
	   }
   }

   //just for debug and testing, no effect and algorithms, structure or process
   public void debug() throws Exception 
   {
		// erase registry
		
		// students.clear();
		// courses.clear();
		// System.out.print("\033[H\033[2J");
		// System.out.flush();

		// final String ANSI_RESET = "\u001B[0m";
		// final String ANSI_BLACK = "\u001B[30m";
		// final String ANSI_RED = "\u001B[31m";
		// final String ANSI_GREEN = "\u001B[32m";
		// final String ANSI_YELLOW = "\u001B[33m";
		// final String ANSI_BLUE = "\u001B[34m";
		// final String ANSI_PURPLE = "\u001B[35m";
		// final String ANSI_CYAN = "\u001B[36m";
		// final String ANSI_WHITE = "\u001B[37m";

		// Random r = new Random();

		
		
		// List<String> colours = Arrays.asList(ANSI_RED,ANSI_GREEN,ANSI_YELLOW,ANSI_BLUE,ANSI_PURPLE,ANSI_CYAN,ANSI_WHITE);

		// String text = "Hello There!";
		// int i;
		// System.out.print("				");
		// for(i = 0; i < text.length(); i++){
			
		// 	System.out.printf( colours.get(r.nextInt(colours.size())) + "%c" + ANSI_RESET, text.charAt(i));
		// 	try{
		// 		Thread.sleep(200);
		// 	}
		// 	catch(InterruptedException ex)
		// 	{
		// 		Thread.currentThread().interrupt();
		// 	}
		// }
		// System.out.println(ANSI_RED + "\n\n    This text is red!" + ANSI_RESET);
   }

   // Print the list of students in an active course
   public void printClassList(String courseCode) throws Exception
   {
	   ActiveCourse activeCourse = courses.get(courseCode);
	   if(activeCourse == null) throw new Exception("405");
	   activeCourse.printClassList();
   }
   
   // Given a course code, find course and sort class list by student name
   public void sortCourseByName(String courseCode) throws Exception
   {
		ActiveCourse activeCourse = courses.get(courseCode);
		if(activeCourse == null) throw new Exception("405");
		activeCourse.sortByName();
   }
   
   // Given a course code, find course and sort class list by student name
   public void sortCourseById(String courseCode) throws Exception
   {
		ActiveCourse activeCourse = courses.get(courseCode);
		if(activeCourse == null) throw new Exception("405");
		activeCourse.sortById();
   }
   
   // Given a course code, find course and print student names and grades
   public void printGrades(String courseCode) throws Exception
   {
	   ActiveCourse activeCourse = courses.get(courseCode); if(activeCourse == null) throw new Exception("405");
	   Set<String> studentIdSet = students.keySet();
			for(String studentId : studentIdSet)
			{
				for(CreditCourse creditCourse : students.get(studentId).courses) 
				{
					if(creditCourse.getCode().equalsIgnoreCase(courseCode))
						System.out.println(students.get(studentId).getId() + " " + students.get(studentId).getName() + " " + students.get(studentId).getGrade(courseCode));
				}
			}
	}
   
   // Given a studentId, print all active courses of student
   public void printStudentCourses(String studentId) throws Exception
   {
	   Student student = students.get(studentId);
	   if(student == null) throw new Exception("404");
	   student.printActiveCourses();
   }
   
   // Given a studentId, print all completed courses and grades of student --->  printStudentTranscript should print out only inactive (i.e. completed) creditcourses of a student.
   public void printStudentTranscript(String studentId) throws Exception
   {
	   Student student = students.get(studentId);
	   if(student == null) throw new Exception("404");
	   student.printTranscript();
   }
   
   // Given a course code, student id and numeric grade
   // set the final grade of the student
   public void setFinalGrade(String courseCode, String studentId, double grade) throws Exception
   {
	   // find the active course
	   // If found, find the student in class list
	   // then search student credit course list in student object and find course
	   // set the grade in credit course and set credit course inactive
	   String ErrorCode = "-1";
	   if(grade > 100) throw new IllegalArgumentException("Final Grade Cannot Be more than 100");
	   if(grade < 0) throw new IllegalArgumentException("Final Grade Cannot Be less than 0");

	   ActiveCourse activeCourse = courses.get(courseCode);
	   if(activeCourse == null) throw new Exception("405");
	   Student student = students.get(studentId);
	   if(student == null) throw new Exception("404");
	   
	   for(CreditCourse creditCourse : student.courses)
	   		if(creditCourse.getCode().equalsIgnoreCase(courseCode))
				{
					student.setGrade(courseCode, grade);
					ErrorCode = "Done";
				} else if (ErrorCode != "Done") ErrorCode = "Student does not have this course";

		if (ErrorCode != "-1" && ErrorCode != "Done" && !isLocal) throw new Exception(ErrorCode);
		else if (ErrorCode != "-1" && ErrorCode != "Done" && isLocal) System.out.print(ErrorCode + " " + courseCode + " " + studentId);

		boolean isFullGraded = true;

		Set<String> studentIdSet = students.keySet();
		for(String studentIdinClass : studentIdSet)
		{
			for(CreditCourse creditCourse : students.get(studentIdinClass).courses) 
			{
				if(creditCourse.getCode().equalsIgnoreCase(courseCode))
				{
					if(students.get(studentIdinClass).getGrade(courseCode) <= 0)
					isFullGraded = false;
				}
			}
		}

		if(isFullGraded) 
		{
			courses.get(courseCode).clearClassList();
			System.out.println("\nCourse list cleared");
		}
		
   }
  
   // this function passes the courses object, used for the schduler at the begining of main according to the instructions
   public TreeMap<String, ActiveCourse> passCourses()
   {
	   return courses;
   }

   // this is to clear the whole registry if neaded.
   public void clear()
   {
		students.clear();
		courses.clear();
   }
}