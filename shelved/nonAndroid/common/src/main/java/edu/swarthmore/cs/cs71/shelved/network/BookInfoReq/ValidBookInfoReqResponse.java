package edu.swarthmore.cs.cs71.shelved.network.BookInfoReq;

import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleAuthor;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleTitle;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;

public class ValidBookInfoReqResponse extends ResponseMessage {
    private SimpleBook book;
    private ValidBookInfoReqResponse() { super(true); }

    public ValidBookInfoReqResponse(SimpleBook book) {
        super(true);
        this.book = book;
    }

    public SimpleBook getBook() {
        return book;
    }
}
