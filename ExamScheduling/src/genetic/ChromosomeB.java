package genetic;

import java.util.ArrayList;

import info.Exam;

public class ChromosomeB {
	private ArrayList<Exam> examsReference;
	private ArrayList<Integer> genes;

	public ChromosomeB(MegaCromosome examsReference, ArrayList<Integer> genes) {
		this.examsReference = examsReference.getExams();
		this.genes = genes;
	}

	public ArrayList<Exam> getExamsReference() {
		return examsReference;
	}

	public void setExamsReference(ArrayList<Exam> examsReference) {
		this.examsReference = examsReference;
	}

	public ArrayList<Integer> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Integer> genes) {
		this.genes = genes;
	}

}