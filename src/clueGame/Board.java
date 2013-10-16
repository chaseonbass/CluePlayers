package clueGame;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/** Name: ebreikss
 *  Date: Oct 1, 2013
 *  Purpose:
 */

public class Board {

	private ArrayList<BoardCell> cells; // Not yet implemented
	private int numRows;
	private int numColumns;
	private String layout, legend;
	private Map<Character, String> rooms; // from legend
	
	// ------------------------------------------------------------------
	
	private int[] index;
	private int baseOfPath;
	private Map<Integer, LinkedList<Integer>> adjMap = new HashMap<Integer, LinkedList<Integer>>();
	private Map<Integer, Boolean> visited;
	// private Set<Integer> targets;
	private Set<BoardCell> targets; // Suggested
	
	// ------------------------------------------------------------------
	
	public Board() {
		super();
		// This contains stuff to deal with C.R.'s tests for adjacencies.  Likely needs to be commented out
		layout = "ClueLayout.csv";
		legend = "ClueLegend.txt";
	}
	
	public Board(String layout, String legend) {
		super();
		this.layout = layout;
		this.legend = legend;
		//loadConfigFiles();
		// will probably just do loadConfigFiles()
	}
	
	public void loadRoomConfig() {
		// empty...
		loadConfigFiles();
	}
	
	public void loadBoardConfig() {
		// empty
	}

	// --------------------------------------------------------------------
	
	public void loadConfigFiles() throws BadConfigFormatException {
		
		// Need to initialize our big objects
		rooms = new HashMap<Character, String>();
		cells = new ArrayList<BoardCell>();
		
		FileReader layoutReader = null;
		FileReader legendReader = null;
		Scanner layoutIn = null;
		Scanner legendIn = null;
		try {
			layoutReader = new FileReader(layout);
			legendReader = new FileReader(legend);
			layoutIn = new Scanner(layoutReader);
			legendIn = new Scanner(legendReader);
		} catch (FileNotFoundException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}	
		
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
				rooms.put(tempInitial, tempRoomName);
			}
		}
		
		// Store our cvs file into an ArrayList
		ArrayList<String[]> tempList = new ArrayList<String[]>();
		
		while (layoutIn.hasNextLine()) {
			// Create an array of string arrays
			String next = layoutIn.nextLine();
			// System.out.println(next);
			tempList.add(next.split(","));
		}
		
		// Check we have proper dimensionality and contents
		int testLength = tempList.get(0).length;
		for (int i = 0; i < tempList.size(); i++) {
			int tempLength = tempList.get(i).length;
			if (tempLength != testLength) {
				throw new BadConfigFormatException(layout, i);
			} else {
				for (String s : tempList.get(i)) {
					if (s.length() > 2) {
						throw new BadConfigFormatException(layout, s, i);
					} else if (!rooms.containsKey(s.charAt(0))) {
						throw new BadConfigFormatException(layout, s, i);
					} else if (s.length() > 1 && 
							(s.charAt(1) != 'U' && s.charAt(1) != 'D' && s.charAt(1) != 'R' && s.charAt(1) != 'L' && s.charAt(1) != 'N')) {
						throw new BadConfigFormatException(layout, s, i);
					} else {
						if (s.equals("W"))
							cells.add(new Walkway());
						else {
							// System.out.println(new RoomCell(s));
							cells.add(new RoomCell(s));
						}
					}
				}
			}
		}
		
		numColumns = testLength;
		numRows = tempList.size();
		// System.out.println("Col: " + numColumns + ", Rows: " + numRows);
		
	}
	
	// --------------------------------------------------------------------
	
	public int calcIndex(int rowNum, int columnNum) {
		return numColumns*rowNum + columnNum;
	}

	public RoomCell getRoomCellAt(int rowNum, int columnNum) {
		// probably uses calcIndex
		if (rowNum < 0|| columnNum < 0 || rowNum >= numRows || columnNum >= numColumns){
			return null;
		} else if (cells.get(calcIndex(rowNum, columnNum)) instanceof Walkway) {
			return new RoomCell();
		}
		
		if (cells.get(calcIndex(rowNum, columnNum)) instanceof RoomCell)
			return (RoomCell) cells.get(calcIndex(rowNum, columnNum));
		return new RoomCell("XN");
	}

	 //overloaded getRoomCellAt
	public RoomCell getRoomCellAt(int index){
		if(index < 0 || index > calcIndex(numRows-1,numColumns-1)){
			return null;
		}  else if (cells.get(index) instanceof Walkway) {
			return new RoomCell();
		}
		
		if (cells.get(index) instanceof RoomCell)
			return (RoomCell) cells.get(index);
		return new RoomCell("XN");
	}
	
	// Alternate getCell function, cuz the one above is annoying!
	
	public BoardCell getCellAt(int rowNum, int columnNum) {
		if (rowNum < 0|| columnNum < 0 || rowNum >= numRows || columnNum >= numColumns){
			return null;
		} else {
			return cells.get(calcIndex(rowNum, columnNum));
		}
	}
	
	public BoardCell getCellAt(int index) {
		if (index < 0 || index > calcIndex(numRows-1,numColumns-1)){
			return null;
		} else {
			return cells.get(index);
		}
	}
	
	// -----------------------------------------------------------------
	// ADJ AND TARGET FUNCTIONS
	
	public void initBoard(int rows, int cols) {
		// likely calls everything else
		index = new int[numRows*numColumns];
		targets = new HashSet<BoardCell>(); // Reset targets
		
		// Initialize maps
		visited = new HashMap<Integer, Boolean>();
		for (int i = 0; i < index.length; i++){
			index[i] = i;
			visited.put(i, false);
		}
		
		//calcAdjacencies();
	}
	
	public boolean isWalkable(int i){
		// helper function
		if (getCells().get(i) instanceof RoomCell){
			if (getCells().get(i).isDoorway())
				return true;
		}
		else if (getCells().get(i) instanceof Walkway)
				return true;
		return false;
	}
	
	public boolean bothSameRoomDoorways(int doorA, int doorB){
		// helper function just for adjacencies
		BoardCell a = getCells().get(doorA);
		BoardCell b = getCells().get(doorB);
		if (a instanceof RoomCell && b instanceof RoomCell)
			if (a.isDoorway() && b.isDoorway()){
				if (((RoomCell) a).getRoomType() == ((RoomCell) b).getRoomType())
					return true;
			}
		return false;
	}
	
	public void calcAdjacencies(){
		
		initBoard(numRows,numColumns);
		
		for (int i = 0; i < index.length; i++) {
			Point temp = indexToCoord(index[i]);
			// NOTE: I fixed this function to return (r,c) instead of (c,r)
			int r = temp.x;
			int c = temp.y;

			LinkedList<Integer> tempAdj = new LinkedList<Integer>();
			
			// The if statements below still need to check for walls and doors
			//sorta checked, helper function right above called isWalkable(int index)
			
			//if you can walk on it (Walkway OR Room door)
			//check direction
			
				if (isWalkable(i)) {
					if (c > 0) { // left
						if (isWalkable(calcIndex(r, c - 1)) && !bothSameRoomDoorways(i, calcIndex(r, c - 1)))
							if (cells.get(calcIndex(r, c - 1)) instanceof Walkway){
								tempAdj.add(calcIndex(r, c - 1)); 
							}
							else if (((RoomCell) cells.get(calcIndex(r, c - 1))).getDoorDirection() == RoomCell.DoorDirection.RIGHT)
										tempAdj.add(calcIndex(r, c - 1)); 
					}
					if (c < numColumns - 1) { // right
						if (isWalkable(calcIndex(r, c + 1)) && !bothSameRoomDoorways(i, calcIndex(r, c + 1)))
							if (cells.get(calcIndex(r, c + 1)) instanceof Walkway){
								tempAdj.add(calcIndex(r, c + 1)); 
							}
							else if (((RoomCell) cells.get(calcIndex(r, c + 1))).getDoorDirection() == RoomCell.DoorDirection.LEFT)
										tempAdj.add(calcIndex(r, c + 1)); 
					}
					if (r > 0) { // up
						if (isWalkable(calcIndex(r - 1, c)) && !bothSameRoomDoorways(i, calcIndex(r - 1, c)))
							if (cells.get(calcIndex(r - 1, c)) instanceof Walkway){
								tempAdj.add(calcIndex(r - 1, c)); 
							}
							else if (((RoomCell) cells.get(calcIndex(r - 1, c))).getDoorDirection() == RoomCell.DoorDirection.DOWN)
										tempAdj.add(calcIndex(r - 1, c)); 
					}
					if (r < numRows - 1) { // down
						if (isWalkable(calcIndex(r + 1, c)) && !bothSameRoomDoorways(i, calcIndex(r + 1, c)))
							if (cells.get(calcIndex(r + 1, c)) instanceof Walkway){
								tempAdj.add(calcIndex(r + 1, c)); 
							}
							else if (((RoomCell) cells.get(calcIndex(r + 1, c))).getDoorDirection() == RoomCell.DoorDirection.UP)
										tempAdj.add(calcIndex(r + 1, c)); 
					}
				}
				
			adjMap.put(i, tempAdj);	
		}
	}
	
	public void startTargets(int row, int col, int steps){
		int index = calcIndex(row,col);
		baseOfPath = index; // For the even-roll cases
		targets = new HashSet<BoardCell>(); // reset targets
		visited.put(index, true);
		calcTargets(index,steps);
	}
	
	public void calcTargets(int index, int steps){
		// called inside startTargets()
		
		// need ifs that check for walls and such
		
		LinkedList<Integer> temp =  getAdjList(index);

		for (int adj : temp) {
			if(visited.get(adj)){
				// skip
			} else {
				visited.put(adj, true);
				if (steps == 1 || cells.get(adj).isDoorway()) {
					if (adj != baseOfPath)
						targets.add(cells.get(adj)); // We need to be grabbing BoardCell objects
				} else {
					calcTargets(adj,steps-1);
					visited.put(adj, false);
				}
				visited.put(adj, false);
			}
		}
	}
	
	public Set<BoardCell> getTargets(){
		// returns a hashSet
		return targets;
	}
	
	public LinkedList<Integer> getAdjList(int index){
		// returns the adjacency list for one cell
		return adjMap.get(index);
	}
	
	// Helper function
	public Point indexToCoord(int index) {
		// opposite of calcIndex()
		// Helper function, does the opposite of calcIndex
		int y = (int) (index / numColumns);
		int x;
		if (index < numColumns)
			x = index;
		else
			x = (index - numColumns) % numColumns;  

		return new Point(y,x);
	}
	
	// -----------------------------------------------------------------
	// GETTERS 
	public ArrayList<BoardCell> getCells() {
		return cells;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}
	// -----------------------------------------------------------------


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
