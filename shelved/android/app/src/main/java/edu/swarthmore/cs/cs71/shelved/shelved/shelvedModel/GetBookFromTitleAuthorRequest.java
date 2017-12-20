package edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.shelved.Continuation;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetBookFromTitleAuthorRequest extends StringRequest{
    private static final String TAG = "Add title/auth request";
    private String title;
    private String author;

    public GetBookFromTitleAuthorRequest(final Context context, final String title, final String author, final Continuation<List<SimpleBook>> continuation) {
        super(Request.Method.POST, ShelvedUrls.SINGLETON.getUrl(context, ShelvedUrls.Name.SEARCH_TITLE_AUTHOR), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "GetRecResp: " + response);
                try {
                    Log.d(TAG, response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = !jObj.getBoolean("result");
                    if (!error) {
                        Log.d(TAG, "no error");
                        Gson gson = new Gson();
                        JSONArray jArr = jObj.getJSONArray("possibleBooks");
                        List<SimpleBook> books = new ArrayList<>();
                        for (int i = 0; i < jArr.length(); i++){
                            books.add(gson.fromJson(jArr.get(i).toString(), SimpleBook.class));
                        }
                        // hold on to this book object in the searchViewModel
                        continuation.run(books);

                    } else {
                        Log.d(TAG, "error");
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Error case");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Search book by title/author error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        this.title = title;
        this.author = author;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", this.title);
        params.put("author", this.author);
        return params;
    }
}
