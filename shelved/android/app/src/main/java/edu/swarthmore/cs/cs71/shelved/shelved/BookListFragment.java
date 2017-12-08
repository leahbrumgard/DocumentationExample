package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.swarthmore.cs.cs71.shelved.shelved.shelvedModel.ListsUpdatedListener;

//import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;

public class BookListFragment extends Fragment {
    private GridView gridview;
    private ImageButton addList;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lists, container, false);

        super.onCreate(savedInstanceState);

        gridview = (GridView) view.findViewById(R.id.list_gridview);
        gridview.setAdapter(new IconAdapter(getContext()));

        addList = (ImageButton) view.findViewById(R.id.add_list);

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
                Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
            }
        });
    }

        private class IconAdapter extends BaseAdapter {
        private Context mContext;

        public IconAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.mipmap.logo,
                R.mipmap.logo,
                R.mipmap.logo,
                R.mipmap.logo
        };
    }
}
