package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

//import entities.Season;
import net.miginfocom.swing.MigLayout;
import utilities.Manager;

public class CardExam extends CardPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	public Manager manager;
	private JComboBox<Object> examCourse;
	private JTextField examName;
	private JButton btnAddExam;
	private JSpinner examYear;
	private JLabel lblExamYearTip;
	private JLabel lblExamCourseTip;
	private JLabel lblExamNameTip;

	private JRadioButton normalSeason;

	private JRadioButton resitSeason;

	public CardExam(Manager manager) {
		
		this.manager = manager;
		initialize();
	}

	private void initialize() {
		
		setLayout(new MigLayout());
		JLabel lblExamName = new JLabel("Name:");
		add(lblExamName,"gapleft 20");
		
		examName = new JTextField();
		examName.setColumns(Manager.getColWidth());
		add(examName,"gapleft 5");
		
		lblExamNameTip = new JLabel();
		add(lblExamNameTip,"wrap");
		
		JLabel lblExamCourse = new JLabel("Course:");
		add(lblExamCourse, "gapleft 20, gaptop 5");
		
		examCourse = new JComboBox<>();
		examCourse.setEditable(true);
		add(examCourse, "gapleft 5");
		
		lblExamCourseTip = new JLabel("");
		add(lblExamCourseTip,"wrap");
		
		JLabel lblExamYear = new JLabel("Year:");
		add(lblExamYear, "gapleft 20, gaptop 5");
		
		examYear = new JSpinner(new SpinnerListModel(new String[] {"1","2","3","4","5","6","7","8","9","10"}));
		JFormattedTextField field = ((JSpinner.DefaultEditor) examYear.getEditor()).getTextField();
		field.setColumns(2);
		add(examYear, "gapleft 5");
		
		lblExamYearTip = new JLabel();
		add(lblExamYearTip,"wrap");
		
		JLabel lblSeason = new JLabel("Season:");
		add(lblSeason, "gapleft 20, gaptop 5");
		
		normalSeason = new JRadioButton("Normal");
		normalSeason.setSelected(true);
		normalSeason.addActionListener(this);
		add(normalSeason, "gapleft 5");
		
		resitSeason = new JRadioButton("Resit");
		resitSeason.addActionListener(this);
		add(resitSeason, "gapleft 5, wrap");
		
		ButtonGroup radioCollect = new ButtonGroup();
		radioCollect.add(normalSeason);
		radioCollect.add(resitSeason);
		
		btnAddExam = new JButton("Add Exam");
		btnAddExam.addActionListener(this);
		add(btnAddExam,"gapleft 20, gaptop 5");
	}
	
	/*
	private Season getActiveSeason(){
		
		if(normalSeason.isSelected())
			return Season.NORMAL;
		else
			return Season.RESIT;
	}
	*/

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnAddExam){
			String name = examName.getText();
			String course = (String)examCourse.getSelectedItem();
			String year = (String)examYear.getValue();
			
			boolean empty = false;
			
			if(name.isEmpty()){
				lblExamNameTip.setText("Cannot be empty");
				empty = true;
			}
			else
				lblExamNameTip.setText("");
			if(course.isEmpty()){
				lblExamCourseTip.setText("Cannot be empty");
				empty = true;
			}
			else
				lblExamCourseTip.setText("");
			
			if(empty)
				return;
			
			if(btnAddExam.getText() == "Confirm"){
				clearFields();
				clearTips();
				btnAddExam.setText("Add Exam");
				//manager.getUniversity().addExam(getActiveSeason(),name,course,Integer.parseInt(year));
				manager.addCourse(course);
				resetCourses();
			}
			else{
				//int code = manager.getUniversity().lookupCourseYear(course, year);
				/*if(code == 0){
					clearTips();
					lblExamCourseTip.setText("Course doesn't exist, please confirm to create");
					lblExamYearTip.setText("Confirm to associate this year to the course");
					btnAddExam.setText("Confirm");
				}
				else if(code == 1){
					clearTips();
					lblExamYearTip.setText("Confirm to associate this year to the course");
					btnAddExam.setText("Confirm");
				}
				else if(code == 2) {
					clearFields();
					clearTips();
					manager.getUniversity().addExam(getActiveSeason(),name,course,Integer.parseInt(year));
					manager.addCourse(course);
					resetCourses();
				}*/
					
			}
		}
	}

	private void clearFields() {
		
		examName.setText("");
		examCourse.getEditor().setItem("");
		examYear.setValue("1");
	}
	
	private void clearTips() {
		
		lblExamNameTip.setText("");
		lblExamCourseTip.setText("");
		lblExamYearTip.setText("");
	}
	
	private void resetCourses(){
		
		LinkedHashSet<String> courses = manager.getCourses();
		Iterator<String> itr = courses.iterator();
		examCourse.removeAllItems();
		while(itr.hasNext())
			examCourse.addItem(itr.next());
	}

	@Override
	public void setup() {
		
		manager.loadCourses();
		resetCourses();
	}
}