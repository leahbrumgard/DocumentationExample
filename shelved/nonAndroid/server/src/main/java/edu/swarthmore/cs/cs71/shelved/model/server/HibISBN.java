package edu.swarthmore.cs.cs71.shelved.model.server;

import edu.swarthmore.cs.cs71.shelved.model.api.ISBN;

import javax.persistence.*;

@Entity
@Table(name="isbn")
public class HibISBN implements ISBN {
    @Id
    @Column(name="isbn_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="isbnName")
    private String isbn;

    private HibISBN() {
    }

    public HibISBN(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String getISBN() {
        return this.isbn;
    }
}
