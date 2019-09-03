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

    public void order(){
        for (int i = 0; i < Dices.length; i++) {
            int temp;
            for (int j = i; j > 0; j--) {
                if (Dices[j].score < Dices[j - 1].score) {
                    temp = Dices[j].score;
                    Dices[j].score = Dices[j - 1].score;
                    Dices[j - 1].score = temp;
                }
            }
        }
        /*for (int i = 0; i < Dices.length; i++) {
            System.out.printf("Dice %d is %d, ",i, Dices[i].score);
        }
        System.out.printf("\n");
         */
    }

    public boolean thereIsAFull(){
        if (this.Dices[0].score == this.Dices[1].score && this.Dices[1].score == this.Dices[2].score){
            if (this.Dices[3].score == this.Dices[4].score){
                return true;
            }
        }
        if (this.Dices[2].score == this.Dices[3].score && this.Dices[3].score == this.Dices[4].score){
            if (this.Dices[0].score == this.Dices[1].score){
                return true;
            }
        }
        return false;
    }

    public boolean thereIsASmallStraight(){
        for (int i=0;i<3;i++) {
            if (this.Dices[i].score == this.Dices[i + 1].score && this.Dices[i + 1].score == this.Dices[i + 2].score) {
                return true;
            }
        }
        return false;
    }

    public boolean thereIsALargeStraight(){
        if (this.Dices[0].score == 1) {
            if (this.Dices[1].score == 2 && this.Dices[2].score == 3 && this.Dices[3].score == 4 && this.Dices[4].score == 5) {
                return true;
            }
        }
        if (this.Dices[0].score == 2){
            if (this.Dices[1].score == 3 && this.Dices[2].score == 4 && this.Dices[3].score == 5 && this.Dices[4].score == 6) {
                return true;
            }
        }
        return false;
    }

    public boolean thereIsAYahtzee(){
        for (int i=0;i<4;i++) {
            if (this.Dices[i].score != this.Dices[i+1].score){
                return false;
            }
        }
        return true;
    }

}
