package info;

import java.util.ArrayList;

public class Student {
	private static long LastStudentsNumber = 0;
	private long studentNumber;
	private int year;
	private ArrayList<Exam> signedUpTo = new ArrayList<Exam>();

	public Student() {
		year = 1;
		studentNumber = LastStudentsNumber;
		incLastStudentNumber();
	}

	public Student(int year, long studentNumber) {
		this.year = year;
		this.studentNumber = studentNumber;
		setLastStudentsNumber(studentNumber);
		incLastStudentNumber();
	}

	public long getStudentNumber() {
		return studentNumber;
	}

	public void setStudentNumber(long studentNumber) {
		this.studentNumber = studentNumber;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public static long getLastStudentsNumber() {
		return LastStudentsNumber;
	}

	public static void setLastStudentsNumber(long lastStudentsNumber) {
		LastStudentsNumber = lastStudentsNumber;
	}

	public static void incLastStudentNumber() {
		LastStudentsNumber++;
	}

	public ArrayList<Exam> getSignedUpTo() {
		return signedUpTo;
	}

	public void setSignedUpTo(ArrayList<Exam> signedUpTo) {
		this.signedUpTo = signedUpTo;
	}
	
}
