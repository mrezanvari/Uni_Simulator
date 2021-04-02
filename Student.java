/*

Mohammadreza Anvari 
ID: 500983431

*/

import java.util.ArrayList;

// Make class Student implement the Comparable interface
// Two student objects should be compared by their name
public class Student implements Comparable<Student>
{
  private String name;
  private String id;
  public  ArrayList<CreditCourse> courses;
  
  
  public Student(String name, String id)
  {
	 this.name = name;
	 this.id   = id;
	 courses   = new ArrayList<CreditCourse>();
  }
  
  public String getId()
  {
	  return id;
  }
  
  public String getName()
  {
	  return name;
  }

  public double getGrade(String courseCode)
  {
    for(CreditCourse course : courses) if(course.getCode().equalsIgnoreCase(courseCode))
    {
      return course.getScore();
    } 
    return 0;
  }

  // set the score since score is private and in the creditCourse we need a method to access it. new fuction here for ease of access and programming
  public void setGrade(String courseCode, double score)
	{
		for(CreditCourse course : courses) if(course.getCode().equalsIgnoreCase(courseCode))
    {
      course.setGrade(score);
      course.setInactive();
    } 
	}
  
  // add a credit course to list of courses for this student
  public void addCourse(String courseName, String courseCode, String descr, String format,String sem, double grade)
  {
    // create a CreditCourse object
    CreditCourse course4Credit = new CreditCourse(courseName, courseCode, descr, format, sem, grade);
    // set course active
    course4Credit.setActive();
    // add to courses array list
    courses.add(course4Credit);
  }

  // Prints a student transcript
  // Prints all completed (i.e. non active) courses for this student (course code, course name, 
  // semester, letter grade
  // see class CreditCourse for useful methods
  public void printTranscript()
  {
    for(CreditCourse creditCourse : courses) if(!creditCourse.getActive())
      System.out.println(creditCourse.getCode() + " " + creditCourse.getName() + " " + creditCourse.getSemester() + " Grade "  + creditCourse.displayGrade());
  }
  
  // Prints all active courses this student is enrolled in
  // see variable active in class CreditCourse
  public void printActiveCourses()
  {
    for(CreditCourse creditCourse : courses) if(creditCourse.getActive())
      System.out.println(creditCourse.getDescription() + "\n " );
  }
  
  // Drop a course (given by courseCode)
  // Find the credit course in courses arraylist above and remove it
  // only remove it if it is an active course
  public void removeActiveCourse(String courseCode)
  {
    for (CreditCourse course : courses) if (course.getCode().equalsIgnoreCase(courseCode))
    {
      courses.remove(course);
      break; // since we are removing an element from arraylist we need to stop here cuse it will raise an error since the size of arraylist has been changed...
    }
  }
  
  public String toString()
  {
	  return "Student ID: " + id + " Name: " + name;
  }
  
  // override equals method inherited from superclass Object
  // if student names are equal *and* student ids are equal (of "this" student
  // and "other" student) then return true
  // otherwise return false
  // Hint: you will need to cast other parameter to a local Student reference variable
  @Override
  public boolean equals(Object other)
  {
    if(other == null) return false;
    Student otherStudent = (Student) other;
    if (otherStudent.getName().equalsIgnoreCase(this.name) && otherStudent.getId().equalsIgnoreCase(this.id)) return true;
	  return false;
  }

    // overriding the compareTo function for student to compare the student names 
    @Override
    public int compareTo(Student otherStudent) 
    {
        //uses compareto from String cuz both names are string and comparable but uses Ignor case to ignor the upper or lower case names:  James == james
        return this.name.compareToIgnoreCase(otherStudent.name);
    }
}
