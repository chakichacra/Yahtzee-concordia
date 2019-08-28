import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Number of player(s) : ");
        String nbPlayer = input.nextLine();
        Game Game1 = new Game(Integer.parseInt(nbPlayer));
        while(Game1.getNbRound()<13){
            Game1.nextRound();

        }
    }
}
