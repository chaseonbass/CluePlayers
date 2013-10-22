package tests;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.*;
import cluePlayer.*;
import clueGame.*;

public class GameSetupTests {
	public  static ClueGame cg;
	@BeforeClass
	public static void configGame(){
		cg = new ClueGame();
		cg.loadConfigFiles("legend", "Weapons.txt", "Players.txt", "CardTypes.txt");
	}
	
	
	@Test
	public void testLoadPlayers(){
		int expected = 6;
		int actual = cg.players.size();
		Assert.assertEquals(expected, actual);
		Player b = new Player("Batman", "Black", 6, 3);
		Player c = new Player("Joker", "Green", 15, 1);
		Player d = new Player("Penguin", "White", 8, 0);
		Assert.assertTrue(cg.players.containsKey("Batman"));
		Assert.assertTrue(cg.players.containsKey("Joker"));
		Assert.assertTrue(cg.players.containsKey("Penguin"));
		String expecto = "Batman";
		String actualo = cg.players.get("Batman").getName();
		Assert.assertEquals(expecto, actualo);
		Assert.assertTrue(cg.players.get("Batman").getColor().equalsIgnoreCase("Black")); //Batman color
		Assert.assertTrue(cg.players.get("Joker").getColor().equalsIgnoreCase("Green")); //Joker color
		Assert.assertTrue(cg.players.get("Penguin").getColor().equalsIgnoreCase("White")); //penguin color
		Assert.assertTrue(cg.players.get("Batman").getRow() == 6);
		Assert.assertTrue(cg.players.get("Joker").getRow() == 15);
		Assert.assertTrue(cg.players.get("Penguin").getRow() == 8);
		Assert.assertTrue(cg.players.get("Batman").getColumn() == 3);
		Assert.assertTrue(cg.players.get("Joker").getColumn() == 1);
		Assert.assertTrue(cg.players.get("Penguin").getColumn() == 0);
	}
	@Test
	public void testLoadCards(){
		int expected = 21;
		int actual = cg.cards.size();
		Assert.assertEquals(expected, actual);
		int numWeapons = 0;
		int numRooms = 0;
		int numPeople = 0;
		for(String key : cg.cards.keySet()){
			if(cg.cards.get(key).getCartype() == Card.CardType.WEAPON)
				numWeapons ++;
			else if(cg.cards.get(key).getCartype() == Card.CardType.PERSON)
				numPeople ++;
			else if(cg.cards.get(key).getCartype() == Card.CardType.ROOM)
				numRooms ++;
		}
		Assert.assertTrue(numWeapons == 6);
		Assert.assertTrue(numRooms == 9);
		Assert.assertTrue(numPeople == 6);
		boolean expecto = true;
		Card b = new Card("Batman", Card.CardType.PERSON);
		boolean actualo = cg.cards.containsKey("Batman");
		Assert.assertTrue(cg.cards.containsKey("Batman"));
		Assert.assertTrue(cg.cards.containsKey("Conservatory"));
		Assert.assertTrue(cg.cards.containsKey("Kitten"));
	}
	@Test
	public void testDealtCards(){
		cg.deal();
		ArrayList<Player> aPlayers = new ArrayList<Player>();
		int remaining = cg.cards.size();
		for(String key : cg.players.keySet()){
			aPlayers.add(cg.players.get(key));
		}
		for(int i = 0; i < aPlayers.size(); i++){
			if(i < aPlayers.size() -1){
				Assert.assertTrue(aPlayers.get(i).getCards().size() == aPlayers.get(i+1).getCards().size() || 
						aPlayers.get(i).getCards().size() == aPlayers.get(i+1).getCards().size() + 1	||
								aPlayers.get(i).getCards().size() == aPlayers.get(i+1).getCards().size() -1);
			}
			remaining = remaining - aPlayers.get(i).getCards().size();
		}
		int expected = 0;
		int actual = remaining;
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testNoDuplicateCards(){
		cg.deal();
		Set <Card> cCards = new HashSet<Card>();  // by adding all the cards to a set we ensure that 
		for(String key : cg.players.keySet()){    // there are no duplicate cards
			for(String pKey : cg.players.get(key).getCards().keySet()){
				cCards.add(cg.players.get(key).getCards().get(pKey));
			}
		}
		Assert.assertTrue(cg.cards.size() == cCards.size());
	}
	
	@Test
	public void testAccusations(){
		cg.setSolution("Batman", "Kitten", "Library");
		Solution correctoMundo = new Solution ("Batman", "Kitten", "Library");
		Solution falseOne = new Solution ("Joker", "Kitten", "Library");
		Solution falseTwo = new Solution ("Batman", "Batarang", "Library");
		Solution falseThree = new Solution ("Batman", "Kitten", "Conservatory");
	
		Assert.assertFalse(cg.checkAccusation(falseOne));
		Assert.assertFalse(cg.checkAccusation(falseTwo));
		Assert.assertFalse(cg.checkAccusation(falseThree));
		Assert.assertTrue(cg.checkAccusation(correctoMundo));

	}


}
