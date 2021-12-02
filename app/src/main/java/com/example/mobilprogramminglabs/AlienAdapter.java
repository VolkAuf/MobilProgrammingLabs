package com.example.mobilprogramminglabs;

import static com.example.mobilprogramminglabs.MainFragment.fm;
import static com.example.mobilprogramminglabs.MainFragment.arrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlienAdapter extends ArrayAdapter<JSONObject> {
    int listLayout;
    ArrayList<JSONObject> list;
    Context context;
    Button btnEditData;
    public static ArrayList<JSONObject> checkedArray = new ArrayList<>();

    public AlienAdapter(Context context, int listLayout, int field, ArrayList<JSONObject> list) {
        super(context, listLayout, field, list);
        this.context = context;
        this.listLayout = listLayout;
        this.list = list;

    }

    public View getView(int index, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View itemView = inflater.inflate(listLayout, parent, false);

        TextView name = itemView.findViewById(R.id.name);
        TextView age = itemView.findViewById(R.id.age);
        CheckBox check = itemView.findViewById(R.id.check);
        btnEditData = itemView.findViewById(R.id.btnEdit);

        FragmentManager fragmentManager = fm;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        try {
            name.setText(list.get(index).getString("name"));
            age.setText(list.get(index).getString("age"));
            check.setChecked(list.get(index).getBoolean("checked"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnEditData.setOnClickListener(v -> {
            AddEditFragment editFragment = new AddEditFragment(name.getText().toString(), Integer.parseInt(age.getText().toString()), index);
            fragmentTransaction.replace(R.id.main_fragment, editFragment, "tag");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        check.setOnClickListener(view -> {
            check.setChecked(check.isChecked());
            JSONObject item = arrayList.get(index);

            if (checkedArray.contains(item)) {
                checkedArray.remove(item);
            } else {
                checkedArray.add(item);
            }

        });

        return itemView;
    }
}
