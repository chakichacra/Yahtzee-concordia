import java.lang.reflect.AccessibleObject;
import java.util.Scanner;
import java.io.File;

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
                    this.displayScoreBoard();
                    theDiceBundle.printTheDices();
                    System.out.printf("Throw n°%d, dice(s) to keep/reroll ? (ex : '2,4,5' or '' to stay the same or even 'stop' to stop here), there's also an admin command if you want to test : \n", nbLance);
                    answer = input.nextLine();
                    while (!isDiceSensitive(answer)) {
                        this.displayScoreBoard();
                        theDiceBundle.printTheDices();
                        System.out.printf("you gotta say the dices you want to keep/reroll like that : '2,4,5' or even nothing to reroll the same way it is right now or 'stop' to stop \n");
                        answer = input.nextLine();
                    }
                    if (!answer.equals("stop")) { //false if the input doesn't mean anything
                        if (answer.equals("admin")) {
                            System.out.printf("Write 'd' to edit the dices, 'c' to edit a case or 'end' to finish the game : \n");
                            while (!isAdminSensitive(answer)) {
                                this.displayScoreBoard();
                                theDiceBundle.printTheDices();
                                System.out.printf("Write 'd' to edit the dices, 'c' to edit the cases or 'end' to finish the game : \n");
                                answer = input.nextLine();
                            }
                            switch (answer) {
                                case "d":
                                    theDiceBundle.forceTheDices();
                                    break;
                                case "c":
                                    break;
                                case "end":
                                    break;
                            }
                        } else {
                            System.out.printf("Erreur de saisie, veuillez recommencer\n");
                            theDiceBundle.switchDices(answer);
                            this.displayScoreBoard(); //Print the score Sheet
                            theDiceBundle.printTheDices(); //Print the dices
                            System.out.printf("Confirm to roll the dices [y/n] : \n");
                            answer = input.nextLine();
                            while (!isYorN(answer)) {
                                this.displayScoreBoard();
                                theDiceBundle.printTheDices();
                                System.out.printf("Please write 'y' for yes or 'n' for no ... : \n");
                                answer = input.nextLine();

                            }
                        }
                    } else { //if the user wrote 'stop' I force the nb to 3 to stop the game (a little ugly but it works)
                        nbLance = 3;
                    }
                }

            }
            this.displayScoreBoard(); //And of the round, the user choose where to put hes dices
            theDiceBundle.resetTheDicesKeep();
            theDiceBundle.order();
            theDiceBundle.printTheDices();
            System.out.printf("Which case would you like to put those dices ? (ex : '7' for Three of a Kind) :\n");
            answer = input.nextLine();

            while (!isACorrectCaseNumber(answer)) {
                this.displayScoreBoard();
                theDiceBundle.printTheDices();
                System.out.printf("The cases are numbers from 1 to 6 and from 7 to 13, look above for their significations :\n");
                answer = input.nextLine();
            }

            //Check if the case is a number (however it can be out of range... maybe fix later, idk)
            int answerInt = Integer.parseInt(answer);
            //if(answerInt < 14 && answerInt > 6)
            //    answerInt += 2;
            while (!ScoreSheets[i].putScoreAtPlace(theDiceBundle, answerInt)) { //check if they're already smthg in that case
                this.displayScoreBoard();
                theDiceBundle.printTheDices();
                System.out.printf("This case is already completed\n");
                answer = input.nextLine();
                while (!isACorrectCaseNumber(answer)) {
                    this.displayScoreBoard();
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
            if (numOfDices.equals("stop") || numOfDices.isBlank() || numOfDices.equals("admin")) { //if its a stop or an empty, we're good (or the admin command)
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

    public boolean isAdminSensitive(String numOfDices) { // A bit more complex, I want only an array of dices or the string "stop" or the empty sting ""
        try {
            if (numOfDices.equals("c") || numOfDices.equals("d") || numOfDices.equals("end")) { //if its a stop or an empty, we're good (or the admin command)
                return true;
            }
            return false;
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

    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////                ////////////////////////////////////////////
    //////////////////////////////////// DISPLAY BOARD  ////////////////////////////////////////////
    ////////////////////////////////////                ////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void displayScoreBoard() {
        int cpt;
        int size;

        size = this.calculateSize();
        this.displayBorder(size);
        this.displayFirstLine();
        this.displayBorder(size);
        cpt = 1;
        while (cpt <= 16) {
            this.displayLine(cpt);
            this.displayBorder(size);
            ++cpt;
        }
    }

    private int calculateSize() {
        int nbOfPlayer;
        int cpt;
        int size;

        size = 0;
        nbOfPlayer = this.ScoreSheets.length;
        cpt = 0;
        while (cpt < nbOfPlayer) {
            size += this.ScoreSheets[cpt].playerName.length();
            ++cpt;
        }
        size += nbOfPlayer + 1;
        size += nbOfPlayer * 2;
        size += 15; //size of 'Three of a kind' largest option string
        return (size + 1);
    }

    private void displayFirstLine() {
        int nbOfPlayer;
        int cpt;

        cpt = 0;
        nbOfPlayer = this.ScoreSheets.length;
        System.out.print("\t");
        System.out.print("|     choix     |");
        while (cpt < nbOfPlayer) {
            System.out.print(" " + this.ScoreSheets[cpt].playerName
                    + " |");
            ++cpt;
        }
        System.out.print("\n");
    }

    private void displayLine(int num) {
        if ((num < 7 || num > 8) && (num != 16)) {
            if (num > 7)
                System.out.print(num - 2 + "-" + "\t");
            else
                System.out.print(num + "-" + "\t");
        } else
            System.out.print("  \t");


        switch (num) {
            case 1:
                System.out.print("|     "
                        + "ONES"
                        + "      |");
                this.displayLineEnd(1);
                break;
            case 2:
                System.out.print("|     "
                        + "TWOS"
                        + "      |");
                this.displayLineEnd(2);
                break;
            case 3:
                System.out.print("|     "
                        + "THREES"
                        + "    |");
                this.displayLineEnd(3);
                break;
            case 4:
                System.out.print("|     "
                        + "FOURS"
                        + "     |");
                this.displayLineEnd(4);
                break;
            case 5:
                System.out.print("|     "
                        + "FIVES"
                        + "     |");
                this.displayLineEnd(5);
                break;
            case 6:
                System.out.print("|     "
                        + "SIXES"
                        + "     |");
                this.displayLineEnd(6);
                break;
            case 7:
                System.out.print("|     "
                        + "SUM"
                        + "       |");
                this.displayLineEnd(7);
                break;
            case 8:
                System.out.print("|     "
                        + "BONUS"
                        + "     |");
                this.displayLineEnd(8);
                break;
            case 9:
                System.out.print("|"
                        + "THREE OF A KIND"
                        + "|");
                this.displayLineEnd(9);
                break;
            case 10:
                System.out.print("|"
                        + "FOUR OF A KIND"
                        + " |");
                this.displayLineEnd(10);
                break;
            case 11:
                System.out.print("|     "
                        + "CHANCE"
                        + "    |");
                this.displayLineEnd(11);
                break;
            case 12:
                System.out.print("|   "
                        + "FULL HOUSE"
                        + "  |");
                this.displayLineEnd(12);
                break;
            case 13:
                System.out.print("|"
                        + "SMALL STRAIGHT"
                        + " |");
                this.displayLineEnd(13);
                break;
            case 14:
                System.out.print("|"
                        + "LARGE STRAIGHT"
                        + " |");
                this.displayLineEnd(14);
                break;
            case 15:
                System.out.print("|     "
                        + "YAHTZEE"
                        + "   |");
                this.displayLineEnd(15);
                break;
            case 16:
                System.out.print("|  "
                        + "TOTAL_SCORE"
                        + "  |");
                this.displayLineEnd(16);
                break;
        }
        System.out.print("\n");
    }

    private void displayLineEnd(int state) {
        int cpt;
        int nbOfPlayer;
        int sizeName;
        int score;
        int temp;

        cpt = 0;
        nbOfPlayer = this.ScoreSheets.length;

        while (cpt < nbOfPlayer) {
            score = this.ScoreSheets[cpt].cases[state];
            sizeName = this.ScoreSheets[cpt].playerName.length() + 2;
            if (score >= 100) {
                temp = sizeName - 3;
                if (temp % 2 == 1)
                    System.out.print(" ".repeat(((temp / 2) + 1)));
                else
                    System.out.print(" ".repeat(temp / 2));
                System.out.print(score
                        + " ".repeat(temp / 2)
                        + "|");
            } else if (score >= 10) {
                temp = sizeName - 2;
                if (temp % 2 == 1)
                    System.out.print(" ".repeat((temp / 2) + 1));
                else
                    System.out.print(" ".repeat(temp / 2));
                System.out.print(score
                        + " ".repeat(temp / 2)
                        + "|");
            } else {
                temp = sizeName - 1;
                if (temp % 2 == 1)
                    System.out.print(" ".repeat((temp / 2) + 1));
                else
                    System.out.print(" ".repeat(temp / 2));
                if (score == -1)
                    System.out.print("."
                            + " ".repeat(temp / 2)
                            + "|");
                else
                    System.out.print(score
                            + " ".repeat(temp / 2)
                            + "|");
            }
            ++cpt;
        }
    }

    private void displayBorder(int size) {
        int cpt;

        cpt = 0;
        System.out.print("\t");
        while (cpt < size) {
            System.out.print("-");
            ++cpt;
        }
        System.out.print("\n");
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////  END OF DISPLAY  /////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void registerScoreBoard() {
        File scoreSave = new File("Save.txt");
        int cpt;
        int size;

        if (scoreSave.createNewFile())
            System.out.println("Save file created!");
        scoreSave.setWritable(true);

        size = this.calculateSize();
        this.displayBorder(size, );
        this.displayFirstLine();
        this.displayBorder(size);
        cpt = 1;
        while (cpt <= 16) {
            this.displayLine(cpt);
            this.displayBorder(size);
            ++cpt;
        }
    }
}