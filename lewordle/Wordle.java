package lewordle;

import java.io.*;
import java.util.*;

public class Wordle {
    // ANSI color codes
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m"; 
    private static final String ANSI_RED = "\u001B[31m"; 

    public static void main(String[] args) {
        List<Word> words = loadWordsFromFile("Wordle/Words.txt");

        if (words.isEmpty()) {
            System.out.println("No words available");
            return;
        }

        Random random = new Random();
        Word gameWord = words.get(random.nextInt(words.size()));
        //System.out.println(gameWord); //debug *REMOVE LATER* idekd

        Scanner scanner = new Scanner(System.in);
        String guessedLetters = "";
        int attempts = 0;
        boolean guessedCorrectly = false;

        System.out.println("Welcome to Wordle! Try to guess the 5-letter word.");
        long startTime = System.nanoTime();

        while (attempts < 6 && !guessedCorrectly) {
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine().toLowerCase();
            
            if (guess.length() != 5) {
                System.out.println("Please enter a 5-letter word.");
                continue;
            }

            attempts++;
            guessedLetters += guess + " ";

            StringBuilder result = new StringBuilder();
            StringBuilder marks = new StringBuilder();

            for (int i = 0; i < 5; i++) {
                if (guess.charAt(i) == gameWord.getWord()[i]) {
                    result.append(ANSI_BOLD).append(ANSI_GREEN).append(guess.charAt(i)).append(ANSI_RESET);
                    marks.append('G');
                } else if (gameWord.checkLetter(guess.charAt(i))) {
                    result.append(ANSI_BOLD).append(ANSI_YELLOW).append(guess.charAt(i)).append(ANSI_RESET);
                    marks.append('Y');
                } else {
                    result.append(ANSI_BOLD).append(ANSI_RED).append(guess.charAt(i)).append(ANSI_RESET);
                    marks.append('*');
                }
            }

            System.out.println("Guessed Words: " + guessedLetters);
            System.out.println("Your Guess: " + result.toString());

            if (marks.toString().equals("GGGGG")) {
                guessedCorrectly = true;
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000000;
                System.out.println("WOOHOO! You've guessed the word: " + gameWord);
                System.out.println("Attempts: " + attempts);
                System.out.println("Time taken: " + duration + "s");
            }
        }

        if (!guessedCorrectly) {
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000000;
            System.out.println("Game over! The word was: " + gameWord);
            System.out.println("Attempts: 6 MAX REACHED");
            System.out.println("Time taken: " + duration + "s");
        }
    }

    private static List<Word> loadWordsFromFile(String filename) {
        List<Word> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 5) {
                    words.add(new Word(line.trim()));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return words;
    }
}
