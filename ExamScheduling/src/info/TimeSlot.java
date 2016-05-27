package info;

import java.io.Serializable;

public class TimeSlot implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int year;
	private int month;
	private int day;
	//TODO: POR HORAS ... DEFINIR NO GUI

	public TimeSlot(int year, int month, int day) {
		
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public int getYear() {
		
		return year;
	}

	public int getMonth() {
		
		return month;
	}

	public int getDay() {
		
		return day;
	}

	private boolean leapYear(){
		
		if(year % 400 == 0)
			return true;
		else if(year % 100 == 0)
			return false;
		return year % 4 == 0;
	}
	
	private int code(){
		if (month == 3 || month == 11)
			return 3;
		else if (month == 4 || month == 7)
			return 6;
		else if (month == 9 || month == 12)
			return 5;
		else if (month == 5)
			return 1;
		else if (month == 6)
			return 4;
		else if (month == 8)
			return 2;
		else if (month == 10)
			return 0;
		else if(month == 1)
			if (leapYear())
				return 6;
			else
				return 0;
		else if(month == 2)
			if (leapYear())
				return 2;
			else
				return 3;
		else return -1;
	}
	
	public int weekDay(){
		
		return (5*(year%100)/4 + code() + day - 2*((year/100)%4) + 7) % 7;
	}
}