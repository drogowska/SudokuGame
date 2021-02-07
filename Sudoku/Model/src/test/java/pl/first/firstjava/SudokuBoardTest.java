package pl.first.firstjava;

import exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    @Test
    public void equalBoardsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        SudokuBoard sudoku1 = (SudokuBoard) sudoku.clone();

        sudoku.solveGame();
        sudoku1.solveGame();
        boolean x = false;
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(sudoku.get(i, j) != sudoku1.get(i, j)) {
                    x = true;
                    break;
                }
            }
            if(x) break;
        }
        assertTrue(x);
        assertNotEquals(sudoku1, sudoku);
    }
    @Test
    public void getSetTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        for (int i = 0; i < 9; i++) {
            for(int j = 0;j< 9; j++){
                assertEquals(sudoku.get(i, j), 0);
            }
        }
        assertThrows(FieldException.class, ()->sudoku.get(-1,-1));
        assertThrows(FieldException.class, ()->sudoku.get(10,10));
        assertThrows(FieldException.class, ()->sudoku.get(10,-1));
        assertThrows(FieldException.class, ()->sudoku.get(-1,10));
        assertThrows(FieldException.class, ()->sudoku.set(-1,-1,1));
        assertThrows(BadFieldValueException.class, ()->sudoku.set(1,1,-1));
        assertThrows(BadFieldValueException.class, ()->sudoku.set(1,1,11));
        assertThrows(FieldException.class, ()->sudoku.set(-1,10,1));
        assertThrows(FieldException.class, ()->sudoku.set(10,-1,1));
        assertThrows(FieldException.class, ()->sudoku.set(10,10,1));
                sudoku.set(1,2,8);
        assertEquals(sudoku.get(1,2), 8);
    }

    @Test
    public void checkBoardTest()  {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        sudoku.solveGame();
        assertTrue(sudoku.checkBoard());
    }

    @Test
    public void incorrectSudokuTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        assertFalse(sudoku.checkBoard());
        sudoku.solveGame();
        sudoku.set(0,0,1);
        sudoku.set(0,1,1);
        assertFalse(sudoku.checkBoard());
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                sudoku.set(i, j, j+1);
            }
        }
        assertFalse(sudoku.checkBoard());
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                sudoku.set(i, j, (i+j)%9+1);
            }
        }
        assertFalse(sudoku.checkBoard());
    }

    @Test
    public void solveGameTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        assertEquals(sudoku.get(0,0),0);
        assertEquals(sudoku.get(5,9),0);
        sudoku.solveGame();
        assertNotEquals(sudoku.get(5,9),0);
        assertNotEquals(sudoku.get(0,0),0);
       
    }

    @Test
    public void getRowTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for (int j = 0; j < 9; j++) {
            board.set(0, j, j+1);
        }
        SudokuRow row = board.getRow(0);
        for (int j = 0; j < 9; j++) {
            assertEquals(row.get(j), board.get(0,j));
        }
        assertThrows(FieldException.class, ()->board.getRow(-1));
        assertThrows(FieldException.class, ()->board.getRow(10));

    }

    @Test
    public void getColumnTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        for(int i = 0; i < 9; i++) {
            board.set(i, 0,i+1);
        }
        SudokuColumn col = board.getColumn(0);
        for (int j = 0; j < 9;j++) {
            assertEquals(col.get(j),board.get(j,0));
        }
        assertThrows(FieldException.class, ()->board.getColumn(-1));
        assertThrows(FieldException.class, ()->board.getColumn(10));
    }

    @Test
    public void getBoxTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuField[] fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField(i + 1);
        }
        int value = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.set(i, j, value);
                value++;
            }
        }
        SudokuBox box = board.getBox(0, 0);
        for (int i = 0; i < 9; i++) {
            assertEquals(box.get(i), fields[i].getFieldValue());
        }
        assertThrows(FieldException.class, () -> board.getBox(-1, -1));
        assertThrows(FieldException.class, () -> board.getBox(10, 10));
        assertThrows(FieldException.class, () -> board.getBox(-1, 10));
        assertThrows(FieldException.class, () -> board.getBox(10, -1));
    }

    @Test
    public void setGetListeningTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        assertFalse(board.isListening());
        board.setListening(true);
        assertTrue(board.isListening());
    }

    /*@Test
    public void toStringTest() {
        String test = "0 0 0 0 0 0 0 0 0 " +
                    "\n0 0 0 0 0 0 0 0 0 " +
                    "\n0 0 0 0 0 0 0 0 0 " +
                    "\n0 0 0 0 0 0 0 0 0 " +
                    "\n0 0 0 0 0 0 0 0 0 " +
                    "\n0 0 0 0 0 0 0 0 0 " +
                    "\n0 0 0 0 0 0 0 0 0 " +
                    "\n0 0 0 0 0 0 0 0 0 " +
                    "\n0 0 0 0 0 0 0 0 0 ";
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        assertEquals(board.toString(), test);
    }*/
    @Test
    public void CloneTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuBoard board1 = (SudokuBoard) board.clone();
        assertTrue(board.equals(board1));
        board.set(1,1,2);
        assertFalse(board1.equals(board));
        assertThrows(FieldException.class, ()->board.set(0,0,10));

        //assertThrows(BadFieldValueException.class, ()->board.clone());

    }

    @Test
    public void hashCodeTest()  {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuBoard board1 = (SudokuBoard) board.clone();
        assertEquals(board.hashCode(), board1.hashCode());
    }
    @Test
    public void equalsTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuBoard board1 = (SudokuBoard) board.clone();
        assertTrue(board.equals(board1));
        assertTrue(board.equals(board));
        assertFalse(board.equals(new SudokuField()));
        assertFalse(board.equals(null));
        assertDoesNotThrow(()->board1.set(0,0,5));
        assertFalse(board.equals(board1));
    }
    @Test
    public void toStringTest() {
        String test ="SudokuBoard{board=[SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, SudokuField{value=0}, " +
                "SudokuField{value=0}, SudokuField{value=0}]}";
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        assertEquals(board.toString(), test);
    }

    @Test
    public void getFieldTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuField field = new SudokuField();
        assertEquals(board.getField(0,0),field);
        assertThrows(FieldException.class, ()->board.getField(-1,-1));
        assertThrows(FieldException.class, ()->board.getField(10,10));
        assertThrows(FieldException.class, ()->board.getField(10,-1));
        assertThrows(FieldException.class, ()->board.getField(-1,10));
    }

    @Test
    public void Test() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        assertDoesNotThrow(()->sudokuBoard.set(0,0, 1));
        assertDoesNotThrow(()->sudokuBoard.set(0,1, 2));
        assertDoesNotThrow(()->sudokuBoard.set(0,2, 3));
        sudokuBoard.solveGame();
        //System.out.print(sudokuBoard.toString());
        sudokuBoard.setListening(true);
        assertDoesNotThrow(()->sudokuBoard.set(0,0, 2));
        assertDoesNotThrow(()->sudokuBoard.set(0,0, 1));
        new DaoException("");
        new FileOperationException("");
        new DataBaseException("");
        assertThrows(WrongObjectException.class, ()->new SudokuBoard(null));
    }

    @Test
    public void convertIsEditableToString() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String s = "";
        for (int i=0;i<81;i++) {
            s += '1';
        }
        assertEquals(sudokuBoard.convertIsEditableToString(),s );
        s = "";
        for (int i=0;i<81;i++) {
            s += '0';
        }
        sudokuBoard.solveGame();
        assertEquals(sudokuBoard.convertIsEditableToString(),s);
    }

    @Test
    public void convertStringToIsChangeableTest() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String s = "";
        String s1 = "";
        for (int i=0;i<81;i++) {
            s += '0';
            s1 += '1';
        }
        SudokuBoard su = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudokuBoard.convertStringToIsChangeable(s),sudokuBoard );
        sudokuBoard.solveGame();
        assertEquals(sudokuBoard.convertStringToIsChangeable(s1),sudokuBoard );
    }

    @Test
    public void convertStringToSudokuBoardTest() {
        SudokuBoard b = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard x = new SudokuBoard(new BacktrackingSudokuSolver());
        String s = x.convertSudokuBoardToString();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                b.set(i, j, Character.getNumericValue(s.charAt(i * 9 + j)));
            }
        }
        SudokuBoard a = x.convertStringToSudokuBoard(s);
        assertTrue(b.equals(a));

    }

}