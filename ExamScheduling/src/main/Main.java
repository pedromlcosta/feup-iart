package main;

import genetic.GeneticAlgorithm;
import info.Exam;

import java.util.ArrayList;

import annealing.SimmulatedAnnealing;

public class Main {

	static private ArrayList<Exam> exams;
	static private ArrayList<ArrayList<Integer>> commonExam;
	
	

	private GeneticAlgorithm genetic = new GeneticAlgorithm();
	private SimmulatedAnnealing annealing = new SimmulatedAnnealing();
	
	public static void main(String args[]) {
		
		
		
	}

	public static ArrayList<Exam> getExams() {
		return exams;
	}

	public static void setExams(ArrayList<Exam> exams) {
		Main.exams = exams;
	}
	
	public static ArrayList<ArrayList<Integer>> getCommonExam() {
		return commonExam;
	}

	public static void setCommonExam(ArrayList<ArrayList<Integer>> commonExam) {
		Main.commonExam = commonExam;
	}
}
