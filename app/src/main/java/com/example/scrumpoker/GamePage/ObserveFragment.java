package com.example.scrumpoker.GamePage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.scrumpoker.Models.Answer;
import com.example.scrumpoker.Models.SessionState;
import com.example.scrumpoker.Models.Sessions;
import com.example.scrumpoker.R;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ObserveFragment extends Fragment {
    public FirebaseDatabase database = FirebaseDatabase.getInstance();
    Context actualContext;
    String sessionName;
    String sessionAction;

    RecyclerView recyclerView;
    AnswerAdapter answerAdapter;
    RecyclerView.LayoutManager answerLayoutManager;
    ArrayList<Answer> answers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.observe_fragment, container, false);
        actualContext = getContext();
        if (savedInstanceState != null) {
            return view;
        }
        sessionName = getArguments().getString("sessionName");
        sessionAction = getArguments().getString("sessionAction");
        TextView sessiontv = view.findViewById(R.id.observe_tv_session);
        sessiontv.setText(sessionName);
        Button b = view.findViewById(R.id.observe_btn_clearall);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
            }
        });
        b = view.findViewById(R.id.observe_btn_endsession);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endSession();
            }
        });

        recyclerView = view.findViewById(R.id.observe_recycler);
        answerLayoutManager = new LinearLayoutManager(getContext());
        answerAdapter = new AnswerAdapter(answers);
        recyclerView.setLayoutManager(answerLayoutManager);
        recyclerView.setAdapter(answerAdapter);
        recyclerView.setHasFixedSize(true);

        final DatabaseReference sessionStateReference = database.getReference().child("SessionsState");
        Query query = sessionStateReference
                .orderByChild("SessionName")
                .equalTo(sessionName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SessionState upsess = new SessionState(sessionName,true);
                    Map<String, Object> sessValues = upsess.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(dataSnapshot1.getKey(), sessValues);
                    sessionStateReference.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference userResponsesReference = database.getReference().child("Responses");
        userResponsesReference.addChildEventListener(new ChildEventListener() {
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
                String qtext = dataSnapshot.child("QuestionText").getValue(String.class);
                String atext = dataSnapshot.child("AnswerText").getValue(String.class);
                String user = dataSnapshot.child("CreatedByUID").getValue(String.class);
                if (sessname.equals(sessionName)) {
                    //if(answers.)//  when this works create uniques
                    answers.add(new Answer(sessname,qtext,atext,user));
                    answerAdapter.notifyItemInserted(answers.size());
                }

            }
        });

        return view;
    }

    private void endSession() {
        final DatabaseReference sessionStateReference = database.getReference().child("SessionsState");
        Query query = sessionStateReference
                .orderByChild("SessionName")
                .equalTo(sessionName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    SessionState upsess = new SessionState(sessionName,false);
                    Map<String, Object> sessValues = upsess.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put(dataSnapshot1.getKey(), sessValues);
                    sessionStateReference.updateChildren(childUpdates);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //Close session
        getActivity().finish();
    }

    private void clearAll() {
        DatabaseReference userResponsesReference = database.getReference().child("Responses");
        Query applesQuery = userResponsesReference.orderByChild("SessionName").equalTo(sessionName);
        int count = answers.size();
        answers.clear();
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
        answerAdapter.notifyItemRangeRemoved(0, count);
    }

}
