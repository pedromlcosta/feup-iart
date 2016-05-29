package gui;

import genetic.GeneticAlgorithm;
import info.Season;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import annealing.SimulatedAnnealing;
import net.miginfocom.swing.MigLayout;
import utilities.Manager;

public class SchedulingSimulator extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Manager manager;
	private JButton btnGeneticAlgorithm;
	private JLabel lblGeneticStatus;
	private JButton btnSimulatedAnnealing;
	
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
		
		btnSimulatedAnnealing = new JButton("Start Simulated Annealing Algorithm");
		btnSimulatedAnnealing.addActionListener(this);
		add(btnSimulatedAnnealing,"gapleft 20, gaptop 10");
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == btnGeneticAlgorithm){
			GeneticAlgorithm genetic = new GeneticAlgorithm(manager.getUniversity());
			try {
				genetic.run(Season.NORMAL);
				System.out.println("Here");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lblGeneticStatus.setText("Clicked");
		}
		else if(e.getSource() == btnSimulatedAnnealing){
			SimulatedAnnealing sa = new SimulatedAnnealing(manager.getUniversity());
			sa.search(Season.NORMAL);

		}
	}
}