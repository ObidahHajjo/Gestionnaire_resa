package fr.bts.sio.Config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static Config config;
    private static String configPath; // On garde le chemin du fichier
    private static final ObjectMapper mapper = new ObjectMapper();

    // Charger le fichier JSON
    public static void load(String path) throws IOException {
        configPath = path;
        config = mapper.readValue(new File(path), fr.bts.sio.Config.Config.class);
    }

    // Récupérer la config
    public static Config get() {
        return config;
    }

    // Sauvegarder la config dans le fichier JSON
    public static void save() throws IOException {
        if (config != null && configPath != null) {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(configPath), config);
        } else {
            throw new IllegalStateException("Config ou chemin non initialisé !");
        }
    }
}
