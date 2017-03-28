package ru.ifmo.cs.test_json_and_api;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;



public class Collection_Activity extends FragmentActivity {
    private ViewPager viewPager;

    private ArrayList<String> saved_words = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        String s = File_Class.readFile(getApplicationContext(),"saved_words");
        saved_words = File_Class.getListFromFileString(s);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        Collection_Swipe_Adaptor swipeAdaptor = new Collection_Swipe_Adaptor(getSupportFragmentManager());

        swipeAdaptor.setCount((saved_words.size() - 1) / 27 + 1);
        swipeAdaptor.setList(saved_words);
        viewPager.setAdapter(swipeAdaptor);

    }

    public void onCollectionClick(View v) {
        Button btn = (Button) v.findViewById(v.getId());
        String text = btn.getText().toString().toLowerCase();
        for (String s : saved_words) {
            String w = Json_Class.getWord(s).toLowerCase();
            if (text.equals(w)) {
                Intent intent = new Intent(getApplicationContext(), Show_Word_Activity.class);
                intent.putExtra("info", s);
                intent.putExtra("class","collection");
                startActivity(intent);
            }
        }

    }

}
