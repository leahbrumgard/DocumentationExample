package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.Book;
import edu.swarthmore.cs.cs71.shelved.model.api.ShelvedBook;

public class SimpleShelvedBook implements ShelvedBook {

    private int bookMark;
    private boolean forSale;
    private boolean forLend;
    private SimpleBook book;

    public SimpleShelvedBook() {

    }

    public SimpleBook getBook() {
        return this.book;
    }
    public int getBookMark() {
        return bookMark;
    }

    public void setBook(SimpleBook book) {
        this.book = book;
    }
    public void setForSale(boolean option) {
        this.forSale = option;
    }
    public void setForLend(boolean option) {
        this.forLend = option;
    }
    public void setBookMark(int page) {
        this.bookMark = page;
    }

    public boolean isForSale() {
        return forSale;
    }
    public boolean isForLend() {
        return forLend;
    }
}
