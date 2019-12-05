package com.example.scrumpoker.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.R;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class QuestionDialog extends DialogFragment {

    public interface onInputSelected{
        void sendInput(String input,Date date,int pos,boolean isEdit);
    }

    public onInputSelected inputSelected;
    private EditText questionText;
    private DatePicker datePicker;
    Context actualContext;
    Calendar calendar;
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
        datePicker = view.findViewById(R.id.question_text_fragment_datePicker);
        calendar = Calendar.getInstance();
        calendar.setTime(questionItem.ExpirationDate);
        datePicker.updateDate(calendar.get(calendar.YEAR), calendar.get(calendar.MONTH),calendar.get(calendar.DAY_OF_MONTH));
        return view;
    }

    void onOkPressed(){
        String input = questionText.getText().toString();
        calendar.set(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth());
        Date selectedDate = calendar.getTime();
        if(!input.equals("")) {
            inputSelected.sendInput(input,selectedDate,editPosition,isEdit);
            getDialog().dismiss();
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        inputSelected = (onInputSelected) getActivity();
    }
}