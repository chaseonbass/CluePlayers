package DetectiveNotesGUI;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class RoomsPanel extends JPanel{
	private JCheckBox Kitchen, Lounge, Conservatory, Study, Billard, Dining, Ball, Hall, Library;
	public RoomsPanel(){
		setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		Kitchen= new JCheckBox("Kitchen");
		Lounge= new JCheckBox("Lounge");
		Conservatory= new JCheckBox("Conservatory");
		Study= new JCheckBox("Study");
		Billard= new JCheckBox("Billard Room");
		Dining= new JCheckBox("Dining");
		Ball= new JCheckBox("Ballroom");
		Hall= new JCheckBox("Hall");
		Library= new JCheckBox("Library");
		
		setLayout(new GridLayout(0, 2));
		setPreferredSize(new Dimension(150,150));
		
		add(Kitchen);
		add(Lounge);
		add(Conservatory);
		add(Study);
		add(Billard);
		add(Dining);
		add(Ball);
		add(Hall);
		add(Library);
		
	}

}
