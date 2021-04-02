/*

Mohammadreza Anvari 
ID: 500983431

*/

public class Course 
{
	private String code;
	private String name;
	private String description;
	private String format;
	   
	public Course()
	{
	  this.code        = "";
	  this.name        = "";
	  this.description = "";
	  this.format      = "";
	}
	   
	public Course(String name, String code, String descr, String fmt)
	{
	  this.code        = code;
	  this.name        = name;
	  this.description = descr;
	  this.format      = fmt;
	}
	   
	public String getCode()
	{
	   return code;
	}
	   
	public String getName()
	{
	  return name;
	}
	   
	public String getFormat()
	{
	  return format;
	}
	   
	public String getDescription()
	{
	  return code + " - " + name + "\n" + description + "\n" + format;
	}
	
	 public String getInfo()
	 {
	   // return code + " - " + name; //original code
	   // my change: since this function is only called once and for addc, otherwise it would cause problem for formating and course description of a course for the new students
	   return description; 
	 }
	 
	 // static method to convert numeric score to letter grade string 
	 // e.g. 91 --> "A+"
	 public static String convertNumericGrade(double score)
	 {
		//based on lab3 CourseGrade and ryerson grading chart (https://www.ryerson.ca/registrar/faculty/grading/gradescales_ugrad/)
		
		final double A = 86, AMINUS = 80, B = 73, BMINUS = 70, C = 63, CMINUS = 60, D = 53, DMINUS = 50;

		final double THRESHOLD = 4; //letter grades change by 4 in their corresponding numeric score
		if(score < DMINUS) return "F";
		else if(score < D) return "D-";
		else if (Math.abs((score - D)) < THRESHOLD) return "D";
		else if(score < CMINUS && score > D) return "D+";
		else if(score < C) return "C-";
		else if (Math.abs((score - C)) < THRESHOLD) return "C";
		else if(score > C && score < BMINUS) return "C+";
		else if(score < B) return "B-";
		else if (Math.abs((score - B)) < THRESHOLD) return "B";
		else if(score > B && score < AMINUS) return "B+";
		else if(score < A) return "A-";
		else if (Math.abs((score - A)) < THRESHOLD) return "A";
		return "A+";
	 }
	 
}
