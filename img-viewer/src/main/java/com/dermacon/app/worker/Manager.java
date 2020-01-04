package com.dermacon.app.worker;

import com.dermacon.ankipdfeditor.data.project.ProjectInfo;
import com.dermacon.app.Bookmark;

import java.util.LinkedList;
import java.util.List;

public class Manager implements Renderer {

    private static final int THREAD_CNT = 2; //todo

    private final AssignmentStack assignments;
    private final List<Thread> workers = new LinkedList<>();
    private final Bookmark bookmark;

    public Manager(Bookmark bookmark) {
        this.bookmark = bookmark;

//        int pageCnt = projectInfo.getPdfPDDoc().getNumberOfPages();
        int pageCnt = bookmark.File
        assignments = new AssignmentStack(pageCnt);

        Thread thread;
        for (int i = 0; i < THREAD_CNT; i++) {
            thread = new Thread(new Worker(assignments, projectInfo));
            thread.start();
            workers.add(thread);
        }

    }

    @Override
    public void renderPageIntervall() {
        assignments.addPage(projectInfo.getCurrPage());
    }

    @Override
    public void stop() {
        for(Thread worker : workers) {
            worker.interrupt();
        }
    }
}
