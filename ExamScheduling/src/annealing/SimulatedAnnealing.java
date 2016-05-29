package annealing;

import java.util.Random;

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
	private Random random;
	
	public SimulatedAnnealing(){
		
	}
	
	public SimulatedAnnealing(University university){
		
		this.university = university;
		coolingRate = 0.97;
		random = new Random();
	}
	
	public SimulatedAnnealing(double coolingRate, University university){
		
		this.university = university;
		this.coolingRate = coolingRate;
	}

	public Individual search(Season season){
		
		Individual.setChangeProb(0.30);
		Individual.setPremuteProb(0.30);
		
		long oldCost, newCost, bestCost = 0;
		currentSolution = new Individual();
		currentSolution.generate(university.getTimeSlots(season).size(), university.getExams(season).size());
		oldCost = currentSolution.getValue(university,season);
		temperature = 1;
		
		while(temperature > T_EPSILON){
			newSolution = currentSolution.getNeighbour(university,season);
			newCost = newSolution.getValue(university,season);
			
			long deltaCost = newCost - oldCost;
			System.out.println("Delta"+ deltaCost);
			System.out.println("Accept" + acceptNeighbour(deltaCost));
			
			if(deltaCost > 0 || acceptNeighbour(deltaCost)){
				currentSolution = newSolution;
				oldCost = newCost;
			}
			
			temperature *= coolingRate;
			
			if(newCost > bestCost){
				bestSolution = newSolution;
				bestCost = newCost;
			}
		}
		
		return bestSolution;
	}
	
	boolean acceptNeighbour(long deltaCost){
		
		return random.nextDouble() <= Math.exp((deltaCost)/temperature);
	}
}
