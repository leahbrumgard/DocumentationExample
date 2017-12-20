package edu.swarthmore.cs.cs71.shelved.shelved;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.koushikdutta.ion.Ion;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {
    static final int PICK_CONTACT_REQUEST = 1;  // The request code
    private TextView _ISBN;
    private TextView _Author;
    private TextView _Title;
    private TextView _Genre;
    private TextView _Publisher;
    private TextView _Pages;
    private ImageView _Cover;
    private Button _AddBook;
    private SimpleBook foundBook;
    private static final String TAG = "CameraFragment";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Context context;
        View rootView = inflater.inflate(R.layout.fragment_camera, container, false);
        context = rootView.getContext();
        _ISBN = (TextView) rootView.findViewById(R.id.ISBN);
        _Author = (TextView) rootView.findViewById(R.id.Author);
        _Title = (TextView) rootView.findViewById(R.id.Title);
        _Genre = (TextView) rootView.findViewById(R.id.Genre);
        _Publisher = (TextView) rootView.findViewById(R.id.Publisher);
        _Pages = (TextView) rootView.findViewById(R.id.Pages);
        _Cover = (ImageView) rootView.findViewById(R.id.Cover);
        _AddBook = (Button) rootView.findViewById(R.id.add_book_button);

        Intent intent = new Intent(context, ScannerActivity.class);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_CONTACT_REQUEST && data!=null) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                String ISBN = ScannerActivity.getISBN();
                String ISBNText = "ISBN: " + ISBN;
                _ISBN.setText(ISBNText);

                AppSingleton.getInstance(getContext()).getModel(getContext()).searchByISBN(getContext(), ISBN,
                        new Continuation<SimpleBook>() {
                            @Override
                            public void run(SimpleBook book) {
                                _Author.setText(book.getAuthor().getAuthorName());
                                _Title.setText(book.getTitle().getTitle());
                                String genreText = "Genre: " + book.getGenre().getGenre();
                                _Genre.setText(genreText);
                                String pubText = "Publisher: " + book.getPublisher().getPublisher();
                                _Publisher.setText(pubText);
                                String pageText = "Pages: " + Integer.toString(book.getPages());
                                _Pages.setText(pageText);
                                Ion.with(_Cover).placeholder(R.mipmap.logo).error(R.mipmap.logo).load(book.getImageUrl());
                                foundBook = book;
                            }
                        }
                );

                _AddBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AppSingleton.getInstance(getContext()).getModel(getContext()).addBook(foundBook);
                        Toast.makeText(getContext(), "Added to Shelf", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
