package edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.shelved.AppSingleton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddBookToListStringRequest extends StringRequest {
    private final ShelvedModel shelvedModel;
    private final SimpleBook book;

    private static final String TAG = "Add Book string request";

    public AddBookToListStringRequest(final Context context, final ShelvedModel shelvedModel, SimpleBook book) {
        super(Method.POST, ShelvedUrls.SINGLETON.getUrl(context, ShelvedUrls.Name.ADD_BOOK_TO_LIST),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Add book to list response: " + response);

                        try {
                            Log.d(TAG, response);
                            JSONObject jObj = new JSONObject(response);
                            boolean error = !jObj.getBoolean("result");
                            if (!error) {
                                Log.d(TAG, "no error");
                                String bookTitle = jObj.getJSONObject("book").getJSONObject("title").getString("title");
                                Toast.makeText(context, "You successfully added " + bookTitle, Toast.LENGTH_SHORT).show();
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
        this.shelvedModel = shelvedModel;
        this.book = book;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", book.getTitle().getTitle());
        params.put("author", book.getAuthor().getAuthorName());
        return params;
    }
}
