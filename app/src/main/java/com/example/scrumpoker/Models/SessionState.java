package com.example.scrumpoker.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SessionState {
    public String SessionName;
    public boolean Available;

    public SessionState(){

    }

    public SessionState(String SessionName,boolean Available){
        this.SessionName = SessionName;
        this.Available = Available;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("SessionName", SessionName);
        result.put("Available", Available);
        return result;
    }
}