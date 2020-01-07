package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.Bookmark;

public class MockRenderer  implements Renderer {

    @Override
    public void renderPageIntervall(Bookmark bookmar) {

    }

    @Override
    public void stop() {
        System.out.println("stop");

    }
}
