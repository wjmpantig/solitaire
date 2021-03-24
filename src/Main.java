import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;




public class Main {
	private static final int MANEUVRE_SIZE = 7;
	private static final int FOUNDATION_SIZE = 4;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		System.out.println(deck);
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
		deck.shuffle(shuffleLimit);
//		System.out.println(deck);
		CardStack[] manouvere = new CardStack[MANEUVRE_SIZE];
		CardStack talon = new CardStack();
		CardStack wasteTalon = new CardStack();
		CardStack[] foundation = new CardStack[FOUNDATION_SIZE];
		CardStack drawCards = new CardStack();
				
		for(int i =0; i < MANEUVRE_SIZE; i++ ) {
			CardStack stack = new CardStack();
			for(int j = 0; j < i + 1; j++) {
				Card card = deck.draw();
				stack.add(card);
			}
			manouvere[i] = stack;
		}
		talon.addAll(deck.drawAllCards());
		scan = new Scanner(System.in);
		boolean gameOver = false;
		do {
		
			System.out.println("Talon: " + talon.size());
			System.out.println("Foundations: ");
			for(int i = 0; i < FOUNDATION_SIZE; i++) {
				CardStack stack = foundation[i];
				if (stack == null) {
					stack = foundation[i] = new CardStack();
				}
				System.out.println("Foundation " + (i+1) + (stack.isEmpty() ? " Empty " : stack.lastCard().toString(true)));
			}
			//print manuevrer
			boolean hasCardsToShow = false;
			int row = 0;
			do {
				String output = "";
				for(int i =0; i < MANEUVRE_SIZE; i++ ) {
					CardStack stack = manouvere[i];
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
			
			int move = 0;
			System.out.println("Select move: ");
			System.out.println("1: Move card from Maneuvre");
			System.out.println("2: Draw 3 from talon");
			System.out.println("3: Move last card from talon");
			move = Integer.parseInt(scan.next());
			String cardStr = null;
			
			if(move == 1) {
				System.out.println("Input card:");
				cardStr = scan.next().toUpperCase();
				String suit = cardStr.charAt(0) + "";
				String rank = cardStr.substring(1);
				int cardCol = -1;
				int cardRow = -1;
				Card card = new Card(suit, rank);
				for(int i =0; i < MANEUVRE_SIZE; i++ ) {
					CardStack stack = manouvere[i];
					for (int j = stack.size() - 1; j > -1; j--) {
						Card c = stack.get(j);
						if (c.isFaceUp() && c.getRank() == card.getRank() && c.getSuitRank() == card.getSuitRank()) {
							cardCol = j;
							cardRow = i;
						}
					}
				}
				if (cardCol == -1 || cardRow == -1) {
					System.out.print("card not found");
					//go back to loop
				} else {
					System.out.println("where do you want to move the card");
					System.out.println("1 Foundation");
					System.out.println("2 Maneuvre");
					int cardMove = Integer.parseInt(scan.next());
					if (cardMove == 1) {
						
					}					
				}
			}
			System.out.println("\nPress Enter to Continue");
			try {
				System.in.read(new byte[3]);
			} catch (IOException e) {
				
			}
			
		} while (!gameOver);
	}

}
