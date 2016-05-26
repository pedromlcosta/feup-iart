package info;

import java.util.ArrayList;
import java.util.Date;

import info.College.Season;

public class Exam {
	private Date examDate;
	private ArrayList<Student> signedUp = new ArrayList<Student>();
	private Season examSeason;
	private int year;

	public void signUp(Student s) {
		if (!signedUp.contains(s))
			signedUp.add(s);
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

	public Exam clone() {
		return null;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
