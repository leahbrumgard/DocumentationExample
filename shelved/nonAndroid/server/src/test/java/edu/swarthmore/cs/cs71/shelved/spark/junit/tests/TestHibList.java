package edu.swarthmore.cs.cs71.shelved.spark.junit.tests;

import edu.swarthmore.cs.cs71.shelved.model.server.HibBook;
import edu.swarthmore.cs.cs71.shelved.model.server.HibShelvedBook;
import org.junit.Assert;
import org.junit.Test;
import edu.swarthmore.cs.cs71.shelved.model.server.HibReadingList;

public class TestHibList {
    @Test
    public void TestNewHibList() {
        HibReadingList list = new HibReadingList();
        list.setPublicStatus(true);
        list.resetName("My List");
        Assert.assertEquals("My List", list.getName());
        Assert.assertEquals(true, list.isPublicStatus());
        Assert.assertNotEquals(false, list.isPublicStatus());
    }

    @Test
    public void TestAddBook() {
        HibReadingList list = new HibReadingList();
        list.setPublicStatus(true);
        list.resetName("My List");

        HibBook book = new HibBook();
        book.setAuthor("Martin Fowler");
        book.setTitle("Refactoring");
        book.setPages(13);
        book.setGenre("Computer Science");
        book.setPublisher("Unknown");
        list.addBook(book);
    }

    @Test
    public void TestAddRemoveBook() {
        HibReadingList list = new HibReadingList();
        list.setPublicStatus(true);
        list.resetName("My List");

        HibBook book = new HibBook();
        book.setAuthor("Martin Fowler");
        book.setTitle("Refactoring");
        book.setPages(13);
        book.setGenre("Computer Science");
        book.setPublisher("Unknown");

        list.addBook(book);
        list.removeBook(book);
    }
}
