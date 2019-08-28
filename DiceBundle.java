public class DiceBundle {
    Dice[] Dices = new Dice[5];    //declaring array of dice
    int nbThrow;

    public Dice[] Throw(){
        for (Dice d: this.Dices)
        {
            d.reroll();
        }
        return this.Dices;
    }

    public int NextThrow(){
        if(this.nbThrow<3){
            this.nbThrow++;
            this.Throw();
        }
        return nbThrow;
    }

    public void switchADice (int numDice){
        this.Dices[numDice].switchKeep();
    }
}
