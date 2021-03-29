import java.util.ArrayList;
import java.util.List;

public class CardStack {
	private ArrayList<Card> cards = new ArrayList<Card>();
	boolean descendingMode = true;
	public CardStack() {
		
	}
	
	public CardStack(boolean descMode) {
		this.descendingMode = descMode;
	}
	
	public CardStack(boolean descMode, ArrayList<Card> cards) {
		this.descendingMode = descMode;
		this.cards = cards;
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
	
	public Card getLastCard() {
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
	
	public int getHighestVisibleCardIndex() {
		for(int i = 0; i < cards.size(); i++) {
			Card card = cards.get(i);
			if (card.isFaceUp()) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean canInsertCard(Card card) {
		if (card == null) {
			return false;
		}
		if(!descendingMode) {
			if (isEmpty()) {
				return card.getRank() == 0; // can insert king
			}
			Card lastCard = this.getLastCard();
			return card.getSuitRank() == lastCard.getSuitRank() && (lastCard.getRank() + 1 == card.getRank());
		}
		if (isEmpty()) {
			return card.getRank() == 12; // can insert king
		}
		
		Card lastCard = this.getLastCard();
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
	
	public Card[] drawCards(int count) {

		Card[] arrayCards = new Card[count];
		for(int i = 0; i < count; i++) {
			arrayCards[i] = cards.get(cards.size() - i - 1);
		}
		for(int i = 0 ; i < count; i++) {
			cards.remove(cards.size()-1);
		}
		return arrayCards;
	}
	
	public void moveAll(CardStack stack) {
		stack.addAll(this.cards);
		this.cards.clear();
	}
	
	public boolean insertStack(CardStack stack, int index) {
		if (stack.isEmpty()) {
			return false;
		}
		Card firstCard = stack.get(index);
		if (this.canInsertCard(firstCard)) {
			if(index == 0 && this.cards.isEmpty() && firstCard.getRank() == 12) {
				return false;
			}
			for (int i = index; i < stack.size(); i++) {
				this.cards.add(stack.get(i));
			}
//			for (int i = index; i < stack.size(); i++) {
//				stack.cards.remove(i);
//			}
			if(stack.size() - index > 1) {
				stack.removeFromIndex(index);				
			} else {
				stack.removeLastCard();
			}
			
			return true;
		}
		return false;
	}
	
	public void removeFromIndex(int i) {
		List<Card> newCards = this.cards.subList(0, i);
		this.cards = new ArrayList<Card>(newCards);
	}
}
