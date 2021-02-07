package pl.first.firstjava;

import exceptions.DaoException;
import exceptions.DataBaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataBase {

    private static String DB_URL = "jdbc:derby:E:/an_pn_1145_04/SudokuDB;";
    //  private static final String DB_USER = "admin";
    // private static final String DB_PASS = "test";
    private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

    Logger logger = LoggerFactory.getLogger(DataBase.class);
    private ResourceBundle listBundle = ResourceBundle.getBundle("modelBundle");

    public DataBase() {

    }

    public Connection createConnection() {
        Connection con;
        try {
            con = DriverManager.getConnection(DB_URL);
            return con;
        } catch (SQLException e) {
            throw new DaoException(listBundle.getString("_con"),e);
        }
    }

    public  String getDbUrl() {
        return DB_URL;
    }

    public  void setDbUrl(String dbUrl) {
        DB_URL = dbUrl;
    }

    public void insertSudoku(final String nazwa, int id) {
        //String sql = "INSERT INTO " + tableName + "(id,wartosc) VALUES(?,?)";
        String sql = "INSERT INTO sudokuBoard (id,nazwa) VALUES(?,?)";
        try (Connection conn = this.createConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, nazwa);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw new DataBaseException(listBundle.getString("_erInInsert"),e);
        }

    }

    static DataBase db = new DataBase();


    public static DataBase getInstance() {
        return db;
    }


    public void insertFields(final String wartosc, String isChangeable, int id) {
        String sql = "INSERT INTO sudokuField (id,wartosc,isChangable) VALUES (?,?,?)";
        try (Connection conn = this.createConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2,  wartosc);
            pstmt.setString(3, isChangeable);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw new DataBaseException(listBundle.getString("_erInInsert"),e);
        }
    }

    public void insertAll(final String name, int id, String wartosci,
                          String isChangeable) {
        insertSudoku(name, id);
        insertFields(wartosci,isChangeable,id);

    }

    public String select(String boardName, int column) {
        //String i = Integer.toString(id);
        //String s = "(SELECT id from sudokuBoard WHERE nazwa='" + name + "')";
        String s  = "(SELECT id FROM sudokuBoard Where nazwa='" + boardName + "')";
        String sql = "SELECT * FROM sudokuField WHERE id=" + s + "";

        try (Connection conn = this.createConnection()) {
            Statement stmt  = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String  code = "";
            if (rs.next()) {
                code = rs.getString(column);
            }
            conn.close();
            rs.close();
            return code;
        } catch (SQLException e) {
            logger.info(e.getMessage());
            throw new DataBaseException(listBundle.getString("_erInselet"),e);
        }
    }
}
