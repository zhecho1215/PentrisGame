public class Zaharry {
    private char[][] boardCopy;
    private int[][] evaluationMatrix;
    private double bestScore;
    private char[][] bestState;
    private Pentomino currentPentomino;

    private char[][] boardMidState = new char[BOARD_HEIGHT][BOARD_WIDTH];

    private static int BOARD_HEIGHT = 18;
    private static int BOARD_WIDTH = 5;
    private static int TOP_OF_BOARD= 3;
    private static double MINIMAL_SCORE = - 1000000;
    private static int ISOLATED_CELLS_VALUE = 35;
    private static int CLEARED_LINES_VALUE = 350;

    public Zaharry() {
        bestScore = MINIMAL_SCORE;
        boardCopy = new char[BOARD_HEIGHT][BOARD_WIDTH];
        bestState = new char[BOARD_HEIGHT][BOARD_WIDTH];
        evaluationMatrix = new int[BOARD_HEIGHT][BOARD_WIDTH];

        for (int i = 0; i < TOP_OF_BOARD; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                evaluationMatrix[i][j] = (int)MINIMAL_SCORE/10;
            }
        }

        for (int i = TOP_OF_BOARD; i < evaluationMatrix.length; i++) {
            for (int j = -2; j <= 2; j++) {
                evaluationMatrix[i][j + 2] =  (Math.abs(j) + 20)*(i+10);
            }
        }
    }
    /**
     * The main method of the bot, which evaluates multiple placements of the pentomino that should be placed on the current board
     * The method also takes into consideration the next pentomino, because that makes easier to make the best decision
     * @param currentState matrix which represents the current state of the board
     * @param current the pentomino that should be put on board
     * @param next the pentomino that follows the current
     * @return the best possible state of the board with the current pentomino placed on it
     */
    public char[][] decide(char[][] currentState, Pentomino current, Pentomino next)
    {
        for(int i = 0; i < currentState.length; i++)
            boardCopy[i] = currentState[i].clone();

        currentPentomino = current;
        for (int rotation = 0; rotation < 4; rotation++) {
            currentPentomino.rotateMatrixClockwise();

            for (int placement = 0; placement <= BOARD_WIDTH-currentPentomino.getWidth(); placement++) {

                char [][] pentominoRepresentation = current.getObjectMatrix();

                int lines = computePentominoFall(placement, currentPentomino);

                for (int i = 0; i < pentominoRepresentation.length; i++) {
                    for (int j = 0; j < pentominoRepresentation[0].length; j++) {
                        if(pentominoRepresentation[i][j] != 'E')
                        {
                            boardCopy[i + lines][j + placement] = pentominoRepresentation[i][j];
                        }
                    }
                }

                for(int i = 0; i < BOARD_HEIGHT; i++)
                    boardMidState[i] = boardCopy[i].clone();

                for (int rotationNext = 0; rotationNext < 4; rotationNext++) {
                    next.rotateMatrixClockwise();

                    for (int placementNext = 0; placementNext <= BOARD_WIDTH-next.getWidth(); placementNext++) {

                        char[][] nextRepresentation = next.getObjectMatrix();

                        int linesNext = computePentominoFall(placementNext, next);

                        for (int i = 0; i < nextRepresentation.length; i++) {
                            for (int j = 0; j < nextRepresentation[0].length; j++) {
                                if (nextRepresentation[i][j] != 'E') {
                                    boardCopy[i + linesNext][j + placementNext] = nextRepresentation[i][j];
                                }
                            }
                        }
                        double evaluationScore = evaluateScore();
                        int nrLinesCleared = calculateNrLinesCleared(boardCopy);
                        int nrIsolatedCells = countIsolatedCells(boardCopy);
                        calculateBestScore(evaluationScore, nrLinesCleared, nrIsolatedCells);

                        for(int i = 0; i < BOARD_HEIGHT; i++)
                            boardCopy[i] = boardMidState[i].clone();
                    }
                }
                for(int i = 0; i < BOARD_HEIGHT; i++)
                    boardCopy[i] = currentState[i].clone();
            }
        }

        return bestState;
    }

    /**
     * The method predicts where a pentomino will fall if it is dropped from a certain position
     * @param pentomino the pentomino we want to put on the board
     * @param placement index which represents the 'X' coordinate of the left corner the pentomino
     * @return the number of the lines the pentomino should be moved down
     */
    private int computePentominoFall(int placement, Pentomino pentomino) {
        int minFall = BOARD_HEIGHT -1 ;
        int currentFall;
        for (int i = placement; i < placement + pentomino.getWidth(); i++) {
            int start = pentomino.getHeight();
            while (pentomino.getObjectMatrix()[start - 1][i -placement] == 'E')
            {
                start--;
            }
            currentFall =0;

            do
            {
                if (boardCopy[start][i] != 'E') {

                    if (minFall > currentFall) {

                        minFall = currentFall;
                    }

                    break;
                }
                if(start == BOARD_HEIGHT - 1)
                {
                    if (minFall > currentFall) {

                        minFall = currentFall + 1;
                    }

                    break;
                }
                currentFall++;
                start++;
            }
            while (start < BOARD_HEIGHT);


        }
        return minFall;
    }

    /**
     * Calculates how many lines that are full and will be cleared by the possible placement of the pentomino
     * @param board char matrix with the possible pentomino placed on it
     * @return the number of lines that are full and will be cleared
     */
    private int calculateNrLinesCleared(char[][] board) {
        int linesToClear = 0;
        boolean lineNotFull;
        for (int i = BOARD_HEIGHT - 1; i >= TOP_OF_BOARD; i--) {
            lineNotFull = false;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == 'E') {
                    lineNotFull = true;
                    break;
                }
            }
            if (!lineNotFull) linesToClear++;
        }

        return linesToClear;
    }

    /**
     * Tests whether a cell can be reached by the bot
     * @param x the x coodinate of the cell that is being tested
     * @param y the y coordinate of the cell that is being tested
     * @param board char matrix with the possible pentomino placed on it 
     * @return whether the cell can be reached by the bot 
     */
    private boolean isIsolatedCell(int x, int y, char[][] board){

        for (int i = y-1; i > 2; i--) {
            if (board[i][x] != 'E') {
                return true;
            }
        }
        return false;
    }


    /**
     * Loops through the board and counts the number of empty cells that can't be reached by the bot
     * @param board the char matrix of the game board with the possible pentomino placed on it 
     * @return the number of cells that are empty and can't be reached by the bot 
     */
    private int countIsolatedCells(char[][] board) {
        int tempCount = 0;
        for (int i = TOP_OF_BOARD; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'E' && isIsolatedCell(j,i,board)){
                    tempCount++;
                }
            }
        }
        return tempCount;
    }


    //These weights can be adjusted
    /**
     * Takes the inputed heuristics and calculates the score for that piece, 
     * if the score is better than the best score so far the method makes the
     *  current score and board the the max
     * @param evaluationScore Score based on the evaluation board points
     * @param nrLinesCleared the number of lines that will be cleared by that piece
     * @param nrIsolatedCells the number of cells that can't be reached by the bot
     */
    private void calculateBestScore(double evaluationScore, int nrLinesCleared, int nrIsolatedCells) {

        double currentScore = evaluationScore+CLEARED_LINES_VALUE*nrLinesCleared*nrLinesCleared- ISOLATED_CELLS_VALUE*Math.log(nrIsolatedCells + 1);
        if(bestScore < currentScore)
        {
            for(int i = 0; i < BOARD_HEIGHT; i++)
                bestState[i] = boardMidState[i].clone();

            bestScore = currentScore;
        }
    }

    /**
     * Calculates the score based on the evaluation matrix
     * @return the score obtained form the evaluation matrix
     */
    private double evaluateScore() {
        double boardEvaluationScore =0;
        for (int i = 0; i < evaluationMatrix.length; i++) {
            for (int j = 0; j < evaluationMatrix[0].length; j++) {
                if(boardCopy[i][j] != 'E')
                    boardEvaluationScore += evaluationMatrix[i][j];
            }
        }
        return boardEvaluationScore;
    }
}