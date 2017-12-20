package edu.swarthmore.cs.cs71.shelved.model.api;

import java.util.Dictionary;

public interface Book {
    // TODO: Need a method to signify that a book has been lent out
    // TODO: Should a book know it location in row
    // Getters
    void setAuthor(String author);
    void setGenre(String genre);
    void setTitle(String title);
    void setPages(int pages);
    void setPublisher(String publisher);
    void setISBN(String isbn);
    void setImageUrl(String imageUrl);
    int getPages();

    // Returns a dictionary of book source mapped to accompanying price.
    // Dont use a string here
    Dictionary<String, Double> getPrices();
}
