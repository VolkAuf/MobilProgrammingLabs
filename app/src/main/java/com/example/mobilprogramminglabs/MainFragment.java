package com.example.mobilprogramminglabs;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainFragment extends Fragment {

    ListView lvMain;
    Button btnAdd;
    Button btnCheckAll;
    Button btnDelete;
    Button btnSearch;

    FragmentTransaction fragmentTransaction;
    View v;

    public static ArrayAdapter<String> adapter;
    public static List<String> list = new LinkedList<>();
    boolean checked = true;

    public MainFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_main, container, false);
        lvMain = v.findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        adapter = new ArrayAdapter<>(v.getContext(),
                android.R.layout.simple_list_item_single_choice, list);
        lvMain.setAdapter(adapter);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        lvMain.setOnItemLongClickListener((parent, v1, selectedIndex, id) -> {

            String selectedText = list.get(selectedIndex);

            AddEditFragment editFragment = new AddEditFragment(selectedText, selectedIndex);
            fragmentTransaction.replace(R.id.main_fragment, editFragment, "tag");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return false;
        });

        @SuppressLint({"NonConstantResourceId", "SetTextI18n"}) View.OnClickListener clickButtons = view -> {
            switch (view.getId()) {
                case R.id.btnCreate:
                    Create();
                    break;
                case R.id.btnCheckAll:
                    CheckAll();
                    break;
                case R.id.btnDelete:
                    Delete();
                    break;
                case R.id.btnSearch:
                    Search();
            }
        };

        btnAdd = v.findViewById(R.id.btnCreate);
        btnAdd.setOnClickListener(clickButtons);
        btnCheckAll = v.findViewById(R.id.btnCheckAll);
        btnCheckAll.setOnClickListener(clickButtons);
        btnDelete = v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(clickButtons);
        btnSearch = v.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(clickButtons);

        return v;
    }

    public void Create() {
        AddEditFragment addFragment = new AddEditFragment();
        fragmentTransaction.replace(R.id.main_fragment, addFragment, "tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void CheckAll() {
        for (int i = 0; i < list.size(); i++) {
            lvMain.setItemChecked(i, checked);
        }
        checked = !checked;
    }

    public void Delete() {
        SparseBooleanArray selected = lvMain.getCheckedItemPositions();
        List<String> checkedList = new LinkedList<>();
        for (int i = 0; i < lvMain.getCount(); i++) {
            if (selected.get(i)) {
                checkedList.add(list.get(i));
            }
        }
        list.removeAll(checkedList);
        adapter.notifyDataSetChanged();
        lvMain.clearChoices();
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
            if (list.get(i).contains(searchStr)) {
                listOut.add(list.get(i));
            }
        }
        if (listOut.size() == 0) {
            Toast.makeText(v.getContext(), "Результатов не найдено", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
        intent.putExtra("SearchResults", listOut);
        startActivity(intent);
    }
}
