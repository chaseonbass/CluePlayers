package tests;

import static org.junit.Assert.*;


import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import cluePlayer.*;
import clueGame.*;

public class GameSetupTests {
	public  static ClueGame cg;
	@BeforeClass
	public static void configGame(){
		cg = new ClueGame();
		cg.loadConfigFiles("legend", "Weapons.txt", "Players.txt");
	}
	
	
	@Test
	public void testLoadPlayers(){
		int expected = 6;
		int actual = cg.players.size();
		Assert.assertEquals(expected, actual);
//		Assert.assertTrue(cg.players.contains(new Player("Batman", "Black", 6, 3)));
//		Assert.assertTrue(cg.players.contains("Joker"));
//		Assert.assertTrue(cg.players.contains("Penguin"));
		String expecto = "Batman";
		String actualo = cg.players.get(0).getName();
		Assert.assertEquals(expecto, actualo);
		Assert.assertTrue(cg.players.get(0).getColor().equalsIgnoreCase("Black")); //Batman color
		Assert.assertTrue(cg.players.get(1).getColor().equalsIgnoreCase("Green")); //Joker color
		Assert.assertTrue(cg.players.get(5).getColor().equalsIgnoreCase("White")); //penguin color
		Assert.assertTrue(cg.players.get(0).getRow() == 6);
		Assert.assertTrue(cg.players.get(1).getRow() == 15);
		Assert.assertTrue(cg.players.get(5).getRow() == 8);
		Assert.assertTrue(cg.players.get(0).getColumn() == 3);
		Assert.assertTrue(cg.players.get(1).getColumn() == 1);
		Assert.assertTrue(cg.players.get(5).getColumn() == 0);
	}
	@Test
	public void testLoadCards(){
		Assert.assertTrue(cg.cards.size() == 21);
		int numWeapons = 0;
		int numRooms = 0;
		int numPeople = 0;
		for(int i = 0; i < cg.cards.size(); i++){
			if(cg.cards.get(i).getCartype() == Card.CardType.WEAPON)
				numWeapons ++;
			else if(cg.cards.get(i).getCartype() == Card.CardType.PERSON)
				numPeople ++;
			else if(cg.cards.get(i).getCartype() == Card.CardType.ROOM)
				numRooms ++;
		}
		//Test that there are 6 weapons, 6 people, and 9 rooms
		Assert.assertTrue(numWeapons == 6);
		Assert.assertTrue(numRooms == 9);
		Assert.assertTrue(numPeople == 6);
		
		//test what type the card is.Batman is a person, Conservatory is a room and kitten is a weapon
		Assert.assertTrue(cg.cards.contains(new Card("Batman", Card.CardType.PERSON)));
		Assert.assertTrue(cg.cards.contains(new Card("Conservatory", Card.CardType.ROOM)));
		Assert.assertTrue(cg.cards.contains(new Card("Kitten", Card.CardType.WEAPON)));
	}
	@Test
		//test that the cards are dealt correctly and it is equally distributed.
		//noone can have more than 1 card more or more than 1 card less than other players.
	public void testDealtCards(){
		cg.deal();
		int remaining = cg.cards.size();
		for(int i = 0; i < cg.players.size(); i ++){
			if(i < cg.players.size() -1){
				Assert.assertTrue(cg.players.get(i).getCards().size() == cg.players.get(i+1).getCards().size() || 
						cg.players.get(i).getCards().size() == cg.players.get(i+1).getCards().size() + 1	||
						cg.players.get(i).getCards().size() == cg.players.get(i+1).getCards().size() -1);
			}
			remaining = remaining - cg.players.get(i).getCards().size();
		}
		Assert.assertTrue(remaining == 0);
	}
	
	@Test
	public void testNoDuplicateCards(){
		cg.deal();
		Set <Card> cCards = new HashSet<Card>();
		for(int i = 0; i < cg.players.size(); i++){
			for(int j = 0; j < cg.players.get(i).getCards().size(); j++){
				cCards.add(cg.players.get(i).getCards().get(j));
			}
		}
		Assert.assertTrue(cg.cards.size() == cCards.size());
	}
	
	@Test
	
	//test to make sure the right accusations is made.
	public void testAccusations(){
		//this set the solution
		Solution correctoMundo = new Solution ("Batman", "Kitten", "Library");
		cg.setSolution(correctoMundo);
		
		// this is an example of a correct solution which means all three fields has to match the solution
		Assert.assertTrue(cg.checkAccusation("Batman", "Kitten", "Library"));
		
		// these are example of the ones that not suppose to be the solution
	
		//wrong person
		Assert.assertFalse(cg.checkAccusation("Joker", "Kitten", "Library"));
		//wrong weapon
		Assert.assertFalse(cg.checkAccusation("Batman", "Batarang", "Library"));
		//wrong weapon, wrong room
		Assert.assertFalse(cg.checkAccusation("Batman", "Batarang", "Conservatory"));
		
		
		

	}


}
