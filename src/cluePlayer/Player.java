package cluePlayer;

import java.util.ArrayList;

public class Player {
	private ArrayList <Card> cards = new ArrayList<Card>();
	private String name, color;
	private int index, row, column;
	public Card disproveSuggestion(String person, String room, String weapon){
		return new Card();
	}
	public String getName(){
		return name;
	}
	public String getColor(){
		return color;
	}
	public int getIndex(){
		return index;
	}
	public int getRow(){
		return row;
	}
	public int getColumn(){
		return column;
	}
	public ArrayList<Card> getCards(){
		return cards;
	}
	
}
