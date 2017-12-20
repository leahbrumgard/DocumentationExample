package edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import edu.swarthmore.cs.cs71.shelved.network.serialization.GsonUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetSingleReadingListStringRequest extends StringRequest {
    private static final String TAG = "Get single readlist req";

    public GetSingleReadingListStringRequest(final Context context, final ShelvedModel shelvedModel) {
        super(Request.Method.POST, ShelvedUrls.SINGLETON.getUrl(context, ShelvedUrls.Name.GET_SINGLE_LIST),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.d(TAG, response);
                        JSONObject jObj = new JSONObject(response);
                        boolean error = !jObj.getBoolean("result");
                        if (!error) {
                            Log.d(TAG, "no error");
                            JSONArray jArr = jObj.getJSONArray("readingList");

                            List<SimpleBook> newList = new ArrayList<>();

                            for (int i = 0; i < jArr.length(); i++) {
                                Gson gson = new Gson();
                                SimpleBook book = gson.fromJson(jArr.getJSONObject(i).toString(), SimpleBook.class);
                                Log.d(TAG, "book: " + book.getTitle().getTitle());
                                Log.d(TAG, book.getTitle().getTitle());
                                newList.add(book);
                            }
//                            shelvedModel.setReadingList(newList);
                        } else {
                            Log.d(TAG, "error");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Error case");
                    }
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Update book list error: " + error.getMessage() + "caused by:" + error.getCause());
            }
        });
    }
}
