package ihm.sokoban;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;



public class SokobanController implements Initializable{


    private Stage MenuPrincipal = null;
//*----
// MENU
// ---- 
//*/

    @FXML
    private void ButtonPlay(javafx.event.ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/sokoban/SelectMode.fxml"));
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
    
    @FXML
    private void AffichageCredits() {
        Alert Credits = new Alert(Alert.AlertType.INFORMATION);
        Credits.setTitle("Crédits");
        Credits.setHeaderText("Crédits du projet Sokoban");
        Credits.setContentText("Le jeu original a été créer par Hiroyuki Imabayashi en 1982 \n\nMais ce dérivé a été réalisé par Antoine Meunier dans le cadre du TP d'IHM à l'IUT de Blagnac.");
        

        Credits.showAndWait();
    }

    @FXML
    private void AffichageAide() {
        Alert Aide = new Alert(Alert.AlertType.INFORMATION);
        Aide.setTitle("Aide");
        Aide.setHeaderText("Aide pour Sokoban");
        Aide.setContentText("Utilisez les touche Z, Q, S et D du clavier pour déplacer le personnage et poussez les caisses sur les emplacements indiqués. Le but du jeu est de déplacer toutes les caisses sur les cibles en un minimum de mouvements. Faites attention à ne pas vous retrouver coincé !\n\nSi toutefois vous trouvez un bug, veuillez contacter le réalisateur de ce projet. \n\nPseudo Discord : megalitht");
        
        Aide.showAndWait();
    
    }

    @FXML
    private void actionQuitter() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Fermeture de l'application");
        confirmation.setHeaderText("Confirmation");
        confirmation.setContentText("Voulez-vous vraiment quitter ?");

        confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional <ButtonType> reponse = confirmation.showAndWait();

        if (reponse.isPresent() && reponse.get() == ButtonType.YES) {
            Platform.exit();
        }
    }
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
