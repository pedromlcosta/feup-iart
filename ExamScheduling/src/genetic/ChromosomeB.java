package genetic;

import java.util.ArrayList;
import java.util.Random;

import info.Exam;

public class ChromosomeB implements Comparable<ChromosomeB> {
	private ArrayList<Exam> examsReference;// = Main.getExams();
	private ArrayList<Integer> genes;
	private int score;
	private double probability;

	public ChromosomeB() {
		examsReference = new ArrayList<Exam>();
		genes = new ArrayList<Integer>();
	}

	public ChromosomeB(ArrayList<Exam> exams) {
		examsReference = exams;
		genes = new ArrayList<Integer>();
	}

	public ChromosomeB(ArrayList<Exam> exams, ArrayList<Integer> givenGenes) {
		examsReference = new ArrayList<Exam>();
		genes = givenGenes;
	}

	public ArrayList<Integer> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Integer> genes) {
		this.genes = genes;
	}

	public ArrayList<Exam> getExamsReference() {
		return examsReference;
	}

	public void setExamsReference(ArrayList<Exam> examsReference) {
		this.examsReference = examsReference;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void evaluate() {
		// TODO Auto-generated method stub

	}

	// TODO
	public void mutate(Random seed, double mutationProb) {
		if (seed.nextDouble() <= mutationProb) {
		}
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(ChromosomeB o) {
		if (o.getScore() > this.getScore())
			return -1;
		else if (o.getScore() < this.getScore())
			return 1;
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((examsReference == null) ? 0 : examsReference.hashCode());
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
		result = prime * result + score;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChromosomeB other = (ChromosomeB) obj;
		if (examsReference == null) {
			if (other.examsReference != null)
				return false;
		} else if (!examsReference.equals(other.examsReference))
			return false;
		if (genes == null) {
			if (other.genes != null)
				return false;
		} else if (!genes.equals(other.genes))
			return false;
		if (score != other.score)
			return false;
		return true;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

}