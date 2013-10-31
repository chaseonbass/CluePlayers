package DetectiveNotesGUI;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class WeaponsPanel extends JPanel{
	private JCheckBox Batarang, Rotary, Kitten, Force, Pikachu, Napalm;

	public WeaponsPanel(){
		setBorder(new TitledBorder(new EtchedBorder(), "People"));
		Batarang= new JCheckBox("Batarang");
		Rotary= new JCheckBox("Rotary Saw");
		Napalm= new JCheckBox("Napalm");
		Kitten= new JCheckBox("Kitten");
		Force= new JCheckBox("The Force");
		Pikachu= new JCheckBox("Pikachu");
		
		setLayout(new GridLayout(0, 2));
		setPreferredSize(new Dimension(150,150));
		
		add(Batarang);
		add(Rotary);
		add(Napalm);
		add(Kitten);
		add(Force);
		add(Pikachu);
		
	}
}
