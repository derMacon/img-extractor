package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.PropertyValues;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class RenderManager implements Renderer {

    private static final int THREAD_CNT = 2; //todo

    private final AssignmentStack assignmentStack;
    private final List<Thread> workers = new LinkedList<>();
//    private final Bookmark bookmark;
//    private final int pageCnt;

    public RenderManager(PropertyValues props) throws IOException {
//        pageCnt = PDDocument.load(bookmark.getFile()).getNumberOfPages();

//        this.bookmark = bookmark;
//        assignmentStack = new AssignmentStack(pageCnt);
        assignmentStack = new AssignmentStack();

        Thread thread;
        for (int i = 0; i < THREAD_CNT; i++) {
            thread = new Thread(new Worker(assignmentStack,
                    props));
            thread.start();
            workers.add(thread);
        }

    }

    @Override
    public void renderPageIntervall(Bookmark bookmark) {
        Assignment assignment = new Assignment(bookmark);
        assignmentStack.addAssignment(assignment);
    }

    @Override
    public void stop() {
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }
}
