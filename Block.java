import java.awt.*;
import java.awt.geom.*;

public class Block
{
    public static final int SIZE = 15;
    private RectangularShape shape;
    private Color color;
    private boolean active;
    private TetrisPanel panel;

    Block(double x, double y, Color color, TetrisPanel panel)
    {
        shape = new Rectangle2D.Double(x, y, SIZE, SIZE);
        this.color = color;
        active = true;
        this.panel = panel;
    }


    protected boolean isActive() {
        return active;
    }

    protected double getX() {
        return shape.getX();
    }

    protected double getY() {
        return shape.getY();
    }

    protected void display()
    {
        if (! active)
        {
            active = true;
        }
    }

    protected void hide()
    {
        if (active)
        {
            active = false;
        }
    }

    protected boolean intersects(Block other)
    {
        if (active)
        {
            return shape.intersects((Rectangle2D.Double) other.shape);
        } else
        {
            //if the block is hidden
            return false;
        }
    }

    public void moveLeft()
    {
        shape.setFrame(shape.getX() - SIZE, shape.getY(), shape.getWidth(), shape.getHeight());
    }

    public void moveRight()
    {
        shape.setFrame(shape.getX() + SIZE, shape.getY(), shape.getWidth(), shape.getHeight());
    }

    public void moveDown()
    {
        shape.setFrame(shape.getX(), shape.getY() + shape.getHeight(), shape.getWidth(), shape.getHeight());
    }

    public boolean isNotAtScreenBottom()
    {
        //if the block is not already at the bottom of the screen
        return (shape.getMaxY() + shape.getHeight()) <= panel.getHeight();
    }

    public boolean isNotAtScreenLeftLimit()
    {
        //if the block is not already at the left edge of the screen
        return shape.getMinX() - shape.getWidth() >= 0;
    }

    public boolean isNotAtScreenRightLimit()
    {
        //if the block is not already at the right edge of the screen
        return shape.getMaxX() + shape.getWidth() <= panel.getWidth();
    }

    public void draw(Graphics2D g2)
    {
        //if its not hidden
        if (active)
        {
            g2.setColor(color);
            g2.fill(shape);
        }
    }
}
