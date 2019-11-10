package com.example.scrumpoker.Models;

import java.util.Calendar;
import java.util.Date;

public class Answer {
    public String SessionName;
    public String QuestionText;
    public String CreatedByUID;
    public String AnswerText;
    public Date CreatedOn;

    public Answer(){

    }

    public Answer(String SessionName,String QuestionText,String AnswerText,String CreatedByUID){
        this.SessionName = SessionName;
        this.QuestionText = QuestionText;
        this.AnswerText = AnswerText;
        this.CreatedOn = Calendar.getInstance().getTime();
        this.CreatedByUID = CreatedByUID;
    }
}
