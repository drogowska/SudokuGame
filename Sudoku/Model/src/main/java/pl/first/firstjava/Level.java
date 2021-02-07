package pl.first.firstjava;

import exceptions.BadFieldValueException;

public class Level {
    private int amountOfFieldsNotFill;

    public Level(int amountOfFieldsNotFill) {
        this.amountOfFieldsNotFill = amountOfFieldsNotFill;
    }

    public void removeFields(final SudokuBoard sudokuBoard) throws BadFieldValueException {
        for (int i = 0; i < amountOfFieldsNotFill; i++) {
            int x = (int) (Math.random() * 9);
            int y = (int) (Math.random() * 9);
            if (sudokuBoard.get(x, y) != 0) {
                sudokuBoard.set(x, y, 0);
            } else {
                i--;
            }
        }
    }
}
