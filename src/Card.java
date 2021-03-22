


public class Card {
	public String suit = null;
	public String rank = null;
	
	
	public int getSuitRank() {
		String[] suitOrder = {"C", "S", "H", "D"};
		
		for(int i=0; i<suitOrder.length; i++) {
			if (suitOrder[i].equals(this.rank)) {
				return i;
			}
		}
		return -1;
	}
	
	
	public int getRank() {
		String[] rankOrder = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
		
		for(int i=0; i<rankOrder.length; i++) {
			if (rankOrder[i].equals(this.rank)) {
				return i;
			}
		}
		return -1;
	}
	

	public String toString() {
		return suit + "-" + rank;
	}
	

	
	
	
	
}
