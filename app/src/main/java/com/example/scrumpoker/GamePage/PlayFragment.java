package com.example.scrumpoker.GamePage;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.scrumpoker.Database.FirebaseDB;
import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.PokerPage.MasterFragment;
import com.example.scrumpoker.PokerPage.NameDialog;
import com.example.scrumpoker.PokerPage.SessionsDialog;
import com.example.scrumpoker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PlayFragment extends Fragment {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    Context actualContext;
    String sessionName;
    String sessionAction;
    int timeLimit;
    int actualPosition=0;
    Question actualQuestion;

    Spinner answersDropDown;
    TextView sessionDisplay;
    TextView questionDisplay;
    TextView timeDisplay;
    CountDownTimer timer;
    boolean onfirstonly=true;
    private ArrayList<Question> questions = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.play_fragment, container, false);
        actualContext = getContext();
        if(savedInstanceState != null){
            return view;
        }
        sessionName = getArguments().getString("sessionName");
        sessionAction = getArguments().getString("sessionAction");
        // Inflate the layout for this fragment
        answersDropDown = view.findViewById(R.id.play_sp_dropdown);
        answersDropDown.setVisibility(View.INVISIBLE);
        sessionDisplay = view.findViewById(R.id.play_tv_session);
        sessionDisplay.setText(sessionName);
        questionDisplay = view.findViewById(R.id.play_tv_question);
        timeDisplay = view.findViewById(R.id.play_tv_timer);

        FloatingActionButton b = view.findViewById(R.id.play_fab);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAnswer();
            }
        });

        DatabaseReference sessionStateReference = database.getReference().child("SessionsState");
        sessionStateReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                boolean active = dataSnapshot.child("Available").getValue(boolean.class);
                if(active == true){
                    loadData();
                }else{
                    getActivity().finish();
                }
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
                boolean active = dataSnapshot.child("Available").getValue(boolean.class);
                if(active == true){
                    loadData();
                }else{
                    getActivity().finish();
                }
            }
        });

        return view;
    }

    void loadData(){

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
                timeLimit = dataSnapshot.child("TimeLimit").getValue(Integer.class);
            }
        });
        DatabaseReference questionsReference = database.getReference().child("Questions");
        questionsReference.addChildEventListener(new ChildEventListener() {
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
                String text = dataSnapshot.child("QuestionText").getValue(String.class);
                if(sessname.equals(sessionName)) {
                    questions.add(new Question(sessname, text));
                    if(onfirstonly){
                        actualQuestion = questions.get(actualPosition);
                        questionDisplay.setText(actualQuestion.QuestionText);            ///This might not work
                        timer = new CountDownTimer(timeLimit * 60000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                timeDisplay.setText("Time remaining: " + millisUntilFinished / 60000 +":"+millisUntilFinished / 1000);
                            }

                            public void onFinish() {
                                nextQuestion();
                            }

                        }.start();
                        answersDropDown.setVisibility(View.VISIBLE);
                        onfirstonly =false;
                    }
                }

            }
        });

    }


    void nextQuestion(){
        actualPosition+=1;
        if(questions.size()>actualPosition){
            actualQuestion = questions.get(actualPosition);
            questionDisplay.setText(actualQuestion.QuestionText);
            if(timer!=null){
                timer.cancel();
                timer.start();
            }
        }else{
            getActivity().finish();
        }

    }

    void sendAnswer(){
        String input = String.valueOf(answersDropDown.getSelectedItem());
        if(!input.equals("")) {
            FirebaseDB.Instance.InsertResponse(sessionName,actualQuestion.QuestionText,input,mAuth.getUid() );
            nextQuestion();
        }
    }
}
