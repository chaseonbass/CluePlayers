package tests;

import static org.junit.Assert.*;
import cluePlayer.*;
import clueGame.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cluePlayer.Card.CardType;
import cluePlayer.ClueGame;
import cluePlayer.Solution;
import cluePlayer.Suggestion;

public class GameActionTests {
	public  static ClueGame cg;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cg = new ClueGame("BoardLayout.csv", "legend.txt");
		cg.loadConfigFiles("legend", "Weapons.txt", "Players.txt");
		cg.board.loadConfigFiles();
		cg.board.calcAdjacencies();
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
	ComputerPlayer player = new ComputerPlayer("Joker", "Green", 9, 15, cg.board);
	// Pick a location with no rooms in target, just three targets
	cg.board.startTargets(0, 20, 2);
	int loc_2_20Tot = 0;
	int loc_1_19Tot = 0;
	int loc_0_18Tot = 0;
	// Run the test 100 times
	for (int i=0; i<100; i++) {
		BoardCell selected = player.pickLocation(cg.board.getTargets());
		if (selected == cg.board.getCellAt(2, 20))
			loc_2_20Tot++;
		else if (selected == cg.board.getCellAt(1,19))
			loc_1_19Tot++;
		else if (selected == cg.board.getCellAt(0, 18))
			loc_0_18Tot++;
		else
			fail("Invalid target selected");
	}
	// Ensure we have 100 total selections (fail should also ensure)
	assertEquals(100, loc_2_20Tot + loc_1_19Tot + loc_0_18Tot);
	// Ensure each target was selected more than once
	System.out.println(loc_2_20Tot);
	System.out.println(loc_1_19Tot);
	assertTrue(loc_2_20Tot > 10);
	assertTrue(loc_1_19Tot > 10);
	assertTrue(loc_0_18Tot > 10);							
}
	@Test
	public void testTargetRoomSelectionNotVisited() {
	// Ensures room loc is picked everytime
	cg.board.startTargets(5, 20, 2);
	int loc_7_20Tot = 0;
	int loc_6_19Tot = 0;
	int loc_5_18Tot = 0;
	// Run the test 100 times
	for (int i=0; i<100; i++) {
		ComputerPlayer player = new ComputerPlayer("Devil", "Red", 5, 20, cg.board);
		BoardCell selected = player.pickLocation(cg.board.getTargets());
		if (selected.equals(cg.board.getCellAt(7, 20))) 
			loc_7_20Tot++;
		else if (selected == cg.board.getCellAt(6,19))
			loc_6_19Tot++;
		else if (selected == cg.board.getCellAt(5, 18))
			loc_5_18Tot++;
		else
			fail("Invalid target selected");
	}
	// Ensure we have 100 total selections (fail should also ensure)
	assertEquals(100, loc_7_20Tot + loc_6_19Tot + loc_5_18Tot);
	// Ensure room was selected every time
	assertTrue(loc_7_20Tot == 100);
	assertTrue(loc_6_19Tot == 0);
	assertTrue(loc_5_18Tot == 0);							
}
	@Test
	public void testTargetRoomSelectionVisited() {
		ComputerPlayer player = new ComputerPlayer("Devil", "Red", 5, 20, cg.board);
		player.pickLocation(cg.board.getTargets());  // picks location to set that room to visited
	// Ensures room loc is picked everytime
	cg.board.startTargets(5, 20, 2);
	int loc_7_20Tot = 0;
	int loc_6_19Tot = 0;
	int loc_5_18Tot = 0;
	int loc_4_19Tot = 0;
	int loc_3_20Tot = 0;
	// Run the test 100 times
	for (int i=0; i<100; i++) {
		BoardCell selected = player.pickLocation(cg.board.getTargets());
		System.out.println(selected);
		if (selected == cg.board.getCellAt(7, 20)) 
			loc_7_20Tot++;
		else if (selected == cg.board.getCellAt(6,19))
			loc_6_19Tot++;
		else if (selected == cg.board.getCellAt(3, 20))
			loc_3_20Tot++;
		else if (selected == cg.board.getCellAt(4, 19))
			loc_4_19Tot++;
		else if (selected == cg.board.getCellAt(5, 18))
			loc_5_18Tot++;
	}
	// Ensure we have 100 total selections (fail should also ensure)
	assertEquals(100, loc_7_20Tot + loc_6_19Tot + loc_5_18Tot + loc_4_19Tot + loc_3_20Tot );
	// Ensure the selection was made at random instead of always choosing the room
	System.out.println(loc_7_20Tot+" " + loc_6_19Tot + " "+ loc_5_18Tot );
	assertTrue(loc_7_20Tot > 9);
	assertTrue(loc_6_19Tot >  9);
	assertTrue(loc_5_18Tot > 9);							
}
	
	public void disproveSuggestion(){
		
		
	}
	
	public void addCardstoSeen(Card b){
		cg.addSeenCards(b);
		
	}
	
	@Test
	public void makeSuggestion(){  //  tests that correct suggestion is made by making all but three cards seen
		ComputerPlayer player = new ComputerPlayer("Joker", "Green", 7, 20, cg.board);
		Suggestion sugg = new Suggestion ("Penguin", "Rotary Saw", "Study");
		int timesSChosen= 0;
		int timesFChosen = 0;
	// batman joker batarang kitten conservatory library 
		Card a = new Card("Pikachu", CardType.WEAPON);
		addCardstoSeen(a);
		Card b = new Card("Arnold", CardType.PERSON);
		addCardstoSeen(b);
		for(int i = 0; i < 100; i ++){
			Suggestion guess = player.createSuggestion();
			if(guess.getRoom() == "Study"){
				timesSChosen++;
			}
			if(guess.getWeapon() == "The Force")
				timesFChosen ++;
		}
		Assert.assertTrue(timesSChosen == 100);  //tests that the Study is chosen everytime for the room
		Assert.assertTrue(timesFChosen > 10);    // tests that the force is chosen few times
		Assert.assertTrue(timesFChosen < 100);    // tests that the force is not chosen everytime
		Card c = new Card("TwoFace", CardType.PERSON);
		addCardstoSeen(c);
		Card d = new Card("Ivy", CardType.PERSON);
		addCardstoSeen(d);
		Card e = new Card("Napalm", CardType.WEAPON);
		addCardstoSeen(e);
		Card f = new Card("The Force", CardType.WEAPON);
		addCardstoSeen(f);
		Suggestion compSuggest = player.createSuggestion();
		Assert.assertTrue(compSuggest.equals(sugg));
		
	}

}
