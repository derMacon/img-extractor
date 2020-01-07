package com.dermacon.app.worker;

public class MockRenderer  implements Renderer {
    @Override
    public void renderPageIntervall() {
        System.out.println("renderPageIntervall");
    }

    @Override
    public void stop() {
        System.out.println("stop");

    }
}
