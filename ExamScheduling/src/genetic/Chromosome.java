package genetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import info.Exam;
import info.Season;
import info.TimeSlot;
import info.University;

public class Chromosome implements Comparable<Chromosome> {

	// For 5 exams, there'll be 5 genes, each is a number, each number is a slot
	// allocated
	// to the corresponding exam
	University university;
	Season season;
	private ArrayList<Exam> examsReference;
	private ArrayList<TimeSlot> allocatedSlots = new ArrayList<TimeSlot>();

	private ArrayList<Integer> genes;
	private long score = 0;
	private double probability;

	public void evaluate(University university) {
		// 1a parcela -> cada exame em comum, vê a distancia entre datas,
		// multiplica pelo nº alunos em comum e pelo fator do ano

		long scoreFirstParcel = 0;
		int sameYearFactor = 2;

		int splitSeasonCount = university.getExams(Season.NORMAL).size();

		// If there are more slots than exams and still, the exams land t
		if (university.getTimeSlots(season).size() > examsReference.size()) {
			Set<Integer> slotSet = new HashSet<Integer>(genes);
			if (slotSet.size() < genes.size()) {
				score = 0;
				return;
			}
		}

		for (int i = 0; i < examsReference.size(); i++) {

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

				int examsInCommon = examsReference.get(i).getCommonStudents().size();
				double commonExamsScore = 0;
				if (examsInCommon == 0) {
					commonExamsScore = 2;
				} else {
					commonExamsScore = 1.0 / examsInCommon;
				}

				/// Exame A -> B C
				// Exame B-> A
				// [0,4,3,4,1]

				scoreFirstParcel += minuteDifference * examsInCommon * nrCommonStudents * sameYearFactor;

			}

		}
		// 2a parcela
		ArrayList<TimeSlot> allocatedSorted = new ArrayList<TimeSlot>(allocatedSlots);

		allocatedSorted.sort(null);

		long scoreSecondParcel = 0;

		for (int i = 0; i < allocatedSorted.size() - 1; i++) {
			long diff = allocatedSorted.get(i).diff(allocatedSorted.get(i + 1));

			scoreSecondParcel += diff;
		}

		score = scoreFirstParcel + scoreSecondParcel;

	}

	public void registerTimeSlots(University university, Season season) {
		this.university = university;
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

	public Chromosome(ArrayList<Exam> examsReference, ArrayList<Integer> genes, long score, double probability, University university, Season season) {

		this.genes = new ArrayList<Integer>();

		this.examsReference = examsReference;
		for (Integer gene : genes)
			this.genes.add(gene.intValue());
		this.score = score;
		this.probability = probability;
		this.registerTimeSlots(university, season);
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
	public Chromosome[] crossOver(Chromosome chromosome, double crossOverProb) throws Exception {
		int size = chromosome.getGenes().size();

		if (size != this.getGenes().size())
			throw new Exception("Genes must have the same size");

		// which exam reference do they get or is it the same? for all?
		// for now we'll assume it's the same
		Chromosome c1 = new Chromosome(examsReference);
		Chromosome c2 = new Chromosome(examsReference);
		ArrayList<Integer> chromosomeGenes = chromosome.getGenes();

		for (Integer i : genes)
			c1.getGenes().add(i);
		for (Integer i : chromosomeGenes)
			c2.getGenes().add(i);

		Random n = new Random();
		for (int index = 0; index < genes.size(); index++) {
			if (n.nextDouble() < crossOverProb) {
				// Troca
				int i = c1.getGenes().get(index);
				c1.getGenes().set(index, c2.getGenes().get(index));
				c2.getGenes().set(index, i);
			}
		}
		c1.registerTimeSlots(university, season);
		c2.registerTimeSlots(university, season);

		return new Chromosome[] { c1, c2 };

	}

	public boolean allocateTimeSlotsToExams() {
		int exameSize;

		if ((exameSize = examsReference.size()) != allocatedSlots.size())
			return false;

		for (int i = 0; i < exameSize; i++) {
			// Allocated time slot given to exam
			examsReference.get(i).setTimeslot(allocatedSlots.get(i));
		}
		ArrayList<Exam> exams = university.getExams(Season.NORMAL);
		for (Exam exam : exams)
			System.out.println(exam.getTimeslot().getCalendar().getTime().toString());
		return true;
	}

	/**
	 * When there is only 1 available (
	 * 
	 * @return
	 */
	public Chromosome crossOver() {
		return new Chromosome(examsReference, genes, score, probability, university, season);
	}

	public void mutate(Random seed, double mutationProb) {
		int size = university.getTimeSlots(season).size();
		boolean mutated = false;
		for (int index = 0; index < genes.size(); index++) {
			if (seed.nextDouble() <= mutationProb) {
				mutated = true;
				int nextValue;
				// More slots than exams available
				if (size > genes.size()) {
					// Only allows a slot that has yet to be picked
					do {
						nextValue = seed.nextInt(size);
					} while (genes.contains(nextValue));
				} else // if not any value will do
					nextValue = seed.nextInt(size);

				genes.set(index, nextValue);
			}
		}
		if (mutated)
			this.registerTimeSlots(university, season);
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
		result = prime * result + ((allocatedSlots == null) ? 0 : allocatedSlots.hashCode());
		result = prime * result + ((examsReference == null) ? 0 : examsReference.hashCode());
		result = prime * result + ((genes == null) ? 0 : genes.hashCode());
		long temp;
		temp = Double.doubleToLongBits(probability);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (score ^ (score >>> 32));
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		return result;
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

	public long getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Chromosome [season=" + season + " score: " + score + ", genes=" + genes + "]";

	}

	public void printTimeSlots() {
		int size = allocatedSlots.size();
		for (int index = 0; index < size; index++) {
			TimeSlot ts = allocatedSlots.get(index);
			Exam e = examsReference.get(index);
			System.out.println("Exam: " + e.getName() + " Has TimeSlot: " + ts);
		}

	}

}
