package com.example.mobilprogramminglabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etInStr;
    Button btnAdd;
    Button btnAllOn;
    Button btnAllOff;
    Button btnToast;
    ListView lvMain;
    ArrayAdapter adapter;
    static List<String> list = new LinkedList<>();
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMain = findViewById(R.id.lvMain);
        lvMain.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, list);
        lvMain.setAdapter(adapter);


        etInStr = findViewById(R.id.etInStr);
        btnAdd = findViewById(R.id.btnAdd);
        btnAllOn = findViewById(R.id.btnAllOn);
        btnAllOff = findViewById(R.id.btnAllOff);
        btnToast = findViewById(R.id.btnToast);

        btnAdd.setOnClickListener(v -> {
            if (!etInStr.getText().toString().isEmpty()) {
                adapter.add(etInStr.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(),
                        "ERROR input string", Toast.LENGTH_SHORT).show();
            }
        });

        btnAllOn.setOnClickListener(v -> setAllFlag(true));
        btnAllOff.setOnClickListener(v -> setAllFlag(false));
        btnToast.setOnClickListener(v -> printToast());
    }

    private void setAllFlag(boolean flag) {
        for (int i = 0; i < lvMain.getAdapter().getCount(); i++) {
            lvMain.setItemChecked(i, flag);
        }
    }

    private void printToast() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < lvMain.getAdapter().getCount(); i++) {
            if (lvMain.isItemChecked(i)) {
                str.append(lvMain.getItemAtPosition(i) + ", ");
            }
        }

        toast = Toast.makeText(getApplicationContext(),
                str, Toast.LENGTH_SHORT);
        toast.show();
    }
}