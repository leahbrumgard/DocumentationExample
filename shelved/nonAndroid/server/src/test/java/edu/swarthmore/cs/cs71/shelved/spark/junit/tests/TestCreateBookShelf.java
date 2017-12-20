package edu.swarthmore.cs.cs71.shelved.spark.junit.tests;
import edu.swarthmore.cs.cs71.shelved.model.server.HibBook;
import edu.swarthmore.cs.cs71.shelved.model.server.HibBookShelf;
import edu.swarthmore.cs.cs71.shelved.model.server.HibRowShelf;
import edu.swarthmore.cs.cs71.shelved.model.server.HibShelvedBook;
import org.junit.Assert;
import org.junit.Test;

public class TestCreateBookShelf {
    @Test
    public void TestNewShelf(){
        HibRowShelf hibRowShelf = new HibRowShelf();
        HibShelvedBook shelvedBook = new HibShelvedBook();

        HibBook hibBook = new HibBook();
        hibBook.setAuthor("JK Rowling");
        hibBook.setGenre("Fiction");
        hibBook.setPages(1);
        hibBook.setTitle("Harry Potter");
        hibBook.setPublisher("HPPub");

        shelvedBook.setBook(hibBook);
        hibRowShelf.addBook(shelvedBook);
        HibBookShelf newBookShelf = new HibBookShelf();
        newBookShelf.addRowShelf(hibRowShelf);
        Assert.assertEquals(hibRowShelf, newBookShelf.getRowShelf(0));
        Assert.assertEquals(1, newBookShelf.getNumRows());
        newBookShelf.removeRowShelf(hibRowShelf);
        Assert.assertEquals(0, newBookShelf.getNumRows());
    }
}
