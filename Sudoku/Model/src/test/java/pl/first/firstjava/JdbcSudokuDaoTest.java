package pl.first.firstjava;

import exceptions.DaoException;
import exceptions.DataBaseException;
import exceptions.WrongObjectException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.dnd.DropTarget;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcSudokuDaoTest {

    Logger logger = LoggerFactory.getLogger(JdbcSudokuDaoTest.class);
    private ResourceBundle bundle = ResourceBundle.getBundle("modelBundle");

    @Test
    public void JdbcWriteReadNegativeTest() {
        try  {
            JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("testBoard");
            SudokuBoard s = new SudokuBoard(new BacktrackingSudokuSolver());
            s.solveGame();
            assertTrue(jdbc.write(s));
            SudokuBoard r  = jdbc.read();
            assertNotEquals(s,r); //?
            assertTrue(s.equals(r));
        } catch (DataBaseException e) {
            e.printStackTrace();
            logger.info(bundle.getString("_exception"));
        }
    }


    @Test
    public void JdbcCloseTest() {
        try  {
            JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao();
            assertThrows(DataBaseException.class,()->jdbc.close());
        } catch (DaoException e) {
            logger.info(bundle.getString("_exception"));
        }
    }

    @Test
    public void JdbcCreateConTest() {
        try (JdbcSudokuBoardDao jdbc = new JdbcSudokuBoardDao("test")) {
            DataBase.getInstance().createConnection();
            jdbc.close();
        } catch (DaoException e) {
            logger.info(bundle.getString("_exception"));
        }
    }

    @Test
    public void DbTest() {
        try {
            DataBase db = new DataBase();
            assertEquals(DataBase.getInstance().getClass(), DataBase.class);
            assertDoesNotThrow(()->DataBase.getInstance().setDbUrl("jdbc:derby:a"));
            assertEquals(DataBase.getInstance().getDbUrl(), "jdbc:derby:a");
            assertThrows(DaoException.class, () -> DataBase.getInstance().createConnection());
            assertDoesNotThrow(()->DataBase.getInstance().setDbUrl("jdbc:derby:E:/an_pn_1145_04/SudokuDB;"));

            assertThrows(DaoException.class, () -> DataBase.getInstance().insertSudoku("sudokusudokusudokousdsdada", -5));
            assertThrows(DaoException.class, () -> DataBase.getInstance().insertFields("", null, 10));
            assertDoesNotThrow(()->DataBase.getInstance().insertSudoku("testdb", 20));
            assertDoesNotThrow(()->DataBase.getInstance().insertFields("123456789", "01010111110", 20));
            assertDoesNotThrow(()->DataBase.getInstance().insertAll("testa", 30, "12", "01"));

            //assertThrows(DataBaseException.class, ()->DataBase.getInstance().select(null,-10));
            assertDoesNotThrow(()->DataBase.getInstance().select("board1", 2));
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(bundle.getString("_exception"));
        }


    }
}
