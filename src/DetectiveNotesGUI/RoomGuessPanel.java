package DetectiveNotesGUI;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class RoomGuessPanel extends JPanel{
	private JComboBox gRoom;
	public RoomGuessPanel(){
		setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		
		gRoom = createRoomCombo();
		//gRoom.setPreferredSize(new Dimension(150,150));
		
		add(gRoom);
		
	}
	private JComboBox<String> createRoomCombo()
	{
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Unsure");
		combo.addItem("Ballroom");
		combo.addItem("Billiard Room");
		combo.addItem("Conservatory");
		combo.addItem("Dining Room");
		combo.addItem("Hall");
		combo.addItem("Kitchen");
		combo.addItem("Library");
		combo.addItem("Lounge");
		combo.addItem("Study");
		return combo;
	}
	
}
