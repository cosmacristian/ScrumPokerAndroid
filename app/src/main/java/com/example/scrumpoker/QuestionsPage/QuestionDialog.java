package com.example.scrumpoker.QuestionsPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class QuestionDialog extends DialogFragment {

    public interface onInputSelected{
        void sendInput(String input,int pos,boolean isEdit);
    }

    public onInputSelected inputSelected;
    private EditText questionText;
    Context actualContext;
    private Question questionItem;
    private int editPosition;
    private boolean isEdit;

    public QuestionDialog(){
        questionItem = new Question();
        editPosition = 0;
        isEdit = false;
    }

    @SuppressLint("ValidFragment")
    public QuestionDialog(Question editQuestion, int pos){
        questionItem = editQuestion;
        editPosition = pos;
        isEdit = true;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.question_text_fragment, container, false);
        questionText = view.findViewById(R.id.question_text_fragment_et_text);
        actualContext = getContext();
        Button b = view.findViewById(R.id.question_text_fragment_btn_cancel);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        b = view.findViewById(R.id.question_text_fragment_btn_ok);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOkPressed();
            }
        });
        questionText.setText(questionItem.QuestionText);
        return view;
    }

    void onOkPressed(){
        String input = questionText.getText().toString();
        if(!input.equals("")) {
            inputSelected.sendInput(input,editPosition,isEdit);
            getDialog().dismiss();
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inputSelected = (onInputSelected) getActivity();
    }
}