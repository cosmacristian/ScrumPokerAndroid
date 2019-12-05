package com.example.scrumpoker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.scrumpoker.Fragments.ObserveFragment;
import com.example.scrumpoker.Fragments.PlayFragment;
import com.example.scrumpoker.R;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;

public class GamePage extends AppCompatActivity {
    String sessionName;
    String sessionAction;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);
        Intent intent = getIntent();
        sessionName = intent.getStringExtra("sessionName");
        sessionAction = intent.getStringExtra("sessionAction");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle args = new Bundle();
        args.putString("sessionName", sessionName);
        args.putString("sessionAction", sessionAction);
        if(sessionAction.equals("Play")){
            PlayFragment fragment = new PlayFragment();
            fragment.setArguments(args);
            fragmentTransaction.add(R.id.gamepage_fragment_container, fragment);
        }else{
            ObserveFragment fragment = new ObserveFragment();
            fragment.setArguments(args);
            fragmentTransaction.add(R.id.gamepage_fragment_container, fragment);
        }
        fragmentTransaction.commit();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        // Action View
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Configure the search info and add any event listeners
        //return super.onCreateOptionsMenu(menu);
        return true;
    }


    // Determines if Action bar item was selected. If true then do corresponding action.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.action_change_theme:
                int nightmode = AppCompatDelegate.getDefaultNightMode();
                View star = findViewById(R.id.action_change_theme);
                if(nightmode==AppCompatDelegate.MODE_NIGHT_YES){
                    // nightmode = false;
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO);
                    if (item != null) {
                        item.setIcon(android.R.drawable.btn_star_big_on);
                    }
                }else{
                    //nightmode = true;
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    if (item != null) {
                        item.setIcon(android.R.drawable.btn_star_big_off);
                    }
                }
                recreate();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
