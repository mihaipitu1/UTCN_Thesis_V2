package commons.logger;


import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    private static final String logFileURL = "./output/logs/app.log";
    private static Logger logger = Logger.getLogger("SueC Editor Application");
    private static FileHandler handler;

    public LoggerConfig() {
        initComponents();
    }

    private void initComponents() {
        try {
            handler = new FileHandler(logFileURL);
            SimpleFormatter sf = new SimpleFormatter();
            handler.setFormatter(sf);
            logger.addHandler(handler);
            logger.setUseParentHandlers(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void infoLog(String tag, String msg) {
        logger.info(tag + " >> "+ msg);
    }

    public static void infoLog(String tag, String msg,Object[] params) {
        logger.log(Level.INFO,tag + " >> "+ msg,params);
    }

    public static void errorLog(String tag, String msg) {
        logger.severe(tag + " >> "+ msg);
    }

    public static void errorLog(String tag, String msg,Object[] params) {
        logger.log(Level.SEVERE,tag + " >> "+ msg,params);
    }
}
