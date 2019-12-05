package com.example.scrumpoker.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.scrumpoker.Models.Question;
import com.example.scrumpoker.Utils.OnItemClickListener;
import com.example.scrumpoker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {


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
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
        holder.questionText.setText(currentItem.QuestionText);
        holder.dateText.setText(simpleDate.format(currentItem.ExpirationDate));
        holder.isActive.setChecked(currentItem.IsActive);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {

        public TextView questionText;
        public TextView dateText;
        public ImageView DeleteQuestion;
        public Switch isActive;

        public QuestionViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            questionText = itemView.findViewById(R.id.question_item_text);
            DeleteQuestion = itemView.findViewById(R.id.question_item_delete);
            dateText = itemView.findViewById(R.id.question_item_date);
            isActive = itemView.findViewById(R.id.question_item_switch);

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

            isActive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSwitchClick(position);
                        }
                    }
                }
            });

        }
    }
}
