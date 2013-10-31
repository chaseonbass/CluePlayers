package DetectiveNotesGUI;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class PersonGuessPanel extends JPanel{
	private JComboBox gPerson;
	public PersonGuessPanel(){
		
		setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		gPerson= createPersonCombo();
		//gPerson.setPreferredSize(new Dimension(150,150));
		add(gPerson);
		
	}
	private JComboBox createPersonCombo()
	{
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Unsure");
		combo.addItem("Arnold");
		combo.addItem("Batman");
		combo.addItem("Ivy");
		combo.addItem("Joker");
		combo.addItem("Penguin");
		combo.addItem("TwoFace");

		return combo;
		
	}
	
}
