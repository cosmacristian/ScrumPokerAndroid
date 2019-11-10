package com.example.scrumpoker.QuestionsPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {


    private ArrayList<Question> questionList;

    private OnItemClickListener Listener;


    public QuestionAdapter(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        Listener = listener;
    }

    public void AddNewItem(Question newQuestion) {
        if (newQuestion != null) {
            this.questionList.add(newQuestion);
        }
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item, parent, false);
        QuestionViewHolder qvh = new QuestionViewHolder(v, Listener);
        return qvh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        Question currentItem = questionList.get(position);
        holder.questionText.setText(currentItem.QuestionText);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        public TextView questionText;
        public ImageView DeleteQuestion;


        public QuestionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            questionText = itemView.findViewById(R.id.question_item_text);
            DeleteQuestion = itemView.findViewById(R.id.question_item_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLongPress(position);
                        }
                    }

                    return true;
                }
            });

            DeleteQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

        }
    }
}
