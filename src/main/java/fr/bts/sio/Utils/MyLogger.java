package fr.bts.sio.Utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    private static final Logger logger = Logger.getLogger(MyLogger.class.getName());
    public static void log(String message, int code) {
        try {
            // Create a FileHandler to write to "log.log"
            FileHandler fileHandler = new FileHandler("log.log", true); // 'true' to append instead of overwrite
            fileHandler.setFormatter(new SimpleFormatter()); // Use a simple text formatter
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Optional: don't show logs in console
            if(code == 0) {
                logger.warning(message);
            }
                logger.warning(message+ " " +code+ "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
