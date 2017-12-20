package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;
import edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel.BookAddedToListListener;

import java.util.List;

public class ListInfoFragment extends ListFragment {
    private BookListAdapter bookListAdapter;
    private static final String TAG = "ListInfoFragment";
    private ListView readingList;
    private ImageButton addBook;
    private SimpleBook book;
    private SimpleReadingList list;
    private String listName;
    private boolean publicStatus;
    private List<SimpleBook> books;
    private int listPosition;


    //TODO: make bundle stuff like in book info fragment to pass list around
    //TODO: copy position stuff from shelffragment to listfragment

    public static ListInfoFragment newInstance(SimpleReadingList list, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("listName", list.getName());
        bundle.putBoolean("publicStatus", list.isPublicStatus());
        bundle.putInt("position", position);
        ListInfoFragment fragment = new ListInfoFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setArguments(Bundle bundle) {
        this.listName = bundle.getString("listName");
        this.publicStatus = bundle.getBoolean("publicStatus");
        this.listPosition = bundle.getInt("position");
        this.list = new SimpleReadingList(listName, publicStatus);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_list, container, false);

        readingList = (ListView)view.findViewById(android.R.id.list);
        this.bookListAdapter = new BookListAdapter(getContext(),
                AppSingleton.getInstance(getContext()).getModel(getContext()).getLists().get(listPosition).getList());
        readingList.setAdapter(bookListAdapter);

        addBook = (ImageButton)view.findViewById(R.id.add_book);
        Bundle argss = getArguments();

        setFieldsFromList(view);
        return view;
    }

    public void replaceFragment(android.support.v4.app.Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_main, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create and show AddBookDialog
                //AddBookToListDialog alert = new AddBookToListDialog(getContext(), list);
                Log.d(TAG, "show add book dialog");
                Log.d(TAG, "called newInstance");
                //alert.show();
            }
        });

        //TODO: change this stuff once we have list stuff in shelf Model
        readingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                book = (SimpleBook)adapterView.getItemAtPosition(position);
                //setFieldsFromBook(book, view);
                Log.d("AUTHOR IN SHELF FRAG", book.getAuthor().getAuthorName());
                android.support.v4.app.Fragment fragment = BookInfoFragment.newInstance(book);
                replaceFragment(fragment);
            }
        });
    }

    public void setFieldsFromList(View view) {
        TextView nameText = (TextView) view.findViewById(R.id.ListName);
        nameText.setText(this.listName);
    }
}
