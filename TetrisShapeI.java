import java.awt.Color;

public class TetrisShapeI extends TetrisShape
{

    TetrisShapeI(Color color, TetrisPanel panel)
    {
        super(color, panel);
    }

    protected void displayRotationOne()
    {
        space[0][1].display();
        space[1][1].display();
        space[2][1].display();
        space[3][1].display();
    }

    protected boolean isRotationOneActive()
    {
        return (space[0][1].isActive() && space[1][1].isActive() && space[2][1].isActive() && space[3][1].isActive());
    }

    protected void displayRotationTwo()
    {
        space[2][0].display();
        space[2][1].display();
        space[2][2].display();
        space[2][3].display();
    }

    protected boolean isRotationTwoActive()
    {
        return (space[2][0].isActive() && space[2][1].isActive() && space[2][2].isActive() && space[2][3].isActive());
    }

    protected void displayRotationThree()
    {
        space[0][2].display();
        space[1][2].display();
        space[2][2].display();
        space[3][2].display();
    }

    protected boolean isRotationThreeActive()
    {
        return (space[0][2].isActive() && space[1][2].isActive() && space[2][2].isActive() && space[3][2].isActive());
    }

    protected void displayRotationFour()
    {
        space[1][0].display();
        space[1][1].display();
        space[1][2].display();
        space[1][3].display();
    }

    protected boolean isRotationFourActive()
    {
        return (space[1][0].isActive() && space[1][1].isActive() && space[1][2].isActive() && space[1][3].isActive());
    }

    public void nextRotation()
    {
        if (isRotationOneActive())
        {
            //if the current shape is not at the bottom of the screen AND the virtual down position is not at the bottom either
            if (isNotAtScreenBottom() && isNotAtScreenBottom(virtualDownPosition()))
            {
                hide();
                displayRotationTwo();
            }
        } else if (isRotationTwoActive())
        {
            if (isNotAtScreenLeftLimit() && isNotAtScreenRightLimit() && isNotAtScreenLeftLimit(virtualLeftPosition())) {
                hide();
                displayRotationThree();
            }
        } else if (isRotationThreeActive())
        {
            if (isNotAtScreenBottom())
            {
                hide();
                displayRotationFour();
            }
        } else if (isRotationFourActive())
        {
            if (isNotAtScreenLeftLimit() && isNotAtScreenRightLimit() && isNotAtScreenRightLimit(virtualRightPosition())) {
                hide();
                displayRotationOne();
            }
        }
    }

}

