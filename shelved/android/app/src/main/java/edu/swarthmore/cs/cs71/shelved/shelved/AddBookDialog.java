
package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;


public class AddBookDialog extends AlertDialog.Builder {
    private static final String TAG = "AddBookDialog";
    private String userID;

    public AddBookDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public AddBookDialog(Context context) {
        super(context);

        this.setTitle("Add Book");
        this.userID = userID;

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText titleBox = new EditText(context);
        titleBox.setHint("Title");
        layout.addView(titleBox);


        final EditText authorBox = new EditText(context);
        authorBox.setHint("Author");
        layout.addView(authorBox);


        this.setView(layout);

        this.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                String titleString = titleBox.getText().toString();
                String authorString = authorBox.getText().toString();

                SimpleBook newBook = new SimpleBook();
                newBook.setAuthor(authorString);
                newBook.setTitle(titleString);
                AppSingleton.getInstance(getContext()).getModel(getContext()).addBook(newBook);
            }
        });

        this.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
    }






}
