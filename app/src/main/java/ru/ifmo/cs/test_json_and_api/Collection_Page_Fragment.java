package ru.ifmo.cs.test_json_and_api;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class Collection_Page_Fragment extends Fragment {


    public Collection_Page_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_layout, container, false);

        Bundle bundle = getArguments();
        int view_num = bundle.getInt("view");
        int words_count = bundle.getInt("size");
        if (words_count == 0) {
            view.findViewById(R.id.fragment_text).setVisibility(1);
            return view;
        }

        int id = R.id.btn1;
        int counter = 0;
        for (int i = view_num * 27; i < 27 * (view_num + 1) && i < words_count; i++) {
            Button current_btn = (Button) view.findViewById(id);
            String str = bundle.getString(String.valueOf(i));
            current_btn.setText(File_Class.ValidateText(str));
            current_btn.setVisibility(1);
            int weight = str.length();
            if (weight < 5)
                weight = 6;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, weight);
            lp.setMargins((int) getResources().getDimension(R.dimen.margin),
                    (int) getResources().getDimension(R.dimen.margin),
                    (int) getResources().getDimension(R.dimen.margin),
                    (int) getResources().getDimension(R.dimen.margin));
          //  current_btn.setTextSize(getResources().getDimensionPixelSize(R.dimen.textSize));
            current_btn.setLayoutParams(lp);
            if (counter == 2) {
                id++;
                counter = -1;
            }
            id++;
            counter++;
        }

        return view;
    }

}
