package com.example.scrumpoker.PokerPage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.scrumpoker.Database.FirebaseDB;
import com.example.scrumpoker.MainPageFragment;
import com.example.scrumpoker.Models.Users;
import com.example.scrumpoker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ScrumPokerPage extends AppCompatActivity {
    Users user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrum_poker_page);
        mAuth = FirebaseAuth.getInstance();
        //user = FirebaseDB.Instance.GetUserRole(mAuth.getUid());
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(FirebaseDB.actualuser.Role == "Master"){
            MasterFragment fragment = new MasterFragment();
            fragmentTransaction.add(R.id.pokerpage_fragment_container, fragment);
        }else{
            DeveloperFragment fragment = new DeveloperFragment();
            fragmentTransaction.add(R.id.pokerpage_fragment_container, fragment);
        }
        fragmentTransaction.commit();*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String uid = mAuth.getUid();
        DatabaseReference usersReference = FirebaseDB.database.getReference().child("Users");
        usersReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, @Nullable String s) {
                String id = dataSnapshot.child("UID").getValue(String.class);
                if(id.equals(uid)){
                    String role = dataSnapshot.child("Role").getValue(String.class);//.toString();
                    if(findViewById(R.id.pokerpage_fragment_container) != null){
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        if(role.equals("Master")){
                            MasterFragment fragment = new MasterFragment();
                            fragmentTransaction.add(R.id.pokerpage_fragment_container, fragment);
                        }else{
                            DeveloperFragment fragment = new DeveloperFragment();
                            fragmentTransaction.add(R.id.pokerpage_fragment_container, fragment);
                        }
                        fragmentTransaction.commit();
                    }
                }

            }
        });
    }
}
