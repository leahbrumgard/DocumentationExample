package edu.swarthmore.cs.cs71.shelved;


import javax.persistence.*;

@Entity
@Table(name="publisher")
public class HibPublisher implements Publisher {
    @Id
    @Column(name="publisher_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name="publisher")
    private String publisher;

    public HibPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String getPublisher() {
        return publisher;
    }
}