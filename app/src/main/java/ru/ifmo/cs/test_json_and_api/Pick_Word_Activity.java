package ru.ifmo.cs.test_json_and_api;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Pick_Word_Activity extends AppCompatActivity {

    private final Random r = new Random(SystemClock.currentThreadTimeMillis());
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    private ArrayList<String> list_of_words = new ArrayList<>();
    private String pack_name;

    @Override
    protected void onPostResume() {
        super.onPostResume();
        String str = File_Class.readFile(getApplicationContext(), pack_name);
        list_of_words = File_Class.getListFromFileString(str);
        mAdapter = new Pick_Word_Recycler_Adapter(list_of_words);
        mRecyclerView.setAdapter(mAdapter)  ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pick_word);
        pack_name = getIntent().getStringExtra("pack_name");
        String str = File_Class.readFile(getApplicationContext(), pack_name);
        list_of_words = File_Class.getListFromFileString(str);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_pick_word);

        // если мы уверены, что изменения в контенте не изменят размер layout-а RecyclerView
        // передаем параметр true - это увеличивает производительность
        mRecyclerView.setHasFixedSize(false);

        // используем linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        // создаем адаптер
//        mAdapter = new Pick_Word_Recycler_Adapter(list_of_words);
//        mRecyclerView.setAdapter(mAdapter);
    }
}
