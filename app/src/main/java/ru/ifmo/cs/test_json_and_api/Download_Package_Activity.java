package ru.ifmo.cs.test_json_and_api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Download_Package_Activity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    private ArrayList<String> packages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_package);
        packages = Json_Class.getListFromJson(getApplicationContext(), R.raw.packages);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_pick_pack);
        // используем linear layout manager
        mRecyclerView.setLayoutManager(mLayoutManager);
        // создаем адаптер
        mAdapter = new Download_Package_Recycler_Adapter(packages,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }
// TODO: 27.03.2017 добавить пакетов
}
