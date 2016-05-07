package genetic;

import java.util.ArrayList;
import java.util.Random;

import info.Exam;

public class ChromosomeA implements Comparable<ChromosomeA> {
	ArrayList<Exam> genes = new ArrayList<Exam>();
	ArrayList<Integer> timeslotsForExam = new ArrayList<Integer>();
	private int score;
	private double probability;

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
	public int compareTo(ChromosomeA o) {
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
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
		result = prime * result + score;
		result = prime * result + ((timeslotsForExam == null) ? 0 : timeslotsForExam.hashCode());
		return result;
	}

	// TODO check equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChromosomeA other = (ChromosomeA) obj;
		if (genes == null) {
			if (other.genes != null)
				return false;
		} else if (!genes.equals(other.genes))
			return false;
		if (score != other.score)
			return false;
		if (timeslotsForExam == null) {
			if (other.timeslotsForExam != null)
				return false;
		} else if (!timeslotsForExam.equals(other.timeslotsForExam))
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
