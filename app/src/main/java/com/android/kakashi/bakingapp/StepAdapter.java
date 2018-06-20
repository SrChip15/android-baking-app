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
    static class StepHolder extends RecyclerView.ViewHolder {
        TextView stepShortDescriptionTextView;

        StepHolder(View itemView) {
            super(itemView);
            stepShortDescriptionTextView = (TextView) itemView;
        }

        private void bind(String itemText) {
            stepShortDescriptionTextView.setText(itemText);
        }
    }

    private Context context;
    private List<String> data = new ArrayList<>(15);

    @SuppressWarnings("WeakerAccess")
    public StepAdapter(Context context) {
        this.context = context;

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

        return new StepHolder(itemView);
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
