package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import cluePlayer.*;
import clueGame.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cluePlayer.Card;
import cluePlayer.Card.CardType;
import cluePlayer.ClueGame;
import cluePlayer.HumanPlayer;
import cluePlayer.Solution;
import cluePlayer.Suggestion;
import clueGame.Board;

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
	// Ensure room was selected every time
	assertTrue(loc_7_20Tot == 100);
	assertTrue(loc_6_19Tot == 0);
	assertTrue(loc_5_18Tot == 0);							
}
	@Test
	public void testTargetRoomSelectionVisited() {
		Board board = new Board("BoardLayout.csv", "legend.txt");
		ComputerPlayer player = new ComputerPlayer();
		player.pickLocation(board.getTargets());  // picks location to set that room to visited
	// Ensures room loc is picked everytime
	board.calcTargets(board.calcIndex(5,20), 2);
	int loc_7_20Tot = 0;
	int loc_6_19Tot = 0;
	int loc_5_18Tot = 0;
	// Run the test 100 times
	for (int i=0; i<100; i++) {
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
	// Ensure the selection was made at random instead of always choosing the room
	assertTrue(loc_7_20Tot > 9);
	assertTrue(loc_6_19Tot >  9);
	assertTrue(loc_5_18Tot > 9);							
}
	
	
	
	@Test
	public void disproveSuggestion(){
		Player player = new Player();
		Card suspected = new Card();
		player.addCard(batarangCard);
		player.addCard(batmanCard);
		player.addCard(conservatoryCard);
		player.addCard(jokerCard);
		player.addCard(kittenCard);
		player.addCard(libraryCard);
		
		//One player, one correct match
		suspected= player.disproveSuggestion("Batman", "Study", "The Force" );
		Assert.assertEquals(batmanCard, suspected);
		suspected= player.disproveSuggestion("Penguin", "Library", "The Force");
		Assert.assertEquals(libraryCard, suspected);
		suspected= player.disproveSuggestion("Penguin", "Study", "Kitten");
		Assert.assertEquals(kittenCard, suspected);
		suspected= player.disproveSuggestion("Penguin", "Study", "The Force");
		Assert.assertEquals(null, suspected);

	}
	
	@Test
		//One player, multiple matches
	public void multiplematches(){
		Player player = new Player();
		Card suspected = new Card();
		suspected= player.disproveSuggestion("Batman", "Batarang", "Library" );
		int timesBatChosen =0;
		int timesBataChosen= 0;
		int timesLibChosen= 0;
		
		Player player2= new Player();
		
		for(int i= 0; i < 100; i++){
			if(suspected.equals(batmanCard)){
				timesBatChosen++;
			}
				else if(suspected.equals(batarangCard)){
					timesBataChosen++;
				}
				else if(suspected.equals(libraryCard)){
					timesLibChosen++;
			}
				else 
					fail("That card was not part of the suggestion");
			
		}
		
		Assert.assertTrue(timesBataChosen > 10);  //tests that the batarang is chosen few time
		Assert.assertTrue(timesBatChosen > 10);    // tests that the bat is chosen few times
		Assert.assertTrue(timesLibChosen > 10);    // tests that the library was chosen few times
		
		
		
		timesBataChosen= 0;
		timesLibChosen= 0;
		
		
		//match weapon and room
		for(int i= 0; i < 100; i++){
				if(suspected.equals(batarangCard)){
					timesBataChosen++;
				}
				else if(suspected.equals(libraryCard)){
					timesLibChosen++;
			}
				else 
					fail("That card was not part of the suggestion");
			
		}
		
		Assert.assertTrue(timesBataChosen > 10);  //tests that the batarang is chosen few time
		Assert.assertTrue(timesLibChosen > 10);    // tests that the bat is chosen few times
		
		
		
		//match person and room
		timesBatChosen= 0;
		timesLibChosen= 0;
		for(int i= 0; i < 100; i++){
				if(suspected.equals(batmanCard)){
					timesBatChosen++;
				}
				else if(suspected.equals(libraryCard)){
					timesLibChosen++;
			}
				else 
					fail("That card was not part of the suggestion");
			
		}
		
		Assert.assertTrue(timesBatChosen > 10);  //tests that the Batman is chosen few time
		Assert.assertTrue(timesLibChosen > 10);    // tests that the bat is chosen few times
		
	}
	
	public void testAllPlayerinQueried(){
		ArrayList<ComputerPlayer> cplayers = new ArrayList<ComputerPlayer>();
		HumanPlayer hplayer= new HumanPlayer("Batman", "Black", 7, 20);
		Card suggestion= new Card();
		ComputerPlayer cplayer1 = new ComputerPlayer();
		cplayer1.addCard(batarangCard);
		cplayers.add(cplayer1);
		ComputerPlayer cplayer2 = new ComputerPlayer();
		cplayer2.addCard(libraryCard);
		cplayers.add(cplayer2);
		ComputerPlayer cplayer3 = new ComputerPlayer();
		cplayer3.addCard(conservatoryCard);
		cplayers.add(cplayer3);
		ComputerPlayer cplayer4 = new ComputerPlayer();
		cplayer4.addCard(jokerCard);
		cplayers.add(cplayer4);
		ComputerPlayer cplayer5 = new ComputerPlayer();
		cplayer5.addCard(kittenCard);
		cplayers.add(cplayer5);
		
		cg.setComputerPlayer(cplayers);
		cg.setHumanPlayer(hplayer);
		
		//A suggestion that no player can prove
		suggestion= cg.handleSuggestion("Penguin", "Kitchen", "The Force", cplayer5 );
		Assert.assertEquals(null, suggestion);
		
		//A suggestion only human can prove
		suggestion= cg.handleSuggestion("Batman", "Kitchen", "The Force", cplayer2);
		Assert.assertEquals("Batman", suggestion);
		
		//cplayer1 makes the suggestion and is the only who can disprove it
		suggestion= cg.handleSuggestion("Penguin", "Kitchen", "Batarang", cplayer1);
		Assert.assertEquals(null, suggestion);
		
		//hplayer makes the suggestion and is the only who can disprove it
		suggestion= cg.handleSuggestion("Batman", "Kitchen", "The Force", hplayer);
		Assert.assertEquals(null, suggestion);
		
		//test the order the players are queried
		suggestion= cg.handleSuggestion("Batman", "Kitchen", "Kitten", cplayer1);
		Assert.assertEquals(batmanCard, "Batman");
		Assert.assertEquals(kittenCard, "Kitten");
	}
	public void addCardstoSeen(Card b){
		cg.addSeenCards(b);
		
	}
	
	@Test
	public void makeSuggestion(){  //  tests that correct suggestion is made by making all but three cards seen
		ComputerPlayer player = new ComputerPlayer("Joker", "Green", 7, 20);
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
