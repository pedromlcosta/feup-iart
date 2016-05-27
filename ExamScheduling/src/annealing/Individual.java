package annealing;

import info.Season;
import info.University;

import java.util.ArrayList;

public class Individual {
	
	private int score;
	private ArrayList<Integer> timeSlots;
	private static int permuteTimeslotProb;
	private static int changeTimeslotProb;

	public Individual(){
		
		timeSlots = new ArrayList<Integer>();
	}
	
	public int getValue() {
		
		return score;
	}

	public Individual getNeighbour(University university, Season season) {
		
		Individual tmp = (Individual)this.clone();
		int countExams = tmp.timeSlots.size();
		
		//permute timeslots with x probability
		if(Math.random() < permuteTimeslotProb){
			int permuteOne = (int)(Math.random() * countExams);
			int permuteSecond = (int)(Math.random() * countExams);
			
			tmp.permute(permuteOne, permuteSecond);
		}
		
		//change the timeslot with y probability
		if(Math.random() < changeTimeslotProb){
			int permute = (int)(Math.random() * countExams);
			int totalTimeslots = university.getTimeslots(season).size();
			int splitCount = university.getTimeslots(Season.NORMAL).size() * season.ordinal();
			
			tmp.change(permute,totalTimeslots,splitCount);
		}
		
		return tmp;
	}
	
	private void change(int location, int totalTimeslots, int splitCount) {
		
		timeSlots.set(location, splitCount + (int)(totalTimeslots * Math.random()));
	}

	public void permute(int locationOne, int locationTwo){
		
		int tmpTimeSlot = timeSlots.get(locationOne);
		
		timeSlots.get(locationOne);
		timeSlots.set(locationOne, timeSlots.get(locationTwo));
		timeSlots.set(locationTwo, tmpTimeSlot);
	}
	
	@Override
	public Object clone(){
		
		Individual tmp = new Individual();
		tmp.score = this.score;
		for(Integer i:timeSlots)
			tmp.timeSlots.add(i);
		
		return tmp;
	}
}
