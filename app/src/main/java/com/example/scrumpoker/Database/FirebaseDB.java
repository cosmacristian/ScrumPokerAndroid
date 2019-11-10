package com.example.scrumpoker.Database;

import com.example.scrumpoker.Models.Answer;
import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.Models.Sessions;
import com.example.scrumpoker.Models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Duration;

import androidx.annotation.NonNull;

public class FirebaseDB {
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference sessionReference = database.getReference().child("Sessions");
    static DatabaseReference questionsReference = database.getReference().child("Questions");
    static DatabaseReference usersReference = database.getReference().child("Users");
    static DatabaseReference userResponsesReference = database.getReference().child("Responses");
    //public static ArrayList<QuestionItem> questions = new ArrayList<>();
    public static Users actualuser = new Users();
    public static Users ActualUserRole = new Users();
    public FirebaseDB() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        sessionReference.keepSynced(true);
        questionsReference.keepSynced(true);
        usersReference.keepSynced(true);

        ValueEventListener UserListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Users post = dataSnapshot.getValue(Users.class);
                actualuser.UID = post.UID;
                actualuser.Role = post.Role;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersReference.addValueEventListener(UserListener);
    }

    public static class Instance {
        public static String CreateNewSession(String sessionName, int timeLimit, String createdByUID) {
            Sessions session = new Sessions(sessionName,timeLimit,createdByUID);
            String key = sessionReference.push().getKey();
            sessionReference.child(key).setValue(session);
            return key;
        }

        public static String InsertQuestion(String question, String sessionName) {
            Question quest = new Question(sessionName,question);
            String key = questionsReference.push().getKey();
            questionsReference.child(key).setValue(quest);
            return key;
        }

        public static String InsertResponse(String sessionText,String questionText, String answerText, String createdByUID) { ///////////////////
            Answer quest = new Answer(sessionText,questionText,answerText,createdByUID);
            String key = userResponsesReference.push().getKey();
            userResponsesReference.child(key).setValue(quest);
            return key;
        }

        public static String AddUserRole(String uid, String role) {
            Users usr = new Users(uid,role);
            String key = usersReference.push().getKey();
            usersReference.child(key).setValue(usr);
            return key;
        }

        public static Users GetUserRole(final String uid){
            //usersReference.
            usersReference.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        String id = item.child("UID").getValue().toString();
                        if(id == uid){
                            String role = item.child("Role").getValue().toString();
                            ActualUserRole.UID = id;
                            ActualUserRole.Role = role;
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return ActualUserRole;
        }
        /*public static String CreateNewSessionWithQuestions(String sessionName, ArrayList<QuestionItem> questions) {

            String sessionKey = CreateNewSession(sessionName);
            if (sessionKey == null) {
                return "Invalid";
            }

            for (QuestionItem question : questions) {
                InsertQuestion(question, sessionKey);
            }

            return sessionKey;
        }
        public static void QuestionsUpdate(ArrayList<QuestionItem> q) {
            questions.addAll(q);

        }

        public static ArrayList<QuestionItem> GetQuestionsForSession(final String session) {

            questionsReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        String txt = item.child("sessionKey").getValue().toString();

                        if (txt.equals(session)) {
                            String q = item.child("question").getValue().toString();
                            String d = item.child("difficulty").getValue().toString();
                            QuestionItem q1 = new QuestionItem(q, d);
                            questions.add(q1);
                        }

                        QuestionsUpdate(questions);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return questions;
        }


        */

    }


}
