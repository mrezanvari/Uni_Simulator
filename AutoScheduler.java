import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeMap;

import java.util.Random;

public class AutoScheduler extends Scheduler // so i can use all the functions and stuff and also, well it makes sence...!
{

    /**
     * This is the AutoScheduler class, extending the scheduler. This class runs almost as an application inside an application and seperate frim the 
     * StudentRegistrySimulator. This class creates a schedule based on the semester of the courses, determined by the user. Then it will ask for some prefrences
     * and options for the schedule. These option are just different algorithms that the auto scheduler will use in order to create the schedule. The schedule will
     * be accessible and editable afterwards since it replaces the lectureSchedule object from the scheduler. Unfortunately this class and its interface uses ANSI
     * codes to render colors to the terminal. This functionality only works in UNIX based terminals or terminal emulators. This means that windows cmd will not be
     * able to render the colors or some of the effects. So to not have any issues with other operating systems, the ANSI codes will not be added to the texts if 
     * the program is running in a windows environment...
     * 
     * Honestly tho... if you have access to a mac or maybe linux you sould give it a try... :)
     * 
     */
    
    //cool console colors! public for everywhere!!!
    public final String ANSI_RESET = "\u001B[0m";
	//final String ANSI_BLACK = "\u001B[30m";
	public final String ANSI_RED = "\u001B[31m";
	public final String ANSI_GREEN = "\u001B[32m";
	public final String ANSI_YELLOW = "\u001B[33m";
	public final String ANSI_BLUE = "\u001B[34m";
	public final String ANSI_PURPLE = "\u001B[35m";
	public final String ANSI_CYAN = "\u001B[36m";
    public final String ANSI_WHITE = "\u001B[37m";
    //list of colors
    List<String> colours = Arrays.asList(ANSI_RED,ANSI_GREEN,ANSI_YELLOW,ANSI_BLUE,ANSI_PURPLE,ANSI_CYAN,ANSI_RED,ANSI_GREEN,ANSI_YELLOW,ANSI_BLUE,ANSI_PURPLE,ANSI_CYAN,ANSI_WHITE);

    //for inputs
    java.io.BufferedReader temp;
	String input;

    private TreeMap<String,ActiveCourse> courseList;
    private TreeMap<String, TreeMap<Integer, ArrayList<String>>> AutoLectureSchedule = new TreeMap<String, TreeMap<Integer, ArrayList<String>>>(); 
    
    // for the type of algorithm chosed by the user...
    enum ScheduleAlgorithm{
        MostDaysOff,
        Balanced,
        straightForward,
        Random
    }

    public AutoScheduler(TreeMap<String,ActiveCourse> schedule, TreeMap<String, TreeMap<Integer, ArrayList<String>>> lectureSchedule)
    {
        //creates a copy of the current scheduler object and its variables
        super(schedule);
        courseList = super.passMeSchedule();
        AutoLectureSchedule = lectureSchedule;
    }

    public TreeMap<String, TreeMap<Integer, ArrayList<String>>> run() throws Exception
    {
        //welcome screen!
        String OS = System.getProperty("os.name").toLowerCase();//clears the terminal but its different for different OS's...
        if(!(OS.indexOf("win") >= 0)) System.out.print("\033[H\033[2J");
        else new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		System.out.flush();
        System.out.print("			    	 ");
        printEffectRainbow("Hello There!", 100);
        System.out.print("\n\n\n	          	");
        printEffectColor("Welcome To Auto Scheduler!!!", 20, ANSI_CYAN); 
        System.out.print("\n\n\n   ");
        printEffectColor("This tool will automatically create a customized schedule to your preference!", 20, ANSI_PURPLE);  
        System.out.print("\n\n\n\n\n");
        printEffectColor("   You can quit at eny time by inputing \"q\" when you are asked to...", 20, ANSI_YELLOW); 
        System.out.print("\n\n\n\n\n");
        printEffectRainbow("Let's get", 20);
        printEffectColor(" serious... :)", 20, ANSI_WHITE);

        boolean isOk2Proceed = false;
        String semester = input;
        ArrayList<String> pickedCourses = new ArrayList<String>();

        while(!isOk2Proceed)
        {
            System.out.println();
            printEffectColor("Please input the semester of the schedule:\n> ", 10, ANSI_WHITE);
            temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scannet for user input no need for scanner.close! YAY!
            input = temp.readLine();
            if (input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("QUIT")) throw new Exception(ANSI_BLUE + "\n    Never liked goodbyes!\n\n       See ya soon!   ;)\n" + ANSI_RESET);
            printEffectColor("Okay good so you want a schedule for semester " + input + "...", 10, ANSI_WHITE);
            System.out.println();
            printEffectColor("Let me See........", 10, ANSI_WHITE);
            semester = input;
            
            // check the and find the semesters
            pickedCourses = new ArrayList<String>();

            Set<String>  courseCodes = courseList.keySet();
            for(String couseCode : courseCodes)
            {
                if(courseList.get(couseCode).getSemester().equalsIgnoreCase(semester))
                {   
                    pickedCourses.add(couseCode);             
                }
            }

            if(pickedCourses.isEmpty()) 
            {
                System.out.println();
                printEffectColor("Ummm... " + semester + "?!", 10, ANSI_RED);
                System.out.println();
                System.out.println();
                printEffectColor("I cant find any courses that are offered in that semester! :(", 10, ANSI_RED);
                System.out.println();
                printEffectColor("it's ok tho, give it another try...! ;)", 10, ANSI_GREEN);
            }

            else isOk2Proceed = true;
        }

        System.out.println();
        printEffectColor("Okay so the courses in semester " + semester + " include", 10, ANSI_WHITE);
        
        for(int i = 0; i < pickedCourses.size(); i++)
        {
            System.out.print(", ");
            printEffectColor(pickedCourses.get(i), 10, ANSI_GREEN);
        }
        //"¯\_(ツ)_/¯"
        System.out.println("\n");
        printEffectColor("Now, please tell me about your self! How do you like your schedule?\n", 10, ANSI_WHITE);
        System.out.println();
        

        isOk2Proceed = false;
        while(!isOk2Proceed)
        {
            try
            {
                TreeMap<String, TreeMap<Integer, ArrayList<String>>> tempSchedule = schedule4Me(getUserPreference(), pickedCourses, true);
                if(tempSchedule != null)
                {
                    String tempScheduleFormat = super.ShowSchedule(tempSchedule);
                    printEffectRainbow("\nTA-DA...!\n\n", 100);
                    printEffectColor(tempScheduleFormat, 1, ANSI_WHITE);
                    System.out.println();
                    printEffectColor("Do you like it? Y or N\n> ", 10, ANSI_CYAN);
                    temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scannet for user input no need for scanner.close! YAY!
                    input = temp.readLine();
                    if (input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("QUIT")) throw new Exception(ANSI_BLUE + "\n    Never liked goodbyes!\n\n       See ya soon!   ;)\n" + ANSI_RESET);
                    else if(input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES"))
                    {
                        System.out.println();
                        printEffectColor("\nGlad I helped! :)\n\nSo I just saved your schedule and now you will be back at StudentRegistrySimulator!", 10, ANSI_GREEN);
                        isOk2Proceed = true;
                    }
                    else if(input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO"))
                    {
                        System.out.println();
                        printEffectColor("\n\nOh no I'm Sorry... Maybe try another option?... :(\n\n", 10, ANSI_RED);
                        isOk2Proceed = false;
                    }
                }
            }
            catch (Exception ex)
            {               
                if(ex.getMessage().contains(";)")) throw new Exception(ex.getMessage());
                else if (ex.getMessage().contains("*___*"))
                {
                    printEffectColor("\n\nOh no it's a too hard for me...!  *___* \n\n\n", 20, ANSI_RED);
                    printEffectColor("After all I'm just a bonus mark...! :b \n", 20, ANSI_YELLOW);
                    printEffectColor("Maybe try a new option or do it manually!\n\n", 20, ANSI_WHITE);
                    //AutoLectureSchedule.clear();
                }
                else printEffectColor("\n" + ex.getMessage(), 10, ANSI_RED);
            }
        }
        // happily done...!
        System.out.println(ANSI_BLUE + "\n\n    Never liked goodbyes!\n\n       See ya soon!   ;)\n\n" + ANSI_RESET);
        return AutoLectureSchedule;
        
    }

    private int getUserPreference() throws Exception
    {
        printEffectColor("1. Imma lazZzZzy human being! zZzZzZz (classes strat at 9. no classes on monday and overall, good for lazy people!)", 10, colours.get(new Random().nextInt(colours.size())));
        System.out.println();
        String face = "¯\\_(ツ)_/¯";
        String OS = System.getProperty("os.name").toLowerCase();
        if((OS.indexOf("win") >= 0)) face = ":p Umm... idk...";
        printEffectColor("2. " + face + " (Kind of a balance, gives you at least an hour off beween the classes))", 10, colours.get(new Random().nextInt(colours.size())));
        System.out.println();
        printEffectColor("3. 0___0  cut the bs! (not very nice... just puts the courses one after the other no conditions)", 10, colours.get(new Random().nextInt(colours.size())));
        System.out.println();
        printEffectColor("4. I'm Feeling Lucky ^___^ (Well...)", 10, colours.get(new Random().nextInt(colours.size())));
        System.out.println();

        // get the desired algorithm...
        int algorithm;
        while(true)
        {
            printEffectColor("\nPlease choose from the above\n> ", 10, ANSI_WHITE);
            temp = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));// temporary scannet for user input no need for scanner.close! YAY!
            input = temp.readLine();
            if (input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("QUIT")) throw new Exception(ANSI_BLUE + "\n    Never liked goodbyes!\n\n       See ya soon!   ;)\n" + ANSI_RESET);
            else if (!input.matches("-?\\d+(\\.\\d+)?")) 
                    printEffectColor("\n Ha...! no seriouslly...!", 10, ANSI_YELLOW);
            else if (Integer.parseInt(input) > 4 || Integer.parseInt(input) < 1) 
                    printEffectColor("\n from 1 to 4... how hard is that...!? 0__0", 10, ANSI_YELLOW);
            else
            {
                algorithm = Integer.parseInt(input);
                break;
            }
            System.out.println();
        }

        printEffectColor("\nYour call...!", 10, ANSI_BLUE);
        System.out.println();
        return algorithm;
    }

    public TreeMap<String, TreeMap<Integer, ArrayList<String>>> schedule4Me(int algor, ArrayList<String> pickedCourses, boolean eraseFirst) throws Exception
    {
        printEffectColor("\n\ntrying...\n\n", 10, ANSI_CYAN);
        List<String> weekDays = Arrays.asList("MON","TUE","WED","THUR","FRI");// available week days for scheduling
        int ErrorCount = 0;// counts the times something fails, if too much then exception
        if(eraseFirst)
        {
            AutoLectureSchedule.clear();
            super.setFullSchedul(AutoLectureSchedule);
           
        }
        else super.setFullSchedul(AutoLectureSchedule);// set the given schedule for the newly created object and work with that(4 single cours automation)

        AutoLectureSchedule = super.passMeLectureSchedule();
        List<String>  availableCourses = new ArrayList<String>(pickedCourses);

        ListIterator<String> courseIterator = availableCourses.listIterator();// using this, not only I can keep track of things but also removing an element wont throw an exception.

        try 
        {

            ScheduleAlgorithm algorithm = ScheduleAlgorithm.values()[algor - 1];
            switch (algorithm) 
            {
                case MostDaysOff:
                /** 
                 * this algorithm packs the classes together but does not put any classes at 8am nad no later than 3pm! good for lazy people like myself!
                 * also, no mondays... who likes mondays anyways...
                 * if possible it will put an hour in between classes.
                 * no more than one 3 hour lecture every day... 
                 * Classes with more labs or lectures are harder! so no more than one hard class every day!
                 * and the last day of the schedule will usually be easier and have more breaks!!!
                 * also, most failure rate amongst other algorythms. 
                */
                    for(int day = 1; day < weekDays.size(); day++)// from first day to last day
                        {
                            boolean hadEnough = false;
                            for(int startTime = 9; startTime <= 15; startTime++)
                            {
                                    String courseCode = "";
                                    try 
                                    {
                                        printEffectColor("*", 0, ANSI_WHITE);//indicates that there is a try
                                        int tempNum = hadEnough ? 100 : -1;
                                        String coursePointer = "";
                                        int duration = 0;
                                        
                                        if(!eraseFirst) courseIterator = availableCourses.listIterator();

                                        while(courseIterator.hasNext())//find the hardest or the easiest course...
                                        {
                                            courseCode = courseIterator.next();
                                            if(!hadEnough)
                                            {
                                                if(findSum(courseList.get(availableCourses.get(availableCourses.indexOf(courseCode))).getFormat()) > tempNum)
                                                {
                                                    tempNum = findSum(courseList.get(availableCourses.get(availableCourses.indexOf(courseCode))).getFormat());
                                                    coursePointer = courseCode;
                                                    int totalDuration = calcTotalDuration(coursePointer);
                                                    duration = totalDuration == 0 ? 3 : 3 - totalDuration;
                                                } 
                                            }  
                                            else 
                                            {
                                                if(findSum(courseList.get(availableCourses.get(availableCourses.indexOf(courseCode))).getFormat()) < tempNum)
                                                {
                                                    tempNum = findSum(courseList.get(availableCourses.get(availableCourses.indexOf(courseCode))).getFormat());
                                                    coursePointer = courseCode;
                                                    duration = calcTotalDuration(coursePointer) == 2 ? 1 : 2;
                                                } 
                                            } 
                                        }
                                        super.setDayAndTime(coursePointer, weekDays.get(day), Integer.parseInt(String.format("%02d00" , startTime)), duration);//try to schedule
                                        printEffectColor("*", 0, ANSI_GREEN);//success!!!
                                        hadEnough = true;
                                        startTime += duration;// a course has been scheduled so the next course should start after hours later but also 1 hour break;
                                        if(calcTotalDuration(coursePointer) == 3) availableCourses.remove(availableCourses.indexOf(coursePointer));//we are done with it so...
                                        courseIterator = availableCourses.listIterator();
                                        if(availableCourses.isEmpty()) return AutoLectureSchedule;
                                    } 
                                    catch (Exception e) 
                                    {
                                        printEffectColor("*", 0, ANSI_RED);
                                        if(++ErrorCount > 9) //if failed to add a course for 9 times in the schedule, maybe there is a major problem so break;
                                            throw new Exception();
                                        else 
                                            printEffectColor("\nError for the course " + courseCode + ": " +  e.getMessage() + "\n", 0, ANSI_RED);
                                    }
                            }
                        }
                        break;

                case Balanced:
                /**
                 * starts from 8 am monday with 1 hour between classes. Classes with more labs or lectures are harder! so no more than one hard class every day!
                 * they say we are more productive in the mornings so it schedules the hard courses in the morning and only for a 2 hour lecture after 12 it schedules
                 * any other availavble courses.
                 */

                    
                    for(int day = 0; day < weekDays.size(); day++)// from first day to last day
                    {
                        boolean hadEnough = false;
                        for(int startTime = 8; startTime <= 17; startTime++)
                        {
                                String courseCode = "";
                                try 
                                {
                                    printEffectColor("*", 0, ANSI_WHITE);//indicates that there is a try
                                    int tempNum = hadEnough ? 100 : -1;
                                    String coursePointer = "";
                                    int duration = 0;
                                    
                                    if(!eraseFirst) courseIterator = availableCourses.listIterator();
                                    
                                    while(courseIterator.hasNext())//find the hardest or the easiest course...
                                    {
                                        courseCode = courseIterator.next();
                                        if(!hadEnough)
                                        {
                                            if(findSum(courseList.get(availableCourses.get(availableCourses.indexOf(courseCode))).getFormat()) > tempNum)
                                            {
                                                tempNum = findSum(courseList.get(availableCourses.get(availableCourses.indexOf(courseCode))).getFormat());
                                                coursePointer = courseCode;
                                                int totalDuration = calcTotalDuration(coursePointer);
                                                duration = totalDuration == 0 ? 3 : 3 - totalDuration;
                                            } 
                                        }  
                                        else 
                                        {
                                            if(findSum(courseList.get(availableCourses.get(availableCourses.indexOf(courseCode))).getFormat()) < tempNum)
                                            {
                                                tempNum = findSum(courseList.get(availableCourses.get(availableCourses.indexOf(courseCode))).getFormat());
                                                coursePointer = courseCode;
                                                duration = calcTotalDuration(coursePointer) == 2 ? 1 : 2;
                                            } 
                                        } 
                                    }
                                    if(duration + startTime > 18)// if its gonna be out of the schedule
                                    {
                                        while(duration + startTime > 18)
                                        {
                                            duration -= 1;
                                        }
                                    }
                                     super.setDayAndTime(coursePointer, weekDays.get(day), Integer.parseInt(String.format("%02d00" , startTime)), duration);//try to schedule
                                    printEffectColor("*", 0, ANSI_GREEN);//success!!!
                                    hadEnough = true;
                                    startTime += duration;// a course has been scheduled so the next course should start after hours later but also 1 hour break;
                                    if(calcTotalDuration(coursePointer) == 3) availableCourses.remove(availableCourses.indexOf(coursePointer));//we are done with it so...
                                    courseIterator = availableCourses.listIterator();
                                    if(availableCourses.isEmpty()) return AutoLectureSchedule;
                                } 
                                catch (Exception e) 
                                {
                                    printEffectColor("*", 0, ANSI_RED);
                                    if(++ErrorCount > 9) //if failed to add a course for 9 times in the schedule, maybe there is a major problem so break;
                                        throw new Exception();
                                    else 
                                        printEffectColor("\nError for the course " + courseCode + ": " +  e.getMessage() + "\n", 0, ANSI_RED);
                                }
                        }
                    }
                    break;

                case straightForward:
                /**
                 * simplest one. starts from 8am monday no break in between and all 3hour lectures
                 */    
                    for(int day = 0; day < weekDays.size(); day++)// from first day to last day
                    {
                        for(int startTime = 8; startTime <= 15; startTime++)
                        {
                            while(courseIterator.hasNext() || !availableCourses.isEmpty())
                            {
                                String courseCode = "";
                                try 
                                {
                                    printEffectColor("*", 0, ANSI_WHITE);//indicates that there is a try
                                    courseCode = courseIterator.hasNext() ? courseIterator.next() : courseIterator.previous();// go back and forth untill all of the courses are scheduled
                                    super.setDayAndTime(courseCode, weekDays.get(day), Integer.parseInt(String.format("%02d00" , startTime)), 3);//try to schedule
                                    printEffectColor("*", 0, ANSI_GREEN);//success!!!
                                    startTime += 2;// a course has been scheduled so the next course should stat three hours later...
                                    courseIterator.remove();//we are done with it so...
                                    if(availableCourses.isEmpty()) return AutoLectureSchedule;
                                    break;// jump to the next block of hour
                                } 
                                catch (Exception e) 
                                {
                                    printEffectColor("*", 0, ANSI_RED);
                                    if(day == weekDays.size() - 1 && startTime >= 15) //if last day and hour then no way to add a new course!
                                        throw new Exception();
                                    else if(eraseFirst)
                                        printEffectColor("\nError for the course " + courseCode + ": " +  e.getMessage() + "\n", 0, ANSI_RED);
                                    break;
                                }
                            }
                        }
                    }
                    break;

                case Random:
                /**
                 * random day, random hour, random class, random duration...
                 */
                    Collections.shuffle(availableCourses);// randomiza the course list
                    courseIterator = availableCourses.listIterator(); //make an iterator
                    String courseCode = "";
                    boolean isOk2Proceed = false;
                    
                    while(courseIterator.hasNext() || !isOk2Proceed)
                    {
                        try 
                        {
                            printEffectColor("*", 0, ANSI_WHITE);//indicates that there is a try
                            courseCode = courseIterator.hasNext() ? courseIterator.next() : courseIterator.previous();// go back and forth untill all of the courses are scheduled
                            String day = weekDays.get(new Random().nextInt(weekDays.size()));// pick a random day
                            int startTime = Integer.parseInt(String.format("%02d00" , new Random().nextInt(14 + 1 - 8) + 8));//pick a random start time no later than 1400 cuz...
                            int duration = new Random().nextInt(3 + 1 - 1) + 1;//pick a random duration
                            super.setDayAndTime(courseCode, day, startTime, duration);//try to schedule
                            printEffectColor("*", 0, ANSI_GREEN);//success!!!

                            //if a code is scheduled for 3 hours remove it and move on to the next course (copied from the scheduler)

                            if (calcTotalDuration(courseCode) == 3)//remove the course if it has 3 hours
                                courseIterator.remove();

                            //if all of the courses have beeen scheduled for 3 hours it is ok to proceed! done!
                            Set<String> daySet = super.passMeLectureSchedule().keySet();
                            Set<Integer> timeSet;
                            int totalDuration = 0;
                            totalDuration = 0;
                            for(String weekDay : daySet)
                            {
                                timeSet = super.passMeLectureSchedule().get(weekDay).keySet();

                                for(Integer time : timeSet) if(super.passMeLectureSchedule().get(weekDay).get(time) != null)
                                {
                                    totalDuration += 1;
                                }
                            }

                            if(totalDuration == pickedCourses.size() * 3)
                                isOk2Proceed = true;

                            if(availableCourses.isEmpty()) isOk2Proceed = true;
                        } 
                        catch (Exception e) 
                        {
                            printEffectColor("*", 0, ANSI_RED);//failed
                            if(++ErrorCount > 600) // try 600 times to make the schedule... usually makes it in the first like 20 tries or so. this is just in case!
                                throw new Exception();
                        }
                    }
                    break;

                default:
                    break;
            }

        } 
        catch (Exception e) 
        {
            throw new Exception("\n*___*"); 
        }
        
        //System.out.println(super.ShowSchedule(AutoLectureSchedule));

        AutoLectureSchedule = super.passMeLectureSchedule();
        return AutoLectureSchedule;
    }

    private void printEffectColor(String text, int delay, String colour)
    {
        //the colors dont work on windows!! 
        String OS = System.getProperty("os.name").toLowerCase();
        if((OS.indexOf("win") >= 0)) colour = "";

        for(int i = 0; i < text.length(); i++)
        {
		    System.out.printf(colour + "%c" + ANSI_RESET, text.charAt(i));
            try
            {
			    Thread.sleep(delay);
            }   
            catch(InterruptedException ex){ }
        }
    }

    private void printEffectRainbow(String text, int delay)
    {
        String OS = System.getProperty("os.name").toLowerCase();


		Random r = new Random();
        for(int i = 0; i < text.length(); i++)
        {
            if(!(OS.indexOf("win") >= 0))
                System.out.printf(colours.get(r.nextInt(colours.size())) + "%c" + ANSI_RESET, text.charAt(i));

            else System.out.printf("%c", text.charAt(i));

            try
            {
			    Thread.sleep(delay);
            }   
            catch(InterruptedException ex){ }
        }
    }

     //finds the sum of all of the numbers in a string (used for ranking courses based on labs and lectures)
     // code from https://www.geeksforgeeks.org/calculate-sum-of-all-numbers-present-in-a-string/

    static int findSum(String str) 
    { 
        String temp = "";  
        int sum = 0;  

        for(int i = 0; i < str.length(); i++)  
        {  
            char ch = str.charAt(i); 
            if (Character.isDigit(ch))  
                temp += ch;  
            else
            {  
                sum += Integer.parseInt(temp);  
                temp = "0";  
            }  
        }  
        return sum + Integer.parseInt(temp);  
    }  

    private int calcTotalDuration(String courseCode)
    {
        Set<String> daySet = super.passMeLectureSchedule().keySet();
        Set<Integer> timeSet;
        int totalDuration = 0;
        for(String weekDay : daySet)
        {
            timeSet = super.passMeLectureSchedule().get(weekDay).keySet();

            for(Integer time : timeSet) if(super.passMeLectureSchedule().get(weekDay).get(time).get(0).equalsIgnoreCase(courseCode))
            {
                totalDuration += 1;
            }
        }
        return totalDuration;
    }
}



// Set<String> daySet = AutoLectureSchedule.keySet();
		// Set<Integer> timeSet;
		
		// for(String day : daySet)
		// {
		// 	timeSet = AutoLectureSchedule.get(day).keySet();
		// 	for(Integer time : timeSet)
		// 		System.out.println(day + " " + time + " : " + AutoLectureSchedule.get(day).get(time));
		// }
