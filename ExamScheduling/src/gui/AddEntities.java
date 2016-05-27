package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import utilities.Manager;
import net.miginfocom.swing.MigLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPanel;

public class AddEntities extends JPanel implements ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	
	private Manager manager;
	private JTextField textCurrentName;
	private JFileChooser fChooser;
	private JButton btnLoadFile;
	private JLabel lblLoadStatus;
	private JButton btnSave;
	private JLabel lblSaveStatus;
	private JButton btnCreateNewFile;
	private JComboBox<String> entitySelect;
	private JPanel cards;
	private CardExam cardExam;
	private CardStudent cardStudent;
	private CardTimeslot cardTimeslot;
	private HashMap<String,CardPanel> cardsPanels;
	private CardPanel currentCard;
	
	public AddEntities(Manager manager){
		
		this.currentCard = null;
		this.manager = manager;
		this.fChooser = new JFileChooser();
		this.cardsPanels = new HashMap<String,CardPanel>();
		initialize();
	}

	private void initialize() {
		
		setLayout(new MigLayout());
		fChooser.setFileFilter(new EFileFilter(manager));
		
		JLabel lblFiles = new JLabel("Load Entities From File");
		manager.setSubtile(lblFiles);
		add(lblFiles,"gapleft 30,wrap,span");
		
		JLabel lblCurrentFile = new JLabel("Current file: ");
		add(lblCurrentFile,"gapleft 20,gaptop 10");
		
		textCurrentName = new JTextField(manager.getCurrentFile());
		textCurrentName.setEnabled(false);
		textCurrentName.setColumns(Manager.getColWidth());
		add(textCurrentName,"wrap");
		
		btnLoadFile = new JButton("Select file");
		btnLoadFile.setToolTipText("Entities from previous file loaded are lost");
		btnLoadFile.addActionListener(this);
		add(btnLoadFile,"gapleft 20");
		
		lblLoadStatus = new JLabel("Load Status");
		add(lblLoadStatus,"gapleft 5");
		
		btnCreateNewFile = new JButton("Empty entities and create new file");
		btnCreateNewFile.setToolTipText("Provide the name for the file when saving");
		btnCreateNewFile.addActionListener(this);
		add(btnCreateNewFile,"gapleft 10, wrap");
		
		JLabel lblFiles2 = new JLabel("Add New Entities");
		manager.setSubtile(lblFiles2);
		add(lblFiles2,"gapleft 30, gaptop 40,wrap,span");
		
		String[] entities = {"Select:","Exam","Student","Time Slot"};
		entitySelect = new JComboBox<>(entities);
		entitySelect.setEditable(false);
		entitySelect.addItemListener(this);
		add(entitySelect,"gapleft 20,gaptop 10,wrap");
		
		cardExam = new CardExam(manager);
		cardStudent = new CardStudent(manager);
		cardTimeslot = new CardTimeslot(manager);
		setupCardTimeslot();
		
		cards = new JPanel(new CardLayout());
	    cards.add(new JPanel(),entities[0]);
		cards.add(cardExam, entities[1]);
		cardsPanels.put(entities[1], cardExam);
	    cards.add(cardStudent, entities[2]);
	    cardsPanels.put(entities[2], cardStudent);
		cards.add(cardTimeslot, entities[3]);
		cardsPanels.put(entities[3], cardTimeslot);
	    add(cards,"span"); 
		
		JLabel lblFiles3 = new JLabel("Save To A File");
		manager.setSubtile(lblFiles3);
		add(lblFiles3,"gapleft 30, gaptop 40,wrap,span");
		
		btnSave = new JButton("Save");
		btnSave.setToolTipText("Entities are saven on a file based on the file extension provided");
		btnSave.addActionListener(this);
		add(btnSave,"gapleft 25,gaptop 10");
		
		lblSaveStatus = new JLabel("Save Status");
		add(lblSaveStatus, "alignx left");
	}
	
	private void setupCardTimeslot() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == btnLoadFile){
			int returnVal = fChooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				if(manager.loadFile(fChooser.getSelectedFile())){
					textCurrentName.setText(manager.getCurrentFile());
					lblLoadStatus.setText("Load successful");
					lblLoadStatus.setForeground(Color.GREEN.darker());
					if(currentCard != null)
						currentCard.setup();
				}
				else{
					textCurrentName.setText("");
					lblLoadStatus.setText("Load failed");
					lblLoadStatus.setForeground(Color.RED);
				}
			}	
		}
		else if(e.getSource() == btnCreateNewFile){
			manager.resetUniversity();
			textCurrentName.setText("NEW FILE");
		}
		else if(e.getSource() == btnSave){
			int returnVal = fChooser.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				if(manager.saveFile(fChooser.getSelectedFile())){
					lblSaveStatus.setText("Save successful");
					lblSaveStatus.setForeground(Color.GREEN.darker());					
				}
				else{
					lblSaveStatus.setText("Save failed");
					lblSaveStatus.setForeground(Color.RED);
				}
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getStateChange() == ItemEvent.SELECTED){
			CardLayout cl = (CardLayout)(cards.getLayout());
			String item = (String)e.getItem();
			cl.show(cards, item);
			if(item != "Select:"){
				 currentCard = (CardPanel)cardsPanels.get(item);
				 currentCard.setup();
			}
		}
	}
}
