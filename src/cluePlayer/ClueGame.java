package cluePlayer;

import java.util.ArrayList;

public class ClueGame {
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList <Card> cards = new ArrayList<Card>();
	private Solution solution;
	public void deal(){
		
	}
	public void loadConfigFiles(){
		
	}
	public void  selectAnswer(){
		
	}
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson){
		
	}
	public boolean checkAccusation(Solution solution){
		return false;
	}
	public Solution getSolution(){
		return solution;
	}
	public void setSolution(String person, String weapon, String room){
		solution = new Solution(person, weapon, room);
	}
	
	
	
}
