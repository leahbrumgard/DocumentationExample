package edu.swarthmore.cs.cs71.shelved.network;

import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;

import java.util.List;

public class ValidSearchResponseTitleAuthor extends ResponseMessage {
    private List<SimpleBook> possibleBooks;

    private ValidSearchResponseTitleAuthor() { super(true); }

    public ValidSearchResponseTitleAuthor(List<SimpleBook> possibleBooks) {
        super(true);
        this.possibleBooks = possibleBooks;
    }

    public List<SimpleBook> getPossibleBooks() {
        return this.possibleBooks;
    }
}
