package com.example.scrumpoker.QuestionsPage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.scrumpoker.Database.FirebaseDB;
import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.Models.Sessions;
import com.example.scrumpoker.Models.Users;
import com.example.scrumpoker.PokerPage.DeveloperFragment;
import com.example.scrumpoker.PokerPage.MasterFragment;
import com.example.scrumpoker.PokerPage.NameDialog;
import com.example.scrumpoker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class QuestionsPage extends AppCompatActivity implements OnItemClickListener, QuestionDialog.onInputSelected, NumberPicker.OnValueChangeListener {
    String sessionName;
    String sessionAction;
    int selectedTime=-1;
    TextView nameDisplay;
    TextView timeDisplay;
    Button sessionButton;
    Button timeButton;
    RecyclerView recyclerView;
    QuestionAdapter questionAdapter;
    RecyclerView.LayoutManager questionLayoutManager;
    FloatingActionButton addNewQuestionButton;
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_page);
        Intent intent = getIntent();
        sessionName = intent.getStringExtra("sessionName");
        sessionAction = intent.getStringExtra("sessionAction");
        nameDisplay = findViewById(R.id.questions_tv_session_name);
        nameDisplay.setText(sessionName);
        sessionButton = findViewById(R.id.questions_btn_create);
        sessionButton.setText(sessionAction);
        sessionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnSessionButtonPressed();
            }
        });
        recyclerView = findViewById(R.id.questions_recycler);
        addNewQuestionButton = findViewById(R.id.questions_fab);
        timeButton = findViewById(R.id.questions_btn_set_time);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnTimeButtonPressed();
            }
        });
        timeDisplay = findViewById(R.id.questions_tv_session_timelimit);
        questions = new ArrayList<>();
        addNewQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnAddButtonPressed();
            }
        });

        questionLayoutManager = new LinearLayoutManager(this);
        questionAdapter = new QuestionAdapter(questions);
        recyclerView.setLayoutManager(questionLayoutManager);
        recyclerView.setAdapter(questionAdapter);
        recyclerView.setHasFixedSize(true);
        questionAdapter.setOnItemClickListener(this);

        if(sessionAction.equals("Edit Session")){
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
                    selectedTime = dataSnapshot.child("TimeLimit").getValue(Integer.class);
                    timeDisplay.setText("Set limit: "+selectedTime+"minutes per question.");
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
                    String text = dataSnapshot.child("QuestionText").getValue(String.class);//.toString();
                    if(sessname.equals(sessionName)) {
                        questions.add(new Question(sessname, text));
                        questionAdapter.notifyItemInserted(questions.size());
                    }
                    //questionAdapter.AddNewItem(new Question(sessname,text));
                }
            });
        }
    }


    void OnAddButtonPressed(){
        QuestionDialog dialog = new QuestionDialog();
        dialog.show(getSupportFragmentManager(), "new question");
    }

    void OnSessionButtonPressed(){
        if(sessionAction.equals("Create Session")){
            //when created
            FirebaseDB.Instance.CreateNewSession(sessionName,selectedTime,mAuth.getUid());
            for (Question item : questions) {
                FirebaseDB.Instance.InsertQuestion(item.QuestionText,item.SessionName);
            }
            finish();
        }else{
            //when edited
            final DatabaseReference sessionReference = database.getReference().child("Sessions");
            Query query = sessionReference
                    .orderByChild("SessionName")
                    .equalTo(sessionName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Sessions upsess = new Sessions(sessionName,selectedTime,mAuth.getUid());
                        Map<String, Object> sessValues = upsess.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(dataSnapshot1.getKey(), sessValues);
                        sessionReference.updateChildren(childUpdates);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            DatabaseReference questionsReference = database.getReference().child("Questions");
            Query applesQuery = questionsReference.orderByChild("SessionName").equalTo(sessionName);

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot itemSnapshot: dataSnapshot.getChildren()) {
                        itemSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            for (Question item : questions) {
                FirebaseDB.Instance.InsertQuestion(item.QuestionText,item.SessionName);
            }
            finish();
        }
    }


    void OnTimeButtonPressed(){
        NumberPickerDialog newFragment = new NumberPickerDialog();
        newFragment.setValueChangeListener(this);
        newFragment.show(getSupportFragmentManager(), "time picker");
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        questions.remove(position);
        questionAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onLongPress(int position) {
        QuestionDialog dialog = new QuestionDialog(questions.get(position), position);

        dialog.show(getSupportFragmentManager(), "edit question");
    }

    @Override
    public void sendInput(String input,int pos,boolean isEdit) {//MIGHT NOT BE GOOD
        if(isEdit) {
            Question temp = questions.get(pos);
            temp.QuestionText = input;
            questionAdapter.notifyItemChanged(pos);
        }else{
            questions.add(new Question(sessionName,input));
            questionAdapter.notifyItemInserted(questions.size());
            //questionAdapter.AddNewItem(new Question(sessionName,input));
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        selectedTime = numberPicker.getValue();
        timeDisplay.setText("Set limit: "+numberPicker.getValue()+"minutes per question.");
    }
}
