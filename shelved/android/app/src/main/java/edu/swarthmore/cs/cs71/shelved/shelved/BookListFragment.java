package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
//import com.koushikdutta.ion.Ion;
import edu.swarthmore.cs.cs71.shelved.model.bookData.BookInfo;
import edu.swarthmore.cs.cs71.shelved.model.bookData.EmptyQueryException;
import edu.swarthmore.cs.cs71.shelved.model.bookData.NotFoundException;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;
import edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel.ListsUpdatedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BookListFragment extends Fragment {
    private GridView gridview;
    private ImageButton addList;
    private List<SimpleReadingList> readingLists;
    private SimpleReadingList list;
    private BookInfo bookInfo = new BookInfo();


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        super.onCreate(savedInstanceState);

        gridview = (GridView) view.findViewById(R.id.list_gridview);
        addList = (ImageButton) view.findViewById(R.id.add_list);
        readingLists = AppSingleton.getInstance(getContext()).getModel(getContext()).getLists();


        gridview.setAdapter(new GridAdapter(readingLists));


        AppSingleton.getInstance(getContext()).getModel(getContext()).addListsUpdatedListener(new ListsUpdatedListener() {
            @Override
            public void listsUpdated() {
//                readingListAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AddListDialog alert = new AddListDialog(getContext(), null);
                alert.show();
                // Handle what happens when a list is created
            }
        });
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                list = (SimpleReadingList)adapterView.getItemAtPosition(i);
                Fragment fragment = ListInfoFragment.newInstance(list, i);
                replaceFragment(fragment);
            }
        });
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_main, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private final int ROW_ITEMS = 1;
    private final class GridAdapter extends BaseAdapter {

        final List<SimpleReadingList> mItems;
        final int mCount;

        private GridAdapter(final List<SimpleReadingList> items) {
            mCount = items.size() * ROW_ITEMS;
            mItems = items;
        }

        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(int i) {
            return mItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {

            View view = convertView;

            if (view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.activity_list_item, parent, false);
            }

            final TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setText(mItems.get(position).getName());
            final ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
            imageView.setImageResource(R.mipmap.logo);

            return view;
        }
    }
}
