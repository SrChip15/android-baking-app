package com.android.kakashi.bakingapp;

import java.util.ArrayList;
import java.util.List;

public class MockData {
    private static final int MAX_SIZE = 11;
    private static List<String> data = new ArrayList<>(MAX_SIZE);


    private MockData() {
    }

    public static List<String> getData() {
        if (data.size() == 0) {
            // create test data
            for (int i = 0; i < MAX_SIZE; i++) {
                data.add("Step #" + (i + 1));
            }
        }

        return data;
    }
}
