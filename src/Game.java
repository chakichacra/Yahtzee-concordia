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
                nbLance ++;
                theDiceBundle.Throw();
                while (!answer.equals("stop") && !answer.equals("y")) {
                    ScoreSheets[i].View();
                    theDiceBundle.printTheDices();
                    System.out.printf("Throw n°%d, dice(s) to keep/reroll ? (ex : '2,4,5' or 'stop' to stop here) : \n", nbLance);
                    if (nbLance < 3) { // A little check never killed anyone
                        answer = input.nextLine();
                    } else
                        answer = "stop"; //I force the user to stop if its the case
                    if (!answer.equals("stop")) {
                        if (!theDiceBundle.switchDices(answer)) { //false if the input doesn't mean anything
                            System.out.printf("Erreur de saisie, veuillez recommencer\n");
                        } else {
                            ScoreSheets[i].View(); //Print the score Sheet
                            theDiceBundle.printTheDices(); //Print the dices
                            System.out.printf("Confirm to roll the dices [y/n] : \n");
                            answer = input.nextLine();
                        }
                    }
                    else{ //if the user wrote 'stop' I force the nb to 3 to stop the game (a little ugly but it works)
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
            while (!ScoreSheets[i].putScoreAtPlace(theDiceBundle,Integer.parseInt(answer))){ //check if they're already smthg in that case
                System.out.printf("This case is already completed\n");
                ScoreSheets[i].View();
                theDiceBundle.printTheDices();
                answer = input.nextLine();
            };
        }
    }

    public void ResetScoreSheet(ScoreSheet SS) { //not used but who knows
        SS.cases = new int[15];
    }

    public int getNbRound() { // '-' who knows ...
        return nbRound;
    }
}
