package cluePlayer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.RoomCell;
import cluePlayer.Card.CardType;

public class ClueGame {
	private Set<Card> seenCards;
	public Map<String, Player> players;
	public Map <String, Card> cards;
	private Solution solution;
	
	public ClueGame(){
		seenCards = new HashSet<Card>();
	}
	
	public void addSeenCards(Card c){
		seenCards.add(c);
	}
	
	public void deal(){  // deals cards to players
		ArrayList <Card> aCards = new ArrayList<Card>();  // created to make things easier
		ArrayList <Player> aPlayers = new ArrayList<Player>();  // makes things easier
		for(String playKey : players.keySet()){
			aPlayers.add(players.get(playKey));
		}
		for(String key : cards.keySet()){
			aCards.add(cards.get(key));
		}
		for(int i = 0; i < aCards.size(); i++){
			aPlayers.get((i)%aPlayers.size()).addCard(aCards.get(i));  
		}
		
		
	}
	public void loadRoomCards(String legend){  // reads rooms and adds to cards
		cards = new HashMap <String, Card>();
		try{
		FileReader legendReader = new FileReader(legend);
		Scanner legendIn = new Scanner(legendReader);
		int lineNumber = 0;
		
		while (legendIn.hasNextLine()) {
			lineNumber = lineNumber + 1;
			String legendLine = legendIn.nextLine();
			if (!legendLine.contains(","))
				throw new BadConfigFormatException(legend, ",", lineNumber);
			if (legendLine.indexOf(',')!=legendLine.lastIndexOf(','))
				throw new BadConfigFormatException(legend, "MULTIPLE ','", lineNumber);
			
			String[] splitLegendLine = legendLine.split(",");
			// Splits the line into two strings, the first being the initial, 
			//   the second being the name of the room   
			// Check if we actually have a character
			if (splitLegendLine[0].length() > 1) {
				throw new BadConfigFormatException(legend, splitLegendLine[0], lineNumber);
			} else {
				char tempInitial = splitLegendLine[0].toCharArray()[0];
				String tempRoomName = splitLegendLine[1].trim();
				if(!tempRoomName.equals("Closet") && !tempRoomName.equals("Walkway")){
					Card c = new Card(tempRoomName, CardType.ROOM);
					cards.put(tempRoomName, c);
				}
			}
		}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	public void loadConfigFiles(String legend, String weaponFile, String peopleFile){  // loads all the files with one function
		loadRoomCards(legend);
		loadWeaponCards(weaponFile);
		loadPeopleCards(peopleFile);
	}
	
	public void loadWeaponCards(String weaponFile) {  // adds weapons to the cards
		try{
			FileReader legendReader = new FileReader(weaponFile);
			Scanner legendIn = new Scanner(legendReader);
			String weaponName;
			while(legendIn.hasNextLine()){
				weaponName = legendIn.nextLine();
				Card c = new Card(weaponName, CardType.WEAPON);
				cards.put(weaponName, c);
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	public void loadPeopleCards(String peopleFile){  // adds each person as a card and as a player
		players = new HashMap<String, Player>();
		try{
			String name;
			String[] line;
			FileReader legendReader = new FileReader(peopleFile);
			Scanner legendIn = new Scanner(legendReader);
			int lineNumber = 0;
			while(legendIn.hasNext()){
				line = legendIn.nextLine().split(", ");
				name = line[0];
				if(lineNumber == 0){
					Player p = new HumanPlayer(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]));
					players.put(line[0], p);
				}
				else{
					Player p = new ComputerPlayer(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]));
					players.put(line[0],p);  // adds the player
				}
				Card c = new Card(line[0], CardType.PERSON);
				cards.put(line[0], c);  // adds as a card
				System.out.println(line[0]);
				lineNumber ++;
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void  selectAnswer(){  //empty
		
	}
	public void handleSuggestion(String person, String room, String weapon, Player accusingPerson){
		
	}
	
	
	public boolean checkAccusation(String person, String weapon, String room){
		if (person.equals(solution.getPerson()) && weapon.equals(solution.getWeapon()) && room.equals(solution.getRoom())){
			return true;
		}
		else{
		return false;
		}
	}
	

	public Solution getSolution(){ 
		return solution;
	}
	public void setSolution(Solution solution){  // done for testing purposes
		this.solution= solution;
	}
	
	
	
}
