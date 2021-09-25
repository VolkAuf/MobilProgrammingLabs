package com.example.mobilprogramminglabs;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainFragment extends Fragment {

    MainActivity activity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View newView = inflater.inflate(R.layout.fragment_main, null);
        Button btnAdd = newView.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view -> activity.create());
        Button btnEdit = newView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(view -> activity.edit());
        newView.findViewById(R.id.btnSearch).setOnClickListener(view -> activity.search());
        newView.findViewById(R.id.btnDelete).setOnClickListener(view -> activity.delete());
        return newView;
    }

    public void setActivity(MainActivity activity){
        this.activity = activity;
    }
}
