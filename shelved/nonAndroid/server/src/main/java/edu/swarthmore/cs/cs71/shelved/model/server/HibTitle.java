package edu.swarthmore.cs.cs71.shelved.model.server;

import edu.swarthmore.cs.cs71.shelved.model.api.Title;

import javax.persistence.*;

@Entity
@Table(name="title")
public class HibTitle implements Title {
    @Id
    @Column(name="title_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="titleName")
    private String title;

    public HibTitle() {
    }

    public HibTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
}
