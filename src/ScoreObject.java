import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.*;
import javax.swing.BoxLayout;

/**
 This Object records the score, last amount scored and name of a single game of pentris
 */
public class ScoreObject
{
    private int total;
    private int increase;
    private String name;
    private JLabel currentScore;
    private JLabel currentAdd;
    /**
     Constructs a placeholder name and score
     */
    public ScoreObject() {
        total = 0;
        increase = 0;
        name = "______";
        currentScore = new JLabel("" + 0);
        currentAdd = new JLabel("");
    }
    /**
     Constructs an object containing a specified score and name
     @param inName the player's name
     @param inScore the score

     */
    public ScoreObject(String inName, int inScore) {
        total = inScore;
        increase = 0;
        name = inName;
        currentScore = new JLabel("Score:"+inScore);
        currentAdd = new JLabel("+0");
    }
    /**
     Attaches a new name to the ScoreObject
     @param inputName the name
     */
    public void insertName(String inputName) {
        name = inputName;
    }
    /**
     Returns the score
     @return total the score so far
     */
    public int getTotal() {
        return total;
    }
    /**
     Returns the last amount of points scored
     @return increase last points scored
     */
    public int getIncrease() {
        return increase;
    }
    /**
     Increases the score accordingly to the amount of lines cleared and records that increase
     @param x the amount of lines cleared
     */
    public void addScore(int x) {
        increase = 100*x*x;
        total = total + increase;
    }
    /**
     Attaches JLabels containing the total score and the last point increase to a JPanel
     @param panel the JPanel to which the JLabels are attached
     */
    public void attachScore(JPanel panel) {
        //I removed some redundant variables
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        currentAdd = new JLabel("+" +increase);
        currentAdd.setFont(currentAdd.getFont().deriveFont(40.0f));
        panel.add(currentAdd);
        currentScore = new JLabel("Score:    " +total);
        currentScore.setFont(currentScore.getFont().deriveFont(40.0f));
        panel.add(currentScore);

        //don't worry about this Component.CENTER_ALIGNMENT it's just a better practice - it does the same thing
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        currentScore.setAlignmentX(Component.RIGHT_ALIGNMENT);
        currentAdd.setAlignmentX(Component.RIGHT_ALIGNMENT);

    }
    /**
     Updates the score on display
     */
    public void updateScore() {
        currentScore.setText("Score:  "+total);
        currentAdd.setText("+"+increase);
    }
    /**

     */
    public void resetScore() {
        total = 0;
        increase = 0;
    }
    /**
     Returns the name of the player
     @return name the player's name
     */
    public String getName() {
        return name;
    }
}     