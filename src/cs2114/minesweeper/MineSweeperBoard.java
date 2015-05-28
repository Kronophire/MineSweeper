package cs2114.minesweeper;

import sofia.util.Random;

// -------------------------------------------------------------------------
/**
 * This class is the MineSweeperBoard that has all the methods to play the
 * MineSweeper game.
 *
 * @author Aska_Toda
 * @version Feb 4, 2014
 */

public class MineSweeperBoard
    extends MineSweeperBoardBase
{
    /**
     * Instance/static fields
     */
    private MineSweeperCell[][] mineBoard;
    private int                 height;
    private int                 width;
    private int                 numMines;


    /**
     * The MineSweeperBoard constructor, having the parameters of the width,
     * height, and number of mines.
     *
     * @param w
     *            the width of the board
     * @param h
     *            the height of the board
     * @param mines
     *            the number of mines on the board
     */
    public MineSweeperBoard(int w, int h, int mines)
    {
        width = w;
        height = h;
        numMines = mines;
        mineBoard = new MineSweeperCell[w][h];

        // Initializes the board
        for (int i = 0; i < w; i++)
        {
            for (int j = 0; j < h; j++)
            {
                setCell(i, j, MineSweeperCell.COVERED_CELL);
            }
        }

        // Place mines

        int k = 0;
        while (k < mines)
        {
            int x = Random.generator().nextInt(w);
            int y = Random.generator().nextInt(h);

            if (getCell(x, y) == MineSweeperCell.COVERED_CELL)
            {

                setCell(x, y, MineSweeperCell.MINE);
                k++;
            }

        }

    }


    /**
     * The flagCell method is when you click on one of the uncovered cell by
     * toggling the flagCell, it will put a flag on top of a covered cell, or
     * mine. If there is a flag on top of a covered cell or mine cell then it
     * will be uncovered.
     *
     * @param x
     *            the column of the cell to be flagged/unflagged
     * @param y
     *            the row of the cell to be flagged/unflagged
     */
    public void flagCell(int x, int y)
    {
        if (getCell(x, y) == MineSweeperCell.COVERED_CELL)
        {
            setCell(x, y, MineSweeperCell.FLAG);
        }
        else if (getCell(x, y) == MineSweeperCell.FLAG)
        {
            setCell(x, y, MineSweeperCell.COVERED_CELL);
        }
        else if (getCell(x, y) == MineSweeperCell.FLAGGED_MINE)
        {
            setCell(x, y, MineSweeperCell.MINE);
        }
        else if (getCell(x, y) == MineSweeperCell.MINE)
        {
            setCell(x, y, MineSweeperCell.FLAGGED_MINE);
        }
    }


    /**
     * The GetCell method is a getter method that returns all the cells in the
     * mineBoard.
     *
     * @param x
     *            the column containing the cell.
     * @param y
     *            the row containing the cell.
     * @return The created mineBoard will be returned and if the dimensions of
     *         the mineboard goes out of the bound then it returns an invalid
     *         cell.
     */
    public MineSweeperCell getCell(int x, int y)
    {
        if (x < width() && y < height() && x >= 0 && y >= 0)
        {
            return mineBoard[x][y];
        }
        else
        {
            return MineSweeperCell.INVALID_CELL;
        }
    }


    /**
     * The height method is getting the number of rows in the MineSweeperBoard.
     *
     * @return The rows or height in MineSweeper will be returned.
     */
    public int height()
    {
        return height;
    }


    /**
     * The isGameLost method is the conditions when the game is over. That is
     * when the user reveals a mine.
     *
     * @return If there is an uncovered mine on the board then true will be
     *         returned, or else false; game continues.
     */
    public boolean isGameLost()
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {

                if (mineBoard[i][j] == MineSweeperCell.UNCOVERED_MINE)
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * The isGameWon method is the conditions when the user wins the game. That
     * is when the user placed all the flags on top of the mines, no flags have
     * been placed incorrectly, and all non-flagged cells have been uncovered.
     *
     * @return Game will end and user will win when the following conditions are
     *         met.
     */
    public boolean isGameWon()
    {
        int count = 0;
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {

                if (mineBoard[i][j] == MineSweeperCell.UNCOVERED_MINE
                    || mineBoard[i][j] == MineSweeperCell.FLAG
                    || mineBoard[i][j] == MineSweeperCell.COVERED_CELL)
                {
                    return false;
                }
                else if (mineBoard[i][j] == MineSweeperCell.FLAGGED_MINE)
                {
                    count++;
                }

            }
        }
        return count == numMines;
    }


    /**
     * The numberOfAdjacent method counts the number of mines that appear in
     * cells that are adjacent to the specified cell.
     *
     * @param x
     *            the column of the cell.
     * @param y
     *            the row of the cell.
     * @return The number of mines that was counted around the surroundings of
     *         the cell will be returned.
     */
    public int numberOfAdjacentMines(int x, int y)
    {
        int count = 0;

        for (int i = x - 1; i <= x + 1; i++)
        {
            for (int j = y - 1; j <= y + 1; j++)
            {
                if (getCell(i, j) == MineSweeperCell.MINE)
                {
                    count++;
                }
                else if (getCell(i, j) == MineSweeperCell.FLAGGED_MINE)
                {
                    count++;
                }
                else if (getCell(i, j) == MineSweeperCell.UNCOVERED_MINE)
                {
                    count++;
                }
            }
        }

        return count;
    }


    /**
     * The revealBoard method uncovers all of the cells on the board.
     */
    public void revealBoard()
    {
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {

                uncoverCell(i, j);
            }
        }
    }


    /**
     * The uncoverCell method works if there is a covered cell then when clicked
     * on it, turn the covered cell into a cell with an adjacent number of mines
     * next to it. If there is a covered mine, and when one clicks it then it
     * will become an uncovered mine.
     *
     * @param x
     *            the column of the cell to be uncovered.
     * @param y
     *            the row of the cell to be uncovered.
     */
    public void uncoverCell(int x, int y)
    {
        if (getCell(x, y) != MineSweeperCell.FLAG
            || getCell(x, y) != MineSweeperCell.FLAGGED_MINE)
        {
            if (getCell(x, y) == MineSweeperCell.COVERED_CELL)
            {
                setCell(
                    x,
                    y,
                    MineSweeperCell.adjacentTo(numberOfAdjacentMines(x, y)));
            }
            else if (getCell(x, y) == MineSweeperCell.MINE)
            {
                setCell(x, y, MineSweeperCell.UNCOVERED_MINE);
            }
        }
    }


    /**
     * The width method gets the number of columns in this MineSweeperBoard.
     *
     * @return The number of columns in this MineSweeperBoard.
     */
    public int width()
    {
        return width;
    }


    /**
     * The setCell method sets the contents of the specified cell on this
     * MineSweeperBoard. The value passed in should be the MineSweeperCell
     * enumerated type.
     *
     * @param x
     *            column of the cell.
     * @param y
     *            rows of the cell.
     * @param value
     *            the value to be placed in the cell.
     */
    protected void setCell(int x, int y, MineSweeperCell value)
    {
        if (x < width() && y < height() && x >= 0 && y >= 0)
        {
            mineBoard[x][y] = value;
        }
    }
}
