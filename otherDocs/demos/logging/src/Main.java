import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        System.out.println("logging test");

        LOGGER.setLevel(Level.ALL);

        LOGGER.warning("warning");
        LOGGER.severe("severe");
        LOGGER.info("info");

        // these are note displayed in cl
        LOGGER.config("config");
        LOGGER.fine("fine");
        LOGGER.finer("finer");
        LOGGER.finest("finest");

    }

}
