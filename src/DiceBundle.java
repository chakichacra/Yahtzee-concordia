public class DiceBundle {
    Dice[] Dices = new Dice[5];    //declaring array of dice
    int nbThrow;

    public void Throw() {
        for (int i = 0; i < Dices.length; i++) {
            Dices[i].reroll();
        }
    }

    public void printTheDices() {
        boolean diceToKeep = false;
        System.out.printf("The dices : ");
        for (int i = 0; i < Dices.length; i++) {
            if (!Dices[i].isKeep()) {
                System.out.printf(Dices[i].getScore() + " ");
            } else
                System.out.printf("[" + Dices[i].getScore() + "] ");
        }
        /*
        if (diceToKeep) {
            System.out.printf("The dices you keep : ");
            for (int i = 0; i < Dices.length; i++) {
                if (Dices[i].isKeep()) {
                    System.out.printf(Dices[i].getScore() + " ");
                }
            }
        }
        */
        System.out.printf("\n");
    }

    public void resetTheDices() {
        for (int i = 0; i < Dices.length; i++) {
            Dices[i] = new Dice(0);
            Dices[i].keepTo0();
        }
    }

    public void resetTheDicesKeep() {
        for (int i = 0; i < Dices.length; i++) {
            Dices[i].keepTo0();
        }
    }

    public boolean switchDices(String numOfDices) {
        if (numOfDices != null) {
            String[] parts = numOfDices.split(",");
            for (int i = 0; i < parts.length; i++) {
                //System.out.println("The dice : "+parts[i]);
                if (!parts[i].matches("-?\\d+(\\.\\d+)?")) {
                    return false;
                }
                if (Integer.parseInt(parts[i]) < 1 || Integer.parseInt(parts[i]) > 5) {
                    return false;
                }
            }
            for (int i = 0; i < parts.length; i++) {
                this.Dices[Integer.parseInt(parts[i]) - 1].switchKeep();
            }
            return true;
        }
        else{
            return true;
        }
    }
}
