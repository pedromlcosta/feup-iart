package genetic;

import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
	private static final double CROSSOVER_DEFAULT_PROB = 0.65;
	private static final double MUTATION_DEFAULT_PROB = 0.0001;
	private static final int MINIMUM_LVL_GOOD_SOLUTION = 152;
	private static final int UNCHANGED_GENERATION_LIMIT = 5;
	private static final double DIFF_LIMIT = 0.001;
	// Pelo menos 30 cromossomas, escolher os dois melhores
	private ArrayList<ChromosomeB> chromosomes;
	// Ia ser 1/2 % do numero de cromossomas
	private int elitistPicks;
	private int sumOfEvaluations = 0;
	private double mutationProb;
	private Random randomValues = new Random();
	private double crossOverProb;

	public GeneticAlgorithm() {
	}

	public GeneticAlgorithm(ArrayList<ChromosomeB> chromosomes) {
		this.chromosomes = chromosomes;
		this.elitistPicks = Math.floorDiv(chromosomes.size(), 100);
		this.mutationProb = MUTATION_DEFAULT_PROB;
		this.crossOverProb = CROSSOVER_DEFAULT_PROB;
	}

	public GeneticAlgorithm(ArrayList<ChromosomeB> chromosomes, double mutationProb, int elitistPicks, double crossOverProb) {
		this.chromosomes = chromosomes;
		if (elitistPicks < chromosomes.size())
			this.elitistPicks = elitistPicks;
		else
			this.elitistPicks = Math.floorDiv(chromosomes.size(), 100);
		this.mutationProb = mutationProb;
		this.crossOverProb = crossOverProb;
	}

	// esqueleto do algoritmo
	public void skeleton() {
		// When the differene betweenGeneration is smaller than DIFF_LIMIT
		int generationUnchanged = 0;

		while (generationUnchanged < UNCHANGED_GENERATION_LIMIT || sumOfEvaluations < MINIMUM_LVL_GOOD_SOLUTION) {
			int newGenerationSum = 0;
			// Might not be needed
			evaluateChromosomes(chromosomes);
			chromosomes.sort(null);

			ArrayList<ChromosomeB> newGeneration = new ArrayList<ChromosomeB>();
			elitistChoice(newGeneration);
			// TODO ser a parte do crossover, not sure need to study more, tbh I
			// kinda forgot almost everything about genetics
			crossOver(chromosomes, newGeneration);
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
	private void mutate(ArrayList<ChromosomeB> newGeneration) {
		for (ChromosomeB chromosome : newGeneration) {

			if (randomValues.nextDouble() <= mutationProb)
				chromosome.mutate();

		}
	}

	private void crossOver(ArrayList<ChromosomeB> currentGeneration, ArrayList<ChromosomeB> newGeneration) {

	}

	// TODO
	@SuppressWarnings("unused")
	private int getRandomChromossomePos() {
		return 0;
	}

	private void elitistChoice(ArrayList<ChromosomeB> newGeneration) {
		for (int i = 0; i < elitistPicks; i++) {
			newGeneration.add(chromosomes.get(i));
		}
	}

	private int evaluateChromosomes(ArrayList<ChromosomeB> toEvaluate) {
		int sum = 0;
		for (ChromosomeB chromossome : toEvaluate) {
			chromossome.evaluate();
			sum += chromossome.getScore();
		}
		for (ChromosomeB chromossome : toEvaluate) {
			chromossome.setProbability(chromossome.getScore() / (1.0 * sum) * 1.0);
		}
		return sum;
	}

	public ArrayList<ChromosomeB> getChromosomes() {
		return chromosomes;
	}

	public void setChromosomes(ArrayList<ChromosomeB> chromosomes) {
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
		return CROSSOVER_DEFAULT_PROB;
	}

	public static double getMutationDefaultProb() {
		return MUTATION_DEFAULT_PROB;
	}

}