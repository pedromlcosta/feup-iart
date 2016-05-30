package genetic;

import java.util.ArrayList;
import java.util.Random;

import info.Exam;
import info.Season;
import info.University;

public class GeneticAlgorithm {

	// TODO: TEMPORIZAR TUDO PARA CALCULAR PERFORMANCES

	// TODO: POR VALORES AJUSTADOS
	private static final double CROSSOVER_DEFAULT = 0.2;
	private static final double MUTATION_DEFAULT_PROB = 0.2;
	private static final int UNCHANGED_GENERATION_LIMIT = 1000;
	private static final double DIFF_LIMIT = 10000;
	private static final int MIN_GENERATIONS_RUN = 1000;
	private University university;
	private Season season = null;
	private ArrayList<Chromosome> chromosomes;
	ArrayList<Chromosome> lastXGenerations;
	int generationUnchanged = 0;

	// At least 30 chromosomes -> statistically relevant
	private int nrChromosomes = 40;
	private int elitistPicks = 3;
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
		long time = System.currentTimeMillis();
		// (Re)Initializing variables for this run.
		this.season = season;
		this.university.setActiveSchedule(false);
		generationUnchanged = 0;
		lastXGenerations = new ArrayList<Chromosome>();

		generateChromosomes(nrChromosomes);

		sumOfEvaluations = evaluateChromosomes(chromosomes);

		int generationsCreated = 0;
		int numberOfNonElitistChromosomes = chromosomes.size() - elitistPicks;

		while (generationsCreated < MIN_GENERATIONS_RUN || generationUnchanged < UNCHANGED_GENERATION_LIMIT) {

			int newGenerationSum = 0;

			ArrayList<Chromosome> copy = new ArrayList<Chromosome>(chromosomes);
			copy.sort(null);

			ArrayList<Chromosome> newGeneration = new ArrayList<Chromosome>();
			elitistChoice(newGeneration, copy);
			crossOver(chromosomes, newGeneration, numberOfNonElitistChromosomes);
			mutate(newGeneration);

			newGenerationSum = evaluateChromosomes(newGeneration);

			ArrayList<Chromosome> copyNew = new ArrayList<Chromosome>(newGeneration);
			copyNew.sort(null);

			int size = newGeneration.size();

			// Top 3
			long oneSide = copyNew.get(size - 1).getScore() + copyNew.get(size - 2).getScore() + copyNew.get(size - 3).getScore();
			long secondSide = copy.get(size - 1).getScore() + copy.get(size - 2).getScore() + copy.get(size - 3).getScore();
			if (Math.abs(oneSide - secondSide) < DIFF_LIMIT)
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
			lastXGenerations.add(copyNew.get(copyNew.size() - 1));

			chromosomes = newGeneration;
			sumOfEvaluations = newGenerationSum;
			generationsCreated++;

		}

		// CROMOSSOMA ESCOLHIDO
		Chromosome chosenChromosome = null;
		if (lastXGenerations.isEmpty())
			chosenChromosome = genMax(chromosomes);
		else
			chosenChromosome = genMax(lastXGenerations);

		chosenChromosome.allocateTimeSlotsToExams();
		System.out.println("It took: " + (System.currentTimeMillis() - time) + " ms");
		// chosenChromosome.registerTimeSlots(season);
		System.out.println("Finished the run");

		chosenChromosome.printTimeSlots();
	}

	private Chromosome genMax(ArrayList<Chromosome> cL) {
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

		for (int i = 0; i < nrChromosomes; i++) {
			Chromosome chromosome = new Chromosome(exams);
			chromosome.generate(nrTimeSlots);
			chromosome.registerTimeSlots(university, season);
			chromosomes.add(chromosome);
		}

	}

	private void mutate(ArrayList<Chromosome> newGeneration) {
		for (int i = elitistPicks + 1; i < newGeneration.size(); i++) {
			newGeneration.get(i).mutate(randomValues, mutationProb);
		}
	}

	private void crossOver(ArrayList<Chromosome> currentGeneration, ArrayList<Chromosome> newGeneration, int numberToCross) throws Exception {
		ArrayList<Chromosome> toCross = selection(currentGeneration, numberToCross);

		boolean isOdd = false;

		if (numberToCross % 2 != 0)
			isOdd = true;
		// 5 1-2 3-4 5
		int contador = 0;
		for (int i = 0; i < numberToCross; i += 2) {
			if (contador >= numberToCross)
				break;
			Chromosome chromosome = toCross.get(i);
			// 0-1 2-3
			// 0-1 2-3 4
			if (i < numberToCross - 1) {
				Chromosome chromossome2 = toCross.get(i + 1);

				Chromosome[] newChromosome = chromosome.crossOver(chromossome2, crossOverProb);
				// add the newly created Chromosomes
				newGeneration.add(newChromosome[0]);
				newGeneration.add(newChromosome[1]);
				contador += 2;
			} else if (isOdd) {
				Chromosome newChromosome = chromosome.crossOver();
				contador++;
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
		int size = currentGeneration.size();
		double probs[] = generateNRandomNumbers(numberToCross);
		ArrayList<Chromosome> toCross = new ArrayList<Chromosome>();

		for (int i = 0; i < probs.length; i++) {
			double value = probs[i] * sumOfEvaluations;
			for (int j = 0; j < size; j++) {

				Chromosome chromosome = currentGeneration.get(j);
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
		return toCross;
	}

	private double[] generateNRandomNumbers(int size) {
		double probs[] = new double[size];
		for (int i = 0; i < size; i++)
			probs[i] = randomValues.nextDouble();

		return probs;
	}

	private void elitistChoice(ArrayList<Chromosome> newGeneration, ArrayList<Chromosome> oldGeneration) {
		int size = oldGeneration.size();
		for (int i = size - 1; i >= 0; i--) {
			newGeneration.add(oldGeneration.get(i));
			if (newGeneration.size() >= elitistPicks)
				return;
		}
	}

	private int evaluateChromosomes(ArrayList<Chromosome> toEvaluate) {
		int sum = 0;
		for (int i = 0; i < toEvaluate.size(); i++) {
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
