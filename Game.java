import java.util.Scanner;

public class Game {
    ScoreSheet[] ScoreSheets;
    int nbRound;

    public Game(int nbPlayer) {
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        ScoreSheets = new ScoreSheet[nbPlayer];
        String name;
        for (ScoreSheet SS:ScoreSheets) {
            System.out.println("The name of player "+nbPlayer+" : ");//Write he's name
            nbPlayer = nbPlayer - 1;
            name = myObj.nextLine();
            SS = new ScoreSheet(name);
            System.out.println("ScoreSheet for "+ SS.getPlayerName()+ " created");
        }
    }

    public int nextRound(){
        nbRound++;
        return nbRound;
    }

    public void ResetScoreSheet(ScoreSheet SS){
        SS.cases = new int[15];
    }

    public int getNbRound() {
        return nbRound;
    }
}
