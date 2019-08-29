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
        int countPlayer = 1;
        for (ScoreSheet SS:ScoreSheets) {  //je cree un scoresheet par nb de joueur
            System.out.println("The name of player "+countPlayer+" : ");// Whrite he's name
            countPlayer ++; //je passe au joueur suivant
            name = myObj.nextLine();
            SS = new ScoreSheet(name);
            System.out.println("ScoreSheet for "+ SS.getPlayerName()+ " created");
        }
        theDiceBundle.resetTheDices();
    }

    public void nextRound(){
        nbRound++;
        System.out.printf("---------- ROUND NUMBER %2d ----------\n",nbRound );
    }

    public void RollTheDices(){
        theDiceBundle.Throw();
        theDiceBundle.printTheDices();
    };

    public void ResetScoreSheet(ScoreSheet SS){
        SS.cases = new int[15];
    }
    public int getNbRound() {
        return nbRound;
    }
}