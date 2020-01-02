package com.dermacon.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxmlApp extends Application {

    private static final String FXML_NAME = "viewer";

//    private static final File

    public static void main( String[] args ) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader =
                new FXMLLoader(FxmlApp.class.getResource(FXML_NAME + ".fxml"));
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
}
