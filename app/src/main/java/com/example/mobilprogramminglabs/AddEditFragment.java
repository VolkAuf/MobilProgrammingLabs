package com.example.mobilprogramminglabs;

import static com.example.mobilprogramminglabs.MainFragment.*;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddEditFragment extends Fragment {

    EditText etInput;
    Button btnSave;
    Button btnCancel;

    String selectedText;
    Integer selectedIndex;


    EditText etName;
    EditText etAge;
    CheckBox check;
    Integer position = null;

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> ages = new ArrayList<>();

    String name;
    int age;
    int index;


    public AddEditFragment() {
    }

    public AddEditFragment(String name, int age, int index) {
        this.name = name;
        this.age = age;
        this.index = index;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addedit, container, false);
        etName = v.findViewById(R.id.etName);
        etAge = v.findViewById(R.id.etAge);
        check = v.findViewById(R.id.check);

        if (name != null) {
            etName.setText(name);
            etAge.setText(age);
        }
        View.OnClickListener clickButton = view -> {
            if (name == null) {
                JSONObject newJSON = new JSONObject();
                try {
                    newJSON.put("name", etName.getText());
                    newJSON.put("age", etAge.getText());
                    newJSON.put("checked", false);
                    arrayList.add(newJSON);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String name;
                    String age;
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (i == position) {
                            name = etName.getText().toString();
                            age = etAge.getText().toString();
                        } else {
                            name = arrayList.get(i).get("name").toString();
                            age = arrayList.get(i).get("age").toString();
                        }
                        names.add(name);
                        ages.add(age);
                    }
                    int count = arrayList.size();
                    arrayList = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        JSONObject newJSON = new JSONObject();
                        newJSON.put("name", names.get(i));
                        newJSON.put("age", ages.get(i));
                        arrayList.add(newJSON);
                    }
                    adapter = new AlienAdapter(getContext(), R.layout.raw, R.id.name, arrayList);
                    lvMain.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            saveData();
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();

        };
        btnSave = v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(clickButton);
        return v;
    }

    private void saveData() {
        try {
            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray(arrayList);
            obj.put("alien", arr);
            SharedPreferences.Editor editor = sPref.edit();
            editor.putString("data", obj.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
