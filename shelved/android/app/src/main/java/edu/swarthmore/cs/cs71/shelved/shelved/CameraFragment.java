package edu.swarthmore.cs.cs71.shelved.shelved;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import edu.swarthmore.cs.cs71.shelved.network.ValidBookInfoReqResponse;
import edu.swarthmore.cs.cs71.shelved.network.serialization.GsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {
    static final int PICK_CONTACT_REQUEST = 1;  // The request code

    private TextView _ISBN;
    private TextView _Author;
    private TextView _Title;
    private static final String TAG = "CameraFragment";
//    final Continuation<SimpleBook> positiveContinuation = new Continuation<SimpleBook>() {
//        @Override
//        public void run(SimpleBook simpleBook) {
//            // TODO: modify aList, tell Adapter, callUpdateBook, Adapter of SimpleBook
//            ShelfFragment.books.add(simpleBook);
//            bookListAdapter.notifyDataSetChanged();
//            ShelfFragment.updateBook();
//        }
//    };

    private String getAddBookByScanUrl() {
        //AppCompatActivity act = new AppCompatActivity();
        return "http://" + getContext().getResources().getString((R.string.server_url)) + ":4567/addBookByScan";
    }

    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        Context context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context;
        View rootView = inflater.inflate(R.layout.fragment_camera, container, false);
        context = rootView.getContext();
        _ISBN = (TextView) rootView.findViewById(R.id.ISBN);
        _Author = (TextView) rootView.findViewById(R.id.Author);
        _Title = (TextView) rootView.findViewById(R.id.Title);

        Intent intent = new Intent(context, ScannerActivity.class);

        Log.d(TAG, "about to call activity start");
        startActivityForResult(intent, PICK_CONTACT_REQUEST);

        return rootView;
        //return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST && data!=null) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                String ISBN = ScannerActivity.getISBN();
                _ISBN.setText(ISBN);
                Log.d(TAG, "past setting ISBN text");


                String cancel_req_tag = "addBookByScan";
                StringRequest strReq = new StringRequest(Request.Method.POST, getAddBookByScanUrl(), new Response.Listener<String>() {
                    public void onResponse(String response) {
                        Log.d(TAG, "past creating message from Gson class");
                        ResponseMessage message = GsonUtils.makeMessageGson().fromJson(response, ResponseMessage.class);
                        if (message.isResult()) {
                            ValidBookInfoReqResponse bookInfoReqResponse = (ValidBookInfoReqResponse) message;
                            Log.d(TAG, "creating validbookinforeqresponse");
                        }
                        try {
                            Log.d(TAG, response);
                            JSONObject jObj = new JSONObject(response);
                            boolean error = !jObj.getBoolean("result");


                            if (!error) {
                                Log.d(TAG, "no error");
                                String bookTitle = jObj.getJSONObject("book").getJSONObject("title").getString("title");
                                String bookAuthor = jObj.getJSONObject("book").getJSONObject("author").getString("title");
                                _Author.setText(bookAuthor);
                                _Title.setText(bookTitle);

                            } else {
                                Log.d(TAG, "error");
                                String errorMsg = jObj.getString("error_msg");
                                Toast.makeText(getContext(),
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
                        Log.e(TAG, "Get book info error: " + error.getMessage());
                        Toast.makeText(getContext(),
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("ISBN", _ISBN.getText().toString());
                        return params;
                    }

                };
                // Adding request to request queue
                Log.d(TAG, "WE ARE HERE");
                // TODO: Context is wrong, strReq never gets accessed
                AppSingleton.getInstance(getContext()).addToRequestQueue(strReq, cancel_req_tag);
                SimpleBook newBook = new SimpleBook();

                newBook.setAuthor(ISBN);
                newBook.setTitle(ISBN);

//                positiveContinuation.run(newBook);


                Log.d(TAG, "past activity start");


            }
        }

    }
}
