package ru.ifmo.cs.test_json_and_api;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Yura on 18.03.2017.
 */

public class Collection_Swipe_Adaptor extends FragmentStatePagerAdapter {
    public Collection_Swipe_Adaptor(FragmentManager fm) {
        super(fm);
    }

    private static ArrayList<String> saved_words = new ArrayList<>();
    private int count;

    public void setList(ArrayList<String> list) {
        saved_words = list;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Collection_Page_Fragment();
        Bundle bundle = new Bundle();
        int size = saved_words.size();
        bundle.putInt("view", position);
        bundle.putInt("size", size);
        if (size > 0) {
            int i = 0;
            for (String s : saved_words) {
                bundle.putString(String.valueOf(i), Json_Class.getWord(s));
                i++;
            }
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }
}
