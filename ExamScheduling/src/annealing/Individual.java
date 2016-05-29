package annealing;

import info.Exam;
import info.Season;
import info.TimeSlot;
import info.University;

import java.util.ArrayList;
import java.util.Map.Entry;

public class Individual {

	private long score;
	private ArrayList<Integer> timeSlots;
	private ArrayList<TimeSlot> allocatedSlots;
	private static double permuteTimeslotProb;
	private static double changeTimeslotProb;

	public Individual(){

		timeSlots = new ArrayList<Integer>();
		allocatedSlots = new ArrayList<TimeSlot>();
	}

	public long getScore() {

		return score;
	}
	
	public long getValue(University university, Season season){
		
		evaluate(university,season);
		return score;
	}

	public void generate(int nrSlots, int nrExams) {

		if (nrSlots <= 0)
			return;

		ArrayList<Integer> ts = new ArrayList<Integer>();

		for (int j = 0; j < nrSlots; j++)
			ts.add(j);

		int totalSlots = ts.size();

		for (int i = 0; i < nrExams; i++) {

			if (ts.isEmpty()) {
				int nextInt = (int) (Math.random() * totalSlots);
				timeSlots.add(nextInt);
			} else {
				int nextInt = (int) (Math.random() * ts.size());
				timeSlots.add(ts.get(nextInt));
				ts.remove(nextInt);
			}
		}
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
			int totalTimeslots = university.getTimeSlots(season).size();
			int splitCount = university.getTimeSlots(Season.NORMAL).size() * season.ordinal();

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

	private void registerTimeslots(University university, Season season){
		
		allocatedSlots.clear();
		ArrayList<TimeSlot> seasonTimeslots = university.getTimeSlots(season);
		
		for (int i = 0; i < timeSlots.size(); i++) {

			TimeSlot ts = seasonTimeslots.get(timeSlots.get(i));
			allocatedSlots.add(ts);
		}
	}
	
	public void evaluate(University university, Season season){

		// 1a parcela -> cada exame em comum, vê a distancia entre datas,
		// multiplica pelo nº alunos em comum e pelo fator do ano
		
		registerTimeslots(university,season);

		long scoreFirstParcel = 0;
		int sameYearFactor = 2;

		int splitSeasonCount = university.getExams(Season.NORMAL).size();
		ArrayList<Exam> examsReference = university.getExams(season);
		
		for (int i = 0; i < examsReference.size(); i++) {
			
			Exam exam = examsReference.get(i);
			TimeSlot examDate = allocatedSlots.get(i);

			// commonExam means the exam that has students in common
			for (Entry<Integer, Integer> entry : examsReference.get(i).getCommonStudents().entrySet()) {

				Integer index = (entry.getKey() - season.ordinal() * splitSeasonCount);
				Exam commonExam = examsReference.get(index);
				int nrCommonStudents = entry.getValue();

				TimeSlot commonExamDate = allocatedSlots.get(index);
				long minuteDifference = examDate.diff(commonExamDate);

				if (exam.getYear() == commonExam.getYear())
					sameYearFactor = 2;
				else
					sameYearFactor = 1;

				scoreFirstParcel += minuteDifference * nrCommonStudents * sameYearFactor;
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

	public static void setPremuteProb(double ptp){
		
		permuteTimeslotProb = ptp;
	}
	
	public static void setChangeProb(double ctp){
		
		changeTimeslotProb = ctp;
	}
}
