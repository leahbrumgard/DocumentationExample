package edu.swarthmore.cs.cs71.shelved.model.server;

import edu.swarthmore.cs.cs71.shelved.model.api.CreatedList;
import edu.swarthmore.cs.cs71.shelved.model.api.ShelvedBook;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="wishList")
public class HibWishList implements CreatedList{
    @Id
    @Column(name="wishList_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;

    @Column(name="name")
    private String name;

    @Column(name="publicStatus")
    private boolean publicStatus;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HibShelvedBook> list = new ArrayList<HibShelvedBook>();

    public HibWishList() {
    }

    // setters
    public void setPublicStatus(boolean publicStatus) {
        this.publicStatus = publicStatus;
    }

    public String getName() {
        return name;
    }

    public boolean getPublicStatus() {
        return publicStatus;
    }

    public List<HibShelvedBook> getList() {
        return list;
    }



    public void addBook(HibShelvedBook shelvedBook) {
        this.list.add(shelvedBook);
    }

    public void removeBook(HibShelvedBook shelvedBook) {
        this.list.remove(shelvedBook);
    }

}



