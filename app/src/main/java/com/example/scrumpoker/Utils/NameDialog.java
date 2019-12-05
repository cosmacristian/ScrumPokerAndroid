package com.example.scrumpoker.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.scrumpoker.Activities.GamePage;
import com.example.scrumpoker.Activities.QuestionsPage;
import com.example.scrumpoker.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class NameDialog extends DialogFragment {
    private EditText sessionName;
    private TextView action;
    Context actualContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.session_name_fragment, container, false);
        String act = getArguments().getString("action");
        sessionName = view.findViewById(R.id.session_name_fragment_et_name);
        action= view.findViewById(R.id.session_name_fragment_tv_action);
        action.setText(act);
        actualContext = getContext();
        Button b = view.findViewById(R.id.session_name_fragment_btn_back);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        b = view.findViewById(R.id.session_name_fragment_btn_cont);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toNextPage();
            }
        });

        return view;
    }

    void toNextPage(){
        String input = sessionName.getText().toString();
        if(!input.equals("")) {
            if (action.getText().toString().equals("Create Session")) {
                Intent i = new Intent(actualContext, QuestionsPage.class);
                i.putExtra("sessionName",input);
                i.putExtra("sessionAction","Create Session");
                startActivity(i);
            } else {
                //join sess
                Intent i = new Intent(actualContext, GamePage.class);
                i.putExtra("sessionName",input);
                i.putExtra("sessionAction","Play");
                startActivity(i);
            }
            getDialog().dismiss();
        }else{
            //toast
        }

    }

}
