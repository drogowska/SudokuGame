package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {

    @Test
    void getFileDaoTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        SudokuBoard board = new SudokuBoard(solver);
        assertTrue(factory.getFileDao("sudoku.txt").write(board));
    }

    @Test
    void getJdbcDaoTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("test");
        assertFalse(factory.getJdbcDao("test").equals(jdbc));
    }
}