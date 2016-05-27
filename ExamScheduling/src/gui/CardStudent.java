package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import net.miginfocom.swing.MigLayout;
import utilities.Manager;

public class CardStudent extends CardPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Manager manager;
	private JTextField studentName;
	private JLabel lblStudentNameTip;
	private JComboBox<String> availableExams;
	private JButton btnAddExam;
	private JButton btnAddStudent;
	private LinkedHashSet<String> exams;
	private JLabel lblExamTip;
	private JList<String> selectedExams;
	private DefaultListModel<String> listModel;
	private String examsStr;

	public CardStudent(Manager manager) {
		
		this.manager = manager;
		this.exams = new LinkedHashSet<String>();
		this.listModel = new DefaultListModel<String>();
		this.examsStr = new String();
		initialize();
	}

	private void initialize() {
		
		setLayout(new MigLayout());
		JLabel lblStudentName = new JLabel("Name:");
		add(lblStudentName,"gapleft 20");
		
		studentName = new JTextField();
		studentName.setColumns(Manager.getColWidth());
		add(studentName,"gapleft 5");
		
		lblStudentNameTip = new JLabel();
		add(lblStudentNameTip,"wrap");
		
		JLabel lblExams = new JLabel("Exams:");
		add(lblExams, "gapleft 20, gaptop 5");
		
		availableExams = new JComboBox<>();
		add(availableExams, "gapleft 5");
		
		btnAddExam = new JButton("Add Exam");
		btnAddExam.addActionListener(this);
		add(btnAddExam);
		
		lblExamTip = new JLabel("");
		add(lblExamTip,"wrap");
		
		JScrollPane scrollPane = new JScrollPane();
		selectedExams = new JList<>(listModel);
		selectedExams.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedExams.setFixedCellWidth(Manager.getColWidth() * Manager.getColWidth());
		selectedExams.setEnabled(false);
		add(scrollPane,"gapleft 20, gaptop 5, wrap,span");
		scrollPane.setViewportView(selectedExams);
		
		btnAddStudent = new JButton("Add Student");
		btnAddStudent.addActionListener(this);
		add(btnAddStudent,"gapleft 20, gaptop 5");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnAddExam){
			String exam = (String)availableExams.getSelectedItem();
			if(exams.add(exam)){
				int index = availableExams.getSelectedIndex();
				listModel = (DefaultListModel<String>) selectedExams.getModel();
				listModel.addElement(exam);
				lblExamTip.setText("");
				if(examsStr.length() == 0)
					examsStr += index;
				else
					examsStr += " " + index;
			}
			else
				lblExamTip.setText("Exam already added");
		}
		
		else if(e.getSource() == btnAddStudent){
			String student = (String)studentName.getText();
			
			boolean empty = false;
			
			if(student.isEmpty()){
				lblStudentNameTip.setText("Cannot be empty");
				empty = true;
			}
			else
				lblStudentNameTip.setText("");
			if(exams.size() == 0){
				lblExamTip.setText("Cannot be empty");
				empty = true;
			}
			else
				lblExamTip.setText("");
			
			if(empty)
				return;
			
			manager.getUniversity().addStudent(student,examsStr);
			clearFields();
			clearTips();
		}
	}

	private void clearFields() {
		
		studentName.setText("");
		listModel = (DefaultListModel<String>) selectedExams.getModel();
		listModel.clear();
	}
	
	private void clearTips() {
		
		lblExamTip.setText("");
		lblStudentNameTip.setText("");
	}
	
	@Override
	public void setup() {
		
		manager.loadExams();
		
		ArrayList<String> exams = manager.getExams();
		availableExams.removeAllItems();
		for(String exam:exams)
			availableExams.addItem(exam);
	}
}
