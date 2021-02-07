package pl.first.firstjava;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements SudokuSolver {
    private final int[] test = new int[81]; //które z kolei liczby testują komórki
    private final int[][] order = new int[81][10]; //kolejność testowania liczb dla komórek
    private final boolean[] locked = new boolean[81]; //czy solver może zmienić tę komórkę

    @Override
    public void solve(SudokuBoard sudokuBoard) {
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j <= 9; j++) {
                order[i][j] = j;
            }
            test[i] = 0;
        }

        for (int i = 0; i < 81; i++) {
            permutation(i);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard.get(i,j) != 0) {
                    locked[i * 9 + j] = true;
                    order[i * 9 + j][0] = sudokuBoard.get(i, j);
                } else {
                    locked[i * 9 + j] = false;
                }
            }
        }

        if (fillCell(0)) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    for (int k = 0; k < 9; k++) {
                        sudokuBoard.set(i, j, order[i * 9 + j][test[ i * 9 + j]]);
                    }
                }
            }
        }
    }


    private void permutation(int i) { //miesza kolejność liczb w tablicach pomocniczych

        order[i][0] = 0;
        List<Integer> list = Arrays.asList(new Integer[9]);
        for (int j = 0; j < 9; j++) {
            list.set(j, j + 1);
        }
        Collections.shuffle(list);
        for (int j = 1; j < 10; j++) {
            order[i][j] = list.get(j - 1);
        }
    }

    private boolean fillCell(int i) { //wypełnia pole, rekurencja
        if (i > 80) {
            return true;
        }
        if (locked[i]) {
            return fillCell(i + 1);
        }
        test[i] = 0;
        for (;;) {
            if (test[i] > 8) {
                test[i] = 0;
                return false;
            }
            if (check(i, order[i][test[i] + 1])) {
                test[i]++;
                if (fillCell(i + 1)) {
                    return true;
                }
            } else {
                test[i]++;
            }
        }
    }

    private boolean isInRow(int row, int number) {
        for (int i = 0; i < 9; i++) {
            if (order[row * 9 + i][test[row * 9 + i]] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isInColumn(int column, int number) {
        for (int i = 0; i < 9; i++) {
            if (order[i * 9 + column][test[i * 9 + column]] == number) {
                return true;
            }
        }
        return false;
    }

    private boolean isInBox(int row, int column, int number) {
        int r = row - row % 3;
        int c = column - column % 3;
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (order[i * 9 + j][test[i * 9 + j]] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check(int i, int number) { //sprawdza 3 funkcje
        int row = i / 9;
        int column = i % 9;
        return !(isInBox(row, column, number)
                || isInRow(row, number) || isInColumn(column, number));
    }

}
