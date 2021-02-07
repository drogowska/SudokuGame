package pl.first.firstjava;

import exceptions.DaoException;
import exceptions.DataBaseException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {


    private static String DB_URL = "jdbc:derby:SudokuDB;create=true";
    //private DataBase db;
    private Connection connection;
    private final String boardName;
    Random rnd = new Random();
    Integer id = 0;

    static final Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    private ResourceBundle bundle = ResourceBundle.getBundle("modelBundle");



    public JdbcSudokuBoardDao(String boardName) {
        this.boardName = boardName;
        connection = DataBase.getInstance().createConnection();
        id = rnd.nextInt(100);
    }


    public JdbcSudokuBoardDao() {
        boardName = "";
    }


    @Override
    public boolean write(SudokuBoard obj) {
        //DataBase.getInstance().createBoardsTable();
        //DataBase.getInstance().createFieldsTable();
        //id += 1;
        String values = obj.convertSudokuBoardToString();
        String isChangeable = obj.convertIsEditableToString();
        DataBase.getInstance().insertAll(boardName,id,values,isChangeable);
        return true;
    }

    @Override
    public SudokuBoard read() {
        SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        String s = DataBase.getInstance().select(boardName,2);
        String c = DataBase.getInstance().select(boardName,3);
        sudokuBoard.convertStringToSudokuBoard(s);
        sudokuBoard.convertStringToIsChangeable(c);
        return sudokuBoard;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException | NullPointerException throwable) {
            throw new DataBaseException(bundle.getString("_erInClos"),throwable);
        }

    }


}
