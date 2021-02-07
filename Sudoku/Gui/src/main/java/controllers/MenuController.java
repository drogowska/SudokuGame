package controllers;

import exceptions.DaoException;
import exceptions.FileOperationException;
import expections.FXMLLoadException;
import helpers.Game;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import helpers.PopOutWindow;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.RadioButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.first.firstjava.*;

import javax.swing.*;


public class MenuController {

    Logger logger = LoggerFactory.getLogger(MenuController.class);

    @FXML private Button easyButton;
    @FXML private Button mediumButton;
    @FXML private Button hardButton;
    private BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
    private Locale locale;
    private ResourceBundle bundle = ResourceBundle.getBundle("bundle");
    @FXML private RadioButton polishButton;
    @FXML private RadioButton englishButton;


    @FXML
    public void initialize() {
        if (easyButton.getText().equals("Easy")) {
            englishButton.setSelected(true);
            locale = new Locale("eng");

        } else {
            polishButton.setSelected(true);
            locale = new Locale("pl");
        }
        bundle =  ResourceBundle.getBundle("bundle", locale);
    }

    @FXML
    public void onExitButtonClick() {
        this.closeApp();
        logger.info(bundle.getString("_exit"));
    }

    @FXML
    public void onEasyButtonClick() throws IOException {
        this.runGame(new Easy(), new SudokuBoard(solver), false);
        logger.info(bundle.getString("_easy"));

    }

    @FXML
    public void onMediumButtonClick() throws IOException {
        this.runGame(new Medium(), new SudokuBoard(solver), false);
        logger.info(bundle.getString("_medium"));
    }

    @FXML
    public void onHardButtonClick() throws IOException {
        this.runGame(new Hard(), new SudokuBoard(solver), false);
        logger.info(bundle.getString("_hard"));
    }

    private void closeApp() {
        Stage stage = (Stage) easyButton.getScene().getWindow();
        stage.close();
    }

    private void runGame(final Level level, final SudokuBoard sudokuBoard,
                         boolean isLoaded) {
        MainSudokuWindow window = new MainSudokuWindow();
        try {
            WindowManager.getInstance().setGame(new Game(level, sudokuBoard, isLoaded));
            window.start();
        } catch (FXMLLoadException e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        }
        Stage stage = (Stage) easyButton.getScene().getWindow();
        WindowManager.getInstance().setPrimaryStage(stage);
        stage.hide();
    }

    @FXML
    private void loadSudokuFromFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(bundle.getString("_selectFile"));
            fileChooser.getExtensionFilters().add(new FileChooser
                    .ExtensionFilter(bundle.getString("_defExtension"), "*.txt"));
            File file = fileChooser.showOpenDialog(this.easyButton.getScene().getWindow());
            if (file != null) {
                SudokuBoardDaoFactory toFile = new SudokuBoardDaoFactory();
                FileSudokuBoardDao dao = (FileSudokuBoardDao) toFile.getFileDao(file.getName());
                SudokuBoard board = dao.read();
                this.runGame(null, board, true);
            }
        } catch (FileOperationException e) {
            logger.error(bundle.getString("_fNotFound"));
            e.printStackTrace();
            PopOutWindow.messageBox(bundle.getString("_warning"),
                    bundle.getString("_fileWindow"), Alert.AlertType.WARNING);
        }

    }

    @FXML
    public void changeLanguage() throws FXMLLoadException {
        if (this.polishButton.isSelected()) {
            locale = new Locale("pl");
        } else {
            locale = new Locale("eng");
        }
        Scene scene = this.polishButton.getScene();
        bundle = ResourceBundle.getBundle("bundle", locale);
        Parent root = null;
        try {
            root = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"),
                    bundle);
        } catch (IOException e) {
            logger.error(bundle.getString("_fxmlMainWin"));
            throw new FXMLLoadException("FXMLLoadException");
        }
        scene.setRoot(root);
        WindowManager.getInstance().setCurrentLocale(locale);
    }

    @FXML
    private void printAuthors() {
        ResourceBundle b = ResourceBundle.getBundle("helpers.NameOfAuthors", locale);
        PopOutWindow.messageBox(bundle.getString("Authors"),
                (b.getObject("1. ") + "\n" + b.getObject("2. ")),
                Alert.AlertType.INFORMATION);
    }

    @FXML
    private void LoadFromDataBase() {
        File file;
        FileChooser fileChooser = new FileChooser();
        try {
            file = fileChooser.showSaveDialog(this.polishButton.getScene().getWindow());
            SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
            JdbcSudokuBoardDao jdbc = (JdbcSudokuBoardDao) factory.getJdbcDao(file.getName());
            SudokuBoard sudokuBoard = jdbc.read();
            runGame(null,sudokuBoard,true);
        } catch (NullPointerException | DaoException e) {
            //logger.warning("Cannot read from database!");
            e.printStackTrace();
            PopOutWindow.messageBox(bundle.getString("_warning"),
                    bundle.getString("_fileWindow"), Alert.AlertType.WARNING);
        }

    }

}
