package com.example.mobilprogramminglabs;

import static com.example.mobilprogramminglabs.AlienAdapter.checkedArray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static ListView lvMain;
    Button btnAdd;
    Button btnDelete;
    Button btnSearch;
    Button btnLoad;

    FragmentTransaction fragmentTransaction;
    View v;

    boolean checked = true;

    public static SharedPreferences sPref;
    public static ArrayList<JSONObject> SearchArrayResult;
    public static ArrayList<JSONObject> arrayList = new ArrayList<>();
    public static ArrayAdapter<JSONObject> adapter = null;
    public static FragmentManager fm;

    public MainFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_main, container, false);
        lvMain = v.findViewById(R.id.lvMain);
        sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        //создаем adapter для добавления записей в listView
        adapter = new AlienAdapter(getContext(), R.layout.raw, R.id.name, arrayList);
        lvMain.setAdapter(adapter);
        fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if (arrayList.size() == 0) loadData();


        @SuppressLint({"NonConstantResourceId", "SetTextI18n"}) View.OnClickListener clickButtons = view -> {
            switch (view.getId()) {
                case R.id.btnCreate:
                    Create();
                    break;
                case R.id.btnDelete:
                    Delete();
                    break;
                case R.id.btnSearch:
                    Search();
                    break;
                case R.id.btnLoad:
                    Load();
                    break;
            }
        };

        //назначем событие на кнопки
        btnAdd = v.findViewById(R.id.btnCreate);
        btnDelete = v.findViewById(R.id.btnDelete);
        btnSearch = v.findViewById(R.id.btnSearch);
        btnLoad = v.findViewById(R.id.btnLoad);
        btnAdd.setOnClickListener(clickButtons);
        btnDelete.setOnClickListener(clickButtons);
        btnSearch.setOnClickListener(clickButtons);
        btnLoad.setOnClickListener(clickButtons);
        // Inflate the layout for this fragment
        return v;
    }

    public void Create() {
        AddEditFragment addFragment = new AddEditFragment();
        fragmentTransaction.replace(R.id.main_fragment, addFragment, "tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void Delete() {
        arrayList.removeAll(checkedArray);
        adapter = new AlienAdapter(getContext(), R.layout.raw, R.id.name, arrayList);
        lvMain.setAdapter(adapter);
        checkedArray = new ArrayList<>();
        saveData();
    }

    public void Search() {
        EditText etSearch = v.findViewById(R.id.etSearch);
        String searchStr = etSearch.getText().toString().trim();
        if (searchStr.equals("")) {
            Toast.makeText(v.getContext(), "Заполните поле", Toast.LENGTH_LONG).show();
            return;
        }
        LinkedList<String> listOut = new LinkedList<>();

        for (int i = 0; i < lvMain.getCount(); i++) {
            try {
                if (arrayList.get(i).get("name").toString().contains(searchStr) ||
                        arrayList.get(i).get("age").toString().contains(searchStr)) {
                    SearchArrayResult.add(arrayList.get(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (SearchArrayResult.size() == 0) {
            Toast.makeText(v.getContext(), "Результатов не найдено", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
        startActivity(intent);
    }

    public void Load(){
        try {
            String jsonString = getJSonData();
            JSONObject obj = new JSONObject(jsonString);
            JSONArray array = obj.getJSONArray("alien");
            arrayList = getArrayListFromJSONArray(array);
            adapter = new AlienAdapter(getContext(), R.layout.raw, R.id.name, arrayList);
            lvMain.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getJSonData() {
        String json = null;
        try {
            InputStream inputStream = getResources().getAssets().open("alienData.json");
            int size = inputStream.available();
            byte[] data = new byte[size];
            inputStream.read(data);
            inputStream.close();
            json = new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray) {
        ArrayList<JSONObject> aList = new ArrayList<>();
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (JSONException js) {
            js.printStackTrace();
        }
        return aList;
    }

    public void saveData() {
        try {
            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray(arrayList);
            obj.put("alien", arr);
            sPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sPref.edit();
            editor.putString("data", obj.toString());
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void loadData() {
        try {
            String localData = sPref.getString("data", "data");
            JSONObject obj = new JSONObject(localData);
            JSONArray array = obj.getJSONArray("alien");
            arrayList = getArrayListFromJSONArray(array);
            adapter = new AlienAdapter(getContext(), R.layout.raw, R.id.name, arrayList);
            lvMain.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}