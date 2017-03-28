package ru.ifmo.cs.test_json_and_api;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Show_Word_Activity extends AppCompatActivity {

    private String info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_word);
        Intent intent = getIntent();
        String income_class = intent.getStringExtra("class");
        Boolean saved = intent.getBooleanExtra("saved",false);
        info = intent.getStringExtra("info");
        Button btn = (Button) findViewById(R.id.add);
        String word = Json_Class.getWord(info);
        if (income_class.equals("pick") && !saved) {
                btn.setVisibility(View.VISIBLE);
        }
        ((TextView) findViewById(R.id.word)).setText(File_Class.ValidateText(word));
        ((TextView) findViewById(R.id.info)).setText(Json_Class.parseJson(info));
    }

    public void addWord(View v){
        File_Class.writeFile(getApplicationContext(),"saved_words",info);
        onBackPressed();
    }
}
