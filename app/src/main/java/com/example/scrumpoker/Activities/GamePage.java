package com.example.scrumpoker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.scrumpoker.Fragments.ObserveFragment;
import com.example.scrumpoker.Fragments.PlayFragment;
import com.example.scrumpoker.R;
import com.google.firebase.auth.FirebaseAuth;

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
}
