package gui;

import info.Season;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

import net.miginfocom.swing.MigLayout;
import utilities.Manager;

public class CardExam extends CardPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	public Manager manager;
	private JTextField examName;
	private JButton btnAddExam;
	private JSpinner examYear;
	private JLabel lblExamYearTip;
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
	
	private Season getActiveSeason(){
		
		if(normalSeason.isSelected())
			return Season.NORMAL;
		else
			return Season.RESIT;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnAddExam){
			String name = examName.getText();
			String year = (String)examYear.getValue();
			
			boolean empty = false;
			
			if(name.isEmpty()){
				lblExamNameTip.setText("Cannot be empty");
				empty = true;
			}
			else
				lblExamNameTip.setText("");
			
			if(empty)
				return;
			
			if(btnAddExam.getText() == "Confirm"){
				clearFields();
				clearTips();
				btnAddExam.setText("Add Exam");
				manager.getUniversity().addExam(getActiveSeason(),name,Integer.parseInt(year));
			
			}
			else
				manager.getUniversity().addExam(getActiveSeason(),name,Integer.parseInt(year));
		}
	}

	private void clearFields() {
		
		examName.setText("");
		examYear.setValue("1");
	}
	
	private void clearTips() {
		
		lblExamNameTip.setText("");
		lblExamYearTip.setText("");
	}

	@Override
	public void setup() {
		
	}
}