package edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import edu.swarthmore.cs.cs71.shelved.model.bookData.BookInfo;
import edu.swarthmore.cs.cs71.shelved.shelved.AppSingleton;
import edu.swarthmore.cs.cs71.shelved.shelved.Continuation;
import edu.swarthmore.cs.cs71.shelved.shelved.LoginActivity;
import edu.swarthmore.cs.cs71.shelved.shelved.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class UserLogInRequest extends StringRequest {
    private static final String TAG = "User request log in";
    private String email;
    private String password;
    public UserLogInRequest(final Context context, String email, String password,
                            final Continuation<LoginInfo> success, final Continuation<String> failure){
        super(Request.Method.POST,
                ShelvedUrls.SINGLETON.getUrl(context, ShelvedUrls.Name.LOG_IN), new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Log in Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d(TAG, jObj.toString());
                            boolean error = !jObj.getBoolean("result");

                            if (!error) {
                                int userID = Integer.valueOf(jObj.getString("id"));
                                String token = jObj.getString("token");
                                //TODO: update userID and token in the shelvedModel
                                LoginInfo loginInfo = new LoginInfo(userID, token);
                                success.run(loginInfo);



                            } else {
                                String errorMsg = jObj.getString("error_msg");
                                failure.run(errorMsg);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Login Error: " + error.getMessage());

                        // Network error response
                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null) {
                            Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                        }

                        if (error instanceof TimeoutError) {
                            Log.e("Volley", "TimeoutError");
                        }else if(error instanceof NoConnectionError){
                            Log.e("Volley", "NoConnectionError");
                        } else if (error instanceof AuthFailureError) {
                            Log.e("Volley", "AuthFailureError");
                        } else if (error instanceof ServerError) {
                            Log.e("Volley", "ServerError");
                        } else if (error instanceof NetworkError) {
                            Log.e("Volley", "NetworkError");
                        } else if (error instanceof ParseError) {
                            Log.e("Volley", "ParseError");
                        }

                        Toast.makeText(context,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        this.email = email;
        this.password = password;
    }


    @Override
    protected Map<String, String> getParams() {
    // Posting params to login url
    Map<String, String> params = new HashMap<String, String>();
    params.put("email", email);
    params.put("password", password);
    return params;

    };
}
