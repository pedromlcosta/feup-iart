package gui;

import java.awt.EventQueue;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utilities.Manager;

public class ExamScheduling implements ChangeListener {

	private JFrame frame;
	private Manager manager;
	private JTabbedPane tabbedPane;
	private ShowEntities panel2;

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamScheduling window = new ExamScheduling();
					window.frame.pack();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ExamScheduling() {
		
		manager = new Manager();
		initialize();
	}

	private void initialize() {
		
		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(this);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		AddEntities panel1 = new AddEntities(manager);
		tabbedPane.addTab("Load, Add and Save",  null, panel1, "Load entities from a file, create new and save the changes in a file");
		
		panel2 = new ShowEntities(manager);
		tabbedPane.addTab("Show Entities", null, panel2, "Display the current loaded entities");
		
		SchedulingSimulator panel3 = new SchedulingSimulator(manager);
		tabbedPane.addTab("Simulate Scheduling", null, panel3, "Run algorithms to simluate exams schedules");
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		JPanel tmpPanel = (JPanel) tabbedPane.getSelectedComponent();
		if(tmpPanel == panel2)
			panel2.setup();
		
	}
}
