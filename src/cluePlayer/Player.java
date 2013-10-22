package cluePlayer;

import java.util.ArrayList;

public class Player implements Comparable {
	private ArrayList <Card> cards = new ArrayList<Card>();
	private String name, color;
	private int index, row, column;
	public Card disproveSuggestion(String person, String room, String weapon){
		return new Card();
	}

	public Player(String name, String color, int row, int column) {
		super();
		this.name = name;
		this.color = color;
		this.row = row;
		this.column = column;
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
