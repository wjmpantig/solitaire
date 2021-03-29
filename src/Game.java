import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;



public class Game {
	private static final int MANEUVRE_SIZE = 7;
	private static final int FOUNDATION_SIZE = 4;
	
	private CardStack[] maneuver;
	private CardStack talon;
	private CardStack wasteTalon;
	private CardStack[] foundation;
	
	private void init() {
		maneuver = new CardStack[MANEUVRE_SIZE];
		talon = new CardStack();
		wasteTalon = new CardStack();
		foundation = new CardStack[FOUNDATION_SIZE];
		
		//init manouvere
		for(int i =0; i < MANEUVRE_SIZE; i++ ) {
			maneuver[i] = new CardStack();
		}
		//init foundation
		for(int i = 0; i < FOUNDATION_SIZE; i++) {
			foundation[i] = new CardStack(false);
		}
	}
	
	private Deck readDeckFromFile() {
		Scanner scan;
		Deck deck = new Deck();
		try {
			File file = new File("Card_Sequence.txt");
			scan = new Scanner(file);
			String deckSequence = scan.nextLine();
			StringTokenizer firstSeperator = new StringTokenizer(deckSequence, ",");
				while(firstSeperator.hasMoreTokens()) {
					String token = firstSeperator.nextToken();
					
					String[] finalSeperation = token.split("-"); 
					Card card = new Card(finalSeperation[0], finalSeperation[1]);
					deck.insert(card);
					scan.close();
				}
		} catch (FileNotFoundException e) {
			System.out.println("File not Found Please Check File Location");
			System.exit( -1 );
		}
		return deck;
	}
	
	private int inputShuffleCount() {
		int shuffleLimit = 5;
//		int shuffleLimit = 0;
//		boolean forShuffle = true;
//		Scanner scanner = new Scanner(System.in);
//		while(forShuffle) {
//			try {
//				System.out.print("Input Number of Shuffle: ");
//				shuffleLimit = Integer.parseInt(scanner.nextLine());
//				if(shuffleLimit < 0) {
//					System.out.println("Enter Only Positive Numbers\n");
//					forShuffle = true;
//				}
//				else {
//					forShuffle = false;
//				}
//			} catch(Exception e) {
//				System.out.println("Invalid Input Enter Only Numbers!\n");
//			}
//		}
//		scanner.close();
		return shuffleLimit;
	}
	
	private void distributeDeckToManeuvre(Deck deck) {
		for(int i =0; i < MANEUVRE_SIZE; i++ ) {
			CardStack stack = maneuver[i];
			for(int j = 0; j < i + 1; j++) {
				Card card = deck.draw();
				stack.add(card);
			}
		}
	}
	
	private void addDeckToTalon(Deck deck) {
		talon.addAll(deck.drawAllCards());
	}

	
	private void displayTable() {
		System.out.println("Talon: " + talon.size());
		System.out.print("Waste Talon: ");
		for(int i = wasteTalon.size() - 3; i < wasteTalon.size(); i++) {
			if (i < 0) {
				continue;
			}
			System.out.print(wasteTalon.get(i) + ",");
		}
		System.out.println();
		System.out.println("Foundations: ");
		for(int i = 0; i < FOUNDATION_SIZE; i++) {
			CardStack stack = foundation[i];
			System.out.println("Foundation " + (i+1) + " " + (stack.isEmpty() ? "Empty" : stack.getLastCard().toString(true)));
		}
		//print manuevrer
		boolean hasCardsToShow = false;
		int highestStackSize = 0;
		for(int i =0; i < MANEUVRE_SIZE; i++ ) {
			CardStack stack = maneuver[i];
			if (highestStackSize < stack.size()) {
				highestStackSize = stack.size();
			}
		}
		int row = 0;
		do {
			String output = "";
			for(int i =0; i < MANEUVRE_SIZE; i++ ) {
				CardStack stack = maneuver[i];
				hasCardsToShow = row < stack.size();
				if (hasCardsToShow) {
					if (row + 1 == stack.size()) {
						stack.get(row).setFaceUp();
					}
					output += stack.get(row) + "\t";
				} else {
					output += "    \t";
				}
			}
			System.out.println(output);
			row++;
		}while(row < highestStackSize);
	}
	
	private boolean moveManeuverCardToManeuver() {
		boolean hasCardMoved = false;
		for(int i = MANEUVRE_SIZE - 1; i >= 0 && !hasCardMoved; i--) {
			CardStack stack  = maneuver[i];
			int j = stack.getHighestVisibleCardIndex();
			if (j < 0) {
				continue;
			}
			Card card = stack.get(j);
			for (int k = MANEUVRE_SIZE - 1; k >= 0 && !hasCardMoved; k--) {
				CardStack target = maneuver[k];
				if (i == k) {
					continue;
				}
				hasCardMoved = target.insertStack(stack, j);
				if (hasCardMoved) {
					System.out.println("moved card " + card + " to column " + (k+1));
				}
			}
			
		}
		if (!hasCardMoved) {
			System.out.println("no card moved in maneuver");
		}
		return hasCardMoved;
	}
	
	private boolean moveMaenuverCardToFoundation() {
		boolean hasCardMoved = false;
		for(int i = MANEUVRE_SIZE - 1; i >= 0 && !hasCardMoved; i--) {
			CardStack stack  = maneuver[i];
			Card card = stack.getLastCard();
			for (int j = FOUNDATION_SIZE - 1; j >= 0 && !hasCardMoved; j--) {
				CardStack target = foundation[j];
				hasCardMoved = target.insert(card);
				if (hasCardMoved) {
					stack.removeLastCard();
					System.out.println("moved card " + card + " to foundation column " + (j+1));
				}
			}
		}
		if (!hasCardMoved) {
			System.out.println("no card moved in maneuver to foundation");
		}
		return hasCardMoved;
	}
	
	public boolean talonWasteHasCards() {
		return !wasteTalon.isEmpty();
	}
	
	public void drawFromTalon() {
		Card[] cards = null;
		if (talon.size() > 3) {
			cards = talon.drawCards(3);
		} else {
			cards = talon.drawCards(talon.size());
		}
		for(Card c : cards) {
			c.setFaceUp();
			wasteTalon.add(c);
		}
		System.out.println("drew " + cards.length +" cards from talon");
	}
	
	public boolean talonHasCards() {
		return !talon.isEmpty();
	}
	
	public boolean moveTalonWasteToManeuver() {
		boolean hasCardMoved = false;
		Card card = wasteTalon.getLastCard();
		for (int j = MANEUVRE_SIZE - 1; j >= 0 && !hasCardMoved; j--) {
			CardStack target = maneuver[j];
			hasCardMoved = target.insert(card);
			if (hasCardMoved) {
				wasteTalon.removeLastCard();
				System.out.println("moved card " + card + " to maneuver column " + (j+1));
			}
		}
		if (!hasCardMoved) {
			System.out.println("no card moved in talon waste to manuever");
		}
		return hasCardMoved;
	}
	
	public boolean moveTalonWasteToFoundation() {
		boolean hasCardMoved = false;
		Card card = wasteTalon.getLastCard();
		for (int j = FOUNDATION_SIZE - 1; j >= 0 && !hasCardMoved; j--) {
			CardStack target = foundation[j];
			hasCardMoved = target.insert(card);
			if (hasCardMoved) {
				wasteTalon.removeLastCard();
				System.out.println("moved card " + card + " to foundation column " + (j+1));
			}
		}
		if (!hasCardMoved) {
			System.out.println("no card moved in talon waste to foundation");
		}
		return hasCardMoved;
	}
	
	public void moveWasteBack() {
		wasteTalon.moveAll(talon); 
		System.out.println("moved all waste talon back to talon");
	}
	
	public boolean isSolitaire() {
		
		for(int i = 0; i < FOUNDATION_SIZE; i++) {
			if (foundation[i].size() != 13) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		int gameCtr=0;
		int wins = 0;
		int totalGames = 1000;
		do {
			System.out.println("game " + (gameCtr+1) + "-------------");
			Game game = new Game();
			game.init();
			Deck deck = game.readDeckFromFile();
//		System.out.println(deck);
//		int shuffleLimit = game.inputShuffleCount();
//		deck.shuffle(shuffleLimit);
			deck.shuffleRandom();
//		System.out.println(deck);
			game.distributeDeckToManeuvre(deck);
			game.addDeckToTalon(deck);
			boolean hasCardMoved = false;
			int drawCtr = 0;
			while(!game.isSolitaire()) { 
				game.displayTable();
//			inputEnter();
				
				hasCardMoved = game.moveManeuverCardToManeuver();
				if (hasCardMoved) {
					drawCtr = 0;
					continue;
				}
				hasCardMoved = game.moveMaenuverCardToFoundation();
				if (hasCardMoved) {
					drawCtr = 0;
					continue;
				}
				if (game.talonWasteHasCards()) {
					hasCardMoved = game.moveTalonWasteToManeuver();
				}
				if (hasCardMoved) {
					drawCtr = 0;
					continue;
				}
				
				if (game.talonWasteHasCards()) {
					hasCardMoved = game.moveTalonWasteToFoundation();
				}
				if (hasCardMoved) {
					drawCtr = 0;
					continue;
				}
				
				if (!game.talonWasteHasCards() && game.talonHasCards()) {
					game.drawFromTalon();
				} else if (!hasCardMoved && game.talonHasCards()){
					game.drawFromTalon();
				} else if (!game.talonHasCards() && game.talonWasteHasCards()) {
					game.moveWasteBack();
					drawCtr++;
				} else {
					drawCtr++;
				}
				hasCardMoved = false;
				System.out.println("Draw counter: " + drawCtr);
				if (drawCtr == 10) {
					break;
				}
			}
			
			if (game.isSolitaire()) {
				game.displayTable();
				System.out.println("Congratulations!!");
				wins++;
			} else {
				System.out.println("Game over, you lose");
			}
			gameCtr++;
		}while(gameCtr < totalGames);
		float percentWin = wins / (float) totalGames;
		System.out.printf("Win rate: %.2f", (percentWin * 100f) );
		
	}
	
	private static void inputEnter() {
		System.out.println("\nPress Enter to Continue");
		try {
			System.in.read(new byte[3]);
		} catch (IOException e) {
			
		}
	}

}
