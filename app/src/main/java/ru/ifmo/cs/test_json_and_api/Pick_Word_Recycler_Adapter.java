package ru.ifmo.cs.test_json_and_api;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Pick_Word_Recycler_Adapter extends RecyclerView.Adapter<Pick_Word_Recycler_Adapter.ViewHolder> {

    private ArrayList<String> mDataset;

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public TextView mNoun;
        public TextView mVerb;
        public TextView mAdj;
        public TextView mAdv;
        public String json;
        public boolean saved=false;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.main_word);
            mNoun = (TextView) v.findViewById(R.id.noun);
            mVerb = (TextView) v.findViewById(R.id.verb);
            mAdj = (TextView) v.findViewById(R.id.adj);
            mAdv = (TextView) v.findViewById(R.id.adv);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context mContext = v.getContext();
                    Intent intent = new Intent(mContext,Show_Word_Activity.class);
                    intent.putExtra("info",json);
                    intent.putExtra("class","pick");
                    intent.putExtra("saved",saved);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    // Конструктор
    public Pick_Word_Recycler_Adapter(ArrayList<String> dataset) {
        mDataset = dataset;
    }


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public Pick_Word_Recycler_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_pick_word, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.json = mDataset.get(position);
        Context c = holder.itemView.getContext();
        String s = File_Class.readFile(c,"saved_words");
        if(s.contains(holder.json)) {
            holder.saved = true;
            holder.itemView.findViewById(R.id.parts_of_speech_layout).setBackgroundResource(R.drawable.green_left_border);
            holder.itemView.findViewById(R.id.word_layout).setBackgroundColor(Color.GREEN);
        }else {
            holder.saved=false;
            holder.itemView.findViewById(R.id.parts_of_speech_layout).setBackgroundResource(R.drawable.white_left_border);
            holder.itemView.findViewById(R.id.word_layout).setBackgroundColor(Color.WHITE);
        }
        HashMap<String, String> map = Json_Class.getPartsOfSpeech(holder.json);
        String main = map.get("main");
        holder.mTextView.setText(File_Class.ValidateText(main));

        String noun = map.get("noun");
        if (noun != null)
            holder.mNoun.setText("Noun: "+File_Class.ValidateText(noun));
        else
            holder.mNoun.setText("Noun: -");

        String verb = map.get("verb");
        if (verb != null)
            holder.mVerb.setText("Verb: "+File_Class.ValidateText(verb));
        else
            holder.mVerb.setText("Verb: -");

        String adj = map.get("adj");
        if (adj != null)
            holder.mAdj.setText("Adj: "+File_Class.ValidateText(adj));
        else
            holder.mAdj.setText("Adj: -");

        String adv = map.get("adv");
        if (adv != null)
            holder.mAdv.setText("Adv: "+File_Class.ValidateText(adv));
        else
            holder.mAdv.setText("Adv: -");
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}