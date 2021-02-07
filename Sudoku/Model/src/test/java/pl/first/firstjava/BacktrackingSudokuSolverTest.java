package pl.first.firstjava;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {

    @Test
    public  void incorrectSudokuTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        for(int i = 1; i < 9; i++) {
            sudokuBoard.set(i, 0, i+1);
        }
        sudokuBoard.set(0,1,1);
        sudokuBoard.solveGame();
        assertEquals(sudokuBoard.get(0, 0),0);
    }
}