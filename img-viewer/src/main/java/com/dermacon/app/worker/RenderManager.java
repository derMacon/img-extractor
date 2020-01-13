package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.jfx.FXMLController;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RenderManager implements Renderer {

    private static final int THREAD_CNT = 2; //todo

    private final AssignmentStack assignmentStack;
    private final List<Thread> workers = new LinkedList<>();
    private final PropertyValues props;

    public RenderManager(PropertyValues props) {
        assignmentStack = new AssignmentStack();
        this.props = props;
    }

    @Override
    public void setController(FXMLController controller) {
        Thread thread;
        for (int i = 0; i < THREAD_CNT; i++) {
            thread = new Thread(new Worker(assignmentStack,
                    props, controller));
            thread.start();
            workers.add(thread);
        }
    }

    @Override
    public void renderPageIntervall(Bookmark bookmark) {
        Assignment assignment = new Assignment(bookmark.copy());
        assignmentStack.addAssignment(assignment);
    }

    @Override
    public void stop() {
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }

    @Override
    public void clearPipeline() {
        this.assignmentStack.clear();
    }
}
