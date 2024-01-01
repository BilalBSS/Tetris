import java.awt.Color;

public class TetrisShapeJ extends TetrisShape
{

    TetrisShapeJ(Color color, TetrisPanel panel)
    {
        super(color, panel);
    }

    protected void displayRotationOne()
    {
        space[2][0].display();
        space[2][1].display();
        space[2][2].display();
        space[1][2].display();
    }

    protected boolean isRotationOneActive()
    {
        return (space[2][0].isActive() && space[2][1].isActive() && space[2][2].isActive() && space[1][2].isActive());
    }

    protected void displayRotationTwo()
    {
        space[1][1].display();
        space[1][2].display();
        space[2][2].display();
        space[3][2].display();
    }

    protected boolean isRotationTwoActive()
    {
        return (space[1][1].isActive() && space[1][2].isActive() && space[2][2].isActive() && space[3][2].isActive());
    }

    protected void displayRotationThree()
    {
        space[2][1].display();
        space[1][1].display();
        space[1][2].display();
        space[1][3].display();
    }

    protected boolean isRotationThreeActive()
    {
        return (space[2][1].isActive() && space[1][1].isActive() && space[1][2].isActive() && space[1][3].isActive());
    }

    protected void displayRotationFour()
    {
        space[0][1].display();
        space[1][1].display();
        space[2][1].display();
        space[2][2].display();
    }

    protected boolean isRotationFourActive()
    {
        return (space[0][1].isActive() && space[1][1].isActive() && space[2][1].isActive() && space[2][2].isActive());
    }

    public void nextRotation()
    {
        if (isRotationOneActive())
        {
            if (isNotAtScreenRightLimit())
            {
                hide();
                displayRotationTwo();
            }
        } else if (isRotationTwoActive())
        {
            if (isNotAtScreenBottom())
            {
                hide();
                displayRotationThree();
            }
        } else if (isRotationThreeActive())
        {
            if (isNotAtScreenLeftLimit())
            {
                hide();
                displayRotationFour();
            }
        } else if (isRotationFourActive())
        {
            hide();
            displayRotationOne();
        }
    }

}
