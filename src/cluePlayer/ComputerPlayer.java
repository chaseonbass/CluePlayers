package cluePlayer;

import java.util.Random;
import java.util.Set;
import clueGame.*;
import cluePlayer.*;

public class ComputerPlayer extends Player{
	RoomCell lastVisited;
	
	public ComputerPlayer(){
		super();
	}
	
	public ComputerPlayer(String name, String color, int row, int column, Board board) {
		super(name, color, row, column, board);
		// TODO Auto-generated constructor stub
	}


	private char lastRoomVisited;
	
	public BoardCell pickLocation(Set<BoardCell> targets){
		Random rand = new Random();
		int randNum = rand.nextInt(targets.size());
		int index = 0;
		if(lastVisited == null){
			for(BoardCell ind : targets){
				if(ind.isRoom()){
					lastVisited = (RoomCell) ind;
					return ind;
				}
			}
			for(BoardCell ind : targets){
				if(index == randNum)
					return ind;
				index++;
			}
		}
		else{
			for(BoardCell ind : targets){
				if(ind.isRoom() && !ind.equals(lastVisited)){
					lastVisited = (RoomCell) ind;
					return ind;
				}
			}
			for(BoardCell ind : targets){
				if(index == randNum)
					return ind;
				index++;
			}
				
		}
		return null;
	}
	public Suggestion createSuggestion(){
		return new Suggestion("x", "y", "z");
	}

	
	public void updateSeen(Card seen){
		
	}

}
