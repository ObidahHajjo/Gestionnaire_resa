package fr.bts.sio;

// Import des classes nécessaires
import fr.bts.sio.Config.Config;
import fr.bts.sio.Config.ConfigManager;
import fr.bts.sio.Controllers.ConnexionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

// Classe principale de l'application JavaFX
public class App extends Application {

    // Méthode appelée au démarrage de l'application JavaFX
    @Override
    public void start(Stage stage) throws Exception {
        // Chargement de l'interface graphique depuis le fichier connexion.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("connexion.fxml"));

        // Configuration de la base de données H2 (locale)
        String url = "jdbc:h2:./testdb";
        Class.forName("org.h2.Driver"); // Chargement du driver JDBC H2
        Connection connection = DriverManager.getConnection(url); // Connexion à la base de données

        // Configuration du contrôleur personnalisé pour injecter la connexion dans le ConnexionController
        fxmlLoader.setControllerFactory(param -> {
            if (param == ConnexionController.class) {
                return new ConnexionController(connection); // Injection de la connexion SQL
            } else {
                try {
                    return param.getDeclaredConstructor().newInstance(); // Création des autres contrôleurs par défaut
                } catch (Exception e) {
                    throw new RuntimeException(e); // Gestion des erreurs lors de l'instanciation
                }
            }
        });

        // Création de la scène JavaFX avec la fenêtre et ses dimensions
        Scene scene = new Scene(fxmlLoader.load(), 1280, 832);

        // Configuration de la fenêtre principale
        stage.setTitle("Login"); // Titre de la fenêtre
        Image icon = new Image(getClass().getResourceAsStream("/Images/loginIcon.png")); // Chargement de l'icône
        stage.getIcons().add(icon); // Application de l'icône
        stage.setScene(scene); // Définition de la scène
        stage.show(); // Affichage de la fenêtre
    }

    // Point d'entrée principal de l'application Java (avant JavaFX)
    public static void main(String[] args) throws Exception {
        // Chargement du fichier de configuration JSON (TVA, etc.)
        ConfigManager.load("Storage/config.json");

        // Lecture et affichage de la TVA actuelle
        Config config = ConfigManager.get();
        System.out.println(config.getTva());

        // Modification de la TVA et sauvegarde dans le fichier JSON
        config.setTva(20);
        ConfigManager.save();

        // Affichage de la TVA après modification
        System.out.println(ConfigManager.get().getTva());

        // Démarrage de l'application JavaFX (lance la méthode start)
        launch();
    }
}

