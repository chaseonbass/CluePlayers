package DetectiveNotesGUI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import cluePlayer.Card.CardType;
public class BoardGUI extends JFrame{
	
	
	public Component createNorthLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(0,2));
		PeoplePanel pPanel= new PeoplePanel();
		PersonGuessPanel pgPanel= new PersonGuessPanel();
		panel.add(pPanel);
		panel.add(pgPanel);
		return panel;
	}
	public Component createCenterLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(0,2));
		RoomsPanel rPanel= new RoomsPanel();
		RoomGuessPanel rgPanel= new RoomGuessPanel();
		panel.add(rPanel);
		panel.add(rgPanel);
		return panel;
	}
	public Component createSouthLayout(){
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(0,2));
		WeaponsPanel wPanel= new WeaponsPanel();
		WeaponGuessPanel wgPanel= new WeaponGuessPanel();
	
		panel.add(wPanel);
		panel.add(wgPanel);
		return panel;
	}
	public BoardGUI(){
		setSize(new Dimension(600,600));
		setTitle("Defective Notes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(createNorthLayout(), BorderLayout.NORTH);
		add(createCenterLayout(), BorderLayout.CENTER);
		add(createSouthLayout(), BorderLayout.SOUTH);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BoardGUI gui= new BoardGUI();
		gui.setVisible(true);
	}

}
