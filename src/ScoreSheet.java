public class ScoreSheet {

    String playerName;
    int nbCase =15;
    int[] cases;

    public ScoreSheet(String playerName) {
        this.playerName = playerName;
        cases = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,}; //I initialize at -1 because I need to distinguished the score 'null' and the score '0'
                                                                          // '-1' mean the case is empty and '0' mean the case is played with a score of 0
    }

    public String getPlayerName() {
        return playerName;
    }

    void View(){
        System.out.printf(" --- Affichage de la Score Sheet ---\n");
        for (int i = 1; i<7; i++){
            System.out.printf("%2d : %2s",i, this.scoreNull(i));
            switch(i){
                case 1: //3 of a kind
                    System.out.printf("%15d : %11s : %2s",i+6,"3 of a kind", this.scoreNull(i+6));
                    break;
                case 2://4 of a kind
                    System.out.printf("%15d : %11s : %2s",i+6,"4 of a kind", this.scoreNull(i+6));
                    break;
                case 3://Luck (sum of Dices)
                    System.out.printf("%15d : %11s : %2s",i+6,"Chance", this.scoreNull(i+6));
                    break;
                case 4://Full House
                    System.out.printf("%15d : %11s : %2s",i+6,"Full House", this.scoreNull(i+6));
                    break;
                case 5://Small Straight
                    System.out.printf("%15d : %11s : %2s",i+6,"S.Straight", this.scoreNull(i+6));
                    break;
                case 6://Large Straight
                    System.out.printf("%15d : %11s : %2s",i+6,"L.Straight", this.scoreNull(i+6));
                    break;
                }
            System.out.printf("\n");
        }
        System.out.printf("%22d : %11s : %2s\n",13,"Yahtzee", this.scoreNull(13));
    }

    String scoreNull(int numCase){ //check if the score is null so he can play here
        if (cases[numCase] == -1) return ".";
        return String.valueOf(cases[numCase]); //probleme
    }

    public boolean putScoreAtPlace(DiceBundle theDiceBundle,int ThePlace){
        if (this.thisPlaceIsAlreadyCompleted(ThePlace)){
            //The case is already completed
            return false;
        }
        theDiceBundle.order();
        boolean putA0 = true;
        switch(ThePlace) {
            case 1: case 2: case 3: case 4: case 5: case 6:
                int nbGoodDice = 0;
                for (int i = 0; i < theDiceBundle.Dices.length; i++){
                    if (theDiceBundle.Dices[i].score == ThePlace){
                        nbGoodDice ++;
                    }
                }
                this.cases[ThePlace] = nbGoodDice*ThePlace;
                break;
            case 7: //3 of a kind
                for (int i = 0; i < 3; i++){
                    if (theDiceBundle.Dices[i].score == theDiceBundle.Dices[i+1].score && theDiceBundle.Dices[i+1].score == theDiceBundle.Dices[i+2].score){
                        this.cases[7] = theDiceBundle.Dices[i].score*3;
                        putA0 = false;
                        break;
                    }
                }
                if (putA0) {
                    this.cases[7] = 0;
                }
                break;
            case 8://4 of a kind
                for (int i = 0; i < 2; i++){
                    if (theDiceBundle.Dices[i].score == theDiceBundle.Dices[i+1].score && theDiceBundle.Dices[i+1].score == theDiceBundle.Dices[i+2].score && theDiceBundle.Dices[i+2].score == theDiceBundle.Dices[i+3].score){
                        this.cases[8] = theDiceBundle.Dices[i].score*3;
                        putA0 = false;
                        break;
                    }
                }
                if (putA0) {
                    this.cases[8] = 0;
                }
                break;
            case 9://Luck (sum of Dices)
                int score = 0;
                for (int i = 0; i < theDiceBundle.Dices.length; i++){
                    score += theDiceBundle.Dices[i].score;
                }
                this.cases[9] = score;
                break;
            case 10://Full House
                if (theDiceBundle.thereIsAFull()) {
                    this.cases[10] = 25;
                }else
                    this.cases[10] = 0;
                break;
            case 11://Small Straight
                if (theDiceBundle.thereIsASmallStraight()) {
                    this.cases[11] = 30;
                }else
                    this.cases[11] = 0;
                break;
            case 12://Large Straight
                if (theDiceBundle.thereIsALargeStraight()) {
                    this.cases[12] = 40;
                }else
                    this.cases[12] = 0;
                break;
            case 13://Yahtzee / Yam case
                if (theDiceBundle.thereIsAYahtzee()) {
                    this.cases[13] = 50;
                }else
                    this.cases[13] = 0;
                break;

        }
        return true;
    }

    public boolean thisPlaceIsAlreadyCompleted(int numCase){
        if(cases[numCase] == -1){
            return false;
        }
        return true;
    }

}
