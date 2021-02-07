package pl.first.firstjava;

import expections.FXMLLoadException;
import helpers.Game;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        Locale locale = new Locale("pl", "PL");
        WindowManager.getInstance().setCurrentLocale(locale);
        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("bundle", locale);
        try {
          root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/menu.fxml"),bundle);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
            throw new FXMLLoadException("FXMLLoadException", e);
        }
        primaryStage.setTitle(bundle.getString("_windowTitle"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
        logger.info(bundle.getString("_start"));


    }



}
