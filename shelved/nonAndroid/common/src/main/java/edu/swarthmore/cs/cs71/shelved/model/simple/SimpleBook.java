package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.Book;

import java.util.Dictionary;
import java.util.List;

public class SimpleBook implements Book {

    private SimpleAuthor author;
    private SimpleGenre genre;
    private SimpleTitle title;
    private int pages;
    private SimplePublisher publisher;
    private SimpleISBN isbn;
    private String imageUrl;

    public SimpleBook(){

    }


    public void setAuthor(String author) {
        this.author = new SimpleAuthor(author);
    }

    public void setGenre(String genre) {
        this.genre = new SimpleGenre(genre);
    }

    public void setTitle(String title) {
        this.title = new SimpleTitle(title);
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPublisher(String publisher) {
        this.publisher = new SimplePublisher(publisher);
    }

    @Override
    public void setISBN(String isbn) { this.isbn = new SimpleISBN(isbn);}

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SimpleAuthor getAuthor() {
        return author;
    }

    public SimpleGenre getGenre() {
        return genre;
    }

    public SimpleTitle getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    public SimplePublisher getPublisher() {
        return publisher;
    }

    public SimpleISBN getISBN() { return isbn; }

    public String getImageUrl() {
        return imageUrl;
    }


    public Dictionary<String, Double> getPrices() {
        //query Amazon's API (and more)
        //http://docs.aws.amazon.com/AWSECommerceService/latest/DG/EX_RetrievingPriceInformation.html
        return null;
    }
}
