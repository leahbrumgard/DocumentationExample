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
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import edu.swarthmore.cs.cs71.shelved.network.serialization.GsonUtils;
import edu.swarthmore.cs.cs71.shelved.shelved.AppSingleton;
import edu.swarthmore.cs.cs71.shelved.shelved.Continuation;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO TODO TODO TODO
// check on _author _title ask zac
// figure out where do get author and title from ISBN
// probably rename this class because its not actually adding the book


public class GetBookFromISBNRequest extends StringRequest {
    private static final String TAG = "Add scan string request";
    private String ISBN;

    public GetBookFromISBNRequest(final Context context, final String ISBN, final Continuation<SimpleBook> continuation) {
        super(Request.Method.POST, ShelvedUrls.SINGLETON.getUrl(context, ShelvedUrls.Name.SEARCH_ISBN), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "addScanResp: " + response);
                try {
                    Log.d(TAG, response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = !jObj.getBoolean("result");
                    if (!error) {
                        Log.d(TAG, "no error");
                        Gson gson = new Gson();
                        SimpleBook book = gson.fromJson(jObj.getJSONObject("book").toString(), SimpleBook.class);

                        // hold on to this book object in the searchViewModel
                        continuation.run(book);

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
                Log.e(TAG, "Add book error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        this.ISBN = ISBN;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("ISBN", this.ISBN);
        return params;
    }
}
