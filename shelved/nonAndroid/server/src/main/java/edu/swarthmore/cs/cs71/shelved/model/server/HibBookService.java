package edu.swarthmore.cs.cs71.shelved.model.server;

import edu.swarthmore.cs.cs71.shelved.model.bookData.EmptyQueryException;
import edu.swarthmore.cs.cs71.shelved.model.bookData.NotFoundException;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.spark.PersistenceUtils;
import edu.swarthmore.cs.cs71.shelved.model.bookData.BookInfo;
import org.hibernate.SessionFactory;
import org.xml.sax.SAXException;

import javax.persistence.EntityManager;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HibBookService {
    public HibBook createBook(SessionFactory sf, String userID, String title, String author) throws NotFoundException, ParserConfigurationException, IOException, XPathExpressionException, SAXException, EmptyQueryException { //, String genre, int pages, String publisher
        HibBook newBook = new HibBook();
        newBook.setTitle(title);
        newBook.setAuthor(author);
//        BookInfo bookInfo = new BookInfo();
//        SimpleBook book = bookInfo.populateSimpleBookFromTitleAndOrAuthor(title, author);
//        newBook.setTitle(book.getTitle().getTitle());
//        newBook.setAuthor(book.getAuthor().getAuthorName());
//        newBook.setGenre(book.getGenre().getGenre());
//        newBook.setPages(book.getPages());
//        newBook.setPublisher(book.getPublisher().getPublisher());
        // TODO: Adding this book to an user
        HibUser currentUser = new HibUserService().getUserByID(sf, Integer.valueOf(userID));
        currentUser.addBook(newBook);
        PersistenceUtils.ENTITY_MANAGER.get().persist(newBook);
        System.out.println("finish persisting newBook");
        PersistenceUtils.ENTITY_MANAGER.get().merge(currentUser);
        System.out.println("finish merging currentUser");
        return newBook;
    }

    public HibBook createBookFromISBN(String ISBN) throws NotFoundException, ParserConfigurationException, IOException, XPathExpressionException, SAXException, EmptyQueryException { //, String genre, int pages, String publisher
        BookInfo bookInfo = new BookInfo();
        String title = bookInfo.getTitleFromISBN(ISBN);
        String author = bookInfo.getAuthorFromISBN(ISBN);
        String publisher = bookInfo.getPublisherFromISBN(ISBN);
        String genre = bookInfo.getGenreFromISBN(ISBN);
        int pages = bookInfo.getNumPagesFromISBN(ISBN);
        HibBook newBook = new HibBook();
        newBook.setAuthor(author);
        newBook.setTitle(title);
        newBook.setGenre(genre);
        newBook.setPages(pages);
        newBook.setPublisher(publisher);
        PersistenceUtils.ENTITY_MANAGER.get().persist(newBook);
        return newBook;
    }

    public List<SimpleBook> getAllBooks(SessionFactory sf){
        EntityManager session = sf.createEntityManager();
        List<SimpleBook> simpleBooks = new ArrayList<>();
        List<HibBook> hibBooks = session.createQuery("FROM HibBook").getResultList();
        for (HibBook book:hibBooks){
            // TODO: Need to get all fields in the future
            SimpleBook newSimpleBook = new SimpleBook();
            newSimpleBook.setTitle(book.getTitle().getTitle());
            newSimpleBook.setAuthor(book.getAuthor().getAuthorName());
            simpleBooks.add(newSimpleBook);
        }
        return simpleBooks;
    }

}
