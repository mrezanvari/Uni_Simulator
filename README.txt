

Mohammdreza Anvari 
ID: 500983431



                    !......................:::::::: All of the functions and commands work as expected ::::::::......................!



Hello Again!

This assignment is based on the code and principles of the first assignment. Meaning that most of the algorithms and functions are designed to be modular and provide extra options
to the users! ;)

So, the Registry() still has two versions, the one with file I/O and the one that just makes a default registry. At the begining of this program however, you will be asked whether
if you wanna use the database of students.txt and courses.txt, located at the Database folder (please check File I/O comments), or you wanna use the default registry like the first
assignment. This means that if the database folder is located at the right place you can just use it at the begining, but if you wanna use another path you have to type "N" or "No"
to bypass the importing process and then use the commands to import from file located in your desired path. Unlike the first assignmen and according to the posted video,any exeption 
with importing a file will resault in complete termination of the application. The File I/O and Error Codes section are almost the same as the first assignment with minor to no changes...

The courses.txt has the new course list that was posted on D2L. The local older version registry() however, is local and old version!

* The commands of importFromFile, ExportToFile and estToFile still do work!

Happily, according to the instructions, both students and courses have been changed to TreeMap and all of the functions have been modified to compinsate for the changes... 
This means that most of the functions have less lines and are more efficient. Also, the Exception throwing structure has been changed since the TreeMap works better than 
ArrayList for this case. students and courses however, have been changed only in registry, in the rest of the classes they are ArrayList.

ActiveCourse class now implements comparable. Two courses are equal if their lecture time collides in any given time. CompareTo function will return a value based on the status of
two courses compared to each other, 0 if they have exactly the same schedule, 1 if lecture day and start time are the same but the duration is more for first one. -1 is the opposite of 1.
a value of -2 will also be returned if none of the obove applies. not having a CompareTo will resault is a error from the compiler. since equals() handles the job, CompareTo is actually 
kinda useless! 


Scheduler class:
{
    Most of it follows the instructions except for:

    private TreeMap<String, TreeMap<Integer, ArrayList<String>>> lectureSchedule = new TreeMap<String, TreeMap<Integer, ArrayList<String>>>():
        This monster varialbe(!) is the heart of the class. It is a TreeMap with two keys, week day and start time keeping the CourseCode value and the
        duration of the lecture. With this the Scheduler class is capable to handle multiple blocks of time for a course during the week.
        The duration vale is not that usefull tho...!
}


class AutoScheduler:
{
    This is the automatic schedule maker class. It is completely seperate from the rest of the application. It is kind of like an easter egg inside the app!
    It has its own and seperate interface. This class extends Scheduler and has access to all of the functions from scheduler. 

    The auto scheduler, schedules the courses in a given semester. It does it so based on the user prefrence, it has 4 options and algorithms to schedule.

    enum ScheduleAlgorithm
    {
        MostDaysOff: This mode/algorithm is designed for lazy people! no mondays no 8am classes and no more than one hard lesson or 3 hour lecture every day 
            an hour breaks in between and etc... 
            Note: it may not create a schedule with most days off but it is indead good for laziness ;)

        Balanced: This mode/algorithm makes the most balanced schedule. Again no more than one hard lesson or 3 hour lecture every day and breaks too. it
            also, schedules the hard classes at the begining of the day based on scientific studies that our brains are more productive in the mornings,
            the courses with more labs and lectures are considered to be harder. 

        straightForward: The simplest algorithm of all. this mode/algorithm schedules the courses with no condition hence the name straightForward!

        Random: random day, random hour, random class, random duration...!
    }

    The interface was tested in both Windows 10 and macOS X 10.15.3 (Catalina). The interface uses ANSI codes to create colored texts and effects. Unfortunately,
    these codes are not sopported in windows cmd so the text will be white but hte animations will work. 
    If you have access to a mac or linux system, please give the colors a try! ;)

    To run the interface:

        ---> the command to run the AutoScheduler interface is "SCH AUTO" <---

        --->  the command to auto schedule a given course is "SCH CourseCode AUTO"  <---

        --->  the command to auto schedule a given course whith a given algorithm is "SCH courseCode AUTO intAlgorithm"  <---

    The algorithms are guarantee to work with 4 to 5 courses but I have not tested them with more than 5.

    SCH CourseCode AUTO command, uses the default algorithm and duration, straightForward for 3 hours. 

    The SCH CourseCode AUTO is more recommended because other algorithms work best when they are set to schedule all of the courses...

    "q" command will close the AutoScheduler at any time. 
}

Added functions:
{
    registry.clear(): this function, as the name implies, clears the whole registry including students and courses. This is used for importing the database from file
        the command of "cls" or "clear" is also added just to clear the registry if needed.

    schedule.ShowSchedule(lectureSchedule): this function creates and returns the schedule in String from. This function is used to print the schedule and saving to file.
        this function also, is used by the AutiScheduler

  used for AutiScheduler
  {
     runAutoScheduler(): This function creates an object of AutoSchedule type and runs the interface.

     passMeSchedule(): This function passes the current schedule varialbe in the Scheduler.

     passMeLectureSchedule(): This function passes the current lectureSchedule varialbe in the Scheduler.

     setFullSchedul(lectureSchedule): This function replaces the lectureSchedule whith a given schedule

     AutoScheduleSingleCourse(courseCode, int algorithm): This function passes a course code and the algorithm to the AutoScheduler to schedule a single course.
  }

  registry.passCourses(): pases the courses list object
  registry.clear(): clears the whole registry.
  ActiveCourse.clearClassList(): clears the class list, called when all of the students are marked
}

Error Codes: 
{
    Almost Every function in the registry throws exceptions...

    404: Student not found
    405: Course not found
    -1 : no errors (init)
    Done: Done!
}

File I/O comments:
{
    Registry is capable of using student.txt and courses.txt files in order to create classes and students.
    It uses a new constructor with an input of DatabasePath. DatabasePath is a path that has the two files of students.txt and courses.txt

    This makes the File I/O completely separated from the program and modular...

    Data format for student.txt:
    {
        StudentFullnameNoSpace;StudentIdOnlyNumeric;StudentInitialCours1;StudentInitialCours2;StudentInitialCours3;StudentInitialCours100
        
        1. student name without space + ";"
        2. student id (must be only numeric) + ";"
        3. list of student courses (course codes) + ";" you can add as many courses as you want but in between there must be a semicolon.
        4. dont put a semicolon at the end!
        
    }

    Data format for courses.txt:
    {
        CourseName;CourseCode;CourseDescription;CourseFormat;CourseSemester

        1. course name + ";"
        2. course code + ";"
        3. course description + ";"
        4. course format + ";"
        5. course semester
        6. dont put a semicolon at the end!

    }

    boolean isLocal is added to the class registry. This boolean shows that the functions of the registry are beeing used inside the class itself.
    this means that the class is using the students.txt and courses.txt Databases and therefore if there is an error while adding a student or course or something, 
    the error must be printed to the user but the code should continue not terinated by exceptions. if there is a problem with the database the corresponding ErrorCode 
    will be printed out so it could be debuged based on the error code chart.

    In the simulator, before Registry(String DatabasePath), I called clear(). This is to clear both students and courses arrayList only to add them again with the File I/O method.
    otherwise the data base is set to append to the courses and students.

    the command takes DatabasePath as input (command at the end...)

    Database Export:
    {
        Registry is also capable of exporting a database of students and courses to the file "students.txt" and "courses.txt"

        **please note that the database will be REPLACED!

        the output of the database could be used as input again as it follows the same import format.

        it takes DatabasePath as input
    }


    $$$ ExportStudentTranscript:
    {
        yet another cool feature...!
        it exports the student transcript as a txt file with their name and id number in the folder "Student Transcripts"!

        works the same as database export just with some changes...

        it takes FilePath and StudentId as input
    }

    $$$$ Scheduler.toFile:
    {
        it exports the schedule in any given path!

         works the same as ExportStudentTranscript just with some changes...

        it takes FilePath as input.

        as always, using "home" or "loopback" as the path will save the file at the default path which is the application path.

    }

    A sample database and student transcript is submited alongside the code.
    

    The default path is the application directory/ folder. If you use "home" or "loopback" as the FilePath, it will automaticly be the default path


    ---> the command to import the database is "importFromFile path" <---

    --->  the command to export the database is "ExportToFile path"  <---

    --->  the command to export the student transcript is "estToFile path studentId"  <---

    --->  the command to export the schedule is "sch tofile path"  <---


}

*Command "help" can be used for the list of possible and recognizable commands.



** PLEASE NOTE: All of these functions and commands have been tested on macOS X 10.15.3 (Catalina) and they DO work as expected. However, the path functions use the 
system functions so TECHNICALLY, it should work across all platforms.



Thank you for reading all this s**t! :)