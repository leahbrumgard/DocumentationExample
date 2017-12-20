package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;

public class AddBookToListDialog extends AlertDialog.Builder {
    private static final String TAG = "AddBookDialog";
    private String userID;
    private SimpleBook book;
    private SimpleReadingList list;

    public AddBookToListDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public AddBookToListDialog(Context context, final SimpleReadingList list, final SimpleBook book) {
        super(context);
        this.setTitle("Add Book to Wishlist?");
        this.userID = userID;
        this.book = book;
        this.list = list;

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        this.setView(layout);

        this.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                AppSingleton.getInstance(getContext()).getModel(getContext()).addBookToList(book, list);
            }
        });

        this.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

    }
}
