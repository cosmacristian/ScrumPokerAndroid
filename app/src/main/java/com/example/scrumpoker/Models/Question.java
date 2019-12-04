package com.example.scrumpoker.Models;

import java.util.Calendar;
import java.util.Date;

public class Question {
    public String SessionName;
    public String QuestionText;
    public boolean IsActive;
    public Date ExpirationDate;

    public Question(){
        this.IsActive = true;
        this.ExpirationDate = Calendar.getInstance().getTime();
    }

    public Question(String SessionName,String QuestionText,boolean active, Date ExpirationDate){
        this.QuestionText = QuestionText;
        this.SessionName = SessionName;
        this.IsActive = active;
        this.ExpirationDate = ExpirationDate;
    }

    public Question(String SessionName,String QuestionText, Date ExpirationDate){
        this.QuestionText = QuestionText;
        this.SessionName = SessionName;
        this.IsActive = true;
        this.ExpirationDate = ExpirationDate;
    }

    public Question(String SessionName,String QuestionText){
        this.QuestionText = QuestionText;
        this.SessionName = SessionName;
        this.IsActive = true;
        this.ExpirationDate = Calendar.getInstance().getTime();
    }
}
