package ihm.sokoban;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class SokobanApp extends Application {

    public static void main2(String[] args) {
		Application.launch(args);
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            FXMLLoader loader = new FXMLLoader(SokobanApp.class.getResource("/ihm/sokoban/MenuPrincipal.fxml"));
            BorderPane borderPane = loader.load();

            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sokoban");
            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}