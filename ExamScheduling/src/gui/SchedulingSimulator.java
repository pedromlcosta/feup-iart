package gui;

import genetic.GeneticAlgorithm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import utilities.Manager;

public class SchedulingSimulator extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Manager manager;
	private JButton btnGeneticAlgorithm;
	private JLabel lblGeneticStatus;
	
	public SchedulingSimulator(Manager manager) {
		
		this.manager = manager;
		initialize();
	}
	
	public void initialize(){
		
		setLayout(new MigLayout());
		
		btnGeneticAlgorithm = new JButton("Start Genetic Algorithm");
		btnGeneticAlgorithm.addActionListener(this);
		add(btnGeneticAlgorithm,"gapleft 20,gaptop 10");
		
		lblGeneticStatus = new JLabel("Genetic Response here");
		add(lblGeneticStatus,"wrap");
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == btnGeneticAlgorithm){
			GeneticAlgorithm genetic = new GeneticAlgorithm();
			lblGeneticStatus.setText("Clicked");
		}
	}
}
