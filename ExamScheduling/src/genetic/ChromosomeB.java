package genetic;

import java.util.ArrayList;

import main.Main;
import info.Exam;

public class ChromosomeB {
	private ArrayList<Exam> examsReference = Main.getExams();
	private ArrayList<Integer> genes;

	public ChromosomeB(){
		
	}
	
	public ChromosomeB( ArrayList<Integer> genes) {
		//put genes being generated randomly?
		this.genes = genes;
	}

	public ArrayList<Integer> getGenes() {
		return genes;
	}

	public void setGenes(ArrayList<Integer> genes) {
		this.genes = genes;
	}

}