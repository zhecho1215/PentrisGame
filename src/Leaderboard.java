import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 The Leaderboard is a JPanel component that records and displays the
 results of played games in a descending order
 */
public class Leaderboard
{
    private ScoreObject scoreArray[] = new ScoreObject[10];
    private ScoreObject placeholder;
    private JLabel placementArray[] = new JLabel[20];
    private JPanel leaderboardX;
    /**
     Constructs the score and name placeholders on a JPanel with a GridLayout
     @param leaderboard the JPanel on which this leaderboard is added
     */
    public Leaderboard(JPanel leaderboard)
    {
        for (int x = 0; x < 20; x++) {
            placementArray[x] = new JLabel();
            placeholder = new ScoreObject();
            placementArray[x].setFont(placementArray[x].getFont().deriveFont(35.0f));
            leaderboard.add(placementArray[x]);
            leaderboardX = leaderboard;
        }
        for (int x = 0; x < 10; x++) {
            scoreArray[x] = new ScoreObject();
        }
    }
    /**
     Inserts and displays the ScoreObject on the appropriate ranking in the leaderboard
     @param score the ScoreObject to insert
     */
    public void inScore(ScoreObject score)
    {
        ScoreObject transfer = new ScoreObject();
        if (score.getTotal() > scoreArray[8].getTotal()) {
            scoreArray[8] = new ScoreObject(score.getName(),score.getTotal());
            for (int i=8; i!=0 && scoreArray[i].getTotal() > scoreArray[i-2].getTotal(); i-=2) {
                placeholder = new ScoreObject(scoreArray[i-2].getName(),scoreArray[i-2].getTotal());
                scoreArray[i-2] = new ScoreObject(scoreArray[i].getName(),scoreArray[i].getTotal());
                scoreArray[i] = new ScoreObject(placeholder.getName(),placeholder.getTotal());
            } }else if(score.getTotal() > scoreArray[9].getTotal()) {
            scoreArray[9] = new ScoreObject(score.getName(),score.getTotal());
            for (int i=9; i!=1 && scoreArray[i].getTotal() > scoreArray[i-2].getTotal(); i-=2) {
                placeholder = new ScoreObject(scoreArray[i-2].getName(),scoreArray[i-2].getTotal());
                scoreArray[i-2] = new ScoreObject(scoreArray[i].getName(),scoreArray[i].getTotal());
                scoreArray[i] = new ScoreObject(placeholder.getName(),placeholder.getTotal());
            }
        }
        updateLeaderboard(leaderboardX);
    }
    /**
     Updates the Leaderboard
     @param leaderboard the JPanel that is updated
     */
    public void updateLeaderboard(JPanel leaderboard) {
        for(int x=0; x < 20; x++) {
            if(x%2 == 0) {
                placementArray[x].setText(scoreArray[x/2].getName());
                leaderboard.add(placementArray[x]);
            } else {
                placementArray[x].setText(""+scoreArray[(x-1)/2].getTotal());
                leaderboard.add(placementArray[x]);
            }
        }
    }
}