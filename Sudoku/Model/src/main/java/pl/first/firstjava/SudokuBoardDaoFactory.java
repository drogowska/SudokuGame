package pl.first.firstjava;

import exceptions.DaoException;

public class SudokuBoardDaoFactory {

    public Dao<SudokuBoard> getFileDao(final String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public Dao<SudokuBoard> getJdbcDao(String fileName) throws DaoException {
        return new JdbcSudokuBoardDao(fileName);
    }
}
