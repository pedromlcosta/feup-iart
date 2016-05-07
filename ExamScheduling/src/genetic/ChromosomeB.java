package genetic;

import java.util.ArrayList;

import main.Main;
import info.Exam;

public class ChromosomeB implements Comparable<ChromosomeB> {
	private ArrayList<Exam> examsReference = Main.getExams();
	private ArrayList<Integer> genes;
	private int score;
	private double probability;

	public ChromosomeB() {

	}

	public ChromosomeB(ArrayList<Integer> genes) {
		// put genes being generated randomly?
		this.genes = genes;
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
	public void mutate() {
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