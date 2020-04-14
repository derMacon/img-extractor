import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;

public class Other {

    public void method() {
        System.out.println("other method");

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
