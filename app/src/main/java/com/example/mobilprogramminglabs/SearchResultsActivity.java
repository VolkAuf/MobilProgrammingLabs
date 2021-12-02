package com.example.mobilprogramminglabs;

import static com.example.mobilprogramminglabs.MainFragment.SearchArrayResult;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class SearchResultsActivity extends AppCompatActivity {

    ArrayAdapter<JSONObject> adapter;
    ListView lvSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_searchresults);
        Bundle data = getIntent().getExtras();
        lvSearchResults = findViewById(R.id.lvSearchResults);
        adapter = new AlienAdapter(this, R.layout.raw, R.id.name, SearchArrayResult);
        lvSearchResults.setAdapter(adapter);
    }
}
