public class DiceBundle {
    Dice[] Dices = new Dice[5];    //declaring array of dice
    int nbThrow;

    public void Throw(){
        for (int i = 0; i < Dices.length; i++)
        {
            Dices[i].reroll();
        }
    }

    public void printTheDices(){
        System.out.println("The dices : "+Dices[0].getScore()+" "+Dices[1].getScore()+" "+Dices[2].getScore()+" "+Dices[3].getScore()+" "+Dices[4].getScore());
    }

    public void resetTheDices(){
        for (int i = 0; i < Dices.length; i++) {
            Dices[i] = new Dice(0);
            Dices[i].keepTo0();
        }
    }

    public void switchADice (int numDice){
        this.Dices[numDice].switchKeep();
    }
}
