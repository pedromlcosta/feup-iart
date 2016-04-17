package genetic;

import java.util.ArrayList;

import info.Exam;

public class MegaCromosome {
	private ArrayList<Exam> exams;
	private ArrayList<ChromosomeB> chromosomes;
	private ArrayList<ArrayList<Integer>> commonExam;

	public ArrayList<Exam> getExams() {
		return exams;
	}

	public void setExams(ArrayList<Exam> exams) {
		this.exams = exams;
	}

	public ArrayList<ChromosomeB> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(ArrayList<ChromosomeB> chromosomes) {
		this.chromosomes = chromosomes;
	}

	public ArrayList<ArrayList<Integer>> getCommonExam() {
		return commonExam;
	}

	public void setCommonExam(ArrayList<ArrayList<Integer>> commonExam) {
		this.commonExam = commonExam;
	}

}