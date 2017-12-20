package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleBook;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleUser;

public class EditProfileDialog extends AlertDialog.Builder {

    private SimpleUser user;
    public Button uploadPicture;

    public EditProfileDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public EditProfileDialog(Context context) {
        super(context);
        this.setTitle("Edit Profile");

        user = new SimpleUser();
        user.setBio("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam felis felis, porta sed elit nec, blandit laoreet sem. Nunc tempus, urna sit amet vehicula pellentesque, lorem orci porta orci, nec pulvinar ipsum risus eu lacus.");

        LinearLayout main = new LinearLayout(context);
        main.setOrientation(LinearLayout.VERTICAL);

        LinearLayout bio = new LinearLayout(context);
        bio.setOrientation(LinearLayout.HORIZONTAL);
        main.addView(bio);

        TextView bioText = new TextView(context);
        bioText.setText("Bio: ");
        bio.addView(bioText);

        EditText bioEdit = new EditText(context);
        bioEdit.setHint(user.getBio());
        bio.addView(bioEdit);

        LinearLayout picture = new LinearLayout(context);
        picture.setOrientation(LinearLayout.HORIZONTAL);
        main.addView(picture);

        TextView pictureText = new TextView(context);
        pictureText.setText("Picture: ");
        picture.addView(pictureText);

        ImageView pictureImage = new ImageView(context);
        pictureImage.setImageResource(R.mipmap.logo);
        picture.addView(pictureImage);

        uploadPicture = new Button(context);
        uploadPicture.setText("UPLOAD");
        picture.addView(uploadPicture);

        this.setView(main);
        this.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v("profile dialog", "submit");

            }
        });
        this.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.v("profile dialog", "cancel");
            }
        });
    }
}
