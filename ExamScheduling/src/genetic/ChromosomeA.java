package genetic;

import java.util.ArrayList;

import info.Exam;

public class ChromosomeA  {
	ArrayList<Exam> genes= new ArrayList<Exam>();
	ArrayList<Integer> timeslotsForExam= new ArrayList<Integer>();
	public ChromosomeA() {
	}
	public ArrayList<Exam> getGenes() {
		return genes;
	}
	public void setGenes(ArrayList<Exam> genes) {
		this.genes = genes;
	}
	public ArrayList<Integer> getTimeslotsForExam() {
		return timeslotsForExam;
	}
	public void setTimeslotsForExam(ArrayList<Integer> timeslotsForExam) {
		this.timeslotsForExam = timeslotsForExam;
	}
}
