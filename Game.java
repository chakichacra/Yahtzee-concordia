import java.util.Scanner;

public class Game {
    ScoreSheet[] ScoreSheets;
    int nbRound;
    DiceBundle theDiceBundle = new DiceBundle();

    public Game(int nbPlayer) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        ScoreSheets = new ScoreSheet[nbPlayer];
        String name;
        nbRound = 0;
        for (ScoreSheet SS:ScoreSheets) {
            System.out.println("The name of player "+nbPlayer+" : ");//Write he's name
            nbPlayer = nbPlayer - 1;
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
