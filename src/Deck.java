

import java.io.IOException;
import java.util.ArrayList;


public class Deck {
	
	private ArrayList<Card> deck = new ArrayList<Card>();
	
	
	public void shuffle(int shuffleLimit) {
		int shuffleCount = 0;
		
		if (shuffleLimit == 0) {
			return;
		}
		ArrayList<Card> shuffledDeck = null;
		ArrayList<Card> refDeck = deck;
		
		while(shuffleCount <= shuffleLimit) {
			shuffledDeck = new ArrayList<Card>();
			if(shuffleLimit == 0) {
				int i = 0;
				while(i < deck.size()) {
					shuffledDeck.add(deck.get(i));
					i++;
				}
			refDeck = shuffledDeck;
			shuffleCount++;
			}
			
			else {
				int half = deck.size() /2;
				ArrayList<Card> deck1 = new ArrayList<Card>(refDeck.subList(0, half));
				ArrayList<Card> deck2 = new ArrayList<Card>(refDeck.subList(half, deck.size()));
					int i = 0;
					while(i < half) {
						shuffledDeck.add(deck1.get(i));
						shuffledDeck.add(deck2.get(i));
						i++;
					}
			refDeck = shuffledDeck;
			shuffleCount++;
			}
		}
		this.deck = shuffledDeck;
	}
	
	public Card give() {
		
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
	public Card draw() {
		
		Card card = deck.get(deck.size() -1);
		deck.remove(deck.size() -1);
		return card;
	}
	
	public void insert(Card card) {
		deck.add(card);
	}
	
	public void insertToBottom(Card card) {
		deck.add(0, card);
	}
	
	public void printCards() {
		
		System.out.println("Deck Size " + deck.size());
		
		for(int i=0;i<deck.size(); i++) {
			Card card = deck.get(i);
			System.out.println("Card " + (i+1) + ": " + card);
		}
	}
	
	public void printCardsComma() {
		System.out.println("Deck Size " + deck.size());
		for(int i=0; i <deck.size(); i++) {
			Card card = deck.get(i);
			System.out.print(card + ",");
		}	
	}
	
	public int size() {
		return deck.size();
	}
	
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	
	public static void startRound() {
	
		System.out.println("\nPress Enter to Continue");
		try {
			System.in.read();
		} catch (IOException e) {
			
		}
	}
	
	public String toString() {
		String output = "";
		for(Card card : deck) {
			output += card.toString() + "\n"; //escape character
		}
		// D10
		// CJ
		return output;
	}
	
	
}
