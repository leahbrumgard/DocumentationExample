package edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.toolbox.StringRequest;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;
import edu.swarthmore.cs.cs71.shelved.shelved.AppSingleton;
import edu.swarthmore.cs.cs71.shelved.shelved.Continuation;
import edu.swarthmore.cs.cs71.shelved.shelved.MainActivity;

import java.util.*;

public class ShelvedModel {
    private List<SimpleBook> bookList = new ArrayList<SimpleBook>();
    private List<SimpleBook> recBookList = new ArrayList<SimpleBook>();
    private List<SimpleReadingList> readingLists = new ArrayList<SimpleReadingList>();
    public static int userID;
    public static String token;

    // listener fields
    Set<SignUpSuccessListener> signUpSuccessListeners = new HashSet<SignUpSuccessListener>();
    Set<LogInSuccessListener> logInSuccessListeners = new HashSet<LogInSuccessListener>();
    Set<ShelfUpdatedListener> shelfUpdatedListeners = new HashSet<ShelfUpdatedListener>();
    Set<BookAddedListener> bookAddedListeners = new HashSet<BookAddedListener>();
    Set<ListsUpdatedListener> listsUpdatedListeners = new HashSet<ListsUpdatedListener>();
    Set<ListAddedListener> listAddedListeners = new HashSet<ListAddedListener>();
    Set<BookAddedToListListener> bookAddedToListListeners = new HashSet<>();
    Set<RecommendedBookListListener> recommendedBookListListeners = new HashSet<>();


    public void initializeReadingLists() {
        SimpleReadingList wishlist = new SimpleReadingList("Wishlist", true);
        this.readingLists.add(wishlist);
    }

    ////////////// Getter methods ///////////////////////
    public List<SimpleBook> getBookList() {
        return Collections.unmodifiableList(bookList);
    }

    public List<SimpleReadingList> getLists() {
        //return Collections.unmodifiableList(readingLists);
        return this.readingLists;
    }

    public List<SimpleBook> getRecBookList() {
        return this.recBookList;
    }


    public static int getUserID() {
        return userID;
    }

    public static String getToken() {
        return token;
    }

    // TODO: Need to fix login: dont put progressDialog here and enable automatic signin
    public void signUp(final Context context, final Continuation<SignupInfo> success,
                       final Continuation<String> failure, String name, String email, String password){

        // Login the user right after sign up success
        Continuation<SignupInfo> localSuccess = new Continuation<SignupInfo>() {
            Continuation<LoginInfo> proceedLoginSuccess = new Continuation<LoginInfo>() {
                @Override
                public void run(LoginInfo loginInfo) {
                    Toast.makeText(context, "Logging you in...", Toast.LENGTH_LONG).show();
                }
            };
            Continuation <String> proceedLoginFailure = new Continuation<String>() {
                @Override
                public void run(String errorMsg) {
                    Toast.makeText(context,
                            errorMsg, Toast.LENGTH_LONG).show();
                }
            };
            @Override
            public void run(SignupInfo signupInfo) {
                logIn(context, proceedLoginSuccess, proceedLoginFailure, signupInfo.getEmail(), signupInfo.getPassword());
            }

        };
        StringRequest strReq = new UserSignUpRequest(context, name, email, password, localSuccess, failure);
        AppSingleton.getInstance(context).addToRequestQueue(strReq, "Sign Up Request");
    };


    public void logIn(final Context context, final Continuation<LoginInfo> success,
                      final Continuation<String> failure, String email, String password){
        Log.d("in Log in", "local success ...");
        Continuation<LoginInfo> localSuccess = new Continuation<LoginInfo>() {
            public void run(LoginInfo info) {
                ShelvedModel.userID = info.getUserID();
                ShelvedModel.token = info.getToken();
                success.run(info);
                Log.d("getting token: ", ShelvedModel.token);
                // Updating the user's book list
                GetBookListStringRequest getBookListStringRequest = new GetBookListStringRequest(context, ShelvedModel.this);
                AppSingleton.getInstance(context).addToRequestQueue(getBookListStringRequest, "get book list");
//                notifyLogInSuccessListener();
                Intent intent = new Intent(
                        context,
                        MainActivity.class);
                context.startActivity(intent);
            }
        };
        StringRequest strReq = new UserLogInRequest(context, email, password, localSuccess, failure);
        AppSingleton.getInstance(context).addToRequestQueue(strReq, "Log In Request");

    }




    public void addBook(SimpleBook book) {
        bookList.add(book);
        notifyShelfUpdatedListeners();
        notifyBookAddedListeners(userID, book);
    }

    public void removeBook(SimpleBook book) {
        bookList.remove(book);
        notifyShelfUpdatedListeners();
    }

    public void setShelf(List<SimpleBook> newBookList) {
        this.bookList.clear();
        this.bookList.addAll(newBookList);
        notifyShelfUpdatedListeners();
    }

    public void addList(SimpleReadingList list) {
        this.readingLists.add(list);
        notifyListUpdatedListeners();
        notifyListAddedListeners(list);
    }

    public void setRecBooksList(List<SimpleBook> recBookList){
        this.recBookList = recBookList;
        Log.d("Setting Rec books", this.recBookList.toString());
        notifyRecommendedBookListListeners();
    }


    public void removeList(SimpleReadingList list) {
        readingLists.remove(list);
        notifyListUpdatedListeners();
    }

    public void setLists(List<SimpleReadingList> newReadingListList) {
        this.readingLists.clear();
        this.readingLists.addAll(newReadingListList);
        notifyListUpdatedListeners();
    }


    //TODO: do this by ints?? not sure
    public void addBookToList(SimpleBook book, SimpleReadingList list) {
        list.addBook(book);
        notifyListUpdatedListeners();
        notifyBookAddedToListListeners(userID, book);
    }

    ///////////////// Shelf Listeners/ updaters /////////////////

    public void addShelfUpdatedListener(ShelfUpdatedListener newShelfUpdatedListener) {
        shelfUpdatedListeners.add(newShelfUpdatedListener);
    }

    private void notifyShelfUpdatedListeners() {
        for (ShelfUpdatedListener listener:this.shelfUpdatedListeners) {
            listener.shelfUpdated();
        }
    }


    ///////////////// ReadingList Listeners/ updaters /////////////////

    public void addListsUpdatedListener(ListsUpdatedListener newListsUpdatedListener) {
        listsUpdatedListeners.add(newListsUpdatedListener);
    }

    private void notifyListUpdatedListeners() {
        for (ListsUpdatedListener listener:this.listsUpdatedListeners) {
            listener.listsUpdated();
        }
    }

    public void addListAddedListener(ListAddedListener newListAddedListener) {
        listAddedListeners.add(newListAddedListener);
    }

    public void notifyListAddedListeners(SimpleReadingList list) {
        for (ListAddedListener listener:this.listAddedListeners) {
            listener.listAdded(list);
        }
    }

    //////////////////Rec Book /////////////////////////////////////
    private void notifyRecommendedBookListListeners() {
        for (RecommendedBookListListener listener:this.recommendedBookListListeners){
            listener.setRecommendedList();
        }
    }

    public void addRecBookListener(RecommendedBookListListener newRecBookListListener) {
        recommendedBookListListeners.add(newRecBookListListener);
    }

    ///////////////// Add book Listeners/ updaters /////////////////

    public void addBookAddedListener(BookAddedListener newBookAddedListener) {
        bookAddedListeners.add(newBookAddedListener);
    }

    private void notifyBookAddedListeners(int userID, SimpleBook book) {
        for (BookAddedListener listener:this.bookAddedListeners) {
            listener.bookAdded(userID, book);
        }
    }

    public void addBookAddedToListListener(BookAddedToListListener newBookAddedToListListener) {
        bookAddedToListListeners.add(newBookAddedToListListener);
    }

    public void notifyBookAddedToListListeners(int userID, SimpleBook book) {
        for (BookAddedToListListener listener:this.bookAddedToListListeners) {
            listener.bookAddedToList(book);
        }
    }

    ///////////////// Sign up success Listeners /////////////////
//    public void addSignUpSuccessListeners(SignUpSuccessListener signUpSuccessListener){
//        signUpSuccessListeners.add(signUpSuccessListener);
//    }
//    private void notifySignUpSuccessListeners(String userName, String email, String password, ProgressDialog progressDialog){
//        for (SignUpSuccessListener listener:this.signUpSuccessListeners){
//            listener.onSignUpSuccess(userName, email, password, progressDialog);
//        }
//    }
//    public void removeAllSignUpSuccessListeners(){
//        signUpSuccessListeners.clear();
//    }


    ///////////////// Sign up attempt Listeners /////////////////
    public void addLogInSuccessListeners(LogInSuccessListener logInSuccessListener){
        logInSuccessListeners.add(logInSuccessListener);
    }
    public void notifyLogInSuccessListener(){
        for (LogInSuccessListener listener: logInSuccessListeners){
            listener.onLogInSuccess();
        }
    }
    public void removeAllLogInSuccessListeners(){ logInSuccessListeners.clear();}
    ///////////////// Scan Listeners/ updaters /////////////////



    public void searchByISBN(final Context context, String ISBN, Continuation<SimpleBook> continuation) {
        StringRequest strReq = new GetBookFromISBNRequest(context, ISBN, continuation);
        // Adding request to request queue
        Log.d("testing","about to add to queue");
        AppSingleton.getInstance(context).addToRequestQueue(strReq, "addSearchByISBN");
        Log.d("testing","Finished adding to queue");
    }
    public void searchByTitleAuthor(final Context context, String title, String author, Continuation<List<SimpleBook>> continuation){
        StringRequest strReq = new GetBookFromTitleAuthorRequest(context, title, author, continuation);
        AppSingleton.getInstance(context).addToRequestQueue(strReq, "searchByTitle");
    }

    public void getRecs(final Context context, String isbn, Continuation<List<SimpleBook>> continuation){
        StringRequest strReq = new GetBookRecsFromISBNRequest(context, isbn, continuation);
        AppSingleton.getInstance(context).addToRequestQueue(strReq, "getRecBooks");
    }

}

