package com.example.scrumpoker.GamePage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scrumpoker.Models.Answer;
import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.QuestionsPage.OnItemClickListener;
import com.example.scrumpoker.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder> {


    private ArrayList<Answer> answerList;


    public AnswerAdapter(ArrayList<Answer> answerList) {
        this.answerList = answerList;
    }

    public void AddNewItem(Answer newAnswer) {
        if (newAnswer != null) {
            this.answerList.add(newAnswer);
        }
    }

    @NonNull
    @Override
    public AnswerAdapter.AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item, parent, false);
        AnswerAdapter.AnswerViewHolder qvh = new AnswerAdapter.AnswerViewHolder(v);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.AnswerViewHolder holder, int position) {
        Answer currentItem = answerList.get(position);
        holder.questionText.setText(currentItem.QuestionText);
        holder.answers.setText(currentItem.AnswerText);
    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public static class AnswerViewHolder extends RecyclerView.ViewHolder {

        public TextView questionText;
        public TextView answers;


        public AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            questionText = itemView.findViewById(R.id.answer_item_q_text);
            answers = itemView.findViewById(R.id.answer_item_count);
        }
    }
}