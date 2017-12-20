package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;
import edu.swarthmore.cs.cs71.shelved.network.ResponseMessage;
import edu.swarthmore.cs.cs71.shelved.network.serialization.GsonUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleReadingList;

public class AddListDialog extends AlertDialog.Builder {

    private static final String TAG = "AddListDialog";
    private String userID;

    public AddListDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public AddListDialog (Context context, String userID) {
        super(context);

        this.setTitle("Add List");
        this.userID = userID;

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText listNameBox = new EditText(context);
        listNameBox.setHint("Title");
        layout.addView(listNameBox);

        final Switch status = new Switch(context);
        status.setText("Public");
        status.setShowText(true);
        status.setTextOn("Yes");
        status.setTextOff("No");
        layout.addView(status);

        this.setView(layout);

        this.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v("name", listNameBox.getText().toString());
                Log.v("public", status.getText().toString());

                String cancel_req_tag = "addList";
                String listNameString = listNameBox.getText().toString();
                String statusString = status.getText().toString();
                boolean publicBool;
                if (statusString.equals("Public")) {
                    publicBool = true;
                } else {
                    publicBool = false;
                }

                SimpleReadingList newList = new SimpleReadingList(listNameString, publicBool);
                AppSingleton.getInstance(getContext()).getModel(getContext()).addList(newList);
            }
        });

        this.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
    }
    // TODO: need userID in the constructor
}
