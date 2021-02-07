package pl.first.firstjava;

import java.io.IOException;
import java.util.ResourceBundle;

import expections.FXMLLoadException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainSudokuWindow {

    Logger logger = LoggerFactory.getLogger(MainSudokuWindow.class);
    private Stage stage;
    private Scene scene;

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void start() throws FXMLLoadException {
        ResourceBundle bundle = ResourceBundle.getBundle("bundle",
                WindowManager.getInstance().getCurrentLocale());
        Parent root = null;
        this.stage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/sudoku.fxml"), bundle);
        } catch (IOException e) {
            logger.error(bundle.getString("_fxmlMainWin"));
            throw new FXMLLoadException("FXMLLoadException", e);
        }
        this.scene = new Scene(root);
        scene.getStylesheets().add("/style/stylesheet.css");
        this.stage.setTitle(bundle.getString("_windowTitle"));
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }
}
