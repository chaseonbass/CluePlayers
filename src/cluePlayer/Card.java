package cluePlayer;

public class Card {
	public enum CardType {
		ROOM ("Room"),
		WEAPON ("Weapon"),
		PERSON ("Person");
		
		private String value;

		CardType (String aValue) {
			value = aValue;
		}
		
		public String toString() {
			return value;
		}
	}
	
	public boolean equals(Object otherCard){
		boolean result = false;
		if(otherCard instanceof Card){
			Card thisCard = (Card) otherCard;
			result = (this.getName()== thisCard.getName() && this.getCartype()== thisCard.getCartype());
		}
		return result;
			
	}
	
	
		private String name;
		private CardType cardtype;
		
		Card(String name, CardType cardtype) {
			super();
			this.name = name;
			this.cardtype = cardtype;
		}

		public Card(){
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public CardType getCartype() {
			return cardtype;
		}

		public void setCartype(CardType cartype) {
			this.cardtype = cardtype;
		}
	

	
	
}
