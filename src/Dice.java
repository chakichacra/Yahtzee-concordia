import java.util.Random;

public class Dice {
    int score; // a number between 1 and 6
    boolean keep; // 0 = want to throw the dice , 1 = want to keep the dice

    public Dice(int score) {
        this.score = score;
    }

    public void reroll(){ //if the keep is true, the dice doesn't change
        if(!this.keep) {
            Random rand = new Random();
            this.score = rand.nextInt(6)+1; // a rand between 1 and 6
        }
    }

    public void switchKeep(){ // 'keep = !keep;' doesn't work :'(
        if (keep){
            keep = false;
        }else{
            keep = true;
        }
    }

    public void keepTo0(){
        keep = false;
    }

    public int getScore() {
        return score;
    }

    public boolean isKeep() {
        return keep;
    }
}
