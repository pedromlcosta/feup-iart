package info;

import java.io.Serializable;
import java.util.Calendar;

public class TimeSlot implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Calendar calendar;
	
	//TODO: POR HORAS ... DEFINIR NO GUI

	public TimeSlot(int year, int month, int day) {
		
		calendar = Calendar.getInstance();
		calendar.set(year, month, day);
	}
	
	public String toString(){
		return calendar.toString();
	}
	
	public int getYear() {
		//yep I know
		return calendar.get(Calendar.YEAR);
	}

	public int getMonth() {
		
		return calendar.get(Calendar.MONTH);
	}

	public int getDay() {
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getWeekDay(){
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
}