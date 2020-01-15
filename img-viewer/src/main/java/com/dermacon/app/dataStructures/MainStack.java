package com.dermacon.app.dataStructures;

import com.dermacon.app.worker.Assignment;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Stacks that contains the main images which the user specified by turning
 * the page.
 */
public class MainStack {

    private List<Assignment> assignments = new LinkedList<>();
//    private Set<Assignment> renderedImgs = new HashSet<>();

    private final boolean shouldDisplay;

    public MainStack() {
        this(true);
    }

    public MainStack(boolean shouldDisplay) {
        this.shouldDisplay = shouldDisplay;
    }

    public synchronized void addAssignment(Assignment assignment) {
//        assignment.translateCurrImgPath()
//        if (!renderedImgs.contains(assignment)) {
            assignments.add(0, assignment);
            this.notifyAll();
//            renderedImgs.add(assignment);
//        }
    }

    public boolean shouldDisplay() {
        return shouldDisplay;
    }

    public synchronized Assignment getAssignment() {
        return assignments.isEmpty() ?
                null : assignments.remove(0);
    }

    public synchronized void blockThread() {
        while (assignments.isEmpty() && !Thread.currentThread().isInterrupted()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public String toString() {
        return this.assignments.toString();
    }
}
