package pl.first.firstjava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import exceptions.BadFieldValueException;
import exceptions.FieldException;
import exceptions.WrongObjectException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

//https://docs.oracle.com/javase/9/docs/api/java/util/Arrays.html#asList-T...-
//https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html?is-external=true#equals-java.lang.Object-
// https://guava.dev/releases/19.0/api/docs/com/google/common/base/MoreObjects.html

public class SudokuBoard implements Cloneable, Serializable {

    //@Id
    //@GeneratedValue
    private Long id;

    private String name;

    //@Transient
    private boolean listening;
    //@Transient
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    //@Transient
    private final SudokuSolver sudokuSolver;
    //private final SudokuField[] board = new SudokuField[81];


    //@Transient
    private final List<SudokuField> board = Arrays.asList(new SudokuField[81]);

    private ResourceBundle listBundle = ResourceBundle.getBundle("modelBundle");

    public void addPropertyChangeListener(PropertyChangeListener p) {
        changes.addPropertyChangeListener(p);
    }

    /*
    public void removePropertyChangeListener (PropertyChangeListener p) {
        changes.removePropertyChangeListener (p);
    }*/

    public boolean isListening() {
        return listening;
    }

    public void setListening(boolean listening) {
        this.listening = listening;
    }

    public void solveGame() {
        sudokuSolver.solve(this);
    }

    public boolean checkBoard() {
        for (int i = 0; i < 9; i++) {
            if (!(getRow(i).verify() && getColumn(i).verify() && getBox(i / 3, i % 3).verify())) {
                return false;
            }
        }
        return true;
    }

    public SudokuBoard(SudokuSolver sudokuSolver) throws  WrongObjectException {
        listening = false;
        addPropertyChangeListener(new SudokuListener(this));
        for (int i = 0; i < 81; i++) {
            board.set(i, new SudokuField());
        }
        if (sudokuSolver == null) {
            throw new WrongObjectException(listBundle.getString("_Nptr"));
        }
        this.sudokuSolver = sudokuSolver;
    }

    public SudokuField getField(int x, int y) {
        if (x < 0 || x > 9 && y < 0 || y > 9) {
            throw new FieldException(listBundle.getString("_wPosition"));
        }
        return board.get(x * 9 + y);
    }

    public int get(int x, int y) {
        if (x < 0 || x > 9 && y < 0 || y > 9) {
            throw new FieldException(listBundle.getString("_wPosition"));
        }
        return board.get(x * 9 + y).getFieldValue();
    }

    public void set(int x, int y, int number) {
        if (x < 0 || x > 9 && y < 0 || y > 9) {
            throw new FieldException(listBundle.getString("_wPosition"));
        }
        if (number < 0 || number > 9) {
            throw new BadFieldValueException(listBundle.getString("_wNum"));
        }
        int fieldNumber = x * 9 + y;
        int oldValue = board.get(fieldNumber).getFieldValue();
        board.get(fieldNumber).setFieldValue(number);
        if (listening) {
            int newValue = board.get(fieldNumber).getFieldValue();
            changes.firePropertyChange(String.valueOf(fieldNumber), oldValue, newValue);
        }
    }
    /*
   * public String print() {  wyświetl sudoku
   *     StringBuilder str = new StringBuilder();
   *     int j = 0;
   *     for (int i = 0; i < 81; i++) {
   *         if (j > 8) {
   *             str.append("\n");
   *             j = 0;
   *         }
   *         str.append(board.get(i).getFieldValue());
   *         str.append(" ");
   *         j++;
   *     }
   *     return str.toString();
   *}
   */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuBoard board1 = (SudokuBoard) o;
        return Objects.equal(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(board);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("board", board)
                .toString();
    }

    @Override
    public Object clone() {
        SudokuBoard sudokuBoard = new SudokuBoard(this.sudokuSolver);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, board.get(i + 9 * j).getFieldValue());
            }
        }
        return sudokuBoard;
    }

    public SudokuColumn getColumn(int x) throws FieldException { //x od 0 do 8
        if (x < 0 || x > 8) {
            throw new FieldException(listBundle.getString("_wNofCol"));
        }
        return new SudokuColumn(board, x);
    }

    public SudokuRow getRow(int x) throws FieldException {
        if (x < 0 || x > 8) {
            throw new FieldException(listBundle.getString("_wNofRow"));
        }
        return new SudokuRow(board, x);
    }

    public SudokuBox getBox(int x, int y) throws FieldException {
        if (x < 0 || x > 8 && y < 0 || y > 8) {
            throw new FieldException(listBundle.getString("_wNofBox"));
        }
        return new SudokuBox(board, x * 3 + y);
    }

    public boolean isChangeableField(int x, int y) {
        return this.getField(x,y).getChangeable();
    }

    public String convertSudokuBoardToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                builder.append(String.valueOf(get(i, j)));
            }
        }
        return builder.toString();
    }

    public String convertIsEditableToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                builder.append(String.valueOf(isChangeableField(i, j) ? 1 : 0));
            }
        }
        return builder.toString();
    }

    public SudokuBoard convertStringToSudokuBoard(String string) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                set(i, j, Character.getNumericValue(string.charAt(i * 9 + j)));
            }
        }

        return this;
    }

    public SudokuBoard convertStringToIsChangeable(String string) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (string.charAt(i * 9 + j) == '1') {
                    setChangeableField(i, j);
                }
            }
        }
        return this;
    }

    public void setChangeableField(int x, int y) {
        getField(x,y).setChangeable();
    }

}
