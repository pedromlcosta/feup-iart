package main;

import java.io.IOException;
import java.util.ArrayList;

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
		ArrayList<Integer> o2 = new ArrayList<Integer>();

		o1.add(1);
		o1.add(2);
		o1.add(3);
		o1.add(4);

		o2.add(5);
		o2.add(6);
		o2.add(7);
		o2.add(8);

		ArrayList<Integer> c1 = new ArrayList<Integer>();
		ArrayList<Integer> c2 = new ArrayList<Integer>();

		c1.addAll(o1);
		c2.addAll(o2);
		int deltaPoint = Math.floorDiv(o2.size(), 1);
		System.out.println(deltaPoint);
		// 1 crossover Point in an array with 3 elements seria
		// 0 - 1 para um deles (logo copia a pos 0) e iria i(0)+3 = 3
		boolean copy = false;
		int oldPos = 0;
		for (int i = 0; i < o2.size(); i += deltaPoint) {
			System.out.println(copy);
			System.out.println(i);
			System.out.println(i + deltaPoint);

			if (!copy) {
				copy = true;
				int x = GeneticAlgorithm.getRandomValues().nextInt() % 2;
				System.out.println(x);
				if (GeneticAlgorithm.getRandomValues().nextInt() % 2 == 0) {
					Main.replaceFrom(o2, c1, i + 1, i + deltaPoint);
					Main.replaceFrom(o1, c2, i + 1, i + deltaPoint);
				} else {
					Main.replaceFrom(o2, c1, oldPos, i + 1);
					Main.replaceFrom(o1, c2, oldPos, i + 1);
				}
			} else {
				copy = false;
			}
			oldPos = i;
		}
		for (Integer c : c1)
			System.out.print(c + " ");

		System.out.println(" ");

		for (Integer c : c2)
			System.out.print(c + " ");

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
