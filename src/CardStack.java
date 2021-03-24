import java.util.ArrayList;

public class CardStack {
	ArrayList<Card> cards = new ArrayList<Card>();
	
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
	
	public boolean canInsertCard(Card card) {
		if (isEmpty()) {
			return card.getSuitRank() == 13;
		}
		
		Card lastCard = this.lastCard();
		return !card.getColor().equals(lastCard.getColor());
	}
}
