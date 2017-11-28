
package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;


public class AddBookDialog extends AlertDialog.Builder {
    private static final String TAG = "AddBookDialog";

    private String getAddBookUrl() {

        return "http://"+getString((R.string.server_url))+":4567/addBook";
    }

    public AddBookDialog(Context context) {
        super(context);
    }

    public AddBookDialog newInstance() {
        Context context = getContext();
        AddBookDialog alert = new AddBookDialog(context);
        alert.setTitle("Add Book");


        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText titleBox = new EditText(context);
        titleBox.setHint("Title");
        layout.addView(titleBox);

        final EditText authorBox = new EditText(context);
        authorBox.setHint("Author");
        layout.addView(authorBox);

        alert.setView(layout);

        alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v("title", titleBox.getText().toString());
                Log.v("author", authorBox.getText().toString());


                String titleString = titleBox.getText().toString();
                String authorString = authorBox.getText().toString();
                // TODO: This StringRequest is still under construction
                StringRequest strReq = new StringRequest(Request.Method.POST,getAddBookUrl(),);

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        return alert;
    }





    public AddBookDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}

