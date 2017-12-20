package edu.swarthmore.cs.cs71.shelved.shelved;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import edu.swarthmore.cs.cs71.shelved.model.bookData.BookInfo;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import edu.swarthmore.cs.cs71.shelved.network.ValidSearchResponseISBN;
import edu.swarthmore.cs.cs71.shelved.network.serialization.GsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    public static final int ISBN = 1;
    public static final int TITLE = 2;
    public static final int AUTHOR = 3;
    int CHOSEN = 0;

    private Button ISBNBtn;
    private Button titleBtn;
    private Button authorBtn;

    PagerAdapter mPagerAdapter;
    ViewPager mViewPager;

    SearchViewModel searchViewModel;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        ISBNBtn = (Button) view.findViewById(R.id.ISBN_button);
        titleBtn = (Button) view.findViewById(R.id.Title_button);
        authorBtn = (Button) view.findViewById(R.id.Author_button);

        searchView = (SearchView) view.findViewById(R.id.search_view);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search for books");

        //  mPagerAdapter = new PagerAdapter(getFragmentManager());
        //  mViewPager = (ViewPager) view.findViewById(R.id.pager);
        //  mViewPager.setAdapter(mPagerAdapter);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ISBNBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CHOSEN = ISBN;
                Fragment fragment = SearchResultsFragment.newInstance();
                replaceFragment(fragment);

            }
        });

        titleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CHOSEN = TITLE;
                Fragment fragment = SearchResultsFragment.newInstance();
                replaceFragment(fragment);
            }
        });

        authorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CHOSEN = AUTHOR;
                Fragment fragment = SearchResultsFragment.newInstance();
                replaceFragment(fragment);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Continuation<SimpleBook> continuationISBN = new Continuation<SimpleBook>() {
                    @Override
                    public void run(SimpleBook book) {
                        searchViewModel = AppSingleton.getInstance(getContext()).getSearchViewModel(getContext());
                        searchViewModel.clearBooks();
                        searchViewModel.getBooklist().add(book);
                        searchViewModel.notifySearchViewModelListeners();
                    }
                };
                Continuation<List<SimpleBook>> continuationTitleAuthor = new Continuation<List<SimpleBook>>() {
                    @Override
                    public void run(List<SimpleBook> books) {
                        searchViewModel = AppSingleton.getInstance(getContext()).getSearchViewModel(getContext());
                        searchViewModel.clearBooks();
                        for (SimpleBook book:books){
                            Log.d("Book:",book.getTitle().getTitle());
                            Log.d("isbn:",book.getISBN().getISBN());
                            searchViewModel.addBook(book);
                        }
                    }
                };

                searchViewModel = AppSingleton.getInstance(getContext()).getSearchViewModel(getContext());
                final ProgressDialog dialog = searchViewModel.newDialogInstance(getContext());
                dialog.setMessage("Searching for " + s);
                dialog.setCancelable(false);
                dialog.show();
                final Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        dialog.dismiss(); // when the task active then close the dialog
                        t.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
                    }
                }, 5000);

                if (CHOSEN == 0) {
                    Toast.makeText(getContext(), "Please choose a category", Toast.LENGTH_SHORT).show();
                } else {
                    switch (CHOSEN) {
                        case ISBN:
                            AppSingleton.getInstance(getContext()).getModel(getContext()).searchByISBN(getContext(), s, continuationISBN);
                            break;
                        case TITLE:
                            AppSingleton.getInstance(getContext()).getModel(getContext()).searchByTitleAuthor(getContext(), s, "", continuationTitleAuthor);
                            break;
                        case AUTHOR:
                            AppSingleton.getInstance(getContext()).getModel(getContext()).searchByTitleAuthor(getContext(), "", s, continuationTitleAuthor);
                            break;
                    }
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.search_results_view, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }



}
