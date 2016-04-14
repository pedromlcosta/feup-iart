package info;

import java.util.HashMap;

public class College {
	public enum Season {
		Normal, Appeal
	}

	private HashMap<String, Course> courses = new HashMap<String, Course>();
	private DateRange normalSeason;
	private DateRange appealSeason;
	private ArrayList<Exam> exams;
	
	
	public HashMap<String, Course> getCourses() {
		return courses;
	}
	public void setCourses(HashMap<String, Course> courses) {
		this.courses = courses;
	}
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
	
	
	
}
