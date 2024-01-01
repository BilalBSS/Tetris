import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.*;

public class TetrisPanel extends JPanel
{
    public static final int MAX_ROWS = 40;
    public static final int MAX_COLUMNS = 10;

    //every time a line is completed
    public static final int SCORE_LINE_COMPLETED = 1000;
    //every time a shape is dropped
    public static final int SCORE_SHAPE_PLACED = 10;

    javax.swing.Timer timer;
    int timerDelay;
    ArrayList<Block> blocks;
    TetrisShape shape;
    String endGameMessage;
    int score;
    int level;
    int completedLines;

    TetrisPanel()
    {
        setFocusable(true);

        addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                {

                    if (! intercepts(shape.virtualLeftPosition()))
                    {
                        shape.moveLeft();
                        ifLineIsComplete();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                {

                    if (! intercepts(shape.virtualRightPosition()))
                    {
                        shape.moveRight();
                        ifLineIsComplete();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    if (! intercepts(shape.virtualDownPosition()))
                    {
                        shape.moveDown();
                        ifLineIsComplete();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                {

                    if (! intercepts(shape.virtualNextRotation()))
                    {
                        shape.nextRotation();

                        ifLineIsComplete();
                    }
                }
                repaint();
            }
        });

        startGame();
    }

    private void startGame()
    {
        timerDelay = 1000;  //1 second (speed of the falling shape)
        timer = new javax.swing.Timer(timerDelay, new TimeListener());
        blocks = new ArrayList<Block>();
        endGameMessage = "";
        score = 0;
        completedLines = 0;
        level = 1;

        shape = createRandomShape();

        timer.start();
    }

    private TetrisShape createRandomShape()
    {
        //create a new shape to fall from the top
        TetrisShape fallingShape = null;
        double randomDouble = Math.random();

        if (randomDouble < 0.145)
        {
            fallingShape = new TetrisShapeO(Color.RED, this);
        } else if (randomDouble < 0.29)
        {
            fallingShape = new TetrisShapeT(Color.BLUE, this);
        } else if (randomDouble < 0.435)
        {
            fallingShape = new TetrisShapeL(Color.PINK, this);
        } else if (randomDouble < 0.58)
        {
            fallingShape = new TetrisShapeJ(Color.GREEN, this);
        } else if (randomDouble < 0.725)
        {
            fallingShape = new TetrisShapeS(Color.CYAN, this);
        } else if (randomDouble < 0.87)
        {
            fallingShape = new TetrisShapeZ(Color.DARK_GRAY, this);
        } else if (randomDouble < 1 )
        {
            fallingShape = new TetrisShapeI(Color.ORANGE, this);
        }
        return fallingShape;
    }

    private void addCurrentShapeToList() {

        //add all blocks from the current shape to the list of blocks
        blocks.addAll(shape.activeBlocks());
        //create a new shape to fall from the top
        shape = createRandomShape();
    }


    private boolean intercepts(ArrayList<Block> virtualBlocks)
    {
        //iterate the list of blocks
        for (Block block : blocks)
        {
            for (Block virtualBlock : virtualBlocks)
            {
                if (block.intersects(virtualBlock))
                {
                    return true;
                }
            }
        }

        //the blocks in the future shape doesn't intercept any block on the screen
        return false;
    }

    private boolean lineIsFull(ArrayList<Block> blocksInRow)
    {
        //if the number of blocks in the row is equal to the max number of possible blocks
        if (blocksInRow.size() == MAX_COLUMNS)
        {
            //get every block from the row
            for (Block block : blocksInRow)
            {
                //if any block in the row is hidden
                if (! block.isActive())
                {
                    //the line is not full
                    return false;
                }
            }
            //the line is full
            return true;
        }
        //the line is not full
        return false;
    }

    private boolean isAnyBlockInRow(int row)
    {
        //if there are no blocks on the list
        if (blocks.isEmpty())
        {
            return false;
        }
        //iterate the list of blocks
        for (Block block : blocks)
        {
            //if there is at least one block on that row
            if (block.getY() == row)
            {
                return true;
            }
        }
        //if there are no blocks on the list on that row
        return false;
    }

    private ArrayList<Block> getBlocksInRow(int row)
    {
        //create the arrayList which will contain the blocks in the row
        ArrayList<Block> blocksInRow = new ArrayList<Block>();

        if (isAnyBlockInRow(row))
        {
            for (Block block : blocks)
            {
                if (block.getY() == row)
                {
                    blocksInRow.add(block);
                }
            }
        }
        return blocksInRow;
    }

    private void removeLine(int row)
    {
        for (Block block : blocks)
        {
            if (block.getY() == row)
            {
                block.hide();
            }
        }
    }

    private void moveDownAllBlocksAbove(int row)
    {
        for (Block block : blocks)
        {
            if (block.getY() > row)
            {
                continue;
            }
            //move the block one position down
            block.moveDown();
        }
    }

    private boolean isGameOver()
    {
        //check if any of the first six rows are occupied
        for (int row = 0; row < 6; row++)
        {
            //get the blocks in the rows where the falling shape is created
            ArrayList<Block> blocksInRow = getBlocksInRow(TetrisShape.SIZE * row);

            for (Block block : blocksInRow)
            {
                //if any of the starting blocks are occupied, then the game is over
                if (block.getX() == (TetrisShape.SIZE * 3)) {
                    return true;
                }
                if (block.getX() == (TetrisShape.SIZE * 4)) {
                    return true;
                }
                if (block.getX() == (TetrisShape.SIZE * 5)) {
                    return true;
                }
                if (block.getX() == (TetrisShape.SIZE * 6)) {
                    return true;
                }
            }
        }
        //the game is not over yet
        return false;
    }

    private void increaseScore()
    {
        score += SCORE_SHAPE_PLACED * level;
    }

    private void increaseLevel()
    {
        level++;
        timerDelay /= 2;
        timer.setDelay(timerDelay);
    }

    private void showMessageCenter(String str, Graphics2D g2)
    {
        Font myFont = new Font("Arial", Font.BOLD, 15);
        g2.setColor(Color.BLACK);
        g2.setFont(myFont);
        Rectangle2D textBox = myFont.getStringBounds(str, g2.getFontRenderContext());
        g2.drawString(str, (int) (getWidth() / 2 - textBox.getWidth() / 2), (int) (getHeight() / 2));
    }

    private void showScore(Graphics2D g2)
    {
        String scoreStr = "Level: " + level + "           Score: " + score;
        Font myFont = new Font("Arial", Font.BOLD, 10);
        g2.setColor(Color.BLACK);
        g2.setFont(myFont);
        Rectangle2D textBox = myFont.getStringBounds(scoreStr, g2.getFontRenderContext());
        g2.drawString(scoreStr, 0, (int) textBox.getHeight());
    }

    private void ifLineIsComplete()
    {
        ArrayList<Integer> deletedRows = new ArrayList<Integer>();
        //search every row for a complete line
        for (int row = 0; row <= MAX_ROWS ; row++)
        {
            int currentRow = (TetrisShape.SIZE * row);
            //get all the blocks in the current row
            ArrayList<Block> blocksInRow = (getBlocksInRow(currentRow));
            //if there is a block in the row
            if (! blocksInRow.isEmpty())
            {
                //if the line is full
                if (lineIsFull(blocksInRow))
                {
                    //delete the line
                    removeLine(currentRow);
                    //save the row coordinate that got deleted
                    deletedRows.add(Integer.valueOf(currentRow));
                    //increase score
                    score += SCORE_LINE_COMPLETED;
                    completedLines++;
                    //when 10 lines get completed
                    if ((completedLines % 10) == 0)
                    {
                        increaseLevel();
                    }
                }
            }
        }

        for (Integer i : deletedRows)
        {
            //move one step down all the blocks above the deleted row
            moveDownAllBlocksAbove(i.intValue());
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //current shape
        shape.draw(g2);

        //draw all shapes
        for (Block block: blocks)
        {
            block.draw(g2);
        }
        //endGame message
        showMessageCenter(endGameMessage, g2);
        //score
        showScore(g2);
    }

    class TimeListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            //if the game is over
            if (isGameOver())
            {
                endGameMessage = "Game Over";
                //stop game
                timer.stop();
            }

            if (shape.isNotAtScreenBottom())
            {
                //if below the shape is another block
                if (intercepts(shape.virtualDownPosition()))
                {
                    addCurrentShapeToList();
                    increaseScore();
                }

                shape.moveDown();
            } else
            {
                //when the shape hits the bottom of the screen
                addCurrentShapeToList();
                increaseScore();
            }

            ifLineIsComplete();

            //update everything on the screen
            repaint();
        }
    }
}
