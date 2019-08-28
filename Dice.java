import java.util.Random;

public class Dice {
    int score;
    boolean keep;

    public void reroll(){
        if(!this.keep) {
            Random rand = new Random();
            this.score = rand.nextInt(7);
        }
    }

    public void switchKeep(){
        if (keep){
            keep = false;
        }else{
            keep = true;
        }
    }

}
