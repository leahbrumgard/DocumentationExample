package edu.swarthmore.cs.cs71.shelved.network;

public class InvalidRecBookResponse extends ResponseMessage {
    private String error_message;

    public InvalidRecBookResponse() {
         super(true);
    }

    public InvalidRecBookResponse(String error_message) {
        super(true);
        this.error_message = error_message;
    }
}
