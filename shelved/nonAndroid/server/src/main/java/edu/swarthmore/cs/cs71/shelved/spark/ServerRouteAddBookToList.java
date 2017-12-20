package edu.swarthmore.cs.cs71.shelved.spark;

import edu.swarthmore.cs.cs71.shelved.model.server.HibBook;
import edu.swarthmore.cs.cs71.shelved.model.server.HibBookService;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.network.AddBookToReadingList.InvalidBookAddedToListResponse;
import edu.swarthmore.cs.cs71.shelved.network.AddBookToReadingList.ValidBookAddedToListResponse;
import edu.swarthmore.cs.cs71.shelved.network.BookAddedToShelf.InvalidBookAddedResponse;
import edu.swarthmore.cs.cs71.shelved.network.BookAddedToShelf.ValidBookAddedResponse;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

public class ServerRouteAddBookToList extends ServerRoute {
    public ServerRouteAddBookToList(SessionFactory sf) { super(sf); }

    @Override
    protected ResponseMessage execute(Request request, Response response) {
        try {
            HibBook newBook = new HibBookService().createBook(
                    this.getSf(),
                    request.queryParams("userID"),
                    request.queryParams("title"),
                    request.queryParams("author"));
            System.out.println("INSIDE SERVER ROUTE ADD BOOK TO LIST");

            SimpleBook simpleBook = new SimpleBook();
            simpleBook.setTitle(newBook.getTitle().getTitle());
            simpleBook.setAuthor(newBook.getAuthor().getAuthorName());
            return new ValidBookAddedToListResponse(simpleBook);
        } catch (Exception e){
            return new InvalidBookAddedToListResponse("Invalid book added response");
        }
    }
}
