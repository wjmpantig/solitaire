import java.util.ArrayList;

public class CardStack {
	ArrayList<Card> cards = new ArrayList<Card>();
	boolean descendingMode = true;
	public CardStack() {
		
	}
	
	public CardStack(boolean descMode) {
		this.descendingMode = descMode;
	}
	
	
	public void add(Card c) {
		cards.add(c);
	}
	
	public Card get(int i) {
		return cards.get(i);
	}
	
	public int size() {
		return cards.size();
	}
	
	public void addAll(ArrayList<Card> cards) {
		this.cards.addAll(cards);
	}
	
	public String toString() {
		String output = "";
		for(int i=0; i <cards.size(); i++) {
			Card card = cards.get(i);
			output += card.toString(true)+",";
		}	
		return output;
	}
	
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	
	public Card lastCard() {
		if (isEmpty()) {
			return null;
		}
		return cards.get(cards.size() - 1);
	}
	
	public Card getHighestVisibleCard() {
		Card highestVisibleCard = null;
		for(int i = cards.size() - 1; i >= 0; i--) {
			Card card = cards.get(i);
			if (card.isFaceUp()) {
				highestVisibleCard = card;
			} else {
				break;
			}
		}
		return highestVisibleCard;
	}
	
	public boolean canInsertCard(Card card) {
		if(!descendingMode) {
			if (isEmpty()) {
				return card.getRank() == 0; // can insert king
			}
			Card lastCard = this.lastCard();
			return card.getSuitRank() == lastCard.getSuitRank() && (lastCard.getRank() + 1 == card.getRank());
		}
		if (isEmpty()) {
			return card.getSuitRank() == 12; // can insert king
		}
		
		Card lastCard = this.lastCard();
		return !card.getColor().equals(lastCard.getColor()) && (lastCard.getRank() - 1 == card.getRank());
	}
	
	public boolean insert(Card c) {
		if (canInsertCard(c)) {
			cards.add(c);
			return true;
		}
		return false;
	}
	
	public void removeLastCard() {
		cards.remove(cards.size() - 1);
	}
}
