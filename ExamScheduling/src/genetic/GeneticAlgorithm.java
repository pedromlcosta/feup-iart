package genetic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import info.Exam;
import info.Season;
import info.University;

public class GeneticAlgorithm {

	// TODO: TEMPORIZAR TUDO PARA CALCULAR PERFORMANCES

	// TODO: POR VALORES AJUSTADOS
	private static final double CROSSOVER_DEFAULT = 0.65;
	private static final double MUTATION_DEFAULT_PROB = 0.25;
	private static final int MINIMUM_LVL_GOOD_SOLUTION = 152;
	private static final int UNCHANGED_GENERATION_LIMIT = 5;
	private static final double DIFF_LIMIT = 0.001;

	private University university;
	private Season season = null;
	private ArrayList<Chromosome> chromosomes;
	ArrayList<Chromosome> lastXGenerations;
	int generationUnchanged = 0;

	// At least 30 chromosomes -> statistically relevant
	private int nrChromosomes = 50;
	private int elitistPicks = 2;
	private int sumOfEvaluations = 0;
	private double mutationProb = MUTATION_DEFAULT_PROB;
	private static Random randomValues = new Random();
	private double crossOverProb = CROSSOVER_DEFAULT;
	private int xGenerations = 5;

	public GeneticAlgorithm() {
		chromosomes = new ArrayList<Chromosome>();
	}

	public GeneticAlgorithm(University university) {
		this.university = university;
		chromosomes = new ArrayList<Chromosome>();
	}

	public GeneticAlgorithm(University university, ArrayList<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
		this.elitistPicks = Math.floorDiv(chromosomes.size(), 100);
	}

	public GeneticAlgorithm(University university, ArrayList<Chromosome> chromosomes, double mutationProb, int elitistPicks, double crossOverProb, int xGenerations) {
		this.chromosomes = chromosomes;
		if (elitistPicks < chromosomes.size())
			this.elitistPicks = elitistPicks;
		else
			this.elitistPicks = Math.floorDiv(chromosomes.size(), 100);
		this.mutationProb = mutationProb;
		this.crossOverProb = crossOverProb;
		this.xGenerations = xGenerations;
	}

	// Runs the algorithm for a certain season, with the info present in the
	// system
	public void run(Season season) throws Exception {

		// (Re)Initializing variables for this run.
		this.season = season;
		generationUnchanged = 0;
		lastXGenerations = new ArrayList<Chromosome>();

		generateChromosomes(nrChromosomes);

		sumOfEvaluations = evaluateChromosomes(chromosomes);

		int numberOfNonElitistChromosomes = chromosomes.size() - elitistPicks;
		while (generationUnchanged < UNCHANGED_GENERATION_LIMIT && sumOfEvaluations < MINIMUM_LVL_GOOD_SOLUTION) {
			int newGenerationSum = 0;

			chromosomes.sort(null);
			System.out.println(chromosomes);
			ArrayList<Chromosome> newGeneration = new ArrayList<Chromosome>();

			elitistChoice(newGeneration);

			crossOver(chromosomes, newGeneration, numberOfNonElitistChromosomes);

			mutate(newGeneration);

			newGenerationSum = evaluateChromosomes(newGeneration);

			if (Math.abs(newGenerationSum - sumOfEvaluations) < DIFF_LIMIT)
				generationUnchanged++;
			else if (generationUnchanged > 0)
				generationUnchanged = 0;

			// mudança de geração
			if (lastXGenerations.size() > xGenerations) {
				// act like pop, remove the oldest one
				lastXGenerations.remove(lastXGenerations.size() - 1);

			}
			// a pos chromosomes.size()-1 é a melhor da geração logo escolhemos
			// o melhor da geração
			lastXGenerations.add(chromosomes.get(chromosomes.size() - 1));
			chromosomes = newGeneration;
			sumOfEvaluations = newGenerationSum;

		}
		System.out.println(sumOfEvaluations);
		// CROMOSSOMA ESCOLHIDO
		Chromosome chosenChromosome = genGenMax(lastXGenerations);
		// chosenChromosome.registerTimeSlots(season);
		System.out.println("Finished the run");
	}

	private Chromosome genGenMax(ArrayList<Chromosome> cL) {
		long scoreMax = -1;
		Chromosome best = null;
		for (Chromosome c : cL) {
			if (c.getScore() > scoreMax) {
				scoreMax = c.getScore();
				best = c;
			}

		}
		return best;
	}

	private void generateChromosomes(int nrChromosomes) {

		int nrTimeSlots = university.getTimeSlots(season).size();
		ArrayList<Exam> exams = university.getExams(season);
		// System.out.println("Generating chromosomes with: " + exams.size() + "
		// exams and " + nrTimeSlots + " timeslots");

		for (int i = 0; i < nrChromosomes; i++) {
			Chromosome chromosome = new Chromosome(exams);
			chromosome.generate(nrTimeSlots);
			chromosome.registerTimeSlots(university, season);

			chromosomes.add(chromosome);
			// System.out.println("Chromosome " + i + " was generated");
			// System.out.println(chromosome);
		}

	}

	private void mutate(ArrayList<Chromosome> newGeneration) {
		for (Chromosome chromosome : newGeneration) {
			chromosome.mutate(randomValues, mutationProb);
		}
	}

	private void crossOver(ArrayList<Chromosome> currentGeneration, ArrayList<Chromosome> newGeneration, int numberToCross) throws Exception {
		int geneSize = currentGeneration.get(0).getGenes().size();
		int crossOverPoints = (int) Math.floor(crossOverProb * geneSize);

		ArrayList<Chromosome> toCross = selection(currentGeneration, numberToCross);
		// System.out.println("Select Things to Cross");
		// System.out.println("CrossOver points: " + crossOverPoints);

		boolean isOdd = false;

		if (numberToCross % 2 != 0)
			isOdd = true;
		// System.out.println(toCross);
		for (int i = 0; i < numberToCross; i++) {
			Chromosome chromosome = toCross.get(i);
			// 0-1 2-3
			// 0-1 2-3 4
			if (i < numberToCross - 1) {
				Chromosome chromossome2 = toCross.get(i + 1);
				// System.out.println("To cross");
				// System.out.println(chromosome);
				// System.out.println(chromossome2);
				// System.in.read();
				Chromosome[] newChromosome = chromosome.crossOver(chromossome2, crossOverPoints);
				// add the newly created Chromosomes
				newGeneration.add(newChromosome[0]);
				newGeneration.add(newChromosome[1]);
			} else if (isOdd) {
				Chromosome newChromosome = chromosome.crossOver();
				// add the Chromosome
				newGeneration.add(newChromosome);
			}
		}
	}

	/**
	 * Picks the Chromosomes that will "suffer" crossover
	 * 
	 * @param currentGeneration
	 * @param numberToCross
	 * @return
	 */
	public ArrayList<Chromosome> selection(ArrayList<Chromosome> currentGeneration, int numberToCross) {
		// System.out.println("Selection Start: " + numberToCross);
		int size = currentGeneration.size();
		double probs[] = generateNRandomNumbers(numberToCross);
		ArrayList<Chromosome> toCross = new ArrayList<Chromosome>();

		for (int i = 0; i < probs.length; i++) {
			double value = probs[i] * sumOfEvaluations;
			for (int j = 0; j < size; j++) {

				Chromosome chromosome = currentGeneration.get(j);
				// System.out.println(chromosome);
				value -= chromosome.getScore();
				if (value <= 0) {
					toCross.add(chromosome);
					break;
				}
			}
			if (toCross.size() == numberToCross)
				break;

			if (i == probs.length - 1 && toCross.size() < numberToCross) {
				// reset probs and i
				probs = generateNRandomNumbers(numberToCross);
				i = -1;
			}
		}
		// System.out.println("Selection End");
		return toCross;
	}

	private double[] generateNRandomNumbers(int size) {
		double probs[] = new double[size];
		for (int i = 0; i < size; i++)
			probs[i] = randomValues.nextDouble();

		return probs;
	}

	private void elitistChoice(ArrayList<Chromosome> newGeneration) {
		int size = chromosomes.size();
		for (int i = size - 1; i >= 0; i--) {
			newGeneration.add(chromosomes.get(i));
			if (newGeneration.size() >= elitistPicks)
				return;
		}
	}

	private int evaluateChromosomes(ArrayList<Chromosome> toEvaluate) {
		int sum = 0;
		for (int i = 0; i < toEvaluate.size(); i++) {
			// System.out.println(toEvaluate.get(i).getGenes());
			toEvaluate.get(i).evaluate(university);
			sum += toEvaluate.get(i).getScore();
		}

		for (Chromosome chromossome : toEvaluate) {
			chromossome.setProbability(chromossome.getScore() / (1.0 * sum) * 1.0);
		}
		return sum;
	}

	public ArrayList<Chromosome> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(ArrayList<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
	}

	public int getElitistPicks() {
		return elitistPicks;
	}

	public int getSumOfEvaluations() {
		return sumOfEvaluations;
	}

	public void setSumOfEvaluations(int sumOfEvaluations) {
		this.sumOfEvaluations = sumOfEvaluations;
	}

	public void setElitistPicks(int elitistPicks) {
		this.elitistPicks = elitistPicks;
	}

	public double getMutationProb() {
		return mutationProb;
	}

	public void setMutationProb(double mutationProb) {
		this.mutationProb = mutationProb;
	}

	public double getCrossOverProb() {
		return crossOverProb;
	}

	public int getxGenerations() {
		return xGenerations;
	}

	public void setxGenerations(int xGenerations) {
		this.xGenerations = xGenerations;
	}

	public static double getCrossoverDefault() {
		return CROSSOVER_DEFAULT;
	}

	public void setCrossOverProb(double crossOverProb) {
		this.crossOverProb = crossOverProb;
	}

	public static int getMinimumLvlGoodSolution() {
		return MINIMUM_LVL_GOOD_SOLUTION;
	}

	public static int getUnchangedGenerationLimit() {
		return UNCHANGED_GENERATION_LIMIT;
	}

	public static double getDiffLimit() {
		return DIFF_LIMIT;
	}

	public static Random getRandomValues() {
		return randomValues;
	}

	public void setRandomValues(Random randomValues) {
		GeneticAlgorithm.randomValues = randomValues;
	}

	public static double getCrossoverDefaultProb() {
		return CROSSOVER_DEFAULT;
	}

	public static double getMutationDefaultProb() {
		return MUTATION_DEFAULT_PROB;
	}

}
