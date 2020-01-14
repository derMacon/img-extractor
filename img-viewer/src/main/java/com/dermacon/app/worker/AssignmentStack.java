package com.dermacon.app.worker;

import java.util.LinkedList;

class AssignmentStack {

//    private static final int PAGE_INTERVALL = 10; //todo
    private LinkedList<Assignment> assignments = new LinkedList<>();
    private LinkedList<Assignment> renderedImages = new LinkedList<>();

    /**
     * Adds a new (valid) assignment to the stack and generates the previous
     * / next page assignment and also adds it to the stack.
     *
     * @param assignment
     */
    public synchronized void addAssignment(Assignment assignment) {
        if (assignment != null) {
            assignments.add(0, assignment.displayGui(true));
//            for (int i = 1; i < PAGE_INTERVALL; i++) {
//                assignments.add(i, assignment.prev().displayGui(false));
//                assignments.add(i, assignment.next().displayGui(false));
//            }
        }

        this.notifyAll();
    }

    public synchronized Assignment getAssignment() {

        while (assignments.isEmpty() && !Thread.currentThread().isInterrupted()) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Assignment assignment = null;
        if (!Thread.currentThread().isInterrupted()) {
            assignment = assignments.remove(0);

            // assignment list may contain duplicates due to
            // the priority of the 'younger' assignments.
            // easier to just filter out already rendered img
            // instead of being picky when creating assignments.
            if (renderedImages.contains(assignment)) {
                return getAssignment();
            }

            this.renderedImages.add(assignment);
        }
        return assignment;
    }

    public void clear() {
        this.assignments.clear();
    }

    @Override
    public String toString() {
        return assignments.toString();
    }
}
