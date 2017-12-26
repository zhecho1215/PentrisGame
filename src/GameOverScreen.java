import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOverScreen extends JFrame implements ActionListener {

    private JLabel score;
    private ScoreObject scoreObj;
    private JButton restart;
    private JButton quit;
    private JTextField name;
    private JButton submit;
    private JPanel leaderboardPanel;
    private JPanel mainPanel = new JPanel();
    private Leaderboard leaderboard;
    private boolean restartActionTriggered;

    public GameOverScreen(ScoreObject gameScore) {

        // record the score
        scoreObj = gameScore;

        // add the components of the GameOverScreen
        name = new JTextField();
        score = new JLabel();
        restart = new JButton("Restart");
        quit = new JButton("Quit");
        submit = new JButton("Submit");
        leaderboardPanel = new JPanel();
        score = new JLabel("Score: "+scoreObj.getTotal());
        leaderboardPanel.setLayout(new GridLayout(5,4));
        leaderboard = new Leaderboard(leaderboardPanel);
        leaderboard.updateLeaderboard(leaderboardPanel);
        score.setFont(new Font("Arial", Font.PLAIN, 40));
        restart.setPreferredSize(new Dimension(160, 40));
        restart.setFont(new Font("Arial", Font.PLAIN, 20));
        quit.setPreferredSize(new Dimension(160, 40));
        quit.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setPreferredSize(new Dimension(200, 25));
        submit.setPreferredSize(new Dimension(120, 25));
        submit.setFont(new Font("Arial", Font.PLAIN, 20));
        submit.requestFocus();
        restart.addActionListener(this);
        quit.addActionListener(this);
        submit.addActionListener(this);

        // organise the panels

        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel scorePanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        scorePanel.setPreferredSize(new Dimension(150, 50));
        scorePanel.add(score);
        mainPanel.add(scorePanel);
        panel2.add(restart);
        panel2.add(quit);
        panel3.add(name);
        panel3.add(submit);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        mainPanel.add(leaderboardPanel);

        // set up the frame and display it
        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Game Over");
        this.pack();
    }

    public void setScore(int score) {
        this.score.setText("Score: " + score);
        mainPanel.updateUI();
    }

    // process user choice
    public void actionPerformed(ActionEvent e) {

        String buttonText = ((JButton) e.getSource()).getText();
        switch (buttonText) {
            case "Restart":
                restartActionTriggered = true;
                this.setVisible(false);
                submit.setEnabled(true);
                break;
            case "Submit":
                scoreObj.insertName(name.getText());
                leaderboard.inScore(scoreObj);
                submit.setEnabled(false);
                break;
            case "Quit":
                System.exit(0); // otherwise exit

            default:

                break;
        }
    }

    public boolean getRestartActionTriggered ()
    {
        return restartActionTriggered;
    }

    public void setRestartActionTriggered(boolean restartActionTriggered) {
        this.restartActionTriggered = restartActionTriggered;
    }
}