package hangman; 

/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 * From the Stanford SEE course
 */

//import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.util.*;

//import java.awt.*;
import java.util.Scanner;

public class Hangman extends ConsoleProgram {    	

    /**Chooses a random word to use as the secret word
     * 
     * @return: a word from Hangman Lexicon
     */
    
	public void init() {
		 canvas = new HangmanCanvas();
		 wordsource = new HangmanLexicon("HangmanLexicon.txt");
		 add(canvas);
		}

	
	private String chooseRandomWord() {
    	println("choosing random word");
    	println("wordsource.getWordCount() is" + wordsource.getWordCount());
		int i = reg.nextInt(0,wordsource.getWordCount());
		println(i);
    	return wordsource.getWord(i);
    }	   
    
	
    /** gives a string of (n = length of secret word) dashes*/
    private String initGuessWord(){
        String word = "";
    	for (int i = 0; i < chosenWord.length(); i++) {
        	word += "-";
        }return word;	
    }

    
    public String getChosenWord(){
    	return chosenWord;	
    }
    
    
    /**replaces one letter in "dash" line*/
    void guessWordInsertLetter(int index) {
    	guessWord = guessWord.substring(0, index)
    				+ letterGuess
    				+ guessWord.substring(index+1, guessWord.length());
    }
    
    
    /**replaces dashes with correctly chosen letter
     * reduces number of guesses by one if letter incorrect or
     * correctly chosen for the first time
     */
    void guessWordReplaceDashes() {
    	int letterReplacements = 0;
    	int letterMatches = 0; 
    	for (int i=0; i < chosenWord.length(); i++) {
    		//println(chosenWord.substring(i, i+1));
    		if (letterGuess.equals(chosenWord.substring(i,i+1))) {
    			letterMatches += 1;
    			if ("-".equals(guessWord.substring(i,i+1))){
    				guessWordInsertLetter(i);
    				letterReplacements += 1;
    			}
    		}	
    		
    	}if (letterMatches == 0) {
    		canvas.noteIncorrectGuess(letterGuess.charAt(0));
    		println("There are no "+letterGuess+"'s in the word.");
    		numGuesses -= 1;
    		
    	}else if (letterReplacements > 0){
    		println("That guess is correct.");
    	}
    }
    
    
    public String getGuessWord(){
    	return guessWord;	
    }
    
    public int getNumGuesses(){
    	return numGuesses;	
    }
    
    
    /**acquires user guess and makes sure it's ok*/
    public void setLetterGuess(){
    	letterGuess = readLine("Your guess: ");
    	
    	while (letterGuess.length() != 1 ||!Character.isLetter(letterGuess.charAt(0))) {
    		println("That's not a letter");
    		letterGuess = readLine("Your guess: ");
    	}letterGuess = letterGuess.toUpperCase();
    }
    
    
    public String getLetterGuess(){
    	return letterGuess;	
    }
    
    
    Boolean guessWordCheck() {
        return guessWord.equals(chosenWord);
    }
    
    
    public void run() {
    	//arrTest = new ArrayList<String>();
    	//arrTest.add("hello test");
    	println(HangmanLexicon.getArrLexicon());
    	//println(arrTest);
    	canvas.reset();
    	chosenWord = chooseRandomWord();
    	println(chosenWord);
    	guessWord = initGuessWord();
    	while (numGuesses > 0) {
    		println("The word now looks like this: "+guessWord
    				+"\nYou have "+numGuesses+" guesses left.");
    		canvas.displayWord(guessWord);
    		setLetterGuess(); 
    		guessWordReplaceDashes();
    		if (guessWordCheck()) {
    			canvas.displayWord(guessWord);
    			println("You guessed the word: "+guessWord+ "\nYou win.");
    			break;
    		}
    	}
    	if (!guessWordCheck()) {
    		println("You are completely hung.");
    		println("The word was "+ chosenWord);	
    	} 
    }
    /*private instance variables*/
	private RandomGenerator reg = RandomGenerator.getInstance();
	private String chosenWord; 
	private String guessWord;
	private int numGuesses = 8;
	private String letterGuess;
	private HangmanCanvas canvas;
	private HangmanLexicon wordsource;
	private ArrayList<String> arrTest;
}