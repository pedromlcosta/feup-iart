package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import annealing.SimulatedAnnealing;
import genetic.GeneticAlgorithm;
import info.Exam;

public class Main {

	static private ArrayList<Exam> exams;
	static private ArrayList<ArrayList<Integer>> commonExam;

	private GeneticAlgorithm genetic = new GeneticAlgorithm();
	private SimulatedAnnealing annealing = new SimulatedAnnealing();

	public static void main(String args[]) {

		System.out.println("correu");

		// which exam reference do they get or is it the same? for all?
		// for now we'll assume it's the same
		ArrayList<Integer> o1 = new ArrayList<Integer>();

		o1.add(1);
		o1.add(2);
		o1.add(3);
		o1.add(5);

		Set<Integer> slotSet = new HashSet<Integer>(o1);
		if (slotSet.size() < o1.size()) {
			System.out.println("There are duplicates");
		}

	}

	public static ArrayList<Exam> getExams() {
		return exams;
	}

	public static <T> void replaceFrom(ArrayList<Integer> toCopy, ArrayList<Integer> toFill, int startPos, int endPos) {
		if (startPos == endPos && endPos < toCopy.size()) {
			toFill.set(startPos, toCopy.get(startPos));
			return;
		}
		for (int index = startPos; index < endPos; index++) {
			toFill.set(index, toCopy.get(index));
		}

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

	public GeneticAlgorithm getGenetic() {
		return genetic;
	}

	public void setGenetic(GeneticAlgorithm genetic) {
		this.genetic = genetic;
	}

	public SimulatedAnnealing getAnnealing() {
		return annealing;
	}

	public void setAnnealing(SimulatedAnnealing annealing) {
		this.annealing = annealing;
	}

}
