import java.awt.Color;

public class TetrisShapeO extends TetrisShape
{

    TetrisShapeO(Color color, TetrisPanel panel)
    {
        super(color, panel);
    }

    protected void displayRotationOne()
    {
        space[1][1].display();
        space[2][1].display();
        space[1][2].display();
        space[2][2].display();
    }

    protected boolean isRotationOneActive()
    {
        return (space[1][1].isActive() && space[2][1].isActive() && space[1][2].isActive() && space[2][2].isActive());
    }

    protected void displayRotationTwo()
    {
        space[1][1].display();
        space[2][1].display();
        space[1][2].display();
        space[2][2].display();
    }

    protected boolean isRotationTwoActive()
    {
        return (space[1][1].isActive() && space[2][1].isActive() && space[1][2].isActive() && space[2][2].isActive());
    }

    protected void displayRotationThree()
    {
        space[1][1].display();
        space[2][1].display();
        space[1][2].display();
        space[2][2].display();
    }

    protected boolean isRotationThreeActive()
    {
        return (space[1][1].isActive() && space[2][1].isActive() && space[1][2].isActive() && space[2][2].isActive());
    }

    protected void displayRotationFour()
    {
        space[1][1].display();
        space[2][1].display();
        space[1][2].display();
        space[2][2].display();
    }

    protected boolean isRotationFourActive()
    {
        return (space[1][1].isActive() && space[2][1].isActive() && space[1][2].isActive() && space[2][2].isActive());
    }

    public void nextRotation()
    {
        if (isRotationOneActive())
        {
            hide();
            displayRotationTwo();
        } else if (isRotationTwoActive())
        {
            hide();
            displayRotationThree();
        } else if (isRotationThreeActive())
        {
            hide();
            displayRotationFour();
        } else if (isRotationFourActive())
        {
            hide();
            displayRotationOne();
        }
    }

}
