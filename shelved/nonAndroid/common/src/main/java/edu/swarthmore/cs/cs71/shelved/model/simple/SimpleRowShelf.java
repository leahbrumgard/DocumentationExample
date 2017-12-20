package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.RowShelf;
import edu.swarthmore.cs.cs71.shelved.model.api.ShelvedBook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SimpleRowShelf implements RowShelf {
    private List<SimpleShelvedBook> rowList = new ArrayList<SimpleShelvedBook>();

    public SimpleRowShelf() {
    }

    public void addBook(SimpleShelvedBook shelvedBook) {
        this.rowList.add(shelvedBook);
    }
    public void removeBook(SimpleShelvedBook book) {
        this.rowList.remove(book);
    }
    public SimpleShelvedBook getBook(int pos) {
        return rowList.get(pos);
    }

    public void resetPosition(int oldPos, int newPos) {
        SimpleShelvedBook oldBook = this.rowList.get(oldPos);
        this.rowList.remove(oldPos);
        this.rowList.add(newPos, oldBook);
    }

    public List<SimpleShelvedBook> getAllBooks() {
        return this.rowList;
    }
}