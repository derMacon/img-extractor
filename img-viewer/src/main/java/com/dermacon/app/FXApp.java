package com.dermacon.app;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.fileio.CSVException;
import com.dermacon.app.fileio.FileHandler;
import com.dermacon.app.hook.HookRunner;
import com.dermacon.app.jfx.FXMLController;
import com.dermacon.app.logik.Organizer;
import com.dermacon.app.logik.TerminalUI;
import com.dermacon.app.logik.UserInterface;
import com.dermacon.app.logik.ViewerOrganizer;
import com.dermacon.app.worker.RenderManager;
import com.dermacon.app.worker.Renderer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXApp extends Application {

    private static final int WINDOW_HEIGHT = 1000;
    private static final int WINDOW_WIDTH = 1000;

    private static Renderer renderer;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader =
                new FXMLLoader(FXApp.class.getResource("viewer.fxml"));
        fxmlLoader.setController(new FXMLController());
        Parent parent = fxmlLoader.load();

        FXMLController controller = fxmlLoader.getController();
        renderer.setController(controller);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setHeight(WINDOW_HEIGHT);
        stage.setWidth(WINDOW_WIDTH);
        stage.setTitle("Clipboard Preview");
        controller.setImgVwResponsive(stage);
        stage.show();
    }

    /**
     * Main method of the whole application.
     * <p>
     * - loads up properties and terminal user interface / menu
     * - waits for user to select a displayed bookmark
     * - select a new bookmark via a file explorer if necessary
     * - inits renderer for the backround processing of the images
     * - inits organizer to delegate the user input regarding the pdf
     * navigation to the appropriate instances
     * - exits and joins threads
     *
     * @param args command line args, the user may specify a separate config
     *             file other than the default config.properties.
     */
    public static void main(String[] args) {
        try {
            FileHandler fileHandler = new FileHandler(args);
            PropertyValues props = fileHandler.getProps();
            UserInterface ui = new TerminalUI(fileHandler,
                    props);

            Bookmark bookmark = ui.waitForUserSelection();

            if (bookmark == null) {
                bookmark = fileHandler.openNewBookmark();
                fileHandler.prependsHistory(bookmark);
            }

            renderer = new RenderManager(props);
            Organizer organizer = new ViewerOrganizer(bookmark, renderer);
            Thread runner = new Thread(new HookRunner(organizer, props));
            runner.start();

            launch(args);

            renderer.stop();
//            runner.join();

            fileHandler.prependsHistory(bookmark);
            System.out.println("user terminated program");
            System.exit(0);
        } catch (IOException | CSVException e) {
            System.err.println("ups something went wrong - " + e.getMessage());
        }

    }

}
