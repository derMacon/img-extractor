package com.dermacon.app.logik;

import com.dermacon.app.jfx.FXMLController;

/**
 * Class holding it all together.
 * Delegates the appropriate data to the actual renderer and the different
 * isntances for copying to the clipboard and updating the bookmark.
 */
public interface Organizer {

    /**
     * Sets the fxml controller for the preview panel so that this organizer
     * instance can call the controller to refresh the view if necessary.
     * Needs to be in a seperate setter because the organizer and the
     * controller are constructed at different times due to the nature of the
     * javafx framework
     * @param controller fxml controller of the preview panel
     */
//    public void setFXController(FXMLController controller);

    /**
     * Renders the prev page (if inbound)
     * copies it to the systems clipboard
     * shows it in the preview window
     */
    public void prevPage();

    /**
     * Renders the next page (if inbound)
     * copies it to the systems clipboard
     * shows it in the preview window
     */
    void nextPage();


    /**
     * todo
     * @param page
     */
    void gotoPage(int page);

}
