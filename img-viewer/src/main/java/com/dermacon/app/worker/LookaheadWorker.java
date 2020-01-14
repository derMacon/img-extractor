package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.MainStack;
import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.jfx.FXMLController;

import java.io.IOException;

public class LookaheadWorker extends Worker{

    private final int interval_width = 5; // todo

    public LookaheadWorker(MainStack assignments, PropertyValues props, FXMLController controller) {
        super(assignments, props, controller);
    }

    @Override
    protected void render(Assignment assignment) {
        for (int i = 1; i <= interval_width; i++) {
            try {
                assignment = assignment.next().displayGui(false);
                super.render(assignment);
            } catch (IOException e) {
                e.printStackTrace();
                // todo
            }
        }
    }
}
