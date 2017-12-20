package edu.swarthmore.cs.cs71.shelved.model.server;


import edu.swarthmore.cs.cs71.shelved.model.api.CreatedList;
import edu.swarthmore.cs.cs71.shelved.model.api.ShelvedBook;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="readingList")
public class HibReadingList implements CreatedList {
    @Id
    @Column(name="readingList_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HibBook> list = new ArrayList<HibBook>();

    @Column(name="listName")
    private String listName;

    @Column(name="publicStatus")
    private boolean publicStatus;

    public HibReadingList() {
    }

    public void addBook(HibBook book) { this.list.add(book); }

    public void removeBook(HibBook book) { this.list.remove(book); }

    // setters
    public void setId(int id) { this.id = id; }
    public void resetName(String listName) { this.listName = listName; }
    public void setPublicStatus(boolean publicStatus) { this.publicStatus = publicStatus; }

    //getters
    public int getId() { return this.id; }
    public String getName() { return this.listName; }
    public boolean isPublicStatus() { return this.publicStatus; }
}
