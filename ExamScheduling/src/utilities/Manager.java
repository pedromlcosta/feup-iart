package utilities;

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
import java.util.LinkedHashSet;
import java.util.Locale;

import javax.swing.JLabel;

//import entities.University;

public class Manager {
	
	private Font subtitleFont;
	private String currentFile;
	private BufferedReader text;
	private BufferedWriter textSave;
	//private University university;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private LinkedHashSet<String> courses;
	private ArrayList<String> exams;
	private static final int COL_WIDTH = 20;

	public Manager(){
		
		this.subtitleFont = new Font("Georgia",Font.BOLD,22);
		this.currentFile = new String();
		//this.university = new University();
		this.courses = new LinkedHashSet<String>();
		this.exams = new ArrayList<String>();
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
	
	/*
	public University getUniversity() {
		
		return university;
	}
	
	public void resetUniversity() {
		
		university = new University();
	}
	*/

	public Calendar dateToCalendar(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public boolean loadFile(File file) {
		
		String ext = getExtension(file);
		if(ext.equals("set")){
			try {
				//resetUniversity();
				text = new BufferedReader(new FileReader(file));
				
				String[] args;
				String tmp = text.readLine();
				
				if(tmp != null && tmp.equals("START EXAMS")){
					tmp = text.readLine();
					while(tmp != null && !tmp.equals("END EXAMS")){
						args = tmp.split(",");
						//university.addExam(args);
						tmp = text.readLine();
					}
					tmp = text.readLine();
				}
				if(tmp != null && tmp.equals("START STUDENTS")){
					tmp = text.readLine();
					while(tmp != null && !tmp.equals("END STUDENTS")){
						args = tmp.split(",");
						//university.addStudent(args);
						tmp = text.readLine();
					}
					tmp = text.readLine();
				}
				if(tmp != null && tmp.equals("START TIMESLOTS")){
					tmp = text.readLine();
					while(tmp != null && !tmp.equals("END TIMESLOTS")){
						args = tmp.split(",");
						//university.addTimeslot(args);
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
			/*try {
				//resetUniversity();
				ois = new ObjectInputStream(new FileInputStream(file));
				//university = (University)ois.readObject();
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
			}*/
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
				//textSave.write(university.getExams());
				textSave.write("END EXAMS\n");
				textSave.write("START STUDENTS\n");
				//textSave.write(university.getStudents());
				textSave.write("END STUDENTS\n");
				textSave.write("START TIMESLOTS\n");
				//textSave.write(university.getTimeslots());
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
				//oos.writeObject(university);
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

	public void loadCourses() {
		
		//courses = university.getCoursesDisplay();
	}
	
	public LinkedHashSet<String> getCourses() {
		
		return courses;
	}
	
	public void addCourse(String course){
		
		courses.add(course);
	}

	public void loadExams() {
		
		//exams = university.getExamsDisplay();
	}

	public ArrayList<String> getExams() {
		
		return exams;
	}
}
