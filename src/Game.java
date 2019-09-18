import java.lang.reflect.AccessibleObject;
import java.util.Scanner;

public class Game {
    ScoreSheet[] ScoreSheets;
    int nbRound;
    DiceBundle theDiceBundle = new DiceBundle();

    public Game(int nbPlayer) {  //Creation de la partie
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        ScoreSheets = new ScoreSheet[nbPlayer];
        String name; //Pour stocker le no; de chacun des joueurs
        nbRound = 0; //initialise le numero du round a zero
        for (int i = 1; i <= nbPlayer; i++) {  //je cree un scoresheet par nb de joueur
            System.out.println("The name of player " + i + " : ");// Whrite he's name
            name = myObj.nextLine();
            ScoreSheets[i - 1] = new ScoreSheet(name); //score sheet (player 1 at place [0])
        }
        theDiceBundle.resetTheDices();
    }

    public void start() {
        while (nbRound < 13) {
            nextRound();
        }
        for (int player = 0; player < ScoreSheets.length; player++) {
            int total = ScoreSheets[player].cases[7] + ScoreSheets[player].cases[8] + ScoreSheets[player].cases[16];
            System.out.printf("Score du joueur : " + ScoreSheets[player].getPlayerName() + " is " + total); //Print the number of the round
        }
    }

    public void nextRound() {
        nbRound++;
        System.out.printf("---------- ROUND NUMBER %2d ----------\n", nbRound); //Print the number of the round
        for (int i = 0; i < ScoreSheets.length; i++) {
            System.out.printf("---------- Tour de : %5s ----------\n", ScoreSheets[i].getPlayerName());
            int nbLance = 0;
            String answer = "default";
            Scanner input = new Scanner(System.in);
            while (nbLance < 3) { //3 throws (or less if the user choose 'stop'
                answer = "default";
                nbLance++;
                theDiceBundle.Throw();
                while (!answer.equals("stop") && !answer.equals("y")) {
                    ScoreSheets[i].View();
                    theDiceBundle.printTheDices();
                    System.out.printf("Throw n°%d, dice(s) to keep/reroll ? (ex : '2,4,5' or '' to stay the same or even 'stop' to stop here) : \n", nbLance);
                    answer = input.nextLine();
                    while (!isDiceSensitive(answer)) {
                        ScoreSheets[i].View();
                        theDiceBundle.printTheDices();
                        System.out.printf("you gotta say the dices you want to keep/reroll like that : '2,4,5' or even nothing to reroll the same way it is right now or 'stop' to stop \n");
                        answer = input.nextLine();
                    }
                    if (!answer.equals("stop")) { //false if the input doesn't mean anything
                        System.out.printf("Erreur de saisie, veuillez recommencer\n");
                        theDiceBundle.switchDices(answer);
                        ScoreSheets[i].View(); //Print the score Sheet
                        theDiceBundle.printTheDices(); //Print the dices
                        System.out.printf("Confirm to roll the dices [y/n] : \n");
                        answer = input.nextLine();
                        while (!isYorN(answer)) {
                            ScoreSheets[i].View();
                            theDiceBundle.printTheDices();
                            System.out.printf("Please write 'y' for yes or 'n' for no ... : \n");
                            answer = input.nextLine();

                        }
                    } else { //if the user wrote 'stop' I force the nb to 3 to stop the game (a little ugly but it works)
                        nbLance = 3;
                    }
                }

            }
            ScoreSheets[i].View(); //And of the round, the user choose where to put hes dices
            theDiceBundle.resetTheDicesKeep();
            theDiceBundle.order();
            theDiceBundle.printTheDices();
            System.out.printf("Which case would you like to put those dices ? (ex : '7' for Three of a Kind) :\n");
            answer = input.nextLine();

            while (!isACorrectCaseNumber(answer)) {
                ScoreSheets[i].View();
                theDiceBundle.printTheDices();
                System.out.printf("The cases are numbers from 1 to 6 and from 7 to 13, look above for their significations :\n");
                answer = input.nextLine();
            }

            //Check if the case is a number (however it can be out of range... maybe fix later, idk)
            int answerInt = Integer.parseInt(answer);
            //if(answerInt < 14 && answerInt > 6)
            //    answerInt += 2;
            while (!ScoreSheets[i].putScoreAtPlace(theDiceBundle, answerInt)) { //check if they're already smthg in that case
                ScoreSheets[i].View();
                theDiceBundle.printTheDices();
                System.out.printf("This case is already completed\n");
                answer = input.nextLine();
                while (!isACorrectCaseNumber(answer)) {
                    ScoreSheets[i].View();
                    theDiceBundle.printTheDices();
                    System.out.printf("We can do it the easy way or we can do it the hard way ... :\n");
                    answer = input.nextLine();
                }
                answerInt = Integer.parseInt(answer);
            }

        }
    }

    public void ResetScoreSheet(ScoreSheet SS) { //not used but who knows
        SS.cases = new int[15];
    }

    public boolean isYorN(String s) { // When we want the user to write an 'y' or an 'n'
        try {
            if (!s.equals("y") && !s.equals("n")) {
                throw new Exception(s + " is not a valid answer");
            } else {
                return true; //every other char is false
            }
        } catch (Exception e) {
            return false; //In case this is not the type required
        }
    }

    public boolean isDiceSensitive(String numOfDices) { // A bit more complex, I want only an array of dices or the string "stop" or the empty sting ""
        try {
            String[] parts;
            if (numOfDices.equals("stop") || numOfDices.isBlank()) { //if its a stop or an empty, we're good
                return true;
            } else {
                parts = numOfDices.split(",");  // Else, I check if the int in the array are good split the string
                for (int i = 0; i < parts.length; i++) {
                    if (Integer.parseInt(parts[i]) < 1 || Integer.parseInt(parts[i]) > 5) {
                        return false;//if Dice not in range, return false
                    }
                }
                return true;
            }
        } catch (Exception e) {
            return false; //In case this is not the type required
        }
    }

    public boolean isACorrectCaseNumber(String ACase) { //When he choose a case, should be between 1 and 13
        try {
            if (Integer.parseInt(ACase) > 0 && Integer.parseInt(ACase) < 14) {
                return true;
            } else {
                return false;//Every other number is false
            }
        } catch (Exception e) {
            return false; //In case this is not the type required
        }
    }


    public int getNbRound() { // '-' who knows ...
        return nbRound;
    }
}
