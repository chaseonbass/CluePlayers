package tests;

import static org.junit.Assert.*;
import cluePlayer.*;
import clueGame.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cluePlayer.ClueGame;
import cluePlayer.Solution;

public class GameActionTests {
	public  static ClueGame cg;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cg = new ClueGame();
		cg.loadConfigFiles("legend", "Weapons.txt", "Players.txt");
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
	board.calcTargets(board.calcIndex(14,2), 2);
	int loc_12_0Tot = 0;
	int loc_14_2Tot = 0;
	int loc_15_1Tot = 0;
	// Run the test 100 times
	for (int i=0; i<100; i++) {
		BoardCell selected = player.pickLocation(board.getTargets());
		if (selected == board.getCellAt(12, 0))
			loc_12_0Tot++;
		else if (selected == board.getCellAt(14, 2))
			loc_14_2Tot++;
		else if (selected == board.getCellAt(15, 1))
			loc_15_1Tot++;
		else
			fail("Invalid target selected");
	}
	// Ensure we have 100 total selections (fail should also ensure)
	assertEquals(100, loc_12_0Tot + loc_14_2Tot + loc_15_1Tot);
	// Ensure each target was selected more than once
	assertTrue(loc_12_0Tot > 10);
	assertTrue(loc_14_2Tot > 10);
	assertTrue(loc_15_1Tot > 10);							
}
	
	public void disproveSuggestion(){
		
	}
	public void makeSuggestion(){
		
	}

}
