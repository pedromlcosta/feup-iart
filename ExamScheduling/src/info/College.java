package info;

import java.util.ArrayList;

public class College {
	public enum Season {
		Normal, Appeal
	}

	///private HashMap<String, Course> courses = new HashMap<String, Course>();
	private DateRange normalSeason;
	private DateRange appealSeason;
	private ArrayList<Exam> exams;
	
	 
	public DateRange getNormalSeason() {
		return normalSeason;
	}
	public void setNormalSeason(DateRange normalSeason) {
		this.normalSeason = normalSeason;
	}
	public DateRange getAppealSeason() {
		return appealSeason;
	}
	public void setAppealSeason(DateRange appealSeason) {
		this.appealSeason = appealSeason;
	}
	public ArrayList<Exam> getExams() {
		return exams;
	}
	public void setExams(ArrayList<Exam> exams) {
		this.exams = exams;
	}
	
	
	
}
