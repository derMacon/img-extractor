package com.dermacon.app.logik;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.ClipboardImage;
import com.dermacon.app.jfx.FXMLController;
import com.dermacon.app.worker.Renderer;

public class ViewerOrganizer implements Organizer {

    private final Bookmark bookmark;
    private final Renderer renderer;

    public ViewerOrganizer(Bookmark bookmark, Renderer renderer) {
        renderer.renderPageIntervall(bookmark);

        this.bookmark = bookmark;
        this.renderer = renderer;
    }

    @Override
    public void prevPage() {
        bookmark.decPageIdx();
        renderer.renderPageIntervall(bookmark); // todo copy bm???
    }

    @Override
    public void nextPage() {
        bookmark.incPageIdx();
        renderer.renderPageIntervall(bookmark);// todo copy bm???
    }

    @Override
    public void gotoPage(int page) {
        bookmark.setCurrPageIdx(page - 1);
        renderer.clearPipeline();
        renderer.renderPageIntervall(bookmark);// todo copy bm???
    }
}
