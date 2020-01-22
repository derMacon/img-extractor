package com.dermacon.app.jfx;

import com.dermacon.app.dataStructures.Bookmark;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * JFX Controller for the pdf gui
 */
public class FXMLController implements Initializable {

    private Bookmark bookmark;

    @FXML
    private BorderPane brdPn_container;

    @FXML
    private Label lbl;

    @FXML
    private ImageView imgVw_page;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl.setAlignment(Pos.CENTER);
        lbl.setFont(new Font("Cambria", 25));
    }

    public void setBookmark(Bookmark bookmark) {
        this.bookmark = bookmark;
    }

    /**
     * Sets width of the image view on the pane to fit its parent
     *
     * @param stage stage of the whole scene
     */
    public void setImgVwResponsive(Stage stage) {
        imgVw_page.fitWidthProperty().bind(stage.widthProperty());
        imgVw_page.fitHeightProperty().bind(stage.heightProperty());
    }

    /**
     * Update the guis image view with the current image and the label with
     * the current page number.
     */
    public synchronized void updateGui(File page, int pageNum) {
//        System.out.println("Thread: " + Thread.currentThread().getName() +
//                ", update gui with page: " + page.getAbsolutePath());
        Platform.runLater(() -> {

            while(!page.exists()) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("waiting for img: " + page);
            }

            if (page != null) {
                Image img = new Image(page.toURI().toString());
                imgVw_page.setImage(img);
                lbl.setText(String.valueOf(pageNum));
            }

        });
    }
}


