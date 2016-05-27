package gui;

import info.Season;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import net.miginfocom.swing.MigLayout;
import utilities.Manager;

public class CardTimeslot extends CardPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Manager manager;
	private JCheckBox weekend;
	private JLabel lblBeginDateTip;
	private JLabel lblEndDateTip;
	private JButton btnAddTimeslots;
	private JSpinner nbTimeslots;
	private JRadioButton normalSeason;
	private JRadioButton resitSeason;
	private JDatePickerImpl beginDate;
	private JDatePickerImpl endDate;
	private JLabel lblnbTimeslotsTip;

	public CardTimeslot(Manager manager) {
		
		this.manager = manager;
		initialize();
	}

	private void initialize() {
		
		setLayout(new MigLayout());
		
		JLabel lblBeginDate = new JLabel("Begin Date:");
		add(lblBeginDate,"gapleft 20");
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		beginDate = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		add(beginDate,"gapleft 5");
		
		lblBeginDateTip = new JLabel();
		add(lblBeginDateTip,"wrap");
		
		JLabel lblEndDate = new JLabel("End Date:");
		add(lblEndDate,"gapleft 20, gaptop 5");
		
		UtilDateModel model2 = new UtilDateModel();
		JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p);
		endDate = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
		add(endDate,"gapleft 5");
		
		lblEndDateTip = new JLabel();
		add(lblEndDateTip,"wrap");
		
		JLabel lblNbTimeslots = new JLabel("Number of timeslots per date:");
		add(lblNbTimeslots,"gapleft 20, gaptop 5");
		
		SpinnerNumberModel nbModel = new SpinnerNumberModel();
		nbModel.setMinimum(1);
		nbTimeslots = new JSpinner(nbModel);
		JFormattedTextField field = ((JSpinner.DefaultEditor) nbTimeslots.getEditor()).getTextField();
		field.setColumns(3);
		add(nbTimeslots, "gapleft 5");
		
		lblnbTimeslotsTip = new JLabel();
		add(lblnbTimeslotsTip,"wrap");
		
		weekend = new JCheckBox("Include Saturday and Sunday");
		add(weekend,"gapleft 20,wrap");
		
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
		
		btnAddTimeslots = new JButton("Add Timeslots");
		btnAddTimeslots.addActionListener(this);
		add(btnAddTimeslots,"gapleft 20");
	}
	
	private Season getActiveSeason(){
		
		if(normalSeason.isSelected())
			return Season.NORMAL;
		else
			return Season.RESIT;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnAddTimeslots){
			
			boolean empty = false;
			
			Date bDate = (Date)beginDate.getModel().getValue();
			if(bDate == null){
				lblBeginDateTip.setText("Cannot be empty");
				empty = true;
			}
			else lblBeginDateTip.setText("");
			
			Date eDate = (Date)endDate.getModel().getValue();
			if(eDate == null){
				lblEndDateTip.setText("Cannot be empty");
				empty = true;
			}
			else lblEndDateTip.setText("");
		
			int nbTs = (Integer)nbTimeslots.getValue();
			if(nbTs < 1){
				lblnbTimeslotsTip.setText("Must be at least 1");
				empty = true;
			}
			else lblnbTimeslotsTip.setText("");
			
			if(empty)
				return;
			
			if(eDate.before(bDate)){
				lblEndDateTip.setText("Date must be equal or after");
				return;
			}
			
			ArrayList<String> ts = manager.getUniversity().getTS(manager.dateToCalendar(bDate),manager.dateToCalendar(eDate),weekend.isSelected());
			for(String tmp:ts)
				for(int i=0; i < nbTs; i++)
					manager.getUniversity().addTimeslot(getActiveSeason(), tmp);
			
			clearFields();
			clearTips();
		}
	}

	private void clearFields() {
		
		beginDate.getModel().setValue(null);
		endDate.getModel().setValue(null);
		nbTimeslots.setValue(0);
	}
	
	private void clearTips() {
		
	
		lblBeginDateTip.setText("");
		lblEndDateTip.setText("");
		lblnbTimeslotsTip.setText("");
	}
	
	@Override
	public void setup() {
		
		//empty setup
	}
}
