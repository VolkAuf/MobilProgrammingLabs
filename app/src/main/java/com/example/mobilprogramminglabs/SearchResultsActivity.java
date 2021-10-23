package com.example.mobilprogramminglabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;

public class SearchResultsActivity extends AppCompatActivity {

    ArrayAdapter<String> adapter;
    ListView lvSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searchresults);
        Bundle data = getIntent().getExtras();
        lvSearchResults = findViewById(R.id.lvSearchResults);
        if (data.containsKey("SearchResults")) {
            Object results = data.get("SearchResults");
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, (ArrayList<String>) results);
            lvSearchResults.setAdapter(adapter);
        }
    }
}
