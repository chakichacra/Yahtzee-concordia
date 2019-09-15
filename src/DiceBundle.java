public class DiceBundle {
    Dice[] Dices = new Dice[5];    //declaring array of dice
    int nbThrow;

    public void Throw() { // for each dice, use the method 'throw' that reroll the dice if the user doesn't want to keep it
        for (int i = 0; i < Dices.length; i++) {
            Dices[i].reroll();
        }
    }

    public void printTheDices() { //Print all the dices and show if the user want to keep by displaying square brackets if its the case
        boolean diceToKeep = false;
        System.out.printf("The dices : ");
        for (int i = 0; i < Dices.length; i++) {
            if (!Dices[i].isKeep()) {
                System.out.printf(Dices[i].getScore() + " ");
            } else
                System.out.printf("[" + Dices[i].getScore() + "] ");
        }
        /*
        if (diceToKeep) {   //I wanted to show the dices with keep 'on' at another row but the problem is that the dice numbers would change
            System.out.printf("The dices you keep : ");
            for (int i = 0; i < Dices.length; i++) {
                if (Dices[i].isKeep()) {
                    System.out.printf(Dices[i].getScore() + " ");
                }
            }
        }   //aborted
        */
        System.out.printf("\n");
    }

    public void resetTheDices() {  //all the dices are reset to 0 and the bool 'keep' is false (used at the initialisation of the dicebundle and at each new turn)
        for (int i = 0; i < Dices.length; i++) {
            Dices[i] = new Dice(0);
            Dices[i].keepTo0();
        }
    }

    public void resetTheDicesKeep() {  //reset only the keep (used at the 3rd throw or if the user stop manually with the command 'stop')
        for (int i = 0; i < Dices.length; i++) {
            Dices[i].keepTo0();
        }
    }

    public boolean switchDices(String numOfDices) {  //swith all the dices in the string (ex : '3,4,5')
        String[] parts;
        if (numOfDices.isBlank()){
                parts = new  String[] {"1","1"};  //if the user choose to send nothing, we just reroll the dices.
            }
            else {
                parts = numOfDices.split(",");  //split the string
            }
            for (int i = 0; i < parts.length; i++) {
                //System.out.println("The dice : "+parts[i]);
                if (!parts[i].matches("-?\\d+(\\.\\d+)?")) { //check if there is no mistake
                    return false; //if mistake return null
                }
                if (Integer.parseInt(parts[i]) < 1 || Integer.parseInt(parts[i]) > 5) {
                    return false; //if Dice not in range, return null
                }
            }
            for (int i = 0; i < parts.length; i++) { //2nd for afer the checks, the dices bool 'keep' are switching here
                this.Dices[Integer.parseInt(parts[i]) - 1].switchKeep();
            }
            return true; //no problem, return true
    }

    public void order(){ //order the dices at the last throw or when the user stop manually
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
        /*for (int i = 0; i < Dices.length; i++) { //A print I used to check if this worked correctly
            System.out.printf("Dice %d is %d, ",i, Dices[i].score);
        }
        System.out.printf("\n");
         */
    }

    //All those functions works if the dices are in order (with the function 'order' above)

    public boolean thereIsAFull(){  //Check if there is a full
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

    public boolean thereIsASmallStraight(){ //Check if there is a small Straight. A bit hard to understand but it works
        //System.out.println("Doing a small straight");
        for (int i=0;i<2;i++) { //starting from the 1st or 2nd dice
            if (this.Dices[i].score==Dices[i+1].score) { // 2 firsts dices are the same
                //System.out.println("1");
                if (this.Dices[i].score==Dices[i+2].score-1 && this.Dices[i+2].score==Dices[i+3].score-1) {
                    return true;
                }
            }else if (this.Dices[i+1].score==Dices[i+2].score){// 2nd and 3rd dices are the same
                //System.out.println("2");
                if (this.Dices[i].score==Dices[i+1].score-1 && this.Dices[i+1].score==Dices[i+3].score-1) {
                    return true;
                }
            }else if (this.Dices[i+2].score==Dices[i+3].score){//3rd and 4th dices are the same
                //System.out.println("3");
                if (this.Dices[i].score==Dices[i+1].score-1 && this.Dices[i+1].score==Dices[i+2].score-1) {
                    return true;
                }
            }
        }
        return false; //not a small straight :(
    }

    public boolean thereIsALargeStraight(){//Check if there is a large Straight
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

    public boolean thereIsAYahtzee(){ //Check if there is a Yathzee
        for (int i=0;i<4;i++) {
            if (this.Dices[i].score != this.Dices[i+1].score){
                return false;
            }
        }
        return true;
    }

    public int sumOfDices(){
        int sum = 0;
        for (int i = 0; i < this.Dices.length;i++){
            sum += this.Dices[i].score;
        }
    return sum;
    }
}
