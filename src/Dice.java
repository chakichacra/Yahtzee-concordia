import java.util.Random;

public class Dice {
    int score;
    boolean keep;

    public Dice(int score) {
        this.score = score;
    }

    public void reroll(){
        if(!this.keep) {
            Random rand = new Random();
            this.score = rand.nextInt(6)+1;
        }
    }

    public void switchKeep(){
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
