package com.example.scrumpoker.Models;

public class Question {
    public String SessionName;
    public String QuestionText;

    public Question(){

    }

    public Question(String SessionName,String QuestionText){
        this.QuestionText = QuestionText;
        this.SessionName = SessionName;
    }
}
