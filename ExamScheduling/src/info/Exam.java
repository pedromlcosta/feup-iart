package info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import info.Student;
import info.TimeSlot;

public class Exam implements Serializable, Comparable<Exam> {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private HashMap<Integer,Integer> commonStudents;
	private ArrayList<Student> students;
	private int year;
	
	private transient TimeSlot ts;
	
	public Exam(String name, int year){
		
		this.name = name;
		this.year = year;
		this.commonStudents = new HashMap<Integer,Integer>();
		this.students = new ArrayList<Student>();
	}

	public String getName() {
		
		return name;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public HashMap<Integer, Integer> getCommonStudents() {
		
		return commonStudents;
	}
	
	public TimeSlot getTimeslot() {
		
		return ts;
	}

	public void addCommonStudent(Integer e) {
		
		commonStudents.put(e, 1);
	}
	
	public void incCommonStudent(Integer minor) {
		
		commonStudents.put(minor,commonStudents.get(minor) + 1);
	}
	
	@Override
	public boolean equals(Object obj){
		
		if(!(obj instanceof Exam))
			return false;
		
		Exam other = (Exam)obj;
		return name.toLowerCase().equals(other.name.toLowerCase()) && (year == other.year);
	}

	public void addStudent(Student student) {
		
		students.add(student);
	}

	public void setTimeslot(TimeSlot timeSlot) {
		
		this.ts = timeSlot;
	}

	@Override
	public int compareTo(Exam exam) {
		return ts.compareTo(exam.ts);
	}
}
