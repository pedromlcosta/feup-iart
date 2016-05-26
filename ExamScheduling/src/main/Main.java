package main;

import java.util.ArrayList;

import annealing.SimulatedAnnealing;
import genetic.GeneticAlgorithm;
import info.Exam;
import info.Room;

public class Main {

	static private ArrayList<Exam> exams;
	static private ArrayList<ArrayList<Integer>> commonExam;
	static private ArrayList<Room> rooms;

	private GeneticAlgorithm genetic = new GeneticAlgorithm();
	private SimulatedAnnealing annealing = new SimulatedAnnealing();

	public static void main(String args[]) {

		System.out.println("correu");

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

	public static ArrayList<Room> getRooms() {
		return rooms;
	}

	public static void setRooms(ArrayList<Room> rooms) {
		Main.rooms = rooms;
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
