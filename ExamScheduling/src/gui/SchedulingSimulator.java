package gui;

import genetic.GeneticAlgorithm;
import info.Season;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import annealing.SimulatedAnnealing;
import net.miginfocom.swing.MigLayout;
import utilities.Manager;

public class SchedulingSimulator extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Manager manager;
	private JButton btnGeneticAlgorithm;
	private JLabel lblGeneticStatus;
	private JButton btnSimulatedAnnealing;
	private JRadioButton normalSeason;
	private JRadioButton resitSeason;
	
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
		
		btnSimulatedAnnealing = new JButton("Start Simulated Annealing Algorithm");
		btnSimulatedAnnealing.addActionListener(this);
		add(btnSimulatedAnnealing,"gapleft 20, gaptop 10");
	}
	
	private Season getActiveSeason(){

		if(normalSeason.isSelected())
			return Season.NORMAL;
		else
			return Season.RESIT;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource() == btnGeneticAlgorithm){
			GeneticAlgorithm genetic = new GeneticAlgorithm(manager.getUniversity());
			try {
				genetic.run(getActiveSeason());
				manager.getUniversity().setActiveSchedule(true);
				System.out.println("Here");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lblGeneticStatus.setText("Clicked");
		}
		else if(e.getSource() == btnSimulatedAnnealing){
			SimulatedAnnealing sa = new SimulatedAnnealing(manager.getUniversity());
			Season season = getActiveSeason();
			sa.schedule(sa.search(season),season);

		}
	}
}