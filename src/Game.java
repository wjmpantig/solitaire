import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;




public class Game {
	private static final int MANEUVRE_SIZE = 7;
	private static final int FOUNDATION_SIZE = 4;
	
	private CardStack[] maneuver;
	private CardStack talon;
	private CardStack wasteTalon;
	private CardStack[] foundation;
	private CardStack drawCards;
	private boolean isGameOver = false;
	
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
		System.out.println("Foundations: ");
		for(int i = 0; i < FOUNDATION_SIZE; i++) {
			CardStack stack = foundation[i];
			System.out.println("Foundation " + (i+1) + " " + (stack.isEmpty() ? "Empty" : stack.lastCard().toString(true)));
		}
		//print manuevrer
		boolean hasCardsToShow = false;
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
		}while(hasCardsToShow);
	}
	
	private void moveManeuverCardToManeuver() {
		boolean hasCardMoved = false;
		for(int i = MANEUVRE_SIZE - 1; i >= 0 && !hasCardMoved; i--) {
			CardStack stack  = maneuver[i];
			Card card = stack.getHighestVisibleCard();
			for (int j = MANEUVRE_SIZE - 1; j >= 0 && !hasCardMoved; j--) {
				CardStack target = maneuver[j];
				if (i == j) {
					continue;
				}
				hasCardMoved = target.insert(card);
				if (hasCardMoved) {
					stack.removeLastCard();
					System.out.println("moved card " + card + " to column " + (j+1));
				}
			}
			
		}
		if (!hasCardMoved) {
			System.out.println("no card moved in maneuver");
		}
	}
	
	private void moveMaenuverCardToFoundation() {
		boolean hasCardMoved = false;
		for(int i = MANEUVRE_SIZE - 1; i >= 0 && !hasCardMoved; i--) {
			CardStack stack  = maneuver[i];
			Card card = stack.getHighestVisibleCard();
			for (int j = FOUNDATION_SIZE - 1; j >= 0 && !hasCardMoved; j--) {
				CardStack target = foundation[j];
				if (i == j) {
					continue;
				}
				
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
	}
	
	public static void main(String[] args) {
		
		Game game = new Game();
		game.init();
		Deck deck = game.readDeckFromFile();
//		System.out.println(deck);
		int shuffleLimit = game.inputShuffleCount();
		deck.shuffle(shuffleLimit);
//		System.out.println(deck);
		game.distributeDeckToManeuvre(deck);
		game.addDeckToTalon(deck);
		
		while(!game.isGameOver) { 
			game.displayTable();
			inputEnter();
			game.moveManeuverCardToManeuver();
			game.moveMaenuverCardToFoundation();
		}

		
	}
	
	private static void inputEnter() {
		System.out.println("\nPress Enter to Continue");
		try {
			System.in.read(new byte[3]);
		} catch (IOException e) {
			
		}
	}

}
