package com.android.kakashi.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    @BindView(R.id.step_description_tv)
    TextView stepDescriptionTextView;
    private String shortDescription;

    private static final String ARG_SHORT_DESC = "stepShortDescription";

    public static StepFragment newInstance(String description) {
        Bundle args = new Bundle();
        args.putString(ARG_SHORT_DESC, description);

        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            shortDescription = getArguments().getString(ARG_SHORT_DESC);
        } else {
            throw new IllegalArgumentException("Expected step argument missing!");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_step, container, false);
        ButterKnife.bind(this, view);

        stepDescriptionTextView.setText(shortDescription);

        return view;
    }
}
