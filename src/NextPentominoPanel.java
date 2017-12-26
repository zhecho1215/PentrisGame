import javax.swing.*;
import java.awt.*;

public class NextPentominoPanel extends JPanel {

    private Pentomino next;
    private ColoredSquare[][] nextPentSquares;
    private static int SQUARESIZE = 40;

    public NextPentominoPanel() {
        pickNewPentomino();
    }

    public void pickNewPentomino() {
        this.next = new Pentomino();
        nextPentSquares  = new ColoredSquare[next.getWidth()][next.getHeight()];
        paintPentomino();
    }

    public Pentomino getNext() {
        return next;
    }

    /**
     * Paints the JPanel
     * @param g a graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintSquares(g);
    }

    private void paintSquares(Graphics g) {

        for(int row = 0; row < next.getHeight(); row++) {
            for(int col = 0; col < next.getWidth(); col++) {
                nextPentSquares[col][row].draw(g);
            }
        }
    }


    public void paintPentomino()
    {
        char[][] squares = next.getObjectMatrix();


        for (int col = 0; col < squares[0].length; col++) { //each column.
            for (int row = 0; row < squares.length; row++) { //each row.
                if (squares[row][col] == 'E') {
                    nextPentSquares[col][row] = new ColoredSquare(UIManager.getColor ( "Panel.background" )
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'u') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(230, 25, 75)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'f') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(60, 180, 75)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 't') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(0, 130, 48)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'w') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(145, 30, 180)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'p') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(70, 240, 240)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'x') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(240, 50, 230)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'v') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(250, 190, 190)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'y') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(255, 0, 128)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'i') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(230, 190, 255)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'z') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(170, 255, 195)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'l') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(255, 255, 255)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
                if (squares[row][col] == 'n') {
                    nextPentSquares[col][row] = new ColoredSquare(new Color(210, 245, 60)
                            , col * SQUARESIZE, row * SQUARESIZE);
                }
            }
        }
        repaint();
    }

}