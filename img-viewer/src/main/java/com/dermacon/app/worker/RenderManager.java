package com.dermacon.app.worker;

import com.dermacon.app.dataStructures.Bookmark;
import com.dermacon.app.dataStructures.MainStack;
import com.dermacon.app.dataStructures.PropertyValues;
import com.dermacon.app.jfx.FXMLController;

import java.util.LinkedList;
import java.util.List;

public class RenderManager implements Renderer {

    private static final int BACKGROUND_THREAD_CNT = 8; //todo
    private static final int FOREGROUND_THREAD_CNT = 4; //todo

    private final MainStack displayStack = new MainStack(true);
    private final MainStack lookaheadStack = new MainStack(false);
    private final MainStack lookbackStack = new MainStack(false);

    private final List<Thread> workers = new LinkedList<>();
    private final PropertyValues props;

    public RenderManager(PropertyValues props) {
        this.props = props;
    }

    @Override
    public void setController(FXMLController controller) {
        Thread mainRenderingThread = new Thread(new Worker(displayStack, props,
                controller));
        workers.add(mainRenderingThread);

        // init foreground / main tasks
        Thread thread;
        for (int i = 0; i < FOREGROUND_THREAD_CNT; i++) {
            thread = new Thread(new Worker(displayStack,
                    props, controller));
            thread.start();
            workers.add(thread);
        }

        // init background tasks
        for (int i = 0; i < BACKGROUND_THREAD_CNT; i++) {
            thread = new Thread(new LookaheadWorker(lookaheadStack,
                    props, controller));
            thread.start();
            workers.add(thread);
        }

        for (int i = 0; i < BACKGROUND_THREAD_CNT; i++) {
            thread = new Thread(new LookbehindWorker(lookbackStack,
                    props, controller));
            thread.start();
            workers.add(thread);
        }
    }

    @Override
    public void renderPageIntervall(Bookmark bookmark) {
        System.out.println("render pi " + bookmark);
        Assignment assignment = new Assignment(bookmark, props.getImgPath());
        displayStack.addAssignment(assignment);
        lookaheadStack.addAssignment(assignment);
        lookbackStack.addAssignment(assignment);

        System.out.println("disp stack: " + displayStack.shouldDisplay());
    }

    @Override
    public void stop() {
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }

    @Override
    public void clearPipeline() {
        // todo
//        this.displayStack.clear();
    }
}
