package pl.first.firstjava;

import exceptions.BadFieldValueException;
import java.util.List;

public class SudokuBox extends SudokuElement {

    private List<SudokuField> board;
    private int variableX;

    public SudokuBox(List<SudokuField> board, int x) throws BadFieldValueException {
        this.variableX = x;
        this.board = board;
        x = x / 3 * 27 + x % 3 * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = i * 3; j < i * 3 + 3; j++) {
                fields.get(j).setFieldValue(board.get(x).getFieldValue());
                x++;
            }
            x += 6;
        }
    }

    @Override
    protected Object clone() {
        SudokuBox box = null;
        box = new SudokuBox(this.board, this.variableX);
        return box;
    }
}
