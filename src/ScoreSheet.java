public class ScoreSheet {

    String playerName;
    int nbCase = 15;
    int[] cases;

    public ScoreSheet(String playerName) {
        this.playerName = playerName;
        cases = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,};
    }

    public String getPlayerName() {
        return playerName;
    }

    void View(ScoreSheet[] scoreSheets) {

        System.out.printf(" --- The Score Sheets ---\n");
        for (int i = 1; i < 8; i++) {
            for (int y=0; y < scoreSheets.length; y++) {
                if (i!=7){
                    System.out.printf("%2d : %2s", i, scoreSheets[y].scoreNull(i));
                }
                switch (i) {
                    case 1: //3 of a kind
                        System.out.printf("%10d : %11s : %2s", i + 6, "3 of a kind", scoreSheets[y].scoreNull(i + 6));
                        break;
                    case 2://4 of a kind
                        System.out.printf("%10d : %11s : %2s", i + 6, "4 of a kind", scoreSheets[y].scoreNull(i + 6));
                        break;
                    case 3://Luck (sum of Dices)
                        System.out.printf("%10d : %11s : %2s", i + 6, "Chance", scoreSheets[y].scoreNull(i + 6));
                        break;
                    case 4://Full House
                        System.out.printf("%10d : %11s : %2s", i + 6, "Full House", scoreSheets[y].scoreNull(i + 6));
                        break;
                    case 5://Small Straight
                        System.out.printf("%10d : %11s : %2s", i + 6, "S.Straight", scoreSheets[y].scoreNull(i + 6));
                        break;
                    case 6://Large Straight
                        System.out.printf("%10d : %11s : %2s", i + 6, "L.Straight", scoreSheets[y].scoreNull(i + 6));
                        break;
                    case 7://Large Straight
                        System.out.printf("%17d : %11s : %2s", i + 6, "Yahtzee", scoreSheets[y].scoreNull(i + 6));
                        break;
                }
                System.out.printf("              ");
            }

            System.out.printf("\n");
        }
        //Simon Tancev
    }

    String scoreNull(int numCase) {
        if (cases[numCase] == -1) return ".";
        return String.valueOf(cases[numCase]); //probleme
    }

    public boolean putScoreAtPlace(DiceBundle theDiceBundle, int ThePlace) {
        if (this.thisPlaceIsAlreadyCompleted(ThePlace)) {
            //The case is already completed
            return false;
        }
        theDiceBundle.order();
        boolean putA0 = true;
        switch (ThePlace) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                int nbGoodDice = 0;
                for (int i = 0; i < theDiceBundle.Dices.length; i++) {
                    if (theDiceBundle.Dices[i].score == ThePlace) {
                        nbGoodDice++;
                    }
                }
                this.cases[ThePlace] = nbGoodDice * ThePlace;
                break;
            case 7: //3 of a kind
                for (int i = 0; i < 3; i++) {
                    if (theDiceBundle.Dices[i].score == theDiceBundle.Dices[i + 1].score && theDiceBundle.Dices[i + 1].score == theDiceBundle.Dices[i + 2].score) {
                        this.cases[7] = theDiceBundle.Dices[i].score * 3;
                        putA0 = false;
                        break;
                    }
                }
                if (putA0) {
                    this.cases[7] = 0;
                }
                break;
            case 8://4 of a kind
                for (int i = 0; i < 2; i++) {
                    if (theDiceBundle.Dices[i].score == theDiceBundle.Dices[i + 1].score && theDiceBundle.Dices[i + 1].score == theDiceBundle.Dices[i + 2].score && theDiceBundle.Dices[i + 2].score == theDiceBundle.Dices[i + 3].score) {
                        this.cases[8] = theDiceBundle.Dices[i].score * 3;
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
                for (int i = 0; i < theDiceBundle.Dices.length; i++) {
                    score += theDiceBundle.Dices[i].score;
                }
                this.cases[9] = score;
                break;
            case 10://Full House
                if (theDiceBundle.thereIsAFull()) {
                    this.cases[10] = 25;
                } else
                    this.cases[10] = 0;
                break;
            case 11://Small Straight
                if (theDiceBundle.thereIsASmallStraight()) {
                    this.cases[11] = 30;
                } else
                    this.cases[11] = 0;
                break;
            case 12://Large Straight
                if (theDiceBundle.thereIsALargeStraight()) {
                    this.cases[12] = 40;
                } else
                    this.cases[12] = 0;
                break;
            case 13://Yahtzee / Yam
                if (theDiceBundle.thereIsAYahtzee()) {
                    this.cases[13] = 50;
                } else
                    this.cases[13] = 0;
                break;

            default:
                // code block
        }
        return true;
    }

    public boolean thisPlaceIsAlreadyCompleted(int numCase) {
        if (cases[numCase] == -1) {
            return false;
        }
        return true;
    }

}
