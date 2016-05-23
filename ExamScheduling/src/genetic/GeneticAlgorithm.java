package genetic;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
	// TODO estes valores foram random
	private static final double CROSSOVER_DEFAULT = 0.65;
	private static final double MUTATION_DEFAULT_PROB = 0.0001;
	private static final int MINIMUM_LVL_GOOD_SOLUTION = 152;
	private static final int UNCHANGED_GENERATION_LIMIT = 5;
	private static final double DIFF_LIMIT = 0.001;
	// Pelo menos 30 cromossomas, escolher os dois melhores
	private ArrayList<Chromosome> chromosomes;
	// Ia ser 1/2 % do numero de cromossomas
	private int elitistPicks;
	private int sumOfEvaluations = 0;
	private double mutationProb;
	private Random randomValues = new Random();
	private double crossOverProb;

	public GeneticAlgorithm() {
	}

	public GeneticAlgorithm(ArrayList<Chromosome> chromosomes) {
		this.chromosomes = chromosomes;
		this.elitistPicks = Math.floorDiv(chromosomes.size(), 100);
		this.mutationProb = MUTATION_DEFAULT_PROB;
		this.crossOverProb = CROSSOVER_DEFAULT;
	}

	public GeneticAlgorithm(ArrayList<Chromosome> chromosomes, double mutationProb, int elitistPicks, double crossOverProb) {
		this.chromosomes = chromosomes;
		if (elitistPicks < chromosomes.size())
			this.elitistPicks = elitistPicks;
		else
			this.elitistPicks = Math.floorDiv(chromosomes.size(), 100);
		this.mutationProb = mutationProb;
		this.crossOverProb = crossOverProb;
	}

	// TODO esqueleto do algoritmo
	public void skeleton() {
		// When the differene betweenGeneration is smaller than DIFF_LIMIT
		int generationUnchanged = 0;

		// assume que já recebe uma gereção inicial
		// TODO do while or just while, a primeira solução "random" pode ser boa
		// o sufeciente
		sumOfEvaluations = evaluateChromosomes(chromosomes);
		int numberOfNonElitistChromosomes = chromosomes.size() - elitistPicks;
		while (generationUnchanged < UNCHANGED_GENERATION_LIMIT || sumOfEvaluations < MINIMUM_LVL_GOOD_SOLUTION) {
			int newGenerationSum = 0;
			// Might not be needed

			// TODO might not be the best choice, since reference and all, wanna
			// kepe the random aspect and such might not keep this and do one of
			// my own that gets the X max from an Array
			chromosomes.sort(null);

			ArrayList<Chromosome> newGeneration = new ArrayList<Chromosome>();
			elitistChoice(newGeneration);
			// TODO ser a parte do crossover, not sure need to study more, tbh I
			// kinda forgot almost everything about genetics
			crossOver(chromosomes, newGeneration, numberOfNonElitistChromosomes);
			newGenerationSum = evaluateChromosomes(newGeneration);

			if (Math.abs(newGenerationSum - sumOfEvaluations) < DIFF_LIMIT)
				generationUnchanged++;
			else if (generationUnchanged > 0)
				generationUnchanged = 0;

			mutate(newGeneration);
			// mudança de geração
			chromosomes = newGeneration;
			sumOfEvaluations = newGenerationSum;
		}
	}

	// TODO the mutation should be done on a gene by gene basis right? so ignore
	// the chromossome part?
	private void mutate(ArrayList<Chromosome> newGeneration) {
		for (Chromosome chromosome : newGeneration) {
			chromosome.mutate(randomValues, mutationProb);
		}
	}

	private void crossOver(ArrayList<Chromosome> currentGeneration, ArrayList<Chromosome> newGeneration, int numberToCross) {
		int size = currentGeneration.size();
		int crossOverPoints = (int) Math.floor(crossOverProb * size);
		ArrayList<Chromosome> toCross = selection(currentGeneration, numberToCross);
		boolean isOdd = false;

		if (size % 2 != 0)
			isOdd = true;

		for (int i = 0; i < size;) {
			Chromosome newChromossome = null;
			Chromosome chromossome = toCross.get(i);

			// 0-1 2-3
			// 0-1 2-3 4
			if (i < size - 1) {
				Chromosome chromossome2 = toCross.get(i + 1);
				newChromossome = chromossome.crossOver(chromossome2, crossOverPoints);
			} else if (isOdd) {
				newChromossome = chromossome.crossOver();
			}
			newGeneration.add(newChromossome);
		}

	}

	public ArrayList<Chromosome> selection(ArrayList<Chromosome> currentGeneration, int numberToCross) {
		int size = currentGeneration.size();
		double probs[] = generateXRadomNumbers(numberToCross);
		ArrayList<Chromosome> toCross = new ArrayList<Chromosome>();

		for (int i = 0; i < probs.length;) {
			double value = probs[i] * sumOfEvaluations;
			for (int j = size - 1; j >= 0; j++) {
				Chromosome chromosome = currentGeneration.get(j);
				value -= chromosome.getScore();
				if (value <= 0) {
					toCross.add(chromosome);
					break;
				}
			}
		}
		return toCross;
	}

	private double[] generateXRadomNumbers(int size) {
		double probs[] = new double[size];
		for (int i = 0; i < size; i++)
			probs[i] = randomValues.nextDouble();

		return probs;
	}

	private void elitistChoice(ArrayList<Chromosome> newGeneration) {
		for (int i = 0; i < elitistPicks; i++) {
			newGeneration.add(chromosomes.get(i));
		}
	}

	private int evaluateChromosomes(ArrayList<Chromosome> toEvaluate) {
		int sum = 0;
		for (Chromosome chromossome : toEvaluate) {
			chromossome.evaluate();
			sum += chromossome.getScore();
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

	public Random getRandomValues() {
		return randomValues;
	}

	public void setRandomValues(Random randomValues) {
		this.randomValues = randomValues;
	}

	public static double getCrossoverDefaultProb() {
		return CROSSOVER_DEFAULT;
	}

	public static double getMutationDefaultProb() {
		return MUTATION_DEFAULT_PROB;
	}

}