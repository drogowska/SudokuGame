package pl.first.firstjava;

import java.beans.PropertyChangeEvent;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuListener implements java.beans.PropertyChangeListener, java.io.Serializable {

    SudokuBoard board;
    Logger logger = LoggerFactory.getLogger(SudokuListener.class);
    private ResourceBundle listBundle = ResourceBundle.getBundle("modelBundle");

    SudokuListener(SudokuBoard board) {
        this.board = board;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        int fieldNumber = Integer.parseInt(evt.getPropertyName());
        if (!board.getRow(fieldNumber / 9).verify()) {
            logger.info(listBundle.getString("_erInRow"));
        }
        if (!board.getColumn(fieldNumber % 9).verify()) {
            logger.info(listBundle.getString("_erInCol"));
            //System.out.print("Błąd w kolumnie");
        }
        if (!board.getBox((fieldNumber / 9) / 3, (fieldNumber % 9) / 3).verify()) {
            logger.info(listBundle.getString("_erInBox"));
            //System.out.print("");
        }
    }
}
