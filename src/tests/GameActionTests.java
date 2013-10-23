package tests;

import static org.junit.Assert.*;
import cluePlayer.*;
import clueGame.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cluePlayer.Card;
import cluePlayer.ClueGame;
import cluePlayer.Solution;

public class GameActionTests {
	public static ClueGame cg;
	public static Card kittenCard;
	public static Card batmanCard;
	public static Card libraryCard;
	public static Card jokerCard;
	public static Card batarangCard;
	public static Card conservatoryCard;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cg = new ClueGame();
		cg.loadConfigFiles("legend", "Weapons.txt", "Players.txt");
		kittenCard = new Card("Kitten", Card.CardType.WEAPON);
		batarangCard = new Card("BatarangCard", Card.CardType.WEAPON);
		batmanCard = new Card("Batman", Card.CardType.PERSON);
		jokerCard = new Card("Joker", Card.CardType.PERSON);
		libraryCard = new Card("Library", Card.CardType.ROOM);
		conservatoryCard = new Card("Conservatory", Card.CardType.ROOM);
		
	}
	
	//test to make sure the right accusations is made.
	@Test
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
	@Test
	public void testTargetRandomSelection() {
	Board board = new Board("BoardLayout.csv", "legend.txt");
	ComputerPlayer player = new ComputerPlayer("Joker", "Green", 9, 15);
	// Pick a location with no rooms in target, just three targets
	board.calcTargets(board.calcIndex(0,20), 2);
	int loc_2_20Tot = 0;
	int loc_1_19Tot = 0;
	int loc_0_18Tot = 0;
	// Run the test 100 times
	for (int i=0; i<100; i++) {
		BoardCell selected = player.pickLocation(board.getTargets());
		if (selected == board.getCellAt(2, 20))
			loc_2_20Tot++;
		else if (selected == board.getCellAt(1,19))
			loc_1_19Tot++;
		else if (selected == board.getCellAt(0, 18))
			loc_0_18Tot++;
		else
			fail("Invalid target selected");
	}
	// Ensure we have 100 total selections (fail should also ensure)
	assertEquals(100, loc_2_20Tot + loc_1_19Tot + loc_0_18Tot);
	// Ensure each target was selected more than once
	assertTrue(loc_2_20Tot > 10);
	assertTrue(loc_1_19Tot > 10);
	assertTrue(loc_0_18Tot > 10);							
}
	@Test
	public void testTargetRoomSelectionNotVisited() {
		Board board = new Board("BoardLayout.csv", "legend.txt");
	// Ensures room loc is picked everytime
	board.calcTargets(board.calcIndex(5,20), 2);
	int loc_7_20Tot = 0;
	int loc_6_19Tot = 0;
	int loc_5_18Tot = 0;
	// Run the test 100 times
	for (int i=0; i<100; i++) {
		ComputerPlayer player = new ComputerPlayer();
		BoardCell selected = player.pickLocation(board.getTargets());
		if (selected == board.getCellAt(2, 20))
			loc_7_20Tot++;
		else if (selected == board.getCellAt(1,19))
			loc_6_19Tot++;
		else if (selected == board.getCellAt(0, 18))
			loc_5_18Tot++;
		else
			fail("Invalid target selected");
	}
	// Ensure we have 100 total selections (fail should also ensure)
	assertEquals(100, loc_7_20Tot + loc_6_19Tot + loc_5_18Tot);
	// Ensure each target was selected more than once
	assertTrue(loc_7_20Tot == 100);
	assertTrue(loc_6_19Tot == 0);
	assertTrue(loc_5_18Tot == 0);							
}

	@Test
	public void disproveSuggestion(){
		ComputerPlayer player = new ComputerPlayer();
		Player suspected = new Player();
		player.addCard(batarangCard);
		player.addCard(batmanCard);
		player.addCard(conservatoryCard);
		player.addCard(jokerCard);
		player.addCard(kittenCard);
		player.addCard(libraryCard);
		
		//One player, one correct match
		suspected.disproveSuggestion("Batman", "Conservatory", "Batarang" );
		Assert.assertEquals(suspected,batmanCard);
		suspected.disproveSuggestion("Joker", "Library", "Batarang");
		Assert.assertEquals(suspected, libraryCard);
		suspected.disproveSuggestion("Joker", "Conservatory", "Kitten");
		Assert.assertEquals(suspected, kittenCard);
		suspected.disproveSuggestion(null, null, null);
		Assert.assertEquals(suspected, null);
		
		
		//One player, multiple matches
		
		
		
		

	}
	public void makeSuggestion(){
		
	}

}
