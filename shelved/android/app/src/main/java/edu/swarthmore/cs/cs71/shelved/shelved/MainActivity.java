package edu.swarthmore.cs.cs71.shelved.shelved;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.NoSuchElementException;


public class  MainActivity extends AppCompatActivity {
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Intent intent = getIntent();
            userId = intent.getStringExtra("userId");
            Log.d("MainActivity", "userID: "+userId);
        }
        catch (Exception e){
            throw new NoSuchElementException();
        }
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                selectedFragment = ShelfFragment.newInstance(userId);
                                break;
                            case R.id.action_item2:
                                selectedFragment = SearchFragment.newInstance();
                                break;
                            case R.id.action_item3:
                                selectedFragment = CameraFragment.newInstance();
                                break;
                            case R.id.action_item4:
                                selectedFragment = ConnectFragment.newInstance();
                                break;
                            case R.id.action_item5:
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout_main, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        // Getting userID from LoginActivity
        // Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_main, ShelfFragment.newInstance(userId));
        transaction.addToBackStack(null);
        transaction.commit();

        // Used to select an item programmatically
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        // TODO: Try to get userID after transferring from Login
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getUserId() {
        return userId;
    }
}
