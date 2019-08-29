import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);  // Je recup le nb de joueurs
        System.out.println("Number of player(s) : ");
        String nbPlayer = input.nextLine(); //J'attends la reponse utilisateur
        Game Game1 = new Game(Integer.parseInt(nbPlayer)); //Je cree la partie avec le nb de joueur saisie
        while(Game1.getNbRound()<13){
            Game1.nextRound();
            Game1.RollTheDices();

        }
    }
}
