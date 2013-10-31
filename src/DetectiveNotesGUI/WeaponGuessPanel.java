package DetectiveNotesGUI;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class WeaponGuessPanel extends JPanel{
	private JComboBox gWeapon;
	public WeaponGuessPanel(){
		setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		gWeapon= createWeaponCombo();
		//gWeapon.setPreferredSize(new Dimension(150,150));
		add(gWeapon);
		
	}
	private JComboBox<String> createWeaponCombo()
	{
		JComboBox<String> combo = new JComboBox<String>();
		combo.addItem("Unsure");
		combo.addItem("Batarang");
		combo.addItem("The Force");
		combo.addItem("Kitten");
		combo.addItem("Napalm");
		combo.addItem("Pikachu");
		combo.addItem("Rotary Saw");

		

		return combo;
	}
	
}
