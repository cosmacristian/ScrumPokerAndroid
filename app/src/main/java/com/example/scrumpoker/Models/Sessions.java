package com.example.scrumpoker.Models;

import com.google.firebase.database.Exclude;

import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sessions {
    public String SessionName;
    public int TimeLimit;
    public Date CreatedOn;
    public String CreatedByUID;

    public Sessions(){

    }

    public Sessions(String SessionName,int TimeLimit,String CreatedByUID){
        this.SessionName = SessionName;
        this.TimeLimit = TimeLimit;
        this.CreatedOn = Calendar.getInstance().getTime();
        this.CreatedByUID = CreatedByUID;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("SessionName", SessionName);
        result.put("TimeLimit", TimeLimit);
        result.put("CreatedOn", CreatedOn);
        result.put("CreatedByUID", CreatedByUID);

        return result;
    }
}
