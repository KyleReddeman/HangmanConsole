import java.util.*;
import java.io.*;

public class HangmanMain {
   public static void main(String[] args) throws FileNotFoundException, InterruptedException {
      Scanner input = new Scanner(System.in);
      boolean playAgain = true;
      while(playAgain == true) {
         String word = "";
         boolean userWord = true;
         System.out.println("Welcome to hangman!");
         System.out.println("\nWhere would you like to get the word?");
         System.out.println("[1]/word bank : [2]/user");
         String wordChoice = input.nextLine();
         char choice = Character.toLowerCase(wordChoice.trim().charAt(0));
         if(choice == '2' || choice == 'u') {
	    System.out.println("What word would you like to use?");
	    word = input.nextLine();
         }
         else {
	    userWord = false;
	    List<String> wordBank = createWordBank();
	    Random random = new Random();
	    word = wordBank.get(random.nextInt(wordBank.size()));
         }
         Board b = new Board(word);
         int incorrectMax = 8;
         int incorrectCount = 0;
         System.out.println(b);
         while(!b.won() && incorrectCount < incorrectMax) {
            System.out.print("Enter Guess: ");
            String s = input.nextLine();
	    if(!b.letterGuessed(s)) {
               b.addLetter(s);
               if(b.contains(s)) {
 	          System.out.println("Good guess!");
               }
               else {
                  System.out.println("NOPE");
	          incorrectCount++;
               }
	    }
            else {
	       System.out.println("You already guessed that");
	    }
            Thread.sleep(450);
            System.out.println();
	    System.out.println("Incorrect Guesses:[" + incorrectCount + "/" + incorrectMax + "]");
	    System.out.println("Letters   Guessed: " + b.usedLetters());
            System.out.println(b);
         }
         if(incorrectCount >= incorrectMax) {
	    System.out.println("Sorry, you lose!");
	    System.out.println("The word was: " + word);
         }
         else {
    	    System.out.println("You won!");
         }
         if(userWord) {
	    List<String> wordBank = createWordBank();
            if(!wordBank.contains(word.toLowerCase())) {
	       System.out.println("\nWould you like to add this word to the word bank?[yes/no]");
	       //String answer = new Scanner(System.in).nextLine();
	       String answer = input.nextLine();
	       if(answer.trim().toLowerCase().charAt(0) == 'y') {
                  PrintStream wordBankFile = new PrintStream(new File("wordBank.txt"));
	          wordBankFile.println(word.toLowerCase()); 
	          for(int i = 0; i < wordBank.size(); i++) {
                     wordBankFile.println(wordBank.get(i));
	          } 
	       }
	    }
         }
         System.out.println("Would you like to keep playing?[yes/no]");
         String answer = input.nextLine();
         playAgain = (answer.trim().toLowerCase().charAt(0) == 'y');
      }
      System.out.println("\nThanks for playing!");
   }
   public static List<String> createWordBank() throws FileNotFoundException {
      Scanner fileInput = new Scanner(new File("wordBank.txt"));
      List<String> wordBank = new ArrayList<String>();
      while(fileInput.hasNextLine()) {
         wordBank.add(fileInput.nextLine().trim());
      }
      return wordBank;
   }
}
