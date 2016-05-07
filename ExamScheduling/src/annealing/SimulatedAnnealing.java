package annealing;

public class SimulatedAnnealing {
	
	private static double T_EPSILON = 0.00001;
	private double temperature;
	private double coolingRate;
	private Individual currentSolution;
	private Individual newSolution;
	private Individual bestSolution;
	
	public SimulatedAnnealing(){
		coolingRate = 0.97;
	}
	
	public SimulatedAnnealing(double coolingRate){
		this.coolingRate = coolingRate;
	}

	public Individual search(){
		
		int oldCost, newCost, bestCost = 0;
		currentSolution = new Individual();
		oldCost = currentSolution.getValue();
		temperature = 1;
		
		while(temperature > T_EPSILON){
			newSolution = currentSolution.getNeighbour();
			newCost = newSolution.getValue();
			if(Math.random() < acceptNeighnour(oldCost,newCost))
				currentSolution = newSolution;
			
			temperature *= coolingRate;
			
			if(newCost > bestCost){
				bestSolution = newSolution;
				bestCost = newCost;
			}
		}
		
		return bestSolution;
	}
	
	double acceptNeighnour(int oldCost, int newCost){
		
		return Math.exp((newCost-oldCost)/temperature);
	}
}
