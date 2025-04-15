package fr.bts.sio;

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

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("connexion.fxml"));
        String url = "jdbc:h2:./testdb";
        Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(url);
        fxmlLoader.setControllerFactory(param -> {
            if (param == ConnexionController.class) {
                return new ConnexionController(connection);
            } else {
                try {
                    return param.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Scene scene = new Scene(fxmlLoader.load(), 1280, 832);
        stage.setTitle("Login");
        Image icon = new Image(getClass().getResourceAsStream("/Images/loginIcon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        /* DB */
        //DB db = new DB();
        //db.start();

        /* TVA */
        ConfigManager.load("Storage/config.json");          // interpreter le fichier config.json
        Config config = ConfigManager.get();
        System.out.println(config.getTva());                     // Lire le TVA
        config.setTva(20);                                       // Modifier le TVA
        ConfigManager.save();                                    // Sauvgarder la modification
        System.out.println(ConfigManager.get().getTva());

        launch();
    }

}
