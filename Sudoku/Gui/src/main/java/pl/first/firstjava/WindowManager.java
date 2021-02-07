package pl.first.firstjava;

import helpers.Game;
import java.util.Locale;
import javafx.stage.Stage;


public class WindowManager {

        private Stage primaryStage;         //current stage
        private Game game;                  //current game
        private Locale currentLocale;

        public Game getGame() {
            return game;
        }

        public void setGame(final Game game) {
            this.game = game;
        }

        public void setPrimaryStage(final Stage primaryStage) {
            this.primaryStage = primaryStage;
        }

        public Stage getPrimaryStage() {
            return primaryStage;
        }

        public Locale getCurrentLocale() {
            return currentLocale;
        }

        public void setCurrentLocale(Locale currentLocale) {
            this.currentLocale = currentLocale;
        }

        private static WindowManager manager = new WindowManager();

        private WindowManager() {
        }

        public static WindowManager getInstance() {
            return manager;
        }


}
