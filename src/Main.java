import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);  // I read the number of player
        System.out.println("Number of player(s) : ");
        String nbPlayer = input.nextLine(); //J'attends la reponse utilisateur
        while (!nbPlayer.matches("-?\\d+(\\.\\d+)?") || Integer.parseInt(nbPlayer)>6 || Integer.parseInt(nbPlayer)<0){ //While player number is too big or not a number
            nbPlayer = input.nextLine(); //I ask again
        }
        Game Game1 = new Game(Integer.parseInt(nbPlayer)); //I create the game with the number of player choosed by the user (max 6)
        //System.out.println(Game1.ScoreSheets[0].getPlayerName());
        Game1.start();
    }
}
