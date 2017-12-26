/**
 * The colored square object that is used to print the JPanel
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ColoredSquare{

    private Color color;
    private Rectangle rect;
    private int width;
    private int height;

    /**
     * Constructor
     */
    public ColoredSquare(Color c, int x, int y) {
        color = c;
        width = 45;
        height = 45;
        rect = new Rectangle(x,y,width,height);
    }

    /**
     * Draws the single colored square for the colored square matrix
     * @param g a graphics object
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(rect.x, rect.y, width, height);
    }
}