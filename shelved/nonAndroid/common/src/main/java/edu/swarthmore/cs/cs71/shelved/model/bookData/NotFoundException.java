package edu.swarthmore.cs.cs71.shelved.model.bookData;
import org.json.JSONObject;

public class NotFoundException extends Exception {
    public NotFoundException(String message, JSONObject jObj){
        super(message+ ".jObj: "+String.valueOf(jObj));
    }
}
