/*

Mohammadreza Anvari 
ID: 500983431

*/

public class CreditCourse  extends Course
{
	// add a constructor method with appropriate parameters
	// should call the super class constructor
	private String semester;
	private double score;
	private boolean isActive = true;

	public CreditCourse(String name, String code, String descr, String fmt,String semester, double grade)
	{
		super(name, code, descr, fmt);
		this.semester  = semester;
		score = grade;
	}

	// returns the semester of the current creditcourse since its private(used for the formating of the student transript)
	public String getSemester()
	{
		return semester;
	}
	
	public boolean getActive()
	{
		return isActive;
	}
	
	public void setActive()
	{
		isActive = true;
	}
	
	public void setInactive()
	{
		isActive = false;
	}

	public double getScore() //score is private so we need a fuction to access it
	{
		return score;
	}

	public void setGrade(double grade)// set the score since score is private we need a function to access it.
	{
		score = grade;
	}
	
	public String displayGrade()
	{
		// Change line below and print out info about this course plus which semester and the grade achieved
		// make use of inherited method in super class
		return super.convertNumericGrade(score); //super class is course so use convernumericgrade()
	}
	
}