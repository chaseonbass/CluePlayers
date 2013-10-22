package cluePlayer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import cluePlayer.Card.CardType;

public class ClueGame {
	public ArrayList<Player> players = new ArrayList<Player>();
	public ArrayList <Card> cards = new ArrayList<Card>();
	private Solution solution;
	public void deal(){
		
	}
	public void loadRoomCards(String legend){
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
				cards.add(new Card(tempRoomName, CardType.ROOM));
			}
		}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	public void loadConfigFiles(String legend, String weaponFile, String peopleFile){
		loadRoomCards(legend);
		loadWeaponCards(weaponFile);
		loadPeopleCards(peopleFile);
	}
	public void loadWeaponCards(String weaponFile) {
		try{
			FileReader legendReader = new FileReader(weaponFile);
			Scanner legendIn = new Scanner(legendReader);
			while(legendIn.hasNextLine()){
				cards.add(new Card(legendIn.nextLine(), CardType.WEAPON));
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	public void loadPeopleCards(String peopleFile){
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
					players.add(p);
				}
				else{
					Player p = new ComputerPlayer(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]));
					players.add(p);
				}
				cards.add(new Card(line[0], CardType.PERSON));
				lineNumber ++;
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
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
