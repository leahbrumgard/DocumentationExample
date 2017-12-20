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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetBookListStringRequest extends StringRequest {
    private static final String TAG = "Get book list str req";
    private int userID;

    public GetBookListStringRequest(final Context context, final ShelvedModel shelvedModel) {
        super(Request.Method.POST, ShelvedUrls.SINGLETON.getUrl(context, ShelvedUrls.Name.GET_BOOK_LIST),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Update book response: " + response);

                        try {
                            Log.d(TAG, response);
                            JSONObject jObj = new JSONObject(response);
                            boolean error = !jObj.getBoolean("result");
                            if (!error) {
                                Log.d(TAG, "no error");
                                // TODO: turn Response into a list of book, update the list, tell Adapter
                                // populate the array
                                JSONArray jArr = jObj.getJSONArray("bookList");

                                List<SimpleBook> newList = new ArrayList<>();

                                for (int i = 0; i < jArr.length(); i++) {
                                    Gson gson = new Gson();
                                    SimpleBook book = gson.fromJson(jArr.getJSONObject(i).toString(), SimpleBook.class);
                                    Log.d(TAG, "book: " + book.getTitle().getTitle());
                                    Log.d(TAG, book.getTitle().getTitle());
//                                    if (!ShelfFragment.this.books.contains(book)) {
//                                        ShelfFragment.this.books.add(book);
//                                    }
                                    newList.add(book);
                                }
                                shelvedModel.setShelf(newList);

                                Log.d(TAG, "Should be updating books");

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
        this.userID = shelvedModel.userID;
    }
    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userID", String.valueOf(this.userID));
        return params;
    }

}
