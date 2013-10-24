package cluePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import clueGame.Board;

public class Player implements Comparable {
	private Map <String, Card> cards;
	private String name, color;
	protected int index, row, column;
	Board board;
	public Card disproveSuggestion(String person, String room, String weapon){
		return new Card();
	}
	public Player(){
		cards = new HashMap<String , Card>();
	}
	


	public Player(String name, String color, int row, int column, Board board) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
		this.board = board;
		cards = new HashMap<String , Card>();
	}

	public String getName(){
		return name;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	public void setColumn(int column) {
		this.column = column;
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
	public Map<String, Card> getCards(){
		return cards;
	}
	public void addCard(Card c){
		cards.put(c.getName(), c);
	}
	public int compareTo(Object o) {
		if(this.name.equalsIgnoreCase(((Player) o).getName())){
			if(this.color.equalsIgnoreCase(((Player) o).getColor())){
				if(this.index == ((Player) o).getIndex()){
					return 1;
				}
			}
		}
		return 0;
	}


	
	
}
