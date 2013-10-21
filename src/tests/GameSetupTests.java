package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
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
		Assert.assertTrue(cg.players.contains("Batman"));
		Assert.assertTrue(cg.players.contains("Joker"));
		
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
