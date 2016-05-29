package gui;

import info.Exam;
import info.Season;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import net.miginfocom.swing.MigLayout;
import utilities.Manager;

public class ShowEntities extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Manager manager;
	private JTable scheduleCalendar;
	private JLabel lblPeriod;
	private JButton btnPreviousWeek;
	private JButton btnNextWeek;
	private Calendar beginMonday;
	private Calendar endMonday;
	private ArrayList<Calendar> period;
	private ScheduleTable model;
	private Calendar beginPeriod;
	private JRadioButton normalSeason;
	private JRadioButton resitSeason;
	
	public ShowEntities(Manager manager) {
		
		this.manager = manager;
		initialize();
	}
	
	public void initialize(){

		setLayout(new MigLayout());
		period = new ArrayList<Calendar>();
		
		JLabel lblFiles = new JLabel("Show Exam Calendar");
		manager.setSubtile(lblFiles);
		add(lblFiles,"gapleft 30,wrap,span");
		
		JLabel lblSeason = new JLabel("Season:");
		add(lblSeason, "gapleft 400, gaptop 15");
		
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
		
		lblPeriod = new JLabel("Week Period: Begin Date - End Date");
		add(lblPeriod,"gapleft 100,gaptop 15");
		
		btnPreviousWeek = new JButton("<");
		btnPreviousWeek.addActionListener(this);
		btnPreviousWeek.setToolTipText("Previous Week");
		add(btnPreviousWeek,"gapleft 40");
		
		btnNextWeek = new JButton(">");
		btnNextWeek.addActionListener(this);
		btnNextWeek.setToolTipText("Next Week");
		add(btnNextWeek,"gapleft 5,wrap");
		
		model = new ScheduleTable(manager);
		scheduleCalendar = new JTable(model);
		scheduleCalendar.setPreferredScrollableViewportSize(new Dimension(getMaximumSize().width,scheduleCalendar.getPreferredScrollableViewportSize().height));
		JTableHeader jth = scheduleCalendar.getTableHeader();
		jth.setPreferredSize(new Dimension(jth.getPreferredSize().width,jth.getPreferredSize().height *2));
		scheduleCalendar.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(scheduleCalendar);
		add(scrollPane,"gapleft 20,gaptop 5,span");
	}
	
	private Season getActiveSeason(){
		
		if(normalSeason.isSelected())
			return Season.NORMAL;
		else
			return Season.RESIT;
	}

	public void setup() {
		
		ArrayList<Exam> exams = manager.getUniversity().getResult(getActiveSeason());
		if(exams.size() == 0)
			return;
		beginMonday = manager.getMonday(exams.get(0));
		endMonday = manager.getMonday(exams.get(exams.size() - 1));
		
		beginPeriod = (Calendar) beginMonday.clone();
		fillTable(exams);
	}

	private void fillTable(ArrayList<Exam> exams) {
		
		Calendar endPeriod = (Calendar) beginPeriod.clone();
		endPeriod.add(Calendar.DATE, 6);
		period = manager.getUniversity().getTS(beginPeriod, endPeriod, true);
		model.setHeader(period);
		
		lblPeriod.setText(manager.formatComplete(beginPeriod) + "  -  " + manager.formatComplete(endPeriod));
		model.setData(period,exams);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		ArrayList<Exam> exams = manager.getUniversity().getResult(getActiveSeason());
		
		if(e.getSource() == btnPreviousWeek){
			if(manager.getUniversity().equals(beginPeriod, beginMonday))
				return;
			
			beginPeriod.add(Calendar.DATE, -7);
			fillTable(exams);
		}
		else if(e.getSource() == btnNextWeek){
			if(manager.getUniversity().equals(beginPeriod, endMonday))
				return;
			
			beginPeriod.add(Calendar.DATE, 7);
			fillTable(exams);
		}
	}
}
