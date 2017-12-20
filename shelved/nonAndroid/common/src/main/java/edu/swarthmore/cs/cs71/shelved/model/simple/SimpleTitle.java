package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.Title;

public class SimpleTitle implements Title {
    private String title;

    public SimpleTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
