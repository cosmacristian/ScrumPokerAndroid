package com.example.scrumpoker.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.scrumpoker.Fragments.MainPageFragment;
import com.example.scrumpoker.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_container) != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            MainPageFragment fragment = new MainPageFragment();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
        }
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
