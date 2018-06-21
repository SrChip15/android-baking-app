package com.android.kakashi.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private static final int MAX_INITIAL_CAPACITY = 15;

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    static class StepHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView stepShortDescriptionTextView;
        OnItemClickListener itemClickListener;

        StepHolder(View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            stepShortDescriptionTextView = (TextView) itemView;
            itemView.setOnClickListener(this);
        }

        private void bind(String itemText) {
            stepShortDescriptionTextView.setText(itemText);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClicked(getAdapterPosition());
        }
    }

    private Context context;
    private List<String> data = new ArrayList<>(MAX_INITIAL_CAPACITY);

    @SuppressWarnings("WeakerAccess")
    public StepAdapter(Context context, OnItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;

        // test data
        for (int i = 0; i < 11 /* for demo purpose */; i++) {
            data.add("Step #" + (i + 1));
        }
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false);

        return new StepHolder(itemView, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder holder, int position) {
        String textAtPositionFromTestData = data.get(position);
        holder.bind(textAtPositionFromTestData);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
