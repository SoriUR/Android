package ru.ifmo.cs.test_json_and_api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Pick_Package_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    private ArrayList<String> list_of_packs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_package);
        String str = File_Class.readFile(getApplicationContext(), "downloaded_packs");
        list_of_packs = File_Class.getListFromFileString(str);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_pick_pack);
        // используем linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        // создаем адаптер
        mAdapter = new Pick_Package_Recycler_Adapter(list_of_packs);
        mRecyclerView.setAdapter(mAdapter);
    }
}
