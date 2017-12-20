package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.Publisher;

public class SimplePublisher implements Publisher {
    private String publisher;
    public SimplePublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getPublisher() {
        return publisher;
    }
}
