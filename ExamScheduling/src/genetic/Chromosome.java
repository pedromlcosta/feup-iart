package genetic;

import java.util.ArrayList;
import java.util.Random;

import info.Exam;

public class Chromosome implements Comparable<Chromosome> {
	private ArrayList<Exam> examsReference;// = Main.getExams();
	private ArrayList<Integer> genes;
	private int score;
	private double probability;

	public Chromosome() {
		examsReference = new ArrayList<Exam>();
		genes = new ArrayList<Integer>();
	}

	public Chromosome(ArrayList<Exam> exams) {
		examsReference = exams;
		genes = new ArrayList<Integer>();
	}

	public Chromosome(ArrayList<Exam> exams, ArrayList<Integer> givenGenes) {
		examsReference = new ArrayList<Exam>();
		genes = givenGenes;
	}

	/**
	 * When we have 2 Chromosomes to cross
	 * 
	 * Problem it will return 2 chromossomes, need to address this
	 * Should create Pair class?
	 * @return generated Chromosome
	 */
	public Chromosome crossOver(Chromosome chromossome2, int crossOverPoints) {
		// TODO Auto-generated method stub
		return null;

	}

	/**
	 * When there is only 1 available (
	 * 
	 * @return
	 */
	public Chromosome crossOver() {
		// TODO Auto-generated method stub
		return null;
	}

	public void evaluate() {
		// TODO Auto-generated method stub

	}

	// TODO
	public void mutate(Random seed, double mutationProb) {
		int size = genes.size();
		int limit = size + 1;
		for (int index = 0; index < size; index++) {
			if (seed.nextDouble() <= mutationProb) {
				genes.set(index, seed.nextInt(limit));
			}
		}
	}

	@Override
	public int compareTo(Chromosome o) {
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
		long temp;
		temp = Double.doubleToLongBits(probability);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + score;
		return result;
	}

	// TODO doubts about the existence of an equals method
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromosome other = (Chromosome) obj;
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
		if (Double.doubleToLongBits(probability) != Double.doubleToLongBits(other.probability))
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

}