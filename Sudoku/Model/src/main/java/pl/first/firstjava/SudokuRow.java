package pl.first.firstjava;

import exceptions.BadFieldValueException;
import java.util.List;

public class SudokuRow extends SudokuElement {

    private int variableX;
    private List<SudokuField> board;

    public SudokuRow(List<SudokuField> board, int x) throws BadFieldValueException {
        this.board = board;
        this.variableX = x;
        x = x * 9;
        for (int i = 0; i < 9; i++) {
            fields.get(i).setFieldValue(board.get(x).getFieldValue());
            x++;
        }
    }

    @Override
    protected Object clone() {
        SudokuRow row = null;
        row = new SudokuRow(board,variableX);
        return row;
    }
}
