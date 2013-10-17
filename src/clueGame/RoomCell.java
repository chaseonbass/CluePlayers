package clueGame;
/** Name: ebreikss
 *  Date: Oct 1, 2013
 *  Purpose:
 */

public class RoomCell extends BoardCell {
	
	public enum DoorDirection {
		
		RIGHT ("Right"),
		LEFT ("Left"),
		UP ("Up"),
		DOWN ("Down"),
		NONE ("None");
		
		private String value;
		
		DoorDirection (String aValue) {
			value = aValue;
		}
		
		public String toString() {
			return value;
		}
	}

	// enumerated type
	private DoorDirection doorDirection;
	private char roomType;
	
	public RoomCell() {
		// Default constructor for dealing with returing Walkways as RoomCells
		doorDirection = DoorDirection.NONE;
		roomType = 'W';
	}
	
	public RoomCell(String config) {
		// break up 'config' and set direction
		if (config.length() > 1){
			String dir = config.substring(1,2);
			if (dir.equals("U"))
				doorDirection = DoorDirection.UP;
			else if (dir.equals("D"))
				doorDirection = DoorDirection.DOWN;
			else if (dir.equals("R"))
				doorDirection = DoorDirection.RIGHT;
			else if (dir.equals("L"))
				doorDirection = DoorDirection.LEFT;
			else
				doorDirection = DoorDirection.NONE;
		} else {
			doorDirection = DoorDirection.NONE;
		}
		
		// Now grab first letter
		roomType = config.toCharArray()[0];
	}
	
	public RoomCell(RoomCell aRoomCell) {
		
	}
	
	public char getRoomType() {
		return roomType;
	}
	
	public boolean isRoom() {
		return true;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public boolean isDoorway() {
		if (doorDirection.equals(DoorDirection.NONE)){
			return false;
		}
		return true;
	}
	
	public String toString(){
		return "Room Initial: " + roomType + ". DoorDirection: " + doorDirection + "\n";
	}


}
