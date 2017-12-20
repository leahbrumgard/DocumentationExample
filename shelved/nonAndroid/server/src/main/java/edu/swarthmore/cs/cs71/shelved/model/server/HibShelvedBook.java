package edu.swarthmore.cs.cs71.shelved.model.server;


import edu.swarthmore.cs.cs71.shelved.model.api.Book;
import edu.swarthmore.cs.cs71.shelved.model.api.ShelvedBook;

import javax.persistence.*;


@Entity
@Table(name="shelvedBook")
public class HibShelvedBook implements ShelvedBook {
    @Id
    @Column(name="shelvedBook_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="bookMark")
    private int bookMark;

    @Column(name="forSale")
    private boolean forSale;

    @Column(name="forLend")
    private boolean forLend;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="book_id")
    private HibBook book;


    public HibShelvedBook() {
    }
    public void setBook(HibBook book) {
        this.book = book;
    }


    @Override
    public int getBookMark() {
        return this.bookMark;
    }

    @Override
    public boolean isForSale() {
        return this.forSale;
    }

    @Override
    public boolean isForLend() {
        return this.forLend;
    }

    @Override
    public void setForSale(boolean option) {
        this.forSale = option;
    }

    @Override
    public void setForLend(boolean option) {
        this.forLend = option;
    }

    @Override
    public void setBookMark(int page) {
        this.bookMark = page;
    }

    @Override
    public Book getBook() {
        return this.book;
    }
}


