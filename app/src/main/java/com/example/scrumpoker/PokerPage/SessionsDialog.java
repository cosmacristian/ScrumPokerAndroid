package com.example.scrumpoker.PokerPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.scrumpoker.GamePage.GamePage;
import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.QuestionsPage.QuestionsPage;
import com.example.scrumpoker.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SessionsDialog extends DialogFragment {
    Context actualContext;
    ListView listView;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.session_list_fragment, container, false);
        actualContext = getContext();
        listView = view.findViewById(R.id.session_list);
        final String act = getArguments().getString("action");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1,new ArrayList<String>());

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                int itemPosition = position;
                String  itemValue = (String) listView.getItemAtPosition(position);
                if(act.equals("Edit Session")){

                    Intent i = new Intent(actualContext, QuestionsPage.class);
                    i.putExtra("sessionName",itemValue);
                    i.putExtra("sessionAction","Edit Session");
                    startActivity(i);
                }else{
                    Intent i = new Intent(actualContext, GamePage.class);
                    i.putExtra("sessionName",itemValue);
                    i.putExtra("sessionAction","Observe Session");
                    startActivity(i);
                }


                getDialog().dismiss();
            }

        });

        DatabaseReference sessionReference = database.getReference().child("Sessions");
        sessionReference.addChildEventListener(new ChildEventListener() {
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
                String sessname = dataSnapshot.child("SessionName").getValue(String.class);
                //String text = dataSnapshot.child("QuestionText").getValue(String.class);//.toString();
                adapter.add(sessname);
                //questionAdapter.AddNewItem(new Question(sessname,text));
            }
        });
        return view;
    }



}