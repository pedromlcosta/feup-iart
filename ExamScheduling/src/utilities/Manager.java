package utilities;

import info.Exam;
import info.University;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JLabel;

public class Manager {
	
	private static final int COL_WIDTH = 20;
	
	private Font subtitleFont;
	private String currentFile;
	private BufferedReader text;
	private BufferedWriter textSave;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String[] months;
	
	private University university;
	private ArrayList<String> exams;
	
	public Manager(){
		
		this.subtitleFont = new Font("Georgia",Font.BOLD,22);
		this.currentFile = new String();
		this.university = new University();
		this.exams = new ArrayList<String>();
		this.months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		Locale.setDefault(Locale.ENGLISH);
	}
	
	public void setSubtile(JLabel lbl) {
		
		lbl.setFont(subtitleFont);
		lbl.setForeground(Color.RED.darker());
	}
	
	public static int getColWidth() {
		
		return COL_WIDTH;
	}
	
	public String getCurrentFile(){
		
		return currentFile;
	}
	
	public University getUniversity() {
		
		return university;
	}
	
	public void resetUniversity() {
		
		university = new University();
	}

	public Calendar dateToCalendar(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	
	public boolean loadFile(File file) {
		
		String ext = getExtension(file);
		if(ext.equals("set")){
			try {
				resetUniversity();
				text = new BufferedReader(new FileReader(file));
				
				String[] args;
				String tmp = text.readLine();
				
				if(tmp != null && tmp.equals("START EXAMS")){
					tmp = text.readLine();
					while(tmp != null && !tmp.equals("END EXAMS")){
						args = tmp.split(",");
						university.addExam(args);
						tmp = text.readLine();
					}
					tmp = text.readLine();
				}
				if(tmp != null && tmp.equals("START STUDENTS")){
					tmp = text.readLine();
					while(tmp != null && !tmp.equals("END STUDENTS")){
						args = tmp.split(",");
						university.addStudent(args);
						tmp = text.readLine();
					}
					tmp = text.readLine();
				}
				if(tmp != null && tmp.equals("START TIMESLOTS")){
					tmp = text.readLine();
					while(tmp != null && !tmp.equals("END TIMESLOTS")){
						args = tmp.split(",");
						university.addTimeslot(args);
						tmp = text.readLine();
					}
				}
				
				text.close();
				
				if(tmp == null)
					return false;
				
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find the file: " + file.getAbsolutePath());
				return false;
			} catch (IOException e) {
				System.err.println("Couldn't read the file: " + file.getAbsolutePath());
				closeReadingBuffer("set");
				return false;
			} catch(IllegalArgumentException e){
				System.err.println(e.getMessage());
				closeReadingBuffer("set");
				return false;
			}
		}
		else if(ext.equals("oef")){
			try {
				resetUniversity();
				ois = new ObjectInputStream(new FileInputStream(file));
				university = (University)ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find the file: " + file.getAbsolutePath());
				return false;
			} catch (IOException e) {
				System.err.println("Couldn't write to file: " + file.getAbsolutePath());
				closeReadingBuffer("oef");
				return false;
			} catch (ClassNotFoundException e) {
				System.err.println("Internal error: University class not found");
				closeReadingBuffer("oef");
				return false;
			}
		}
		else
			return false;
		
		currentFile = file.getName();
		return true;
	}
	
	public boolean saveFile(File file) {
		
		String ext = getExtension(file);
		if(ext.equals("set")){
			try {
				textSave = new BufferedWriter(new FileWriter(file));
				
				textSave.write("START EXAMS\n");
				textSave.write(university.getExams());
				textSave.write("END EXAMS\n");
				textSave.write("START STUDENTS\n");
				textSave.write(university.getStudents());
				textSave.write("END STUDENTS\n");
				textSave.write("START TIMESLOTS\n");
				textSave.write(university.getTimeslots());
				textSave.write("END TIMESLOTS");
				textSave.flush();
				
				textSave.close();
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find the file: " + file.getAbsolutePath());
				return false;
			} catch (IOException e) {
				System.err.println("Couldn't write to file: " + file.getAbsolutePath());
				closeWritingBuffer("set");
				return false;
			} 
		}
		else if(ext.equals("oef")){
			
			try {
				oos = new ObjectOutputStream(new FileOutputStream(file));
				oos.writeObject(university);
				oos.close();
			} catch (FileNotFoundException e) {
				System.err.println("Couldn't find the file: " + file.getAbsolutePath());
				return false;
			} catch (IOException e) {
				System.err.println("Couldn't write to file: " + file.getAbsolutePath());
				closeWritingBuffer("oef");
				return false;
			}
		}
		else
			return false;
		
		currentFile = file.getName();
		return true;
	}
	
	private void closeReadingBuffer(String extension) {
		
		try {
			if(extension == "set")
				text.close();
			else if(extension == "oef")
				ois.close();
		} catch (IOException e) {
			System.err.println("Couldn't close the file: " + currentFile);
		}
	}
	
	private void closeWritingBuffer(String extension) {
		
		try {
			if(extension == "set")
				textSave.close();
			else if(extension == "oef")
				oos.close();
		} catch (IOException e) {
			System.err.println("Couldn't close the file: " + currentFile);
		}
	}

	public String getExtension(File file){
		
		String fileName = file.getName();
		int i = fileName.lastIndexOf('.');

		if (i > 0 &&  i < fileName.length() - 1)
			return fileName.substring(i+1).toLowerCase();
		
		return "";
	}
	
	public void loadExams() {
		
		exams = university.getExamsDisplay();
	}

	public ArrayList<String> getExams() {
		
		return exams;
	}

	public String[] getTimes() {
		
		List<String> list = new ArrayList<String>();
		for(int i=0; i < 24; i++){
			list.add(String.format("%02d", i) + ":00");
			list.add(String.format("%02d", i) + ":30");
		}
		
		return list.toArray(new String[0]);
	}
	
	public int timeIndex(int hour, int minutes){
		
		return (hour*60+minutes)/30;
	}

	public long minutesElapsed(String timeStr){
		
		String times[] = timeStr.split(":");
		return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
	}
	
	public long diff(String startTimeStr, String endTimeStr) {
		
		return minutesElapsed(endTimeStr) - minutesElapsed(startTimeStr);
	}
	
	public int[] timesToHours(long[] times){
		
		int[] hours = new int[times.length];
		
		for(int i=0; i < times.length; i++)
			hours[i] = (int)(times[i] / 60);
		
		return hours;
	}
	
	public int[] timesToMinutes(long[] times){
		
		int[] minutes = new int[times.length];
		
		for(int i=0; i < times.length; i++)
			minutes[i] = (int)(times[i] % 60);
		
		return minutes;
	}

	public long[] calculateTimes(String startTimeStr, long time, int nbDivisions){
		
		long[] times = new long[nbDivisions];
		long startTime = minutesElapsed(startTimeStr);
		
		//to not divide by 0
		if(nbDivisions == 1){
			time = ((int)(time/60))*30;
			times[0] = startTime + time;
		}
		else{
			long gap = (time / (nbDivisions - 1));
			for(int index=0; index < nbDivisions; index++)
				times[index] = startTime + ((int)(gap*index/30))*30;
		}
		
		return times;
	}
	
	public boolean valid(String time) {
		
		String[] times = time.split(":");
		
		if(times.length != 2)
			return false;
		
		try{
			int hours = Integer.parseInt(times[0]);
			if (hours < 0 || hours > 23)
				return false;
			
			int minutes = Integer.parseInt(times[1]);
			if (minutes != 0 && minutes != 30)
				return false;
		}
		catch(NumberFormatException e){
			return false;
		}
		
		return true;
	}

	public Calendar getMonday(Exam exam) {
		
		Calendar tmp = (Calendar) exam.getTimeslot().getCalendar().clone();
		while (tmp.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
			tmp.add(Calendar.DATE, -1);
		
		return tmp;
		
	}
	
	public String format(Calendar calendar){
		
		return calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault()) + ", " + months[calendar.get(Calendar.MONTH)] + " " +
				String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
	}

	public String formatComplete(Calendar calendar) {
		
		return calendar.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault()) + ", " +calendar.get(Calendar.YEAR) + " " +
		calendar.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault()) + " " +
				String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
	}

}
