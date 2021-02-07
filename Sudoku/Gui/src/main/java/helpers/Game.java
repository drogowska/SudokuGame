package helpers;

import pl.first.firstjava.Level;
import pl.first.firstjava.SudokuBoard;

public class Game {
        private Level gameLevel;
        private SudokuBoard sudokuBoard;
        private SudokuBoard sudokuBoardCopy;

        private boolean isLoaded;

        public SudokuBoard getFillSudokuBoard() {
            return this.sudokuBoardCopy;
        }

        public boolean isLoaded() {
            return isLoaded;
        }

        public Game(final Level gameLevel, final SudokuBoard sudokuBoard, final boolean isLoaded) {
            this.gameLevel = gameLevel;
            this.sudokuBoard = sudokuBoard;
            //this.sudokuBoardCopy = (SudokuBoard) sudokuBoard.clone();
            this.isLoaded = isLoaded;
            if (!this.isLoaded) {
                this.sudokuBoard.solveGame();
                this.gameLevel.removeFields(sudokuBoard);
            }
        }

        public SudokuBoard getSudokuBoard() {
            return this.sudokuBoard;
        }

}
