/**
 * Creates the panel and does all computations of the game
 *
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Game extends JPanel implements ActionListener,  KeyListener{

    private static final int DELAY = 500; // Millisecs between timer ticks
    private static final int BOARDWIDTH = 5;
    private static final int BOARDHEIGHT = 18;
    private static final int TOP_OF_THE_BOARD = 3;
    private static final int SQUARESIZE = 50;
    private Pentomino currentPent;
    private int currentX = 0;
    private int currentY = 0;
    private char[][] gameBoard = new char[BOARDHEIGHT][BOARDWIDTH];
    private ColoredSquare[][] squares;
    private boolean[][] finishedFalling;
    private GameOverScreen gameOverScreen;
    private boolean gameOverWindowIsOpened = false;
    private boolean gameGoingAllowed = true;
    private NextPentominoPanel nextPentominoPanel;
    private ScoreObject score;
    private int linesCleared = 0;
    private boolean isZaharryPlaying = false;
    private boolean shouldClearLines = false;

    public ScoreObject getScoreObject() {
        return score;
    }

    public NextPentominoPanel getNextPentPanel() {
        return nextPentominoPanel;
    }
    /**
     * Constructor
     */
    public Game(){
        score = new ScoreObject();
        gameOverScreen = new GameOverScreen(score);
        nextPentominoPanel = new NextPentominoPanel();
        initializeValuesOfBoard();
    }

    private void initializeValuesOfBoard() {
        score.resetScore();
        finishedFalling = new boolean[BOARDHEIGHT][BOARDWIDTH];
        squares = new ColoredSquare[BOARDWIDTH][BOARDHEIGHT];
        for (int i = 0; i < BOARDHEIGHT; i++)
            for (int j = 0; j < BOARDWIDTH; j++) {
                gameBoard[i][j] = 'E';
            }
    }

    void run(){

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer timer = new Timer(DELAY, this);
        timer.start();

        if(!isZaharryPlaying){
            placeNewPent();
        }
        populateSquares();
    }

    /**
     * Prints the gameBoard matrix in the output window
     * Will be deleted for the final program
     */
    private void PrintMatrix() {
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                System.out.print(gameBoard[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void upKey() {
        System.out.println("UP KEY PRESSED");
        removePentominoFromBoard();

        currentPent.rotateMatrixClockwise();

        if(putPentominoOnBoardPossible() == false)
        {
            currentPent.rotateMatrixCounterClockwise();//return the pentomino to the initial state
        }
        putPentomino();

    }
    /**
     * The method checks whether it's possible to place the pentomino on the board
     */
    private boolean putPentominoOnBoardPossible(){
        int temporalX = currentX;
        int[][] currentRotation = currentPent.getPentominoMatrix();
        while (currentX >= 0)
        {
            if(!canBePutOnBoard(currentRotation))
            {
                currentX--;
            }
            else
            {
                return true;
            }
        }
        currentX = temporalX;
        return false;
    }

    private void putPentomino() {
        int objectX = 0, objectY = 0;
        for (int i = currentX; i < currentX + currentPent.getWidth(); i++) {
            for (int j = currentY+1 - currentPent.getHeight() ; j < currentY+1; j++ ) {
                if(gameBoard[j][i] == 'E' || !finishedFalling[j][i])
                {
                    gameBoard[j][i] = currentPent.getObjectMatrix()[objectX][objectY];
                }

                objectX++;
            }
            objectY++;
            objectX = 0;
        }
        populateSquares();
    }

    private boolean canBePutOnBoard(int [][] rotated)
    {
        if( currentX + rotated[0].length > 5 ) {return false;}

        for (int i = currentX;  i < currentX + rotated[0].length; i++) {
            for (int j = currentY+1 - rotated.length ; j < currentY+1; j++ ) {
                if (j<0 ||  gameBoard[j][i] != 'E' || finishedFalling[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void removePentominoFromBoard() {
        for(int i = currentX; i < currentX + currentPent.getWidth();i++) {
            for(int j = currentY; j > currentY - currentPent.getHeight(); j--) {
                if(gameBoard[j][i]==currentPent.getIdentity() && !finishedFalling[j][i])
                    gameBoard[j][i]='E';
            }

        }
        populateSquares();
    }


    private void downKey() {
        System.out.println("DOWN KEY PRESSED");
        removePentominoFromBoard();

        currentPent.rotateMatrixCounterClockwise();
        if(putPentominoOnBoardPossible() == false)
        {
            currentPent.rotateMatrixClockwise();
        }

        putPentomino();

    }

    /**
     * Moves the falling piece one unit to the left if possible
     */
    private void leftKey() {
        if (moveLeftIsPossible(currentX-1)){
            for (int i = currentX; i <= currentX+currentPent.getWidth()-1; i++) {
                for (int j = currentY-currentPent.getHeight(); j <= currentY; j++) {
                    if (gameBoard[j][i-1] == 'E' && !finishedFalling[j][i]) {
                        gameBoard[j][i-1] = gameBoard[j][i];
                        gameBoard[j][i] = 'E';
                    }

                }
            }
            currentX--;
            populateSquares();
        }
    }

    /**
     * Moves the falling piece one unit to the right if possible
     */
    private void rightKey() {
        if (moveRightIsPossible(currentX+currentPent.getWidth())) {

            for (int i = currentX+currentPent.getWidth()-1; i >= currentX; i--) {
                for (int j = currentY-currentPent.getHeight(); j <= currentY; j++) {
                    if (gameBoard[j][i+1] == 'E' && !finishedFalling[j][i]) {
                        gameBoard[j][i+1] = gameBoard[j][i];
                        gameBoard[j][i] = 'E';
                    }

                }
            }
            currentX++;
            populateSquares();
        }
    }

    /**
     * Moves the piece to the place it is finished falling
     */
    private void spaceKey() {
        while (!isFinishedFalling())
        {
            downOne();
        }
    }

    /**
     * Moves the falling piece down one unit to simulate falling
     */
    private void downOne() {

        for (int i = currentY; i >= 0; i--)
            for (int j = currentX; j <= currentX+currentPent.getWidth()-1; j++) {
                if (gameBoard[i + 1][j] == 'E' && !finishedFalling[i][j]) {
                    gameBoard[i + 1][j] = gameBoard[i][j];
                    gameBoard[i][j] = 'E';
                }

            }

        populateSquares();
        currentY++;
        PrintMatrix();
    }

    /**
     * checks for squares that are above the playfield, i.e. the game is over
     * @return false if all squares above the actual game field are empty(the should not be over)
     */
    private boolean gameShouldBeOver() {
        for (int j = 0; j < BOARDWIDTH; j++) {
            if (gameBoard[TOP_OF_THE_BOARD-1][j] != 'E') {
                return true;
            }
        }

        return false;
    }

    /**
     * Tests if it is possible to move the piece one unit to the left
     * @param testX the x coordinate that the pentomino will use as the currentX if the move is possible
     * @return whether the move is possible
     */
    private boolean moveLeftIsPossible(int testX) {
        if (testX < 0) {
            return false;
        }

        char[][] pentMatrix = currentPent.getObjectMatrix();
        int tempX = 0;
        int tempY = 0;

        for (int i = currentY-currentPent.getHeight()+1; i <= currentY; i++) {

            while (pentMatrix[tempY][tempX] == 'E') {
                tempX++;
            }
            if (gameBoard[i][currentX+tempX-1] != 'E') {
                return false;
            }
            tempY++;
            tempX = 0;
        }
        return true;
    }

    /**
     * Tests if it is possible to move the piece one unit to the right
     * @param testX the x coordinate that the pentomino will use as the currentX if the move is possible
     * @return whether the move is possible
     */
    private boolean moveRightIsPossible(int testX) {
        if (testX >= BOARDWIDTH) {
            return false;
        }

        char[][] pentMatrix = currentPent.getObjectMatrix();
        int tempX = currentPent.getWidth()-1;
        int tempY = 0;

        for (int i = currentY-currentPent.getHeight()+1; i <= currentY; i++) {
            while (pentMatrix[tempY][tempX] == 'E') {
                tempX--;
            }
            if (gameBoard[i][currentX+tempX+1] != 'E') {
                return false;
            }
            tempY++;
            tempX = currentPent.getWidth() -1;
        }

        return true;
    }

    /**
     * Test whether the falling piece has encountered an obstacle (another pentomino or the end of the board)
     * @return whether the falling piece has finished falling
     */
    private boolean isFinishedFalling() {
        char[][] pentMatrix = currentPent.getObjectMatrix();
        int tempY = currentPent.getHeight() -1;
        int tempX = 0;
        if (currentY+1 >= BOARDHEIGHT) {
            return true;
        }
        else {
            for (int i = currentX; i < currentX+currentPent.getWidth(); i++) {
                while (pentMatrix[tempY][tempX] == 'E') {
                    tempY--;
                }
                int checkY = currentPent.getHeight()-1-tempY;
                if (gameBoard[currentY-checkY+1][currentX+tempX] != 'E') {
                    return true;
                }
                tempX++;
                tempY = currentPent.getHeight() -1;
            }
        }
        return false;
    }

    /**
     * Updates the finished falling matrix
     */
    private void fillFinishedFallingBooleanMatrix() {
        for (int i = 0; i < BOARDHEIGHT; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                finishedFalling[i][j] = gameBoard[i][j] != 'E';
            }
        }
    }

    /**
     * Places a new pentomino onto the game board
     */
    private void placeNewPent() {

        currentPent = nextPentominoPanel.getNext();
        currentX = currentPent.getWidth()-1;
        currentY = currentPent.getHeight()-1;
        int objectX = 0;
        int objectY = 0;
        for (int i = currentX; i < currentX + currentPent.getWidth(); i++) {
            for (int j = 0; j <= currentY; j++) {
                gameBoard[j][i] = currentPent.getObjectMatrix()[objectY][objectX];
                objectY++;
            }
            objectX++;
            objectY = 0;
        }
        nextPentominoPanel.pickNewPentomino();
    }



    /**
     * Makes a matrix of ColoredSquare objects and paints on the JPanel
     */
    private void populateSquares() {

        for (int col = 0; col < squares.length; col++) { //each column.
            for (int row = TOP_OF_THE_BOARD; row < squares[0].length; row++) { //each row.
                if (gameBoard[row][col] == 'E') {
                    squares[col][row] = new ColoredSquare(new Color(0, 0, 0)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'u') {
                    squares[col][row] = new ColoredSquare(new Color(230, 25, 75)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'f') {
                    squares[col][row] = new ColoredSquare(new Color(60, 180, 75)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 't') {
                    squares[col][row] = new ColoredSquare(new Color(0, 130, 48)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'w') {
                    squares[col][row] = new ColoredSquare(new Color(145, 30, 180)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'p') {
                    squares[col][row] = new ColoredSquare(new Color(70, 240, 240)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'x') {
                    squares[col][row] = new ColoredSquare(new Color(240, 50, 230)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'v') {
                    squares[col][row] = new ColoredSquare(new Color(250, 190, 190)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'y') {
                    squares[col][row] = new ColoredSquare(new Color(255, 0, 128)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'i') {
                    squares[col][row] = new ColoredSquare(new Color(230, 190, 255)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'z') {
                    squares[col][row] = new ColoredSquare(new Color(170, 255, 195)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'l') {
                    squares[col][row] = new ColoredSquare(new Color(255, 255, 255)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
                if (gameBoard[row][col] == 'n') {
                    squares[col][row] = new ColoredSquare(new Color(210, 245, 60)
                            ,col * SQUARESIZE, row * SQUARESIZE);
                }
            }
        }
        repaint();
    }

    /**
     * Paints the squares of the matrix that are within the game board dimensions
     * @param g a graphics object
     */
    private void paintSquares(Graphics g) {

        for(int row = TOP_OF_THE_BOARD; row < BOARDHEIGHT; row++) {
            for(int col = 0; col < BOARDWIDTH; col++) {
                squares[col][row].draw(g);
            }
        }
    }

    /**
     * Paints the JPanel
     * @param g a graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintSquares(g);
    }

    /**
     * The key listener
     * @param e a KeyEvent object
     */
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_UP) {
            upKey();
        }
        if (code == KeyEvent.VK_DOWN) {
            downKey();
        }
        if (code == KeyEvent.VK_LEFT) {
            leftKey();
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightKey();
        }
        if (code == KeyEvent.VK_SPACE) {
            spaceKey();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if(isZaharryPlaying)
        {
            if(gameOverScreen.getRestartActionTriggered()) {
                initializeValuesOfBoard();
                score.updateScore();
                gameOverScreen.setRestartActionTriggered(false);
            }
                Zaharry harry = new Zaharry();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if (gameShouldBeOver())
            {
                gameOverScreen.setVisible(true);
                gameOverScreen.setScore(score.getTotal());
            }
            else {
                if(!shouldClearLines)
                {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    currentPent = nextPentominoPanel.getNext();

                    nextPentominoPanel.pickNewPentomino();
                    char[][] newState = harry.decide(gameBoard,currentPent, nextPentominoPanel.getNext());

                    for(int i = 0; i < BOARDHEIGHT; i++)
                        gameBoard[i] = newState[i].clone();

                    shouldClearLines =true;

                }
                else {
                    clearLines();
                    shouldClearLines = false;
                }

                populateSquares();

            }
        }

        else {
            if(gameOverScreen.getRestartActionTriggered())
            {
                initializeValuesOfBoard();
                score.updateScore();
                gameOverWindowIsOpened = false;
                gameGoingAllowed = true;
                gameOverScreen.setRestartActionTriggered(false);
                placeNewPent();
            }

            if (isFinishedFalling())
            {
                if(gameGoingAllowed) {
                    if (gameShouldBeOver() && !gameOverWindowIsOpened) {
                        gameOverWindowIsOpened = true;

                        gameOverScreen.setVisible(true);
                        gameOverScreen.setScore(score.getTotal());

                        gameGoingAllowed = false;

                    } else {
                        clearLines();
                        fillFinishedFallingBooleanMatrix();
                        placeNewPent();
                    }
                }
            }
            else downOne();
        }
    }

    /**
     * Clears the lines of the matrix that are full
     */
    private void clearLines() {
        int elementsOfRow = 0;
        for (int i = 0; i < BOARDHEIGHT; i++) {
            for (int j = 0; j < BOARDWIDTH; j++) {
                if (gameBoard[i][j] != 'E') {
                    elementsOfRow++;
                }
                if (elementsOfRow == 5) {
                    linesCleared++;
                    gameBoard = fall(i, gameBoard);
                }
            }
            elementsOfRow = 0;
        }
        if (linesCleared !=0)
        {
            score.addScore(linesCleared);
            linesCleared = 0;
            score.updateScore();
        }
    }

    /**
     * Moves all rows of the matrix above a cleared line down
     * @param row the row that is deleted
     * @param board the original game board
     * @return the updated game board
     */
    private char[][] fall(int row, char[][] board) {
        // Goes through c checking for rows that are all zero
        for (int k = row; k > 0; k--) {
            for (int l = 0; l < board[0].length; l++) {
                board[k][l] = board[k - 1][l];
            }
        }
        // Sets first row elements equal to zero
        for (int m = 0; m < board[0].length; m++) {
            board[0][m] = 'E';
        }

        return board;
    }

    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}