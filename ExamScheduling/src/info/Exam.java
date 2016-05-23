package info;

import java.util.ArrayList;
import java.util.Date;

import info.College.Season;

public class Exam {
	private String courseName;
	private Date examDate;
	private ArrayList<Student> signedUp = new ArrayList<Student>();
	private Season examSeason;
	private int year;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public ArrayList<Student> getSignedUp() {
		return signedUp;
	}

	public void setSignedUp(ArrayList<Student> signedUp) {
		this.signedUp = signedUp;
	}

	public Season getExamSeason() {
		return examSeason;
	}

	public void setExamSeason(Season examSeason) {
		this.examSeason = examSeason;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	 

}
