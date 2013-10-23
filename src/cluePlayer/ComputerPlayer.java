package cluePlayer;

import java.util.Set;

import clueGame.BoardCell;

public class ComputerPlayer extends Player{
	public ComputerPlayer(){
		super();
	}
	
	public ComputerPlayer(String name, String color, int row, int column) {
		super(name, color, row, column);
		// TODO Auto-generated constructor stub
	}


	private char lastRoomVisited;
	
	public BoardCell pickLocation(Set<BoardCell> targets){
		return new BoardCell();
	}
	public void createSuggestion(){
		
	}

	
	public void updateSeen(Card seen){
		
	}

}
