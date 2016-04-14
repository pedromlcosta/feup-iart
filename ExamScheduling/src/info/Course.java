package info;

import java.util.ArrayList;

public class Course {
	private String courseName;
	private ArrayList<Student> enrroled = new ArrayList<Student>();
	private int year;

	public Course(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public ArrayList<Student> getEnrroled() {
		return enrroled;
	}

	public void setEnrroled(ArrayList<Student> enrroled) {
		this.enrroled = enrroled;
	}

}
