package com.android.kakashi.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.kakashi.bakingapp.R;
import com.android.kakashi.bakingapp.data.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepFragment extends Fragment {

    @BindView(R.id.step_description_tv)
    TextView stepDescriptionTextView;
    private Step step;

    private static final String ARG_STEP = "step";

    public static StepFragment newInstance(Step currentStep) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_STEP, currentStep);

        StepFragment fragment = new StepFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            step = getArguments().getParcelable(ARG_STEP);
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

        String description = step.getDescription();
        stepDescriptionTextView.setText(description);

        return view;
    }
}
