import java.awt.*;
import java.util.ArrayList;

public abstract class TetrisShape {
    public static final int SIZE = 15;
    public static final int START_X = 3;    //start middle of the screen
    public static final int START_Y = 1;    //start at row 1 so it doesn't overlap the score message
    public static final int MAX_ROWS = 4;
    public static final int MAX_COLUMNS = 4;

    protected Block[][] space;
    protected Color color;
    protected TetrisPanel panel;

    TetrisShape(Color color, TetrisPanel panel) {
        space = new Block[MAX_ROWS][MAX_COLUMNS];
        this.color = color;
        this.panel = panel;
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                space[row][column] = new Block(((row + START_X) * SIZE), ((column + START_Y) * SIZE), color, panel);
                space[row][column].hide();
            }
        }

        displayRandomRotation();
    }

    protected abstract void displayRotationOne();

    protected abstract boolean isRotationOneActive();

    protected abstract void displayRotationTwo();

    protected abstract boolean isRotationTwoActive();

    protected abstract void displayRotationThree();

    protected abstract boolean isRotationThreeActive();

    protected abstract void displayRotationFour();

    protected abstract boolean isRotationFourActive();

    public abstract void nextRotation();

    protected void displayRandomRotation() {
        double randomDouble = Math.random();
        if (randomDouble < 0.25) {
            displayRotationOne();
        } else if (randomDouble < 0.5) {
            displayRotationTwo();
        } else if (randomDouble < 0.75) {
            displayRotationThree();
        } else {
            displayRotationFour();
        }
    }

    public void previousRotation() {
        if (isRotationOneActive()) {
            hide();
            displayRotationFour();
        } else if (isRotationTwoActive()) {
            hide();
            displayRotationOne();
        } else if (isRotationThreeActive()) {
            hide();
            displayRotationTwo();
        } else if (isRotationFourActive()) {
            hide();
            displayRotationThree();
        }
    }

    protected void hide() {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                space[row][column].hide();
            }
        }
    }

    public boolean isNotAtScreenBottom() {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                //only check those blocks that aren't hidden
                if (space[row][column].isActive()) {
                    //if one of the bricks is at bottom of the screen
                    if (! space[row][column].isNotAtScreenBottom()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean isNotAtScreenBottom(ArrayList<Block> virtualBlocks) {
        for (Block block : virtualBlocks) {
            //only check those blocks that aren't hidden
            if (block.isActive()) {
                //if one of the bricks is at the bottom of the screen
                if (! block.isNotAtScreenBottom()) {
                    return false;
                }
            }
        }
        //no bricks on the virtual down shape are at the bottom
        return true;
    }

    public boolean isNotAtScreenLeftLimit() {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                //only check those blocks that aren't hidden
                if (space[row][column].isActive()) {
                    //if one of the bricks is at the screen left limit
                    if (! space[row][column].isNotAtScreenLeftLimit()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean isNotAtScreenLeftLimit(ArrayList<Block> virtualBlocks) {
        for (Block block : virtualBlocks) {
            //only check those blocks that aren't hidden
            if (block.isActive()) {
                //if one of the bricks is at the screen left limit
                if (! block.isNotAtScreenLeftLimit()) {
                    return false;
                }
            }
        }
        //no bricks on the virtual left shape are at the screen left limit
        return true;
    }

    public boolean isNotAtScreenRightLimit() {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                //only check those blocks that aren't hidden
                if (space[row][column].isActive()) {
                    //if one of the bricks is at the screen right limit
                    if (! space[row][column].isNotAtScreenRightLimit()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected boolean isNotAtScreenRightLimit(ArrayList<Block> virtualBlocks) {
        for (Block block : virtualBlocks) {
            //only check those blocks that aren't hidden
            if (block.isActive()) {
                //if one of the bricks is at the screen right limit
                if (! block.isNotAtScreenRightLimit()) {
                    return false;
                }
            }
        }
        //no bricks on the virtual right shape are at the screen right limit
        return true;
    }

    public void moveLeft() {
        if (isNotAtScreenLeftLimit()) {
            for (int row = 0; row < MAX_ROWS; row++) {
                for (int column = 0; column < MAX_COLUMNS; column++) {
                    space[row][column].moveLeft();
                }
            }
        }
    }

    public void moveRight() {
        if (isNotAtScreenRightLimit()) {
            for (int row = 0; row < MAX_ROWS; row++) {
                for (int column = 0; column < MAX_COLUMNS; column++) {
                    space[row][column].moveRight();
                }
            }
        }
    }

    public void moveDown() {
        if (isNotAtScreenBottom()) {
            for (int row = 0; row < MAX_ROWS; row++) {
                for (int column = 0; column < MAX_COLUMNS; column++) {
                    space[row][column].moveDown();
                }
            }
        }
    }

    //if one block from this shape intercepts any other block
    public boolean intercepts(Block other) {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                if (space[row][column].isActive()) {
                    if (space[row][column].intersects(other)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<Block> virtualDownPosition() {
        ArrayList<Block> virtualBlocks = activeBlocks();
        for (Block block : virtualBlocks) {
            block.moveDown();
        }
        return virtualBlocks;
    }

    public ArrayList<Block> virtualLeftPosition() {
        ArrayList<Block> virtualBlocks = activeBlocks();
        for (Block block : virtualBlocks) {
            block.moveLeft();
        }
        return virtualBlocks;
    }

    public ArrayList<Block> virtualRightPosition() {
        ArrayList<Block> virtualBlocks = activeBlocks();
        for (Block block : virtualBlocks) {
            block.moveRight();
        }
        return virtualBlocks;
    }

    public ArrayList<Block> virtualNextRotation() {
        nextRotation();
        ArrayList<Block> virtualBlocks = activeBlocks();
        previousRotation();
        return virtualBlocks;
    }

    //returns only the blocks that are active in the shape
    public ArrayList<Block> activeBlocks() {
        ArrayList<Block> activeBlocks = new ArrayList<Block>();
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                if (space[row][column].isActive()) {
                    activeBlocks.add(new Block(space[row][column].getX(), space[row][column].getY(), color, panel));
                }
            }
        }
        return activeBlocks;
    }

    public void draw(Graphics2D g2) {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int column = 0; column < MAX_COLUMNS; column++) {
                space[row][column].draw(g2);
            }
        }
    }

}
