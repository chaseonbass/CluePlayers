package DetectiveNotesGUI;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class PeoplePanel extends JPanel{
	private JCheckBox Batman, Joker, TwoFace, Ivy, Arnold, Penguin;
	public PeoplePanel(){
		setBorder(new TitledBorder(new EtchedBorder(), "People"));
		Batman= new JCheckBox("Batman");
		Joker= new JCheckBox("Joker");
		TwoFace= new JCheckBox("TwoFace");
		Ivy= new JCheckBox("Ivy");
		Arnold= new JCheckBox("Arnold");
		Penguin= new JCheckBox("Penguin");
		
		setLayout(new GridLayout(0, 2));
		setPreferredSize(new Dimension(150,150));
		add(Batman);
		add(Joker);
		add(TwoFace);
		add(Ivy);
		add(Arnold);
		add(Penguin);
		
	}

}
