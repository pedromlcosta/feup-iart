package genetic;

import java.util.ArrayList;

import info.Exam;

public class GeneticAlgorithm {
	
	private ArrayList<ChromosomeB> chromosomes;
	

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