/*
    This is a guessing game played in the console for 1 player.
    The range of numbers always starts at 1 and ends with the given "max" number
    Players can play multiple games per session and when exiting, will be shown stats
 */

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class Guess
{
    public static void main(String[] args)
    {
        //initializing main's variables
        int max = 100;
        int totalGuesses = 0;
        int totalGames = 0;
        int bestGuess = 0;
        Scanner console = new Scanner(System.in);
        boolean playing = true;

        //play the intro
        intro(max);

        //repeats playing as long as the player wants to
        while(playing)
        {
            int numG = play(console, max);
            totalGuesses = totalGuesses + numG;
            totalGames++;
            if(bestGuess == 0)
            {
                bestGuess = numG;
            } else if(numG < bestGuess)
            {
                bestGuess = numG;
            }
            playing = playAgain(console);
        }

        //show the stats and done with the program
        results(totalGuesses, totalGames, bestGuess);
    }
    /*
        intro serves the purpose of introducing the player to the game
        it receives the maximum range and uses that in its text
     */
    public static void intro(int max)
    {
            System.out.println("This program allows you to play a guessing game.");
            System.out.println("I will think of a number between 1 and");
            System.out.println(max + " and will allow you to guess until");
            System.out.println("you get it. For each guess, I will tell you");
            System.out.println("whether the right answer is higher or lower");
            System.out.println("than your guess.\n");
    }
    /*
        play contains the main game loop responsible for all gameplay
        it returns the number of guesses taken to be used in the stats screen
        it also displays the victory message
     */
    public static int play(Scanner console, int max)
    {
        //generating a random number
        int answer = ThreadLocalRandom.current().nextInt(1, max + 1);

        System.out.println("I'm thinking of a number between 1 and " + max + "...");
        //we start the number of guesses at 1 because you can't guess 0 times
        int numGuesses = 1;

        /*
            while the player is guessing we repeatedly ask what the guess is
            and give a small hint in the direction they should go and increment the number of guesses
            once the correct guess is given we break the loop
         */
        boolean guessing = true;
        while(guessing)
        {
            int guess = 0;
            boolean typing = true;
            while(typing) {
                System.out.print("Your guess? ");
                try
                {
                    guess = console.nextInt();
                    if(1 <= guess && guess <= max)
                    {
                        typing = false;
                    } else
                    {
                        System.out.println("Please type a number between 1 and " + max);
                    }
                } catch (java.util.InputMismatchException e)
                {
                    console.nextLine();
                    System.out.println("Please type a number between 1 and " + max);
                }
            }
            console.nextLine();


            if (guess == answer)
            {
                guessing = false;
            }else if (guess < answer)
            {
                System.out.println("It's higher.");
                numGuesses++;
            } else if (guess > answer)
            {
                System.out.println("It's lower.");
                numGuesses++;
            }
        }

        //checking for plurality
        if(numGuesses == 1)
        {
            System.out.println("You got it right in 1 guess");
        } else {
            System.out.println("You got it right in " + numGuesses + " guesses");
        }
        return numGuesses;
    }
    /*
        results prints the stats at the end of the play session
        I cast totalGuesses as a double to avoid excessive formatting on other lines
     */
    public static void results(int totalGuesses, int totalGames, int bestGuess)
    {
        double avg = ((double)totalGuesses) / totalGames;
        System.out.println("Overall results:");
        System.out.println("    total games = " + totalGames);
        System.out.println("    total guesses = " + totalGuesses);
        System.out.printf("    guess/game = %.1f%n", avg);
        System.out.println("    best game = " + bestGuess);
    }
    /*
        playAgain is used to return a boolean if the player would like to play another game
     */
    public static boolean playAgain(Scanner console)
    {
        while(true)
        {
            System.out.print("Do you want to play again? ");
            String resp = console.nextLine();
            System.out.println();
            //given the specs of the assignment we do not need any other checks on the response
            switch (resp.toLowerCase().charAt(0))
            {
                case 'y':
                    return true;
                case 'n':
                    return false;
                default:
                    System.out.println("Invalid response given, please try again.");
            }
        }
    }
}