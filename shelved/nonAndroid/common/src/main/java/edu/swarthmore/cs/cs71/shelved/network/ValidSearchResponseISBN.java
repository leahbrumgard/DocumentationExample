package edu.swarthmore.cs.cs71.shelved.network;

import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;

public class ValidSearchResponseISBN extends ResponseMessage {
    private SimpleBook book;

    private ValidSearchResponseISBN() { super(true); }

    public ValidSearchResponseISBN(SimpleBook simpleBook) {
        super(true);
        this.book = simpleBook;
    }

    public SimpleBook getSimpleBook() {
        return this.book;
    }
}

