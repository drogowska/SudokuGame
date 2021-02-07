package pl.first.firstjava;

import exceptions.DaoException;
import exceptions.FileOperationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import java.util.Scanner;


public class FileSudokuBoardDao implements Dao<SudokuBoard> {
    String fileName;
    File file;
    private Scanner in;
    private PrintWriter out;
    private ResourceBundle bundle = ResourceBundle.getBundle("modelBundle");

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
        this.file = new File(fileName);
    }

    protected void setFileReadOnly() {
        file.setReadOnly();
    }

    protected void setFileReadAndWrite() {
        file.setReadable(true);
        file.setWritable(true);
    }

    protected void openFile() throws FileNotFoundException {
        in = new Scanner(file);
        out = new PrintWriter(fileName);
    }

    protected void setFileWriteOnly() {
        file.setReadable(false);
    }

    protected void deleteFile() {
        file.delete();
    }

    @Override
    public SudokuBoard read() throws FileOperationException {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        try (Scanner in = new Scanner(file)) {
            for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        int value = Integer.parseInt(in.nextLine());
                        sudokuBoard.set(i, j, value);
                    }
                }
                in.close();
            } catch (FileNotFoundException e) {
                throw new FileOperationException(bundle.getString("_fnf"),e);
            }

        return sudokuBoard;
    }

    @Override
    public boolean write(SudokuBoard obj) throws FileOperationException {
        try (PrintWriter out = new PrintWriter(fileName)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    out.println(obj.get(i,j));
                }
            }
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            throw new FileOperationException(bundle.getString("_fnf"),e);
        }
    }

    @Override
    public void finalize() throws DaoException {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            throw new DaoException(bundle.getString("_erClose"),e);
        }

    }

    @Override
    public void close() throws DaoException {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            throw new DaoException(bundle.getString("_erClose"),e);
        }


    }
}
