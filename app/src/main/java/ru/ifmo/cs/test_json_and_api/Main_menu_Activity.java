package ru.ifmo.cs.test_json_and_api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main_menu_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
//        File_Class.cleanAll(getApplicationContext());
    }

    public void onClick_Menu(View v){
        switch (v.getId()){
            case R.id.find_btn:
                startActivity(new Intent(getApplicationContext(), Find_Word_Activity.class));
                break;
            case R.id.download_packs:
                startActivity(new Intent(getApplicationContext(),Download_Package_Activity.class));
                break;
            case R.id.pick_btn:
                startActivity(new Intent(getApplicationContext(), Pick_Package_Activity.class));
                break;
            case R.id.collection_btn:
                startActivity(new Intent(getApplicationContext(), Collection_Activity.class));
                break;
        }
    }
    // TODO: 27.03.2017 добавить динамическое подгружение во все ресайклер адаптеры
}
