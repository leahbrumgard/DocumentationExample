package edu.swarthmore.cs.cs71.shelved.spark;

import edu.swarthmore.cs.cs71.shelved.model.server.HibBook;
import edu.swarthmore.cs.cs71.shelved.model.server.HibBookService;
import edu.swarthmore.cs.cs71.shelved.model.bookData.BookInfo;
import edu.swarthmore.cs.cs71.shelved.model.bookData.BookInfo.*;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.network.*;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

public class ServerRouteAddBookScan extends ServerRoute {
    public ServerRouteAddBookScan(SessionFactory sf) {
        super(sf);
        System.out.println("INSIDE ServerRouteAddBookScan SUPER CALL");
    }

    @Override
    protected ResponseMessage execute(Request request, Response response) {
        System.out.println("RIGHT BEFORE TRY IN RESPONSE MESSAGE");
        try {
            //HibBook newBook = new HibBookService().createBook(
            //        request.queryParams("title"),
            //        request.queryParams("author"));
            String ISBN = request.queryParams("ISBN");
            System.out.println("ISBN: " + ISBN);

            BookInfo bookInfo = new BookInfo();

            String author = bookInfo.getAuthorFromISBN(ISBN);
            String title = bookInfo.getTitleFromISBN(ISBN);
            HibBook newBook = new HibBookService().createBook(title, author);

            //String publisher = bookInfo.getPublisherFromISBN(ISBN);
            //String numPages = bookInfo.getNumPagesFromISBN(ISBN);
            //int pages = Integer.parseInt(numPages);


            SimpleBook simpleBook = new SimpleBook();
            simpleBook.setTitle(newBook.getTitle().getTitle());
            simpleBook.setAuthor(newBook.getAuthor().getAuthorName());


            //        simpleBook.setGenre(newBook.getGenre().getGenre());
            //        simpleBook.setPages(pages);
            //        simpleBook.setPublisher(publisher);
            return new ValidBookInfoReqResponse(simpleBook);
        } catch (Exception e){
            return new InvalidBookInfoReqResponse("Invalid ISBN search response");
        }

    }
}