


public class Card {
	private String suit = null;
	private String rank = null;
	private boolean isFaceUp = false;
	
	public Card(String suit, String rank) {
		this.suit = suit;
		this.rank = rank;
	}
		
	public int getSuitRank() {
		String[] suitOrder = {"C", "S", "H", "D"};
		
		for(int i=0; i<suitOrder.length; i++) {
			if (suitOrder[i].equals(this.suit)) {
				return i;
			}
		}
		return -1;
	}
	
	
	public int getRank() {
		String[] rankOrder = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		
		for(int i=0; i<rankOrder.length; i++) {
			if (rankOrder[i].equals(this.rank)) {
				return i;
			}
		}
		return -1;
	}
	

	public String toString() {
		if (!this.isFaceUp) {
			return "##";
		}
		return suit + rank;
	}
	
	public void setFaceUp() {
		this.isFaceUp = true;
	}
	
	public String toString(boolean showCard) {
		if (showCard) {
			return suit+rank;
		} else
			return "##";
	}
	
	public String getColor() {
		return suit.equals("C") || suit.equals("S") ? "Black" : "Red";
	}
	
	public boolean isFaceUp() {
		return this.isFaceUp;
	}
	
	
}
