package ihm.sokoban;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;


public class SokobanSelectMode {
    @FXML
    private void ButtonAdv(javafx.event.ActionEvent event) {

        List<String> niveaux = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            niveaux.add("Niveau " + i);
        }

        ChoiceDialog<String> jeuDialog = new ChoiceDialog<>("Niveau 1", niveaux);
        jeuDialog.setTitle("Lancement du jeu");
        jeuDialog.setHeaderText("Quel niveau d'aventure voulez-vous jouer ?");
        jeuDialog.setContentText("Sélectionnez votre niveau :");

        Optional<String> reponse = jeuDialog.showAndWait();

        if (reponse.isPresent()) {
            String niveauSelectionne = reponse.get();

            int indexNiveau = 0;

            switch (niveauSelectionne) {
                case "Niveau 1": indexNiveau = 0; break;
                case "Niveau 2": indexNiveau = 1; break;
                case "Niveau 3": indexNiveau = 2; break;
                case "Niveau 4": indexNiveau = 3; break;
                case "Niveau 5": indexNiveau = 4; break;
                case "Niveau 6": indexNiveau = 5; break;
                case "Niveau 7": indexNiveau = 6; break;
                case "Niveau 8": indexNiveau = 7; break;
                case "Niveau 9": indexNiveau = 8; break;
                case "Niveau 10": indexNiveau = 9; break;
                default: return; 
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/sokoban/level.fxml"));
                Parent root = loader.load();

                SokobanLevelController niveauController = loader.getController();

                niveauController.initialiserPartie(indexNiveau, false);

                javafx.scene.Node source = (javafx.scene.Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();

                javafx.scene.Scene sceneJeu = new javafx.scene.Scene(root);
                stage.setScene(sceneJeu);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de chargement");
                alert.setHeaderText("Impossible de lancer le niveau");
                alert.setContentText("Le fichier de l'interface graphique du niveau n'a pas pu être chargé.");
                alert.showAndWait();
            }
        }
    }



    @FXML
    private void ButtonTuto(javafx.event.ActionEvent event) {


        List<String> niveaux = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            niveaux.add("Niveau " + i);
        }

        ChoiceDialog<String> jeuDialog = new ChoiceDialog<>("Niveau 1", niveaux);
        jeuDialog.setTitle("Lancement du jeu");
        jeuDialog.setHeaderText("Quel niveau du tutoriel voulez-vous jouer ?");
        jeuDialog.setContentText("Sélectionnez votre niveau :");

        Optional<String> reponse = jeuDialog.showAndWait();

        if (reponse.isPresent()) {
            String niveauSelectionne = reponse.get();

            int indexNiveau = 0;

            switch (niveauSelectionne) {
                case "Niveau 1": indexNiveau = 0; break;
                case "Niveau 2": indexNiveau = 1; break;
                case "Niveau 3": indexNiveau = 2; break;
                case "Niveau 4": indexNiveau = 3; break;
                case "Niveau 5": indexNiveau = 4; break;
                default: return; 
            }

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/sokoban/level.fxml"));
                Parent root = loader.load();

                SokobanLevelController niveauController = loader.getController();

                niveauController.initialiserPartie(indexNiveau, true);

                javafx.scene.Node source = (javafx.scene.Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();

                javafx.scene.Scene sceneJeu = new javafx.scene.Scene(root);
                stage.setScene(sceneJeu);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de chargement");
                alert.setHeaderText("Impossible de lancer le niveau");
                alert.setContentText("Le fichier de l'interface graphique du niveau n'a pas pu être chargé.");
                alert.showAndWait();
            }
        }
    }


    @FXML
    private void actionReturnMenu(javafx.event.ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/sokoban/MenuPrincipal.fxml"));
            Parent root = loader.load();

            javafx.scene.Node source = (javafx.scene.Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            javafx.scene.Scene sceneMode = new javafx.scene.Scene(root);
            stage.setScene(sceneMode);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Navigation impossible");
            alert.setContentText("Impossible de charger le menu principal.");
            alert.showAndWait();
        }
        
    }
}



