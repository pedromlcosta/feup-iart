package gui;

import info.Exam;
import info.TimeSlot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import utilities.Manager;

public class ScheduleTable extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Manager manager;
	private Vector<String> colNames;
	private Vector<Vector<String>> slots;
	private static int COL_SIZE = 8;
	private static int ROW_SIZE = 48;
	
	public ScheduleTable(Manager manager) {
		
		this.manager = manager;
		this.colNames = new Vector<String>();
		this.colNames.setSize(COL_SIZE);
		this.colNames.set(0, "Time");
		allocateSlots();
		setTimes();
	}
	
	private void allocateSlots() {
		
		this.slots = new Vector<Vector<String>>();
		for(int i=0; i < COL_SIZE; i++){
			Vector<String> v = new Vector<String>(ROW_SIZE);
			v.setSize(ROW_SIZE);
			this.slots.add(0, v);
		}
	}

	@Override
	public int getColumnCount() {
		
		return COL_SIZE;
	}
	
	@Override
	public String getColumnName(int column){
		
		return colNames.get(column);
	}
	
	@Override
	public int getRowCount() {
		
		return ROW_SIZE;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return slots.get(columnIndex).get(rowIndex);
	}
	
	public void setHeader(ArrayList<Calendar> columns){
		
		if(columns.size() != COL_SIZE - 1)
			return;
		
		for(int index=1; index < COL_SIZE; index++)
			colNames.set(index,manager.format(columns.get(index-1)));
				
		fireTableStructureChanged();
	}
	
	private void setTimes(){
		
		String[] times = manager.getTimes();
		for(int i=0; i < times.length; i++)
			slots.get(0).set(i, times[i]);
	}

	public void setData(ArrayList<Calendar> period, ArrayList<Exam> exams) {
		
		clearData();
		
		int examIndex = 0;
		int periodIndex = 1;
		
		for(Calendar c:period){
			while(examIndex < exams.size()){
				TimeSlot ts = exams.get(examIndex).getTimeslot();
				String exam = manager.getUniversity().format(exams.get(examIndex));
				if(manager.getUniversity().equals(c, ts.getCalendar()))
					setValue(periodIndex,manager.timeIndex(ts.getHour(), ts.getMinute()),exam);
				else if(ts.getCalendar().after(c))
					break;
					
				examIndex++;
			}
			periodIndex++;
		}
		
		fireTableStructureChanged();
	}

	private void clearData() {
		
		for(int i=1;i<COL_SIZE;i++){
			slots.get(i).clear();
			slots.get(i).setSize(ROW_SIZE);
		}
	}

	private void setValue(int periodIndex, int timeIndex, String element) {
		
		String already = slots.get(periodIndex).get(timeIndex);
		if(already != null)
			slots.get(periodIndex).set(timeIndex, already + ", " + element);
		else
			slots.get(periodIndex).set(timeIndex, element);
	}
}
