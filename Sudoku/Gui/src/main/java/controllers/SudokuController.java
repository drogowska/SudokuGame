package controllers;

import exceptions.FileOperationException;
import helpers.FieldPane;
import helpers.Game;
import helpers.PopOutWindow;
import java.io.File;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.firstjava.*;


public class SudokuController {

    Logger logger = LoggerFactory.getLogger(SudokuController.class);

    private FieldPane currentSelectedField; //reference to current selected field on sudoku
    private MenuController menuController;
    private ResourceBundle bundle = ResourceBundle.getBundle("bundle");
    private Locale locale;

    @FXML
    private GridPane sudokuBoardGrid;
    private Game game;
    private Level level;
    @FXML private MenuItem backToMenu;
    @FXML private MenuItem exit;
    @FXML private Button checkButton;
    private PopOutWindow popOutWindow = new PopOutWindow();

    @FXML
    public void onBackToMenuClick() {
        Stage stage = (Stage) this.sudokuBoardGrid.getScene().getWindow();
        stage.close();
        WindowManager.getInstance().getPrimaryStage().show();
    }

    @FXML
    public void onExitClick() {
        Stage stage = (Stage) this.sudokuBoardGrid.getScene().getWindow();
        stage.close();
        logger.info(bundle.getString("_exit"));
    }

    private boolean isSudokuSolved() {
        if (game.getSudokuBoard().checkBoard()) {
            return true;
        }
        return false;
    }

    public void onCheckClick() {
        if (!isInputValid()) {
            popOutWindow.messageBox(bundle.getString("_warning"),
                    bundle.getString("_war1"), Alert.AlertType.WARNING);
        } else {
            if (isSudokuSolved()) {
                popOutWindow.messageBox("",
                        bundle.getString("_solved"), Alert.AlertType.INFORMATION);
            } else {
                popOutWindow.messageBox("",
                        bundle.getString("_lost"), Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void initialize() {
        this.currentSelectedField = null;
        this.game = WindowManager.getInstance().getGame();
        this.initializeSudokuGrid();
        if (this.checkButton.getText().equals("Check")) {
            locale = new Locale("eng");
        } else {
            locale = new Locale("pl");
        }
        bundle =  ResourceBundle.getBundle("bundle", locale);
        //logger.debug("MainSudokuWindowController initialized.");
    }

    private void initializeSudokuGrid() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                GridPane grid = new GridPane();
                grid.getStyleClass().add("sudoku-grid-border");
                GridPane.setConstraints(grid, x, y);
                sudokuBoardGrid.getChildren().add(grid);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int computedX = x * 3 + i;
                        int computedY = y * 3 + j;
                        FieldPane pane = this.createFieldPane(computedX, computedY);
                        GridPane.setConstraints(pane, i, j);
                        grid.getChildren().add(pane);
                        try {
                            Bindings.bindBidirectional(pane.getProperty(),
                                    JavaBeanIntegerPropertyBuilder.create()
                                            .bean(this.game.getSudokuBoard()
                                            .get(computedX, computedY)).name("value")
                                            .build(), new NumberStringConverter());
                        } catch (NoSuchMethodException e) {
                            //throw new BindingFailedException("BindingFailedException");
                        }
                        pane.getProperty().addListener(new ChangeListener<String>() {
                            @Override
                            public void changed(ObservableValue<? extends String> observableValue,
                                                String s, String t1) {
                                game.getSudokuBoard().set(computedX, computedY, pane.converter());
                            }
                        });
                    }
                }
            }
        }
    }


    private FieldPane createFieldPane(int x, int y) {
        return new FieldPane(x, y, Integer.toString(this.game.getSudokuBoard().get(x, y)),
                this.game.getSudokuBoard().getField(x, y).getChangeable());
    }

    private boolean isInputValid() {
        boolean isValid = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                GridPane grid = (GridPane) sudokuBoardGrid.getChildren().get(i);
                String fieldValue = ((TextField) grid.getChildren().get(j)).getText();
                if (!((fieldValue.matches("[1-9]")) || (fieldValue.equals("")))) {
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    @FXML
    private void saveSudokuToFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(bundle.getString("_local"));
            fileChooser.setInitialFileName("Sudoku");
            fileChooser.getExtensionFilters().add(new FileChooser
                    .ExtensionFilter(bundle.getString("_defExtension"), "*.txt"));
            File file = fileChooser.showSaveDialog(this.sudokuBoardGrid.getScene().getWindow());
            if (file != null) {
                SudokuBoardDaoFactory toFile = new SudokuBoardDaoFactory();
                FileSudokuBoardDao dao = (FileSudokuBoardDao) toFile.getFileDao(file.getName());
                dao.write(game.getSudokuBoard());
            }
        } catch (FileOperationException e) {
            logger.error(e.getLocalizedMessage());
            PopOutWindow.messageBox(bundle.getString("_warning"),
                    bundle.getString("_fileWindow"), Alert.AlertType.WARNING);
        }

    }

    @FXML
    public void onSaveDBStateButtonClicked() {
        File file;
        FileChooser fileChooser = new FileChooser();
        try {
            file = fileChooser.showSaveDialog(this.sudokuBoardGrid.getScene().getWindow());
            SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
            logger.info(file.getName());
            JdbcSudokuBoardDao jdbc = (JdbcSudokuBoardDao) factory.getJdbcDao(file.getName());

            jdbc.write(this.game.getSudokuBoard());
            PopOutWindow.messageBox("",
                    bundle.getString("_sucessSave"), Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            PopOutWindow.messageBox(bundle.getString("_warning"),
                    bundle.getString("_fileWindow"), Alert.AlertType.WARNING);
        }
    }


}
