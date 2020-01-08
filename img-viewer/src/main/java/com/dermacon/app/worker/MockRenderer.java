package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.jfx.FXMLController;

public class MockRenderer  implements Renderer {

    @Override
    public void setController(FXMLController controller) {

    }

    @Override
    public void renderPageIntervall(Bookmark bookmar) {

    }

    @Override
    public void stop() {
        System.out.println("stop");

    }
}
