package fr.bts.sio;

import fr.bts.sio.Config.Config;
import fr.bts.sio.Config.ConfigManager;
import fr.bts.sio.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    public static void main(String[] args) throws Exception {
        /* DB */
        DB db = new DB();
        db.start();

        /* TVA */
        ConfigManager.load("Storage/config.json");          // interpreter le fichier config.json
        Config config = ConfigManager.get();
        System.out.println(config.getTva());                     // Lire le TVA
        config.setTva(20);                                       // Modifier le TVA
        ConfigManager.save();                                    // Sauvgarder la modification
        System.out.println(ConfigManager.get().getTva());
    }
}
