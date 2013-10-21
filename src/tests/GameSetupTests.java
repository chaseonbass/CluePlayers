package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.*;
import cluePlayer.*;

public class GameSetupTests {
	ClueGame cg;
	@BeforeClass
	public void configGame(){
		cg = new ClueGame();
		cg.loadConfigFiles();
	}
	
	
	@Test
	public void testLoadPlayers(){
		Assert.assertTrue(cg.players.size() == 5);
		Assert.assertTrue(cg.players.contains("Batman"));
		Assert.assertTrue(cg.players.contains("Joker"));
		Assert.assertTrue(cg.players.contains("Penguin"));
		Assert.assertTrue(cg.players.get(0).getName() == "Batman");
		Assert.assertTrue(cg.players.get(0).getColor().equalsIgnoreCase("Black")); //Batman color
		Assert.assertTrue(cg.players.get(1).getColor().equalsIgnoreCase("Green")); //Joker color
		Assert.assertTrue(cg.players.get(4).getColor().equalsIgnoreCase("White")); //penguin color
		Assert.assertTrue(cg.players.get(0).getRow() == 6);
		Assert.assertTrue(cg.players.get(0).getRow() == 15);
		Assert.assertTrue(cg.players.get(0).getRow() == 8);
		Assert.assertTrue(cg.players.get(0).getColumn() == 3);
		Assert.assertTrue(cg.players.get(0).getColumn() == 1);
		Assert.assertTrue(cg.players.get(0).getColumn() == 0);
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
		Assert.assertTrue(numWeapons == 6);
		Assert.assertTrue(numRooms == 9);
		Assert.assertTrue(numPeople == 6);
		Assert.assertTrue(cg.cards.contains(new Card("Batman", Card.CardType.PERSON)));
		Assert.assertTrue(cg.cards.contains(new Card("Conservatory", Card.CardType.ROOM)));
		Assert.assertTrue(cg.cards.contains(new Card("Kitten", Card.CardType.WEAPON)));
	}
	@Test
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
	public void testAccusations(){
		cg.setSolution("Batman", "Kitten", "Library");
		Solution falseOne = new Solution ("Joker", "Kitten", "Library");
		Solution falseTwo = new Solution ("Batman", "Batarang", "Library");
		Solution falseThree = new Solution ("Batman", "Kitten", "Conservatory");
		Solution correctoMundo = new Solution ("Batman", "Kitten", "Library");
		Assert.assertFalse(cg.checkAccusation(falseOne));
		Assert.assertFalse(cg.checkAccusation(falseTwo));
		Assert.assertFalse(cg.checkAccusation(falseThree));
		Assert.assertTrue(cg.checkAccusation(correctoMundo));
	}


}
