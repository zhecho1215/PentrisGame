/**
 * The pentomino object
 */
import java.lang.Math;
import java.util.Random;
public class Pentomino {

    private int[][] pentominoMatrix;
    private char identity;
    private int width;
    private int height;

    /**
     * Constructor
     */
    public Pentomino() {
        setRandomShape();
        width = pentominoMatrix[0].length;
        height = pentominoMatrix.length;
    }


    public void rotateMatrixClockwise(){
        int h = pentominoMatrix.length;
        int w = pentominoMatrix[0].length;
        int[][] rotatedPentomino = new int[w][h];


        for(int i = 0; i < h; i++)
        {
            for(int j = 0; j < w; j++)
            {
                rotatedPentomino[j][h-1-i] = pentominoMatrix[i][j];
            }
        }

        pentominoMatrix = rotatedPentomino;
        width = h;
        height = w;
    }


    public void rotateMatrixCounterClockwise(){
        int h = pentominoMatrix.length;
        int w = pentominoMatrix[0].length;
        int[][] rotatedPentomino = new int[w][h];


        for(int i = 0; i < h; i++)
        {
            for(int j = 0; j < w; j++)
            {
                rotatedPentomino[j][i] = pentominoMatrix[i][w-j-1];
            }
        }
        pentominoMatrix = rotatedPentomino;
        width = h;
        height = w;
    }
    /**
     * Accessor method  for the matrix of the pentomino
     * @return the matirx of the pentomino
     */
    public char[][] getObjectMatrix() {
        char[][] result = new char[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                if (pentominoMatrix[i][j] == 1) {
                    result[i][j] = identity;
                } else {
                    result[i][j] = 'E';
                }
            }
        return result;
    }

    public int[][] getPentominoMatrix() {
        return pentominoMatrix;
    }

    /**
     * Accessor method for the width of the pentomio's matrix
     * @return the width of the matrix
     */
    public int getWidth() {
        return width;
    }

    /**
     * Accessor method for the height of the pentomio's matrix
     * @return the height of the matrix
     */
    public int getHeight()
    {
        return height;
    }
    public char getIdentity()
    {
        return identity;
    }
    /**
     * Generates a random pentomino
     */
    public void setRandomShape(){
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 12;
        switch(x){
            case 0: pentominoMatrix = new int[][] { {1, 1}, {1, 1}, {1, 0} }; // P
                identity = 'p';
                break;
/*
Width = 2
Height = 3
11
11
10
*/
            case 1: pentominoMatrix = new int[][] { {0, 1, 0}, {1, 1, 1}, {0, 1, 0} };  // X
                identity = 'x';
                break;
/*
Width = 3
Height = 3
010
111
010
*/
            case 2: pentominoMatrix = new int[][] { {0, 1, 1}, {1, 1, 0}, {0, 1, 0} }; // F
                identity = 'f';
                break;
/*
Width = 3
Height = 3
011
110
010
*/
            case 3: pentominoMatrix = new int[][] { {1, 0, 0}, {1, 0, 0}, {1,1,1} };  // V
                identity = 'v';
                break;
/*
Width = 3
Height = 3
100
100
111
*/
            case 4: pentominoMatrix = new int[][] { {1,0,0}, {1,1,0}, {0,1,1} }; // W
                identity = 'w';
                break;
/*
Width = 3
Height = 3
100
110
011
*/
            case 5: pentominoMatrix = new int[][] { {0,1}, {1,1}, {0,1}, {0,1} };  // Y
                identity = 'y';
                break;
/*
Width = 2
Height = 4
01
11
01
01
*/
            case 6: pentominoMatrix = new int[][] { {1}, {1}, {1}, {1}, {1} }; // I
                identity = 'i';
                break;
/*
Width = 1
Height = 5
1
1
1
1
1
*/
            case 7: pentominoMatrix = new int[][] { {1,1,1}, {0,1,0}, {0,1,0} };  //T
                identity = 't';
                break;
/*
Width = 3
Height = 3
111
010
010
*/
            case 8: pentominoMatrix = new int[][] { {1,1,0}, {0,1,0}, {0,1,1} };  // Z
                identity = 'z';
                break;
/*
Width = 3
Height = 3
110
010
011
*/
            case 9: pentominoMatrix = new int[][] {	{1,0,1}, {1,1,1} }; //U
                identity = 'u';
                break;
/*
Width = 3
Height = 2

101
111
*/
            case 10: pentominoMatrix = new int[][] {	{0,1}, {0,1}, {1,1}, {1,0} };  // N
                identity = 'n';
                break;
/*
Width = 2
Height = 4
01
01
11
10
*/
            case 11: pentominoMatrix = new int[][] {	{1,0}, {1,0}, {1,0}, {1,1} }; // L
                identity = 'l';
                break;
/*
Width = 2
Height = 4
10
10
10
11
*/
        }

    }
}