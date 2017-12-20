package edu.swarthmore.cs.cs71.shelved.model.api;

import java.lang.reflect.Type;

public interface MessageBody  {
    String createJSON(Object object);
    Object objectFromJSON(String json, Type typeOfT);
}
