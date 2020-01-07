package com.dermacon.app.dataStructures;

import java.io.File;
import java.io.IOException;

public class MockProperties extends PropertyValues {

    public MockProperties() throws IOException {
        super(new File("test"));
    }
}
