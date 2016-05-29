package info;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimeSlot implements Serializable {

	private static final long serialVersionUID = 1L;
	
	Calendar calendar;
	
	//TODO: POR HORAS ... DEFINIR NO GUI

	public TimeSlot(Calendar calendar){
		
		this.calendar = calendar;
	}
	
	public TimeSlot(int year, int month, int day, int hour, int minute) {
		
		calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
	
	public String toString(){
		return calendar.toString();
	}
	
	public int getYear() {
		
		return calendar.get(Calendar.YEAR);
	}
	
	public int getMonth() {
		
		return calendar.get(Calendar.MONTH);
	}

	public int getDay() {
		
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public int getHour(){
		
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	public int getMinute(){
		
		return calendar.get(Calendar.MINUTE);
	}
	
	public int getWeekDay(){
		
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	public long diff(TimeSlot ts) {
        
		return TimeUnit.MILLISECONDS.toMinutes(calendar.getTimeInMillis() - ts.calendar.getTimeInMillis());
    }

	public Calendar getCalendar() {
		
		return calendar;
	}
}