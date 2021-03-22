import java.io.File;
import java.io.FileNotFoundException;
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
					Card card = new Card();
					String[] finalSeperation = token.split("-"); 
					card.suit = finalSeperation[0];
					card.rank = finalSeperation[1];
					deck.insert(card);
					scan.close();
				}
		} catch (FileNotFoundException e) {
			System.out.println("File not Found Please Check File Location");
			System.exit( -1 );
		}
		//System.out.println(deck);
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
		CardStack[] foundation = new CardStack[FOUNDATION_SIZE];
		
		for(int i =0; i < MANEUVRE_SIZE; i++ ) {
			CardStack stack = new CardStack();
			for(int j = 0; j < i + 1; j++) {
				Card card = deck.draw();
				stack.add(card);
			}
			manouvere[i] = stack;
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
					output += stack.get(row) + "\t";
				} else {
					output += "    \t";
				}
				
			}
			System.out.println(output);
			row++;
		}while(hasCardsToShow);
	}

}
