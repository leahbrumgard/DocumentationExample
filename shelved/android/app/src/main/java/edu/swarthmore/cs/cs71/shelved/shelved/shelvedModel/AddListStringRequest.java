package edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;
import edu.swarthmore.cs.cs71.shelved.network.ListAdded.ValidListAddedResponse;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import edu.swarthmore.cs.cs71.shelved.network.serialization.GsonUtils;
import edu.swarthmore.cs.cs71.shelved.shelved.AppSingleton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddListStringRequest extends StringRequest {
    private final ShelvedModel shelvedModel;
    private final SimpleReadingList list;
    private static final String TAG = "Add list string request";

    public AddListStringRequest(final Context context, final ShelvedModel shelvedModel, SimpleReadingList list) {
        super(Method.POST, ShelvedUrls.SINGLETON.getUrl(context, ShelvedUrls.Name.ADD_LIST), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Add list response: " + response);

                ResponseMessage message = GsonUtils.makeMessageGson().fromJson(response, ResponseMessage.class);
                if (message.isResult()) {
                    ValidListAddedResponse listAddedResponse = (ValidListAddedResponse) message;
                }
                try {
                    Log.d(TAG, response);
                    JSONObject jObj = new JSONObject(response);
                    boolean error = !jObj.getBoolean("result");

                    if (!error) {
                        Log.d(TAG, "no error");
                        String listName = jObj.getJSONObject("list").getJSONObject("name").getString("name");
                        Toast.makeText(context, "You successfully added " + listName, Toast.LENGTH_SHORT).show();

                        GetReadingListsStringRequest getReadingListsStringRequest = new GetReadingListsStringRequest(context, shelvedModel);
                        AppSingleton.getInstance(context).addToRequestQueue(getReadingListsStringRequest, "get reading lists");

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
                Log.e(TAG, "Add list error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        this.shelvedModel = shelvedModel;
        this.list = list;
    }

    @Override
    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        //params.put("userID", String.valueOf(shelvedModel.getUserID()));
        params.put("listName", list.getName());

        String publicStatusString;
        if (list.isPublicStatus() == true) {
            publicStatusString = "true";
        } else {
            publicStatusString = "false";
        }

        params.put("publicStatus", publicStatusString);
        return params;
    }
}
