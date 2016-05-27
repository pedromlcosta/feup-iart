package annealing;

import info.Season;
import info.University;

public class SimulatedAnnealing {
	
	private static double T_EPSILON = 0.00001;
	private double temperature;
	private double coolingRate;
	private Individual currentSolution;
	private Individual newSolution;
	private Individual bestSolution;
	private University university;
	
	public SimulatedAnnealing(){
		
	}
	
	public SimulatedAnnealing(University university){
		
		this.university = university;
		coolingRate = 0.97;
	}
	
	public SimulatedAnnealing(double coolingRate, University university){
		
		this.university = university;
		this.coolingRate = coolingRate;
	}

	public Individual search(Season season){
		
		int oldCost, newCost, bestCost = 0;
		currentSolution = new Individual();
		oldCost = currentSolution.getValue();
		temperature = 1;
		
		while(temperature > T_EPSILON){
			newSolution = currentSolution.getNeighbour(university,season);
			newCost = newSolution.getValue();
			if(Math.random() < acceptNeighbour(oldCost,newCost))
				currentSolution = newSolution;
			
			temperature *= coolingRate;
			
			if(newCost > bestCost){
				bestSolution = newSolution;
				bestCost = newCost;
			}
		}
		
		return bestSolution;
	}
	
	double acceptNeighbour(int oldCost, int newCost){
		
		return Math.exp((newCost-oldCost)/temperature);
	}
}
