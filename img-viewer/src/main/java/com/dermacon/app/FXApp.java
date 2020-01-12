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

//    private boolean running = true;

    //    private static Bookmark bookmark;
//    private static Organizer organizer;
//    private static PropertyValues props;
    private static Renderer renderer;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader =
                new FXMLLoader(FXApp.class.getResource("viewer.fxml"));
        fxmlLoader.setController(new FXMLController());
        Parent parent = fxmlLoader.load();

        FXMLController controller = fxmlLoader.getController();
//        organizer.setFXController(controller);
        renderer.setController(controller);
//        controller.setBookmark(bookmark);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
//        controller.setImgVwResponsive(stage);
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
            UserInterface ui = new TerminalUI(fileHandler.getBookmarks(),
                    props);

            Bookmark bookmark = ui.waitForUserSelection();

            if (bookmark == null) {
                bookmark = fileHandler.openNewBookmark();
                fileHandler.prependsHistory(bookmark);
            }

            // mock data for testing
//            props = new MockProperties();
//            UserInterface ui = new MockTerminalUI();
//            bookmark = new MockBookmark();
//            Renderer renderer = new MockRenderer();

//            Consumer<File> updater = new Updater();
            renderer = new RenderManager(props);
            Organizer organizer = new ViewerOrganizer(bookmark, renderer);
            Thread runner = new Thread(new HookRunner(organizer, props));
            runner.start();

            System.out.println("launch");
            launch(args);

//            ui.waitForExit();
            renderer.stop();
//            runner.join();

            // todo set history csv file
            fileHandler.prependsHistory(bookmark);




            System.out.println("user terminated program");
            System.exit(0);

        } catch (IOException | CSVException e) {
//             todo
            e.printStackTrace();
        }

    }

}
