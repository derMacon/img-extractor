package system;

import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public final class TMain extends Application {

    private Desktop desktop = Desktop.getDesktop();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage stage) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            openFile(file);
        }
        stage.setTitle("Choose Pdf-Document");
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            ex.printStackTrace();
//            Logger.getLogger(
//                    FileChooserSample.class.getName()).log(
//                    Level.SEVERE, null, ex
//            );
        }
    }
}