package ihm.sokoban;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * Application JavaFX principale pour le jeu Sokoban.
 */
public class SokobanApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Chaer pour un test : charger directement le stage 1 du tutoriel
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../resource/ihm/sokoban/MenuPrincipal.fxml"));
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement du fichier FXML : ");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main2(String[] args) {
        launch(args);
    }
}
