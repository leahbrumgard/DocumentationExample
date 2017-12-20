package edu.swarthmore.cs.cs71.shelved.shelved;
import android.content.Context;

import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;
import edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel.*;
import android.app.ProgressDialog;



public class AppSingleton {
    private static AppSingleton mAppSingletonInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;
    private ShelvedModel model;
    private SearchViewModel searchViewModel;

    private AppSingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized AppSingleton getInstance(Context context) {
        if (mAppSingletonInstance == null) {
            mAppSingletonInstance = new AppSingleton(context);
        }
        return mAppSingletonInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public ShelvedModel getModel(Context context) {
        if (model == null) {
            model = new ShelvedModel();
            addBookNetworkListeners(context, model);
            addListNetworkListeners(context, model);
            addLogInSuccessNetWorkListeners(context, model);
            addBookToListNetworkListeners(context, model);
            model.initializeReadingLists();
            //addRecommendedBookListeners(context, model);
        }
        return model;
    }

    public SearchViewModel getSearchViewModel(Context context) {
        if (searchViewModel == null) {
            searchViewModel = new SearchViewModel();

        }
        return searchViewModel;
    }



    ///////////////// Book Added Listeners /////////////////
    private void addBookNetworkListeners(final Context context, final ShelvedModel shelvedModel) {
        shelvedModel.addBookAddedListener(new BookAddedListener() {
            @Override
            public void bookAdded(final int userID, final SimpleBook book) {
                StringRequest strReq = new AddBookStringRequest(context, shelvedModel, userID, book);
                // Adding request to request queue
                addToRequestQueue(strReq, "addBook");
            }
        });
    }

    private void addBookToListNetworkListeners(final Context context, final ShelvedModel shelvedModel) {
        shelvedModel.addBookAddedToListListener(new BookAddedToListListener() {
            @Override
            public void bookAddedToList(final SimpleBook book) {
                StringRequest strReq = new AddBookToListStringRequest(context, shelvedModel, book);
                // Adding request to request queue
                addToRequestQueue(strReq, "addBookToList");
            }
        });
    }


    ///////////////// Rec books /////////////////////////////
//    private void addRecommendedBookListeners(final Context context, final ShelvedModel shelvedModel) {
//        shelvedModel.addListAddedListener(new RecommendedBookListListener() {
//
//            @Override
//            public void getRecommendedList(String isbn) {
//                StringRequest strReq = new GetBookRecsFromISBNRequest(context, isbn, );
//            }
//        });
//    }
    ///////////////// List added Listeners /////////////////

    private void addListNetworkListeners(final Context context, final ShelvedModel shelvedModel) {
        shelvedModel.addListAddedListener(new ListAddedListener() {
            @Override
            public void listAdded(SimpleReadingList list) {
                StringRequest strReq = new AddListStringRequest(context, shelvedModel, list);
                // Adding request to request queue
                addToRequestQueue(strReq, "addList");
            }
        });
    }

    ///////////////// Sign up success Listeners /////////////////

//    public void addSignUpSuccessNetworkListeners(final Context context, final ShelvedModel shelvedModel){
//        shelvedModel.addSignUpSuccessListeners(new SignUpSuccessListener() {
//            @Override
//            public void onSignUpSuccess(String userName, String email, String password, ProgressDialog progressDialog) {
//                StringRequest strReq = new UserSignUpRequest(context, userName, email, password, progressDialog);
//                AppSingleton.getInstance(context).addToRequestQueue(strReq, "signUp");
//                // Launch loginActivity
//                Intent intent = new Intent(
//                        context,
//                        LoginActivity.class);
//                context.startActivity(intent);
//            }
//        });
//    }

    public void addLogInSuccessNetWorkListeners(final Context context, final ShelvedModel shelvedModel){
        shelvedModel.addLogInSuccessListeners(new LogInSuccessListener() {
            @Override
            public void onLogInSuccess() {
                // Launch MainActivity
                Intent intent = new Intent(
                        context,
                        MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    public void hideDialog(ProgressDialog progressDialog) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

}
