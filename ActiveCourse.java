/*

Mohammadreza Anvari 
ID: 500983431

*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// Active University Course
 
public class ActiveCourse extends Course implements Comparable<ActiveCourse>
{
	private ArrayList<Student> students; 
   private String             semester;
   
   private String lectureDay;
   private int lectureStart;
   private int lectureDuration = 0;
	
	   
   // Add a constructor method with appropriate parameters
   // should call super class constructor to initialize inherited variables
   // make sure to *copy* students array list being passed in into new arraylist of students
   // see class Registry to see how an ActiveCourse object is created and used
   public ActiveCourse(String name, String code, String descr, String fmt,String semester,ArrayList<Student> students)
   {
	   super(name, code, descr, fmt);
      this.semester = semester;
      this.students = new ArrayList<Student>(students);
   }
   
   public String getSemester()
   {
	   return semester;
   }
   
   // Prints the students in this course  (name and student id)
   public void printClassList()
   {
      for(Student student : students)
      {
         System.out.println("Student ID: " + student.getId() + " Name: " + student.getName());
      }
   }
   
   // Prints the grade of each student in this course (along with name and student id)
   // 
   public void printGrades()
   {
	   for(Student student : students)
      {
         System.out.println("Student ID: " + student.getId() + " Name: " + student.getName() + " Grade: " + getGrade(student.getId()));
      }
   }
   
   // Returns the numeric grade in this course for this student
   // If student not found in course, return 0 
   public double getGrade(String studentId)
   {
	  // Search the student's list of credit courses to find the course code that matches this active course
	  // see class Student method getGrade() 
     // return the grade stored in the credit course object
     for(Student student : students) if (student.getId() == studentId )
     {
         return student.getGrade(this.getCode());
     }
     return 0; 
   }
   
   //adds a student in the course list since the student arraylist is private we need a function to access it.
   public void addStudent(Student student) 
   {
      students.add(student);
   }

   // removes a stdent from course list since the student arraylist is private we need a function to access it.
   public void removeStudent(Student student) 
   {
      student.removeActiveCourse(super.getCode());
      students.remove(student);
   }

   // Returns a String containing the course information as well as the semester and the number of students 
   // enrolled in the course
   // must override method in the superclass Course and use super class method getDescription()
   public String getDescription()
   {
	   return super.getDescription() + " " + this.getSemester() + " Enrolment: " + students.size() + "\n";
   }
    
   // Sort the students in the course by name using the Collections.sort() method with appropriate arguments
   // Make use of a private Comparator class below
   public void sortByName()
   {
      // sort using name
 	  Collections.sort(students, new NameComparator());
   }
   
   // Fill in the class so that this class implement the Comparator interface
   // This class is used to compare two Student objects based on student name
   private class NameComparator implements Comparator<Student>
   {
      
      public int compare(Student a, Student b)
	   {
         //can use the student.compareTo() since it uses name;
		   return a.compareTo(b);
	   }
   }
   
   // Sort the students in the course by student id using the Collections.sort() method with appropriate arguments
   // Make use of a private Comparator class below
   public void sortById() 
   {
      // sort suing id
 	  Collections.sort(students, new IdComparator());
   }
   
   // Fill in the class so that this class implement the Comparator interface
   // This class is used to compare two Student objects based on student id
   private class IdComparator implements Comparator<Student>
   {
   	public int compare(Student a, Student b)
	   {
         // comparing the ids
         return a.getId().compareTo(b.getId());
      }
   }

   //to set the schedule day, hour and duration for a course
   public void setSchedule(String day, int startTime, int duration)
   {
      this.lectureDay = day;
      if (duration == 0) this.lectureDuration = 0;
      this.lectureDuration += duration;
      this.lectureStart = startTime;
   }

   //to get the schedule day for a course
   public String getScheduleDay()
   {
	   return this.lectureDay;
   }

   //to get the schedule duration for a course
   public int getScheduleDuration()
   {
	   return this.lectureDuration;
   }

   //to get the schedule start time for a course
   public int getScheduleStartTime()
   {
	   return this.lectureStart;
   }

   @Override
  public boolean equals(Object other)
  {
      if(other == null) return false;
      ActiveCourse otherCourse = (ActiveCourse) other;

      //if there is no schedule getting the values will throw an exception
      try 
      {
         if (this.lectureDay.equalsIgnoreCase(otherCourse.getScheduleDay()))
         {
            ActiveCourse fisrtCourse = (ActiveCourse) this;
            ActiveCourse secondCourse = (ActiveCourse) this;;

            if (Integer.compare(otherCourse.getScheduleStartTime(), this.lectureStart) == 0)
               return true;

            else if (Integer.compare(otherCourse.getScheduleStartTime(), this.lectureStart) == 1)
            {
               fisrtCourse = (ActiveCourse) this;
               secondCourse = (ActiveCourse)otherCourse;
            } 
               
            else if (Integer.compare(otherCourse.getScheduleStartTime(), this.lectureStart) == -1)
            {
               fisrtCourse = (ActiveCourse)otherCourse;
               secondCourse = (ActiveCourse) this;
            }
               
            for (int i = 1; i < fisrtCourse.getScheduleDuration(); i++)
            {  
               if(fisrtCourse.getScheduleStartTime() + (i * 100) == secondCourse.getScheduleStartTime())
                  return true;
            }
         }
      } 
      catch (Exception e) { }

      return false;
  }

  @Override
  public int compareTo(ActiveCourse o) // almost useles! cuz equals handles all this!
  {
     /**
      * CompareTo function will return a value based on the status of two courses compared to each other, 0 if they have exactly the same schedule, 1 if lecture day and start 
      time are the same but the duration is more for the first one. -1 is the opposite of 1. a value of -2 will also be returned if none of the obove applies. This is used for scheduler 
      and automation of it.
      */
      
      if (this.lectureDay.equalsIgnoreCase(o.getScheduleDay()) && this.lectureStart == o.getScheduleStartTime() && this.lectureDuration == o.getScheduleDuration()) return 0; //both equal

      //if there is no schedule getting the values will throw an exception
      try 
      {
         if(this.lectureDay.equalsIgnoreCase(o.getScheduleDay()) && this.lectureStart == o.getScheduleStartTime() && this.lectureDuration > o.getScheduleDuration()) return 1; //first one is longer
         if(this.lectureDay.equalsIgnoreCase(o.getScheduleDay()) && this.lectureStart == o.getScheduleStartTime() && this.lectureDuration < o.getScheduleDuration()) return -1; //second one is longer
      } 
      catch (Exception e) { }
      

      return -2;// if none of the above applies...
  }

  public void clearClassList()// tovlear the class list when all of the student are marked...
  {
     this.students.clear();
  }

}
