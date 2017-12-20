package edu.swarthmore.cs.cs71.shelved.network;

import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;

import java.util.List;

public class ValidRecBookResponse extends ResponseMessage {
    private List<SimpleBook> possibleBooks;

    private ValidRecBookResponse() { super(true); }

    public ValidRecBookResponse(List<SimpleBook> possibleBooks) {
        super(true);
        this.possibleBooks = possibleBooks;
    }

    public List<SimpleBook> getPossibleBooks() {
        return this.possibleBooks;
    }}
