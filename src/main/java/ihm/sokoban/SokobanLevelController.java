package ihm.sokoban;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import ihm.sokoban.model.Direction;
import ihm.sokoban.model.JeuSokoban;
import ihm.sokoban.model.ResultatMouvement;
import ihm.sokoban.model.TypeCase;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class SokobanLevelController implements Initializable {

    @FXML
    private GridPane maGrille;

    private JeuSokoban jeu;

    // Associe chaque TypeCase à son objet Image correspondant
    private final Map<TypeCase, Image> textures = new EnumMap<>(TypeCase.class);

    // Indique si le niveau actuel est un tutoriel ou un niveau d'aventure
    boolean isTutorial = false;

    // Mémorise la direction actuelle du joueur (par défaut vers le bas)
    private Direction orientationJoueur = Direction.BAS;

    // Dictionnaire pour les sprites du joueur par rapport a sa direction
    private final Map<Direction, Image> texturesJoueur = new EnumMap<>(Direction.class);


    public void chargerTextures() {
        textures.put(TypeCase.MUR, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Blocks/block_01.png"), 40, 40, true, true));
        textures.put(TypeCase.SOL, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Ground/ground_06.png"), 40, 40, true, true));
        textures.put(TypeCase.CIBLE, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Environment/environment_06.png"), 40, 40, true, true));
        textures.put(TypeCase.CAISSE, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Crates/crate_02.png"), 40, 40, true, true));
        textures.put(TypeCase.CAISSE_SUR_CIBLE, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Crates/crate_27.png"), 40, 40, true, true));
        textures.put(TypeCase.JOUEUR_SUR_CIBLE, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Player/player_05.png"), 40, 40, true, true));
        // Chargement des 4 orientations du joueur
        texturesJoueur.put(Direction.BAS, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Player/player_05.png"), 40, 40, true, true));
        texturesJoueur.put(Direction.HAUT, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Player/player_08.png"), 40, 40, true, true));
        texturesJoueur.put(Direction.DROITE, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Player/player_17.png"), 40, 40, true, true));
        texturesJoueur.put(Direction.GAUCHE, new Image(getClass().getResourceAsStream("/ihm/sokoban/assets/Player/player_20.png"), 40, 40, true, true));
    }


    public void initialiserPartie(int indexNiveau, boolean isTuto) {
        chargerTextures();
        this.isTutorial = isTuto;
        // initialisation des partie en mode aventure
        if (isTutorial == false ){
            this.jeu = new JeuSokoban(indexNiveau);
        } else {
            // initialisation des partie tutoriel
            this.jeu = new JeuSokoban(ihm.sokoban.util.NiveauxTutoriel.getNiveaux(), 
            ihm.sokoban.util.NiveauxTutoriel.getNoms(), 
            indexNiveau);
        }

        refraichirAffichage();
        configurerControlesClavier();
        actualiserTitreFenetre();

        Platform.runLater(() -> {
            Stage stage = (Stage) maGrille.getScene().getWindow();
            actualiserTitreFenetre();
        });
    }


    private void actualiserTitreFenetre() {
        Platform.runLater(() -> {
            Stage stage = (Stage) maGrille.getScene().getWindow();
            stage.setTitle("Sokoban - Niveau " + (jeu.getNiveauCourant() + 1) + " - " + jeu.getNomNiveauCourant());
        });
    }


    

    private void actualiserTitreFenetreM(){
        Platform.runLater(() -> {
            Stage stage = (Stage) maGrille.getScene().getWindow();
            stage.setTitle("Sokoban");
        }); 
    }


    private void refraichirAffichage() {
        maGrille.getChildren().clear();

        if (jeu == null) {
            return;
        }

        int lignes = jeu.getNbLignes();
        int colonnes = jeu.getNbColonnes();

        for (int l = 0; l < lignes; l++) {
            for (int c = 0; c < colonnes; c++) {
                
                TypeCase tc = jeu.getCase(l, c);
                
                // Si la case est vide, inutile de charger des composants graphiques
                if (tc == TypeCase.VIDE) {
                    continue;
                }

                StackPane conteneurCase = new StackPane();

                if (tc == TypeCase.MUR) {
                    Image imgMur = textures.get(TypeCase.MUR);
                    if (imgMur != null) {
                        conteneurCase.getChildren().add(new ImageView(imgMur));
                    }
                } else {
                    // 1. Calque de fond : on place systématiquement le sol
                    Image imgSol = textures.get(TypeCase.SOL);
                    if (imgSol != null) {
                        conteneurCase.getChildren().add(new ImageView(imgSol));
                    }

                    // Calque intermédiaire : si l'entité est sur une cible, on dessine la cible
                    if (tc == TypeCase.CAISSE_SUR_CIBLE || tc == TypeCase.JOUEUR_SUR_CIBLE) {
                        Image imgCible = textures.get(TypeCase.CIBLE);
                        if (imgCible != null) {
                            conteneurCase.getChildren().add(new ImageView(imgCible));
                        }
                    }

                    if (tc != TypeCase.SOL) {
                        Image imgElement;
                        
                        // Si c'est le joueur, on choisit l'image selon son orientation
                        if (tc == TypeCase.JOUEUR || tc == TypeCase.JOUEUR_SUR_CIBLE) {
                            imgElement = texturesJoueur.get(orientationJoueur);
                        } 
                        // Sinon, c'est une caisse ou autre chose, on prend l'image standard
                        else {
                            imgElement = textures.get(tc);
                        }

                        if (imgElement != null) {
                            conteneurCase.getChildren().add(new ImageView(imgElement));
                        }
                    }
                }

                // Ajout du conteneur de calques dans la grille JavaFX
                maGrille.add(conteneurCase, c, l);
            }
        }
    }


    @FXML
    private Label labelDeplacements;

    @FXML
    private Label labelPoussees;

    @FXML 
    private Label labelCaissesPlacees;


    private int nbdep = 0;
    private int nbPoussees = 0;
    private int nbVie = 3;


    private void labelDeplacements() {
        if (labelDeplacements != null) {
            labelDeplacements.setText("Déplacements : " + nbdep);
        }
    }


    private void configurerControlesClavier() {
        maGrille.sceneProperty().addListener((observable, oldScene, newScene) -> {
            Scene scene = maGrille.getScene();
            
            if (scene != null) {
                
                scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (!jeu.peutJouer()) {
                    return;
                }

                Direction dir = null;
                boolean actionSpeciale = false;

                switch (event.getCode()) {
                    case Z: case UP:    dir = Direction.HAUT; break;
                    case S: case DOWN:  dir = Direction.BAS; break;
                    case Q: case LEFT:  dir = Direction.GAUCHE; break;
                    case D: case RIGHT: dir = Direction.DROITE; break;
                    case R: jeu.reset(); nbdep = 0; nbPoussees = 0; actionSpeciale = true; break;



                    default: break;
                }

                if (dir != null) {
                    
                    this.orientationJoueur = dir;

                    ResultatMouvement resultat = jeu.deplacer(dir);

                    if (resultat != ResultatMouvement.BLOQUE) {
                        
                        nbdep++; 
                        
                        if (resultat == ResultatMouvement.POUSSE) {
                            nbPoussees++;
                        }
                        
                        refraichirAffichage();
                        
                        mettreAJourCompteurs();

                        verifierFinDePartie();
                    }
                    
                    event.consume();
                }
            });
        }
        });
    }  
    

    private void mettreAJourCompteurs() {
        labelDeplacements.setText("Déplacement : " + nbdep);
        labelPoussees.setText("Déplacement Caisse : " + nbPoussees);

        int caissesSurCible = 0;
        for (int l = 0; l < jeu.getNbLignes(); l++) {
            for (int c = 0; c < jeu.getNbColonnes(); c++) {
                if (jeu.getCase(l, c) == TypeCase.CAISSE_SUR_CIBLE) {
                    caissesSurCible++;
                }
            }
        }

        labelCaissesPlacees.setText("Caisse en place : " + caissesSurCible + " / " + jeu.getNbCaisses());
    }


    private void verifierFinDePartie() {        
        if (jeu.isNiveauTermine()) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(event -> {
                Platform.runLater(() -> {
                    afficherDialogueVictoire();
                });
            });
            pause.play();
            
        } else if (jeu.isPerdu()) {
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
            pause.setOnFinished(event -> {
                Platform.runLater(() -> {
                    nbVie = nbVie - 1;
                    affichageDialoguePerdu();
            });
            });     
            pause.play();
        }
    }


    private void afficherDialogueVictoire() {
        Alert alerteV = new Alert(Alert.AlertType.INFORMATION);
        alerteV.setTitle("Niveau terminé !");
        alerteV.setHeaderText("Félicitations !");
        alerteV.setContentText("Vous avez résolu ce niveau en " + nbdep + " déplacements.\nQue voulez-vous faire ?");

        ButtonType btnSuivant = new ButtonType("Niveau Suivant");
        ButtonType btnRecommencer = new ButtonType("Recommencer");
        ButtonType btnMenu = new ButtonType("Menu");

        if (jeu.estDernierNiveau()) {
            alerteV.setTitle("FIN !");
            alerteV.setHeaderText("Félicitations !");
            alerteV.setContentText("Vous avez résolu tout les niveaux du jeu.\nQue voulez-vous faire ?");
            
            alerteV.getButtonTypes().setAll(btnRecommencer, btnMenu);
        } else {
            alerteV.getButtonTypes().setAll(btnSuivant, btnRecommencer);
        }

        Optional<ButtonType> choix = alerteV.showAndWait();

        if (choix.isPresent()) {
            if (choix.get() == btnSuivant) {
                jeu.niveauSuivant();
                nbdep = 0;
                nbPoussees = 0;
                mettreAJourCompteurs();
                refraichirAffichage();
                actualiserTitreFenetre();
                

            } else if (choix.get() == btnRecommencer) {
                jeu.reset();
                nbdep = 0;
                nbPoussees = 0;
                mettreAJourCompteurs();
                refraichirAffichage();
                
            } else if (choix.get() == btnMenu) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/sokoban/MenuPrincipal.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) maGrille.getScene().getWindow();
                    Scene sceneMenu = new Scene(root);
                    stage.setTitle("Sokoban");
                    stage.setScene(sceneMenu);
                    stage.show();
                    actualiserTitreFenetreM();

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
    }


    private void affichageDialoguePerdu() {
        if (nbVie > 0){
            Alert alerteP = new Alert(Alert.AlertType.WARNING);
            alerteP.setTitle("Vous avez Perdu!");
            alerteP.setHeaderText("Vous avez Perdu!");
            alerteP.setContentText("Vous etes maintenant "+ nbVie+ " vies");
            

            ButtonType btnRecommencer = new ButtonType("Recommencer");
            alerteP.getButtonTypes().setAll(btnRecommencer);

            alerteP.showAndWait();

            
            jeu.reset();
            nbdep = 0;
            nbPoussees = 0;
            mettreAJourCompteurs();
            refraichirAffichage();

        } else {
            Alert alerteGameOver = new Alert(Alert.AlertType.ERROR);
            alerteGameOver.setTitle("Game Over");
            alerteGameOver.setHeaderText("Game Over!");
            alerteGameOver.setContentText("Vous avez épuisé toutes vos vies.\nVous allez être redirigé vers le menu principal.");
            
            ButtonType btnMenu = new ButtonType("Retour au Menu");
            alerteGameOver.getButtonTypes().setAll(btnMenu);
            
            alerteGameOver.showAndWait();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/sokoban/MenuPrincipal.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) maGrille.getScene().getWindow();
                Scene sceneMenu = new Scene(root);
                stage.setScene(sceneMenu);
                stage.show();
                actualiserTitreFenetre();

            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }

    }


    @FXML
    private void actionReturnMenu(){
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Retour Menu");
        confirmation.setHeaderText("Confirmation");
        confirmation.setContentText("Voulez-vous vraiment retouné au Menu. \n\nSi vous retourné au Menu, vous allez perdre votre progression");
        confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> reponse = confirmation.showAndWait();

        if (reponse.isPresent() && reponse.get() == ButtonType.YES) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/sokoban/MenuPrincipal.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) maGrille.getScene().getWindow();
                Scene sceneMenu = new Scene(root);
                Stage stageMenu = (Stage) maGrille.getScene().getWindow();
                stageMenu.setTitle("Sokoban");
                stage.setScene(sceneMenu);
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
    

    @FXML
    private void actionQuitter() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Fermeture de l'application");
        confirmation.setHeaderText("Confirmation");
        confirmation.setContentText("Voulez-vous vraiment quitter ?");
        confirmation.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

        Optional<ButtonType> reponse = confirmation.showAndWait();

        if (reponse.isPresent() && reponse.get() == ButtonType.YES) {
            Platform.exit();
        }
    }


    @FXML
    private void actionRecommencer() {
        jeu.reset();
        nbdep = 0;
        nbPoussees = 0;
        mettreAJourCompteurs();
        refraichirAffichage();
        
    }

    @FXML
    private void actionNiveauSuivant() {

        if (jeu.estDernierNiveau() == true){
            Alert alertF = new Alert(Alert.AlertType.ERROR);
            alertF.setTitle("FIN");
            alertF.setHeaderText("Fin du jeu");
            alertF.setContentText("Vous etes deja au dernier niveau ! \n\n Voulez-vous recommencer du début ou aller au Menu ?");
            ButtonType rec = new ButtonType("Recommencer");
            ButtonType menu = new ButtonType("Menu");
            alertF.getButtonTypes().setAll(rec, menu);

            Optional<ButtonType> reponse = alertF.showAndWait();

            if (reponse.isPresent() && reponse.get() == menu) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ihm/sokoban/MenuPrincipal.fxml"));
                    Parent root = loader.load();

                    Stage stage = (Stage) maGrille.getScene().getWindow();
                    Scene sceneMenu = new Scene(root);
                    stage.setScene(sceneMenu);
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
            if (reponse.isPresent() && reponse.get() == rec) {
                jeu.chargerNiveauParIndex(0);
            }
            
        } else { 
            jeu.niveauSuivant();

            nbdep = 0;
            nbPoussees = 0;
            mettreAJourCompteurs();
            refraichirAffichage();
            actualiserTitreFenetre();
        }
    
    }


    @FXML
    private void actionNiveauPrecedent() {
        jeu.niveauPrecedent();
        nbdep = 0;
        nbPoussees = 0;
        mettreAJourCompteurs();
        refraichirAffichage();
        actualiserTitreFenetre();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


}