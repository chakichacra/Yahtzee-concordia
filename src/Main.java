import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);  // I read the number of player
        System.out.println("Number of player(s) : ");
        String nbPlayer = input.nextLine(); //J'attends la reponse utilisateur
        while (!isAReasonableNumberOfPlayer(nbPlayer)) { //While player number is too big or not a number
            System.out.println("A number between 1 and 5 if possible ... : ");
            nbPlayer = input.nextLine(); //I ask again
        }
        Game Game1 = new Game(Integer.parseInt(nbPlayer)); //I create the game with the number of player choosed by the user (max 6)
        //System.out.println(Game1.ScoreSheets[0].getPlayerName());
        Game1.start();
    }

    public static boolean isAReasonableNumberOfPlayer(String nbPlayer) {
        try {
            if (Integer.parseInt(nbPlayer) > 0 && Integer.parseInt(nbPlayer) < 6) {
                return true;
            }
            return false; //every other char is false
        } catch (Exception e) {
            return false; //In case this is not the type required
        }
    }

}

