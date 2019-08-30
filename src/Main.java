import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);  // Je recup le nb de joueurs
        System.out.println("Number of player(s) : ");
        String nbPlayer = input.nextLine(); //J'attends la reponse utilisateur
        while (!nbPlayer.matches("-?\\d+(\\.\\d+)?") || Integer.parseInt(nbPlayer)>6 || Integer.parseInt(nbPlayer)<0){
            nbPlayer = input.nextLine();
        }
        Game Game1 = new Game(Integer.parseInt(nbPlayer)); //Je cree la partie avec le nb de joueur saisie
        System.out.println(Game1.ScoreSheets[0].getPlayerName());
        Game1.start();
    }
}
