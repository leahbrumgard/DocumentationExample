package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.CreatedList;

import java.util.ArrayList;
import java.util.List;

public class SimpleReadingList implements CreatedList {
    private String name = "Untitled";
    private boolean publicStatus = false;
    private List<SimpleBook> list = new ArrayList<>();

    public SimpleReadingList(String name, boolean publicStatus) {
        this.name = name;
        this.publicStatus = publicStatus;
    }

    //getters
    public String getName() {
        return name;
    }
    public boolean isPublicStatus() {
        return publicStatus;
    }
    public List<SimpleBook> getList() {
        return list;
    }

    //setters
    public void resetName(String name) {
        this.name = name;
    }
    public void setPublicStatus(boolean publicStatus) {
        this.publicStatus = publicStatus;
    }
    public void addBook(SimpleBook book) {
        this.list.add(book);
    }
    public void removeBook(SimpleBook book) {
        this.list.remove(book);
    }

}
