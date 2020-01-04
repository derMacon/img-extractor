package com.dermacon.app.worker;

import com.dermacon.app.Bookmark;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Manager implements Renderer {

    private static final int THREAD_CNT = 2; //todo

    private final AssignmentStack assignmentStack;
    private final List<Thread> workers = new LinkedList<>();
    private final Bookmark bookmark;
    private final int pageCnt;

    public Manager(Bookmark bookmark) throws IOException {
        pageCnt = PDDocument.load(bookmark.getFile()).getNumberOfPages();

        this.bookmark = bookmark;
        assignmentStack = new AssignmentStack(pageCnt);

        Thread thread;
        for (int i = 0; i < THREAD_CNT; i++) {
            thread = new Thread(new Worker(assignmentStack.getAssignment()));
            thread.start();
            workers.add(thread);
        }

    }

    @Override
    public void renderPageIntervall() {
        Assignment assignment = new Assignment(bookmark);
        assignmentStack.addAssignment(assignment);
    }

    @Override
    public void stop() {
        for(Thread worker : workers) {
            worker.interrupt();
        }
    }
}
