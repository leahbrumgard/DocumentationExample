package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.BookShelf;
import edu.swarthmore.cs.cs71.shelved.model.api.RowShelf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleBookShelf implements BookShelf {
    private List<SimpleRowShelf> allRows = new ArrayList<>();

    public SimpleBookShelf() {
    }

//    public void configureBookShelf(int numRows){
//        this.allRows = new ArrayList<>();
//        for (int i=0;i<numRows;i++){
//            this.allRows.add(new SimpleRowShelf());
//        }
//    }

    public List<SimpleRowShelf> getAllRows() {
        return this.allRows;
    }

    @Override
    public int getNumRows() {
        return this.allRows.size();
    }

    public SimpleRowShelf getRowShelf(int rowPosition){
        return this.allRows.get(rowPosition);
    }
    public void addRowShelf(SimpleRowShelf rowShelf) {
        this.allRows.add(rowShelf);
    }

    public void removeRowShelf(SimpleRowShelf rowShelf) {
        this.allRows.remove(rowShelf);
    }
}
