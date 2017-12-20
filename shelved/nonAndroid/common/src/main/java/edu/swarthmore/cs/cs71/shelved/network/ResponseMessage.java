package edu.swarthmore.cs.cs71.shelved.network;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

public abstract class ResponseMessage {
    private boolean result;

    private ResponseMessage(){}
    public ResponseMessage(boolean result) {
        this.result = result;
    }
    public boolean isResult() {
        return result;
    }

}
