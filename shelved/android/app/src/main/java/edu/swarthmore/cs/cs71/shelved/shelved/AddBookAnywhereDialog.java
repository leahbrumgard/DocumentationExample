package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.LinearLayout;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;

public class AddBookAnywhereDialog extends AlertDialog.Builder {
    private static final String TAG = "AddBookAnywhereDialog";
    private String userID;
    private SimpleBook book;

    public AddBookAnywhereDialog(Context context, int themeResId) { super(context, themeResId); }

    public AddBookAnywhereDialog(Context context, final SimpleBook book) {
        super(context);
        this.setTitle("Add Book to Shelf");
        this.userID = userID;
        this.book = book;

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        this.setView(layout);

        this.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String cancel_req_tag = "addBook";

                // if select add to shelf:
                //AppSingleton.getInstance(getContext()).getModel(getContext()).addBook(book);
                // if select add to list:
                //int position = 0;
                //TODO:
                //AppSingleton.getInstance(getContext()).getModel(getContext()).addBookToList(book, position);

                AppSingleton.getInstance(getContext()).getModel(getContext()).addBook(book);
            }
        });

        this.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

    }
}
