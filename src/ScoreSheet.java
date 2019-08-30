public class ScoreSheet {
    
    String playerName;
    int nbCase =15;
    int[] cases;

    public ScoreSheet(String playerName) {
        this.playerName = playerName;
        cases = new int[15];
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        System.out.println("This is the function");
        this.playerName = playerName;
    }

    public int getNbCase() {
        return nbCase;
    }

    public void setNbCase(int nbCase) {
        this.nbCase = nbCase;
    }

    public int[] getCases() {
        return cases;
    }

    public void setCases(int[] cases) {
        this.cases = cases;
    }

    public void changePlayer(String name){
        this.playerName=name;
                                  }

    public void whriteSheet(int empCase,int Score){
        if(empCase<=this.nbCase && empCase>0){
            this.cases[empCase]=Score;
        }
        else{
            System.out.println("Error while putting the score "+Score+" at the place "+empCase+" of the player "+this.playerName);
        }
    }

    void View(){
        System.out.println(" --- Affichage de la Score Sheet --- \n\n\n\n\n\n --- Affichage de la Score Sheet ---");
        //Simon Tancev
    }

}
