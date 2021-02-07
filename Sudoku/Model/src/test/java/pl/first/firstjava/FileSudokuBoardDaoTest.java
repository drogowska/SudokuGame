package pl.first.firstjava;

import exceptions.DaoException;
import exceptions.FileOperationException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    Logger logger = LoggerFactory.getLogger(FileSudokuBoardDaoTest.class);
    private ResourceBundle bundle = ResourceBundle.getBundle("modelBundle");

    @Test
    void writePositiveTest() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard board = new SudokuBoard(solver);
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try (FileSudokuBoardDao file = (FileSudokuBoardDao) factory.getFileDao("sudoku.txt")) {
            board.solveGame();
            file.setFileWriteOnly();
            assertTrue(file.write(board));
            assertDoesNotThrow( ()->file.write(board));
        } catch (Exception e) {
            logger.info(bundle.getString("_exception"));
        }
    }

    @Test
    void writeNegativeTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try ( FileSudokuBoardDao file = (FileSudokuBoardDao) factory.getFileDao("sudoku.txt")) {
            SudokuSolver solver = new BacktrackingSudokuSolver();
            SudokuBoard board = new SudokuBoard(solver);
            board.solveGame();
            file.setFileReadOnly();
            //assertFalse(file.write(board));
            assertThrows( FileOperationException.class, ()->file.write(board));
        } catch (Exception e) {
            logger.info(bundle.getString("_exception"));
        }
    }

    @Test
    void readePositiveTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try ( FileSudokuBoardDao file = (FileSudokuBoardDao) factory.getFileDao("sudoku.txt")) {
            SudokuSolver solver = new BacktrackingSudokuSolver();
            SudokuBoard board = new SudokuBoard(solver);
            board.solveGame();
            file.setFileReadAndWrite();
            assertTrue(file.write(board));
            assertDoesNotThrow( ()->file.write(board));
            assertEquals(file.read(), board);
            assertDoesNotThrow( ()->file.read());
        } catch (Exception e) {
            logger.info(bundle.getString("_exception"));
        }
    }

    @Test
    void readeNegativeTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try ( FileSudokuBoardDao file = (FileSudokuBoardDao) factory.getFileDao("sudoku.txt")) {
            SudokuSolver solver = new BacktrackingSudokuSolver();
            SudokuBoard board = new SudokuBoard(solver);
            board.solveGame();
            file.setFileWriteOnly();
            assertNotEquals(file.read(), board);
            assertDoesNotThrow(()->file.read());
        } catch (Exception e) {
            logger.info(bundle.getString("_exception"));
        }
    }

    @Test
    void readFileDoesntExists() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try ( FileSudokuBoardDao file = (FileSudokuBoardDao) factory.getFileDao("sudoku.txt")) {
            file.deleteFile();
            assertThrows( FileOperationException.class, ()->file.read());
        } catch (Exception e) {
            logger.info(bundle.getString("_exception"));
        }

    }

    @Test
    void finalizeTest() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try ( FileSudokuBoardDao file = (FileSudokuBoardDao) factory.getFileDao("sudoku.txt")) {
            assertThrows( DaoException.class, ()->file.finalize());
            assertThrows( DaoException.class, ()->file.close());
        } catch (Exception e) {
            logger.info(bundle.getString("_exception"));
        }

    }

    @Test
    public void finalizeNegativeTest() throws FileNotFoundException {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try ( FileSudokuBoardDao file = (FileSudokuBoardDao) factory.getFileDao("sudoku.txt")) {
            file.openFile();
            assertDoesNotThrow(()->file.finalize());
            assertDoesNotThrow(()->file.close());
        } catch (Exception e) {
            logger.info(bundle.getString("_exception"));
        }


    }

}