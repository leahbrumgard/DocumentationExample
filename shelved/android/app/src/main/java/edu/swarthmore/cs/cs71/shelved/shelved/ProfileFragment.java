package edu.swarthmore.cs.cs71.shelved.shelved;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.facebook.login.Login;
import edu.swarthmore.cs.cs71.shelved.model.simple.SimpleUser;
import org.w3c.dom.Text;

public class ProfileFragment extends Fragment implements View.OnClickListener{
    private ImageButton settingsBtn;
    private SimpleUser user;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        settingsBtn = (ImageButton)rootView.findViewById(R.id.popup_settings);
        Button shelvesBtn = (Button) rootView.findViewById(R.id.ShelvesButton);
        Button listsBtn = (Button) rootView.findViewById(R.id.ListsButton);

        shelvesBtn.setOnClickListener(this);
        listsBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.ShelvesButton:
                fragment = ShelfFragment.newInstance(null);
                replaceFragment(fragment);
                break;
            case R.id.ListsButton:
                fragment = new BookListFragment();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.shelf_list_frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View innerView = view;
                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.getMenuInflater().inflate(R.menu.profile_popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()) {
                            case R.id.edit_profile_button:
                                EditProfileDialog dialog = new EditProfileDialog(getContext());
                                Button uploadPicture = (Button) dialog.uploadPicture;
                                uploadPicture.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                        // Start the Intent
                                        startActivity(galleryIntent);
                                    }
                                });
                                dialog.show();
                                return true;
                            case R.id.logout:
                                Intent i = new Intent(getContext(), LoginActivity.class);
                                final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                                        R.style.AppTheme_Dark_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage("Logging out...");
                                progressDialog.show();
                                startActivity(i);
                                return true;
                            default:
                                Toast.makeText(getContext(),
                                    "You Clicked : " + menuItem.getTitle(),
                                    Toast.LENGTH_SHORT).show();
                                return true;

                        }
                    }
                });
                popup.show();
            }
        });
    }
}