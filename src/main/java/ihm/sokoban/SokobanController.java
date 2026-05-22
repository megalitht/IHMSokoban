package ihm.sokoban;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private void ButtonPlay() {
        System.out.println("Lancement du tutoriel...");

    }
    @FXML
    private void AffichageCredits() {
        System.out.println("Affichage des crédits...");
        Alert Credits = new Alert(Alert.AlertType.INFORMATION);
        Credits.setTitle("Crédits");
        Credits.setHeaderText("Crédits du projet Sokoban");
        Credits.setContentText("Ce projet a été réalisé par Antoine Meunier dans le cadre du TP d'IHM à l'IUT de Blagnac.");
        

        Credits.showAndWait();
    }

    @FXML
    private void AffichageAide() {
        System.out.println("Affichage de l'aide...");
        Alert Aide = new Alert(Alert.AlertType.INFORMATION);
        Aide.setTitle("Aide");
        Aide.setHeaderText("Aide pour Sokoban");
        Aide.setContentText("Utilisez les touche Z, Q, S et D du clavier pour déplacer le personnage et poussez les caisses sur les emplacements indiqués. Le but du jeu est de déplacer toutes les caisses sur les cibles en un minimum de mouvements. Faites attention à ne pas vous retrouver coincé !\n\nSi toutefois vous trouvez un bug, veuillez contacter le réalisateur de ce projet. Pseudo Discord : megalitht");
        
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
        } else {
            System.out.println("On continue");
        }
    }
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Initialisation du contrôleur SokobanController...");
        
	}
}
