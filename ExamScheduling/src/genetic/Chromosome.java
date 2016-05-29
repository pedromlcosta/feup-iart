package genetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Random;

import info.Exam;
import info.Season;
import info.TimeSlot;
import info.University;
import main.Main;

public class Chromosome implements Comparable<Chromosome> {

	// For 5 exams, there'll be 5 genes, each is a number, each number is a slot
	// allocated
	// to the corresponding exam

	Season season;
	private ArrayList<Exam> examsReference;
	private ArrayList<TimeSlot> allocatedSlots = new ArrayList<TimeSlot>();

	private ArrayList<Integer> genes;
	private int score = 0;
	private double probability;

	public void evaluate(University university) {
		// 1a parcela -> cada exame em comum, vê a distancia entre datas,
		// multiplica pelo nº alunos em comum e pelo fator do ano

		long scoreFirstParcel = 0;
		int sameYearFactor = 2;

		int splitSeasonCount = university.getExams(Season.NORMAL).size();

		System.out.println(genes);

		for (int i = 0; i < examsReference.size(); i++) {
			System.out.println("Exam:" + examsReference.get(i).getName());

			Exam exam = examsReference.get(i);

			TimeSlot examDate = allocatedSlots.get(i);

			// commonExam means the exam that has students in common
			for (Entry<Integer, Integer> entry : examsReference.get(i).getCommonStudents().entrySet()) {

				Integer key = entry.getKey();

				Exam commonExam = examsReference.get(key - season.ordinal() * splitSeasonCount);
				int nrCommonStudents = entry.getValue();

				TimeSlot commonExamDate = allocatedSlots.get(key - season.ordinal() * splitSeasonCount);

				Long minuteDifference = examDate.diff(commonExamDate);

				if (exam.getYear() == commonExam.getYear()) {
					sameYearFactor = 2;
				} else {
					sameYearFactor = 1;
				}

				System.out.println("Date difference score: " + minuteDifference);
				System.out.println("nrCommonStudents: " + nrCommonStudents);
				System.out.println("same Year Factor: " + sameYearFactor);
				scoreFirstParcel += minuteDifference * nrCommonStudents * sameYearFactor;

				// System.out.println("Exam " + examsReference.get(i).getName()
				// + " with keyID: " + i +" has " + nrCommonStudents + "
				// students in common with exam " + examsReference.get(key -
				// season.ordinal() * splitSeasonCount).getName() + " wtih
				// keyID: " + (key - season.ordinal() * splitSeasonCount));
				// System.out.println("TimeSlot for exam with keyID " + i + " is
				// " + examDate.toString() + " and the exam in common with keyID
				// " + (key - season.ordinal() * splitSeasonCount) + " has
				// timeslot " + commonExamDate.toString());
			}

		}

		// 2a parcela
		ArrayList<TimeSlot> allocatedSorted = new ArrayList<TimeSlot>(allocatedSlots);

		System.out.println("These genes correspond to the times: ");
		for (int i = 0; i < allocatedSorted.size(); i++) {
			System.out.println(allocatedSorted.get(i).toString());
		}

		allocatedSorted.sort(null);

		long scoreSecondParcel = 0;

		for (int i = 0; i < allocatedSorted.size() - 1; i++) {
			long diff = allocatedSorted.get(i).diff(allocatedSorted.get(i + 1));
			// System.out.println("Date " + i + " and " + (i+1) +" difference: "
			// + diff);
			scoreSecondParcel += diff;
		}

		long totalScore = scoreFirstParcel + scoreSecondParcel;

		System.out.println("First parcel: " + scoreFirstParcel);
		System.out.println("Second parcel: " + scoreSecondParcel);
		System.out.println("Total: " + totalScore);
	}

	public void registerTimeSlots(University university, Season season) {

		this.season = season;

		allocatedSlots.clear();
		ArrayList<TimeSlot> seasonTimeslots = university.getTimeSlots(season);

		for (int i = 0; i < genes.size(); i++) {
			TimeSlot ts = seasonTimeslots.get(genes.get(i));
			allocatedSlots.add(ts);
		}
	}

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

	public Chromosome(ArrayList<Exam> examsReference, ArrayList<Integer> genes, int score, double probability) {

		this.genes = new ArrayList<Integer>();

		this.examsReference = examsReference;
		for (Integer gene : genes)
			this.genes.add(gene.intValue());
		this.score = score;
		this.probability = probability;
	}

	public void generate(int nrSlots) {

		if (nrSlots <= 0)
			return;

		// Re-initize stuff
		genes = new ArrayList<Integer>();
		ArrayList<Integer> timeSlots = new ArrayList<Integer>();
		Random rn = GeneticAlgorithm.getRandomValues();

		for (int j = 0; j < nrSlots; j++) {
			timeSlots.add(j);
		}

		int totalSlots = timeSlots.size(); // to save how many slots there are
											// actually

		for (int i = 0; i < examsReference.size(); i++) {

			if (timeSlots.isEmpty()) {
				int nextInt = rn.nextInt(totalSlots);
				genes.add(nextInt);
			} else {
				int nextInt = rn.nextInt(timeSlots.size());
				genes.add(timeSlots.get(nextInt));
				timeSlots.remove(nextInt);
			}
		}

	}

	/**
	 * When we have 2 Chromosomes to cross
	 * 
	 * Problem it will return 2 chromossomes, need to address this Should create
	 * Pair class?
	 * 
	 * @return generated Chromosome
	 * @throws Exception
	 */
	// TODO should we use clone after all or it won't matter that the objects
	// are the same?

	public Chromosome[] crossOver(Chromosome chromosome, int crossOverPoints) throws Exception {
		// System.out.println("Starting Cross Overs");
		int size = chromosome.getGenes().size();

		if (size != this.getGenes().size())
			throw new Exception("Genes must have the same size");

		// which exam reference do they get or is it the same? for all?
		// for now we'll assume it's the same
		Chromosome c1 = new Chromosome(examsReference);
		Chromosome c2 = new Chromosome(examsReference);
		ArrayList<Integer> chromossomeGenes = chromosome.getGenes();

		c1.getGenes().addAll(genes);
		c2.getGenes().addAll(chromossomeGenes);

		int deltaPoint = Math.floorDiv(size, crossOverPoints);

		// System.out.println("size " + size);
		// System.out.println("crossOverPoints " + crossOverPoints);
		// System.out.println("DeltaPoint " + (size % crossOverPoints));
		// System.out.println("DeltaPoint " + deltaPoint);

		// add a bit of random to the start
		boolean copy = GeneticAlgorithm.getRandomValues().nextBoolean();
		// 1 crossover Point in an array with 3 elements seria
		// 0 - 1 para um deles (logo copia a pos 0) e iria i(0)+3 = 3
		for (int i = 0; i < size; i += deltaPoint) {
			// System.out.println("I CROSS: " + i);
			if (!copy) {
				copy = true;
				Main.replaceFrom(chromossomeGenes, c1.getGenes(), i + 1, i + deltaPoint);
				Main.replaceFrom(genes, c2.getGenes(), i + 1, i + deltaPoint);
			} else
				copy = false;

		}
		// System.out.println("Ending Cross Overs");
		return new Chromosome[] { c1, c2 };

	}

	/**
	 * When there is only 1 available (
	 * 
	 * @return
	 */
	public Chromosome crossOver() {
		// TODO Auto-generated method stub
		return new Chromosome(examsReference, genes, score, probability);
	}

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
