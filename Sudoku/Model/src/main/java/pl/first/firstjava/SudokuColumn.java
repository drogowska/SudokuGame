package pl.first.firstjava;

import exceptions.BadFieldValueException;
import java.util.List;

public class SudokuColumn extends SudokuElement {

    private List<SudokuField> board;
    private int variableX;

    public SudokuColumn(List<SudokuField> board, int x) throws BadFieldValueException {
        this.board = board;
        this.variableX = x;
        for (int i = 0; i < 9; i++) {
            fields.get(i).setFieldValue(board.get(x).getFieldValue());
            x += 9;
        }
    }

    @Override
    protected Object clone() {
        SudokuColumn col = null;
        col = new SudokuColumn(this.board,this.variableX);
        return col;
    }
}
