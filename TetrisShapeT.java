import java.awt.Color;

public class TetrisShapeT extends TetrisShape
{

    TetrisShapeT(Color color, TetrisPanel panel)
    {
        super(color, panel);
    }

    protected void displayRotationOne()
    {
        space[1][1].display();
        space[2][1].display();
        space[3][1].display();
        space[2][2].display();
    }

    protected boolean isRotationOneActive()
    {
        return (space[1][1].isActive() && space[2][1].isActive() && space[3][1].isActive() && space[2][2].isActive());
    }

    protected void displayRotationTwo()
    {
        space[2][0].display();
        space[1][1].display();
        space[2][1].display();
        space[2][2].display();
    }

    protected boolean isRotationTwoActive()
    {
        return (space[2][0].isActive() && space[1][1].isActive() && space[2][1].isActive() && space[2][2].isActive());
    }

    protected void displayRotationThree()
    {
        space[2][0].display();
        space[1][1].display();
        space[2][1].display();
        space[3][1].display();
    }

    protected boolean isRotationThreeActive()
    {
        return (space[2][0].isActive() && space[1][1].isActive() && space[2][1].isActive() && space[3][1].isActive());
    }

    protected void displayRotationFour()
    {
        space[2][0].display();
        space[2][1].display();
        space[3][1].display();
        space[2][2].display();
    }

    protected boolean isRotationFourActive()
    {
        return (space[2][0].isActive() && space[2][1].isActive() && space[3][1].isActive() && space[2][2].isActive());
    }

    public void nextRotation()
    {
        if (isRotationOneActive())
        {
            //if the current shape is not at the bottom of the screen
            if (isNotAtScreenBottom())
            {
                hide();
                displayRotationTwo();
            }
        } else if (isRotationTwoActive())
        {
            if (isNotAtScreenRightLimit())
            {
                hide();
                displayRotationThree();
            }
        } else if (isRotationThreeActive())
        {
            //if the current shape is not at the bottom of the screen or the row before
            if (isNotAtScreenBottom() && isNotAtScreenBottom(virtualDownPosition()))
            {
                hide();
                displayRotationFour();
            }
        } else if (isRotationFourActive())
        {
            if (isNotAtScreenLeftLimit())
            {
                hide();
                displayRotationOne();
            }
        }
    }

}
