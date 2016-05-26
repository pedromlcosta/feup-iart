package gui;

import java.awt.EventQueue;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import utilities.Manager;

public class ExamScheduling {

	private JFrame frame;
	private Manager manager;

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
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		AddEntities panel1 = new AddEntities(manager);
		tabbedPane.addTab("Load, Add and Save",  null, panel1, "Load entities from a file, create new and save the changes in a file");
		
		ShowEntities panel2 = new ShowEntities(manager);
		tabbedPane.addTab("Show Entities", null, panel2, "Display the current loaded entities");
		
	}
}
