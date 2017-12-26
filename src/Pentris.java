import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pentris {

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 1000;

    private static final int GAME_PANEL_WIDTH = 250;
    private static final int GAME_PANEL_HEIGHT = 1000;
    private static final int GAME_PANEL_LOCATION_X = 50;
    private static final int GAME_PANEL_LOCATION_Y = -100;

    private static final int NEXT_PENTOMINO_PANEL_WIDTH = 250;
    private static final int NEXT_PENTOMINO_PANEL_HEIGHT = 250;
    private static final int NEXT_PENTOMINO_PANEL_LOCATION_X = 400;
    private static final int NEXT_PENTOMINO_PANEL_LOCATION_Y = 500;

    private static final int SCORE_PANEL_WIDTH = 300;
    private static final int SCORE_PANEL_HEIGHT = 300;
    private static final int SCORE_PANEL_LOCATION_X = 250;
    private static final int SCORE_PANEL_LOCATION_Y = 200;

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame();

        Game b = new Game();
        b.setLocation(GAME_PANEL_LOCATION_X, GAME_PANEL_LOCATION_Y);
        b.setSize(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT);
        b.run();

        JPanel scorePanel = new JPanel();
        b.getScoreObject().attachScore(scorePanel);
        scorePanel.setLocation(SCORE_PANEL_LOCATION_X, SCORE_PANEL_LOCATION_Y);
        scorePanel.setSize(SCORE_PANEL_WIDTH, SCORE_PANEL_HEIGHT);

        NextPentominoPanel nextPentominoPanel = b.getNextPentPanel();
        nextPentominoPanel.setLocation(NEXT_PENTOMINO_PANEL_LOCATION_X, NEXT_PENTOMINO_PANEL_LOCATION_Y);
        nextPentominoPanel.setSize(NEXT_PENTOMINO_PANEL_WIDTH, NEXT_PENTOMINO_PANEL_HEIGHT);

        frame.setLayout(null);
        frame.add(b);
        frame.add(scorePanel);
        frame.add(nextPentominoPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        frame.setVisible(true);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }
}