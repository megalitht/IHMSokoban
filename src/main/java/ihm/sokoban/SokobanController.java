package ihm.sokoban;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;



public class SokobanController implements Initializable{


    private Stage MenuPrincipal = null;
//*----
// MENU
// ---- 
//*/

    @FXML
    private void ButtonPlay() {
        System.out.println("Lancement du jeu...");

        List<String> niveaux = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            niveaux.add("Niveau " + i);
        }

        ChoiceDialog<String> jeu = new ChoiceDialog<>("Niveau 1", niveaux);
        jeu.setTitle("Lancement du jeu");
        jeu.setHeaderText("Quel niveau voulez-vous jouer ?");
        jeu.setContentText("Sélectionnez votre niveau :");

        Optional<String> reponse = jeu.showAndWait();

        if (reponse.isPresent()) {
            String niveauSelectionne = reponse.get();
            System.out.println("Chargement du " + niveauSelectionne + "...");

            switch (niveauSelectionne) {
                case "Niveau 1":
                    break;
                case "Niveau 2":
                    break;
                case "Niveau 3":
                    break;
                case "Niveau 4":
                    break;
                case "Niveau 5":
                    break;
                case "Niveau 6":
                    break;
                case "Niveau 7":
                    break;
                case "Niveau 8":
                    break;
                case "Niveau 9":
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Aucun niveau sélectionné (Action annulée).");
        }
    }
    
    @FXML
    private void AffichageCredits() {
        System.out.println("Affichage des crédits...");
        Alert Credits = new Alert(Alert.AlertType.INFORMATION);
        Credits.setTitle("Crédits");
        Credits.setHeaderText("Crédits du projet Sokoban");
        Credits.setContentText("Le jeu original a été créer par Hiroyuki Imabayashi en 1982 \n\nMais ce dérivé a été réalisé par Antoine Meunier dans le cadre du TP d'IHM à l'IUT de Blagnac.");
        

        Credits.showAndWait();
    }

    @FXML
    private void AffichageAide() {
        System.out.println("Affichage de l'aide...");
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
        } else {
            System.out.println("On continue");
        }
    }
    
    
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Initialisation du contrôleur SokobanController...");
        
	}
}
