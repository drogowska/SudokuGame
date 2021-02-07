package pl.first.firstjava;

import exceptions.BadFieldValueException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LevelTest {

    @Test
    public void EasyTest() throws BadFieldValueException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        Level easy = new Easy();
        int tmp = 0;
        sudokuBoard.solveGame();
        easy.removeFields(sudokuBoard);
        for( int i=0; i<9; i++){
            for(int j =0;j <9; j++) {
                if (sudokuBoard.get(i,j) == 0) {
                    tmp++;
                }
            }
        }
        assertEquals(tmp, 25);
    }

    @Test
    public void HardTest() throws BadFieldValueException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        Level hard = new Hard();
        int tmp = 0;
        sudokuBoard.solveGame();
        hard.removeFields(sudokuBoard);
        for( int i=0; i<9; i++){
            for(int j =0;j <9; j++) {
                if (sudokuBoard.get(i,j) == 0) {
                    tmp++;
                }
            }
        }
        assertEquals(tmp, 45);
    }

    @Test
    public void MediumHardTest() throws BadFieldValueException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        Level medium = new Medium();
        int tmp = 0;
        sudokuBoard.solveGame();
        medium.removeFields(sudokuBoard);
        for( int i=0; i<9; i++){
            for(int j =0;j <9; j++) {
                if (sudokuBoard.get(i,j) == 0) {
                    tmp++;
                }
            }
        }
        assertEquals(tmp, 35);
    }
}
