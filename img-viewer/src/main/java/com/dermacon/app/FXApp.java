package com.dermacon.app;

import com.dermacon.app.worker.Manager;
import com.dermacon.app.worker.Renderer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader =
                new FXMLLoader(FXApp.class.getResource("viewer.fxml"));
//        fxmlLoader.setController(new FXMLController());
        Parent parent = fxmlLoader.load();

//        FXMLController controller = fxmlLoader.getController();
//        controller.setProjectController(this.projectController);
//        SpringApiController.setJFXController(controller);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
//        controller.setImgVwResponsive(stage);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            FileHandler fileHandler = new FileHandler(args);
            TerminalUI ui = new TerminalUI(fileHandler.getBookmarks(), fileHandler);
            Bookmark user_select = ui.waitForUserSelection();
            Renderer renderer = new Manager(user_select);
        } catch (IOException e) {
//             todo
            e.printStackTrace();
        }
        launch(args);
    }
}
