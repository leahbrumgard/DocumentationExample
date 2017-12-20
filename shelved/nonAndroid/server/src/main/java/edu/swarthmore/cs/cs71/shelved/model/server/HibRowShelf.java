package edu.swarthmore.cs.cs71.shelved.model.server;

import edu.swarthmore.cs.cs71.shelved.model.api.RowShelf;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="rowShelf")
public class HibRowShelf implements RowShelf {
    @Id
    @Column(name = "rowShelf_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HibShelvedBook> rowList = new ArrayList<>();

    public HibRowShelf() {
    }

    public void addBook(HibShelvedBook shelvedBook) {
        this.rowList.add(shelvedBook);
    }

    public void removeBook(HibShelvedBook book) {
        this.rowList.remove(book);
    }

    public HibShelvedBook getBook(int position) {
        return this.rowList.get(position);
    }

    public void resetPosition(int oldPosition, int newPosition) {
        HibShelvedBook currentBook = this.rowList.get(oldPosition);
        this.rowList.remove(oldPosition);
        this.rowList.add(newPosition, currentBook);
    }

    public List<HibShelvedBook> getAllBooks() {
        return this.rowList;
    }
}
