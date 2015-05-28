package cs2114.minesweeper;

// -------------------------------------------------------------------------
/**
 * This is the test class for MineSweeperBoard, and every methods in that class
 * will be tested here.
 *
 * @author Aska_Toda
 * @version Feb 6, 2014
 */

public class MineSweeperBoardTest
    extends student.TestCase
{
    // ~ Instance/static fields ...............................................
    private MineSweeperBoard board;


    // ~ Constructor ..........................................................

    // ----------------------------------------------------------
    /**
     * Creates a new MineSweeperBoardTest object.
     */
    public MineSweeperBoardTest()
    {
        // The constructor is usually empty in unit tests, since it runs
        // once for the whole class, not once for each test method.
        // Per-test initialization should be placed in setUp() instead.
    }


    // ~ Methods ...............................................................

    // ----------------------------------------------------------
    /**
     * Sets up the test fixture. Called before every test case method.
     */
    public void setUp()
    {
        board = new MineSweeperBoard(4, 4, 2);
    }


    // ----------------------------------------------------------
    /* Insert your own test methods here */

    /**
     * The assertBoard method creates a rough diagram of a mineBoard and uses
     * symbol to represent the flag, cell, and mines.
     *
     * @param theBoard
     *            the declared name for the MineSweeperBoard.
     * @param expected
     *            the diagram or visual of the board represented as symbols.
     */
    public void assertBoard(MineSweeperBoard theBoard, String... expected)
    {
        MineSweeperBoard expectedBoard =
            new MineSweeperBoard(expected[0].length(), expected.length, 0);
        expectedBoard.loadBoardState(expected);
        assertEquals(expectedBoard.toString(), theBoard.toString());
    }


    /**
     * The test for flagCell() and checking to see if a flag is added into the
     * board after calling the method.
     */
    public void testFlagCellTrue()
    {
        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        board.flagCell(1, 1);

        assertBoard(board, "    ", "OFOO", "O++O", "OOOO");
    }


    /**
     * The test for flagCell() and checking to see if a flag is not added into
     * the board after calling the method; when it's in a false condition.
     */
    public void testFlagCellFalse()
    {
        board.loadBoardState("    ", "O*OO", "O++O", "OOOO");

        board.flagCell(1, 1);

        assertBoard(board, "    ", "O*OO", "O++O", "OOOO");
    }


    /**
     * The test for flagCell() and checking to see if the flagged cell becomes a
     * covered cell.
     */
    public void testFlagCellFlag()
    {
        board.loadBoardState("    ", "OFOO", "O++O", "OOOO");

        board.flagCell(1, 1);

        assertBoard(board, "    ", "OOOO", "O++O", "OOOO");
    }


    /**
     * The test for flagCell() and checking to see if trying to place a flag on
     * top of a flagged mine then it will become a mine cell.
     */
    public void testFlagCellFlagMine()
    {
        board.loadBoardState("    ", "OMOO", "O++O", "OOOO");

        board.flagCell(1, 1);

        assertBoard(board, "    ", "O+OO", "O++O", "OOOO");
    }


    /**
     * The test for flagCell() and checking to see if a flag is added into the
     * board, on top of a mine cell.
     */
    public void testFlagCellMine()
    {
        board.loadBoardState("    ", "O+OO", "O++O", "OOOO");

        board.flagCell(1, 1);

        assertBoard(board, "    ", "OMOO", "O++O", "OOOO");
    }


    /**
     * The test for getCell() and checking to see if there is a covered cell in
     * the coordinates given, and checking what it does when the given
     * coordinates are outside the boundaries.
     */
    public void testGetCell()
    {
        board.setCell(-100, -100, MineSweeperCell.MINE);

        board.loadBoardState("    ", "OOOO", "O++O", "OOOO");

        assertEquals(board.getCell(3, 3), MineSweeperCell.COVERED_CELL);
        assertEquals(board.getCell(-100, -100), MineSweeperCell.INVALID_CELL);
    }


    /**
     * The test for height() and checking to see if the height of the mineBoard
     * is 4.
     */
    public void testGetterHeight()
    {
        assertEquals(4, board.height());
    }


    /**
     * The test for isGameLost() and checking to see if there is an uncovered
     * mine then the game is lost and it asserts true.
     */
    public void testIsGameLostTrue()
    {
        board.loadBoardState("    ", "O*OO", "O++O", "OOOO");

        assertTrue(board.isGameLost());
    }


    /**
     * The test for isGameLost() and checking to see if there is no uncovered
     * mine(s) then the game still continues and it asserts false.
     */
    public void testIsGameLostFalse()
    {
        board.loadBoardState("    ", "MMMM", "O++O", "OOOO");

        assertFalse(board.isGameLost());
    }


    /**
     * The test for isGameWon() and checking to see if there is an mine then the
     * game still continues until the mine is revealed, and it asserts false.
     */
    public void testIsGameWonFalseMine()
    {
        board.loadBoardState("  MM", "MM  ", "    ", "    ");

        assertFalse(board.isGameWon());
    }


    /**
     * The test for isGameWon() and checking to see if there is a flag on top of
     * a cell then the game still continues until there is no unnecessary flags
     * on top of a covered cell, and it asserts false.
     */
    public void testIsGameWonFalseFlag()
    {
        board.loadBoardState("    ", "FOOF", "O  *", "****");

        assertEquals(false, board.isGameWon());
    }


    /**
     * The test for isGameWon() and checking to see if there is a flag on top of
     * a cell then the game still continues until there is no unnecessary flags
     * on top of a covered cell, and it asserts false.
     */
    public void testIsGameWonTrueMine()
    {
        board.loadBoardState("++++", "++++", "++++", "++++");

        assertFalse(board.isGameWon());
    }


    /**
     * The test for numberOfAdjacentMines() and checking to see if there is a
     * mine around the clicked cell, and counts the number of mines.
     */
    public void testNumberOfAdjacentMines()
    {
        board.loadBoardState("+++O", "+O+O", "+++O", "OOOO");

        assertEquals(board.numberOfAdjacentMines(1, 1), 8);
    }


    /**
     * The test for numberOfAdjacentMines() and checking to see if there is a
     * mine around the clicked cell, and counts the number of mines.
     */
    public void testNumberOfAdjacentFlagMines()
    {
        board.loadBoardState("MFMM", "+O+O", "+++O", "OOOO");

        assertEquals(board.numberOfAdjacentMines(1, 0), 4);
    }


    /**
     * The test for numberOfAdjacentMines() and checking to see if there are no
     * mines around the clicked cell, and counts the number of mines.
     */
    public void testNumberOfAdjacentMinesFalse()
    {
        board.loadBoardState("OOOO", "OOOO", "OOOO", "OOOO");

        assertEquals(board.numberOfAdjacentMines(1, 1), 0);
    }


    /**
     * The test for revealBoard() and checking to see if all cells on the board
     * is revealed including covered mines.
     */
    public void testRevealBoardMine()
    {
        board.loadBoardState("+++M", "O++F", "++++", "++++");

        board.revealBoard();

        assertBoard(board, "***M", "5**F", "****", "****");
    }


    /**
     * The test for uncoverCell() and checking to see if after clicking on a
     * covered cell then it would become a cell that has a value of its adjacent
     * cell that contains a mine.
     */
    public void testUncoverCell()
    {
        board.loadBoardState("O++O", "OOOO", "OOOO", "OOOO");

        board.uncoverCell(1, 1);

        assertBoard(board, "O++O", "O2OO", "OOOO", "OOOO");
    }


    /**
     * The test for uncoverCell() and checking to see if after clicking on a
     * covered mine then it would become an uncovered mine that will lose the
     * game.
     */
    public void testUncoverCellMine()
    {
        board.loadBoardState("O++O", "O+OO", "OOOO", "OOOO");

        board.uncoverCell(1, 1);

        assertBoard(board, "O++O", "O*OO", "OOOO", "OOOO");
    }


    /**
     * The test for uncoverCell() and checking to see if after clicking on a
     * cell that is not a covered mine then it would not change.
     */
    public void testUncoverCellNotMine()
    {
        board.loadBoardState("O++O", "O*OO", "OOOO", "OOOO");

        board.uncoverCell(1, 1);

        assertBoard(board, "O++O", "O*OO", "OOOO", "OOOO");
    }


    /**
     * The test for uncoverCell() and checking to see if after clicking on a
     * cell that is a flagged mine or just flag cell then it would not change.
     */
    public void testUncoverCellFlag()
    {
        board.loadBoardState("M++O", "OFOO", "OOOO", "OOOO");

        board.uncoverCell(1, 1);
        board.uncoverCell(0, 0);

        assertBoard(board, "M++O", "OFOO", "OOOO", "OOOO");
    }


    /**
     * The test for width() and checking to see if the width of the mineBoard is
     * 4.
     */
    public void testGetterWidth()
    {
        assertEquals(4, board.width());
    }


    /**
     * The test for setCell(), also known as the setter method, and checking to
     * see if the certain type of cell is being placed on the board and check if
     * it equals to the set cell type.
     */
    public void testSetCell()
    {
        board.setCell(-100, -100, MineSweeperCell.MINE);

        board.loadBoardState("    ", "O+OO", "OO+O", "+OO+");

        board.setCell(1, 1, MineSweeperCell.FLAGGED_MINE);

        assertBoard(board, "    ", "OMOO", "OO+O", "+OO+");
    }

}
