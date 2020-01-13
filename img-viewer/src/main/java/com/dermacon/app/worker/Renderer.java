package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.jfx.FXMLController;

/**
 * Opens a pseudo priority queue for assignements which consists of
 * page numbers that should be rendered by the worker. The workers
 * are managed by the Renderer itself and rely on a semaphore blocking
 * mechanism of the queue. If there are no more assignments left, instead
 * of stopping these worker threads wait for the next assignments.
 */
public interface Renderer {

    /**
     * Initializes threads with a given controller. Whenever one of the
     * threads finishes rendering an assignment it will call the given
     * controller instance to update the gui if necessary
     * @param controller gui controller instance to update the gui with if
     *                   necessary
     */
    void setController(FXMLController controller);

    /**
     * todo
     * Adds the given bookmark (as an assignment) to the beginning of the
     * queue (highest priority) and adds the surrounding page numbers afterwards.
     * A constant determines how many page numbers should be rendered.
     *
     * Example: surrounding == 2;
     * 1. Add page 5: queue == [5, 6, 4, 7, 3]
     * 2. Add page 8: queue == [8, 9, 7, 10, 6, 5, 6, 4, 7, 3]
     */
    void renderPageIntervall(Bookmark bookmark);

    /**
     * Stops the whole rendering process
     */
    void stop();

    /**
     * Necessary to clear render stack when calling the goto function of the
     * viewer. When this method is called we do not need the current page
     * interval that's in the process of being rendered because we jump to an
     * entirely different part of the document anyway.
     */
    void clearPipeline();

}
