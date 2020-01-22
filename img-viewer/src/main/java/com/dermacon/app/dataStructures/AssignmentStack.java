package com.dermacon.app.dataStructures;

import com.dermacon.app.worker.Assignment;

public interface AssignmentStack {

    void addAssignment(Assignment assignment);

    Assignment getAssignment();

    void blockThread();

}
