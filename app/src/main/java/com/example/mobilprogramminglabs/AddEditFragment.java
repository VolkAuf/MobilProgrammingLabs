package com.example.mobilprogramminglabs;

import static com.example.mobilprogramminglabs.MainFragment.list;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

public class AddEditFragment extends Fragment {

    EditText etInput;
    Button btnSave;
    Button btnCancel;

    String selectedText;
    Integer selectedIndex;

    public AddEditFragment() {
    }

    public AddEditFragment(String selectedText, int selectedIndex) {
        this.selectedText = selectedText;
        this.selectedIndex = selectedIndex;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addedit, container, false);
        etInput = v.findViewById(R.id.etInput);
        btnSave = v.findViewById(R.id.btnSave);
        btnCancel = v.findViewById(R.id.btnCancel);
        if (selectedText != null) etInput.setText(selectedText);

        btnSave.setOnClickListener((view) -> {
            String inputText = etInput.getText().toString().trim();
            if (inputText.isEmpty()) return;

            if (selectedIndex == null) {
                list.add(inputText);
            } else {
                list.set(selectedIndex, inputText);
            }

            MainFragment.adapter.notifyDataSetChanged();
            etInput.setText("");
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
        });

        btnCancel.setOnClickListener((view) -> {
            etInput.setText("");
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
        });

        return v;
    }
}
