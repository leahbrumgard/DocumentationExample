package edu.swarthmore.cs.cs71.shelved.spark;

import edu.swarthmore.cs.cs71.shelved.model.server.HibReadingList;
import edu.swarthmore.cs.cs71.shelved.model.server.HibListService;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;
import edu.swarthmore.cs.cs71.shelved.network.ListAdded.InvalidListAddedResponse;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import edu.swarthmore.cs.cs71.shelved.network.ListAdded.ValidListAddedResponse;
import org.hibernate.SessionFactory;
import spark.Request;
import spark.Response;

public class ServerRouteAddList extends ServerRoute {

    public ServerRouteAddList(SessionFactory sf) { super(sf); }

    @Override
    protected ResponseMessage execute(Request request, Response response) {
        try {
            String listName = request.queryParams("listName");
            String publicStatus = request.queryParams("publicStatus");
            boolean publicStatusBool;

            if (publicStatus.equals("true")) {
                publicStatusBool = true;
            } else {
                publicStatusBool = false;
            }

            HibReadingList newList = new HibListService().createList(listName, publicStatusBool);
            SimpleReadingList readingList = new SimpleReadingList(newList.getName(), newList.isPublicStatus());

            return new ValidListAddedResponse(readingList);

        } catch (Exception e) {
            return new InvalidListAddedResponse("Invalid list added response");
        }
    }
}
