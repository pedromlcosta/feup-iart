package info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import info.Season;
import info.Student;
import info.TimeSlot;

public class University implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private HashMap<Season,ArrayList<Exam>> exams;
	private ArrayList<Student> students;
	private HashMap<Season,ArrayList<TimeSlot>> timeslots;
	private int examsCount;
	private int timeslotsCount;

	public University(){
		
		exams = new HashMap<Season,ArrayList<Exam>>();
		exams.put(Season.NORMAL,new ArrayList<Exam>());
		exams.put(Season.RESIT,new ArrayList<Exam>());
		examsCount = 0;
		students = new ArrayList<Student>();
		timeslots = new HashMap<Season,ArrayList<TimeSlot>>();
		timeslots.put(Season.NORMAL, new ArrayList<TimeSlot>());
		timeslots.put(Season.RESIT, new ArrayList<TimeSlot>());
		timeslotsCount = 0;
	}
	
	public void addExam(String[] args) {
		
		//id,season,name,course,year
		if(args.length != 4)
			throw new IllegalArgumentException("Couldn't add an exam, number of arguments wrong");
		try{
			if(Integer.parseInt(args[0]) != examsCount)
				throw new IllegalArgumentException("Exam id invalid");
			
			Season season = Season.valueOf(args[1]); //throws IllegalArgumentException if not valid
			int year = Integer.parseInt(args[3]);
			addExam(season,args[2], year);
		}
		catch (NumberFormatException e){
			throw new IllegalArgumentException("Error adding an exam, expected a number");
		}
	}

	public void addExam(Season season, String name, int year) {
		
		Exam exam = new Exam(name, year);
		ArrayList<Exam> seasonExams = exams.get(season);
		if(seasonExams != null){
			int index = seasonExams.indexOf(exam);
			if(index == -1)
				seasonExams.add(exam);
		}
		examsCount++;
	}

	public void addStudent(String[] args) {
		
		//id,name,examList
		if(args.length != 3)
			throw new IllegalArgumentException("Couldn't add a student, number of arguments wrong");
		try{
			if(Integer.parseInt(args[0]) != students.size())
				throw new IllegalArgumentException("Student id invalid");
			
			addStudent(args[1],args[2]);
		}
		catch (NumberFormatException e){
			throw new IllegalArgumentException("Error adding a student, expected a number");
		}
	}

	public void addStudent(String name, String exams) {
		
		Student student = new Student(name);
		students.add(student);
		addStudentExams(student, exams);
	}

	public void addTimeslot(String[] args) {

		//id, season, Date (yyyy-mm-dd)
		if(args.length != 3)
			throw new IllegalArgumentException("Couldn't add a timeslot, number of arguments wrong");
		try{
			if(Integer.parseInt(args[0]) != timeslotsCount)
				throw new IllegalArgumentException("Timeslot id invalid");
			
			Season season = Season.valueOf(args[1]);
			addTimeslot(season,args[2]);
		}
		catch (NumberFormatException e){
			throw new IllegalArgumentException("Error adding a student, expected a number");
		}		
	}

	public void addTimeslot(Season season, String date) {
		
		String[] dateArgs = date.split("-");
		if(dateArgs.length != 3)
			throw new IllegalArgumentException("Couldn't add a timeslot, date format invalid");
		
		TimeSlot ts = new TimeSlot(Integer.parseInt(dateArgs[0]),Integer.parseInt(dateArgs[1]),Integer.parseInt(dateArgs[2]));
		ArrayList<TimeSlot> seasonTimeslots = timeslots.get(season);
		if(seasonTimeslots != null)
			seasonTimeslots.add(ts);
		timeslotsCount++;
	}
	
	private void addStudentExams(Student student, String args) {
		
		try{
			int normalExamsCnt = exams.get(Season.NORMAL).size();
			String[] examsArgs = args.split(" ");
			ArrayList<Integer> studentExams = student.getExams();
			for(String e:examsArgs){
				int exam = Integer.parseInt(e);
				addCommonStudents(studentExams,exam,normalExamsCnt);
				studentExams.add(exam);
				
				Season s = examSeason(exam,normalExamsCnt);
				ArrayList<Exam> seasonExams = exams.get(s);
				Exam tmpExam = seasonExams.get(exam - s.ordinal() * normalExamsCnt);
				tmpExam.addStudent(student);
			}
		}
		catch(IndexOutOfBoundsException e){
			throw new IllegalArgumentException("Error adding a student, exam not found");
		}
	}
	
	private Season examSeason(int examInt, int splitSeason){
		
		if(examInt < splitSeason)
			return Season.NORMAL;
		else return Season.RESIT;
	}
	
	private void addCommonStudents(ArrayList<Integer> studentExamsInt, int examInt, int splitSeason) {
		
		//get exam with bigger id, set there the commonStudents
		Season s = examSeason(examInt,splitSeason);
		ArrayList<Exam> seasonExams = exams.get(s);
		Exam exam = seasonExams.get(examInt - s.ordinal() * splitSeason);
		
		for(Integer eInt:studentExamsInt){
			Season auxS = examSeason(eInt, splitSeason);
			Exam e;
			if(s == auxS){
				e = seasonExams.get(eInt - s.ordinal() * splitSeason);
				if(examInt > eInt)
					setRelationExams(exam, e);
				else
					setRelationExams(e,exam);
			}
			else
				e = exams.get(auxS).get(eInt - auxS.ordinal() * splitSeason); //to throw IndexOutOfBounds if exam doesn't exist
		}
	}

	private void setRelationExams(Exam major, Exam minor) {
		
		Integer count = major.getCommonStudents().get(minor);
		if(count != null)
			major.incCommonStudent(minor);
		else
			major.addCommonStudent(minor);
	}
	
	public String getExams(){
		
		String examsList = new String();
		ArrayList<Exam> seasonExams = exams.get(Season.NORMAL);
		int splitCount = seasonExams.size();
		
		for(int i=0; i < splitCount; i++){
			Exam e = seasonExams.get(i);
			examsList += i + "," + Season.NORMAL.name() + "," + e.getName() + "," + e.getYear() + "\n";
		}
		
		seasonExams = exams.get(Season.RESIT);
		
		for(int i=0; i < seasonExams.size(); i++){
			Exam e = seasonExams.get(i);
			examsList += (i + splitCount) + "," + Season.RESIT.name() + "," + e.getName() + "," + e.getYear() + "\n";
		}
		
		return examsList;
	}
	
	public String getStudents() {
		
		String studentsList = new String();
		for (int i=0; i < students.size(); i++){
			Student s = students.get(i);
			studentsList += i + "," + s.getName() + ",";
			for(Integer e: s.getExams())
				studentsList+= e + " ";
			studentsList = studentsList.substring(0,studentsList.length() - 1);
			studentsList += "\n";
		}
		return studentsList;
	}
	
	public String getTimeslots() {
		
		String timeslotsList = new String();
		ArrayList<TimeSlot> seasonTS = timeslots.get(Season.NORMAL);
		int splitCount = seasonTS.size();
		
		for(int i=0; i < splitCount; i++){
			TimeSlot ts = seasonTS.get(i);
			timeslotsList += i + "," + Season.NORMAL.name() + "," + ts.getYear() + "-" + String.format("%02d",ts.getMonth()) + "-" + String.format("%02d",ts.getDay()) + "\n";
		}
		seasonTS = timeslots.get(Season.RESIT);
		
		for(int i=0; i < seasonTS.size(); i++){
			TimeSlot ts = seasonTS.get(i);
			timeslotsList += (i+splitCount) + "," + Season.RESIT.name() + "," + ts.getYear() + "-" + String.format("%02d",ts.getMonth()) + "-" + String.format("%02d",ts.getDay()) + "\n";
		}
		
		return timeslotsList;
	}
	
	public ArrayList<String> getExamsDisplay() {
		
		ArrayList<String> examsList = new ArrayList<String>();
		
		ArrayList<Exam> seasonExams = exams.get(Season.NORMAL);
		
		if(seasonExams != null){
			for(Exam exam: seasonExams){
				String tmp = exam.getName() + " (" + exam.getYear() + ") - " + Season.NORMAL.name();
				examsList.add(tmp);
			}
		}
		seasonExams = exams.get(Season.RESIT);
		
		if(seasonExams != null){
			for(Exam exam: seasonExams){
				String tmp = exam.getName() + " (" + exam.getYear() + ") - " + Season.NORMAL.name();
				examsList.add(tmp);
			}
		}
		
		return examsList;
	}


	public boolean equals(Calendar bDate, Calendar eDate){
		
		if(bDate.get(Calendar.YEAR) != eDate.get(Calendar.YEAR))
			return false;
		else if (bDate.get(Calendar.MONTH) != eDate.get(Calendar.MONTH))
			return false;
		return bDate.get(Calendar.DAY_OF_MONTH) == eDate.get(Calendar.DAY_OF_MONTH);
	}
	
	private boolean blockDate(Calendar date, boolean weekendFlag){
		
		if(weekendFlag)
			return false;
		
		if (date.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
			return true;
		
		return date.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
	}

	public ArrayList<String> getTS(Calendar bDate, Calendar eDate, boolean weekendFlag) {
		
		ArrayList<String> ts = new ArrayList<String>();
		
		while(!equals(bDate,eDate)){
			if(!blockDate(bDate,weekendFlag))
				ts.add(bDate.get(Calendar.YEAR) + "-" + bDate.get(Calendar.MONTH) + "-" + bDate.get(Calendar.DAY_OF_MONTH));
			bDate.add(Calendar.DATE,1);
		}
		ts.add(eDate.get(Calendar.YEAR) + "-" + eDate.get(Calendar.MONTH) + "-" + eDate.get(Calendar.DAY_OF_MONTH));
		
		return ts;
	}	
}
