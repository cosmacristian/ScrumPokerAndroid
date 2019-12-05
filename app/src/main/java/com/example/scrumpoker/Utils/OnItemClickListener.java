package com.example.scrumpoker.Utils;

public interface OnItemClickListener {
    void onItemClick(int position);

    void onDeleteClick(int position);

    void onSwitchClick(int position);

    void onLongPress(int position);
}
