public class DiceBundle {
    Dice[] Dices = new Dice[5];    //declaring array of dice
    int nbThrow;

    public Dice[] Throw(){
        for (Dice D: Dices)
        {
            System.out.println(D.getScore());
            D.reroll();
        }
        return this.Dices;
    }

    public void printTheDices(){
        System.out.println("The dices : "+Dices[0]+" "+Dices[1]+" "+Dices[2]+" "+Dices[3]+" "+Dices[4]);
    }

    public void resetTheDices(){
        for (Dice D:Dices) {
            D = new Dice(0);
            System.out.println("I reset a Dice");
            D.keepTo0();
            System.out.println(D.getScore());
        }
    }

    public void switchADice (int numDice){
        this.Dices[numDice].switchKeep();
    }
}
