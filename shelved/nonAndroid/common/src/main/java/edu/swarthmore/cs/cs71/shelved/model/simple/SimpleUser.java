package edu.swarthmore.cs.cs71.shelved.model.simple;

import edu.swarthmore.cs.cs71.shelved.model.api.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleUser implements User {
    public String header = this.getClass().getSimpleName();
    private SimpleEmail simpleEmail;
    private String password;
    private String name;
    private String bio;
    private String location;
    private String salt;
    private List<SimpleBookShelf> allShelves;
    private List<SimpleReadingList> allReadingLists;
    private String token;
    private List<SimpleBook> userBooks;

    public SimpleUser() {
        setSalt();
        setUserBooks();
        setShelves();
        setReadingLists();
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
    @Override
    public void setEmail(String userName) {
        this.simpleEmail = new SimpleEmail(userName);
    }
    public void setSalt() {
        this.salt = BCrypt.gensalt();
    }
    public void setPassword(String password) {
        String hashedPassword = BCrypt.hashpw(password, this.salt);
        this.password = hashedPassword;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void setShelves() {
        this.allShelves = new ArrayList<SimpleBookShelf>();
    }
    public void setReadingLists() {
        this.allReadingLists = new ArrayList<SimpleReadingList>();
    }
    public void changePassword(String oldPassword, String newPassword) {
        if (BCrypt.hashpw(oldPassword, this.salt).equals(password)) {
            this.setSalt();
            this.setPassword(newPassword);
        }
    }

    // getters
    public SimpleEmail getSimpleEmail() {
        return simpleEmail;
    }
    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }
    public String getBio() {
        return bio;
    }
    public String getLocation() {
        return location;
    }
    public String getSalt() {
        return salt;
    }

    //setters
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public List<SimpleBookShelf> getAllShelves() {
        return allShelves;
    }
    public void addBookShelf(SimpleBookShelf bookShelf){ allShelves.add(bookShelf);}
    public void addReadingList(SimpleReadingList readingList){
        this.allReadingLists.add(readingList);
    }
    public void setUserBooks(){ this.userBooks = new ArrayList<SimpleBook>(); }
    public void addBook(SimpleBook book){
        this.userBooks.add(book);
    }
}
