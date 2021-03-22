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
}
