package info;

import java.io.Serializable;
import java.util.ArrayList;

public class Student implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private ArrayList<Integer> exams;

	public Student(String name){
		
		this.name = name;
		this.exams = new ArrayList<Integer>();
	}

	public String getName() {
		
		return name;
	}

	public ArrayList<Integer> getExams() {
		
		return exams;
	}

	public void addExams(ArrayList<Integer> exams) {
		
		this.exams = exams;
	}
}
